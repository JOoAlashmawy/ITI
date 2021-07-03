import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        //Read File
        List<Passenger> node = new ReadFile().readJsonFile();
        System.out.println(node);

        //xChart
        Graph.graphPassengerAges(node);
//        Graph.graphPassengerClass(node);
        Graph.graphPassengerSurvived(node);
        Graph.graphPassengerNotSurvivedSex(node);

    }
}
