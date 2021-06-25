import java.io.*;
import java.io.IOException;
import java.util.*;

public class PyramidDAO {
    List<Pyramid>  pyramids = new ArrayList<Pyramid>() ;
    public List<Pyramid> readPyramidsFromCSV(String fileName) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
//        if (line != null){
//            System.out.println(line);
//        }
            line = br.readLine();
            while(line != null){

                String[] attributes = line.split(",");
                Pyramid p1 = createPyramid(attributes );
                pyramids.add(p1) ;
                line = br.readLine();
            }
            br.close();
        }catch(IOException ex){
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
        return pyramids;
    }

    public Pyramid createPyramid(String[] metadata){
        String height;
        if (metadata[7].length() == 0){
            height = "0";
        }else{
            height = metadata[7];
        }
        double doubleHeight = Double.parseDouble(height);
//        double str1 = Double.parseDouble(str);
        return new Pyramid(metadata[0],metadata[2],metadata[4],doubleHeight);
    }


    //  Build a sorting method

    public void sortByHeight(List pyramidsList)
    {
        Collections.sort(pyramidsList, new Compare());
    }


    //  Build a map method

    public  Map<String, Integer>  mapSiteLocations (List<Pyramid> listOfPyramids)
    {
        Map<String, Integer> siteCounts = new HashMap<>();

        for (Pyramid p: listOfPyramids)
        {
            String site= p.getSite();
            Integer count = siteCounts.get(site);

            if (count == null)
                count = 1;
            else
                count++;

            siteCounts.put(site, count);
        }
        return siteCounts;
    }
}
