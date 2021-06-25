import java.util.Collections;
import java.util.Comparator;

public class Compare implements Comparator<Pyramid> {
    // sort the list of pyramids you have by pyramid’s height.
    @Override
    public int compare(Pyramid o1, Pyramid o2) {
        return Double.compare(o1.getHeight(), o2.getHeight());
    }
}
