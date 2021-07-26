package com.magnatron.WuzzufSparkSpring;


import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;



@Configuration
@PropertySource("classpath:application.properties")

public class SparkServices {

	@Bean
	public SparkConf sparkConf() {
		SparkConf sparkConf = new SparkConf()
				.setAppName("Java Spark").setMaster("local[*]").set("spark.testing.memory", "2147480000");

		return sparkConf;
	}

	@Bean
	public JavaSparkContext javaSparkContext() {
		return new JavaSparkContext(sparkConf());
	}

	@Bean
	public SparkSession sparkSession() {
		return SparkSession
				.builder()
				.sparkContext(javaSparkContext().sc())
				.appName("Java Spark")
				.getOrCreate();
	}

	@Bean
	public Dataset<Row> mDataset() {
		DataFrameReader dataFrameReader = sparkSession().read();
		//
		dataFrameReader.option("header", "true");

		Dataset<Row> dataset = dataFrameReader.csv("src\\\\main\\\\resources\\\\Wuzzuf_Jobs.csv");
		// String data_filepath = "src\\main\\resources\\Wuzzuf_Jobs.csv";
		//Dataset<Row> dataset = sparkSession().read().format("csv").option("header", true).load(data_filepath);
		return dataset;
	}

	@Bean
	public Dataset<Job> mDataPojo() {
		Dataset<Job> ds_jobs = mDataset().map(new JobMapper(), Encoders.bean(Job.class));
		return ds_jobs;}

		@Bean
		public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer () {
			return new PropertySourcesPlaceholderConfigurer();
		}

	}
