import java.util.Scanner;

public class IP_Cutter {
    public static int[] Cutter(String Line){
        String[] ip = Line.split("\\.");
        int[] numbers = new int[ip.length];
        for(int i = 0;i < ip.length;i++)
        {
            numbers[i] = Integer.parseInt(ip[i]);
        }
        return numbers;

    }
//    public static void main(String[] args){
//        String IP;
//        Scanner input = new Scanner(System.in);
//        IP = input.next();
//
//        for (int i : Cutter(IP)){
//            System.out.println(i);
//        }
//    }

}
