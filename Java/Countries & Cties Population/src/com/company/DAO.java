package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DAO {
    List<Country> countries;
    List<City> cities;

    public DAO() {
        countries = new ArrayList<Country>();
        cities = new ArrayList<City>();
    }


    public List<Country> readcountry(String file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            line = br.readLine();

            while (line != null) {
                String[] columns = line.split(",");
                double area = Double.parseDouble(columns[4]);
                int pop = Integer.parseInt(columns[3]);
                double GNP = Double.parseDouble(columns[5]);
                int capital = Integer.parseInt(columns[6]);
                Country info = new Country(columns[0], columns[1], columns[2],area, pop,GNP, capital);
                countries.add(info);
                line = br.readLine();


            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return countries;
    }

    public List<City> readcity(String file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            line = br.readLine();

            while (line != null) {
                String[] columns = line.split(",");
                int POP = Integer.parseInt(columns[2]);
                int ID = Integer.parseInt(columns[0]);
                City temp = new City(ID, columns[1], POP, columns[3]);
                cities.add(temp);
                line = br.readLine();


            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }





    public Map<String, List<String>> listCity(List<Country> listOfCountrys, List<City> listOfCitys) {
        Map<String, List<String>> Citylist = new HashMap<>();

        for (Country c: listOfCountrys){
            String contrCode = c.getCode();

            List<String> cityList = listOfCitys.stream()
                    .filter(city -> city.getCountrCode().equals(contrCode))
                    .sorted(Comparator.comparingInt(City::getPopulation)) // c -> c.getPopulation
                    .map(city -> city.getName())
                    .collect(Collectors.toList());

            Citylist.put(contrCode, cityList);
        }

        return Citylist;

        }

    }






