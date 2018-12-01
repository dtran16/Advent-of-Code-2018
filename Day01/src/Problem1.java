import java.util.Scanner;
import java.io.File;
import java.util.TreeSet;
import java.io.IOException;

public class Problem1 {
    public static int count; //counting current frequency
    public static void main (String[] args) throws Exception{
        count = 0;
        System.out.println(find());
        count = 0;
        TreeSet<Integer> freq = new TreeSet<Integer>(); //list containing frequencies seen before. Tree Set used for O(logN) search time
        freq.add(0);
        while (!firstFrequency(freq)) {
            System.out.println("Next Loop");
        }
    }
    public static int find () throws IOException{
        Scanner in = new Scanner(new File("Day01Input.txt"));
        while (in.hasNext()) {
            count += in.nextInt();
        }
        return count;
    }
    public static boolean firstFrequency (TreeSet<Integer> freq) throws IOException{
        Scanner in = new Scanner(new File("Day01Input.txt"));
        while (in.hasNext()) {
            count += in.nextInt();
            if (freq.contains(count)) {
                System.out.println(count);
                in.close();
                return true;
            }
            freq.add(count);
        }
        in.close();
        return false;
    }
}

