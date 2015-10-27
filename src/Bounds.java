/**
 * Created by igoryan on 23.10.15.
 */
public class Bounds {
    private int begin = 0;
    private int end = 0;
    private int index = 0;

    Bounds(int begin, int end) {
        this.begin = begin;
        this.end = end;
        index = begin;
    }

    public int getBegin() {
        return begin;
    }

    public int getIndex() {
        return index;
    }

    public void incIndex() {
        index++;
    }

    public void decIndex() {
        index--;
    }

    public int getEnd() {
        return end;
    }

    public void setIndex(int index) {
        this.index = -1;
    }

    boolean inside(int index) {
        if (begin <= index && index <= end) {
            return true;
        }
        return false;
    }

    boolean inside() {
        return inside(index);
    }

    public static Bounds getBound(Bounds[] boundses, int index) {
        for (Bounds bounds : boundses) {
            if (bounds.inside(index))
                return bounds;
        }
        return null;
    }
}
