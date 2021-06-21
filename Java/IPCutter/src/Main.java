import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String IP;
        Scanner input = new Scanner(System.in);
        IP = input.next();

        for (int i : IP_Cutter.Cutter(IP)) {
            System.out.println(i);
        }
    }
}
