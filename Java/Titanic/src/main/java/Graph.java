import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Graph {
    public static void graphPassengerAges(List<Passenger> passengerList){

        List<Float> pAges= passengerList.stream().map(Passenger::getAge).limit(8).collect(Collectors.toList());
        List<String> pNames= passengerList.stream().map(Passenger::getName).limit(8).collect(Collectors.toList());

        //Use xChart
        CategoryChart chart = new CategoryChartBuilder().width (1024).height (768).title ("Age Histogram").xAxisTitle("Names").yAxisTitle("Age").build();
        //Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);
        //Seires
        chart.addSeries("Passenger's Ages", pNames, pAges);
        //Show it
        new SwingWrapper(chart).displayChart();
    }

    public static void graphPassengerClass(List<Passenger> passengerList) {

        //filter to get a map of passenger class and total number of passengers in each class
        Map<String, Long> result =
                passengerList.stream().collect(
                        Collectors.groupingBy(Passenger::getPclass, Collectors.counting()));

        //Create Chart
        PieChart chart = new PieChartBuilder().width(800).height(600).title("Passenger Classes").build();
        //Customize Chart
        Color[] sliceColors = new Color[]{new Color(180, 68, 50), new Color(130, 105, 120), new Color(80, 143, 160)};
        chart.getStyler().setSeriesColors(sliceColors);
        // Series
        chart.addSeries("First Class", result.get("1"));
        chart.addSeries("Second Class", result.get("2"));
        chart.addSeries("Third Class", result.get("3"));
        // Show it
        new SwingWrapper(chart).displayChart();
    }

    public static void graphPassengerSurvived(List<Passenger> passengerList)
    {
        Map<String, Long> map =passengerList.stream()
                .collect(Collectors.groupingBy(Passenger::getSurvived, Collectors.counting()));

        PieChart chart = new PieChartBuilder().width (800).height (600).build ();
        //Customize Chart
        Color[] sliceColors= new Color[]{new Color (180, 68, 50), new Color (130, 105, 120)};
        chart.getStyler().setSeriesColors(sliceColors);
        // Series
        chart.addSeries("Survived", map.get("0"));
        chart.addSeries("Not-Survived", map.get("1"));
        // Show it
        new SwingWrapper(chart).displayChart();
    }

    public static void graphPassengerNotSurvivedSex(List<Passenger> passengerList)
    {
        Map<String, Long> map =passengerList.stream()
                .filter(p -> p.getSurvived().equals("0"))
                .collect(Collectors.groupingBy(Passenger::getSex, Collectors.counting()));

        PieChart chart = new PieChartBuilder().width (800).height (600).build();
        //Customize Chart
        Color[] sliceColors= new Color[]{new Color (180, 68, 50), new Color (130, 105, 120)};
        chart.getStyler().setSeriesColors(sliceColors);
        // Series
        chart.addSeries("Dead Males", map.get("male"));
        chart.addSeries("Dead Females", map.get("female"));
        // Show it
        new SwingWrapper(chart).displayChart();
    }
}
