import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class HashMapQuestions {

    // b <============128. Longest Consecutive Sequence ===========>
    // https://leetcode.com/problems/longest-consecutive-sequence/description/

    // # Simple kaam kiya hai.
    // # Pehle sare elements ko dala set mai.

    // Ab koi element hai uska mai prev aur next set kiya.
    // Maine while loop mai dekha ki mai prev -- kab tak present hai hashset mai.
    // same maine next ke liye kiya.

    // At the end, mujhe mera length mil jayegi.

    // # Hum set se remove isiliye kar rahe hain, since isse searching improve hogi
    // # hashset mai.

    // ! Important Point

    // for(int ele : set) == > We cannot use this statement when we have to remove
    // element from hashSet int the same loop.
    // Doing this gives concurrent modification exception.

    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();

        for (int ele : nums)
            set.add(ele);

        int max = 0;
        for (int ele : nums) {
            if (set.contains(ele)) {
                set.remove(ele);
                int prev = ele - 1, next = ele + 1;
                while (set.contains(prev))
                    set.remove(prev--);

                while (set.contains(next))
                    set.remove(next++);

                max = Math.max(max, next - prev - 1);
            }
        }

        return max;
    }

    // b <========= 781. Rabbits in Forest ===========>
    // https://leetcode.com/problems/rabbits-in-forest/description/

    // [10,10,10]

    // For the above test case, there can be total 11 people that can say that they
    // have another 10 that have the same colour.

    // # Array method will be the fastest since array is the fastest DS.

    public int numRabbits(int[] answers) {
        int[] ans = new int[10001];
        int minRabbits = 0;
        for (int ele : answers) {
            if (ans[ele] == 0) // If first occurance, then there will be minimum that ele + 1 amount of rabbit.
                               // +1 for mine.
                minRabbits += ele + 1;
            ans[ele]++;

            if (ans[ele] == ele + 1) { // If the total ele frequency has reached the ele + 1, then the all the people
                                       // have been found. If now someone claims to have same ele value, then it will
                                       // definitely be of another color.
                ans[ele] = 0;
            }
        }
        return minRabbits;
    }

    public int numRabbits_(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = arr.length, ans = 0;

        for (int ele : arr) {

            if (!map.containsKey(ele)) {
                ans += 1 + ele;
                map.put(ele, 1);
            } else {
                map.put(ele, map.get(ele) + 1);
            }

            if (map.get(ele) == ele + 1) {
                map.remove(ele);
            }
        }

        return ans;
    }

    // One More method

    public int numRabbits__(int[] answers) {

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int ele : answers) {
            map.put(ele, map.getOrDefault(ele, 0) + 1);
        }

        int minRabbits = 0;
        for (int key : map.keySet()) {
            int freq = map.get(key);
            int nPeople = key + 1;

            int remGroup = freq % nPeople;

            if (remGroup == 0)
                minRabbits += freq;
            else
                minRabbits += ((freq / nPeople) + 1) * nPeople;
        }

        return minRabbits;
    }

    // b <=======1218. Longest Arithmetic Subsequence of Given Difference ======>
    // https://leetcode.com/problems/longest-arithmetic-subsequence-of-given-difference/description/

    // Maine har index ko bola ki tu apne se aage wale jitne hain, unse puch ki
    // tumse start hone wala max subsequence kaunsa hai. To mujse start hone wala jo
    // tum sab mai se max aayega, usme +1 kardenge.

    // ! Recursion :

    public int longestSubsequence_(int[] arr, int difference, int idx) {
        int n = arr.length;
        int max = 0;
        for (int i = idx + 1; i < n; i++) {
            int currVal = arr[idx];
            int nextVal = arr[i];

            if (nextVal - currVal == difference)
                max = Math.max(max, longestSubsequence_(arr, difference, i));
        }

        return max + 1;
    }

    public int longestSubsequence(int[] arr, int difference) {
        int max = 0;
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, longestSubsequence_(arr, difference, i));
        }

        return max;
    }

    // Memoisation :

    public int longestSubsequence_(int[] arr, int difference, int idx, int[] dp) {
        if (dp[idx] != 0)
            return dp[idx];

        int n = arr.length;
        int max = 0;
        for (int i = idx + 1; i < n; i++) {
            int currVal = arr[idx];
            int nextVal = arr[i];

            if (nextVal - currVal == difference)
                max = Math.max(max, longestSubsequence_(arr, difference, i));
        }

        return dp[idx] = max + 1;
    }

    public int longestSubsequence_(int[] arr, int difference) {
        int max = 0;
        int n = arr.length;
        int[] dp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            max = Math.max(max, longestSubsequence_(arr, difference, i, dp));
        }
        return max;
    }

    // ! All the above method will give TLE since it is n^2.

    // # O(n) solution Using Hashmap

    // Humne element, length of subsequence ka hashmap banaya.
    // [7,8,5,3,4,2,1], difference =-2

    // initially 7 ke accross 1 store hua.
    // same 8 ke accross.

    // ab 5 aaya, 5-(-2)=7, 5 ne kaha 7 tak 1 length ka subsequence exist karta hai,
    // to agar mai us subsequence ka part banunga to total 2 length ka subsequnece
    // exist karega. To 5 ne apne accross 1 store kiya.

    // ab 3 aaya. 3-(-2)=5, 3 ne kaha 5 tak 2 length ka subsequence exist karta hai,
    // to agar mai us subsequence ka part banunga to total 3 length ka subsequnece
    // exist karega. To 3 ne apne accross 3 store kiya.

    // # Aisa karte karte mujhe max subsequence mil jayaga.

    public int longestSubsequence_map(int[] arr, int difference) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int maxLen = 0;
        for (int ele : arr) {
            map.put(ele, map.getOrDefault(ele - difference, 0) + 1);
            maxLen = Math.max(maxLen, map.get(ele));
        }

        return maxLen;
    }

    // b <============= 1424. Diagonal Traverse II ==============>
    // https://leetcode.com/problems/diagonal-traverse-ii/

    // # Simple hai. Mai ek diagonal ko kaise identify kar sakta hun.
    // # Uska i + j index ka sum constant hota hai.

    // Yahan pe bas usi property ka use kiya hai. Per kyunki list ka size vary kar
    // raha hai, to hum i + j ko hashmap ka key banayenge aur uske against list
    // rakhenge elements ki.

    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        HashMap<Integer, LinkedList<Integer>> map = new HashMap<>();

        int maxDiag = 0; // To calculate max diagonal. (total diagonal will be from 0 to maxDiagonal)
        int len = 0; // To calculate total elements in the nums list of list.
        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.get(i).size(); j++) {
                int idx = i + j;
                map.putIfAbsent(idx, new LinkedList<>()); // To new the linked list.
                map.get(idx).addFirst(nums.get(i).get(j));

                maxDiag = Math.max(maxDiag, idx);
                len++;
            }
        }

        int[] ans = new int[len];
        int idx = 0;
        for (int i = 0; i <= maxDiag; i++) {
            LinkedList<Integer> list = map.get(i);
            while (list.size() != 0) {
                ans[idx++] = list.removeFirst();
            }
        }

        return ans;
    }

    // b <================1027. Longest Arithmetic Subsequence ==========>
    // https://leetcode.com/problems/longest-arithmetic-subsequence/

    // # Same LIS wala concept hai.
    // Mai apne se piche wale element se pucha ki tu apne pe khatam hone wala max
    // LIS ki length batade jiska differene k hai. To meri length + 1 hogi.

    // Test Case to dry run : [15,20,1,15,3,10,5,8]
    // Ans : 4, [20,15,10,5]

    public int longestArithSeqLength(int[] nums) {
        int n = nums.length;
        HashMap<Integer, Integer>[] dp = new HashMap[n];

        for (int i = 0; i < n; i++)
            dp[i] = new HashMap<>();

        int len = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                int diff = nums[i] - nums[j];
                int currLen = dp[i].getOrDefault(diff, 0); // Apne curr length nikali
                // For ek 15 3rd index mai haim, usse newLength 3 aayegi aur ye pehle store
                // hoga.Isisliye curr nikalna jarrori hai aur compare karna

                // ek 150th index pe hai, usse newlen 2 aayegi
                int newLen = dp[j].getOrDefault(diff, 1) + 1; // jth element ke sath jo lenght aayi wo nikali

                dp[i].put(diff, Math.max(currLen, newLen)); // Jo dono mai max hoga, whi meri max length hogi.
                len = Math.max(len, dp[i].get(diff)); // To store max length.
            }
        }
        return len;
    }

    // b <========2453. Destroy Sequential Targets ===============>
    // https://leetcode.com/problems/destroy-sequential-targets/description/

    // # Humko multiples check karne the. To multiples ka remainder same hota hai.
    // # Bas yhi kiya hai. Simple.

    // To hashmap mai remainder, count store kiya hai. Jiska sabse jyada hoga, hume
    // whi smallest element kake dena hai.

    // Agar kisi 2 element ka reminder count same hai to hume smallest element
    // return karna hai, isiliye loop ko piche se run kiya.

    public int destroyTargets(int[] nums, int space) {
        Arrays.sort(nums);
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        int maxLen = 0, minEle = -1;
        for (int i = n - 1; i >= 0; i--) {
            int val = nums[i];
            int r = val % space;

            map.put(r, map.getOrDefault(r, 0) + 1);
            if (map.get(r) >= maxLen) {
                maxLen = map.get(r);
                minEle = val;
            }
        }
        return minEle;
    }

    // b <=========1010. Pairs of Songs With Total Durations Divisible by 60 ==>
    // https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/description/

    // Simple remainder nikala. Aur usko store kiya hashmap mai.
    // Hume sare combination nikalne the, to humne 60 - remainder dhundha kyunki whi
    // log total 60 banayenge.

    // (60 - rem) % 60) isiliye kiya since when remainder is 0, tab to 60 ko
    // dhundhta hai, joki kabhi nhi milega. % se wo 0 hojayega.

    public int numPairsDivisibleBy60(int[] time) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int ele : time) {
            int rem = ele % 60;
            if (map.containsKey((60 - rem) % 60)) {
                count += map.get((60 - rem) % 60);
            }
            map.put(rem, map.getOrDefault(rem, 0) + 1);
        }
        return count;
    }

    // b <==========954. Array of Doubled Pairs ==========>
    // https://leetcode.com/problems/array-of-doubled-pairs/description/

    // [0,0,2,4,1,8], [-16,-2,-8,-4] : Test cases to dry run

    // Hume basically yahan pe pucha hai ki can we rearrange array such that array
    // ` becomes like [a,2a,b,2b,c,2c,d,2d].

    // So hume basically har ek element ke liye uska pair dhundhna hai. Aur agar har
    // ek element ke liye uska pair mil jata hai to wo rearrange ho sakta hai.

    // Humne hashmap mai har ek element ki frequency nikali
    // Ab agar humne array ko normally sort kiya to hume array aise milega.
    // -16,-8,-4,-2,0,0,1,2,4,8,. Jisme hume -ve elements ke liye unka half check
    // karna padega aur +ve elements ke liye unka double.

    // Ab agar mai chahta hun ki mujhe har element ka double he check karna page, to
    // isiliye maine Arr ko apne hisab se sorrt karunga. To do this, mujhe Apna ek
    // Integer class ka array banana padega since int is a primitive type.

    // So now the array will be sorted like treating the -ve number as +ve.

    // 0,0,1,2,-2,4,-4,-8,8,-16.

    public boolean canReorderDoubled(int[] arr) {

        int n = arr.length;
        Integer[] Arr = new Integer[n];
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
            Arr[i] = arr[i];
        }

        Arrays.sort(Arr, (a, b) -> { // Sorting the array as if the -ve number are +ve.
            return Math.abs(a) - Math.abs(b);
        });

        for (int ele : Arr) {
            if (map.get(ele) == 0) // If frequency is zero, the element has already been paired.
                continue;
            if (map.getOrDefault(ele * 2, 0) <= 0) // If we cannot find a 2*ele, we cannot pair this element. Hence
                                                   // returning false.
                return false;

            // Since a pairing requires both elements, decreasing the frequency of both the
            // elements of the pair.
            map.put(ele, map.get(ele) - 1);
            map.put(2 * ele, map.get(ele * 2) - 1);
        }

        return true;
    }

    // b <=========== 2007. Find Original Array From Doubled Array ==========>
    // https://leetcode.com/problems/find-original-array-from-doubled-array/description/

    // # Logic is same as above.
    // # But here no -ve elements, so just normally sort the array.

    public int[] findOriginalArray(int[] arr) {

        int n = arr.length;
        if (n % 2 == 1)
            return new int[] {};
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int ele : arr)
            map.put(ele, map.getOrDefault(ele, 0) + 1);

        Arrays.sort(arr);

        int[] ans = new int[n / 2];
        int i = 0;
        for (int ele : arr) {
            if (map.get(ele) == 0)
                continue;

            if (map.getOrDefault(ele * 2, 0) <= 0)
                return new int[] {};

            map.put(ele, map.get(ele) - 1);
            map.put(2 * ele, map.get(ele * 2) - 1);
            ans[i++] = ele;
        }
        return ans;
    }

    // b <=============2122. Recover the Original Array ============>
    // https://leetcode.com/problems/recover-the-original-array/description/

    // Same uper ki tarah hai. Pair banake hashmap se remove he kardo.
    // Agar end mai sare pair ban jayenge, to bas answer ko remove kardo.

    // Consider [a,b,c,d] to be the original array.
    // [a-k,b-k,c-k,d-k] will be the lower array
    // [a+k,b+k,c+k,d+k] will be the higher array.

    // Humne agar array ko sort kiya, to hume pata hai ki nums[0] humesha lower
    // array mai he aayega, kyunki wo smallest value hogi.

    // So consider a-k to be the smallest element in the sorted array.
    // then (a+k)- (a-k)=2k.

    // We know that (a-k) is nums[0].

    // So the formula becomes (nums[i] - nums[0]) /2 for getting the various values
    // of k.

    // Now for each values of k, check if all the pairs are formed. If yes, then
    // return the array.

    public int[] recoverArray(int[] nums) {

        HashMap<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        for (int ele : nums) {
            map.put(ele, map.getOrDefault(ele, 0) + 1); // getting the frequency.
        }

        Arrays.sort(nums);

        int[] ans = new int[n / 2];
        for (int i = 1; i < n; i++) {
            int k = (nums[i] - nums[0]) / 2; // getting all the values of k.

            if (k <= 0)
                continue; // k cannot be -ve or 0.

            HashMap<Integer, Integer> cmap = new HashMap<>(map);
            int idx = 0;
            for (int ele : nums) {
                if (cmap.get(ele) == 0) // Agar kisi element ki frequency 0 hai, to wo already paired hai.
                    continue;

                int higher = ele + 2 * k;
                int lower = ele - 2 * k;

                // If a elements gets paired, then we are decreasing the frequency of both
                // elements of the pair.
                if (cmap.getOrDefault(higher, 0) > 0) {
                    cmap.put(ele, cmap.get(ele) - 1);
                    cmap.put(higher, cmap.get(higher) - 1);
                    ans[idx++] = ele + k;
                } else if (cmap.getOrDefault(lower, 0) > 0) {
                    cmap.put(ele, cmap.get(ele) - 1);
                    cmap.put(lower, cmap.get(lower) - 1);
                    ans[idx++] = ele - k;
                } else {
                    // if the higher or lower cease to exist in the map, then the k value is wrong.
                    break;
                }
            }

            // if all the values are paired, then idx will reach to n/2.
            if (idx == n / 2)
                return ans;
        }

        return new int[] {};
    }

    // b <====================380. Insert Delete GetRandom O(1)=======>
    // https://leetcode.com/problems/insert-delete-getrandom-o1/description/

    // Since we have to return true if data is present or not in insert and remove
    // function, therefore we need to have hashmap. So we will be storing value
    // across index in it.

    // Now but to get randon, since all have equal probabilities, then we cannot do
    // it in hashmap as to access the hashmap,we need a key. So we need another DS
    // to resolve this issue for us. Hence we will be using Arraylist.

    // We can easily get the randon number from a arraylist or array.
    // Since the size can be anything, so we cannnot use array. Hence we will be
    // ` using arraylist.

    class RandomizedSet {

        private ArrayList<Integer> list;
        private HashMap<Integer, Integer> map;
        private Random rand;

        public RandomizedSet() {
            list = new ArrayList<>();
            map = new HashMap<>();
            rand = new Random();
        }

        public boolean insert(int val) {
            if (map.containsKey(val))
                return false;

            // Always adding element in the last index of arraylist.
            // Mapping the same value in HashMap.
            int idx = list.size();
            map.put(val, idx);
            list.add(val);
            return true;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val))
                return false;

            // To remove a element from HashMap is easy.
            // If we remove a element from middle of arraylist, shifting of element occurs
            // which will make this operation of O(n);
            // So what we do is swap with the last element of the arraylist.
            // And remove the last element which is O(1) operation in ArrayList.
            int lidx = list.size() - 1; // last index of arraylist
            int idx = map.get(val);
            list.set(idx, list.get(lidx));
            map.put(list.get(idx), idx); // ` update swapped value index
            list.remove(lidx);
            map.remove(val);
            return true;
        }

        public int getRandom() {
            // rand.nextInt gives value from 0 to n-1;
            // n is the parameted passed in it.
            return list.get(rand.nextInt(list.size()));
        }
    }

    // b <====== 381. Insert Delete GetRandom O(1) - Duplicates allowed=========>
    // https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/

    // # Same like above, just that ki ab duplicates hai to hume hashmap ke andar ek
    // # list maintain karni padegi same value ke indexes ki.

    // The order of statement must be as below in the remove function:

    // 1. Removing the delete index.
    // 2. Then adding the new index of the swapped value.
    // 3. Removing the old index of the swapped value.

    // If not, the following test case will fail :

    // # Test case where when removing 4, the val and last value is 4.
    // # For first iteration, the value of didx will be 0. Now dry run.
    // ["RandomizedCollection","insert","insert","insert","insert","insert","remove","remove","remove","remove"]
    // [[],[4],[3],[4],[2],[4],[4],[3],[4],[4]]

    // # Test case where 0 is the last value, 0 is the value, 0 is the last index
    // # and 0 is the delete index.
    // ["RandomizedCollection","insert","remove","insert","remove","getRandom","getRandom","getRandom","getRandom","getRandom","getRandom","getRandom","getRandom","getRandom","getRandom"]
    // [[],[0],[0],[-1],[0],[],[],[],[],[],[],[],[],[],[]]

    // // didx is the removed or delete index.

    // map.get(val).remove(didx);
    // map.get(lastValue).add(didx);
    // map.get(lastValue).remove(lidx);

    class RandomizedCollection {

        private ArrayList<Integer> list;
        private HashMap<Integer, HashSet<Integer>> map;
        private Random rand;

        public RandomizedCollection() {
            list = new ArrayList<>();
            map = new HashMap<>();
            rand = new Random();
        }

        public boolean insert(int val) {
            list.add(val);
            if (!map.containsKey(val))
                map.put(val, new HashSet<>());
            map.get(val).add(list.size() - 1);
            return map.get(val).size() == 1;
        }

        public boolean remove(int val) {

            if (!map.containsKey(val) || map.get(val).size() == 0) {
                return false;
            }

            int lidx = list.size() - 1; // last index
            int didx = map.get(val).iterator().next(); // delete index
            map.get(val).remove(didx);

            int lastValue = list.get(lidx);

            list.set(didx, lastValue);

            // The order of adding first and then removing should be as mentioned below due
            // to the 0 test case mentioned above.
            map.get(lastValue).add(didx);
            map.get(lastValue).remove(lidx);

            list.remove(lidx);
            return true;
        }

        public int getRandom() {
            return list.get(rand.nextInt(list.size()));
        }
    }

    // b <========== 447. Number of Boomerangs =========>
    // https://leetcode.com/problems/number-of-boomerangs/description/

    // Humne kiya ye pehle ek point liya. Usse har ek remaining point ki distance
    // calculate ki aur use hashmap mai store kiya. To hume end tak same distance pe
    // jitne points hai unki frequency count mil jayega.

    // ab question ye hai ki tuple kitne banenge.
    // So agar manle mujse 1 distance pe 4 log mile hain, to tuple formed will be 4
    // * (4-1)= 4 * 3 ====> i.e. (n * n-1).

    // How ???

    // Since we have to find the pairs with same distance from me and these pairs
    // can have arrangement as can be seen in the test case, at the end we have to
    // find permutation, which will be nP2. i.e. n!/(n-2)!

    // ==> Solving the above will turn out to be n * n-1.

    // At the end map ko clear kar denge aur next point ke liiye same check karenge.

    public int numberOfBoomerangs(int[][] points) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = points.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j)
                    continue;

                int dis = getDistance(points, i, j);
                map.put(dis, map.getOrDefault(dis, 0) + 1);
            }

            for (int value : map.values()) {
                count += value * (value - 1);
            }

            map.clear();
        }

        return count;
    }

    public static int getDistance(int[][] points, int i, int j) {
        int x = points[i][0] - points[j][0];
        int y = points[i][1] - points[j][1];

        return x * x + y * y;
    }

    // b <===========149. Max Points on a Line ============>
    // https://leetcode.com/problems/max-points-on-a-line/

    // # For the points to lie on the same line, their must be same.
    // # Agar kinhi points ke beech ka slope same hai to sare points ek he line mai
    // # lie karte hain.

    // To bas same uper wale question ki tarah kiya.
    // Ek point ko pick kiya aur har dusre points se slope nikala. Jin points ke
    // sath slope same aaya ,wo sab same he line mai exist karte hain.

    // Aur aisa har ek points ke sath kiya. End mai max nikal ke return kar diya.

    // # Formula for slope is m=(y2-y1)/(x2-x1);
    // What if the denominator becomes 0, so the slope will become infinite.

    // Now this needs to be handled. One thing is to replace it with very large
    // number that can be stored in hashMap as key and do increase count as same.

    // Another way is to calculate the gcd of both, y2 - y1, x2 -x1, and reduce it
    // to the lowest fraction possible of slope by dividing both with their
    // gcd(greatest common divisor)

    // Now we will create a unique string using the lowest fraction.
    // Hence here we do not have to consider infinity part, although it could be
    // handled.

    // For example y2 - y1 is 18 and x2 - x1 is 14.
    // For another points , y2 - y1 is 9 and x2 - x1 is 7.

    // Since both the slope are same as 18/14 is also 9/7.
    // So therefore, their string will be same 9@7 as 18/14 gcd will be 2 reducing
    // it to 9/7

    public int maxPoints(int[][] points) {

        HashMap<String, Integer> map = new HashMap<>();

        int n = points.length, ans = 0;
        for (int i = 0; i < n; i++) {
            int max = 0;
            for (int j = i + 1; j < n; j++) {

                int yDiff = points[j][1] - points[i][1];
                int xDiff = points[j][0] - points[i][0];

                int gcd = getGcd(yDiff, xDiff);
                yDiff /= gcd;
                xDiff /= gcd;

                String s = xDiff + "@" + yDiff;
                map.put(s, map.getOrDefault(s, 0) + 1);
                max = Math.max(max, map.get(s));
            }
            ans = Math.max(ans, max + 1); // max + 1, since I am also a point . Maine apne aap ko count he nhi kiya tha
                                          // sare points mai ek line ke.
            map.clear();
        }

        return ans;
    }

    public static int getGcd(int a, int b) {
        if (b == 0)
            return a;
        return getGcd(b, a % b);
    }

    // b <============2280. Minimum Lines to Represent a Line Chart ========>
    // https://leetcode.com/problems/minimum-lines-to-represent-a-line-chart/description/

    // # Logic is same as above.

    public int minimumLines(int[][] arr) {

        int n = arr.length;
        Arrays.sort(arr, (a, b) -> {
            return a[0] - b[0];
        });
        String prev = "$";
        int count = 0;
        for (int i = 0; i < n - 1; i++) {
            int yDiff = arr[i + 1][1] - arr[i][1];
            int xDiff = arr[i + 1][0] - arr[i][0];

            int gcd = getGcd(yDiff, xDiff);
            yDiff /= gcd;
            xDiff /= gcd;

            String s = xDiff + "@" + yDiff;
            if (!s.equals(prev))
                count++;
            prev = s;
        }

        return count;
    }

    public static int getGcd_(int a, int b) {
        if (b == 0)
            return a;
        return getGcd(b, a % b);
    }

    // b <===============2452. Words Within Two Edits of Dictionary ===========>
    // https://leetcode.com/problems/words-within-two-edits-of-dictionary/description/

    // # Simple bas compare kiya characters ko. Agar difference <=2 hai, to possible
    // # hai use convert karna.

    public List<String> twoEditWords(String[] queries, String[] dictionary) {
        List<String> ans = new ArrayList<>();

        for (String str : queries) {
            for (String s : dictionary) {
                int diff = 0;
                for (int i = 0; i < str.length(); i++) {
                    char ch1 = str.charAt(i);
                    char ch2 = s.charAt(i);

                    if (ch1 != ch2)
                        diff++;
                }
                if (diff <= 2) {
                    ans.add(str);
                    break;
                }
            }
        }

        return ans;
    }

    // b <========433. Minimum Genetic Mutation ================>
    // https://leetcode.com/problems/minimum-genetic-mutation/description/

    // ! A Beautifull Graph Question.

    // We can imagine the problem as a graph. Each gene string is a node, and
    // mutations are the edges. Two nodes have an edge (are neighbors) if they
    // differ by one character. The added constraints are that the characters must
    // ` be one of "ACGT", and each node must be in bank.

    // Then, the problem is simplified: what is the shortest path between start and
    // end? When a graph problem involves finding a shortest path, BFS should be
    // ` used over DFS. This is because with BFS, all nodes at distance x from start
    // will be visited before any node at distance x + 1 will be visited. Once we
    // find the target (end), we know that we found it in the shortest number of
    // steps possible.

    // # Simple Bfs lagaya hai. Ek gene ke har eke mutation ko ek edge consider kiya
    // # hai. To sare mutation nikale ek gene ke aur use check kiya ki kaunsa
    // # mutation possible hai, by checking it in the bank.

    // # Number of mutation will be the level of the graph.
    // # Ek level denote karta hai ek gene ke bas ek character ko change karke kitne
    // # mutation possible hain. Ek gene ke jinte mutation possible honge, utni uski
    // # edges hongi.

    public int minMutation(String startGene, String endGene, String[] bank) {

        HashSet<String> bankSet = new HashSet<>();
        HashSet<String> vis = new HashSet<>();

        for (String str : bank)
            bankSet.add(str);

        LinkedList<String> que = new LinkedList<>();
        // addLast and remove first
        que.addLast(startGene);

        int level = 0;
        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {
                String rn = que.removeFirst();

                vis.add(rn);

                if (rn.equals(endGene))
                    return level;

                char[] chr = { 'A', 'C', 'G', 'T' };

                // Getting all possible mutation for a gene and adding them to que if they are
                // not visited and are in the bankset.
                for (char ch : chr) {
                    for (int i = 0; i < 8; i++) {
                        String mutatedString = rn.substring(0, i) + ch + rn.substring(i + 1);
                        if (!vis.contains(mutatedString) && bankSet.contains(mutatedString)) {
                            que.addLast(mutatedString);
                        }
                    }
                }
            }
            level++;
        }
        return -1;
    }

    // b <=========== Word Ladder ===========>
    // https://leetcode.com/problems/word-ladder/

    // ! Getting TLE :

    // # Same as above question.

    public int ladderLength_(String beginWord, String endWord, List<String> wordList) {

        HashSet<String> vis = new HashSet<>();
        HashSet<String> wordSet = new HashSet<>();

        for (String str : wordList)
            wordSet.add(str);
        LinkedList<String> que = new LinkedList<>();
        // addLast and remove first

        que.add(beginWord);
        int level = 0;
        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                String rn = que.removeFirst();

                vis.add(rn);

                if (rn.equals(endWord))
                    return level + 1;
                for (int i = 0; i < 26; i++) {
                    char ch = (char) ('a' + i);
                    for (int j = 0; j < rn.length(); j++) {
                        String transformed = rn.substring(0, j) + ch + rn.substring(j + 1);

                        if (!vis.contains(transformed) && wordSet.contains(transformed))
                            que.add(transformed);
                    }
                }
            }
            level++;
        }
        return 0;
    }

    // ! Optimized :

    // # Since in above we got TLE, we have to optimize the searching of words.
    // # So for all the words in word list, we got all of its generic states.

    // For eg. While doing BFS if we have to find the adjacent nodes for Dug we can
    // first find all the generic states for Dug.

    // Dug => *ug
    // Dug => D*g
    // Dug => Du*

    // # *ug, D*g, and Du* represent all the states of Dug if the Dug is to differ
    // # by single character. Means all 26 character represeented by *.

    // The second transformation D*g could then be mapped to Dog or Dig, since all
    // of them share the same generic state. Having a common generic transformation
    // means two words are connected and differ by one letter.

    // # All the words in word list will be added to thier generic state in map,
    // # because we can use these words only.

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        HashSet<String> vis = new HashSet<>();
        HashMap<String, ArrayList<String>> map = new HashMap<>();

        for (String str : wordList) {
            for (int i = 0; i < beginWord.length(); i++) {
                String groupString = str.substring(0, i) + "*" + str.substring(i + 1);

                ArrayList<String> combination = map.getOrDefault(groupString, new ArrayList<>());
                combination.add(str);
                map.put(groupString, combination);
            }
        }

        LinkedList<String> que = new LinkedList<>();
        // addLast and remove first

        que.add(beginWord);
        int level = 0;
        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                String rn = que.removeFirst();
                vis.add(rn);

                if (rn.equals(endWord))
                    return level + 1;
                for (int i = 0; i < beginWord.length(); i++) {
                    String transformed = rn.substring(0, i) + "*" + rn.substring(i + 1);

                    for (String word : map.getOrDefault(transformed, new ArrayList<>())) { // Only iterating through the
                                                                                           // word in geenric state of
                                                                                           // the remove node word.
                                                                                           // Hence optimizing the
                                                                                           // search.
                        if (!vis.contains(word))
                            que.add(word);
                    }
                }
            }
            level++;
        }
        return 0;
    }

    // ! Another way by storing the index, Which is more space optimized.

    public int ladderLength__(String beginWord, String endWord, List<String> wordList) {

        HashSet<String> vis = new HashSet<>();
        HashMap<String, ArrayList<Integer>> map = new HashMap<>();

        for (int j = 0; j < wordList.size(); j++) {
            String str = wordList.get(j);
            for (int i = 0; i < beginWord.length(); i++) {
                String groupString = str.substring(0, i) + "*" + str.substring(i + 1);

                ArrayList<Integer> combination = map.getOrDefault(groupString, new ArrayList<>());
                combination.add(j);
                map.put(groupString, combination);
            }
        }

        LinkedList<String> que = new LinkedList<>();
        // addLast and remove first

        que.add(beginWord);
        int level = 0;
        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                String rn = que.removeFirst();
                vis.add(rn);

                if (rn.equals(endWord))
                    return level + 1;
                for (int i = 0; i < beginWord.length(); i++) {
                    String transformed = rn.substring(0, i) + "*" + rn.substring(i + 1);

                    for (int wordIndex : map.getOrDefault(transformed, new ArrayList<>())) { // Only iterating through
                                                                                             // the
                                                                                             // word in geenric state of
                                                                                             // the remove node word.
                                                                                             // Hence optimizing the
                                                                                             // search.
                        if (!vis.contains(wordList.get(wordIndex)))
                            que.add(wordList.get(wordIndex));
                    }
                }
            }
            level++;
        }
        return 0;
    }

    // # To more optimized the above question, we can use bidirectional bfs.
    // # Start word or end word do se bfs run karo.
    // # If we ever find a node/word which is in the visited dictionary of the
    // # parallel search we terminate our search, since we have found the meet point
    // # of this bidirectional search. It's more like meeting in the middle instead
    // # of going all the way through.

    // # But the search time reduces to half, since the two parallel searches meet
    // # somewhere in the middle.

}
