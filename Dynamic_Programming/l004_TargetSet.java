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
}
