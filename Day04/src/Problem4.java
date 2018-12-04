import java.util.*;
import java.io.File;
import java.io.IOException;

public class Problem4 {
    static ArrayList <Timestamp> stamps = new ArrayList<> (); //list of time stamps
    static HashMap<Integer, int[]> counter = new HashMap<>(); //maps ids to arrays that count the number of times asleep during a given minute
    public static void main(String[] args) throws IOException{
        readIntoStamp(); //read data in
        Collections.sort(stamps);
        int maxId = getMaxId();
        int[] maxArray = counter.get(maxId);
        int maxMinute = 0;
        int maxValue = Integer.MIN_VALUE;
        for (int a = 0; a < maxArray.length - 1; a += 1) {
            if (maxArray[a] > maxValue) {
                maxMinute = a;
                maxValue = maxArray[a];
            }
        }
        System.out.println(maxMinute * maxId); //answer part 1
        //part 2
        int maxVal = Integer.MIN_VALUE;
        int maxIndex = 0;
        maxId = 0;
        Iterator<Integer> iter = counter.keySet().iterator();
        while (iter.hasNext()) { //for each id, compares each of the maximum minutes
            int currId = iter.next();
            int count[] = getMaxMinute(counter.get(currId));
            if (count[1] > maxVal) {
                maxIndex = count[0];
                maxVal = count[1];
                maxId = currId;
            }
        }
        System.out.println(maxIndex * maxId); //answer part 2
    }

    /**
     * Part 2
     */
    public static int[] getMaxMinute(int[] arr) {
        int maxValue = Integer.MIN_VALUE;
        int maxIndex = 0;
        for (int i = 0; i < arr.length - 1; i += 1) {
            if (arr[i] > maxValue) {
                maxValue = arr[i];
                maxIndex = i;
            }
        }
        return new int[] {maxIndex, maxValue}; //returns the maximum value and the minute it occurred as [minute, value]
    }

    /**
     * Part 1
     */
    public static int getMaxId() {
        int max = Integer.MIN_VALUE;
        int maxId = 0;
        for (int i = 0; i < stamps.size(); i += 1) {
            int id = stamps.get(i).type;
            if (!counter.containsKey(id)) { //if this id has not been tracked yet, add
                counter.put(id, new int[61]); //initialize array to track number of times asleep during a given minute
            }                                   // minute 60 is used to track the total time asleep for given guard
            int[] count = counter.get(id);
            while (i + 1 < stamps.size() && stamps.get(i+1).type < 0) {
                i += 2;
                int sleep = stamps.get(i - 1).min; //falling asleep and waking up come in pairs
                int wake = stamps.get(i).min;
                count[60] += (wake - sleep);
                for (int a = sleep; a < wake; a += 1) { //update each minute
                    count[a] += 1;
                }
            }

            int total = count[60];
            if (total > max) { //update maximum
                maxId = id;
                max = total;
            }
        }
        return maxId;
    }

    /**
     * Reads all the data in
     */
    public static void readIntoStamp() throws IOException{ //parsing probably could've been done with regex expressions/patterns
        Scanner in = new Scanner(new File("input.txt"));  //     but was not familiar with them
        while (in.hasNext()) {
            String stamp = in.nextLine();
            int month = Integer.parseInt(stamp.substring(6, 8));
            int day = Integer.parseInt(stamp.substring(9,11));
            int hr = Integer.parseInt(stamp.substring(12, 14));
            int min = Integer.parseInt(stamp.substring(15, 17));
            int tp;
            String first = stamp.substring(19, 24);
            if (first.equals("wakes")) {
                tp = -2;
            } else if (first.equals("falls")) {
                tp = -1;
            } else {
                int index = 26;
                String id = "";
                while (stamp.charAt(index) != ' ') {
                    id += "" + stamp.charAt(index);
                    index += 1;
                }
                tp = Integer.parseInt(id);
            }
            stamps.add(new Timestamp(month, day, hr, min, tp));
        }
    }

    /**
     * Subclass to store timestamps and their elements
     */
    public static class Timestamp implements Comparable <Timestamp>{
        public int month;
        public int day;
        public int type; // id for guard message, -1 for fall asleep, -2 for wake up
        public int hr;
        public int min;

        public Timestamp (int m, int d, int h, int mn, int tp) {
            month = m;
            day = d;
            type = tp;
            hr = h;
            min = mn;
        }
        public int compareTo (Timestamp t) { // needed for sort
            if (month == t.month) {
                if (day == t.day) {
                    if (hr == t.hr) {
                        return min - t.min;
                    }
                    return hr - t.hr;
                }
                return day - t.day;
            }
            return month - t.month;
        }
    }
}
