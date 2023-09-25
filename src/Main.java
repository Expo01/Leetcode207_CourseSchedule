import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
    }
}


class Solution {
    public boolean canFinish(int courses, int[][] pre) {
        if (pre == null || pre.length == 0) return true;
        int[] inDegree = new int[courses]; // indegree will have indexes associated with each course and the value will be the number of prereqs that course has
        Map<Integer, List<Integer>> map = new HashMap<>(); // map will be used to store prereqs as a key and have a value of List which contains all the courses it is a prereq for


        for (int i = 0; i < pre.length; i++) {
            inDegree[pre[i][0]]++; // inner array index 0 is the desired class. each time it occurs, it means we are IDing a new prereq at index [i][1]
            // it seems we can assume that courses are numbered sequentially. for example, if pre.length() = 4,  we will not have courses 1,5,99,1000. But rather 0,1,2,3
            // so everything should index fine and if no a course never mentioned such as [1,4], [2,4], [3,1], 3,2] where 4 is never at index 0 in the nested array, its value at index 4 will be 0

            if (!map.containsKey(pre[i][1])) { // if prereq not in map
                List<Integer> cur = new ArrayList<>(); // create an ArrayList to hold courses it is a prereq for
                cur.add(pre[i][0]); // add the originally desired course
                map.put(pre[i][1], cur); // put the prereq key into the map with cur as the value
            } else {
                map.get(pre[i][1]).add(pre[i][0]); // same except if key already exists such as [1,3], [2,3] where 3 is prereq for both 1 and 2. map will be like <1,[2,3]>
            }
        }

        Queue<Integer> queue = new LinkedList<>(); // create a queue of indexes with indegree of 0,
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) queue.add(i); //  which represents classes that have no prereqs and are ready to take
        }

        while (!queue.isEmpty()) { // while there are still classes that can be taken
            int cur = queue.poll(); // retrieve a class from the list
            List<Integer> list = map.get(cur); // create a temp list which will contain the map value for the prereq key. This is again the list of courses that course 'cur' is a prereq for
            for (int x = 0; list != null && x < list.size(); x++) { // looping the list of desired courses
                inDegree[list.get(x)]--; // decrement the value at the course index -1 since cur is a prereq for all those
                if (inDegree[list.get(x)] == 0) queue.add(list.get(x)); // if the number of prereqs for the course has decreased to 0, add it to the queue of courses that can be taken
            }
        }
        for (int i : inDegree) { // at this point, all courses that can be taken have been taken.
            if (i != 0) return false; // loop through and test for any remaining indegrees (outstanding prereqs to complete course set)
        }
        return true;
    }
}

/*

    Map, want to add prereq as a key and create an arrayList which contains the intended course. the arrayList becomes the value. if the prereq already exists as a key, then rerieve key and add the
        new intended course. such as if we have [1,3], [2,3]. 3 is added as key with arrayList of [1]. 3 already exists for other so just revise so 3 key has [1,2]. stores all the courses for which
        it is a prereq.

        * at this point, all desired classes have number of their prereqs listed and all prereqs have an arrayList showing which classes it is a prereq for


 */

// 37 test cases of 52 passed
//class Solution {
//    public boolean canFinish(int numCourses, int[][] prerequisites) {
//        if(numCourses < prerequisites.length){
//            return false;
//        }
//        HashMap<Integer,Integer> cMap = new HashMap<>();
//
//        for(int[] c: prerequisites){
//            if(!cMap.containsKey(c[0])){
//                cMap.put(c[0],c[1]);
//            }
//            if (cMap.containsKey(c[1]) && cMap.get(c[1]) == c[0]){
//                return false;
//            }
//        }
//        return true;
//    }
//}

// 39/52
//class Solution {
//    public boolean canFinish(int numCourses, int[][] prerequisites) {
//        if(numCourses < prerequisites.length){
//            return false;
//        }
//        HashMap<Integer,Integer> cMap = new HashMap<>();
//
//        for(int[] c: prerequisites){
//            if (cMap.containsKey(c[0]) || cMap.containsKey(c[1])){
//                return false;
//            } else {
//                cMap.put(c[0],c[1]);
//            }
//        }
//        return true;
//    }
//}