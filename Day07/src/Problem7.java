import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Queue;

public class Problem7 {
    static int[][] adj = new int[26][27]; //adjacency matrix representing graph, last column of each array
                                        // represents whether it has a predecessor (1 if does, 0 otherwise, 2 if processed)
    static Queue<Integer> q;
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        Pattern p = Pattern.compile("Step (\\w) must be finished before step (\\w) can begin.");
        Matcher m;
        //read in input
        while(in.hasNextLine()) {
            String line = in.nextLine();
            m = p.matcher(line);
            if(m.find()) {
                char from = m.group(1).charAt(0);
                char to = m.group(2).charAt(0);
                adj[to - 'A'][from - 'A'] = 1; //adding edges to graph [to][from] = 1 if there is an edge to --> from
                adj[to - 'A'][26] = 1;
            }
        }
        in.close();
        q = new PriorityQueue<>(); //processed in alphabetical order
        for (int i = 0; i < 26; i += 1) { //search for steps with no prereqs
            if (adj[i][26] == 0) {
                q.add(i);
                adj[i][26] = 2;
            }
        }
        String res = bfs();
        System.out.println(res); //part 1
    }
    //breadth first search
    public static String bfs() {
        String res = "";
        while (!q.isEmpty()) {
            int a = q.remove();
            if (avail(a)) {
                res += (char) (a + 'A');
                addSuccessors(a);
            }
        }
        return res;
    }
    private static void addSuccessors(int a) { //adds successors and removes the edges (removes predecessors)
        for (int i = 0; i < 26; i += 1) {
            if (adj[i][a] == 1) {
                q.add(i);
                adj[i][a] = 0;
            }
        }
        adj[a][26] = 0;
    }
    private static boolean avail(int a) { //checks if this step is ready to be done (has no more predecessors
        if (adj[a][26] == 2) {
            return true;
        } else if (adj[a][26] == 0) {
            return false;
        }
        for (int i = 0; i < 26; i += 1) {
            if (adj[a][i] == 1) {
                return false;
            }
        }
        return true;
    }

}
