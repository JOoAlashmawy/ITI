package com.magnatron.WuzzufSparkSpring;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

//import org.apache.spark.ml.clustering.KMeans;
//import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.commons.io.IOUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructField;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.style.PieStyler.AnnotationType;
import org.knowm.xchart.style.PieStyler.ClockwiseDirectionType;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import static org.apache.spark.sql.functions.regexp_replace;
import static org.apache.spark.sql.functions.trim;

@RestController
public class SparkController
{

    @Autowired
    private SparkSession sparkSession;
    @Autowired
    private JavaSparkContext sparkContext;
    
    @Autowired
    private Dataset<Row> mDataset;
    
    @Autowired
    private Dataset<Job> mDataPojo;
    // Home Page:
	@GetMapping(value = "/")
	public String index() {
		StringBuilder builder = new StringBuilder();

		builder.append("<h1 style=\"color:red;font-family:Arial;text-align:center;\"><u>Wuzzuf Dataset Project</u></h1>");
		builder.append("<b><p style=\"color:red;font-family:Arial;\"><u>Introduction:</u></p><b>");
		builder.append("<b><p style=\"font-family:Arial;\">Java web application that uses the spark library to perform certain operations on a Wuzzuf Dataset.</p><b>");
		builder.append("<b><p style=\"color:red;font-family:Arial;\"><u>Operations:</u></p><b>");

		builder.append("<a style=\"color:blue;font-family:Arial;\"" + ">Load Dataset</a> &#10233; " +
				"<a style=\"font-family:Arial;color:black;\" href=http://localhost:8080/loadDataset >http://localhost:8080/loadDataset<a>");
		builder.append("<br><a style=\"color:blue;font-family:Arial;\" " +
				">Summary&Structure</a> &#10233; " +
				"<a style=\"font-family:Arial;color:black;\" href=http://localhost:8080/summaryStructure >http://localhost:8080/summaryStructure<a>");
		builder.append("<br><a style=\"color:blue;font-family:Arial;\" " +
				">Cleaning Data</a> &#10233; " +
				"<a style=\"font-family:Arial;color:black;\" href=http://localhost:8080/cleaningData >http://localhost:8080/cleaningData<a>");
		builder.append("<br><a style=\"color:blue;font-family:Arial;\" " +
				">Jobs Per Company</a> &#10233; " +
				"<a style=\"font-family:Arial;color:black;\" href=http://localhost:8080/jobsPerCompany > http://localhost:8080/jobsPerCompany<a>");
		builder.append("<br><a style=\"color:blue;font-family:Arial;\" " +
				">Jobs Per Company Pie Chart</a> &#10233; " +
				"<a style=\"font-family:Arial;color:black;\" href=http://localhost:8080/jobsPerCompanyPieChart > http://localhost:8080/jobsPerCompanyPieChart<a>");
		builder.append("<br><a style=\"color:blue;font-family:Arial;\" " +
				">Popular Job Titles</a> &#10233; " +
				"<a style=\"font-family:Arial;color:black;\" href=http://localhost:8080/popularJobTitles > http://localhost:8080/popularJobTitles<b>");
		builder.append("<br><a style=\"color:blue;font-family:Arial;\" " +
				">Popular Job Titles Bar Chart</a> &#10233; " +
				"<a style=\"font-family:Arial;color:black;\" href=http://localhost:8080/popularJobTitlesBarChart > http://localhost:8080/popularJobTitlesBarChart<a>");
		builder.append("<br><a style=\"color:blue;font-family:Arial;\" " +
				">Popular Areas</a> &#10233; " +
				"<a style=\"font-family:Arial;color:black;\" href=http://localhost:8080/popularAreas > http://localhost:8080/popularAreas<a>");
		builder.append("<br><a style=\"color:blue;font-family:Arial;\" " +
				">Popular Areas Bar Chart</a> &#10233; " +
				"<a style=\"font-family:Arial;color:black;\" href=http://localhost:8080/popularAreasBarChart > http://localhost:8080/popularAreasBarChart<a>");
		builder.append("<br><a style=\"color:blue;font-family:Arial;\" " +
				">Skills</a> &#10233; " +
				"<a style=\"font-family:Arial;color:black;\" href=http://localhost:8080/skills > http://localhost:8080/skills<a>");
		builder.append("<br><a style=\"color:blue;font-family:Arial;\" " +
				">Years Experience Column Factorized</a> &#10233; " +
				"<a style=\"font-family:Arial;color:black;\" href=http://localhost:8080/factorize > http://localhost:8080/factorize<a>");
		builder.append("<p style=\"position:absolute;bottom:0; left:0;width:100%;text-align:center\">Abdel Rahman Alaa elDin, Yousef Mohamed, Amr Ahmed.</p>");

		return builder.toString(); }
	//Load Dataset
    @GetMapping(value = "/loadDataset" , produces	 =  MediaType.TEXT_PLAIN_VALUE )
    public @ResponseBody String show()
	{ return mDataset.showString(10,0,false); }
	//Summary and Structure
	@GetMapping(value = "/summaryStructure")
	public @ResponseBody
	StringBuilder displayBasicSummaryStructure()
	{//-- n_records
		StringBuilder builder=new StringBuilder();
		long records_before_cleaning = mDataset.count();
		builder.append("<h1 style=\"color:blue;font-family:Arial;\">Data Summary & Structure </h1>");
		builder.append("<br><b style=\"font-family:Arial;\">Number of records : "+mDataset.count()+"<b><br>");
		builder.append("<br><b style=\"font-family:Arial;\">Number of columns : "+mDataset.columns().length+"<b><br>");
		builder.append("<br><b style=\"font-family:Arial;\">Column names & DataType : <b><br>");
		builder.append(mDataset.schema().toDDL().replaceAll(",","\n"));
		return  builder; }

	//cleaning the data
	@GetMapping(value = "/cleaningData")
	public @ResponseBody StringBuilder dropNullAndDuplicates()
	{
		Dataset<Row> mDatasetCleanedDuplicates = mDataset.dropDuplicates();
		Dataset<Row> mDatasetCleanedNulls = mDatasetCleanedDuplicates.drop();
		StringBuilder builder=new StringBuilder();
		long records_before_cleaning = mDataset.count();
		builder.append("<h1 style=\"color:blue;font-family:Arial;\">Cleaning Duplicates & Nulls </h1>");
		builder.append("<b style=\"font-family:Arial;\">Cleaned Duplicates and Nulls </b>");
		builder.append("<br><b style=\"font-family:Arial;\">Number of records (after cleaning) : "+mDatasetCleanedNulls.count()+"<b><br>");
		//builder.append("<br><b style=\"font-family:Arial;\">Column names & DataType : <b><br>");
		//builder.append(mDataset.schema().toDDL().replaceAll(",","\n"));
		return  builder;
	}

	//jobs per company
	@GetMapping(value="/jobsPerCompany")
	public @ResponseBody
	StringBuilder jobsPerCompanyString()
	{	StringBuilder builder=new StringBuilder();
		JavaRDD<String> companiesRDD = mDataPojo.toJavaRDD().map(j->j.getCompany());
		List<Entry<String,Long>> sortedCompanies = companiesRDD.countByValue().entrySet().stream ()
				.sorted(Entry.comparingByValue()).collect (Collectors.toList ());
		builder.append("<h1 style=\"color:blue;font-family:Arial;\">Jobs Per Company </h1>");
		for (int i = sortedCompanies.size()-1; i>0; i--) {
		builder.append("<p style=\"font-family:Arial;\"> "+sortedCompanies.get(i).getKey()+" : "+sortedCompanies.get(i).getValue()+" <p>");
		builder.append(System.getProperty("line.separator"));
	}
		return builder;
		 }


	//popular job titles
	@GetMapping(value = "/popularJobTitles")
    public @ResponseBody
	StringBuilder popularJobTitlesString()
    {
    	JavaRDD<String> jobTitlesRDD = mDataPojo.toJavaRDD().map(j->j.getTitle().split("\\p{javaSpaceChar}[-||(]")[0]); //--- only get the title before any - or () descriptions
		List<Entry<String,Long>> sortedTitles = jobTitlesRDD.countByValue().entrySet().stream ()
				.sorted(Entry.comparingByValue()).collect (Collectors.toList ());
		StringBuilder builder=new StringBuilder();
		builder.append("<h1 style=\"color:blue;font-family:Arial;\">Popular Job Titles </h1>");
		for (int i = sortedTitles.size()-1; i>0; i--) {
			builder.append("<p style=\"font-family:Arial;\" > "+sortedTitles.get(i).getKey()+" : "+sortedTitles.get(i).getValue()+"<p> ");
			builder.append(System.getProperty("line.separator"));
		}
		return builder;

    }

	//popular Areas
	@GetMapping(value = "/popularAreas")
	public @ResponseBody
	StringBuilder popularAreasString()
	{
		JavaRDD<String> popularAreasRDD = mDataPojo.toJavaRDD().map(j->j.getLocation().split("\\p{javaSpaceChar}[-||(]")[0]); //--- only get the title before any - or () descriptions

		List<Entry<String,Long>> sortedAreas = popularAreasRDD.countByValue().entrySet().stream ()
				.sorted(Entry.comparingByValue()).collect (Collectors.toList ());
		StringBuilder builder=new StringBuilder();
		builder.append("<h1 style=\"color:blue;font-family:Arial;\" > Popular Areas </h1>");
		for (int i = sortedAreas.size()-1; i>0; i--) {
			builder.append("<p style=\"font-family:Arial;\" > "+sortedAreas.get(i).getKey()+" : "+sortedAreas.get(i).getValue()+"<p> ");
			builder.append(System.getProperty("line.separator"));
		}
		return builder;
	}
	//Skills
	@GetMapping(value = "/skills")
	public @ResponseBody
	StringBuilder skills()
	{
		JavaRDD<String> skillsRDD = mDataPojo.toJavaRDD()
				.flatMap(j-> Arrays.asList(j.getSkills().split("\\p{javaSpaceChar}[-||(]")[0])
						.iterator())
				.filter(ar->!ar.isEmpty()); //--- only get the title before any - or () descriptions

		List<Entry<String,Long>> sortedSkills = skillsRDD.countByValue().entrySet().stream ()
				.sorted(Entry.comparingByValue()).collect (Collectors.toList ());
		StringBuilder builder=new StringBuilder();
		builder.append("<h1 style=\"color:blue;font-family:Arial;\" > Skills </h1>");
		for (int i = sortedSkills.size()-1; i>0; i--) {
			builder.append("<p style=\"font-family:Arial;\" > "+sortedSkills.get(i).getKey()+" : "+sortedSkills.get(i).getValue()+"<p> ");
			builder.append(System.getProperty("line.separator")); }
		return builder;
	}

	//factorize years exp column
	@GetMapping(value = "/factorize",produces= MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody
	String factorize_yearExp()
	{
		Dataset<Row> mDatasetFactorized = mDataset.withColumn("YearExp_Factorized",
				regexp_replace(trim(regexp_replace(mDataset.col("YearsExp"), "[A-Za-z]", "")), "^$", "0"));
		String datasetreturn = mDatasetFactorized.select("YearsExp","YearExp_Factorized").showString(20, 0, false);
		return datasetreturn;
	}

	/////Kmeans:
//	@GetMapping(value = "/kmeans",produces=  MediaType.TEXT_PLAIN_VALUE)
//	public String kmeans(){
//		// Trains a k-means model.
//		KMeans kmeans = new KMeans().setK(2).setSeed(1L);
//		Dataset<Row> mDataestKmeans = mDataset.select("title", "company");
//		KMeansModel model = kmeans.fit(mDataestKmeans);
//		Dataset<Row> predictions = model.transform(mDataestKmeans);
//		System.out.println(predictions.showString(20,0,false));
//		return predictions.showString(20,0,false);
//}


















    //Charts

	public List<Entry<String, Long>> popularAreas()
	{
		JavaRDD<String> popularAreasRDD = mDataPojo.toJavaRDD().map(j->j.getLocation().split("\\p{javaSpaceChar}[-||(]")[0]); //--- only get the title before any - or () descriptions

		List<Entry<String,Long>> sortedAreas = popularAreasRDD.countByValue().entrySet().stream ()
				.sorted(Entry.comparingByValue()).collect (Collectors.toList ());

		return sortedAreas;
	}

	public List<Entry<String, Long>> jobsPerCompany()
	{StringBuilder sortedCompaniesString=new StringBuilder();
		JavaRDD<String> companiesRDD = mDataPojo.toJavaRDD().map(j->j.getCompany());
		List<Entry<String,Long>> sortedCompanies = companiesRDD.countByValue().entrySet().stream ()
				.sorted(Entry.comparingByValue()).collect (Collectors.toList ());
		sortedCompaniesString.append(sortedCompanies);
		return sortedCompanies; }

	public List<Entry<String, Long>> popularJobTitles()
	{
		JavaRDD<String> jobTitlesRDD = mDataPojo.toJavaRDD().map(j->j.getTitle().split("\\p{javaSpaceChar}[-||(]")[0]); //--- only get the title before any - or () descriptions

		List<Entry<String,Long>> sortedTitles = jobTitlesRDD.countByValue().entrySet().stream ()
				.sorted(Entry.comparingByValue()).collect (Collectors.toList ());
		return sortedTitles;
	}

    public void getMostDemandedTitlesChart(String filename)
    {// Bar Chart
	    CategoryChart chart2 = new CategoryChartBuilder().width(2048).height(1200).theme(ChartTheme.XChart).title("bars").build();
	    // Customize Chart
	    chart2.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
	    chart2.getStyler().setHasAnnotations(true);
		List<Entry<String, Long>> Titles = popularJobTitles();
		List<String> popularJobTitlesNames=new ArrayList<String>();
		List<Long> popularJobTitlesNumbers= new ArrayList<Long>();
		for (int i = 0; i < Titles.size(); i++) {
			popularJobTitlesNames.add(Titles.get(i).getKey());
			popularJobTitlesNumbers.add(Titles.get(i).getValue()); }
	    chart2.getStyler().setShowTotalAnnotations(true);
	    List<Integer> simpleJobTitles = new ArrayList<Integer>();
	    int size2 = popularJobTitles().size();
		List<String> poptitles = popularJobTitlesNames.subList(size2 - 10, size2);
		List<Long> poptitlesnum = popularJobTitlesNumbers.subList(size2 - 10, size2);
	    // Series
	    chart2.addSeries("Most Demanded Titles",  poptitles ,   poptitlesnum ) ;
	    try
		{ BitmapEncoder.saveBitmap(chart2, filename,BitmapFormat.PNG);
		} catch (IOException e)
		{// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public void popularAreasBarChart(String filename)
	{// Bar Chart
		CategoryChart chart2 = new CategoryChartBuilder().width(2048).height(1200).theme(ChartTheme.XChart).title("bars").build();
		// Customize Chart
		chart2.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
		chart2.getStyler().setHasAnnotations(true);
		chart2.getStyler().setShowTotalAnnotations(true);
		List<Entry<String, Long>> Areas = popularAreas();
		List<String> popularAreasNames=new ArrayList<String>();
		List<Long> popularAreasNumbers = new ArrayList<Long>();
		for (int i = 0; i < Areas.size(); i++) {
			popularAreasNames.add(Areas.get(i).getKey());
			popularAreasNumbers.add(Areas.get(i).getValue()); }
		int size = popularAreas().size();
		List<String> popnames = popularAreasNames.subList(size - 10, size);
		List<Long> popnumbers = popularAreasNumbers.subList(size - 10, size);
		// Series
		chart2.addSeries("Popular Areas",  popnames ,   popnumbers) ;
		try
		{ BitmapEncoder.saveBitmap(chart2, filename,BitmapFormat.PNG);
		} catch (IOException e)
		{// TODO Auto-generated catch block
			e.printStackTrace(); } }



    public void getMostDemandingCompaniesChart(String filename)
    {
		// Pie Chart
		PieChart chart = new PieChartBuilder().width(2048).height(1024).theme(ChartTheme.GGPlot2).title("Most Demanding Companies").build();
		List<Long> compDemands = new ArrayList<Long>();
		List<String> compNames = new ArrayList<String>();
	    int size1 =  jobsPerCompany().size();
		 jobsPerCompany().subList(size1-12, size1).forEach(cc -> {
			compDemands.add(cc.getValue());
			compNames.add(cc.getKey());
		}
		);
		int size_dem = compDemands.size();
		for(int i = size_dem; i > size_dem-10 ; i--)
		{
			chart.addSeries(compNames.get(i-1), compDemands.get(i-1));
		}
	    Color[] sliceColors = new Color[size_dem];
	    float increment =  size_dem/40.0f ;
	    float accumulator = 0;
	    for(int i = 0; i < sliceColors.length; i++)
	    {
	    	accumulator += increment;
	    	sliceColors[i] = Color.getHSBColor( 1-accumulator/5 ,  0.7f,0.95f);
	    }
	    chart.getStyler().setHasAnnotations(true);
	    chart.getStyler().setShowTotalAnnotations(false);
	    chart.getStyler().setAnnotationType(AnnotationType.LabelAndValue);
	    chart.getStyler().setAnnotationDistance(0.65);
	    chart.setTitle("Most Demanding Companies");
	    chart.getStyler().setDrawAllAnnotations(false);
	    chart.getStyler().setLegendVisible(false);
	    chart.getStyler().setClockwiseDirectionType(ClockwiseDirectionType.CLOCKWISE);
	    //chart.getStyler().setStartAngleInDegrees(90.0);
	    chart.getStyler().setSeriesColors(sliceColors);
	    try
		{
			BitmapEncoder.saveBitmap(chart, filename,BitmapFormat.PNG);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 

    @GetMapping(value = "/popularJobTitlesBarChart",produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getDemandedTitlesImage() throws IOException 
    {
     
    	getMostDemandedTitlesChart("src/main/resources/bar1.png");
    	Path filePath = Paths.get("src", "main","resources","bar1.png");
    	if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
    		System.out.println("exists!"); 
    		
    	    InputStream in = Files.newInputStream(filePath, StandardOpenOption.READ);
    	    return IOUtils.toByteArray(in);
    	}
    	InputStream in = Files.newInputStream(filePath, StandardOpenOption.READ);
        return IOUtils.toByteArray(in);
    }
	@GetMapping(value = "/popularAreasBarChart",produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] popularAreasBarChart() throws IOException
	{

		popularAreasBarChart("src/main/resources/bar2.png");
		Path filePath = Paths.get("src", "main","resources","bar2.png");
		if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
			System.out.println("exists!");

			InputStream in = Files.newInputStream(filePath, StandardOpenOption.READ);
			return IOUtils.toByteArray(in);
		}
		InputStream in = Files.newInputStream(filePath, StandardOpenOption.READ);
		return IOUtils.toByteArray(in);
	}
    @GetMapping(value = "/jobsPerCompanyPieChart",produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getDemandingCompaniesImage() throws IOException 
    {
     
    	getMostDemandingCompaniesChart("src/main/resources/pie1.png");
    	Path filePath = Paths.get("src", "main","resources","pie1.png");
    	if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
    		System.out.println("exists!"); 
    		
    	    InputStream in = Files.newInputStream(filePath, StandardOpenOption.READ);
    	    return IOUtils.toByteArray(in);
    	}
    	InputStream in = Files.newInputStream(filePath, StandardOpenOption.READ);
        return IOUtils.toByteArray(in);
    }
 
}
