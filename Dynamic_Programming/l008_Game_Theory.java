import java.util.Arrays;

public class l008_Game_Theory {

    // b <================== Super Egg Drop ===================>
    // https://leetcode.com/problems/super-egg-drop/

    // # Tried all the methods.
    // # All gave TLE or MLE except for binary search.

    // To kiya kya ??
    // Hume wo critcal floor diya nhi hai. Hume aise min number of moves batane hai
    // ki wo critical floor ki har value ko satisfy kare.

    // # Faith :

    // Maine har ek floor se egg ko drop karke dekha

    // To egg break bhi kar sakta hai aur nhi bhi.
    // To maine kaha ki jo bache hue floors hain
    // wo apne min move lake dede.

    // Aur aisa maine har floor ke liye kiya.

    // ! REcursion :
    // TLE

    public int superEggDrop_recu(int eggs, int floors) {
        if (eggs == 1 || eggs == 0)
            // Agar egg he 1 bacha hai to hume har floor pe jake check karna hoga. Kyunki
            // critical floor ko bhi ho sakta hai. To hume sare floors pe jake check karnna
            // padega. Worst case ye hai ki critical floor ho he nam us case mai min moves
            // will be equal to floors.
            return eggs == 1 ? floors : 0;
        if (floors == 0 || floors == 1)
            return floors == 0 ? 0 : 1;

        int min = (int) 1e9;
        for (int floor = 1; floor <= floors; floor++) {

            int eggBreak = superEggDrop_recu(eggs - 1, floor - 1); // Agar egg berak hua to hume critical floor current
                                                                   // floor se nich milega.
            int eggNotBreak = superEggDrop_recu(eggs, floors - floor); // Agar egg break nhi hota to hume current floor
                                                                       // se uper critical floor milega.

            // Why floors - floor ?
            // Problem ko short kar diya. Manle pehle 100 floors the, ab tune agar 10th
            // floor se drop kiya aur wo nhi phuta, to problem reamins eggs for 100- 10 = 90
            // floors.

            int worstMoves = Math.max(eggBreak, eggNotBreak) + 1;
            // Why Math.max === >
            // Hume nhi pata ki egg break karega ya nhi karega. Ye pure luck hai.
            // To jahan pe bhi luck involved ho jaya hai, wahan pe hum worse condition leke
            // chalte hain, kyunki hum sure nhi hote hain ki konsa wala shi hai. Luck wala
            // factor yahan pe use karna tha.
            // To jo bhi eggBreak ya eggNotBreak mai se maximum aayega, to wo main maan ke
            // chalunga ki itne moves mai pata chal he jayega,

            min = Math.min(min, worstMoves);
            // kyunki hume question mai minimum number of moves that you need to determine
            // with certainty what the value of critical floor is.
            // To humne sare floor ke worstMoves ka min nikalke return kara hai.
        }

        return min;
    }

    public int superEggDrop_rec(int k, int n) {

        return superEggDrop_recu(k, n);
    }

    // ! Memoisation :
    // TLE
    public int superEggDrop_memo(int eggs, int floors, int[][] dp) {
        if (eggs == 1 || eggs == 0)
            return eggs == 1 ? floors : 0;
        if (floors == 0 || floors == 1)
            return floors == 0 ? 0 : 1;

        if (dp[eggs][floors] != (int) 1e9)
            return dp[eggs][floors];
        int min = (int) 1e9;
        for (int floor = 1; floor <= floors; floor++) {

            int eggBreak = superEggDrop_memo(eggs - 1, floor - 1, dp);
            int eggNotBreak = superEggDrop_memo(eggs, floors - floor, dp);

            int worstMoves = Math.max(eggBreak, eggNotBreak) + 1;
            min = Math.min(min, worstMoves);
        }

        return dp[eggs][floors] = min;
    }

    public int superEggDrop(int k, int n) {
        int[][] dp = new int[k + 1][n + 1];
        for (int[] d : dp)
            Arrays.fill(d, (int) 1e9);
        return superEggDrop_memo(k, n, dp);
    }

    // ! Memoisation with 3-d dp
    // 3-d dp == > Memory Limit Exceeded

    public int superEggDrop_memo(int eggs, int si, int floors, int[][][] dp) {
        if (eggs == 1 || eggs == 0)
            return eggs == 1 ? floors : 0;
        if (floors == 0 || floors == 1)
            return floors == 0 ? 0 : 1;

        if (dp[eggs][floors][si] != (int) 1e9)
            return dp[eggs][floors][si];
        int min = (int) 1e9;
        for (int floor = 1; floor <= floors; floor++) {

            int eggBreak = superEggDrop_memo(eggs - 1, 1, floor - 1, dp);
            int eggNotBreak = superEggDrop_memo(eggs, floor, floors - floor, dp);

            int worstMoves = Math.max(eggBreak, eggNotBreak) + 1;
            min = Math.min(min, worstMoves);
        }

        return dp[eggs][floors][si] = min;
    }

    public int superEggDrop_(int k, int n) {
        int[][][] dp = new int[k + 1][n + 1][n + 1];
        for (int[][] d : dp)
            for (int[] dd : d)
                Arrays.fill(dd, (int) 1e9);
        return superEggDrop_memo(k, 1, n, dp);
    }

    // ! Memoisation with Binary Search
    // Now using binary search

    public int superEggDrop_memo_BS(int eggs, int floors, int[][] dp) {
        if (eggs == 1 || eggs == 0)
            return eggs == 1 ? floors : 0;
        if (floors == 0 || floors == 1)
            return floors == 0 ? 0 : 1;

        if (dp[eggs][floors] != (int) 1e9)
            return dp[eggs][floors];
        int min = (int) 1e9;
        int low = 1, high = floors;
        while (low <= high) {

            int mid = (low + high) / 2;
            int eggBreak = superEggDrop_memo_BS(eggs - 1, mid - 1, dp);
            int eggNotBreak = superEggDrop_memo_BS(eggs, floors - mid, dp);

            int worstMoves = Math.max(eggBreak, eggNotBreak) + 1;
            min = Math.min(min, worstMoves);

            if (eggBreak < eggNotBreak) { // Agar egg break nhi kiya, to mai uper wali range mai search karunga next,
                                          // nhi to lower range in binary search.
                low = mid + 1;
            } else
                high = mid - 1;
        }
        return dp[eggs][floors] = min;
    }

    public int superEggDrop_BS(int k, int n) {
        int[][] dp = new int[k + 1][n + 1];
        for (int[] d : dp)
            Arrays.fill(d, (int) 1e9);
        return superEggDrop_memo_BS(k, n, dp);
    }

    // B <======================== Optimal Strategy For A Game ====================>
    // https://practice.geeksforgeeks.org/problems/optimal-strategy-for-a-game-1587115620/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // Kyunki bataya hai ki opponent is as clever as I am. To agar usko choice
    // milega to wo bhi max element he pick karega.

    // manle si ,ei initial index hai
    // Ab mai ya to si ko choose kar sakta hun ya to ei ko.

    // Maine si ko choose kiya aur range pass ki si + 1, ei.
    // Ab mere opponent ke pass do choice hain.
    // Ya to wo si + 1 wale ko pick karke aage si+2, ei range pass karega.
    // Main ab si+2 ya ei mai se koi pick karta aur mujh isse answer milta.

    // ya to wo ei wale ko choose karke si + 1, ei - 1 range pass karega.
    // Main ab si+1 ya ei - 1 mai se koi pick karta aur mujh isse answer milta.

    // Per Since opponent is as clever as I am, to dono call mai se jiska answer
    // sabse minimum aayega mere liye, opponent to whi chahega, therefore in dono
    // call ka min nikala.

    // My opponent always want his total to be max, therefore would want me to
    // recieve fewer one.
    // Jaise he uski bari aati hai , wo mere liye worst choose kar deta hai.

    // Same steps if I first choose ei.

    // # Faith

    // Maine kaha mujhe countMaximum max count lake dede mera uss array ke range
    // mai.
    //
    static long countMaximum(int arr[], int n, int si, int ei) {

        if (si > ei)
            return 0;
        int max = -(int) 1e9;

        long pickFirst = Math.min(countMaximum(arr, n, si + 2, ei), countMaximum(arr, n, si + 1, ei - 1)) + arr[si];
        long pickLast = Math.min(countMaximum(arr, n, si + 1, ei - 1), countMaximum(arr, n, si, ei - 2)) + arr[ei];

        long bestPick = Math.max(pickFirst, pickLast);
        return bestPick;
        // Your code here
    }

    static long countMaximum_(int arr[], int n) {
        // Your code here
        return countMaximum(arr, n - 1, 0, n - 1);
    }

    static long countMaximum_memo(int arr[], int n, int si, int ei, long[][] dp) {

        if (si > ei)
            return 0;

        if (dp[si][ei] != -1)
            return dp[si][ei];
        long pickFirst = Math.min(countMaximum_memo(arr, n, si + 2, ei, dp),
                countMaximum_memo(arr, n, si + 1, ei - 1, dp)) + arr[si];
        long pickLast = Math.min(countMaximum_memo(arr, n, si + 1, ei - 1, dp),
                countMaximum_memo(arr, n, si, ei - 2, dp)) + arr[ei];

        long bestPick = Math.max(pickFirst, pickLast);
        return dp[si][ei] = bestPick;
        // Your code here
    }

    static long countMaximum(int arr[], int n) {
        // Your code here
        long[][] dp = new long[n][n];
        for (long[] d : dp)
            Arrays.fill(d, -1);
        return countMaximum_memo(arr, n - 1, 0, n - 1, dp);
    }

    // b <=============== Predict the Winner ==================>
    // https://leetcode.com/problems/predict-the-winner/description/

    // ! Memoisation :
    static int countMaximum_mp(int arr[], int n, int si, int ei, int[][] dp) {

        if (si > ei)
            return 0;

        if (dp[si][ei] != -1)
            return dp[si][ei];
        int pickFirst = Math.min(countMaximum_mp(arr, n, si + 2, ei, dp), countMaximum_mp(arr, n, si + 1, ei - 1, dp))
                + arr[si];
        int pickLast = Math.min(countMaximum_mp(arr, n, si + 1, ei - 1, dp), countMaximum_mp(arr, n, si, ei - 2, dp))
                + arr[ei];

        int bestPick = Math.max(pickFirst, pickLast);
        return dp[si][ei] = bestPick;
        // Your code here
    }

    public boolean PredictTheWinner(int[] nums) {
        int sum = 0;
        for (int e : nums)
            sum += e;
        int n = nums.length;
        int[][] dp = new int[n][n];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        int myMaxPick = countMaximum_mp(nums, n, 0, n - 1, dp);
        int opponentPick = sum - myMaxPick;
        return myMaxPick >= opponentPick;

    }

    // b <================= Stone Game ===================>
    // https://leetcode.com/problems/stone-game/

    // ! Recursion :

    static int countMaximum_rs(int arr[], int n, int si, int ei) {

        if (si > ei)
            return 0;

        int pickFirst = Math.min(countMaximum_rs(arr, n, si + 2, ei), countMaximum_rs(arr, n, si + 1, ei - 1))
                + arr[si];
        int pickLast = Math.min(countMaximum_rs(arr, n, si + 1, ei - 1), countMaximum_rs(arr, n, si, ei - 2)) + arr[ei];

        int bestPick = Math.max(pickFirst, pickLast);
        return bestPick;
        // Your code here
    }

    public boolean stoneGame_(int[] nums) {
        int sum = 0;
        for (int e : nums)
            sum += e;
        int n = nums.length;
        int myMaxPick = countMaximum_rs(nums, n, 0, n - 1);
        int opponentPick = sum - myMaxPick;
        return myMaxPick >= opponentPick;

    }

    // ! Memoisation :

    static int countMaximum_m(int arr[], int n, int si, int ei, int[][] dp) {

        if (si > ei)
            return 0;

        if (dp[si][ei] != -1)
            return dp[si][ei];
        int pickFirst = Math.min(countMaximum_m(arr, n, si + 2, ei, dp), countMaximum_m(arr, n, si + 1, ei - 1, dp))
                + arr[si];
        int pickLast = Math.min(countMaximum_m(arr, n, si + 1, ei - 1, dp), countMaximum_m(arr, n, si, ei - 2, dp))
                + arr[ei];

        int bestPick = Math.max(pickFirst, pickLast);
        return dp[si][ei] = bestPick;
        // Your code here
    }

    public boolean stoneGame(int[] nums) {
        int sum = 0;
        for (int e : nums)
            sum += e;
        int n = nums.length;
        int[][] dp = new int[n][n];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        int myMaxPick = countMaximum_m(nums, n, 0, n - 1, dp);
        int opponentPick = sum - myMaxPick;
        return myMaxPick >= opponentPick;

    }

}
