import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

public class Problem5 {
    public static void main (String[] args) throws IOException{
        Scanner in = new Scanner(new File("input.txt"));
        String poly = in.nextLine();
        System.out.println(findLength(poly)); //part 1
        int min = Integer.MAX_VALUE;
        for (int i = 65; i <= 90; i += 1) { //used ascii values
            String exp = "";
            for (int j = 0; j < poly.length();) {
                char t = poly.charAt(j);
                if (!(t == i || t == (i + 32))) {
                    exp += t;
                }
                j += 1;
            }
            int ln = findLength(exp);
            if (ln < min) {
                min = ln;
            }
        }
        System.out.println(min); //part 2 answer
    }
    public static int findLength (String poly) {
        boolean normal = false;
        while (!normal) {
            normal = true;
            for (int i = 1; i < poly.length() - 1;) {
                boolean diff1 = Math.abs(poly.charAt(i - 1) - poly.charAt(i)) == 32;
                boolean diff2 = Math.abs(poly.charAt(i + 1) - poly.charAt(i)) == 32; // if same letter, then uppercase - lowercase = 32
                if (diff1) {
                    poly = poly.substring(0, i - 1) + poly.substring(i + 1);
                    normal = false;
                } else if (diff2){
                    poly = poly.substring(0, i) + poly.substring(i + 2);
                    normal = false;
                } else {
                    i += 1;
                }
            }
        }
        return poly.length();
    }
}
