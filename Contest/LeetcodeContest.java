import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class LeetcodeContest {

    private class TreeNode {

        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        TreeNode(int val) {
            this(val, null, null);
        }
    }

    // b<===== 2457. Minimum Addition to Make Integer Beautiful =============>
    // https://leetcode.com/problems/minimum-addition-to-make-integer-beautiful/

    // # Logic : if we want a smaller digit sum we have to visit its nearest tens ,
    // # hundreds , thousands and so on and check digit sum at those places, because
    // # we can obtain lesser digit sum at nearest distance at these places only.

    public int digitSum(long n) { // Simple function to calculate the sum
        int sum = 0;
        while (n != 0) {
            sum += n % 10;
            n = n / 10;
        }
        return sum;
    }

    public long makeIntegerBeautiful(long n, int target) {

        long x = n;
        int i = 0; // To calculate the number of iteration that the original number has been
                   // divided by 10.
        while (digitSum(x) > target) {
            // Dividing by 10, reduces 467 to 46.
            // Added + 1 to make it to 47 since the nearest ten for 467 is 470.
            // Then in second iteration, 47 reduces to 4.
            // + 1 makes it 5, since the nearest ten for 46 is 50.
            x = x / 10 + 1;
            i++;
        }
        // Multiplied with Math.pow to get the new number back to the original number of
        // digits.
        // If the initial n is 467, the by the end of while loop, the x will be 5 .
        // So to get the answer, we have to multiply 5 by hundered and subtract it with
        // 467.
        return (long) (x * Math.pow(10, i) - n);
    }

    // b<======Minimum Number of Operations to Sort a Binary Tree by Level=======>
    // https://leetcode.com/problems/minimum-number-of-operations-to-sort-a-binary-tree-by-level/

    // Basically the Problem has been reduced to sort the array with min swaps.

    // What we did first stored the original (index,value) pair in array and sorted
    // it value wise, so that we know the sorted position of values.

    private class pair implements Comparable<pair> {

        int index = -1;
        int value = 0;

        pair(int index, int value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(pair p) {
            return this.value - p.value;
        }
    }

    // Consider it to a similar graph problem
    public int getMinSwapsToGetArraySorted(int[] arr) {
        int minSwaps = 0;

        pair[] indexMapArr = new pair[arr.length];
        for (int i = 0; i < arr.length; i++) { // created a index value pair array
            pair indexValue = new pair(i, arr[i]);
            indexMapArr[i] = indexValue;
        }

        // # Used visited so that once the node has been visited, we don't again start
        // # from it. Otherwise it may again get swapped and we will not get the right
        // # answer.

        Arrays.sort(indexMapArr);
        boolean[] vis = new boolean[indexMapArr.length];
        for (int i = 0; i < indexMapArr.length; i++) {
            pair indexValue = indexMapArr[i];
            int index = indexValue.index; // index before sorted
            if (vis[i] || i == index) // if the sorted index i and unsorted index is same, no need for swaps
                continue;

            vis[i] = true;
            int cycleLength = 0;
            while (i != index) { // To calculate the cycle length and marking the used index.
                vis[index] = true;
                pair nextIndexVal = indexMapArr[index];
                index = nextIndexVal.index;
                cycleLength++;
            }
            minSwaps += cycleLength;
            // Just added cycle length since we do not count the last edge to make a cycle
            // in cycle length.
        }

        return minSwaps;
    }

    public int minimumOperations(TreeNode root) {

        if (root == null || (root.left == null && root.right == null))
            return 0;
        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);
        int level = 0;
        int totalOperation = 0;
        while (que.size() != 0) {
            int size = que.size();
            ArrayList<Integer> myAns = new ArrayList<>();
            while (size-- > 0) {
                TreeNode rn = que.removeFirst();
                myAns.add(rn.val);

                if (rn.left != null)
                    que.addLast(rn.left);
                if (rn.right != null)
                    que.addLast(rn.right);
            }

            int[] arr = new int[myAns.size()];
            int i = 0;
            for (int e : myAns) {
                arr[i] = e;
                i++;
            }
            totalOperation += getMinSwapsToGetArraySorted(arr);
            level++;
        }

        return totalOperation;
    }

    // b <=========Is Subsequence============>
    // https://leetcode.com/problems/is-subsequence/

    // Simple. Dono character nikale. Bas ab check kiya agar dono equal hai to dono
    // ke index ko increase kiya.

    // Aur agar dono equal nhi hai to jis string mai dhundhna hai us string mai aage
    // dhundhne ke liye uske index ko increase kiya.

    public boolean isSubsequence(String str1, String str2) {

        int n1 = str1.length(), n2 = str2.length();
        int i = 0, j = 0;

        while (i < n1 && j < n2) {
            char ch1 = str1.charAt(i);
            char ch2 = str2.charAt(j);

            if (ch1 == ch2) {
                i++;
                j++;
            } else {
                j++;
            }
        }

        return i == n1 ? true : false; // Ab agar i n1 ke equal ho jata hai , matlab puri string mil gayi.
    }

    // b<===========Append Characters to String to Make Subsequence=====>
    // https://leetcode.com/problems/append-characters-to-string-to-make-subsequence/

    // Same as above. Bas end mai difference return kar diya.
    public int appendCharacters(String str2, String str1) {
        int n1 = str1.length(), n2 = str2.length();
        int i = 0, j = 0;

        while (i < n1 && j < n2) {
            char ch1 = str1.charAt(i);
            char ch2 = str2.charAt(j);

            if (ch1 == ch2) {
                i++;
                j++;
            } else {
                j++;
            }
        }

        return n1 - i;
    }

    // b <================= Minimum Cost to Split an Array ============>
    // https://leetcode.com/problems/minimum-cost-to-split-an-array/description/

    // # Maine current subarray ki value nikali aur bache hue array ko kaha tu apni
    // # importance value leke aaja. Dono ko add karke maine min se compare kiya.
    // # Ab aisa agar mai har ek value ke liye karunga, to mujhe min value mil
    // # jayegi.

    // ! Recursion :

    public int minCost_rec(int[] nums, int k, int si, int n) {

        if (si >= n)
            return 0;

        int[] map = new int[n]; // To store the count of numbers,instead of using Hashmap, we used array which
                                // is faster.
        int count = 0;
        int min = (int) (1e9);
        for (int i = si; i < n; i++) {
            int val = nums[i];
            map[val]++;
            if (map[val] == 2)
                count += 2;
            else if (map[val] > 2)
                count++;

            int currentArrImpValue = count + k;
            int remainingArrImpValue = minCost_rec(nums, k, i + 1, n);

            min = Math.min(min, currentArrImpValue + remainingArrImpValue);
        }
        return min;
    }

    public int minCost_rec(int[] nums, int k) {
        return minCost_rec(nums, k, 0, nums.length);
    }

    // ! Memoisation :

    public int minCost_memo(int[] nums, int k, int si, int n, int[] dp) {
        if (si >= n)
            return dp[si] = 0;

        if (dp[si] != -1)
            return dp[si];

        int[] map = new int[n];
        int count = 0;
        int min = Integer.MAX_VALUE; // Integer.MAX_VALUE isiliye liya since test was getting more than (int)(1e9)
        for (int i = si; i < n; i++) {
            int val = nums[i];
            map[val]++;
            if (map[val] == 2)
                count += 2;
            else if (map[val] > 2)
                count++;

            int currentArrImpValue = count + k;
            int remainingArrImpValue = minCost_memo(nums, k, i + 1, n, dp);

            min = Math.min(min, currentArrImpValue + remainingArrImpValue);

        }

        return dp[si] = min;
    }

    public int minCost(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        return minCost_memo(nums, k, 0, n, dp);
    }

    // b <======== 2571. Minimum Operations to Reduce an Integer to 0 ======>
    // https://leetcode.com/problems/minimum-operations-to-reduce-an-integer-to-0/description/

    // The general logarithm states that for every real number n, can be expressed
    // in exponential form as. n = a^x (a to the power x).

    // So Taking log on both sides
    // log n= x (log a)

    // so x= log n / log a;

    // Here we have to represent the n in power of 2.
    // So n = 2 ^ x;

    // Now taking log on both sides,
    // x= log(n)/ log(2);

    // To ye x ki value is the approzimate value, if n is not a power of two.

    // To hum ek lower power value nikalta hain 2 power x and ek just higher value
    // nikalta hain x + 1.

    // Aur unko n se subtract karte hain, jisse difference value aayegi, which will
    // act as next n because ab is difference ko bhi 2 power x mai break karna hoga
    // taki subtract karke answer 0 ke pass aaye.

    // # Faith :

    // maine kaha ki lowDiff mujhe apni min operation lake dede aur highDiff mujhe
    // min operation lake dede. Jo dono mai se minimum hoga usme mai apni
    // calculation ka 1 add kar dunga.

    public int minOperations_(int n) {
        int appPower = (int) (Math.log(n) / Math.log(2)); // Approx power

        if (Math.pow(2, appPower) == n) { // if number a power of 2, return 1.
            return 1;
        }

        int low = (int) (Math.pow(2, appPower));
        int high = (int) (Math.pow(2, appPower + 1));

        int lowDiff = n - low;
        int highDiff = high - n;

        return Math.min(minOperations(lowDiff), minOperations(highDiff)) + 1;
    }

    public int minOperations(int n) {
        return minOperations_(n);
    }

    // ! Optimized :

    // # Humko pata hai ki lowDiff mai aur highDiff mai jo choti value hogi, whi
    // # mujhe answer degi since mujhe min operation chahiye. To maine whin ki call
    // # lagayi jahan pe difference min hoga.

    public int minOperations_Opti_(int n) {
        int appPower = (int) (Math.log(n) / Math.log(2));

        if (Math.pow(2, appPower) == n) {
            return 1;
        }

        int low = (int) (Math.pow(2, appPower));
        int high = (int) (Math.pow(2, appPower + 1));

        int lowDiff = n - low;
        int highDiff = high - n;

        int min = 0;

        if (lowDiff <= highDiff)
            min = minOperations_Opti_(lowDiff);
        else
            min = minOperations_Opti_(highDiff);

        return min + 1;
    }

    public int minOperations_Opti(int n) {
        return minOperations_Opti(n);
    }

    // ! Using while loop

    class Solution {
        public:
            int minOperations(int n) { // n=39

                int count=0;
                 while( n!=0 ){
                     
                     // +1 becoz if n is a pefect power of 2, we can subtract that directly, so 1 operation for the subtraction
                     if( n== pow(2, (int)log2(n)))
                        return count+1;
                     
                     // low : power of 2 and <= n            high: power of 2 and >=n
                     // lowdiff = differnce between n and low
                     // highdiff = difference between high and n
                     
                     int low = pow(2, (int)log2(n));
                     int high = pow(2, (int)log2(n)+1);
                     int lowdiff = n - low; // 7
                     int highdiff = high - n; //25
        
                    // we will always move towards the closer power of 2
                    // for eg: if lowdiff = 7 and highdiff=25, our next target(n)= 7
                    // now in the next iteration : 
                     //                          low =2^2(4),
                     //                          n = 7, &
                     //                          high = 2^3(8)
                     // and we move towards 8 ,ie, do a +1 to reach 8, instead of a -3 to reach 4
                     // becoz we want to get to any perfect power of 2 as quickly as possible
                     if( lowdiff < highdiff)
                         n=lowdiff;
                    
                     else
                         n=highdiff;
                    
                     ++count;  
                 }
                return count;
            }
    };
}
