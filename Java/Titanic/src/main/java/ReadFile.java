import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;

//https://www.baeldung.com/jackson-object-mapper-tutorial

public class ReadFile {  // The file is JSON

    public static List<Passenger> readJsonFile() throws IOException {

        List<Passenger> allPassenger = new ArrayList<>();

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            InputStream input = new FileInputStream("Data_to_use\\titanic.json");

            allPassenger = objectMapper.readValue(input, new TypeReference<List<Passenger>>() {});



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return allPassenger;
    }
    
    public static Table  readCsvFile() throws IOException {
        Table titanicData;
        titanicData = Table.read().csv("Data_to_use\\titanic.csv");
        return titanicData;
    }
}
