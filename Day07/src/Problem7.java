import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Queue;
import java.util.ArrayList;

public class Problem7 {
    static int[][] adj = new int[26][27]; //adjacency matrix representing graph, last column of each array
                                        // represents count of predecessors (-1 if processed)
    static Queue<Integer> q = new PriorityQueue<>(); //processed in alphabetical order
    static ArrayList<Task> tasks = new ArrayList<>();
    static int time = 0;
    static String res = "";
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
        System.out.println(time);
    }
    //breadth first search - sort of - alphabetical order prioritized
    public static String bfs() {
        for (int i = 0; i < 26; i += 1) { //search for steps with no prereqs, initializes queue
            if (adj[i][26] == 0) {
                q.add(i);
            }
        }
        while (!q.isEmpty()) { //performs search
            int a = q.remove();
            if (adj[a][26] == 0) { // has no more prerequisites
                //begin part 2
                if (tasks.size() < 5) {
                    tasks.add(new Task((char) (a + 'A'), 61 + a));
                } else {
                    if(tasks.size() == 5) {
                        dotasks();
                    }
                    tasks.add(new Task((char) (a + 'A'), 61 + a));
                }
                //addSuccessors(a); (removed for part 2), used for part 1) (also added to res here for part 1)
            }
            if (q.isEmpty() && tasks.size() > 0) {
                dotasks();
                //System.out.println("run");
            }
            //end part 2
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
    private static void dotasks() { //complete a time step (part 2)
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < tasks.size(); i += 1) {
            if (tasks.get(i).time < min) {
                min = tasks.get(i).time;
                index = i;
            }
        }
        for (Task t : tasks) {
            t.subtract(min);
        }
        time += min;
        res += (char) tasks.get(index).letter; //moved here from part 1
        addSuccessors(tasks.remove(index).letter - 'A'); //moved here from part 1
    }
    private static class Task {
        char letter;
        int time;
        Task(char l, int t) {
            letter = l;
            time = t;
        }
        void subtract(int a) {
            time -= a;
        }
    }
}
