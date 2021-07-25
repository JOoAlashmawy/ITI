package com.magnatron.WuzzufSparkSpring;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

public class JobMapper implements MapFunction<Row, Job>
{

	@Override
	public Job call(Row value) throws Exception
	{
		Job new_job =  new Job();
		new_job.setTitle(value.getAs("Title"));
		new_job.setCompany(value.getAs("Company"));
		new_job.setLocation(value.getAs("Location"));
		new_job.setType(value.getAs("Type"));
		new_job.setLevel(value.getAs("Level"));
		new_job.setYearExp(value.getAs("YearsExp"));
		new_job.setCountry(value.getAs("Country"));
		new_job.setSkills(value.getAs("Skills"));
	
		return new_job;
	}

}
