package com.magnatron.WuzzufSparkSpring;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.io.IOUtils;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.style.PieStyler.AnnotationType;
import org.knowm.xchart.style.PieStyler.ClockwiseDirectionType;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * Controller Class 
 * 
 */

@RestController
public class SparkController
{
    @Autowired
    private SparkServices services = new SparkServices();
    
    //------ Home Page:
	@GetMapping(value = "/")
	public String index() 
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("<h1 style=\"text-align:center;\"><img src=\"https://d33v4339jhl8k0.cloudfront.net/docs/assets/5a1d81bd2c7d3a272c0e01dd/images/5d5a6bc40428634552d85802/WUZZUF_Logotype_Blue_RBG.png\" alt=\"wuzzuf_logo\"> </h1>");
		
		builder.append("<h1 style=\"color:DarkSlateGray;font-family:Arial;text-align:center;\">Wuzzuf Dataset Job Market Analysis Project</h1>");
		builder.append("<p style=\"color:DimGray;font-family:Arial;\"><b><u>Introduction:</u></b></p>");
		builder.append("<p style=\"font-family:Arial;\">Java web application that uses the spark library to perform certain operations on Wuzzuf Job Market Dataset.</p>");
		builder.append("<p style=\"color:DimGray;font-family:Arial;\"><b><u>Operations:</u></b></p>");

		builder.append("<a style=\"color:Teal;font-family:Arial;\"" + ">Show Dataset</a> &emsp;&#10233;&emsp;" +
				"<a style=\"font-family:Arial;color:Navy;\" href=http://localhost:8080/showDataset >http://localhost:8080/showDataset<a>");

		builder.append("<br><a style=\"color:Teal;font-family:Arial;\" " +
				">Summary&Structure</a> &emsp;&#10233;&emsp;" +
				"<a style=\"font-family:Arial;color:Navy;\" href=http://localhost:8080/summaryStructure >http://localhost:8080/summaryStructure<a>");
		
		builder.append("<br><a style=\"color:Teal;font-family:Arial;\" " +
				">Cleaning Data</a>  &emsp;&#10233;&emsp;" +
				"<a style=\"font-family:Arial;color:Navy;\" href=http://localhost:8080/cleaningData >http://localhost:8080/cleaningData<a>");
		
		builder.append("<br><a style=\"color:Teal;font-family:Arial;\" " +
				">Jobs Per Company</a> &emsp;&#10233;&emsp;" +
				"<a style=\"font-family:Arial;color:Navy;\" href=http://localhost:8080/jobsPerCompany > http://localhost:8080/jobsPerCompany<a>");
		
		builder.append("<br><a style=\"color:Teal;font-family:Arial;\" " +
				">Jobs Per Company Pie Chart</a> &emsp;&#10233;&emsp;" +
				"<a style=\"font-family:Arial;color:Navy;\" href=http://localhost:8080/jobsPerCompanyPieChart > http://localhost:8080/jobsPerCompanyPieChart<a>");
		
		builder.append("<br><a style=\"color:Teal;font-family:Arial;\" " +
				">Popular Job Titles</a> &emsp;&#10233;&emsp;" +
				"<a style=\"font-family:Arial;color:Navy;\" href=http://localhost:8080/popularJobTitles > http://localhost:8080/popularJobTitles");
		
		builder.append("<br><a style=\"color:Teal;font-family:Arial;\" " +
				">Popular Job Titles Bar Chart</a> &emsp;&#10233;&emsp;" +
				"<a style=\"font-family:Arial;color:Navy;\" href=http://localhost:8080/popularJobTitlesBarChart > http://localhost:8080/popularJobTitlesBarChart<a>");
		
		builder.append("<br><a style=\"color:Teal;font-family:Arial;\" " +
				">Popular Areas</a> &emsp;&#10233;&emsp;" +
				"<a style=\"font-family:Arial;color:Navy;\" href=http://localhost:8080/popularAreas > http://localhost:8080/popularAreas<a>");
		
		builder.append("<br><a style=\"color:Teal;font-family:Arial;\" " +
				">Popular Areas Bar Chart</a> &emsp;&#10233;&emsp;" +
				"<a style=\"font-family:Arial;color:Navy;\" href=http://localhost:8080/popularAreasBarChart > http://localhost:8080/popularAreasBarChart<a>");
		
		builder.append("<br><a style=\"color:Teal;font-family:Arial;\" " +
				">Skills</a> &emsp;&#10233;&emsp;" +
				"<a style=\"font-family:Arial;color:Navy;\" href=http://localhost:8080/skills > http://localhost:8080/skills<a>");
		
		builder.append("<br><a style=\"color:Teal;font-family:Arial;\" " +
				">Years Experience Column Factorized</a> &emsp;&#10233;&emsp;" +
				"<a style=\"font-family:Arial;color:Navy;\" href=http://localhost:8080/factorize > http://localhost:8080/factorize<a>");
		
		builder.append("<p style=\"position:absolute;bottom:0; left:0;width:100%;color:Navy;text-align:center\">**Amr Ahmed	|	Abd Elrahman Alaa Eldin	|	Youssef Mohamed**</p>");

		return builder.toString(); 
	}
	
	//Show First 10 Rows Dataset
    @GetMapping(value = "/showDataset", produces= MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String show()
	{ 
    	return services.showDataset();
    }
    
	//------ Show Summary and Structure
	@GetMapping(value = "/summaryStructure")
	public @ResponseBody StringBuilder displayBasicSummaryStructure()
	{
		long rowCount = services.getRowCount();
		int columnCount = services.getColumnCount();

		StringBuilder builder=new StringBuilder();
		builder.append("<h1 style=\"text-align:center;\"><img src=\"https://d33v4339jhl8k0.cloudfront.net/docs/assets/5a1d81bd2c7d3a272c0e01dd/images/5d5a6bc40428634552d85802/WUZZUF_Logotype_Blue_RBG.png\" alt=\"wuzzuf_logo\"> </h1>");
		builder.append("<h1 style=\"text-align:center;color:DarkSlateGray;font-family:Arial;\">Data Summary & Structure </h1>");
		builder.append("<br><b style=\"color:blue;font-family:Arial;\">Number of records : "+ rowCount +"<b><br>");
		builder.append("<br><b style=\"color:blue;font-family:Arial;\">Number of columns : " + columnCount +"<b><br>");
		builder.append("<br><b style=\"color:blue;font-family:Arial;\">Column names & DataType : <b><br>");
		builder.append("<br><b style=\"color:black;font-family:Arial;\"><b><br>");
		
		String[] column_structure = services.getSchemaStructure();
		for(String str : column_structure)
		{
			builder.append("<p>" + str + "</p>");
		}
		return  builder; 
	}

	//------ Cleaning The Data
	@GetMapping(value = "/cleaningData")
	public @ResponseBody StringBuilder dropNullAndDuplicates()
	{
		long rec_before_cleaning = services.getRowCount();
		services.dropDuplicatesAndNulls();
		long rec_after_cleaning = services.getRowCount();

		StringBuilder builder=new StringBuilder();
		 
		builder.append("<h1 style=\"text-align:center;\"><img src=\"https://d33v4339jhl8k0.cloudfront.net/docs/assets/5a1d81bd2c7d3a272c0e01dd/images/5d5a6bc40428634552d85802/WUZZUF_Logotype_Blue_RBG.png\" alt=\"wuzzuf_logo\"> </h1>");
		builder.append("<h1 style=\"text-align:center;color:DarkSlateGray;font-family:Arial;\">Cleaning Duplicates & Nulls </h1>");
		builder.append("<b> style=\"font-family:Arial;\">Cleaned Duplicates and Nulls </b>");
		builder.append("<br><b style=\"font-family:Arial;\">Number of records (before cleaning) : "+ rec_before_cleaning +"<b><br>");
		builder.append("<br><b style=\"font-family:Arial;\">Number of records (after cleaning) : "+ rec_after_cleaning +"<b><br>");

		return  builder;
	}

	//------ Jobs per Company
	@GetMapping(value="/jobsPerCompany")
	public @ResponseBody StringBuilder jobsPerCompanyString()
	{	
		List<Entry<String,Long>> sortedCompanies = services.getSortedCompanies();
		
		StringBuilder builder=new StringBuilder();
		builder.append("<h1 style=\"text-align:center;\"><img src=\"https://d33v4339jhl8k0.cloudfront.net/docs/assets/5a1d81bd2c7d3a272c0e01dd/images/5d5a6bc40428634552d85802/WUZZUF_Logotype_Blue_RBG.png\" alt=\"wuzzuf_logo\"> </h1>");
		builder.append("<h1 style=\"text-align:center;color:DarkSlateGray;font-family:Arial;\">Jobs Per Company </h1>");
		
		for (int i = sortedCompanies.size()-1; i>0; i--) 
		{
			builder.append("<p style=\"font-family:Arial;\"> " + sortedCompanies.get(i).getKey()+" : "+sortedCompanies.get(i).getValue()+" <p>");
			builder.append(System.getProperty("line.separator"));
		}
		return builder;
	}


	//------ Popular Job Titles
	@GetMapping(value = "/popularJobTitles")
    public @ResponseBody
	StringBuilder popularJobTitlesString()
    {
		List<Entry<String,Long>> sortedTitles = services.getSortedTitles();
		
		StringBuilder builder=new StringBuilder();
		
		builder.append("<h1 style=\"text-align:center;\"><img src=\"https://d33v4339jhl8k0.cloudfront.net/docs/assets/5a1d81bd2c7d3a272c0e01dd/images/5d5a6bc40428634552d85802/WUZZUF_Logotype_Blue_RBG.png\" alt=\"wuzzuf_logo\"> </h1>");
		builder.append("<h1 style=\"text-align:center;color:DarkSlateGray;font-family:Arial;\"> Popular Job Titles </h1>");
		
		for (int i = sortedTitles.size()-1; i>0; i--) {
			builder.append("<p style=\"font-family:Arial;\" > "+sortedTitles.get(i).getKey()+" : "+sortedTitles.get(i).getValue()+"<p> ");
			builder.append(System.getProperty("line.separator"));
		}
		return builder;
    }

	//------ Popular Areas
	@GetMapping(value = "/popularAreas")
	public @ResponseBody StringBuilder popularAreasString()
	{

		List<Entry<String,Long>> sortedAreas = services.getSortedAreas();
		StringBuilder builder=new StringBuilder();
 
		builder.append("<h1 style=\"text-align:center;\"><img src=\"https://d33v4339jhl8k0.cloudfront.net/docs/assets/5a1d81bd2c7d3a272c0e01dd/images/5d5a6bc40428634552d85802/WUZZUF_Logotype_Blue_RBG.png\" alt=\"wuzzuf_logo\"> </h1>");
		builder.append("<h1 style=\"text-align:center;color:DarkSlateGray;font-family:Arial;\"> Popular Areas </h1>");
		
		for (int i = sortedAreas.size()-1; i>0; i--) {
			builder.append("<p style=\"font-family:Arial;\" > "+sortedAreas.get(i).getKey()+" : "+sortedAreas.get(i).getValue()+"<p> ");
			builder.append(System.getProperty("line.separator"));
		}
		
		return builder;
	}
	
	//------ Frequent Skills
	@GetMapping(value = "/skills")
	public @ResponseBody
	StringBuilder skills()
	{
		List<Map.Entry<String,Long>> sortedSkills = services.getSortedSkills();
		
		StringBuilder builder=new StringBuilder();
		builder.append("<h1 style=\"text-align:center;\"><img src=\"https://d33v4339jhl8k0.cloudfront.net/docs/assets/5a1d81bd2c7d3a272c0e01dd/images/5d5a6bc40428634552d85802/WUZZUF_Logotype_Blue_RBG.png\" alt=\"wuzzuf_logo\"> </h1>");
		builder.append("<h1 style=\"text-align:center;color:DarkSlateGray;font-family:Arial;\"> Most Required Skills </h1>");
		
		for (int i = sortedSkills.size()-1; i>0; i--) {
			builder.append("<p style=\"font-family:Arial;\" > "+sortedSkills.get(i).getKey()+" : "+sortedSkills.get(i).getValue()+"<p> ");
			builder.append(System.getProperty("line.separator")); }
		return builder;
	}

	//------ Factorize Years of Experience Column
	@GetMapping(value = "/factorize",produces=  MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String factorize_yearExp()
	{
		return services.getFactorize_YearExp();
	}

	//------ Kmeans
	@GetMapping(value = "/kmeans",produces=  MediaType.TEXT_PLAIN_VALUE)
	public String kmeans()
	{
        return services.getKmeans_Data();
	}
	
    @GetMapping(value = "/popularJobTitlesBarChart",produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getDemandedTitlesImage() throws IOException 
    {
    	getMostDemandedTitlesChart("src/main/resources/bar1.png");
    	Path filePath = Paths.get("src", "main","resources","bar1.png");
    	
    	if (Files.exists(filePath) && !Files.isDirectory(filePath)) 
    	{
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
		
		if (Files.exists(filePath) && !Files.isDirectory(filePath)) 
		{
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
    	
    	if (Files.exists(filePath) && !Files.isDirectory(filePath)) 
    	{
    	    InputStream in = Files.newInputStream(filePath, StandardOpenOption.READ);
    	    return IOUtils.toByteArray(in);
    	}
    	
    	InputStream in = Files.newInputStream(filePath, StandardOpenOption.READ);
        return IOUtils.toByteArray(in);
    }
    
    
	//------ Chart Construction 
    public void getMostDemandedTitlesChart(String filename)
    {
	    CategoryChart chart2 = new CategoryChartBuilder().width(1200).height(600).theme(ChartTheme.XChart).title("Popular Job Titles").build();

	    chart2.getStyler().setLegendVisible(false);
	    chart2.getStyler().setHasAnnotations(true);
	    chart2.getStyler().setXAxisLabelRotation(90);
	    
	    Font myfont = new Font("Arial",2,12);
	    chart2.getStyler().setAxisTickLabelsFont(myfont);
	    
		List<Entry<String, Long>> Titles = services.getSortedTitles();
		
		List<String> popularJobTitlesNames=new ArrayList<String>();
		List<Long> popularJobTitlesNumbers= new ArrayList<Long>();
		
		for (int i = 0; i < Titles.size(); i++) 
		{
			popularJobTitlesNames.add(Titles.get(i).getKey());
			popularJobTitlesNumbers.add(Titles.get(i).getValue()); 
		}
	    
		chart2.getStyler().setShowTotalAnnotations(true);
	 
	    int size = Titles.size();
		List<String> poptitles = popularJobTitlesNames.subList(size - 10, size);
		List<Long> poptitlesnum = popularJobTitlesNumbers.subList(size - 10, size);
	    
	    chart2.addSeries("Frequency",  poptitles ,   poptitlesnum ) ;
	    try
		{ 
	    	BitmapEncoder.saveBitmap(chart2, filename,BitmapFormat.PNG);
		} 
	    catch (IOException e)
		{
			e.printStackTrace();
		}
    }

	public void popularAreasBarChart(String filename)
	{ 
		CategoryChart chart2 = new CategoryChartBuilder().width(1200).height(600).theme(ChartTheme.XChart).title("Popular Areas").build();

		chart2.getStyler().setLegendVisible(false);
		chart2.getStyler().setHasAnnotations(true);
		chart2.getStyler().setShowTotalAnnotations(true);
		
		List<Entry<String, Long>> Areas = services.getSortedAreas();
		
		List<String> popularAreasNames=new ArrayList<String>();
		List<Long> popularAreasNumbers = new ArrayList<Long>();
		
		for (int i = 0; i < Areas.size(); i++) 
		{
			popularAreasNames.add(Areas.get(i).getKey());
			popularAreasNumbers.add(Areas.get(i).getValue()); 
		}
		
		int size = Areas.size();
		List<String> popnames = popularAreasNames.subList(size - 10, size);
		List<Long> popnumbers = popularAreasNumbers.subList(size - 10, size);

		chart2.addSeries("Frequency",  popnames ,   popnumbers) ;
		try
		{ 
			BitmapEncoder.saveBitmap(chart2, filename,BitmapFormat.PNG);
		} 
		catch (IOException e)
		{
			e.printStackTrace(); 
		} 
	}

    public void getMostDemandingCompaniesChart(String filename)
    {
		PieChart chart = new PieChartBuilder().width(800).height(600).theme(ChartTheme.GGPlot2).title("Most Demanding Companies").build();
		List<Long> compDemands = new ArrayList<Long>();
		List<String> compNames = new ArrayList<String>();
		
		List<Entry<String, Long>> jobsPerCompany = services.getSortedCompanies();
		
	    int size1 =  jobsPerCompany.size();
	    
	    jobsPerCompany.subList(size1-12, size1)
	    .forEach(cc -> 
	    {
			compDemands.add(cc.getValue());
			compNames.add(cc.getKey());
		});
	    
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
	    chart.getStyler().setSeriesColors(sliceColors);
	    
	    try
		{
			BitmapEncoder.saveBitmap(chart, filename,BitmapFormat.PNG);
		} 
	    catch (IOException e)
		{

			e.printStackTrace();
		}
    }
 

 
}
