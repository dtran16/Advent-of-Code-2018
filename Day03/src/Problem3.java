import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class Problem3 {
    static int[][] fabric = new int[1000][1000]; //large piece of fabric
    static boolean isClean = false; //for part 2
    static int count = 0;
    public static void main (String[] args) throws IOException{
        //part 1
        Scanner in = new Scanner(new File("input.txt"));
        while (in.hasNext()) {
            in.next();
            in.next();
            String distances = in.next();
            String dim = in.next();
            int div1 = distances.indexOf(',');
            int div2 = dim.indexOf('x');
            int fromLeft = Integer.parseInt(distances.substring(0, div1));
            int fromTop = Integer.parseInt(distances.substring(div1 + 1, distances.length() - 1));
            int width = Integer.parseInt(dim.substring(0, div2));
            int height = Integer.parseInt(dim.substring(div2 + 1));
            addFabric(fromLeft, fromTop, width, height);
        }
        System.out.println(count);
        in.close();
        //part 2
        in = new Scanner(new File("input.txt"));
        while (in.hasNext()) {
            String id = in.next();
            in.next();
            String distances = in.next();
            String dim = in.next();
            int div1 = distances.indexOf(','); //parsing string into int and removing dividers
            int div2 = dim.indexOf('x');
            int fromLeft = Integer.parseInt(distances.substring(0, div1));
            int fromTop = Integer.parseInt(distances.substring(div1 + 1, distances.length() - 1));
            int width = Integer.parseInt(dim.substring(0, div2));
            int height = Integer.parseInt(dim.substring(div2 + 1));
            checkFabric(fromLeft, fromTop, width, height);
            if (isClean) {
                System.out.println(id);
            }
        }
        in.close();
    }
    public static void addFabric (int left, int top, int width, int height) {
        for (int i = top; i < top + height; i += 1) {
            for (int j = left; j < left + width; j += 1) {
                fabric[i][j] += 1;
                if (fabric[i][j] == 2) {
                    count += 1;
                }
            }
        }
    }
    public static void checkFabric (int left, int top, int width, int height) {
        boolean clean = true;
        for (int i = top; i < top + height; i += 1) {
            for (int j = left; j < left + width; j += 1) {
                if (fabric[i][j] > 1) {
                    clean = false;
                }
            }
        }
        isClean = clean;
    }
}
