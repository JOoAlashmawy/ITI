import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;
import tech.tablesaw.api.Table;

public class Main {
    public static void main(String[] args) throws IOException {
        
        //The first task
        Table data = new ReadFile().readCsvFile();
        System.out.println(data.summary());
        // or we can use dataFrame too

        //The second task
        //Read File
        List<Passenger> node = new ReadFile().readJsonFile();
//        System.out.println(node);

        //xChart
        Graph.graphPassengerAges(node);
//        Graph.graphPassengerClass(node);
        Graph.graphPassengerSurvived(node);
        Graph.graphPassengerNotSurvivedSex(node);

    }
}
