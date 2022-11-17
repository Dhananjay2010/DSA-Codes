import java.util.Arrays;

public class l001_twoPointerSet {

    // ! Important point to follow while solving dp

    // # 1. Faith
    // # 2. Recursive Tree
    // # 3. Recursive Code ==> Memoization
    // # 4. Observation
    // # 5. Tabulation
    // # 6. Optimization

    // ? Jahan pe bhi recursive tree mai repetetive calls lagti hain, jisme tree
    // ? aage same rehta hai, wahan pe hum dp laga sakte hain

    // Jahan pe bhi recursion mai return fire hoga, us value ko kahin pe store kiya
    // jayega.
    // dp ki length kya honi chahiye == > mai apne recursive tree mai sabse min
    // value aur max value dekhni hai kitni aa rahi hai.

    // To mujhe anpe array ka size aisa rakhna padega ki mai dono min se lekar max
    // tak mai index ki tarah use kar sakun ke unki value store kar paun kahun pe.
    // To mera total dp ka size hoga (max -min + 1);

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
    }

    // b <====================== Fibonacci =================>
    // ! Recursion :

    // Faith : maine ye mana ki mujhe n-1 aur n-2 ki value mil jayegi. To mai bas
    // dono ko add karke return kar dunga.
    public static int fibo_rec(int n) {
        if (n <= 1)
            return n;
        int ans = fibo_rec(n - 1) + fibo_rec(n - 2);

        return ans;
    }

    // ! Memoisation :

    // Faith : maine ye mana ki mujhe n-1 aur n-2 ki value mil jayegi. To mai bas
    // dono ko add karke return kar dunga aur unke specific index mai uski ans ko
    // dubara store bhi kar dunga, taki mujhe unko dubara na calculate karna pade,
    // hence saving a lot of time.

    // ? Important point to note : dp ki default value wo nhi honi chahiye jo aapke
    // ? answer ka most frequent part bane.

    public static int fibo_memo(int n, int[] dp) {
        if (n <= 1)
            return dp[n] = n;

        if (dp[n] != 0) // Agar koi value pehle se evaluated hai, to return kardo, kyunki use dubara
                        // evaluate karne ki jaroorat nhi hai
            return dp[n];
        int ans = fibo_memo(n - 1, dp) + fibo_memo(n - 2, dp);

        return dp[n] = ans;
    }

    // ? In dp, when we store a value at the base case, we are actually telling
    // ? ourselves that we have reached the destination. Wo ko one humne store kiya
    // ? wo achievement ka one hai, joki denote karta hai ki hume ek path mila hai
    // ? wahan tak pahunchne ka.

    // ! Observation and Tabulation :

    // # To find the tabulation, we have do observation in the memoisation answer
    // # and resolve the dependency.

    // value : 0,1,1,2,3,5,8,13,21
    // index : 0,1,2,3,4,5,6,7, 8

    // # So we see that to calculate a value at an index, we will be needing
    // # previous two indexes value, so the dependency is from left to right.
    // # Means that first the left values must be calculated in order to calculate
    // # the the right ones.

    public static int fibo_tabu(int N, int[] dp) { // to keep the varaible same as in memoisation, we have used N as the
                                                   // value to be calculated and n as the loop iterator.
        for (int n = 0; n <= N; n++) {
            if (n <= 1) {
                dp[n] = n;
                continue; // The return statement in memoisation converts to continue in tabulation
            }

            // if (dp[n] != 0) // no need of this is tabulation.
            // return dp[n];

            int ans = dp[n - 1] + dp[n - 2]; // fibo_memo(n - 1, dp) + fibo_memo(n - 2, dp);

            dp[n] = ans; // no need for continue as the loop is ending here.
        }
        return dp[N];
    }

    // ! Optimization :

    public static int fibo_opti(int N) { // for N greater than 1, otherwise add a if statement for 0 and 1.
        int a = 0, b = 1;
        for (int i = 2; i <= N; i++) {
            int sum = a + b;
            a = b;
            b = sum;
        }
        return b;
    }

    // ! Important point : To calculate the time complexity, always use the
    // ! tabulation code.

    // # The Real life example is our brain. We store everything that we learn and
    // # then again start form that point. But what if the brain data vanishes after
    // # you sleep. You have to relearn again from the start. That is dp.

    // # Same example is while we travel to a new place. At first we find it a
    // # little tough to travel through that place. but When we start doing this
    // # repeatedly, we start to remember the routes and then it is easy to travel.

    public static void fibo(int n) {
        int[] dp = new int[n + 1]; // dp ka size is n+1. Why ?????

        // # lowest index in which value will be stored will be 0 and the largest value
        // # of index where the value will be stored is n, therefore we have to include
        // # both of the indexes so the total length becomes (n-0+1) i.e. n+1;

    }

    // b <==================== Maze Path ===============================>

    // # To find total path from source to destination.

    // Started from sr and sc and reaching to er and ec.

    // Faith : maine teeno direction se unke source tak ke jane ka number of path
    // mange. ab mai agar teeno ko add kar dunga to mujhe mere se source tak ke sare
    // path mil jayenge.

    // ! Recursion :
    public static int mazePath_rec(int sr, int sc, int er, int ec, int[][] dir, int[][] maze) {

        if (sr == er && sc == ec)
            return 1;

        int count = 0;
        for (int d = 0; d < dir.length; d++) {

            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < maze.length && c < maze[0].length) {
                count += mazePath_rec(r, c, er, ec, dir, maze);
            }
        }

        return count;
    }

    // ! Memoisation :

    public static int mazePath_memo(int sr, int sc, int er, int ec, int[][] dir, int[][] maze, int[][] dp) {

        if (sr == er && sc == ec)
            return dp[sr][sc] = 1;

        if (dp[sr][sc] != 0)
            return dp[sr][sc];

        int count = 0;
        for (int d = 0; d < dir.length; d++) {

            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < maze.length && c < maze[0].length) {
                count += mazePath_memo(r, c, er, ec, dir, maze, dp);
            }
        }

        return dp[sr][sc] = count;
    }

    // ! Observation and Tabulation :

    // # For observation, just pick a point and from that point, make all the
    // # recursive call. Then see how the answer of that point is dependent on other
    // # points. That's how you will get that in which direction we have to start
    // # our loop.

    // 13 5 1
    // 5 3 1
    // 1 1 1

    // For example here is the answer that we got from memoisation. What we will do
    // is we will pick a point like (1,1) who has a answer of 3. Now make a tree
    // diagram for those three directions recursive call, which will point to (1,2),
    // (2,2) and (2,1) respectivelya and if we sum the values of these three points,
    // it will add up to 3.

    // # Hence there will be two loops. First for column and second for row.

    public static int mazePath_tabu(int SR, int SC, int ER, int EC, int[][] dir, int[][] maze, int[][] dp) {

        for (int sr = ER; sr >= SR; sr--) {
            for (int sc = EC; sc >= SC; sc--) {
                if (sr == ER && sc == EC) {
                    dp[sr][sc] = 1;
                    continue;
                }
                int count = 0;
                for (int d = 0; d < dir.length; d++) {

                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];

                    if (r >= 0 && c >= 0 && r < maze.length && c < maze[0].length) {
                        count += dp[r][c]; // mazePath_memo(r, c, er, ec, dir, maze, dp);
                    }
                }

                dp[sr][sc] = count;
            }
        }

        return dp[SR][SC];

    }

    public static int mazePathInfiJump_tabu(int SR, int SC, int ER, int EC, int[][] dir, int[][] maze, int[][] dp) {

        for (int sr = ER; sr >= SR; sr--) {
            for (int sc = EC; sc >= SC; sc--) {
                if (sr == ER && sc == EC) {
                    dp[sr][sc] = 1;
                    continue;
                }
                int count = 0;
                for (int d = 0; d < dir.length; d++) {

                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    // This one is better since mai kahin jane se pehle check karta hun ki wo point
                    // valid hai ki nhi, to yeh bahut better hai
                    while (r >= 0 && c >= 0 && r < maze.length && c < maze[0].length) { // # Inner radius loop in a
                                                                                        // # different way
                        count += dp[r][c]; // mazePath_memo(r, c, er, ec, dir, maze, dp);
                        r += dir[d][0];
                        c += dir[d][1];
                    }
                }
                dp[sr][sc] = count;
            }
        }
        return dp[SR][SC];
    }

    public static void mazePath() {
        int n = 3;
        int[][] dir = { { 0, 1 }, { 1, 1 }, { 1, 0 } };
        int[][] maze = new int[n][n];

        int[][] dp = new int[n][n];
        mazePath_tabu(0, 0, n - 1, n - 1, dir, maze, dp);

        display2D(dp);
    }

    // b <===================== Climbing Stairs =======================>
    // https://leetcode.com/problems/climbing-stairs/

    // ! Recursion :

    public static int climbStairs_rec(int n) {

        if (n == 0)
            return 1;
        return climbStairs_rec(n - 1) + (n - 2 >= 0 ? climbStairs_rec(n - 2) : 0);
    }

    // ! Memoisation :

    public static int climbStairs_memo(int n, int[] dp) {

        if (n == 0)
            return dp[n] = 1;

        if (dp[n] != 0)
            return dp[n];
        return dp[n] = climbStairs_memo(n - 1, dp) + (n - 2 >= 0 ? climbStairs_memo(n - 2, dp) : 0);

    }

    public int climbStairs(int n) {
        int[] dp = new int[n - 0 + 1];
        return climbStairs_memo(n, dp);
    }

    // ! Observation and Tabulation :

    public static int climbStairs_tabu(int N, int[] dp) {

        for (int n = 0; n <= N; n++) {

            if (n == 0) {
                dp[n] = 1;
                continue;
            }
            dp[n] = dp[n - 1] + (n - 2 >= 0 ? dp[n - 2] : 0);// climbStairs_memo(n - 1, dp) + (n - 2 >= 0
                                                             // ` ? climbStairs_memo(n - 2, dp) : 0);
        }
        return dp[N];
    }

    // ! Optimization :

    public static int climbStairs_opti(int n) {
        if (n <= 1)
            return 1;
        int a = 1, b = 1;
        for (int i = 2; i <= n; i++) {
            int sum = a + b;
            a = b;
            b = sum;
        }

        return b;
    }

    // b <============== Min Cost Climbing Stairs ====================>
    // https://leetcode.com/problems/min-cost-climbing-stairs/

    // ! Recursion :

    // Faith : Agar mai 4 pe hun, to maine 3(n-1) ko bola ki tu merepe jitni min
    // cost ke sath aa sakta hai tu aaja aur maine similarly 2(n-1) ko bola ki tu
    // merepe jitni min cost ke sath aa sakta hai to tu aaja. Ab tum dono se jo
    // merepe min cost ke sath aayega, whi merepe aane ki min cost hogi. Aur mai
    // agar usme apni cost add kardun, to wo cost merese jisne mujhe call kiya hai,
    // wahan jane ki min cost hogi

    public int minCostClimbingStairs_rec(int[] cost, int n) {
        if (n == 0 || n == 1)
            return cost[n];

        int minCostOneStep = minCostClimbingStairs_rec(cost, n - 1);
        int minCostTwoStep = n - 2 >= 0 ? minCostClimbingStairs_rec(cost, n - 2) : (int) (1e9);

        int min = Math.min(minCostOneStep, minCostTwoStep);

        return min + cost[n];
    }

    public int minCostClimbingStairs_rec_(int[] cost) {

        // Manle mera cost array cost = [1,100,1,1,1,100,1,1,100,1] hai. To isme total
        // 10 elements hai(0 to 9); Ab jo mujse answer manga hai wo manga hai 10 ka. To
        // isiliye, 10 ki jo min cost hogi wo hogi Math.min(9 ki min cost, 8 ki min
        // cost).

        // Math.min because cannot send n=10 in the function as it will give out of
        // `bound error for index 10.
        return Math.min(minCostClimbingStairs_rec(cost, cost.length - 1),
                minCostClimbingStairs_rec(cost, cost.length - 2));
    }

    // ! Memoisation :
    public int minCostClimbingStairs_memo(int[] cost, int n, int[] dp) {
        if (n == 0 || n == 1)
            return dp[n] = cost[n];

        if (dp[n] != -1)
            return dp[n];
        int minCostOneStep = minCostClimbingStairs_memo(cost, n - 1, dp);
        int minCostTwoStep = n - 2 >= 0 ? minCostClimbingStairs_memo(cost, n - 2, dp) : (int) (1e9);

        int min = Math.min(minCostOneStep, minCostTwoStep);

        return dp[n] = min + cost[n];
    }

    public int minCostClimbingStairs_memo_(int[] cost) {

        int[] dp = new int[cost.length];
        Arrays.fill(dp, -1);
        return Math.min(minCostClimbingStairs_memo(cost, cost.length - 1, dp),
                minCostClimbingStairs_memo(cost, cost.length - 2, dp));
    }

    // ! Observation and Tabulation :

    public int minCostClimbingStairs_tabu(int[] cost, int N, int[] dp) {

        for (int n = 0; n <= N; n++) {
            if (n == 0 || n == 1) {
                dp[n] = cost[n];
                continue;
            }
            int minCostOneStep = dp[n - 1]; // minCostClimbingStairs_memo(cost, n - 1, dp);
            int minCostTwoStep = n - 2 >= 0 ? dp[n - 2] : (int) (1e9); // minCostClimbingStairs_memo(cost, n - 2, dp) :
                                                                       // (int) (1e9);

            int min = Math.min(minCostOneStep, minCostTwoStep);

            dp[n] = min + cost[n];
        }

        return Math.min(dp[N], dp[N - 1]);
    }

    public int minCostClimbingStairs_tabu_(int[] cost) {
        int[] dp = new int[cost.length];
        Arrays.fill(dp, -1);
        return minCostClimbingStairs_tabu(cost, cost.length - 1, dp);
    }

    // b<============ Board Path ======================>

    // Given a dice and its move in a array, find out number of ways to get from
    // starting point to end point.

    // ! Recursion :
    public static int boardPath_rec(int sp, int ep, int[] dice) {

        if (sp == ep)
            return 1;

        int count = 0;
        for (int i = 0; i < dice.length; i++) {
            int diceMove = dice[i];
            if (diceMove + sp <= ep)
                count += boardPath_rec(sp + diceMove, ep, dice);
        }

        return count;
    }

    // ! Memoisation :
    public static int boardPath_memo(int sp, int ep, int[] dice, int[] dp) {

        if (sp == ep)
            return dp[sp] = 1;

        if (dp[sp] != -1)
            return dp[sp];
        int count = 0;
        for (int i = 0; i < dice.length; i++) {
            int diceMove = dice[i];
            if (diceMove + sp <= ep)
                count += boardPath_memo(sp + diceMove, ep, dice, dp);
        }

        return dp[sp] = count;
    }

    // ! Tabulation :

    public static int boardPath_tabu(int SP, int EP, int[] dice, int[] dp) {

        for (int sp = EP; sp >= SP; sp--) {
            if (sp == EP) {
                dp[sp] = 1;
                continue;
            }
            int count = 0;
            for (int i = 0; i < dice.length; i++) {
                int diceMove = dice[i];
                if (diceMove + sp <= EP)
                    count += dp[sp + diceMove];// boardPath_memo(sp + diceMove, ep, dice, dp);
            }

            dp[sp] = count;
        }

        return dp[SP];
    }

    // b <============= Decode Ways =====================>
    // https://leetcode.com/problems/decode-ways/

    // Faith : Maine apne ch1 ko decode kiya, baki ki string apne aap ko decode
    // karke le aayegi. Similarly maine ch1+ch2 ko decode kiya. baki ki string apne
    // aap ko decode karke leyagi.

    // ! Recursion :

    // # In this type of recursion very hard to apply integer dp. We can use a
    // # HashMap and then optimize it.
    public int numDecodings_rec(String str) {

        if (str == "")
            return 1;

        int count = 0;

        char ch = str.charAt(0);
        if (ch == '0')
            return 0;

        count += numDecodings_rec(str.substring(1));

        if (str.length() >= 2) {
            String s = str.substring(0, 2);
            int num = Integer.parseInt(s);
            if (num >= 10 && num <= 26) {
                count += numDecodings_rec(str.substring(2));
            }
        }

        return count;
    }

    // ! Better Recursion :

    // # Now in this type of recursion, we have used the idx, and doing all
    // # calculation according to it, therefore reducing the computation time of
    // # substring and creation of a new string in the above recusion.

    // ? Hence this one is a far better one. Here now we can use integer array as dp
    // ? also.

    public int numDecodingIndex_recu(String str, int idx) {
        if (idx == str.length())
            return 1;

        int count = 0;
        char ch1 = str.charAt(idx);
        if (ch1 == '0')
            return 0;

        count += numDecodingIndex_recu(str, idx + 1);
        if (idx + 2 <= str.length()) {
            char ch2 = str.charAt(idx + 1);
            int num = (ch1 - '0') * 10 + (ch2 - '0'); // To get "15" as a number , ('1' - '0') * 10 + ('5' -'0')
            if (num >= 10 && num <= 26) {
                count += numDecodingIndex_recu(str, idx + 2);
            }
        }
        return count;
    }

    // ! Memoisation :

    public int numDecodingIndex_memo(String str, int idx, int[] dp) {
        if (idx == str.length())
            return dp[idx] = 1;

        if (dp[idx] != -1)
            return dp[idx];
        int count = 0;
        char ch1 = str.charAt(idx);
        if (ch1 == '0')
            return dp[idx] = 0;

        count += numDecodingIndex_memo(str, idx + 1, dp);
        if (idx + 2 <= str.length()) {
            char ch2 = str.charAt(idx + 1);
            int num = (ch1 - '0') * 10 + (ch2 - '0');
            if (num >= 10 && num <= 26) {
                count += numDecodingIndex_memo(str, idx + 2, dp);
            }
        }
        return dp[idx] = count;
    }

    public int numDecodings_memo(String s) {
        int[] dp = new int[s.length() - 0 + 1]; // Why s.length + 1 ? for string 11106, we have to store answer for 0th
                                                // index and 5th index so the total will be 5 - 0 + 1.

        Arrays.fill(dp, -1);
        return numDecodingIndex_memo(s, 0, dp);
    }

    // ! Observation and Tabulation :

    public int numDecodingIndex_tabu(String str, int IDX, int[] dp) {
        for (int idx = str.length(); idx >= 0; idx--) {
            if (idx == str.length()) {
                dp[idx] = 1;
                continue;
            }
            int count = 0;
            char ch1 = str.charAt(idx);
            if (ch1 == '0') {
                dp[idx] = 0;
                continue;
            }

            count += dp[idx + 1];// numDecodingIndex_memo(str, idx + 1, dp);
            if (idx + 2 <= str.length()) {
                char ch2 = str.charAt(idx + 1);
                int num = (ch1 - '0') * 10 + (ch2 - '0');
                if (num >= 10 && num <= 26) {
                    count += dp[idx + 2];// numDecodingIndex_memo(str, idx + 2, dp);
                }
            }
            dp[idx] = count;
        }
        return dp[IDX];
    }

    public int numDecodings_tabu(String s) {
        int[] dp = new int[s.length() - 0 + 1];
        Arrays.fill(dp, -1);
        return numDecodingIndex_tabu(s, 0, dp);
    }

    // ! Optimization :

    public static int numDecodings_opti(String s) {
        int a = 1, b = 0;
        for (int idx = s.length() - 1; idx >= 0; idx--) {
            char ch = s.charAt(idx);
            int sum = 0;
            if (ch != '0') {
                sum += a;

                if (idx < s.length() - 1) {
                    char ch1 = s.charAt(idx + 1);
                    int num = (ch - '0') * 10 + (ch1 - '0');
                    if (num <= 26)
                        sum += b;
                }
            }

            b = a;
            a = sum;
        }
        return a;
    }

    // ! Modulus in question :

    // Manle ki int ki range [-20,20] tak hai.
    // Ab agar maine 18 + 12 kiya to agar mai ise integer mai he store karta to ye
    // -10 deta answer deta.

    // To isiloye mai pehle is kisi bade mai store karunga. Jaise ki long mai store
    // kiya. Ab mai agar isme modulus lagaunga to ab ye shi aayegaa

    // Ab agar mujhe bola jata ki 18 se modulus karke answer chahiye
    // To mai 18 + 12 hone deta, joki 30 aata. mai use 18 se modulus karta. to 12
    // aata aur wo mera answer hota.

    // To pehle maine overflow hone diya, us overflow value ko kisi badi range mai
    // set kiya uske baad modulus operation lagaya

    // B <==================== Decode Ways II ==============>>
    // https://leetcode.com/problems/decode-ways-ii/

    // # How to apply mod : pehle mai overflow hone dunga tabhi to mai range mai
    // # answer laa paunga. Isiliye mai count aur dp aur jo uper wala function hai
    // # usko long mai liya hai taki mai answer nikal paun mod karne ke baad.

    // # To basically maine pehle answer nikala joki integer ki range mai overflow
    // # tha, to isiliye maine long mai store kiya use. ab kyunki mujhe, mod wala
    // # answer cahhiye tha, to maine use mod kar diya.

    // Everything is same as Decode Ways I but with a little twist.
    // So Recommend to solve it first then try to solve it.

    // ! Memoisation :

    static int mod = (int) (1e9) + 7;

    public long numDecodingIndex2_memo(String str, int idx, long[] dp) {
        if (idx == str.length())
            return dp[idx] = 1;

        if (dp[idx] != -1)
            return dp[idx];
        long count = 0;
        char ch1 = str.charAt(idx);
        if (ch1 == '0')
            return dp[idx] = 0;

        if (ch1 == '*')
            count = (count + 9 * numDecodingIndex2_memo(str, idx + 1, dp)) % mod;
        else
            count = (count + numDecodingIndex2_memo(str, idx + 1, dp)) % mod;

        if (idx + 2 <= str.length()) {
            // Now for two character string there can be four possibilities. **, n*, *n, nn
            // where n is a digit character.
            char ch2 = str.charAt(idx + 1);
            if (ch1 == '*' && ch2 == '*') {
                // Number that can be formed are 11,12,13,14,15,16,17,18,19,21,22,23,24,25,26.
                // Total 15 number, so ** can be replaced by any of the number therefore
                // multiplied by 15.
                count = (count + 15 * numDecodingIndex2_memo(str, idx + 2, dp)) % mod;
            } else if (ch1 == '*' && ch2 != '*') {
                // The *n type.
                // Number that can be formed are
                if (ch2 >= '0' && ch2 <= '6') { // 10,11,12,13,14,15,16 or 20,21,22,23,24,25,26.
                    // So if the n is 1, the number can be 11 or 21. Therefore multiplied by 2.
                    count = (count + 2 * numDecodingIndex2_memo(str, idx + 2, dp)) % mod;
                } else if (ch2 >= '7' && ch2 <= '9') { // 17,18,19
                    // if n is 7, number can be only 17. since 27 cannot be mapped.
                    count = (count + 1 * numDecodingIndex2_memo(str, idx + 2, dp)) % mod;
                }
            } else if (ch1 != '*' && ch2 == '*') {
                // The n* type
                // if n is 1, then the number can be 11,12,13,14,15,16,17,18,19
                // if n is 2, them the number can be 21,22,23,24,25,26.
                if (ch1 == '1' || ch1 == '2') {
                    int n = ch1 == '1' ? 9 : 6;
                    count = (count + n * numDecodingIndex2_memo(str, idx + 2, dp)) % mod;
                }
            } else {
                // The nn type.
                // The number has to be in range 10 to 26.
                int num = (ch1 - '0') * 10 + (ch2 - '0');
                if (num >= 10 && num <= 26) {
                    count = (count + numDecodingIndex2_memo(str, idx + 2, dp)) % mod;
                }
            }
        }
        return dp[idx] = count;
    }

    public int numDecodings(String s) {
        long[] dp = new long[s.length() + 1];
        Arrays.fill(dp, -1);
        long ans = numDecodingIndex2_memo(s, 0, dp);
        return (int) ans;
    }

    // ! Observation & Tabulation :
    // Its tabulation and memoisation will be same as Decode ways 1. Just *
    // conditions have been added.

    public long numDecodingIndex2_tabu(String str, int IDX, long[] dp) {

        for (int idx = str.length(); idx >= 0; idx--) {
            if (idx == str.length()) {
                dp[idx] = 1;
                continue;
            }

            long count = 0;
            char ch1 = str.charAt(idx);
            if (ch1 == '0') {
                dp[idx] = 0;
                continue;
            }

            if (ch1 == '*')
                count = (count + 9 * dp[idx + 1]) % mod;
            else
                count = (count + dp[idx + 1]) % mod;

            if (idx + 2 <= str.length()) {
                char ch2 = str.charAt(idx + 1);
                if (ch1 == '*' && ch2 == '*') {
                    count = (count + 15 * dp[idx + 2]) % mod;
                } else if (ch1 == '*' && ch2 != '*') {
                    if (ch2 >= '0' && ch2 <= '6') {
                        count = (count + 2 * dp[idx + 2]) % mod;
                    } else if (ch2 >= '7' && ch2 <= '9') {
                        count = (count + 1 * dp[idx + 2]) % mod;
                    }
                } else if (ch1 != '*' && ch2 == '*') {
                    if (ch1 == '1' || ch1 == '2') {
                        int n = ch1 == '1' ? 9 : 6;
                        count = (count + n * dp[idx + 2]) % mod;
                    }
                } else {
                    int num = (ch1 - '0') * 10 + (ch2 - '0');
                    if (num >= 10 && num <= 26) {
                        count = (count + dp[idx + 2]) % mod;
                    }
                }
            }
            dp[idx] = count;
        }

        return dp[IDX];
    }

    // ! Optimization :

    // Its optimiaztion will be same as Decode ways I.

    // b<====================== Gold Mine Problem =====================>
    // https://practice.geeksforgeeks.org/problems/gold-mine-problem2608/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // ! Recursion :
    // Faith : Maine teeno direction ko bola ki tum jitna max gold leke aa sakte ho
    // leke aa jo. Tume jo sabse max hoga, usme mai gar apna gold add kar dunga to
    // merese start hua max gold nikal aayega.

    // Aur aisa maine har ek row ke liye kiya aur unme se max nikal ke return kar
    // diya.

    public static int maxGold_rec(int sr, int sc, int[][] grid, int[][] dir) {

        int max = 0;

        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length) {
                max = Math.max(max, (maxGold_rec(r, c, grid, dir)));
            }
        }

        return max + grid[sr][sc];
    }

    static int maxGoldrec(int n, int m, int M[][]) {

        int[][] dir = { { -1, 1 }, { 0, 1 }, { 1, 1 } };

        int max = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, maxGold_rec(i, 0, M, dir));
        }

        return max;
    }

    // ! Memoisation :

    public static int maxGold_memo(int sr, int sc, int[][] grid, int[][] dir, int[][] dp) {

        if (dp[sr][sc] != -1)
            return dp[sr][sc];
        int max = 0;

        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length) {
                max = Math.max(max, (maxGold_memo(r, c, grid, dir, dp)));
            }
        }

        return dp[sr][sc] = max + grid[sr][sc];
    }

    static int maxGoldMemo(int n, int m, int M[][]) {

        int[][] dir = { { -1, 1 }, { 0, 1 }, { 1, 1 } };
        int[][] dp = new int[n][m];
        for (int[] d : dp) {
            Arrays.fill(d, -1);
        }
        int max = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, maxGold_memo(i, 0, M, dir, dp));
        }

        return max;
    }

    // ! Tabulation and Observation :

    // We see that if we are standing on (1,1), it will be needing (0,2), (1,2)
    // ,(2,2). So therefore the loop sequence is such that it first calculate all
    // the rows of a column starting from the rightmost column.

    public static int maxGold_Tabu(int SR, int SC, int[][] grid, int[][] dir, int[][] dp) {

        for (int sc = grid[0].length - 1; sc >= 0; sc--) {
            for (int sr = grid.length - 1; sr >= 0; sr--) {
                int max = 0;
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];

                    if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length) {
                        max = Math.max(max, dp[r][c]);
                    }
                }

                dp[sr][sc] = max + grid[sr][sc];
            }
        }
        return dp[SR][SC];
    }

    static int maxGold(int n, int m, int M[][]) {

        int[][] dir = { { -1, 1 }, { 0, 1 }, { 1, 1 } };
        int[][] dp = new int[n][m];
        for (int[] d : dp) {
            Arrays.fill(d, -1);
        }
        int max = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, maxGold_Tabu(i, 0, M, dir, dp));
        }
        return max;
    }

    // b <================ Friends Paring ====================>
    // https://practice.geeksforgeeks.org/problems/friends-pairing-problem5425/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // ! Recursion :

    // b <<== Always draw the tree diagram and deduce the faith.This make recursion
    // b <<== easier.

    // static int mod = (int) 1e9 + 7;

    // Faith : Agar mera number "abc" hai, to 'a' single reh sakta hai ya wo 'b' ya
    // 'c' ke sath pair ho sakta hai. "ab" ya "ac".

    // To maine single wale ko bola ki tu mujhe apna count lake dede aur aur maine
    // pair wale ko bola ki tu mujhe apna count lake dede. Ab agar mai dono ko add
    // kar dunga, to mujhe mera answer mil jayega.

    // Ab main pair wale mai (n-1) se bhi multiply kiya hai. Wo isiliye kyunki jo
    // answer "ab" leke aayega, whi "ac" bhi leke aayega.

    // Bas baki simple recursion hai.
    public long countFriendsPairings_rec(int n) {
        // code here
        if (n == 0)
            return 1;

        long count = 0;
        count = (count + countFriendsPairings_rec(n - 1)) % mod;

        if (n - 2 >= 0) {
            count = (count + (n - 1) * countFriendsPairings_rec(n - 2)) % mod;
        }
        return count;
    }

    // ! Memoisation :

    public long countFriendsPairings_memo(int n, long[] dp) {
        // code here
        if (n == 0)
            return dp[n] = 1;

        if (dp[n] != -1)
            return dp[n];
        long count = 0;
        count = (count + countFriendsPairings_memo(n - 1, dp)) % mod;

        if (n - 2 >= 0) {
            count = (count + (n - 1) * countFriendsPairings_memo(n - 2, dp)) % mod;
        }

        return dp[n] = count;
    }

    public long countFriendsPairings_memo(int n) {

        long[] dp = new long[n + 1];
        Arrays.fill(dp, -1);
        return countFriendsPairings_memo(n, dp);
    }

    // ! Observation and Tabulation :

    public long countFriendsPairings_tabu(int N, long[] dp) {

        for (int n = 0; n <= N; n++) {

            // code here
            if (n == 0) {
                dp[n] = 1;
                continue;
            }

            long count = 0;
            count = (count + dp[n - 1]) % mod;

            if (n - 2 >= 0) {
                count = (count + (n - 1) * dp[n - 2]) % mod;
            }

            dp[n] = count;
        }
        return dp[N];
    }

    public long countFriendsPairings_tabu(int n) {

        long[] dp = new long[n + 1];
        Arrays.fill(dp, -1);
        return countFriendsPairings_tabu(n, dp);
    }

    // ! Optimisation :
    public long countFriendsPairings_opti(int n) {
        long a = 1, b = 1;
        for (int i = 2; i <= n; i++) {
            long sum = b + (a * (i - 1)) % mod; // Multiply karne se meri value overflow ho sakti hai isiliye maine mod
                                                // kiya. Per jab maine b ko add kiya, meri value tabhi bhi to overflow
                                                // ho sakti hai. To isiliye maine sum ko dubara mod kiya.
            a = b;
            b = sum % mod;
        }

        return b;
    }

    // b <=== Count number of ways to partition a set into k subsets =========>
    // https://www.geeksforgeeks.org/count-number-of-ways-to-partition-a-set-into-k-subsets/
    // ! Not Solved by you, Practice it more.

    // Here basically divide N people in K groups where no group should be empty.

    // For example we have 5 people . "abcde" and we have to devide them in 3 group.

    // ? Faith :
    // 'a' ke pass 2 choice hai.
    // 'a' bolega ki mai ek apna single group banaunga, aur baki sab apne aap ko
    // ` bache hue groups(k-1) mai devide kar lein.
    // 'a' bolega ki tum apne aap ko 'K' groups mai divide kar lo. Mai apne aap ko
    // tumne jo groups banaye honge use mai add kar lunga.

    // ! Memoisation :

    // # Why 2-D dp is used ?
    // It is because there are two values that are changing. n and k. And different
    // value of n & k has different tree structures..
    public static int divideInKGroup_memo(int n, int k, int[][] dp) {
        if (n == k || k == 1)
            return dp[n][k] = 1;

        if (dp[n][k] != 0) // # !=0 Since 0 can never be part of the answer.
            return dp[n][k];
        int selfGroup = divideInKGroup_memo(n - 1, k - 1, dp);
        int partOfGroup = divideInKGroup_memo(n - 1, k, dp) * k;
        // Total number of ways multiplied by total groups.
        // So itne possible ways hain ki mai baki groups ke sath combine ho sakta hun.

        return dp[n][k] = selfGroup + partOfGroup;
    }

    public static int divideInKGroup_DP(int N, int K, int[][] dp) {
        // Did not start both loops from 0 since it is not possible to divide 0 people
        // in k groups or n people in 0 groups. Hence does not make sense to fill those
        // 0th row and 0th column, since their value will always be zero.

        for (int n = 1; n <= N; n++) {
            for (int k = 1; k <= K; k++) {
                if (n == k || k == 1) {
                    dp[n][k] = 1;
                    continue;
                }

                int selfGroup = dp[n - 1][k - 1];// divideInKGroup(n - 1, k - 1, dp);
                int partOfGroup = dp[n - 1][k] * k;// divideInKGroup(n - 1, k, dp) * k;

                dp[n][k] = selfGroup + partOfGroup;
            }
        }

        return dp[N][K];
    }

    public static void divideInKGroup() {
        int n = 5;
        int k = 3;

        int[][] dp = new int[n + 1][k + 1];
        System.out.println(divideInKGroup_memo(n, k, dp));
        display2D(dp);
    }

    // b <======================= String Set ================>
    // Yahan pe basically humesha 2 question puchne hote hain :

    // 1. si==sj, (agar dono equal hain) to mai kya karunga
    // 2. si!=sj, (agar dono equal nhi hain)to mai kya karunga.

    // b <========== Longest Palindromic Subsequence ================>
    // https://leetcode.com/problems/longest-palindromic-subsequence/

    // # A subsequence is a sequence that can be derived from another sequence by
    // # deleting some or no elements without changing the order of the remaining
    // # elements.

    // Ab kyunki mai subsequence ki baat kar raha hun, to merepe do character honge
    // aur dono ke pass do choices hongi.

    // i j
    // ✔ ✔ (Dono subsequence ka part honge) (Case 1)
    // ✔ ✖ (i part hoga, aur j nhi hoga) (Case 2)
    // ✖ ✔ (j part hoga, aur i nhi hoga) ( Case 3)
    // ✖ ✖ (Dono he subsequence ka part nhi honge) (Case 4)

    // The above are cases for when si!=sj.
    // Ab yahan pe dono character agar equal hai he nhi, mujhe un dono ko sath leke
    // kuch fayda milega he nhi. So Case 1 ka yahan pe no use.

    // So we will have to discuss about remaining three cases.

    // ? Case 4 : Hidden Call (Not compulsory call)
    // So ab hum case 4 ki baat karte hain. Iski call hume logically lagani chahiye,
    // per ye call Case 2 aur Case 3 wali calls mai chupi hai, isiliye hum ise bhi
    // neglect kar sakte hain. Matlab agar hum lagayenge to answer mil jayega kyunki
    // dp bacha lega.

    // ! Recursion :

    public int longestPalindromeSubseq_recu(String str, int i, int j) {
        if (i == j || j < i)// j < i for test case "kss". When only "ss" is left, then in next call i will
                            // ` be greater than j
            return i == j ? 1 : 0;

        char ch1 = str.charAt(i);
        char ch2 = str.charAt(j);

        int count = 0;
        if (ch1 == ch2) { // When i and j, both will be part of the subsequence
            count = longestPalindromeSubseq_recu(str, i + 1, j - 1) + 2;
        } else {
            count = Math.max(longestPalindromeSubseq_recu(str, i + 1, j), longestPalindromeSubseq_recu(str, i, j - 1));
        }

        return count;
    }

    public int longestPalindromeSubseq_recu(String s) {
        return longestPalindromeSubseq_recu(s, 0, s.length() - 1);
    }

    // ! Memoisation :

    public int longestPalindromeSubseq_memo(String str, int i, int j, int[][] dp) {
        if (i == j || j < i) // j < i for test case "kss". When only "ss" is left, then in next call i will
                             // ` be greater than j
            return dp[i][j] = i == j ? 1 : 0;

        if (dp[i][j] != 0)
            return dp[i][j];

        char ch1 = str.charAt(i);
        char ch2 = str.charAt(j);

        int count = 0;
        if (ch1 == ch2) {
            count = longestPalindromeSubseq_memo(str, i + 1, j - 1, dp) + 2;
        } else {
            count = Math.max(longestPalindromeSubseq_memo(str, i + 1, j, dp),
                    longestPalindromeSubseq_memo(str, i, j - 1, dp));
        }

        return dp[i][j] = count;
    }

    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        return longestPalindromeSubseq_memo(s, 0, s.length() - 1, dp);
    }

    // ! Observation & Tabulation :

    // Taking a example for i and j.
    // Let i and j be (1,3) respectively.
    // So we will be needing (2,3) and (1,2) respectively if both are not equal.
    // And if they are equal we will be needing (2.2).

    // Therefore we have resolved the dependency left to right and bottom to top.

    public int longestPalindromeSubseq_tabu(String str, int I, int J, int[][] dp) {
        int n = str.length();
        for (int j = 0; j < n; j++) { // column
            for (int i = n - 1; i >= 0; i--) { // row
                if (i == j || j < i) {
                    dp[i][j] = (i == j) ? 1 : 0;
                    continue;
                }
                char ch1 = str.charAt(i);
                char ch2 = str.charAt(j);

                int count = 0;
                if (ch1 == ch2) {
                    count = dp[i + 1][j - 1] + 2;
                } else {
                    count = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
                dp[i][j] = count;
            }
        }
        return dp[I][J];
    }

    // ! Tabulation using gap strategy :

    // This dependency can also be resolved by filling the diagonal.
    // Hence we can use what we call as gap startegy.

    public int longestPalindromeSubseq_tabu_gap(String str, int I, int J, int[][] dp) {
        int n = str.length();
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; i < n && j < n; i++, j++) {
                if (i == j || j < i) {
                    dp[i][j] = (i == j) ? 1 : 0;
                    continue;
                }
                char ch1 = str.charAt(i);
                char ch2 = str.charAt(j);

                int count = 0;
                if (ch1 == ch2) {
                    count = dp[i + 1][j - 1] + 2;
                } else {
                    count = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
                dp[i][j] = count;
            }
        }
        return dp[I][J];
    }

    public int longestPalindromeSubseq_tabu(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        return longestPalindromeSubseq_tabu(s, 0, s.length() - 1, dp);
    }

    // b <============ Longest Common Subsequence ================>
    // https://leetcode.com/problems/longest-common-subsequence/

    // ! Recursion :

    // Agar mere ith or jth character same hai, to mai baki i+1, j+1 wali string ko
    // ` bolunga ki tum apna longest subsequence leke aa jao.

    // Aur agar ith or jth character same nhi hai to ab teen calls lagengi.
    // (Case 2,3,4). But as discussed 4th case ki call lagane ki jaroorat nhi hai.To
    // ` Bas 2 aur 3 case ki call lagengi

    // # Yahan pe basically hum index pass kar rahe hain.
    public int longestCommonSubsequence_recu(String str1, String str2, int i, int j) {

        if (i == str1.length() || j == str2.length())
            return 0;

        char ch1 = str1.charAt(i);
        char ch2 = str2.charAt(j);

        int count = 0;
        if (ch1 == ch2) {
            count = longestCommonSubsequence_recu(str1, str2, i + 1, j + 1) + 1;
        } else {
            count = Math.max(longestCommonSubsequence_recu(str1, str2, i + 1, j),
                    longestCommonSubsequence_recu(str1, str2, i, j + 1));
        }

        return count;
    }

    public int longestCommonSubsequence_recu(String text1, String text2) {
        return longestCommonSubsequence_recu(text1, text2, 0, 0);
    }

    // ! Memoisation :

    public int longestCommonSubsequence_memo(String str1, String str2, int i, int j, int[][] dp) {

        if (i == str1.length() || j == str2.length())
            return dp[i][j] = 0;

        if (dp[i][j] != -1)
            return dp[i][j];
        char ch1 = str1.charAt(i);
        char ch2 = str2.charAt(j);

        int count = 0;
        if (ch1 == ch2) {
            count = longestCommonSubsequence_memo(str1, str2, i + 1, j + 1, dp) + 1;
        } else {
            count = Math.max(longestCommonSubsequence_memo(str1, str2, i + 1, j, dp),
                    longestCommonSubsequence_memo(str1, str2, i, j + 1, dp));
        }

        return dp[i][j] = count;
    }

    public int longestCommonSubsequence(String text1, String text2) {
        int n1 = text1.length(), n2 = text2.length();
        int[][] dp = new int[n1 + 1][n2 + 1];

        for (int[] d : dp) {
            Arrays.fill(d, -1);
        }
        return longestCommonSubsequence_memo(text1, text2, 0, 0, dp);
    }

    // # Note :
    // ? String ke question, pallindrome ko chodke, dry run normal aage se he karna
    // ? chahiye, per question solve piche se karna chahiye. Matlab length ke basis
    // ? pe. To function mai length pass karni chahiye. Aage jake iss approach se
    // ? help milti hai.

    // ! Recursion : Passing length

    public int longestCommonSubsequence_recu_length(String str1, String str2, int n, int m) {

        if (n == 0 || m == 0)
            return 0;

        char ch1 = str1.charAt(n - 1);
        char ch2 = str2.charAt(m - 1);

        int count = 0;
        if (ch1 == ch2) {
            count = longestCommonSubsequence_recu_length(str1, str2, n - 1, m - 1) + 1;
        } else {
            count = Math.max(longestCommonSubsequence_recu_length(str1, str2, n - 1, m),
                    longestCommonSubsequence_recu_length(str1, str2, n, m - 1));
        }

        return count;
    }

    public int longestCommonSubsequence_recu_length(String text1, String text2) {
        int n = text1.length(), m = text2.length();
        return longestCommonSubsequence_recu_length(text1, text2, n, m);
    }

    // ! Memoisation : Length

    public int longestCommonSubsequence_recu_length_memo(String str1, String str2, int n, int m, int[][] dp) {

        if (n == 0 || m == 0)
            return dp[n][m] = 0;

        if (dp[n][m] != -1)
            return dp[n][m];
        char ch1 = str1.charAt(n - 1);
        char ch2 = str2.charAt(m - 1);

        int count = 0;
        if (ch1 == ch2) {
            count = longestCommonSubsequence_recu_length_memo(str1, str2, n - 1, m - 1, dp) + 1;
        } else {
            count = Math.max(longestCommonSubsequence_recu_length_memo(str1, str2, n - 1, m, dp),
                    longestCommonSubsequence_recu_length_memo(str1, str2, n, m - 1, dp));
        }

        return dp[n][m] = count;
    }

    public int longestCommonSubsequence_recu_length_memo(String text1, String text2) {
        int n = text1.length(), m = text2.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int[] d : dp) {
            Arrays.fill(d, -1);
        }
        return longestCommonSubsequence_recu_length_memo(text1, text2, n, m, dp);
    }

    // ! Observation & Tabulation :

    public int longestCommonSubsequence_recu_length_tabu(String str1, String str2, int N, int M, int[][] dp) {

        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = 0;
                    continue;
                }
                char ch1 = str1.charAt(n - 1);
                char ch2 = str2.charAt(m - 1);

                int count = 0;
                if (ch1 == ch2) {
                    count = dp[n - 1][m - 1] + 1;
                } else {
                    count = Math.max(dp[n - 1][m], dp[n][m - 1]);
                }

                dp[n][m] = count;
            }
        }
        return dp[N][M];
    }

    public int longestCommonSubsequence_recu_length_tabu(String text1, String text2) {
        int n = text1.length(), m = text2.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int[] d : dp) {
            Arrays.fill(d, -1);
        }
        return longestCommonSubsequence_recu_length_tabu(text1, text2, n, m, dp);
    }

    // b <======== Distinct Subsequences =================>
    // https://leetcode.com/problems/distinct-subsequences/

    // Subsequence mai hum ek character pe do choices hoti hai. Ya to wo subsequence
    // ka part banega ya fir nhi banega.

    // ! Recursion :

    // ? Faith :
    // # Agar mere character match karte hain, to do kaam honge.
    // s = "babgbag", t = "bag"

    // 1. Ab mai "abgbag" string mai "ag" ko search karunga. ==> (Kyunki mujhe b mil
    // gaya hain to ab mai bachi hui string mai baki ki string ko search karunga)

    // 2. or mai "abgbag" string mai "bag" ko search karuga. == > (Kyunki bachi hui
    // string mai bhi to mujhe search string mil sakti hai)

    // # Agar mere character match nhi karte hain to mai agle character ki simple
    // call
    // # laga dunga.
    // s = "gbag", t = "bag"
    // Ab mai "bag" mai "bag" ko search karunga

    // # Same concept dry run aage se (using index), code piche se (using length).

    public int numDistinct_recu(String str1, String str2, int n, int m) {
        if (m == 0)
            return 1;
        if (n < m) // Agar meri search karne wali string hai uski length agar greater hai jis
                   // string mai dhundhna hai, to hum kabhi bhi string mil he nhi payegi.
            return 0;
        char ch1 = str1.charAt(n - 1);
        char ch2 = str2.charAt(m - 1);
        int count = 0;

        if (ch1 == ch2) {
            count += numDistinct_recu(str1, str2, n - 1, m - 1);
            count += numDistinct_recu(str1, str2, n - 1, m);
        } else {
            count += numDistinct_recu(str1, str2, n - 1, m);
        }

        return count;
    }

    public int numDistinct_recu(String s, String t) {
        int n = s.length(), m = t.length();
        return numDistinct_recu(s, t, n, m);
    }

    // ! Memoisation :

    // In length terms, 0 indicate the empty string. Remember.
    public int numDistinct_memo(String str1, String str2, int n, int m, int[][] dp) {
        if (m == 0)
            return dp[n][m] = 1;
        if (n < m)
            return dp[n][m] = 0;

        if (dp[n][m] != -1)
            return dp[n][m];
        char ch1 = str1.charAt(n - 1);
        char ch2 = str2.charAt(m - 1);
        int count = 0;

        if (ch1 == ch2) {
            count += numDistinct_memo(str1, str2, n - 1, m - 1, dp);
            count += numDistinct_memo(str1, str2, n - 1, m, dp);
        } else {
            count += numDistinct_memo(str1, str2, n - 1, m, dp);
        }

        return dp[n][m] = count;
    }

    public int numDistinct_memo(String s, String t) {
        int n = s.length(), m = t.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return numDistinct_memo(s, t, n, m, dp);
    }

    // ! Observation And Tabulation :

    public int numDistinct_tabu(String str1, String str2, int N, int M, int[][] dp) {

        for (int m = 0; m <= M; m++) {
            for (int n = 0; n <= N; n++) {
                if (m == 0) {
                    dp[n][m] = 1;
                    continue;
                }
                if (n < m) {
                    dp[n][m] = 0;
                    continue;
                }
                char ch1 = str1.charAt(n - 1);
                char ch2 = str2.charAt(m - 1);
                int count = 0;

                if (ch1 == ch2) {
                    count += dp[n - 1][m - 1]; // numDistinct_memo(str1, str2, n - 1, m - 1, dp);
                    count += dp[n - 1][m];// numDistinct_memo(str1, str2, n - 1, m, dp);
                } else {
                    count += dp[n - 1][m]; // numDistinct_memo(str1, str2, n - 1, m, dp);
                }
                dp[n][m] = count;
            }
        }
        return dp[N][M];
    }

    public int numDistinct_tabu(String s, String t) {
        int n = s.length(), m = t.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return numDistinct_tabu(s, t, n, m, dp);
    }

    // ! Dp ka har ek cell recursion ka faith he denote kar raha hota hai.

    // b <========== 72. Edit Distance ==================>
    // https://leetcode.com/problems/edit-distance/

    // ! Recursion :

    // # Agar character equal hai to mujhe koi bhi operation karne ki jaroorat nhi
    // # hai

    // # Agar ab mere character equal nhi hai, to mai teen operation use kar sakta
    // # hun : Insert, Delete and replace

    // Now consider we have to convert "aturday" into "unday"

    // 1. Insert : We can add u at starting making "uaturday". Now the problem
    // statement remains : To convert "aturday" into "nday"

    // 2. Delete : We can delete a at starting making "turday". Now the problem
    // statement remains : To convert "turday" into "unday"

    // 3. Replace : We can replace a at the starting with u making "uturday". Now
    // the problem statement remains : To convert "turday" into "nday"

    // # Same concept dry run aage se (using index), code piche se (using length).

    public int minDistance_recu(String str1, String str2, int n, int m) {

        if (n == 0 || m == 0) {
            return n == 0 ? m : n;
            // Three cases to be handled :
            // 1. n==0 and m==0, then there is no operation required.
            // Convert "" to "".

            // 2. if n!=0 and m==0, n delete operation are required.
            // Convert "abc" to "".0

            // 3. if n==0 and m!=0, m insert operation are required.
            // Convert "" to "abc".
        }

        char ch1 = str1.charAt(n - 1);
        char ch2 = str2.charAt(m - 1);
        int min = 0;
        if (ch1 == ch2) {
            min = minDistance_recu(str1, str2, n - 1, m - 1);
        } else {
            int insert = minDistance_recu(str1, str2, n, m - 1);
            int delete = minDistance_recu(str1, str2, n - 1, m);
            int replace = minDistance_recu(str1, str2, n - 1, m - 1);

            min = Math.min(insert, Math.min(delete, replace)) + 1; // +1 added for either of these operation used.
        }
        return min;
    }

    public int minDistance_recu(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        return minDistance_recu(word1, word2, n, m);
    }

    // ! Memoisation :

    public int minDistance_memo(String str1, String str2, int n, int m, int[][] dp) {

        if (n == 0 || m == 0) {
            return dp[n][m] = n == 0 ? m : n;
        }

        if (dp[n][m] != -1)
            return dp[n][m];
        char ch1 = str1.charAt(n - 1);
        char ch2 = str2.charAt(m - 1);
        int min = 0;
        if (ch1 == ch2) {
            min = minDistance_memo(str1, str2, n - 1, m - 1, dp);
        } else {
            int insert = minDistance_memo(str1, str2, n, m - 1, dp);
            int delete = minDistance_memo(str1, str2, n - 1, m, dp);
            int replace = minDistance_memo(str1, str2, n - 1, m - 1, dp);

            min = Math.min(insert, Math.min(delete, replace)) + 1;
        }
        return dp[n][m] = min;
    }

    public int minDistance_memo(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return minDistance_memo(word1, word2, n, m, dp);
    }

    // ! Observation and Tabulation :

    public int minDistance_tabu(String str1, String str2, int N, int M, int[][] dp) {

        for (int m = 0; m <= M; m++) {
            for (int n = 0; n <= N; n++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = n == 0 ? m : n;
                    continue;
                }

                char ch1 = str1.charAt(n - 1);
                char ch2 = str2.charAt(m - 1);
                int min = 0;
                if (ch1 == ch2) {
                    min = dp[n - 1][m - 1];// minDistance_memo(str1, str2, n - 1, m - 1, dp);
                } else {
                    int insert = dp[n][m - 1]; // minDistance_memo(str1, str2, n, m - 1, dp);
                    int delete = dp[n - 1][m]; // minDistance_memo(str1, str2, n - 1, m, dp);
                    int replace = dp[n - 1][m - 1]; // minDistance_memo(str1, str2, n - 1, m - 1, dp);

                    min = Math.min(insert, Math.min(delete, replace)) + 1;
                }
                dp[n][m] = min;
            }
        }
        return dp[N][M];
    }

    public int minDistance(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return minDistance_tabu(word1, word2, n, m, dp);
    }

    // ! Follow Up Question :
    // # Given the cost of insert, delete and replace, find the min cost to convert
    // # one string to another.

    // ! Memoisation :
    static int insertCost, deleteCost, replaceCost;

    public int minDistance_memo_minCost(String str1, String str2, int n, int m, int[][] dp) {

        if (n == 0 || m == 0) {
            // Three cases to be handled :
            // 1. n==0 and m==0, then there is no operation required.
            // Convert "" to "".

            // 2. if n!=0 and m==0, n delete operation are required.
            // Convert "abc" to "".0

            // 3. if n==0 and m!=0, m insert operation are required.
            // Convert "" to "abc".
            if (n == 0 && m == 0)
                return dp[n][m] = 0;
            else if (n != 0 && m == 0)
                return dp[n][m] = n * deleteCost;
            else if (n == 0 && m != 0)
                return dp[n][m] = m * insertCost;
        }

        if (dp[n][m] != -1)
            return dp[n][m];
        char ch1 = str1.charAt(n - 1);
        char ch2 = str2.charAt(m - 1);
        int min = 0;
        if (ch1 == ch2) {
            min = minDistance_memo_minCost(str1, str2, n - 1, m - 1, dp);
        } else {
            int insert = minDistance_memo_minCost(str1, str2, n, m - 1, dp);
            int delete = minDistance_memo_minCost(str1, str2, n - 1, m, dp);
            int replace = minDistance_memo_minCost(str1, str2, n - 1, m - 1, dp);

            min = Math.min(insert + insertCost, Math.min(delete + deleteCost, replace + replaceCost));
        }
        return dp[n][m] = min;
    }

    public int minDistance_memo_minCost(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return minDistance_memo_minCost(word1, word2, n, m, dp);
    }

    // ! 2nd Follow Up question :
    // # you have been given the cost of inserting a character in a array.
    // # Similary for replace and delete.
    // # So here instead of a single cost, there is cost corresponding to every
    // # character from 'a' to 'z' for insert, delete and replace operation.

    // # Now find the min cost to convert the string 1 to string 2.

    // Yahan pe basically jis character ko insert karenge, uski cost add karenge.
    // Similarly jis character ko delete karenge, uski cost add karenge.
    // Similarly jis character ko replace karenge, uski cost add karenge.

    // Yahan pe Base case mai chages honge.
    // Jab hume "abc" ko "" mai convert karna tha, to hume a + b + c Teeno ki delete
    // cost return karni hogi. Iske liye hum ek alag se function likh sakte hain.

    // Similarly jab "" ko "abc" mai convert karna hoga.

    // b <===================== Wildcard Matching ==========================>
    // https://leetcode.com/problems/wildcard-matching/

    // ! Recursion :

    // # Faith :

    // Agar mere dono character equal hai to mai n -1 string ko bolunga tu apne aap
    // ke m - 1 se map karne ki try kar

    // Ab agar question mark aata hai to wo kisi bhi ek character ko match kar sakta
    // hai, to same uper wali call lagegi

    // Ab agar '*' aata hai to do batein ho sakti hai.
    // 1. Wo empty string ko map kare. (simple call of n, m-1);
    // 2. Wo sequence of character ko map kare.

    // To maine call aise lagayi hai kyunki wo sequence of character ko map kar
    // sakta hai, to maine * to next call mai alive bhi rakha hai.

    // For example (adceb, *a*b);
    // Now Iske liye, * can be a, ad, adc, adce, adceb. Therefore humne star ko
    // alive rakha hai taki wo aage wale ko bhi map kar paye.
    // # Isiliye call (n-1,m) ki lagayi hai.

    // Basic Two calls from (adceb, *a*b) :
    // 1. (dceb,*a*b); == > sequence of character wali call
    // 2. (adceb,a*b); == > empty string ko map karne wali call

    public boolean isMatch_recu(String str1, String str2, int n, int m) {

        if (n == 0 || m == 0) {
            if (n == 0 && m == 0)
                return true;
            else if (m == 1 && str2.charAt(m - 1) == '*') // ! Important condition :
                // Agar case aise bane ("" / "*");
                // n=0 per abhi bhi "*" bacha hai to abhi bhi * empty string ko map kar sakta
                // hai.
                return true;
            else
                return false;
        }

        char ch1 = str1.charAt(n - 1);
        char ch2 = str2.charAt(m - 1);

        if (ch1 == ch2 || ch2 == '?') {
            return isMatch_recu(str1, str2, n - 1, m - 1);
        } else if (ch2 == '*') {
            boolean res = false;
            res = res || isMatch_recu(str1, str2, n - 1, m); // mapping sequence of character
            res = res || isMatch_recu(str1, str2, n, m - 1); // mapping empty string
            return res;
        } else {
            return false;
        }
    }

    public String removeStars(String str) { // Jo kaam single "*" kar raha hai, whi kaam "****" karenge. To
                                            // lagataar aane wale stars ko remove kar diya
        if (str.length() == 0)
            return "";

        StringBuilder sb = new StringBuilder();
        sb.append(str.charAt(0));

        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == '*' && sb.charAt(sb.length() - 1) == '*')
                continue;
            sb.append(str.charAt(i));
        }

        return sb.toString();
    }

    public boolean isMatch_recu(String s, String p) {
        String newP = removeStars(p);
        int n = s.length(), m = newP.length();
        return isMatch_recu(s, newP, n, m);
    }

    // ! Memoisation :

    // # Don't do the mistake of making the boolean dp since true and false are the
    // # part of the answer. False denote that the string cannot be mapped and true
    // # Denotes that the string can be mapped.

    // ? Therefore make a dp of integer and prefill it with -1.
    // ? 0 denoting false and 1 denoting true value respectively.

    public int isMatch_memo(String str1, String str2, int n, int m, int[][] dp) {

        if (n == 0 || m == 0) {
            if (n == 0 && m == 0)
                return dp[n][m] = 1;
            else if (m == 1 && str2.charAt(m - 1) == '*') // ! Important condition :
                // Agar case aise bane ("" / "*");
                // n=0 per abhi bhi "*" bacha hai to abhi bhi * empty string ko map kar sakta
                // hai.
                return dp[n][m] = 1;
            else
                return dp[n][m] = 0;
        }

        if (dp[n][m] != -1)
            return dp[n][m];

        char ch1 = str1.charAt(n - 1);
        char ch2 = str2.charAt(m - 1);

        if (ch1 == ch2 || ch2 == '?') {
            return dp[n][m] = isMatch_memo(str1, str2, n - 1, m - 1, dp);
        } else if (ch2 == '*') {
            boolean res = false;
            res = res || isMatch_memo(str1, str2, n - 1, m, dp) == 1; // mapping sequence of character
            res = res || isMatch_memo(str1, str2, n, m - 1, dp) == 1; // mapping empty string
            return dp[n][m] = res ? 1 : 0;
        } else {
            return dp[n][m] = 0;
        }
    }

    public boolean isMatch_memo(String s, String p) {
        String newP = removeStars(p);
        int n = s.length(), m = newP.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return isMatch_memo(s, newP, n, m, dp) == 1 ? true : false;
    }

    // ! Observation and Tabulation :

    // For tabulation, just follow the basic rules and everything will run smoothly.
    public int isMatch_tabu(String str1, String str2, int N, int M, int[][] dp) {

        for (int m = 0; m <= M; m++) {
            for (int n = 0; n <= N; n++) {
                if (n == 0 || m == 0) {
                    if (n == 0 && m == 0) {
                        dp[n][m] = 1;
                        continue;
                    } else if (m == 1 && str2.charAt(m - 1) == '*') {
                        dp[n][m] = 1;
                        continue;
                    } else {
                        dp[n][m] = 0;
                        continue;
                    }
                }

                char ch1 = str1.charAt(n - 1);
                char ch2 = str2.charAt(m - 1);

                if (ch1 == ch2 || ch2 == '?') {
                    dp[n][m] = dp[n - 1][m - 1]; // isMatch_memo(str1, str2, n - 1, m - 1, dp);
                } else if (ch2 == '*') {
                    boolean res = false;
                    res = res || dp[n - 1][m] == 1; // mapping sequence of character
                    res = res || dp[n][m - 1] == 1; // mapping empty string
                    dp[n][m] = res ? 1 : 0;
                } else {
                    dp[n][m] = 0;
                }
            }
        }

        return dp[N][M];
    }

    public boolean isMatch_tabu(String s, String p) {
        String newP = removeStars(p);
        int n = s.length(), m = newP.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return isMatch_tabu(s, newP, n, m, dp) == 1 ? true : false;
    }

    public static void main(String[] args) {
        mazePath();
    }

}
