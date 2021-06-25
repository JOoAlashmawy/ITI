
public class SimpleRunThread implements Runnable {
    String name;
    public SimpleRunThread(String name){
        this.name = name;
    }
    public void run(){
        for(int i=0; i<10; i++){
            System.out.println(i+ " " + name);
            try{
                Thread.sleep((int)(Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Done! "+ name);
    }
}
