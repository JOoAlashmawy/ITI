import java.util.function.BiPredicate;

public class StringUtils {
    public static String betterString(String s1, String s2, BiPredicate<String,String> p){
        if(p.test(s1, s2 )){
            return s1;
        }else{
            return s2;
        }
    }
}
