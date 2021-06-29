package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        DAO pop = new DAO();

        // reading Countries file :
        List<Country> countries = pop.readcountry("Countries.csv");
        // reading cities file :
        List<City> cities = pop.readcity("Cities.csv");
        // (1) read two files and convert countries and cities to list :
        for (Country country : countries) {
            System.out.println( country);
        }
       for (City city : cities) {
            System.out.println( city);
        }

        // (2) Mapping using country code as key and list of cities as values foe each country :
        // Note : the list of cities is already sorted according to population ;

        Map<String,List<String>> cities1 = cities1 = pop.listCity(countries, cities);
       System.out.println("List of Cities "+ cities1);
       String code ;
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r) ;
        System.out.println("Enter Country code : ");
        code =br.readLine();
        List<String> vc = cities1.get(code);
        System.out.println(" The List of Cities :" + vc);


       // (3) List of Countries Population :

        List<Integer> countryPop = countries.stream().map(country -> country.getPopulation()).collect(Collectors.toList());
        System.out.println("List of Countries Population " + countryPop);

        // (4) Average Countries Population :

        OptionalDouble avgPop = countries.stream().map(country -> country.getPopulation()).mapToInt(Integer:: intValue).average();
        System.out.println("Average poulation is : "+avgPop);

        // (5) Maximum Country Poulation given the name & the code of the country :

        Optional<Country> maxPop = countries.stream().max(Comparator.comparing(Country::getPopulation));
           System.out.println("Maximum Population : " +maxPop);


        // (7) Highest Populatopn Capital  given the Capital name and country code:
        List<Integer> capitalid = new ArrayList<>();
        for(Country c : countries){
            capitalid.add(c.getCapital());

        }
       List<City > caitals = cities.stream().filter(city -> capitalid.contains(city.getId())).collect(Collectors.toList());
        Optional<City> highest   = caitals.stream().max(Comparator.comparing(City::getPopulation));
       System.out.println("Highest Population Caital : " +highest);

        //(6) Highest Population city of each country :
        cities1.forEach((k, v) -> {
            if (v.size() == 0) {
                System.out.println("Country Code :" + k + ";" + "Highest Population City :" + "[]");
            } else {

                System.out.println("Country Code :" + k + ";" + "Highest Population City :" + v.get(0));
            }
        });



    }}



