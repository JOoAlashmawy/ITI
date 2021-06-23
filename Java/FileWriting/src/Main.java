import java.io.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try{
            InputStreamReader r = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(r);
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter("C:\\Users\\Youssef\\Desktop\\Java\\input.txt"));

            String name = "";
            while (!name.equals("stop")){
                try{
                    System.out.println("Enter data: ");
                    name = br.readLine();
                    if (!name.equals("stop")) {
                        bw.write(name + "\n");
                    }

                }catch(IOException ex){
                    System.out.println("An error occurred.");
                    ex.printStackTrace();
//                    logger.getlogger(StopCons.class.getName()).log(level.SEVERE, null, ex);
//                    return;
                }

            }
            br.close();
            bw.close();
        }catch(IOException ex){
            System.out.println("An error occurred.");
            ex.printStackTrace();
//            logger.getlogger(StopCons.class.getName()).log(level.SEVERE, null, ex);
//            return;
        }
    }

}
