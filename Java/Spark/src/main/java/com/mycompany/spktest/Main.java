package com.mycompany.spktest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        List<Map.Entry> sortedTags = ReadFile.fileTagsReader("src/main/resources/data/USvideos.csv");
        //show Tags data
        for (Map.Entry<String, Long> entry : sortedTags) {
            System.out.println (entry.getKey () + " : " + entry.getValue ());
        }

//        List<Map.Entry> sortedTitle = ReadFile.fileTitleReader("src/main/resources/data/USvideos.csv");
//        //show Title data
//        for (Map.Entry<String, Long> entry : sortedTitle) {
//            System.out.println (entry.getKey () + " : " + entry.getValue ());
//        }
    }

}
