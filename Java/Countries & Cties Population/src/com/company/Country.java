package com.company;

public class Country {
        String Code ;
        String name ;
        String Continent ;
        double surfaceArea ;
        int population;
        double gnp ;
        int capital ;


    public Country(String code, String name, String continent, double surfaceArea, int population, double gnp, int capital) {
        Code = code;
        this.name = name;
        Continent = continent;
        this.surfaceArea = surfaceArea;
        this.population = population;
        this.gnp = gnp;
        this.capital = capital;
    }

    public String getCode() {
        return Code;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return Continent;
    }

    public double getSurfaceArea() {
        return surfaceArea;
    }

    public int getPopulation() {
        return population;
    }

    public double getGnp() {
        return gnp;
    }

    public int getCapital() {
        return capital;
    }

    public void setCode(String code) {
        Code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContinent(String continent) {
        Continent = continent;
    }

    public void setSurfaceArea(double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setGnp(double gnp) {
        this.gnp = gnp;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    @Override
    public String toString() {
        return "Country{" +
                "Code='" + Code + '\'' +
                ", name='" + name + '\'' +

                ", population=" + population +

                '}';
    }
}


