import java.util.Comparator;

/**
 * Created by igoryan on 22.10.15.
 */
public class Sort {
    protected Comparator<String> cmpAlfavit = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            int o2Length = o2.length();
            int o1Length = o1.length();
            int length = (o2Length > o1Length) ? o1Length : o2Length;
            int res = 0;
            for (int i = 0; i < length; i++) {
                res = o2.charAt(i) - o1.charAt(i);
                if (res!= 0) {
                    return res;
                }
            }
            return res;
        }
    };

    protected Comparator<String> cmpI = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            String second = o2.toLowerCase();
            String first = o1.toLowerCase();
            return cmpAlfavit.compare(first, second);
        }
    };

    public int compare(String str1, String str2) {
        return str2.length() -  str1.length();
    }

    public void swap(String[] str, int i , int j) {
        String temp = str[i];
        str[i] = str[j];
        str[j] = temp;
    }
}
