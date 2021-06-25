
public class Main {

    public static void main(String[] args) {
        new SimpleThread("Thread Object 1").start();
        new SimpleThread("Thread Object 2").start();

        new Thread(new SimpleRunThread("runnable object")).start();

    }
}