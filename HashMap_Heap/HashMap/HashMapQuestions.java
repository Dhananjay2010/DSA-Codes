import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
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

    // b <=========413. Arithmetic Slices ============>
    // https://leetcode.com/problems/arithmetic-slices/description/

    // We will increase the window till the difference is same.
    // When the difference is not same, we will start shrinking the window till the
    // ` both ei and si come to same position.

    // # [1,2,3,4,5,7,9,11] , [7,7,7,7,7,7] Test case to dry run.

    public int numberOfArithmeticSlices_subarray(int[] nums) {

        int n = nums.length;

        if (n < 3)
            return 0;

        int si = 0, ei = 0, d = 5000, totalSlices = 0;

        while (ei < n - 1) {
            int currVal = nums[ei], nextVal = nums[ei + 1];
            if (nextVal - currVal != d) {
                while (si != ei) { // Shrinking the window.
                    int count = ei - si - 1; // This will be -ve if the length of the subarray is less than 3.
                    totalSlices += count >= 0 ? count : 0;
                    si++;
                }
                d = nextVal - currVal;
            }
            ei++;
        }
        // It may happen that after the above loop has been terminated, there is still a
        // window left. Hence shrinking the window again to get all the subarrays.
        while (si != ei) {
            int count = ei - si - 1;
            totalSlices += count >= 0 ? count : 0;
            si++;
        }

        return totalSlices;
    }

    // # Bhaiya method :

    // The idea of this approach lies behind the simple observation, but little
    // tricky because we have min length of subset.
    // Every extra element in the subsequence increases amount of subsets by length
    // of subsequence.
    // For example we have [1,2,3] with 1 correct subset, and we have just added 4
    // to it.
    // That mean we got new subsets 1234 and 234, why that? we already had 123
    // ` before so we can construct only 2 more.
    // ` But at this point the relation is still not very obvious
    // Lets add on more digit, we had [1,2,3,4] from previous step with 3 correct
    // subsets. Now we will have [1,2,3,4,5]
    // Which subsets did we got? 12345, 2345, 345.
    // Lets add on more digit, we had [1,2,3,4,5] with 6 correct subsets. Now we
    // will have [1,2,3,4,5,6].
    // Which subsets did we got? 123456 23456 3456 456

    public int numberOfArithmeticSlices_(int[] arr) {
        if (arr.length < 3)
            return 0;

        int ans = 0;
        int count = 0;

        for (int i = 1; i < arr.length - 1; i++) {

            int d1 = arr[i] - arr[i - 1];
            int d2 = arr[i + 1] - arr[i];

            if (d1 == d2)
                ans += (++count);
            else
                count = 0;
        }

        return ans;
    }

    // b<=======446. Arithmetic Slices II - Subsequence ===========>
    // https://leetcode.com/problems/arithmetic-slices-ii-subsequence/description/

    // # Maine same faith rakha hai.

    // Maine kaha ki do elements ka diff nikala aur kahan iss difference ke sath aur
    // elements ke conbination dhundho.

    // Aur since ye subsequence hai, to maine diff har ek apne se aage wale element
    // ke sath check kiya.

    // For example we have [1,2,3] with 1 correct subset, and we have just added 4
    // to it.
    // That mean we got new subsets 1234 and 234, why that? we already had 123
    // ` before so we can construct only 2 more.
    // ` But at this point the relation is still not very obvious
    // Lets add on more digit, we had [1,2,3,4] from previous step with 3 correct
    // subsets. Now we will have [1,2,3,4,5]
    // Which subsets did we got? 12345, 2345, 345.
    // Lets add on more digit, we had [1,2,3,4,5] with 6 correct subsets. Now we
    // will have [1,2,3,4,5,6].

    // No need for base case since for loop will handle it.

    // ! Recursion :

    public int numberOfArithmeticSlices_recu(int[] nums, int idx, long diff) {
        int n = nums.length;
        int count = 0;
        for (int i = idx + 1; i < n; i++) {
            long currDiff = (long) nums[i] - (long) nums[idx];
            if (currDiff == diff) {
                count += 1 + numberOfArithmeticSlices_recu(nums, i, diff);
            }
        }

        return count;
    }

    public int numberOfArithmeticSlices_recu(int[] nums) {
        int n = nums.length;

        int ans = 0;
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                long diff = (long) nums[j] - (long) nums[i];

                ans += numberOfArithmeticSlices_recu(nums, j, diff);
            }
        }

        return ans;
    }

    // ! Memoisation :

    public int numberOfArithmeticSlices_memo(int[] nums, int idx, long diff, int[] dp) {
        int n = nums.length;
        int count = 0;
        if (dp[idx] != 0)
            return dp[idx];
        for (int i = idx + 1; i < n; i++) {
            long currDiff = (long) nums[i] - (long) nums[idx];
            if (currDiff == diff) {
                count += 1 + numberOfArithmeticSlices_memo(nums, i, diff, dp);
            }
        }

        return dp[idx] = count;
    }

    public int numberOfArithmeticSlices(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                int[] dp = new int[n + 1];
                long diff = (long) nums[j] - (long) nums[i];
                ans += numberOfArithmeticSlices_memo(nums, j, diff, dp);
            }
        }
        return ans;
    }
}
