package com.magnatron.WuzzufSparkSpring;

/*
 * Job Pojo Class
 */
public class Job
{
	String title;
	String company;
	String location;
	String type;
	String level;
	String yearExp;
	String country;
	String Skills;
	
	public String getTitle()
	{
		return title;
	}
	public String getCompany()
	{
		return company;
	}
	public String getLocation()
	{
		return location;
	}
	public String getType()
	{
		return type;
	}
	public String getYearExp()
	{
		return yearExp;
	}
	public String getCountry()
	{
		return country;
	}
	public String getSkills()
	{
		return Skills;
	}
	public String getLevel()
	{
		return level;
	}
	public void setTitle(String title)
	{
		this.title = title.toUpperCase().trim();
	}
	public void setCompany(String company)
	{
		this.company = company.toUpperCase().trim();
	}
	public void setLocation(String location)
	{
		this.location = location.toUpperCase().trim();
	}
	public void setType(String type)
	{
		this.type = type.toUpperCase().trim();
	}

	public void setLevel(String level)
	{
		this.level = level.toUpperCase().trim();
	}
	public void setYearExp(String year_experience)
	{
		this.yearExp = year_experience.toUpperCase().trim();
	}
	public void setCountry(String country)
	{
		this.country = country.toUpperCase().trim();
	}
	public void setSkills(String skills)
	{
		Skills = skills.toUpperCase().trim();
	}

}
