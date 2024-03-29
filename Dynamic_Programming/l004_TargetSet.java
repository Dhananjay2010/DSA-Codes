import java.util.Arrays;

public class l004_TargetSet {

    public static void display(int[] dp) {
        for (int ele : dp) {
            System.out.print(ele + " ");
        }
        System.out.println();
    }

    public static void display2D(int[][] dp) {
        for (int[] d : dp) {
            display(d);
        }
        System.out.println();
    }

    // b <============= Coin Change (Permutation) =============>
    // Aapke pass coins ki infinite supply hai. Find number of permutation to
    // achieve the target.

    // # Permutation mai main apne se piche wale coins ko bhi access kar sakta hun.

    // ! Recursion :
    public static int permutation_rec(int[] arr, int tar) {

        if (tar == 0)
            return 1;

        int count = 0;
        for (int i = 0; i < arr.length; i++) { // Therefore i started from 0.
            if (tar - arr[i] >= 0)
                count += permutation_rec(arr, tar - arr[i]);
        }

        return count;
    }

    // ! Memoisation :

    public static int permutation_memo(int[] arr, int tar, int[] dp) { // dp size will be tar+1. (tar - 0 +1)

        if (tar == 0)
            return dp[tar] = 1;

        if (dp[tar] != -1)
            return dp[tar];
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (tar - arr[i] >= 0)
                count += permutation_memo(arr, tar - arr[i], dp);
        }

        return dp[tar] = count;
    }

    // ! Observation and Tabulation :

    public static int permutation_tabu(int[] arr, int TAR, int[] dp) { // dp size will be tar+1. (tar - 0 +1)

        for (int tar = 0; tar <= TAR; tar++) {
            if (tar == 0) {
                dp[tar] = 1;
                continue;
            }
            int count = 0;
            for (int i = 0; i < arr.length; i++) {
                if (tar - arr[i] >= 0)
                    count += dp[tar - arr[i]];// permutation_tabu(arr, tar - arr[i], dp);
            }
            dp[tar] = count;
        }
        return dp[TAR];
    }

    public static void permutation_d(int[] arr, int tar) {
        int[] dp = new int[tar + 1];
        Arrays.fill(dp, -1);
        permutation_tabu(arr, tar, dp);
    }

    // b <============= Coin Change (Combination) ===========>
    // Aapke pass coins ki infinite supply hai. Find number of combination to
    // achieve the target.

    // ! Recursion :

    // # In combination, mai sirf apne aap ko ya apne se aage walon ko he pick kar
    // # sakta hun. Agar maine apne se piche walon ko pick kar diya to arrangement
    // # ho jayegi, joki permutation hoti hai, jo mai nhi chahta.

    // ? For test case [2,3,5], and target 10, the combination will be :
    // 2 2 2 2 2
    // 2 2 3 3
    // 2 3 5
    // 5 5

    // Kyunki infinite supply hai isiliye main apne aap ko dubara use kar paya.

    public static int combination_rec(int[] arr, int idx, int tar) {

        if (tar == 0) {
            return 1;
        }

        int count = 0;
        for (int i = idx; i < arr.length; i++) { // i started from idx.
            if (tar - arr[i] >= 0)
                count += combination_rec(arr, i, tar - arr[i]);
        }

        return count;
    }

    // ! Memoisation :

    // 2-d dp is used since two variables are changing. tar and idx.
    public static int combination(int[] arr, int n, int tar, int[][] dp) {
        if (tar == 0)
            return dp[n][tar] = 1;
        if (dp[n][tar] != -1)
            return dp[n][tar];
        int count = 0;
        for (int i = n; i > 0; i--) { // loop piche se start isliye kiya taki observation mai help mile.
            if (tar - arr[i - 1] >= 0)
                count += combination(arr, i, tar - arr[i - 1], dp);
        }
        return dp[n][tar] = count;
    }

    // ! Tabulation :
    // Since it is 2-d dp, hum isko same pehle ki tarah do loop laga ke memoisation
    // ka code use kar sakte hain.

    // Jab hum usme observation karte hain, to humne pata lagta hai ki hum pehle ki
    // ek coin ke liye sare target ko achieve karne ka count nikalte hain aur tab
    // next coin ke liye bhi same karte hain. Aisa karte karte end mai hume apne
    // target ke liya answer mil jata hai.

    // To ab hum single dp mai convert kar sakte hain yhi cheez ko use karke.
    // Same method, pehle single coin ke liye sare target ko explore kiya . Then
    // next coin ke liye. Aisa karke hume answer mil jayega.

    // # Tabulation using 1-d dp.
    public static int combination_tabu(int[] arr, int Tar, int[] dp) { // Do not fill dp with -1 for this. Can do it in
                                                                       // memoisation.
        dp[0] = 1;
        for (int ele : arr) {
            for (int tar = ele; tar <= Tar; tar++) {
                if (tar - ele >= 0) {
                    dp[tar] += dp[tar - ele];
                }
            }
        }
        return dp[Tar];
    }

    public static void target() {
        int[] arr = { 2, 3, 5, 7 };
        int tar = 10;
        int[] dp = new int[tar + 1];
        // fill(dp);

        // int[][] dp = new int[arr.length + 1][tar + 1];
        // fill2D(dp);

        // System.out.println(permutation(arr, tar, dp));
        System.out.println(combination_tabu(arr, tar, dp));
        // display2D(dp);

        display(dp);
    }

    // b <============= Coin Change =============>
    // https://leetcode.com/problems/coin-change/description/

    // # Faith :
    // Maine sare coins ko bola tum jao aur tum jitne min coins ke sath target ko
    // achieve kar sakte hao wo leke aa jao. Ab jo tumme se min hoga, utne min coins
    // required honge.

    public static int permutation_(int[] coins, int[] dp, int target) {
        if (target == 0)
            return dp[target] = 0; // 0 return kiya since mai top to bottom ans create kar raha hun, to target ==0
                                   // humnesha 0 he return karega.

        if (dp[target] != -1)
            return dp[target];
        int min = (int) 1e9;
        for (int ele : coins) {
            if (target - ele >= 0) {
                int value = permutation_(coins, dp, target - ele);
                min = Math.min(min, value + 1); // Ye maine apne naam ka + 1 kiya hai, kyunki maine abhi apne aap ko to
                                                // count to kiya he nhi tha.
            }
        }

        return dp[target] = min;
    }

    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, -1);
        int x = permutation_(coins, dp, amount);
        return x == (int) 1e9 ? -1 : x;
    }

    // # Since here min number of coins are asked, therefore it does not matter if
    // # you find permutation or combination. Results will remain same.

    public int coinChange_combination(int[] coins, int TAR) {

        int[] dp = new int[TAR + 1];
        Arrays.fill(dp, (int) 1e9);
        dp[0] = 0;
        for (int ele : coins) {
            for (int tar = 0; tar <= TAR; tar++) {
                if (tar - ele >= 0) {
                    dp[tar] = Math.min(dp[tar], dp[tar - ele] + 1);
                }
            }
        }

        return dp[TAR] == (int) 1e9 ? -1 : dp[TAR];
    }

    // b <==================== Subset Sum Problem ==============>
    // https://practice.geeksforgeeks.org/problems/subset-sum-problem-1611555638/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // # This can be solved by using normal single permutation/ single combination
    // # recursion/memoistaion/ tabulation.
    // Why single ? Since one index element can be used once.

    // ! Solved here by using single permutation.

    public static int isSubsetSum_permutation(int[] arr, int tar, int[] dp) {

        if (tar == 0)
            return dp[tar] = 1;

        if (dp[tar] != -1)
            return dp[tar];
        boolean res = false;
        for (int i = 0; i < arr.length; i++) {
            int ele = arr[i];
            if (tar - ele >= 0 && arr[i] > 0) {
                arr[i] = -arr[i];
                res = res || (isSubsetSum_permutation(arr, tar - ele, dp) == 1);
                arr[i] = -arr[i];
            }
        }

        return dp[tar] = res ? 1 : 0;
    }

    static Boolean isSubsetSum_(int N, int arr[], int sum) {
        // code here
        int[] dp = new int[sum + 1];
        Arrays.fill(dp, -1);
        return isSubsetSum_permutation(arr, sum, dp) == 1 ? true : false;
    }

    // # But it generally solved by using subsequence method.

    // Subsequence mai har kisi ke pass do choices hoti hain.
    // 1. Mai answer ka part banunga.
    // 2. Mai answer ka part nhi banunga.

    // ? Since true and false can be the part of the answer therefore dp cannot be
    // ? made boolean. Therefore a integer dp is used.

    // -1 : not explored, 0 : false, 1 : true
    public static int targetSum(int[] arr, int n, int tar, int[][] dp) {
        if (n == 0 || tar == 0) {
            return dp[n][tar] = (tar == 0 ? 1 : 0);
        }

        if (dp[n][tar] != -1)
            return dp[n][tar];

        boolean res = false;
        if (tar - arr[n - 1] >= 0)
            res = res || targetSum(arr, n - 1, tar - arr[n - 1], dp) == 1;
        res = res || targetSum(arr, n - 1, tar, dp) == 1;

        return dp[n][tar] = res ? 1 : 0;
    }

    public static Boolean isSubsetSum(int N, int arr[], int sum) {
        int[][] dp = new int[N + 1][sum + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);

        int ans = targetSum(arr, N, sum, dp);

        return ans == 1;
    }

    // ! Observation & Tabulation :

    // A single row in this tabulation dp indicates that kya mai apne se ya merese
    // `uper jitne bhi coins hain, unka use karke target ko achieve kar sakta hun ya
    // nhi.

    public static boolean targetSum_tabu(int[] arr, int N, int TAR, boolean[][] dp) {

        for (int n = 0; n <= N; n++) {
            for (int tar = 0; tar <= TAR; tar++) {
                if (n == 0 || tar == 0) {
                    dp[n][tar] = (tar == 0);
                    continue;
                }

                boolean res = false;
                if (tar - arr[n - 1] >= 0)
                    res = res || dp[n - 1][tar - arr[n - 1]]; // (arr, n - 1, tar - arr[n - 1], dp) == 1;
                res = res || dp[n - 1][tar]; // (arr, n - 1, tar, dp) == 1;

                return dp[n][tar] = res;

            }
        }

        return dp[N][TAR];
    }

    // ! Back Engineering :

    // # For back engineering, we should not use the memoised dp since many of the
    // # cell in the dp can be unexplored and are never evaluated. Hence we should
    // # always use tabulation dp.

    // # Mai sirf wahan ki call lagata hun jahan pe answer hoga dp mai.

    public static int target_sum_backEngineering(int[] arr, int n, boolean[][] dp, int tar, String asf) {

        if (tar == 0 || n == 0) {
            if (tar == 0) {
                System.out.println(asf);
                return 1;
            }
            return 0;
        }
        int count = 0;
        // Sirf wahan ki call lagayi jahan pe dp mai true hai.
        if (tar - arr[n - 1] >= 0 && dp[n - 1][tar - arr[n - 1]]) { // Maine element ko pick kiya.
            count += target_sum_backEngineering(arr, n - 1, dp, tar - arr[n - 1], asf + arr[n - 1] + " ");
        }
        if (dp[n - 1][tar]) { // Maine element ko pick nhi kiya.
            count += target_sum_backEngineering(arr, n - 1, dp, tar, asf);
        }

        return count;
    }

    public static void targetSum_backEngg() {
        int[] arr = { 2, 3, 5, 7 };
        int tar = 10, N = 4;
        boolean[][] dp = new boolean[N + 1][tar + 1];
        System.out.println(targetSum_tabu(arr, N, tar, dp));
        System.out.println(target_sum_backEngineering(arr, N, dp, tar, ""));
    }

    // b <============= KnapSack ==================>
    // https://practice.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article
    // ? Good Question. Pick question from scratch.

    // # Simple sa faith use kiya hai yahan pe. Maine simple subsequence ki call
    // # lagayi hai.

    // # To har ek element se ye pucha ki agar mai tereko pick karta hun, to tu apni
    // # value bata de.

    // # Aur agar mai tereko nhi pick karta to tabhi bhi apni value bta de.

    // # Indo mai se jo max aayega, whi mera answer hoga.

    public static int knapSack(int W, int wt[], int[] val, int n, int[][] dp) {
        if (W == 0 || n == 0) // Agar merepe weight he nhi bache pick karne ke(n==0), to total value zero
                              // hogi.
                              // for W==0, mere sack ki limit he 0 hai to mai kuch wt dal he nhi paunga.
            return dp[n][W] = 0;

        if (dp[n][W] != -1)
            return dp[n][W];

        int maxVal = 0;
        if (W - wt[n - 1] >= 0)
            // Agar mai pick karta hun to mai uski value bhi to add karunga.
            maxVal = Math.max(maxVal, knapSack(W - wt[n - 1], wt, val, n - 1, dp) + val[n - 1]);
        maxVal = Math.max(maxVal, knapSack(W, wt, val, n - 1, dp));

        return dp[n][W] = maxVal;

    }

    static int knapSack(int W, int wt[], int val[], int n) {
        // your code here

        int[][] dp = new int[n + 1][W + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);

        return knapSack(W, wt, val, n, dp);
    }

    // b <============== Unbounded Knapsack ===========>
    // https://practice.geeksforgeeks.org/problems/knapsack-with-duplicate-items4201/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // Same as above but here the coins can be used n number of times.

    public static int knapSack_unbounded(int W, int wt[], int[] val, int n, int[][] dp) {
        if (W == 0 || n == 0)
            return dp[n][W] = 0;

        if (dp[n][W] != -1)
            return dp[n][W];

        int maxVal = 0;
        if (W - wt[n - 1] >= 0)
            maxVal = Math.max(maxVal, knapSack_unbounded(W - wt[n - 1], wt, val, n, dp) + val[n - 1]);
        maxVal = Math.max(maxVal, knapSack_unbounded(W, wt, val, n - 1, dp));

        return dp[n][W] = maxVal;

    }

    static int knapSack(int n, int W, int val[], int wt[]) {
        // your code here
        int[][] dp = new int[n + 1][W + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);

        return knapSack_unbounded(W, wt, val, n, dp);
    }

    // ! Tabulation :
    // Same like combination

    public static int unboundedKnapsack_Tabu_dp(int[] wt, int[] val, int bagWeight) {
        int n = wt.length;
        int[] dp = new int[bagWeight + 1];
        for (int i = 0; i < wt.length; i++) {
            for (int weight = 0; weight <= bagWeight; weight++) {
                if (weight - wt[i] >= 0)
                    dp[weight] = Math.max(dp[weight], dp[weight - wt[i]] + val[i]);
            }
        }

        return dp[bagWeight];
    }

    // b <======Find number of solutions of a linear equation of n variables ====>
    // https://www.geeksforgeeks.org/find-number-of-solutions-of-a-linear-equation-of-n-variables/

    // It is same as infinite combination.
    // Input: coeff[] = {1, 2}, rhs = 5
    // Output: 3
    // The equation "x + 2y = 5" has 3 solutions.
    // (x=3,y=1), (x=1,y=2), (x=5,y=0).

    // In the above example consider it like this.
    // Mai kitne 1 coins aur 2 coins kar use karke 5 target ko achive kar sakta hun.
    // Concept same as infinite combination.

    // b <===========Partition Equal Subset Sum ===============>
    // https://leetcode.com/problems/partition-equal-subset-sum/description/

    // Agar array ko equal subsets mai batna hai, to array ke elements ka sum
    // devided by 2 should be an integer. Agar nhi, to false kardo return.

    // Ab agar array ke kuch elements ka sum = totalArraySum/2, tab wo array do
    // equal sets mai devide ho sakta hai.

    public static boolean targetSum_DP(int[] arr, int N, int TAR, boolean[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int tar = 0; tar <= TAR; tar++) {
                if (n == 0 || tar == 0) {
                    dp[n][tar] = (tar == 0);
                    continue;
                }
                boolean res = false;
                if (tar - arr[n - 1] >= 0)
                    res = res || dp[n - 1][tar - arr[n - 1]]; // (arr, n - 1, tar - arr[n - 1], dp) == 1;
                res = res || dp[n - 1][tar]; // (arr, n - 1, tar, dp) == 1;
                return dp[n][tar] = res;
            }
        }
        return dp[N][TAR];
    }

    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int ele : nums)
            sum += ele;

        if (sum % 2 != 0)
            return false;
        int tar = sum / 2, n = nums.length; // now the target is sum/2. If target reached, then array can be splitted
                                            // into 2 subsets of equal value.
        boolean[][] dp = new boolean[n + 1][tar + 1];
        return targetSum_DP(nums, n, tar, dp);
    }

    // b <============ Target Sum ================>
    // https://leetcode.com/problems/target-sum/description/

    // ! Recursion :
    // Code will pass because the constraints are small, but the complexity will not
    // ` be good.

    // # Faith :
    // maine bola har ek coin ko ki ek bar tu +ve banke jaa aur ek bar -ve banke
    // jaa. Ab dono se jo raste aayenge , agar mai unka sum kar dunga to mujhe total
    // ways mil jayenge.

    public int findTargetSumWays(int[] nums, int n, int target) {

        if (n == 0)
            return target == 0 ? 1 : 0;

        int count = 0;
        count += findTargetSumWays(nums, n - 1, target - (nums[n - 1]));
        count += findTargetSumWays(nums, n - 1, target - (-nums[n - 1]));

        return count;

    }

    public int findTargetSumWays(int[] nums, int target) {

        int sum = 0;
        for (int ele : nums)
            sum += ele;

        if (target > sum || target < -sum) // Agar target he bada hai total sum se to kabhi bhi achieve ho he nhi
                                           // ppayega.
                                           // Aur agar mera target chota hai total -ve of sum se, to bhi kabhi bhi
                                           // target achieve ho he nhi payega.
            return 0;

        return findTargetSumWays(nums, nums.length, target);
    }

    // ! Memoisation :

    // We know that -sum<=target<=sum. So the target can lie between sum and -sum.
    // Now consider all of the points are placed in number line, with -sum at
    // extreme left and sum at extreme right point with target in between.

    // Now we will shift the origin to 0.
    // so to make -sum to 0, we have to add sum to it. So everything will be shifted
    // `by sum.

    // Now new target becomes target + sum.

    // And now the target will range from 0<=target<=2*sum.

    // Now we can apply our dp since we have removed the -ve index part, now the
    // array index will never be -ve.

    public int findTargetSumWays_memo(int[] nums, int target, int sum, int[][] dp, int n) {

        if (n == 0)
            return dp[n][target] = (target == sum ? 1 : 0); // Originally base case mai hum target ==0 ka check lagate
                                                            // hain, per kuunki humne origin ko sum se shift kiya hai to
                                                            // ab target ==sum ka check lagega.
        if (dp[n][target] != -1)
            return dp[n][target];
        int count = 0;
        if (target - nums[n - 1] >= 0)
            count += findTargetSumWays_memo(nums, target - nums[n - 1], sum, dp, n - 1);
        if (target - (-nums[n - 1]) <= 2 * sum)
            count += findTargetSumWays_memo(nums, target - (-nums[n - 1]), sum, dp, n - 1);

        return dp[n][target] = count;
    }

    public int findTargetSumWays_memo(int[] nums, int target) {
        int n = nums.length;
        int sum = 0;
        for (int ele : nums)
            sum += ele;

        if (target > sum || target < -sum)
            return 0;

        int[][] dp = new int[n + 1][2 * sum + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return findTargetSumWays_memo(nums, target + sum, sum, dp, n);
    }

    // ! Memoisation : 2nd Method

    // Now in the above method, hum target ko sum ke equal leke gaye to hume if ke
    // checks add karne pade taki target range se bahar na jaye.

    // Per agar hum sum ko target ki taraf leke jeyein, to sum kabhi bhi out of
    // range jayega he nhi, kyunki max hum sum mai sum he add kar sakte hain, aur
    // humari dp sum*2+1 ki hai. To koi issue he nhi aayega.

    public int findTargetSumWays_memo2(int[] nums, int target, int sum, int[][] dp, int n) {

        if (n == 0)
            return dp[n][sum] = (target == sum ? 1 : 0);
        if (dp[n][sum] != -1)
            return dp[n][sum];
        int count = 0;
        count += findTargetSumWays_memo2(nums, target, sum + nums[n - 1], dp, n - 1); // Taking sum towards the target
        count += findTargetSumWays_memo2(nums, target, sum - nums[n - 1], dp, n - 1);

        return dp[n][sum] = count;
    }

    public int findTargetSumWays_memo2(int[] nums, int target) {
        int n = nums.length;
        int sum = 0;
        for (int ele : nums)
            sum += ele;

        if (target > sum || target < -sum)
            return 0;

        int[][] dp = new int[n + 1][2 * sum + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return findTargetSumWays_memo2(nums, target + sum, sum, dp, n);
    }

    // b <================ Partition to K Equal Sum Subsets ==========>
    // https://leetcode.com/problems/partition-to-k-equal-sum-subsets/description/

    // Simple recursive call lagake k ko 0 tak leke jana hai. agar k==0 hua, to
    // matlab ho sakte hain equally devide.
    public boolean canPartitionKSubsets(int[] nums, int k, int tar, int ssf, int idx, boolean[] vis) {

        if (k == 0)
            return true;

        if (ssf > tar)
            return false;

        if (ssf == tar) // agar sum so far target ke equal hua to k-1 ke liye call lagado aur idx ko 0
                        // pass karo taki wo starting se jo element use nhi hue hain unhe bhi use kar
                        // paye.
            return canPartitionKSubsets(nums, k - 1, tar, 0, 0, vis);

        boolean res = false;
        for (int i = idx; i < nums.length; i++) {
            if (vis[i])
                continue;
            vis[i] = true; // ye mark isiliye taki arrray ke har ek element ko bas ek he bar use kar paye
                           // answer banane ke liye.
            res = res || canPartitionKSubsets(nums, k, tar, ssf + nums[i], i + 1, vis);
            vis[i] = false;
        }

        return res;
    }

    public boolean canPartitionKSubsets(int[] nums, int k) {

        int sum = 0;
        for (int ele : nums)
            sum += ele;
        if (sum % k != 0) // agar sum puri tarah devide nhi ho pata k se, to array kabhi bhi devide to ho
                          // he nhi payega.
            return false;
        int tar = sum / k;
        boolean[] vis = new boolean[nums.length];
        return canPartitionKSubsets(nums, k, tar, 0, 0, vis);
    }

    // B <============== Knight Probability in ChessBoard =====================>
    // https://leetcode.com/problems/knight-probability-in-chessboard/description/

    static int dirX[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
    static int dirY[] = { 1, 2, 2, 1, -1, -2, -2, -1 };

    // ! Recursion :

    // Maine sare 8 direction se pucha ki tum mujhe apne board ke andar rehne ki
    // probability do. Ab agar mai unka sum kar dunga to mujhe merese un 8 direction
    // ke andar rehne ki probability mil jaayegi. Ab jo mere naam ka move liya hoga,
    // ` uski probability hogi sum/8.

    public double knightProbability_recu(int n, int k, int sr, int sc) {

        if (k == 0)
            return 1.0;

        double count = 0;
        for (int d = 0; d < 8; d++) {
            int r = sr + dirX[d];
            int c = sc + dirY[d];

            if (r >= 0 && c >= 0 && c < n && r < n) {
                count += knightProbability_recu(n, k - 1, r, c);
            }
        }

        return count / 8;
    }

    public double knightProbability_rec(int n, int k, int row, int column) {
        return knightProbability_recu(n, k, row, column);
    }

    // ! Memoisation :
    // # Curious case of 3-d dp since three variables are changing in recursion.
    // k, row. column;

    public double knightProbability_memo(int n, int k, int sr, int sc, double[][][] dp) {

        if (k == 0)
            return dp[k][sr][sc] = 1.0;

        if (dp[k][sr][sc] != 0.0)
            return dp[k][sr][sc];

        double count = 0;
        for (int d = 0; d < 8; d++) {
            int r = sr + dirX[d];
            int c = sc + dirY[d];

            if (r >= 0 && c >= 0 && c < n && r < n) {
                count += knightProbability_memo(n, k - 1, r, c, dp);
            }
        }

        return dp[k][sr][sc] = count / 8.0;
    }

    public double knightProbability_memo(int n, int k, int row, int column) {
        double[][][] dp = new double[k + 1][n + 1][n + 1];
        return knightProbability_memo(n, k, row, column, dp);
    }

    // ! Observation and Tabulation :

    // # Important point to note :
    // Humne sr aur sc loop ko 0 se start kiya hai. kyunki direction array kahin bhi
    // leke jaa sakta hai, therefore mujhe sari states ko evaluate karna compulsory
    // hai.

    // ? Therefore here, the memoisation works far much better than tabulation.

    public double knightProbability_tabu(int N, int K, int SR, int SC, double[][][] dp) {

        for (int k = 0; k <= K; k++) {
            for (int sr = 0; sr <= N; sr++) {
                for (int sc = 0; sc <= N; sc++) {
                    if (k == 0) {
                        dp[k][sr][sc] = 1.0;
                        continue;
                    }

                    double count = 0;
                    for (int d = 0; d < 8; d++) {
                        int r = sr + dirX[d];
                        int c = sc + dirY[d];

                        if (r >= 0 && c >= 0 && c < N && r < N) {
                            count += dp[k - 1][r][c]; // knightProbability_memo(n, k - 1, r, c, dp);
                        }
                    }

                    dp[k][sr][sc] = count / 8.0;
                }
            }
        }

        return dp[K][SR][SC];
    }

    public double knightProbability(int n, int k, int row, int column) {
        double[][][] dp = new double[k + 1][n + 1][n + 1];
        return knightProbability_tabu(n, k, row, column, dp);
    }

    // b <================ Out of Boundary Path ====================>
    // https://leetcode.com/problems/out-of-boundary-paths/description/

    // ! Recursion :

    // # Logic is same as above, with a little change.

    int[][] dir = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
    int mod = (int) (1e9) + 7;

    public int findPaths_recu(int m, int n, int maxMove, int sr, int sc) {

        if (maxMove == 0)
            return 0;

        int count = 0;

        for (int d = 0; d < 4; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < m && c < n) {
                count = count % mod + findPaths_recu(m, n, maxMove - 1, r, c) % mod;
            } else {
                count += 1;
            }
        }

        return count % mod;
    }

    public int findPaths_rec(int m, int n, int maxMove, int startRow, int startColumn) {
        return findPaths_recu(m, n, maxMove, startRow, startColumn);
    }

    // ! Memoisation :

    public long findPaths_memo(int m, int n, int maxMove, int sr, int sc, long[][][] dp) {
        if (maxMove == 0)
            return dp[maxMove][sr][sc] = 0;

        if (dp[maxMove][sr][sc] != -1) // the mistake that you did was to not fill the dp initially with -1 since 0 can
                                       // `be part of the answer
            return dp[maxMove][sr][sc];

        long count = 0;

        for (int d = 0; d < 4; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < m && c < n) {
                count = count % mod + findPaths_memo(m, n, maxMove - 1, r, c, dp) % mod;
            } else {
                count = count % mod + 1;
            }
        }

        return dp[maxMove][sr][sc] = count % mod;
    }

    public int findPaths_mem(int m, int n, int maxMove, int startRow, int startColumn) {
        long[][][] dp = new long[maxMove + 1][m + 1][n + 1];
        for (long[][] move : dp) {
            for (long[] row : move) {
                Arrays.fill(row, -1);
            }
        }

        long ans = findPaths_memo(m, n, maxMove, startRow, startColumn, dp);
        return (int) ans;
    }

    // ! Observation and Tabulation :

    public long findPaths_tabu(int M, int N, int MAXMOVE, int SR, int SC, long[][][] dp) {

        for (int maxMove = 0; maxMove <= MAXMOVE; maxMove++) {
            for (int sr = 0; sr <= M; sr++) {
                for (int sc = 0; sc <= N; sc++) {
                    if (maxMove == 0) {
                        dp[maxMove][sr][sc] = 0;
                        continue;
                    }

                    long count = 0;

                    for (int d = 0; d < 4; d++) {
                        int r = sr + dir[d][0];
                        int c = sc + dir[d][1];

                        if (r >= 0 && c >= 0 && r < M && c < N) {
                            count = count % mod + dp[maxMove - 1][r][c];
                        } else {
                            count = count % mod + 1;
                        }
                    }
                    dp[maxMove][sr][sc] = count % mod;
                }
            }
        }

        return dp[MAXMOVE][SR][SC];
    }

    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        long[][][] dp = new long[maxMove + 1][m + 1][n + 1];
        for (long[][] move : dp) {
            for (long[] row : move) {
                Arrays.fill(row, -1);
            }
        }

        long ans = findPaths_tabu(m, n, maxMove, startRow, startColumn, dp);
        return (int) ans;
    }

    // b <=========== Mobile numeric keypad ==================>
    // https://practice.geeksforgeeks.org/problems/mobile-numeric-keypad5456/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // # Is question ko aise soch ki tereko N digit ke numbers banane hain har kisi
    // # digit se start hone wale jo ki mobile phone ki mapping mai hote hain.

    // To simply iske liye direction wale loop lagaya.

    // ! Recursion :

    public long getCount_rec(int sr, int sc, char[][] grid, int[][] dir, int N) {
        // Your code goes here
        if (N == 0)
            return 1;

        long count = 0;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] != '#' && grid[r][c] != '*') {
                count += getCount_rec(r, c, grid, dir, N - 1);
            }
        }

        return count;
    }

    public long getCount_rec(int N) {

        char[][] grid = { { '1', '2', '3' },
                { '4', '5', '6' },
                { '7', '8', '9' },
                { '*', '0', '#' } };

        int[][] dir = { { 0, 0 }, { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } }; // Yahan pe {0,0} ko bhi rakha hai kyunki
                                                                              // if N>1, to number apne sath jud kar bhi
                                                                              // koi number bana sakta hai like "00" ,
                                                                              // "88" etc. Taki apne liye bhi ek call
                                                                              // lag jaye.

        int n = grid.length, m = grid[0].length;

        long count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char ch = grid[i][j];
                if (ch != '#' && ch != '*')
                    count += getCount_rec(i, j, grid, dir, N - 1); // yahan pe N -1 kiya taki is function mai uper if
                                                                   // statement na lagana pade. if(N==1) return 10;
            }
        }

        return count;

    }

    // ! Memoisation :

    public long getCount(int sr, int sc, char[][] grid, int[][] dir, int N, long[][][] dp) {
        // Your code goes here
        if (N == 0)
            return dp[N][sr][sc] = 1;

        if (dp[N][sr][sc] != -1)
            return dp[N][sr][sc];
        long count = 0;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] != '#' && grid[r][c] != '*') {
                count += getCount(r, c, grid, dir, N - 1, dp);
            }
        }

        return dp[N][sr][sc] = count;
    }

    public long getCount(int N) {

        char[][] grid = { { '1', '2', '3' },
                { '4', '5', '6' },
                { '7', '8', '9' },
                { '*', '0', '#' } };

        int[][] dir = { { 0, 0 }, { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

        int n = grid.length, m = grid[0].length;
        long[][][] dp = new long[N + 1][n + 1][m + 1];
        for (long[][] move : dp) {
            for (long[] d : move) {
                Arrays.fill(d, -1);
            }
        }
        long count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char ch = grid[i][j];
                if (ch != '#' && ch != '*')
                    count += getCount(i, j, grid, dir, N - 1, dp);
            }
        }
        return count;
    }

    // b <============ 221. Maximal Square ===============>
    // https://leetcode.com/problems/maximal-square/description/

    // # Faith :

    // Maine har ek 1 ko kaha ki ja jo tu apna maximum square bana sakta hai, tu
    // ` uska length leke aaja. Mai sab mai se max nikalke area return kardunga.

    // ? Ek square banane ke liye, kisi bhi 1 ke right side, right bottom diagonal
    // ? aur bottom mai 1 hona chahiye. Agar kahin pe bhi 0 milta hai, to wo kabhi
    // ? bhi square to nhi hoga. Per kyunki 1 akela bhi ek square hai to hum teeno
    // ? direction ka min nikalke + 1 karke return kar denge.

    public static int dfs(char[][] grid, int[][] dir, int sr, int sc, int[][] dp) {

        if (dp[sr][sc] != -1)
            return dp[sr][sc];
        int size = (int) 1e9;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] == '1') {
                size = Math.min(size, dfs(grid, dir, r, c, dp));
            } else { // Agar koi call grid se bahar jati hai ya 0 ko jati hai, to wahan se to 0
                     // return hoga.
                size = Math.min(size, 0);
            }
        }
        return dp[sr][sc] = size + 1; // +1 since agar dfs mai aaya hoga, to wo 1 se aaya hoga, to wo atleast single
                                      // he ek square hoga
    }

    public int maximalSquare(char[][] grid) {

        int n = grid.length, m = grid[0].length;
        int[][] dp = new int[n][m];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        int maxArea = -(int) 1e9;
        int[][] dir = { { 0, 1 }, { 1, 1 }, { 1, 0 } };
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1')
                    maxArea = Math.max(maxArea, dfs(grid, dir, i, j, dp));
            }
        }
        return maxArea == -(int) 1e9 ? 0 : maxArea * maxArea;
    }
}
