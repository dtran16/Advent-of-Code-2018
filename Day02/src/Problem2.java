import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Problem2 {
    public static void main (String[] args) throws IOException {
        //part 1
        Scanner in = new Scanner(new File("day2input.txt"));
        int[] counter = new int[]{0, 0}; //counter of frequency of id having exactly two/three of same letter
        while (in.hasNext()) {
            String id = in.next();
            int[] freq = count(id);
            counter[0] += freq[0];
            counter[1] += freq[1];
        }
        in.close();
        System.out.println(counter[0] * counter[1]);
        //part 2
        in = new Scanner(new File("day2input.txt"));
        ArrayList<String> ids = new ArrayList<String>();
        while (in.hasNext()) {
            String id = in.next();
            ids.add(id);
        }
        for (int i = 0; i < ids.size(); i += 1) {
            String str = ids.get(i);
            for (int j = i + 1; j < ids.size(); j += 1) {
                String cmpr = ids.get(j);
                int count = 0;
                for (int z = 0; z < str.length(); z += 1) {
                    if (str.charAt(z) == cmpr.charAt(z)) {
                        count += 1;
                    }
                }
                if (count == cmpr.length() - 1) {
                    System.out.println(str);
                    System.out.println(cmpr);
                }
            }
        }
    }
    public static int[] count(String id) {
        int[] letters = new int[26]; //array of frequency of letter appearances
        for (int i = 0; i < 26; i += 1) {
            letters[i] = 0;
        }
        for (int j = 0; j < id.length(); j += 1) {
            letters[(id.charAt(j) - 'a')] += 1;
        }
        int[] freq = new int[]{0, 0};
        for (int a: letters) {
            if (a == 2) {
                freq[0] = 1;
            } else if (a == 3) {
                freq[1] = 1;
            }
        }
        return freq; // array [x, y], x = 1 if a letter appears twice, y = 1 if a letter appears three times
    }
}
