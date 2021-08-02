package com.magnatron.WuzzufSparkSpring;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

 

@Configuration
@PropertySource("classpath:application.properties")
 
public class SparkConfig
{
    @Bean
    public SparkConf sparkConf() 
    {
        SparkConf sparkConf = new SparkConf()
        		.setAppName("Java Spark").setMaster("local[*]");
         
        return sparkConf;
    }

    @Bean
    public JavaSparkContext javaSparkContext() 
    {
        return new JavaSparkContext(sparkConf());
    }

    @Bean
    public SparkSession sparkSession() 
    {
        return SparkSession
                .builder()
                .sparkContext(javaSparkContext().sc())
                .appName("Java Spark")
                .getOrCreate();
    }
    
    @Bean
    public  Dataset<Row> mDataset() 
    {
    	DataFrameReader dataFrameReader = sparkSession().read();
        dataFrameReader.option ("header", "true");
        
        Dataset<Row> dataset = dataFrameReader.csv ("src\\main\\resources\\Wuzzuf_Jobs.csv");
        return  dataset;
    }
    
    @Bean
    public Dataset<Job> mDataPojo()
    {
    	Dataset<Job> ds_jobs = mDataset().map(new JobMapper(), Encoders.bean(Job.class));
    	return ds_jobs;
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}