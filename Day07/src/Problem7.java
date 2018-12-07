import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Queue;

public class Problem7 {
    static int[][] adj = new int[26][27]; //adjacency matrix representing graph, last column of each array
                                        // represents count of predecessors ( -1 if processed)
    static Queue<Integer> q = new PriorityQueue<>(); //processed in alphabetical order
    static int[] workers = new int[5];
    static int[] letters = new int[5]; //letters that they are working on
    int time = 0;
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        Pattern p = Pattern.compile("Step (\\w) must be finished before step (\\w) can begin.");
        Matcher m;
        while(in.hasNextLine()) { //read in input
            String line = in.nextLine();
            m = p.matcher(line);
            if(m.find()) {
                char from = m.group(1).charAt(0);
                char to = m.group(2).charAt(0);
                adj[to - 'A'][from - 'A'] = 1; //adding edges to graph [to][from] = 1 if there is an edge to --> from
                adj[to - 'A'][26] += 1;
            }
        }
        in.close();
        String res = bfs();
        System.out.println(res); //part 1

    }
    //breadth first search - sort of - alphabetical order prioritized
    public static String bfs() {
        for (int i = 0; i < 26; i += 1) { //search for steps with no prereqs, initializes queue
            if (adj[i][26] == 0) {
                q.add(i);
            }
        }
        String res = "";
        while (!q.isEmpty()) { //performs search
            int a = q.remove();
            if (adj[a][26] == 0) { // has no more prerequisites
                res += (char) (a + 'A');
                addSuccessors(a);
            }
        }
        return res;
    }
    private static void addSuccessors(int a) { //adds successors and removes the from to successors
        for (int i = 0; i < 26; i += 1) {
            if (adj[i][a] == 1) {
                q.add(i);
                adj[i][a] = 0;
                adj[i][26] -= 1;
            }
        }
        adj[a][26] = -1;
    }
}
