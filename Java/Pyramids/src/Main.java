import java.util.*;

public class Main {
    public static void main(String[] args) {
        PyramidDAO pyramids = new PyramidDAO();
        List<Pyramid>  pyramidsList = pyramids.readPyramidsFromCSV("E:\\AI_ITI\\Java\\Day3\\pyramids.csv");

        pyramids.sortByHeight(pyramidsList); //the sort
        for(Pyramid p : pyramidsList){
            System.out.println("Pharaoh: " + p.getPharaoh() + " Modern_name: " + p.getModern_name() + " Site: " + p.getSite() + " Height: " + p.getHeight());
//            System.out.println("Modern_name: " + p.getModern_name());
//            System.out.println("Site: " + p.getSite());
//            System.out.println("Height: " + p.getHeight());
        }

        Map<String, Integer> map = pyramids.mapSiteLocations(pyramidsList); //the map
        System.out.println(map);

    }
}
