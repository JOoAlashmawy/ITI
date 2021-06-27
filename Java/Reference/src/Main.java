public class Main {
    public static void main(String[] args) {
        String st1 = "Joo";
        String st2 = "Youssef";
        String stringLonger = StringUtils.betterString(st1,st2,(s1,s2) -> s1.length() > s2.length() );
        String stringFirst = StringUtils.betterString(st1,st2,(s1,s2) -> true );
        String stringSec = StringUtils.betterString(st1,st2,(s1,s2) -> false );
        System.out.println(stringLonger);
        System.out.println(stringFirst);
        System.out.println(stringSec);
    }
}
