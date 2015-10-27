import sun.nio.cs.StreamDecoder;

import java.io.*;
import java.util.*;

/**
 * Created by igoryan on 27.10.15.
 */
public class FileIO {
    public String[] readLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        return fromReader(bufferedReader);
    }

    public String[] readLines(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        return fromReader(bufferedReader);
    }

    public String[] fromReader(BufferedReader bufferedReader) {
        List<String> lines = new ArrayList<String>();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines.toArray(new String[lines.size()]);
    }

    public void write(String filename, boolean flag, String[] array) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(filename);
        if (!flag) {
            for (int i = 0; i < array.length; i++) {
                out.println(array[i]);
            }
        } else {
            Set<String> mySet = new HashSet<String>();
            Collections.addAll(mySet, array);
            for (String string : mySet) {
                out.println(string);
            }
        }
    }

    public void write(boolean flag, String[] array) {
        if (!flag) {
            for (int i = 0; i < array.length; i++) {
                System.out.println(array[i]);
            }
        } else {
            Set<String> mySet = new HashSet<String>();
            Collections.addAll(mySet, array);
            for (String string : mySet) {
                System.out.println(string);
            }
        }
    }
}
