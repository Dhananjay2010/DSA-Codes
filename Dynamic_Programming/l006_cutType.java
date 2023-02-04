import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class l006_cutType {

    // b <============== Basic Matrix Multiplication ==================>

    // ? Some Basic rules :
    // 1. A * B != B * A
    // 2. (n*m) * (m*q) = (n * q)
    // 3. (n*m) * (m*p) * (p*q) * (q*r) * (r*s) = (n*s)

    // # For a multiplication of two matrix of size (n*m) * (m*q), the resultant
    // # will be (n * q).

    // # so to fill each cell of (n*q) matrix, "m" multiplication are needed.
    // # Since there are total (n*q) cell, so the total multiplication needed will
    // # be n*m*q. (Itni puri jaan lagi multiplication mai).

    // ! The above is the cost to generate a singl matrix.

    // # For a multiplication of two matrix of size (m*q) * (q*n) , the resultant
    // # will be (m * n).

    // The total cost will also be diffrent. (m*q*n)

    // b <================ Matrix Chain Multiplication ==================>
    // https://practice.geeksforgeeks.org/problems/matrix-chain-multiplication0303/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // ? Now the question starts :)

    // ! Recursion :

    // # Whi same basic faith. Maine apni left wali matrix aur right wali matrix se
    // # kaha ki tum apne aap to multiply karne ki least cost leke aao. Ab jo total
    // # multiplication cost hogi wo sum hoga left ki cost ka plus right ki cost ka
    // # plus jo cost lagi left aur right matrix ko aapas mai multiply karne mai.

    // # Jo inme se sabse min cost aayegi mai whi return kar dunga.

    // Ab kyunki hume matrix ka size array ki form mai de rakha hai, to hume index
    // pass karne honge.

    // Aur cut lagana hoga taki hum matrix ko alag alag combination mai multiply kar
    // payein.

    // For a array given of size 5, [40,20,30,10,30] , the total single matrix will
    // ` be 4.

    // Input: N = 5
    // arr = {40, 20, 30, 10, 30}
    // Explaination: There are 4 matrices of dimension
    // 40x20, 20x30, 30x10, 10x30. Say the matrices are
    // named as A, B, C, D

    // So cut can be only at 1,2,3 index.
    // ABCD
    // Applying cut at 0("", "ABCD") or 4("ABCD", "") leaves the question at same
    // point. Hence it will act as a infinite loop.

    static int matrixMultiplication_recu(int N, int si, int ei, int arr[]) {

        if (ei - si == 1) // Single matrix has no one to multiply with, therefore the multiplication sum
                          // will be 0.
            return 0;

        int min = (int) 1e9;

        for (int cut = si + 1; cut < ei; cut++) {

            int leftRes = matrixMultiplication_recu(N, si, cut, arr);
            int rightRes = matrixMultiplication_recu(N, cut, ei, arr);

            min = Math.min(min, leftRes + arr[si] * arr[cut] * arr[ei] + rightRes);
        }
        return min;
    }

    static int matrixMultiplication_recu(int N, int arr[]) {
        return matrixMultiplication_recu(N, 0, N - 1, arr);
    }

    // ! Memoisation :

    static int matrixMultiplication_memo(int N, int si, int ei, int arr[], int[][] dp) {

        if (ei - si == 1)
            return dp[si][ei] = 0;

        if (dp[si][ei] != -1)
            return dp[si][ei];
        int min = (int) 1e9;
        for (int cut = si + 1; cut < ei; cut++) {

            int leftRes = matrixMultiplication_memo(N, si, cut, arr, dp);
            int rightRes = matrixMultiplication_memo(N, cut, ei, arr, dp);

            min = Math.min(min, leftRes + arr[si] * arr[cut] * arr[ei] + rightRes);
        }
        return dp[si][ei] = min;
    }

    static int matrixMultiplication_memo(int N, int arr[]) {
        int[][] dp = new int[N][N];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return matrixMultiplication_memo(N, 0, N - 1, arr, dp);
    }

    // ! Observation and Tabulation :

    // Since the dependency is diagonal, therefore used gap strategy.

    // ? Important Point :
    // # Cut type mai kuch bhi use karlo, tabulation ya memoisation, almost dono mai
    // # same state visit ho rahi hain.

    static int matrixMultiplication_tabu(int N, int SI, int EI, int arr[], int[][] dp) {

        for (int gap = 1; gap < N; gap++) {
            for (int si = 0, ei = gap; ei < N; si++, ei++) {
                if (ei - si == 1) {
                    dp[si][ei] = 0;
                    continue;
                }
                int min = (int) 1e9;
                for (int cut = si + 1; cut < ei; cut++) {
                    int leftRes = dp[si][cut];
                    int rightRes = dp[cut][ei];

                    min = Math.min(min, leftRes + arr[si] * arr[cut] * arr[ei] + rightRes);
                }
                dp[si][ei] = min;
            }
        }
        return dp[SI][EI];
    }

    static int matrixMultiplication_tabu(int N, int arr[]) {
        int[][] dp = new int[N][N];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return matrixMultiplication_tabu(N, 0, N - 1, arr, dp);
    }

    // b <===================Brackets in Matrix Chain Multiplication
    // ================>
    // https://practice.geeksforgeeks.org/problems/brackets-in-matrix-chain-multiplication1024/1

    // ! Get the matrix multiplication string :

    // # Creating a string dp is costly but a good method to calculate the path.
    static String matrixMultiplication_tabu_string(int N, int SI, int EI, int arr[], int[][] dp) {
        String[][] sdp = new String[N][N];
        for (int gap = 1; gap < N; gap++) {
            for (int si = 0, ei = gap; ei < N; si++, ei++) {
                if (ei - si == 1) {
                    dp[si][ei] = 0;
                    sdp[si][ei] = (char) (si + 'A') + "";
                    continue;
                }
                int min = (int) 1e9;
                String minStr = "";
                for (int cut = si + 1; cut < ei; cut++) {
                    int leftRes = dp[si][cut];
                    int rightRes = dp[cut][ei];

                    if (leftRes + arr[si] * arr[cut] * arr[ei] + rightRes < min) {
                        min = leftRes + arr[si] * arr[cut] * arr[ei] + rightRes;
                        minStr = "(" + sdp[si][cut] + sdp[cut][ei] + ")"; // simple hai left se jo string aayi aur right
                                                                          // se jo string aayi use combine kar diya
                    }
                    min = Math.min(min, leftRes + arr[si] * arr[cut] * arr[ei] + rightRes);
                }
                dp[si][ei] = min;
                sdp[si][ei] = minStr;
            }
        }
        return sdp[SI][EI];
    }

    static String matrixMultiplication_tabu_string(int N, int arr[]) {
        int[][] dp = new int[N][N];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return matrixMultiplication_tabu_string(N, 0, N - 1, arr, dp);
    }

    // b <==========Minimum and Maximum values of an expression with * and + =====>
    // https://www.geeksforgeeks.org/minimum-maximum-values-expression/

    // # Simple faith rakha hain aur uspe he sara code likha hai.
    // # Maine bola jahan jahan bhi mai cut laga raha hun, wo mujhe left aur right
    // # ke apna min aur max value lake dede. Ab mai operation ko evalute karunga
    // # aur compare karunga already existing values ke sath aur update kar dunga.

    public static class pairmm {

        int min = (int) (1e9);
        int max = 0; // max 0 le sakte hain kyunki sirf + aur * operation hain which will always
                     // result in +ve number, warna simple -(int)(1e9) lena.

        pairmm() {

        }

        pairmm(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    public static pairmm evaluateOperation(pairmm leftRes, pairmm rightRes, char operator) {
        pairmm myAns = new pairmm();
        if (operator == '+') {
            // Agar operator + hai, to sabse min value left aur right ke min values ko add
            // karke he aayegi. Similarly max value ka bhi same concept.
            myAns.min = leftRes.min + rightRes.min;
            myAns.max = leftRes.max + rightRes.max;

        } else if (operator == '*') {
            // Agar operator * hai, to sabse min value left aur right ke min values ko
            // multiply karke he aayegi. Similarly max value ka bhi same concept.
            myAns.min = leftRes.min * rightRes.min;
            myAns.max = leftRes.max * rightRes.max;
        }
        return myAns;
    }

    public static pairmm minMaxValue(String str, int si, int ei, pairmm[][] dp) {

        if (si == ei) {
            // Agar ek single element hai to uski min aur max value whi hoga.
            return dp[si][ei] = new pairmm(str.charAt(si) - '0', str.charAt(si) - '0');
        }

        if (dp[si][ei] != null)
            return dp[si][ei];

        pairmm myAns = new pairmm();
        for (int cut = si + 1; cut < ei; cut += 2) { // Cut + 2 since hum sirf operator pe he cur laga sakte hain aur
                                                     // operator alternate hain.

            pairmm leftRes = minMaxValue(str, si, cut - 1, dp);
            pairmm rightRes = minMaxValue(str, cut + 1, ei, dp);

            pairmm operationRes = evaluateOperation(leftRes, rightRes, str.charAt(cut));
            myAns.min = Math.min(myAns.min, operationRes.min);
            myAns.max = Math.max(myAns.max, operationRes.max);
        }

        return dp[si][ei] = myAns;
    }

    public static void minMax() {
        String str = "1+2*3+4*5";
        int n = str.length();
        pairmm[][] dp = new pairmm[n][n];

        // Rather than using pair, hum 3-d dp bhi use kar sakte the. Like int[][][] dp =
        // new int[n][n][2]; The [2] at the end whi indicate kar raha hai jo ek pair
        // indicate kar raha hai. dp[si][ei][0] will store the min value and
        // dp[si][ei][1] will store the max value. Bas Pair use karke code easy ho jata
        // hai samajne mai isiliye humne pair use kiya hai, which is a good habit.

        pairmm res = minMaxValue(str, 0, n - 1, dp);

        System.out.println("Min value : " + res.min);
        System.out.println("Max value : " + res.max);
    }

    // ? What if there is '-' operator also ?
    // Is case mai evaluate operation ke function mai he changes aayega bas. Aur
    // kahin nahin aayega. Usme bas ab sari condition lagane honge kyuni - * - is
    // +ve; '-' + '+' will reduce the value. Aise he similar condition lagane honge
    // hunme. To ab ye question logical ho gaya hai.

    // ? What if the digits are plural like "12+22*33+41*55"?
    // One way is to use split function to get the operator and number seperately.
    // We can use stack to make the split function. Stack ko fbharte jao jab tak
    // operator nhi milta. Aur jiase he mile, stack khali kardo. We will get the
    // number.

    // Also we can store the digit and the operator sequentially in the arraylist.

    // ! Important note :

    // # Humne abhi tak cut type mai ek he kaam kiya hai. Har cut ke liye maine left
    // # aur right dono ki value nikali. Left aur right se jo aay usme jahan pe cut
    // # lagaya, uska operation kiya. Aur use compare karte gye.

    // # Bas yhi kar rahe hain hum.

    // b <================== Burst Balloons ==============================>
    // https://leetcode.com/problems/burst-balloons/description/

    // For test case [3,1,5,8], kyunki mujhe nhi pata ki pehle 3 burst hoga ya 1
    // ` burst hoga, to mai pehle burst karke call nhi laga sakta. Isiliye mujhe
    // ` balloon ko sabse end mai he burst karna hoga taki wo balloon alive rahe.
    // Alive in the sense ki jispe humne cut lagaya wo alive rahega tab tak jab tak
    // `uske jo tree ke balloons hai wo pure burst na ho jaye. Taki cut wala balloon
    // ` bhi calculation mai aaye.

    // # Ab ek example consider kar. [3,1,5,8] ke tree ko expand kiya aur ab (2,3)
    // # wali call dekhenge.

    // (2,3) se do cut lagenge. Pehle cut 2 pe aur dusra 3 pe.
    // Ab jab 2 pe cut laga to left mai gaya X and right mai gaya (3,3).
    // To ab pehle 3 burst hoga jiski cost aayegi 5*8*1=40. ye Right se cost aayi.
    // Ab left se 0 aayega return hoke kyuki wo range(2,1) he exist nhi karegi.
    // To ab 3 burst ho chuka hai to ab 2 burst hoga(jispe cut laga hai), to cost
    // aayegi 1*5*1=5 so jo total is subtree se return hoga wo hoga 45.

    // Ab jab 3 pe cut laga to left mai gaya (2,2) and right mai gaya X.
    // To ab pehle 2 burst hoga jiski cost aayegi 1*5*8=40. ye left se cost aayi.
    // Ab right se 0 aayega return hoke kyuki wo range(4,3) he exist nhi karegi.
    // To ab 2 burst ho chuka hai to ab 3 burst hoga(jispe cut laga hai), to cost
    // aayegi 1*8*1=8 so jo total is subtree se return hoga wo hoga 48.

    // Dono mai se max 48 hai to (2,3) se yhi return hoga.
    // To aise kaam kar raha hai yahan pe.

    public static int getCost(int leftIndex, int rightIndex, int cutIndex, int[] arr) {

        int n = arr.length;
        int leftValue = leftIndex < 0 ? 1 : arr[leftIndex];
        int rightValue = rightIndex > n - 1 ? 1 : arr[rightIndex];

        return leftValue * arr[cutIndex] * rightValue;
    }

    public static int BalloonBurst(int[] arr, int si, int ei, int[][] dp) {

        if (si > ei)
            return 0;

        if (si == ei) {
            return dp[si][ei] = getCost(si - 1, si + 1, si, arr);
        }

        if (dp[si][ei] != -1)
            return dp[si][ei];

        int maxValue = 0;
        for (int cut = si; cut <= ei; cut++) {
            int leftRes = BalloonBurst(arr, si, cut - 1, dp);
            int rightRes = BalloonBurst(arr, cut + 1, ei, dp);

            // Jab mai cut ki cost nikalta hun, tab si to cut - 1 already burst ho chuka
            // hota hai.
            // Similarly cut+1 to ei, already burst ho chuka hota hai. Therefore isiliye jo
            // cost hai wo si -1 , cut, ei+1 pe calculate hoti hai.
            int cutCost = getCost(si - 1, ei + 1, cut, arr);
            maxValue = Math.max(maxValue, cutCost + leftRes + rightRes);
        }

        return dp[si][ei] = maxValue;
    }

    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n]; // (0) +(n-1) + 1 = n;
        for (int[] d : dp) {
            Arrays.fill(d, -1);
        }
        return BalloonBurst(nums, 0, n - 1, dp);
    }

    // b <============== Boolean Parenthesization ==============>
    // https://practice.geeksforgeeks.org/problems/boolean-parenthesization5610/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // # Yahan pe bhi same he kiya. Jitne operator hai unpe cut lagaya aur left aur
    // # right dono res ko evaluate kiya. Then ans ko return kar diya.

    // ! Thing to learn :

    // Kabhi bhi agar (a-b) mai mod lagana ho to what to write :
    // (a-b+mod) % mod;

    // ? Yahan pe bhi humne same he use kiya.

    public static class bPair {
        int totalTrue = 0;
        int totalFalse = 0;

        bPair(int totalTrue, int totalFalse) {
            this.totalTrue = totalTrue;
            this.totalFalse = totalFalse;
        }

        bPair() {

        }
    }

    public static void evaluateCountWays(bPair leftRes, bPair rightRes, bPair res, char operator) {

        int mod = 1003; // Kuynko mod ki value bahut small hai isiliye humne long nhi use kiya.

        int totalTrue = 0, totalFalse = 0;
        int totalTrueFalse = ((leftRes.totalTrue + leftRes.totalFalse) * (rightRes.totalTrue + rightRes.totalFalse))
                % mod;

        // Total True False combination kya honge. Agar mere left se (t1,f1) values
        // aayein aur right se (t2,f2) values aayein, to total true false values hongi
        // t1*t2 + t1*f2 + t2*f2 + f1*f2

        if (operator == '|') {
            // '|' operator ek he condition mai false hota hai jab dono he false hon. Baki
            // sab values mai true hota hai.Isiliye totalTrueFalse mai se totalFalse ko
            // nikala to hume totalTrue value mil gayi in '|' operator.

            // Humne multiply kyun kiya. totalFalse = (leftRes.totalFalse *
            // rightRes.totalFalse) % mod; ??

            // Multiply isiiye kiya kuynki agar left se mujhe total false mila aur right se
            // total false mila, to total mere operation('|') karne ke baad total False
            // (leftFalse * rightFalse) hoga.

            totalFalse = (leftRes.totalFalse * rightRes.totalFalse) % mod;
            totalTrue = (totalTrueFalse - totalFalse + mod) % mod;
        } else if (operator == '&') {
            // '&' operator ek he condition mai true hota hai jab left aur right dono true
            // hon, baki sab mai false rehta hai.
            totalTrue = (leftRes.totalTrue * rightRes.totalTrue) % mod;
            totalFalse = (totalTrueFalse - totalTrue + mod) % mod;
        } else if (operator == '^') {
            // '^' XOR ooperator true tab rehta hai jab ya to left true ho aur right false
            // ya vise-versa.
            totalTrue = ((leftRes.totalTrue * rightRes.totalFalse) % mod
                    + (leftRes.totalFalse * rightRes.totalTrue) % mod) % mod;
            totalFalse = (totalTrueFalse - totalTrue + mod) % mod;
        }

        res.totalTrue = (res.totalTrue + totalTrue) % mod;
        res.totalFalse = (res.totalFalse + totalFalse) % mod;
    }

    public static bPair countWays(int si, int ei, String str, bPair[][] dp) {

        if (si == ei) {
            char ch = str.charAt(si);
            int a = ch == 'T' ? 1 : 0;
            int b = ch == 'F' ? 1 : 0;
            return dp[si][ei] = new bPair(a, b);
            // Agar ch == "F", to return karna hai (0,1) kyunki true banane ka koi way nhi
            // hai aur false to wo hai he already.

            // Agar ch == "T", to return karna hai (1,0) kyunki false banane ka koi way nhi
            // hai aur false to wo hai he already.
        }

        if (dp[si][ei] != null)
            return dp[si][ei];

        bPair res = new bPair();
        for (int cut = si + 1; cut < ei; cut += 2) {

            bPair leftRes = countWays(si, cut - 1, str, dp);
            bPair rightRes = countWays(cut + 1, ei, str, dp);

            evaluateCountWays(leftRes, rightRes, res, str.charAt(cut));
        }

        return dp[si][ei] = res;
    }

    static int countWays(int N, String S) {
        // code here
        bPair[][] dp = new bPair[N][N];
        return countWays(0, N - 1, S, dp).totalTrue;
    }

    // b <================ Optimal Binary Search Tree =================>
    // https://www.geeksforgeeks.org/optimal-binary-search-tree-dp-24/

    // Since the val array is sorted, so every cut we make that will act as root,
    // the left part of array will be a part of left subtree and the right part of
    // the array will become the part of right subtree.

    // # First thing that would come in mind is maintaining the level while in
    // # recursion, but that is very tough and more complex to handle.

    // ? Trying a different approach.

    // Now consider a val array of [10,12,20] and freq[] = {34, 8, 50}
    // Let say, If we have cut at 12, then on the left subtree will be 10 and on the
    // right will be 20.

    // Now let's consider 10 seperately as a single node with no child. So it has
    // level of 1 and will return 34*1=34 to the left res of 12.
    // Now let's consider 20 seperately as a single node with no child. So it has
    // level of 1 and will return 50*1=50 to the right res of 12.

    // Now when we consider 12, the 10 and 12 node level is increased by 1. Hence we
    // add the sum of the range of array passed. So we will add (30 + 8 + 50) == >
    // The sum of the range to the answer. So adding 34 and 50 again does the
    // purpose of increasing the level of the 10 and 20 by 1 since now we consider
    // 12 as root.

    // So for 12 as root , we wanted 8 + (34+50) * 2 as answer.
    // reframing it, like 34 + (34 + 8 + 50 ) + 50, gives the same above answer.

    // ! Important point :

    // # We make a cut at 10 or 12 or 20, the sum to be added will remain constant.
    // # that will be (34 + 8 + 50) for the range. Therefore have added it the last.

    public static int obst_(int[] val, int si, int ei, int[] freq, int[][] dp) {

        if (si > ei)
            return 0;

        if (dp[si][ei] != -1)
            return dp[si][ei];

        int sum = 0;
        int min = (int) 1e9;
        for (int cut = si; cut <= ei; cut++) {

            int leftRes = obst_(val, si, cut - 1, freq, dp);
            int rightRes = obst_(val, cut + 1, ei, freq, dp);
            sum += freq[cut];

            min = Math.min(min, leftRes + 0 + rightRes);
        }

        return dp[si][ei] = min + sum;
    }

    // ? Bhaiya Method :

    public static int obst(int[] val, int[] freq, int si, int ei, int[][] dp) {
        if (dp[si][ei] != 0)
            return dp[si][ei];
        int minCost = (int) 1e8;

        int sum = 0;

        for (int i = si; i <= ei; i++)
            sum += freq[i];

        for (int cut = si; cut <= ei; cut++) {
            int lres = cut == si ? 0 : obst(val, freq, si, cut - 1, dp);
            int rres = cut == ei ? 0 : obst(val, freq, cut + 1, ei, dp);
            // sum += freq[cut];
            minCost = Math.min(minCost, lres + sum + rres); // sum : sumOfRange(freq, si,ei);
        }

        return dp[si][ei] = minCost; // minCost + sum
    }

    // B <============ Minimum Score Triangulation of Polygon ==============>
    // https://leetcode.com/problems/minimum-score-triangulation-of-polygon/

    // # Basic thing to remember is that two pointss need to be fixed in order to
    // # create a triangle. Here we have fixed starting and ending point in order to
    // # apply dp.

    // Faith same hai. Maine cut alagaya aur maine apne left wale triangle ko bola
    // ki tu apna minTriangulation score leke aaye. Similarly, maine right wale ko
    // ` bola ki tu bhi apna minTriangulation score leke aaja.

    // Ab jo maine cut lagaya, uski wajah se jo triangle banega wo banega si, cut,
    // ei. To uska score mai left aur right ke score mai add karke min value nikal
    // lunga sare possiblilities ke liye aur return karunga.

    // ! Recursion :

    public int minScoreTriangulation_rec(int[] values, int si, int ei) {

        if (ei - si < 2)
            return 0;
        int minScore = (int) 1e9;
        for (int cut = si + 1; cut < ei; cut++) {
            int leftRes = minScoreTriangulation_rec(values, si, cut);
            int rightRes = minScoreTriangulation_rec(values, cut, ei);

            int myTriangleScore = values[si] * values[cut] * values[ei]; // Triangle cut by keeping si and ei fix and
                                                                         // the third point will be cut, hence forming a
                                                                         // triangle.
            minScore = Math.min(minScore, leftRes + myTriangleScore + rightRes);
        }

        return minScore;
    }

    public int minScoreTriangulation_rec(int[] values) {
        int n = values.length;
        return minScoreTriangulation_rec(values, 0, n - 1);
    }

    // ! Memoisation :

    public int minScoreTriangulation_memo(int[] values, int si, int ei, int[][] dp) {

        if (ei - si < 2)
            return dp[si][ei] = 0;
        if (dp[si][ei] != 0)
            return dp[si][ei];
        int minScore = (int) 1e9;
        for (int cut = si + 1; cut < ei; cut++) {
            int leftRes = minScoreTriangulation_memo(values, si, cut, dp);
            int rightRes = minScoreTriangulation_memo(values, cut, ei, dp);

            int myTriangleScore = values[si] * values[cut] * values[ei];
            minScore = Math.min(minScore, leftRes + myTriangleScore + rightRes);
        }

        return dp[si][ei] = minScore;
    }

    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        int[][] dp = new int[n][n];
        return minScoreTriangulation_memo(values, 0, n - 1, dp);
    }

    // b <==================== Unique Binary Search Tree II ==============>
    // https://leetcode.com/problems/unique-binary-search-trees-ii/

    // # Mai har kisi node ko root banne ka mauka de raha hun. Isiliye cut equal to
    // # si se equal to ei tak hai.

    // Faith :

    // Maine left ko bola tu apne jitne bhi structures mai tu break ho sakta hai tu
    // ` uski list merepe leke aaja. Similarly maine right ko bhi same bola.

    // Ab ye soch left se tereko agar x trees milenge aur right se tereko y trees
    // milenge. Aur tera root hai cut.

    // To total number of trees that will have root as cut will be x*y.
    // To bas yhi kiya hai.

    public static class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void generateAllTrees(List<TreeNode> leftRes, List<TreeNode> rightRes, int cut, List<TreeNode> ans) {

        // cut ko root mante hue are combination honge leftRes * right res.
        // Ab kyunki hume answer list mai chahiye, to humne loop ke andar loop laga
        // diya.
        for (TreeNode left : leftRes) {
            for (TreeNode right : rightRes) {
                TreeNode root = new TreeNode(cut);
                root.left = left;
                root.right = right;
                ans.add(root);
            }
        }
    }

    public List<TreeNode> generateTrees(int si, int ei) {

        List<TreeNode> ans = new ArrayList<>();
        if (si > ei) {
            ans.add(null);
            return ans;
        }

        // si ==ei wali condition jab hume single node return karna hai wo loop ne
        // sambhal liya.

        for (int cut = si; cut <= ei; cut++) {

            List<TreeNode> leftRes = generateTrees(si, cut - 1);
            List<TreeNode> rightRes = generateTrees(cut + 1, ei);

            generateAllTrees(leftRes, rightRes, cut, ans);
        }

        return ans;
    }

    public List<TreeNode> generateTrees(int n) {
        return generateTrees(1, n);
    }

    // ! Applying dp to this problem

    // # Dp lagane se ek node ke multiple parent ho jayenge. Kaise ? ?

    // Kyunki hum sidhe ek arraylist return karenge rather than creting a new
    // arraylist. To uuse ab jo address milega, wo change nhi honge. Matlab for
    // examplew : (abc) ne apne aap ko break kiya aur arraylist mai le aaye
    // [3k,4k,5k,6k,7k]. Total 5 ways.

    // Ab jab bhi koi abc ke liye call lagayega, ab use sidhe yhi arraylist return
    // hogi. Aur sab same he address pe point karenge. To multiple parent create ho
    // jayenge, which is not good. Per agar karna pade to yhi hai.

    public void generateAlllBST(List<TreeNode> leftList, List<TreeNode> rightList, List<TreeNode> ans, int num) {
        for (TreeNode ln : leftList) {
            for (TreeNode rn : rightList) {
                TreeNode root = new TreeNode(num);
                root.left = ln;
                root.right = rn;
                ans.add(root);
            }
        }
    }

    public List<TreeNode> generateTrees(int si, int ei, List<TreeNode>[][] dp) {
        List<TreeNode> myAns = new ArrayList<>();
        if (si >= ei) { // equal to condition is handle by the loop below. So not needed.
            TreeNode root = (si == ei ? new TreeNode(si) : null);
            myAns.add(root);
            return myAns;
        }

        if (dp[si][ei] != null)
            return dp[si][ei];

        for (int cut = si; cut <= ei; cut++) {
            List<TreeNode> leftList = generateTrees(si, cut - 1, dp);
            List<TreeNode> rightList = generateTrees(cut + 1, ei, dp);

            generateAlllBST(leftList, rightList, myAns, cut);
        }

        return dp[si][ei] = myAns;
    }

    public List<TreeNode> generateTrees_dp(int n) {
        List<TreeNode>[][] dp = new ArrayList[n][n];
        return generateTrees(1, n, dp);
    }

    // b <========================== Unique Binary Search Trees ==============>
    // https://leetcode.com/problems/unique-binary-search-trees/

    // # Logic is same as above.

    // ! Recursion :
    public int numTrees_rec(int si, int ei) {

        if (si >= ei)
            return 1;

        int total = 0;
        for (int cut = si; cut <= ei; cut++) {

            int leftRes = numTrees_rec(si, cut - 1);
            int rightRes = numTrees_rec(cut + 1, ei);

            total += leftRes * rightRes;
        }
        return total;

    }

    public int numTrees_rec(int n) {
        return numTrees_rec(1, n);
    }

    // ! Memoisation :

    public int numTrees(int si, int ei, int[][] dp) {

        if (si >= ei)
            return 1;

        if (dp[si][ei] != 0)
            return dp[si][ei];

        int total = 0;
        for (int cut = si; cut <= ei; cut++) {

            int leftRes = numTrees(si, cut - 1, dp);
            int rightRes = numTrees(cut + 1, ei, dp);

            total += leftRes * rightRes;
        }
        return dp[si][ei] = total;

    }

    public int numTrees(int n) {
        int[][] dp = new int[n + 1][n + 1];
        return numTrees(1, n, dp);
    }

    // b <==================== House Robber II ====================>
    // https://leetcode.com/problems/house-robber-ii/description/

    // # Faith :

    // Merepe ek house aaya. ya to mai use rob karunga ya to use rob nhi karunga.
    // Agar mai use rob karta hun, to mai next element ko rob nhi kar paaunga,
    // isiliye si + 2 ki call lagayi hai.

    // Agar mai use rob nhi karta hun to mai next element ko rob kar paaunga.
    // isiliye si + 1 ki call lagayi hai.

    // ! Recursion :

    public int rob_rec(int[] nums, int si, int ei) {

        if (si > ei)
            return 0;

        int currHouseRob = nums[si] + rob_rec(nums, si + 2, ei);
        int currHouseNotRob = rob_rec(nums, si + 1, ei);

        return Math.max(currHouseNotRob, currHouseRob);
    }

    public int rob_rec(int[] nums) {
        int n = nums.length;
        if (n == 1)
            return nums[0];

        int excludingLastElement = rob_rec(nums, 0, n - 2);
        int excludingFirstElement = rob_rec(nums, 1, n - 1);
        return (Math.max(excludingLastElement, excludingFirstElement));
    }

    // ! Memoisation :

    public int rob(int[] nums, int si, int ei, int[] dp) {

        if (si > ei) // si out of range gaya, to house hai he nhi. rob value will be 0.
            return 0;

        if (dp[si] != -1)
            return dp[si];
        int currHouseRob = nums[si] + rob(nums, si + 2, ei, dp);
        int currHouseNotRob = rob(nums, si + 1, ei, dp);

        return dp[si] = Math.max(currHouseNotRob, currHouseRob);
    }

    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1)
            return nums[0];

        int[] dp1 = new int[n];
        Arrays.fill(dp1, -1);

        int[] dp2 = new int[n];
        Arrays.fill(dp2, -1);

        // Since the given houses are in circle. So therefore I cannot rob first and the
        // last house at the same time.

        // Isiliye maine pehle First house ko exclude kiya aur uska answer mangaya.
        // Then maine last house ko exclude kiya aur uska answer mangaya and then dono
        // mai jo max tha, maine use return kar diya.

        // This is due to the above reason , that we have created the two dp.
        // Kyunki hum range alag alag pass kar rahe hain. Ek mai 0th index ko exclude
        // kiya aur ek mai n-1 index ko exclude kiya.

        int excludingLastElement = rob(nums, 0, n - 2, dp1);
        int excludingFirstElement = rob(nums, 1, n - 1, dp2);
        return (Math.max(excludingLastElement, excludingFirstElement));
    }

    // b <============== House Robber I ===================>
    // https://leetcode.com/problems/house-robber/description/

    // # Logic is same as above.
    // ! Recursion :

    public int rob_re(int[] nums, int si, int ei) {

        if (si > ei)
            return 0;

        int currHouseRob = nums[si] + rob_re(nums, si + 2, ei);
        int currHouseNotRob = rob_re(nums, si + 1, ei);

        return Math.max(currHouseNotRob, currHouseRob);
    }

    public int rob_re(int[] nums) {
        int n = nums.length;
        if (n == 1)
            return nums[0];
        return rob_re(nums, 0, n - 1);
    }

    // ! Memoisation :

    public int rob_mem(int[] nums, int si, int ei, int[] dp) {

        if (si > ei)
            return 0;

        if (dp[si] != -1)
            return dp[si];
        int currHouseRob = nums[si] + rob_mem(nums, si + 2, ei, dp);
        int currHouseNotRob = rob_mem(nums, si + 1, ei, dp);

        return dp[si] = Math.max(currHouseNotRob, currHouseRob);
    }

    public int rob_(int[] nums) {
        int n = nums.length;
        if (n == 1)
            return nums[0];

        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return rob_mem(nums, 0, n - 1, dp);
    }

    // b <============ 1388. Pizza With 3n Slices ==================>
    // https://leetcode.com/problems/pizza-with-3n-slices/description/

    // ! Recursion :

    // The logic is same.
    // I can only pick slices.length / 3 slices.

    // Pehle sare points ko assume kar ki circle mai hai, aur jaise he maine koi
    // slice pick kiya, circle shrink ho raha hai.
    // For example : [1,2,3,4,5,6]

    // Ab agar mai 1 pick kiya to mai 6 nhi pick kar sakta and vise versa.
    // To mai do alag alag range pass karta hun. Aur dono mai max nikalke return
    // karta hun.

    // # Faith :

    // Merepe do choices hai. Ya to mai slice pick kar sakta hun ya to mai nhi pick
    // kar sakta.

    // Agar mai pick karta hun to mai agli slice +2 wali he pick kar paunga.
    // Aur agar mai nhi pick karta, to mai agli slice +1 wali pick kar sakta hun.

    // To mai yahan pe sirf ek insaan ke bare mai he soch raha hun, jo hai Alice.
    // ` Bob apne aap noOfSlices wale check se handle ho raha hai.

    public int maxSizeSlices_rec(int[] slices, int si, int ei, int noOfSlices) {

        if (si > ei || noOfSlices == 0)
            return 0;
        int pickSlice = slices[si] + maxSizeSlices_rec(slices, si + 2, ei, noOfSlices - 1);
        int notPickSlice = maxSizeSlices_rec(slices, si + 1, ei, noOfSlices);

        return Math.max(pickSlice, notPickSlice);
    }

    public int maxSizeSlices_(int[] slices) {
        int n = slices.length;
        int noOfSlices = n / 3;
        // Since I cannot pick first and last slice both at the same time.
        int pickFirstSlice = maxSizeSlices_rec(slices, 0, n - 2, noOfSlices);
        int pickLastSlice = maxSizeSlices_rec(slices, 1, n - 1, noOfSlices);

        return Math.max(pickFirstSlice, pickLastSlice);
    }

    // # Basic dry run :

    // Array hai [1,2,3,4,5,6].
    // Maine range pass ki (0,4)

    // To pehle 1 pick hua. To mai 2 ko pick nhi kar sakta. aur 6 ko bhi.
    // Iske baad jo array shrink hua (2,4). To ab wo isi mai koi next slice pick
    // karega.
    // To maine ab 3 pick kiya. to alice ne 4 pick kiya aur bob ne 5. Aise he sare
    // combination check ho jeyenge.

    // ! Memoisation :

    public int maxSizeSlices(int[] slices, int si, int ei, int noOfSlices, int[][] dp) {

        if (si > ei || noOfSlices == 0)
            return 0;

        if (dp[si][noOfSlices] != -1)
            return dp[si][noOfSlices];
        int pickSlice = slices[si] + maxSizeSlices(slices, si + 2, ei, noOfSlices - 1, dp);
        int notPickSlice = maxSizeSlices(slices, si + 1, ei, noOfSlices, dp);

        return dp[si][noOfSlices] = Math.max(pickSlice, notPickSlice);
    }

    public int maxSizeSlices(int[] slices) {
        int n = slices.length;
        int noOfSlices = n / 3;

        int[][] dp1 = new int[n + 1][noOfSlices + 1];
        for (int[] d : dp1)
            Arrays.fill(d, -1);

        int[][] dp2 = new int[n + 1][noOfSlices + 1];
        for (int[] d : dp2)
            Arrays.fill(d, -1);
        // Since I cannot pick first and last slice both at the same time.
        int pickFirstSlice = maxSizeSlices(slices, 0, n - 2, noOfSlices, dp1);
        int pickLastSlice = maxSizeSlices(slices, 1, n - 1, noOfSlices, dp2);

        return Math.max(pickFirstSlice, pickLastSlice);
    }
}