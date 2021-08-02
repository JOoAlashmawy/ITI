package com.magnatron.WuzzufSparkSpring;

import static org.apache.spark.sql.functions.regexp_replace;
import static org.apache.spark.sql.functions.trim;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.feature.OneHotEncoder;
import org.apache.spark.ml.feature.OneHotEncoderModel;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Services Class 
 * 
 */

@Service
public class SparkServices
{
    @Autowired
    private Dataset<Row> mDataset;
    
    @Autowired
    private Dataset<Job> mDataPojo;
    
    public String showDataset()
	{ 
    	return mDataset.showString(10,35,false); 
    }
    
    public long getRowCount()
    {
    	return mDataset.count();
    }
    
    public int getColumnCount()
    {
    	return mDataset.columns().length;
    }
    
    public String[] getSchemaStructure()
    {
	    return mDataset.schema().toDDL().split(",");
    }
    
    public void dropDuplicatesAndNulls()
    {
    	//--- duplicates
    	mDataset = mDataset.dropDuplicates();
    	//--- Nulls
    	mDataset = mDataset.drop();
    }
    
    public List<Entry<String,Long>> getSortedCompanies()
    {
		JavaRDD<String> companiesRDD = mDataPojo.toJavaRDD().map(j->j.getCompany());
		List<Entry<String,Long>> sortedCompanies = companiesRDD.countByValue()
				.entrySet().stream().sorted(Entry.comparingByValue())
				.collect (Collectors.toList ());
		
		return sortedCompanies;
    }

    public List<Entry<String,Long>> getSortedTitles()
    {
    	JavaRDD<String> jobTitlesRDD = mDataPojo.toJavaRDD().map(j->j.getTitle().split("\\p{javaSpaceChar}[-||(]")[0]); //--- only get the title before any - or () descriptions
    	
    	List<Entry<String,Long>> sortedTitles = jobTitlesRDD.countByValue()
    			.entrySet().stream ()
    			.sorted(Entry.comparingByValue())
    			.collect (Collectors.toList ());
		
		return sortedTitles;
    }

    public List<Entry<String,Long>> getSortedAreas()
    {
    	JavaRDD<String> popularAreasRDD = mDataPojo.toJavaRDD().map(j->j.getLocation().split("\\p{javaSpaceChar}[-||(]")[0]); //--- only get the title before any - or () descriptions

    	List<Entry<String,Long>> sortedAreas = popularAreasRDD.countByValue()
    			.entrySet().stream ()
    			.sorted(Entry.comparingByValue())
    			.collect (Collectors.toList ());
		
		return sortedAreas;
    }
    
    public List<Entry<String,Long>> getSortedSkills()
    {
    	JavaRDD<String> skillsRDD = mDataPojo.toJavaRDD().flatMap(j -> Arrays.asList
    			(j.getSkills().split(",")).iterator()).filter(ar -> !ar.isEmpty());
    	
    	List<Entry<String,Long>> sortedSkills = skillsRDD.countByValue()
    			.entrySet().stream ().sorted(Entry.comparingByValue())
    			.collect (Collectors.toList ());
		
		return sortedSkills;
    }
    
    public String getFactorize_YearExp()
	{
    	mDataset = mDataset.withColumn("YearExp_Factorized",
				regexp_replace(
						trim(regexp_replace(mDataset.col("YearsExp"), "[A-Za-z]", ""))
						, "^$", "0"));
		String year_exp = mDataset.select("YearsExp","YearExp_Factorized").showString(30, 0, false);
		return year_exp;
	}
    
    public String getKmeans_Data()
	{
		// Trains a k-means model.
		KMeans kmeans = new KMeans().setK(3).setSeed(1L);
		 
        StringIndexerModel indexer = new StringIndexer()
        		  .setInputCols(new String[] {"Title", "Company"})
        		  .setOutputCols(new String[] {"TitleIndex", "CompanyIndex"})
        		  .fit(mDataset);
        
        Dataset<Row> indexed = indexer.transform(mDataset);
         
        
        OneHotEncoder encoder = new OneHotEncoder()
        		  .setInputCols(new String[] {"TitleIndex", "CompanyIndex"})
        		  .setOutputCols(new String[] {"TitleVec", "CompanyVec"});
        
        OneHotEncoderModel Encodermodel = encoder.fit(indexed);
        Dataset<Row> encoded = Encodermodel.transform(indexed);
   
        
        String inputColumns[] = {"TitleVec", "CompanyVec"};	
        VectorAssembler vectorAssembler = new VectorAssembler ();
        
        vectorAssembler.setInputCols (inputColumns);
        vectorAssembler.setOutputCol ("features");
       
        Dataset<Row> mDataestKmeans = vectorAssembler.transform ( encoded.na ().drop ());
		KMeansModel model = kmeans.fit(mDataestKmeans);
		Dataset<Row> predictions = model.transform(mDataestKmeans);
		
		System.out.println(predictions.showString(20,50,false));
		// Evaluate clustering by computing Silhouette score
		//ClusteringEvaluator evaluator = new ClusteringEvaluator();

		//double silhouette = evaluator.evaluate(predictions);
		//System.out.println("Silhouette with squared euclidean distance = " + silhouette);

		Vector[] centers = model.clusterCenters();
		System.out.println("Cluster Centers: ");
		for (Vector center: centers) 
		{
		  System.out.println(center);
		}
		
		String data_str = "";
		data_str += "Centers" + "\n";
		for (Vector center: centers) 
		{
			data_str += center + "\n";
		}
		
		return data_str;
	}
}
