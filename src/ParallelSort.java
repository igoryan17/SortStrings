import java.io.*;
import java.util.*;

/**
 * Created by igoryan on 22.10.15.
 */
public class ParallelSort extends Sort {
    private String[] array = null;
    private int threadsCount = 0;
    private Comparator<String> cmp;
    private int size = 0;

    ParallelSort(String[] array, int threadsCount, String flagCmp) {
        this.array = array;
        this.threadsCount = (threadsCount == 0) ? 4 : threadsCount;
        size = array.length;
        if (flagCmp == null) {
            cmp = cmpAlfavit;
        }
        if (flagCmp.equals("-i")) {
            cmp = cmpAlfavit;
        }
    }

    class MyThread extends Thread {
        private int begin = 0;
        private int end = 0;

        MyThread(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public void run() {
            Arrays.sort(array, begin, end + 1, cmp);
        }
    }

    public String[] sort() {
        List<MyThread> threads = new LinkedList<>();
        Bounds[] boundses = new Bounds[threadsCount];
        if (size <= threadsCount) {
            new MyThread(0, size);
        } else {
            int step = size / threadsCount;
            for (int i = 0; i < threadsCount; i++) {
                //calculate bound
                int begin = i * step;
                int end = (i == threadsCount - 1) ? size - 1 : (i + 1) * step - 1;
                boundses[i] = new Bounds(begin, end);
                MyThread thread = new MyThread(begin, end);
                thread.run();
                threads.add(thread);
            }
        }

        for (Thread i : threads) {
            try {
                i.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return merge(array, boundses);
    }

    private String[] merge(String[] array, Bounds[] boundses) {
        int numberBounds = 0;
        /*while (inside(boundses)) {
            int min = getMin(array, boundses);
            if (min >= 0) {
                if (!boundses[numberBounds].inside()) {
                    numberBounds++;
                }
                if (numberBounds >= boundses.length)
                    return;
                swap(array, min, boundses[numberBounds].getIndex());
                if (boundses[numberBounds].getIndex() >= boundses[numberBounds].getEnd()) {
                    boundses[numberBounds].setIndex(-1);
                }
                else {
                    boundses[numberBounds].incIndex();
                }
            } else {
                return;
            }

        }*/
        int length = array.length;
        String[] sortedArray = new String[length];
        int index = 0;
        for (int i = 0; i < length; i++) {
            index = getMin(array, boundses);
            sortedArray[i] = array[index];
        }
        return sortedArray;
    }

    private int getMin(final String[] array, Bounds[] boundses) {
        int min = -1;
        int i = 0;
        int boundNumber = 0;
        while ((min < 0) && (i < boundses.length)) {
            int index = boundses[i].getIndex();
            if (index >= 0) {
                min = index;
                boundNumber = i;
            }
            i++;
        }
        for (; i < boundses.length; i++) {
            int index = boundses[i].getIndex();
            if (index >= 0) {
                if (cmp.compare(array[index], array[min]) <= 0) {
                    min = index;
                    boundNumber = i;
                }
            }
        }
        Bounds minBound = boundses[boundNumber];
        minBound.incIndex();
        if (!minBound.inside()) {
            minBound.setIndex(-1);
        }
        return min;
    }

    public static void main(String[] arg) {
        String register = null;
        String outFile = null;
        String inFile = null;
        boolean unique = false;
        boolean reg = false;
        boolean threads = false;
        boolean output = false;
        String[] array = null;
        int threadsCount = 0;
        for (int i = 0; i < arg.length; i++) {
            boolean flag = true;
            if (!reg) {
                if (arg[i].equals("-i")) {
                    reg = true;
                    register = arg[i];
                    flag = false;
                }
            }
            if (!unique) {
                if (arg[i].equals("-u")) {
                    unique = true;
                    flag = false;
                }
            }
            if (!threads) {
                if (arg[i].equals("-t")) {
                    threadsCount = Integer.parseInt(arg[i + 1]);
                    threads = true;
                    flag = false;
                }
            }
            if (!output) {
                if (arg[i].equals("-o")) {
                    outFile = arg[i + 1];
                    output = true;
                    flag = false;
                }
            }
            if (flag) {
                inFile = arg[i];
            }
        }
        FileIO io = new FileIO();
        if (inFile!= null) {
            try {
                array = io.readLines(inFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            array = io.readLines(System.in);
        }
        ParallelSort parallelSort = new ParallelSort(array, threadsCount, register);
        array = parallelSort.sort();
        if (outFile!= null) {
            try {
                io.write(outFile, unique, array);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            io.write(unique, array);
        }
    }
}
