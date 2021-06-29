package com.company;

public class City {
    int id;
    String name ;
    int population;
    String countrCode;

    public City(int id, String name, int population, String countrCode) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.countrCode = countrCode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public String getCountrCode() {
        return countrCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setCountrCode(String countrCode) {
        this.countrCode = countrCode;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", population=" + population +
                ", countrCode='"+countrCode + '\'' +
                '}';
    }
}

