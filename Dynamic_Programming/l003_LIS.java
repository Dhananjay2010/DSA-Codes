import java.util.Arrays;

public class l003_LIS {

    // b<================= Longest Increasing Subsequence ==============>
    // https://leetcode.com/problems/longest-increasing-subsequence/

    // ! Recursion :

    // Faith :
    // # Maine har ek element se kaha ki jo bhi tumse chote element hai, unse un tak
    // # ka LIS lake do. Aur jaise mai usme +1 kar dunga, to wo mere tak kaa longest
    // # LIS hoga.

    // # Ei se chote jitne bhi log hai na, muhje apne pe end one wala LIS nikal ke
    // # dedo. Merepe khatam hone wala jo LIS hoga tumhari value + 1 hoga.

    // # Ab merese chote to bahut sare log ho sakte hain. To sab mai jiski value
    // # jyada aayegi, mai unka max le lunga.

    // # Aur mujhe aisa array ke har ek element ke liye karna padega.Aur usme mai se
    // # jo max aayega mai use return kar dunga

    public int lengthOfLIS_rec(int[] arr, int ei) {
        int maxLen = 1; // har element apne aap to hoga he LIS.

        for (int idx = ei - 1; idx >= 0; idx--) {

            if (arr[ei] > arr[idx]) {
                maxLen = Math.max(maxLen, lengthOfLIS_rec(arr, idx) + 1);
            }
        }

        return maxLen;
    }

    public int lengthOfLIS_rec(int[] nums) {
        int maxLen = 0;
        int[] arr = { 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15, 14 }; // Good test case.
        for (int i = 0; i < nums.length; i++) {
            maxLen = Math.max(maxLen, lengthOfLIS_rec(nums, i));
        }

        return maxLen;
    }

    // ! Memoisation :

    // # aayega mai use return kar dunga
    public int lengthOfLIS_memo(int[] arr, int ei, int[] dp) {

        if (dp[ei] != 0) {
            return dp[ei];
        }
        int maxLen = 1;

        for (int idx = ei - 1; idx >= 0; idx--) {

            if (arr[ei] > arr[idx]) {
                maxLen = Math.max(maxLen, lengthOfLIS_memo(arr, idx, dp) + 1);
            }
        }

        return dp[ei] = maxLen;
    }

    public int lengthOfLIS(int[] nums) {
        int maxLen = 0;
        int[] arr = { 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15, 14 }; // Good test case.
        int[] dp = new int[nums.length]; // Since 0 can never be the part of the answer so no need to fill it with -1;
        for (int i = 0; i < nums.length; i++) {
            maxLen = Math.max(maxLen, lengthOfLIS_memo(nums, i, dp));
        }

        return maxLen;
    }

    // b <================== LIS (Left to Right) ====================>
    // # LIS ka har ek index uspe khatam hone wala longest increasing sequence
    // # batata hai

    // int[] arr = { 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15, 14 };
    // For the above test case , LIS for 9 will be 0, 4, 6, 9.
    public static int LIS_LR(int[] arr, int[] dp) {
        int maxLen = 0;
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            dp[i] = 1; // Har ek element apne aap mai he longest increasing to hoga he.
            for (int j = i - 1; j >= 0; j--) {
                if (arr[i] > arr[j]) { // Merese koi choti value hai mai tabhi check karunga.
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }

    // b<============ LDS(Left to right) ==============>
    // # Longest Decreasing Subsequence.

    // # LDS ka har ek index uspe khatam hone wala longest decreasing sequence
    // # batata hai

    // int[] arr = { 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15, 14 };
    // For the above test case , LDS for 9 will be 12, 10, 9.

    // Just here just changed the arrow sign in the comparison as compared to LIS.
    public static int LDS_LR(int[] arr, int[] dp) {
        int n = arr.length, maxLen = 0;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[i] < arr[j]) { // agar koi merese badi value hai tabhi mai check karunga.
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(dp[i], maxLen);
        }

        return maxLen;
    }

    // b <================== LIS (Right to Left) ====================>
    public static int LIS_RL(int[] arr, int[] dp) {
        int n = arr.length, maxLen = 0;
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (arr[i] > arr[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            maxLen = Math.max(dp[i], maxLen);
        }

        return maxLen;
    }

    // b<============ LDS(Right to Left) ==============>
    public static int LDS_RL(int[] arr, int[] dp) {
        int n = arr.length, maxLen = 0;
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (arr[i] < arr[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(dp[i], maxLen);
        }

        return maxLen;
    }

    // b <===========Longest Bitonic Subsequence ================>
    // https://practice.geeksforgeeks.org/problems/longest-bitonic-subsequence0824/1

    // # For this we will be needing two things :

    // 1. Merepe khatam hone wala longest increasing subsequence. (LIS {Left to
    // Right})
    // 2. Merese start hone wala longest decreasing subsequence joki sam hai agar
    // mai LIS ko right to left nikal deta hun. (LIS {Rigth to Left});

    // # Why ??? Jo sequence mere liye left se right dekhne mai decreasing hai, agar
    // # usi sequence ko mai right to left dekhun to wo mujhe increasing dikhega.

    // int[] arr = { 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15, 14 };

    // Manle hum 14 ki baar kar rahe hain uper. To agar mai 14 pe khatam hone wala
    // longest increasing subsequence nikal dun left to right wala aur same 14 pe
    // khatam hone wla longest subsequence nikal dun right to left, to mujhe answee
    // mil jayega 14 ke liye. Per kyunki maine 14 ko do bar include kiya hai to
    // mujhe answer mai se -1 karna hoga. Aur aisa mai sare elements ke liye karne
    // wla hun.

    public static void LIS_LR_bitonic(int[] arr, int[] dp) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[i] > arr[j])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
    }

    public static void LIS_RL_bitonic(int[] arr, int[] dp) {
        int n = arr.length;
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (arr[i] > arr[j])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
    }

    public int LongestBitonicSequence(int[] nums) {

        int n = nums.length;
        int[] LIS = new int[n];
        int[] LDS = new int[n];

        LIS_LR_bitonic(nums, LIS);
        LIS_RL_bitonic(nums, LDS);
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            maxLen = Math.max(maxLen, LIS[i] + LDS[i] - 1);
        }
        return maxLen;
    }

    // b <==============Reverse Bitonic Sequence ================>

    // 1. Merepe khatam hone wala longest decreasing subsequence. (LDS {Left to
    // Right})
    // 2. Merese start hone wala longest increasing subsequence joki same hai agar
    // mai LDS ko right to left nikal deta hun. (LDS {Rigth to Left});

    public int LongestReverseBitonicSequence(int[] nums) {

        int n = nums.length;
        int[] LDS = new int[n];
        int[] LIS = new int[n];

        LDS_LR(nums, LDS);
        LDS_RL(nums, LIS);
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            maxLen = Math.max(maxLen, LIS[i] + LDS[i] - 1);
        }
        return maxLen;
    }

    // b <=========== Maximum Sum Bitonic Subsequence=============>
    // https://practice.geeksforgeeks.org/problems/maximum-sum-bitonic-subsequence1857/1

    // # Same as above bas dp sum ki banayi, aur baki sab same.

    public static void max_LIS_Sum_LR(int[] arr, int[] dp) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            dp[i] = arr[i];
            for (int j = i - 1; j >= 0; j--) {
                if (arr[i] > arr[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + arr[i]);
                }
            }
        }
    }

    public static void max_LIS_Sum_RL(int[] arr, int[] dp) {
        int n = arr.length;
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = arr[i];
            for (int j = i + 1; j < n; j++) {
                if (arr[i] > arr[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + arr[i]);
                }
            }
        }
    }

    public static int maxSumBS(int nums[], int n) {
        int[] LIS = new int[n];
        int[] LDS = new int[n];

        max_LIS_Sum_LR(nums, LIS);
        max_LIS_Sum_RL(nums, LDS);
        int maxSum = 0;
        for (int i = 0; i < n; i++) {
            maxSum = Math.max(maxSum, LIS[i] + LDS[i] - nums[i]);
        }
        return maxSum;
    }

    // b <==============Maximum sum increasing subsequence===============>
    // https://practice.geeksforgeeks.org/problems/maximum-sum-increasing-subsequence4749/1

    public static int max_LIS_Sum_LR_(int[] arr, int[] dp) {
        int n = arr.length;
        int maxSum = 0;
        for (int i = 0; i < n; i++) {
            dp[i] = arr[i];
            for (int j = i - 1; j >= 0; j--) {
                if (arr[i] > arr[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + arr[i]);
                }
            }
            maxSum = Math.max(maxSum, dp[i]);
        }

        return maxSum;
    }

    public int maxSumIS(int arr[], int n) {
        int[] dp = new int[n];
        return max_LIS_Sum_LR_(arr, dp);
    }

    // b <=========Mimimum deletetion required to make array sorted =======>

    // Logic ye hai ki longest increasing nikal aur usko array ki length se minus
    // karde.

    // Per yahan pe ek aur check add hoga. For test case [2,2,3,3,0], the min
    // deletion will be 1;

    // To iske liye jahan pe compare kar rahe hain, wahan pe equal values ko bhi
    // lenge.

    public static int minDeletion(int[] arr) {
        int n = arr.length, maxLen = 0;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[i] >= arr[j]) { // # Here added equal sign, rest is same as LIS.
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(dp[i], maxLen);
        }

        return arr.length - maxLen;
    }

    // b <===========Number of Longest Increasing Subsequence ===============>
    // https://leetcode.com/problems/number-of-longest-increasing-subsequence/

    // Simple hai ,same LIS ka use karna hai, bas thoda count maintain karke chalna
    // hai.

    // Humari dp same max length store karegi. To dp ka index whi batayega ki merepe
    // khatan hone wala LIS ki max Length.

    // Second humne ek count array variable rakha hai joki ye denote kar raha hai ki
    // mere maxLength ke kitne LIS hai jo merepe end hote hain.

    // # tab end mai max nikalte gaye aur max count return kar diya.
    public int findNumberOfLIS(int[] arr) {
        int n = arr.length, maxLen = 0, maxCount = 0;
        int[] dp = new int[n];
        int[] count = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            count[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[i] > arr[j]) {
                    if (dp[j] + 1 > dp[i]) { // Agar merese bada hai to sab update kardo.
                        dp[i] = dp[j] + 1;
                        count[i] = count[j];
                    } else if (dp[i] == dp[j] + 1) { // Agar same Len ka mila hai to bas count update kardo.
                        count[i] += count[j];
                    }
                }
            }
            if (dp[i] > maxLen) { // Agar allover max length se bada nikala, to sab update mardo
                maxLen = dp[i];
                maxCount = count[i];
            } else if (dp[i] == maxLen) { // Agar same nikla to count mai add kardo.
                maxCount += count[i];
            }
        }
        return maxCount;
    }

    // b <============= Building Bridges ===============>
    // https://www.geeksforgeeks.org/dynamic-programming-building-bridges/

    // maine ek side ko sort kiya, isse maine ye insure kiya ki meri ek side kabhi
    // ` bhi overlap nhi karegi.

    // Aur dusri side se maine LIS chala diya taki mujhe max increasing length mil
    // jaye.

    // Ye karne se mujhe mil jayega ki kitne non overlapping bridges mai bana sakta
    // hun. Kyun ???

    // # Kyunki ab mujhe dono increasing mai mile hai to wo kabhi bhi overlap
    // # karenge he nhi

    public static int maxBridges(int[][] arr) {
        Arrays.sort(arr, (a, b) -> {
            return a[1] - b[1]; // Sorted at 1 index
        });
        int maxLen = 0;
        int n = arr.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) { // Now Lis at 0th index
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[i][0] > arr[j][0] && arr[i][1] > arr[j][1]) { // arr[i][1] > arr[j][1] This check added so that
                                                                      // ending point of the bridges should not be same.
                                                                      // To be added if needed.
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }

    // b <============Russian Doll Envelopes ==============>
    // https://leetcode.com/problems/russian-doll-envelopes/

    // Logic is same as above.
    // The code gave TLE since there is also a approach of NLogN which we will study
    // in Arrays part. But for now, this is the solution.

    // Height ko sort karo, aur width pe LIS chala do. Aisa karne se hume dono
    // increasing order mai mil jayega.

    public int maxEnvelopes(int[][] arr) {
        Arrays.sort(arr, (a, b) -> {
            return a[1] - b[1];
        });
        int maxLen = 0;
        int n = arr.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[i][0] > arr[j][0]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
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
