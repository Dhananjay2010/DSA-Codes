import java.util.Arrays;

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
        String str = "";
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

    // ! Important note :

    // # Humne abhi tak cut type mai ek he kaam kiya hai. Har cut ke liye maine left
    // # aur right dono ki value nikali. Left aur right se jo aay usme jahan pe cut
    // # lagaya, uska operation kiya. Aur use compare karte gye.

    // # Bas yhi kar rahe hain hum.

}