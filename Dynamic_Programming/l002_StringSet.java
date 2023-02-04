import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;

public class l002_StringSet {

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

    public int longestPalindromeSubseq_memo(String s) {
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

    // b <=============== Regular Expression Matching ============>
    // https://leetcode.com/problems/regular-expression-matching/

    // ! Recursion :

    // ! Test case to dry run on :

    // ? "aab" , "c*a*b"

    // # Faith :

    // Agar mere dono character equal hai to mai n -1 string ko bolunga tu apne aap
    // ke m - 1 se map karne ki try kar

    // Ab agar '.' aata hai to wo kisi bhi ek character ko match kar sakta
    // hai, to same uper wali call lagegi

    // Ab agar '*' aata hai to do batein ho sakti hai.
    // 1. Wo empty string ko map kare. (simple call of n, m-2); // 0 * a = ""
    // == > why m-2 because since * can map the preceeding character, therefore if
    // star says it does want to map anyone, then it will be mapping an empty
    // string, hence m-2.

    // 2. Wo preceeding character ko map karega.

    // Ab kyunko bola hai ki '*' Matches zero or more of the preceding element,
    // Iska matlab ye hai ki '*' kitne times bhi apne preceeding character ko map
    // kar sakta hai. To '*' can replace n times preceeding character, where n can
    // `be 0,1,2 ...

    // So in a*, * can be "", a,aa,aaa,aaaa, ......
    // 0 * a = ""
    // 1* a = "a"
    // 2 * a= "aa"

    // Hence goes on ....

    // ? So for example ("aab" , "c*a*b")

    // THen the first basic call will be n-1,m-1 since both the character are same.
    // this reduces the string to

    // ? So now ("aa" , "c*a*")

    // Now the two calls will be
    // 1. ("aa","c*").
    // 2. ("a", "c*a*")

    // In second call, keeping the * alive since it can map n number of preceeding
    // character.

    public boolean isMatch_RE_recu(String str1, String str2, int n, int m) {

        if (n == 0 && m == 0) // If both becomes 0, then str1 has been mapped by str2
            return true;
        if (m == 0) // if n!=0 && m ==0, then the string cannot be mapped
            return false;

        // ! Why $ used ?
        // For the edge case where n=0 but m!=0 and str2 is a*b*c*, so the each of star
        // can map an empty string, hence ("","a*b*c*") should return true.

        char ch1 = n > 0 ? str1.charAt(n - 1) : '$';
        char ch2 = str2.charAt(m - 1);

        if (ch1 != '$' && (ch1 == ch2 || ch2 == '.')) {
            return isMatch_RE_recu(str1, str2, n - 1, m - 1);
        } else if (ch2 == '*') {

            boolean res = false;
            res = res || isMatch_RE_recu(str1, str2, n, m - 2); // Mapping an empty string( " " )

            // m >1 can be removed since if ch2="*" then m will always be greater than 1 as
            // given.
            if (n > 0 && m > 1) { // n > 0 check added since we do not want this call to happen for the above edge
                                  // case . i.e ("","a*b*c*")
                char preceedingCharacter = str2.charAt(m - 2);
                if (preceedingCharacter == ch1 || preceedingCharacter == '.') {
                    res = res || isMatch_RE_recu(str1, str2, n - 1, m); // Mapping the sequence of preceeding character.
                }
            }
            return res;
        } else {
            return false;
        }
    }

    public boolean isMatch_RE_recu(String s, String p) {
        int n = s.length(), m = p.length();
        return isMatch_RE_recu(s, p, n, m);
    }

    // ! Memoisation :

    // # Don't do the mistake of making the boolean dp since true and false are the
    // # part of the answer. False denote that the string cannot be mapped and true
    // # Denotes that the string can be mapped.

    // ? Therefore make a dp of integer and prefill it with -1.
    // ? 0 denoting false and 1 denoting true value respectively.

    public int isMatch_RE_memo(String str1, String str2, int n, int m, int[][] dp) {

        if (n == 0 && m == 0)
            return dp[n][m] = 1;
        if (m == 0)
            return dp[n][m] = 0;

        if (dp[n][m] != -1)
            return dp[n][m];
        char ch1 = n > 0 ? str1.charAt(n - 1) : '$';
        char ch2 = str2.charAt(m - 1);

        if (ch1 != '$' && (ch1 == ch2 || ch2 == '.')) {
            return dp[n][m] = isMatch_RE_memo(str1, str2, n - 1, m - 1, dp);
        } else if (ch2 == '*') {

            boolean res = false;
            res = res || isMatch_RE_memo(str1, str2, n, m - 2, dp) == 1;

            if (n > 0 && m > 1) {
                char preceedingCharacter = str2.charAt(m - 2);
                if (preceedingCharacter == ch1 || preceedingCharacter == '.') {
                    res = res || isMatch_RE_memo(str1, str2, n - 1, m, dp) == 1;
                }
            }
            return dp[n][m] = res ? 1 : 0;
        } else {
            return dp[n][m] = 0;
        }
    }

    public boolean isMatch_RE_memo(String s, String p) {
        int n = s.length(), m = p.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return isMatch_RE_memo(s, p, n, m, dp) == 1 ? true : false;
    }

    // ! Observation and Tabulation :

    public int isMatch_RE_tabu(String str1, String str2, int N, int M, int[][] dp) {
        for (int m = 0; m <= M; m++) {
            for (int n = 0; n <= N; n++) {
                if (n == 0 && m == 0) {
                    dp[n][m] = 1;
                    continue;
                }
                if (m == 0) {
                    dp[n][m] = 0;
                    continue;
                }
                char ch1 = n > 0 ? str1.charAt(n - 1) : '$';
                char ch2 = str2.charAt(m - 1);
                if (ch1 != '$' && (ch1 == ch2 || ch2 == '.')) {
                    dp[n][m] = dp[n - 1][m - 1]; // isMatch_RE_memo(str1, str2, n - 1, m - 1, dp);
                } else if (ch2 == '*') {
                    boolean res = false;
                    if (m > 1) {
                        res = res || dp[n][m - 2] == 1;
                    }
                    if (n > 0 && m > 1) {
                        char preceedingCharacter = str2.charAt(m - 2);
                        if (preceedingCharacter == ch1 || preceedingCharacter == '.') {
                            res = res || dp[n - 1][m] == 1;
                        }
                    }
                    dp[n][m] = res ? 1 : 0;
                } else {
                    dp[n][m] = 0;
                }
            }
        }
        return dp[N][M];
    }

    public boolean isMatch(String s, String p) {
        int n = s.length(), m = p.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return isMatch_RE_tabu(s, p, n, m, dp) == 1 ? true : false;
    }

    // b <==================== Uncrossed Lines ===================>
    // https://leetcode.com/problems/uncrossed-lines/

    // ! Recursion :
    public int maxUncrossedLines_recu(int[] nums1, int[] nums2, int n, int m) {

        if (n == 0 || m == 0) {
            return 0;
        }

        int ch1 = nums1[n - 1];
        int ch2 = nums2[m - 1];

        if (ch1 == ch2) {
            return maxUncrossedLines_recu(nums1, nums2, n - 1, m - 1) + 1;
        } else {
            int leftCall = maxUncrossedLines_recu(nums1, nums2, n - 1, m);
            int rightCall = maxUncrossedLines_recu(nums1, nums2, n, m - 1);
            return Math.max(leftCall, rightCall);
        }

    }

    public int maxUncrossedLines_recu(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        return maxUncrossedLines_recu(nums1, nums2, n, m);
    }

    // ! Memoisation :

    public int maxUncrossedLines_memo(int[] nums1, int[] nums2, int n, int m, int[][] dp) {

        if (n == 0 || m == 0) {
            return dp[n][m] = 0;
        }

        if (dp[n][m] != -1)
            return dp[n][m];
        int ch1 = nums1[n - 1];
        int ch2 = nums2[m - 1];

        if (ch1 == ch2) {
            return dp[n][m] = maxUncrossedLines_memo(nums1, nums2, n - 1, m - 1, dp) + 1;
        } else {
            int leftCall = maxUncrossedLines_memo(nums1, nums2, n - 1, m, dp);
            int rightCall = maxUncrossedLines_memo(nums1, nums2, n, m - 1, dp);
            return dp[n][m] = Math.max(leftCall, rightCall);
        }

    }

    public int maxUncrossedLines_memo(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return maxUncrossedLines_memo(nums1, nums2, n, m, dp);
    }

    // ! Observation & Tabulation :

    public int maxUncrossedLines_tabu(int[] nums1, int[] nums2, int N, int M, int[][] dp) {

        for (int m = 0; m <= M; m++) {
            for (int n = 0; n <= N; n++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = 0;
                    continue;
                }

                int ch1 = nums1[n - 1];
                int ch2 = nums2[m - 1];

                if (ch1 == ch2) {
                    dp[n][m] = dp[n - 1][m - 1] + 1;
                } else {
                    int leftCall = dp[n - 1][m];
                    int rightCall = dp[n][m - 1];
                    dp[n][m] = Math.max(leftCall, rightCall);
                }
            }
        }
        return dp[N][M];
    }

    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return maxUncrossedLines_tabu(nums1, nums2, n, m, dp);
    }

    // b <============== Max Dot Product of Two Subsequences ================>
    // https://leetcode.com/problems/max-dot-product-of-two-subsequences/

    // ! Recursion :

    // We are here checking all the possibilities.
    // n-1, m-1
    // n -1, m
    // n, m-1
    // n,m

    // To maine bola ki tum jao aur apna max dot product leke aao. Jo bhi tumme max
    // aayega mai use return kar dunga.

    // For Example [2,1,-2,5] , [3,0,6]
    // The 4 situation will be

    // # Faith :
    // 1. Calcualating 5 * 6.
    // 2. Maine bole [2,1,-2] ,[3,0] ko ki tum apna max dot product leke aao, mai
    // ` usme apne index mutiplication ki value add kar dunga.
    // 3. Maine bola [2,1,-2] , [3,0,6] ki tum apni max dot product ki value lao
    // 4. Maine bola [2,1,-2,5] , [3,0] ki tum apni max dot product ki value lao
    // 5. Ek aur case ho sakta hai ki hum dono ko he include nhi kar rahe hain, jo
    // ki hidden hota hain call as discussed above. wo simple (n-1,m-1 ki call
    // hoti).
    // To isko lagane ki jaroorat nhi hai.

    // Ab in charon mai se jo max hoga whi to answer hoga.

    public int maximum(int... arr) {
        int max = arr[0];

        for (int ele : arr) {
            max = Math.max(max, ele);
        }
        return max;
    }

    public int maxDotProduct_recu(int[] nums1, int[] nums2, int n, int m) {
        if (n == 0 || m == 0)
            return -(int) 1e8;

        int value = nums1[n - 1] * nums2[m - 1];
        int acceptBothNumbers = maxDotProduct_recu(nums1, nums2, n - 1, m - 1) + value;
        int a = maxDotProduct_recu(nums1, nums2, n - 1, m);
        int b = maxDotProduct_recu(nums1, nums2, n, m - 1);

        return maximum(value, acceptBothNumbers, a, b);

    }

    public int maxDotProduct_recu(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        return maxDotProduct_recu(nums1, nums2, n, m);
    }

    // ! Memoisation :

    public int maxDotProduct_memo(int[] nums1, int[] nums2, int n, int m, int[][] dp) {
        if (n == 0 || m == 0)
            return dp[n][m] = -(int) 1e8;

        if (dp[n][m] != -(int) 1e9)
            return dp[n][m];
        int value = nums1[n - 1] * nums2[m - 1];
        int acceptBothNumbers = maxDotProduct_memo(nums1, nums2, n - 1, m - 1, dp) + value;
        int a = maxDotProduct_memo(nums1, nums2, n - 1, m, dp);
        int b = maxDotProduct_memo(nums1, nums2, n, m - 1, dp);

        return dp[n][m] = maximum(value, acceptBothNumbers, a, b);

    }

    public int maxDotProduct_memo(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -(int) 1e9);
        return maxDotProduct_memo(nums1, nums2, n, m, dp);
    }

    // ! Observation & Tabulation :

    public int maxDotProduct_tabu(int[] nums1, int[] nums2, int N, int M, int[][] dp) {

        for (int m = 0; m <= M; m++) {
            for (int n = 0; n <= N; n++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = -(int) 1e8;
                    continue;
                }
                int value = nums1[n - 1] * nums2[m - 1];
                int acceptBothNumbers = dp[n - 1][m - 1] + value;
                int a = dp[n - 1][m];
                int b = dp[n][m - 1];

                dp[n][m] = maximum(value, acceptBothNumbers, a, b);
            }
        }

        return dp[N][M];
    }

    public int maxDotProduct(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -(int) 1e9);
        return maxDotProduct_tabu(nums1, nums2, n, m, dp);
    }

    // b <===============Longest Palindromic Substring ===============>
    // https://leetcode.com/problems/longest-palindromic-substring/

    // ! Ye sidhe tabulation se karna hai. iska recursion jugad hai jahan pe static
    // ! use karna pad jata hai

    // Here boolean dp is used in which true singnifies the substring is a
    // palindrome.

    // Dry run on "gegpeepf"

    public String longestPalindrome(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        int totalPalindromeCount = 0; // Just to calculate total numbers of palindrome in the string.
        int smallIndex = 0; // To save the smaller index where we find the largest palindrome.
        int largeIndex = 0; // To save the larger index where we find the largest palindrome.
        int maxPalindromeLength = 0; // To get the largest length of the palindrome
        for (int gap = 0; gap < n; gap++) { // gap represent a diagonal. gap =0 represent the first diagonal from (0,0)
                                            // to (n,n). gap =1 represent second diagonal from (0,1) to (n-1,n). Hence
                                            // it goes on.
            for (int i = 0, j = gap; i < n && j < n; i++, j++) {
                if (i == j) { // every single alphabet is a palindrome in itself.
                    dp[i][j] = true;
                    continue;
                }
                if (j < i) { // Just for remembering, otherwise not required since initial array will be
                             // filled with false;
                    dp[i][j] = false;
                    continue;
                }

                char ch1 = s.charAt(i);
                char ch2 = s.charAt(j);

                if (ch1 != ch2) { // It the character are not equal, cannot be a palindrome.
                    dp[i][j] = false;
                } else {
                    if (gap == 1) { // edge case, for 2 length string, if both the character are equal then also it
                                    // is a palindrome. For Example, dry run on string "gegpeepf", here ee can also
                                    // ` be a palindrome with length 2.
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                        // If both the characters are equal, then I will have to ask from
                        // the elements between both the character to tell if they are
                        // palindrome. For ex : "peep", the p at 0th and 3rd index are
                        // equal. But the whole string "peep" is palindrome depends upon
                        // wherther "ee" the string between is palindrome or not. If it is
                        // palindrome, the string "peep" is palindrome. But the string
                        // "pekp" will not be a palindrome for the same reason.
                    }
                }

                if (dp[i][j]) {
                    totalPalindromeCount++;
                    if (j - i + 1 > maxPalindromeLength) { // j-i+1 will be the length of the palindrome.
                        maxPalindromeLength = j - i + 1;
                        smallIndex = i;
                        largeIndex = j;
                    }
                }
            }
        }

        return s.substring(smallIndex, largeIndex + 1);
    }

    // b <==================== Longest Common Substring =============>
    // https://practice.geeksforgeeks.org/problems/longest-common-substring1452/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // It is similar to subsequence but in substring we don;t have the choice to
    // choose the character.

    // Also do substring direclty by tabulation, like above.
    int longestCommonSubstr_(String str1, String str2, int N, int M, int[][] dp) {
        // code here
        int maxLength = 0, ei = 0;
        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = 0;
                    continue;
                }

                char ch1 = str1.charAt(n - 1);
                char ch2 = str2.charAt(m - 1);

                if (ch1 == ch2) {
                    dp[n][m] = dp[n - 1][m - 1] + 1;
                    if (maxLength < dp[n][m]) { // Storing the max length in a variable since maxLength can be found
                                                // anywhere in the string.
                        maxLength = dp[n][m];
                        ei = m - 1;
                    }
                } else {
                    // Here in substring, don't have the choice to choose the character, therefore
                    // cannot take max as we have taken while calculating common subsequence.
                    dp[n][m] = 0;
                }
            }
        }
        String longestCommonSubstring = str2.substring(ei - maxLength + 1, ei + 1);
        return maxLength;
    }

    int longestCommonSubstr(String S1, String S2, int n, int m) {
        // code here
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(dp, -1);
        return longestCommonSubstr_(S1, S2, n, m, dp);
    }

    // b <================ Delete Operation for Two Strings =======================>
    // https://leetcode.com/problems/delete-operation-for-two-strings/

    // # It is same like longest common subsequence. Therefore the faith moreover
    // # remains the same, just with a slight change.

    // ! Recursion :

    public int minDistance_del_recu(String str1, String str2, int n, int m) {
        if (n == 0 || m == 0) {
            return n == 0 ? m : n;
            // For test case like ("abc", "") , ("","abc")
            // For example ("abc", ""), the number of steps to make both string equal is 3
            // since character a, b, c have to be deleted to make equal to "".
            // Similarly for ("","abc")
            // And if both the strings are ("","") , then we have to return 0;
        }

        char ch1 = str1.charAt(n - 1);
        char ch2 = str2.charAt(m - 1);
        int count = 0;
        if (ch1 == ch2) {
            count = minDistance_del_recu(str1, str2, n - 1, m - 1); // If both the characters are equal then no need to
                                                                    // delete anything
        } else {
            // For test case ("sea", "eat"), the two calls will be
            // 1. ("ea", "eat")
            // 2. ("sea", "at")
            // Out of two which ever returns the min we add + 1;
            // Since here we are deleting one character, therefore + 1 is done.
            count = Math.min(minDistance_del_recu(str1, str2, n - 1, m), minDistance_del_recu(str1, str2, n, m - 1))
                    + 1;
        }

        return count;
    }

    public int minDistance_del_recu(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        return minDistance_del_recu(word1, word2, n, m);
    }

    // ! Memoisation :

    public int minDistance_del_memo(String str1, String str2, int n, int m, int[][] dp) {
        if (n == 0 || m == 0) {
            return dp[n][m] = n == 0 ? m : n;
        }
        if (dp[n][m] != -1)
            return dp[n][m];
        char ch1 = str1.charAt(n - 1);
        char ch2 = str2.charAt(m - 1);
        int count = 0;
        if (ch1 == ch2) {
            count = minDistance_del_memo(str1, str2, n - 1, m - 1, dp);
        } else {
            count = Math.min(minDistance_del_memo(str1, str2, n - 1, m, dp),
                    minDistance_del_memo(str1, str2, n, m - 1, dp)) + 1;
        }
        return dp[n][m] = count;
    }

    public int minDistance_del_memo(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return minDistance_del_memo(word1, word2, n, m, dp);
    }

    // ! Observation && Tabulation :
    public int minDistance_del_tabu(String str1, String str2, int N, int M, int[][] dp) {
        for (int m = 0; m <= M; m++) {
            for (int n = 0; n <= N; n++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = n == 0 ? m : n;
                    continue;
                }
                char ch1 = str1.charAt(n - 1);
                char ch2 = str2.charAt(m - 1);
                int count = 0;
                if (ch1 == ch2) {
                    count = dp[n - 1][m - 1]; // minDistance(str1, str2, n - 1, m - 1, dp);
                } else {
                    count = Math.min(dp[n - 1][m], dp[n][m - 1]) + 1;
                }
                dp[n][m] = count;
            }
        }
        return dp[N][M];

    }

    public int minDistance_del_tabu(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return minDistance_del_tabu(word1, word2, n, m, dp);
    }

    // ! Using longest common subsequence.

    // find the longest common subsequence.
    // Then str1.length() + str2.length - 2 * lcss(longest common subsequence)

    public static int lcss_DP(String str1, String str2, int N, int M, int[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = 0;
                    continue;
                }

                if (str1.charAt(n - 1) == str2.charAt(m - 1))
                    dp[n][m] = dp[n - 1][m - 1] + 1;// lcss(str1, str2, n - 1, m - 1, dp) + 1;
                else
                    dp[n][m] = Math.max(dp[n - 1][m], dp[n][m - 1]);
            }
        }

        return dp[N][M];
    }

    public int longestCommonSubsequence_lcss(String text1, String text2) {
        int n = text1.length(), m = text2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        int ans = lcss_DP(text1, text2, n, m, dp);

        return ans;
    }

    public int minDistance_lcss(String word1, String word2) {
        return word1.length() + word2.length() - 2 * longestCommonSubsequence(word1, word2);
    }

    // b <============= 131. Palindrome Partitioning ==============>
    // https://leetcode.com/problems/palindrome-partitioning/

    // Since we want to get all the answers, so we have to use recursion.
    // Now we will firstly fill the palindrome dp.
    // Then as requied, we will add a cut if the substring is a palindrome and will
    // similarly add it to the answer arrayList.

    public void partition(String str, int si, int ei, List<List<String>> ans, List<String> myAns,
            boolean[][] pdp) {

        if (si == str.length()) {
            List<String> recAns = new ArrayList<>(myAns);
            ans.add(recAns);
            return;
        }

        for (int cut = si; cut <= ei; cut++) {
            if (pdp[si][cut]) { // If the subtring is palindrome ,then added a cut.
                myAns.add(str.substring(si, cut + 1));
                partition(str, cut + 1, ei, ans, myAns, pdp);
                myAns.remove(myAns.size() - 1);
            }
        }
    }

    public List<List<String>> partition(String s) {
        int n = s.length();
        boolean[][] pdp = new boolean[n][n];
        isSubstringPalindrome(s, pdp); // Filled the palindrome dp so that I can know in O(1) time if a substring is
                                       // palindrome or not.

        List<List<String>> ans = new ArrayList<>();
        List<String> myAns = new ArrayList<>();

        partition(s, 0, n - 1, ans, myAns, pdp);
        return ans;

    }

    public void isSubstringPalindrome(String str, boolean[][] pdp) {

        int n = str.length();
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; i < n && j < n; i++, j++) {
                if (gap == 0) {// i==j
                    pdp[i][j] = true;
                    continue;
                }

                char ch1 = str.charAt(i);
                char ch2 = str.charAt(j);

                if (ch1 == ch2) {
                    if (gap == 1) // for two length palindrome edge case.
                        pdp[i][j] = true;
                    else {
                        pdp[i][j] = pdp[i + 1][j - 1];
                    }
                } else {
                    pdp[i][j] = false;
                }
            }
        }
    }

    // b <=================== 132. Palindrome Partitioning II ================>
    // https://leetcode.com/problems/palindrome-partitioning-ii/

    // # Faith :

    // ? Maine kaha agar meri substring palindrome hai to mai wahan pe cut lagaunga
    // ? aur baki bachi hui string ko boulunga ki tum apne aap ko minimum cut mai
    // ? leke aajao. Jo cut mujhe min result lake dega, mai use return kar dunga.

    // ! For Example :

    // Taking a string : "fafaaabaageeg"
    // Now the cuts for following string are :

    // # Cut string , (String forwarded)
    // 1. "f" ("afaaabaageeg")
    // 2. "faf" ("aaabaageeg")

    // # Hence the recursive tree goes on.

    public int minCut(String s, int si, int ei, boolean[][] pdp, int[] dp) {

        // if (si == s.length()){
        // return dp[si] = 0;
        // }

        // If you want to use the above if statement, then we have to subtract 1 from
        // the final answer. It is because we are counting an extra cut. Dry run and
        // ` understand.
        if (pdp[si][ei]) // If my substring is already a palindrome, then there is no need for any cut;
            return 0;
        if (dp[si] != -1)
            return dp[si];
        int minAns = (int) 1e8;
        for (int cut = si; cut <= ei; cut++) {
            if (pdp[si][cut]) {
                minAns = Math.min(minAns, minCut(s, cut + 1, ei, pdp, dp) + 1); // +1 added for the cut
            }
        }

        return dp[si] = minAns;
    }

    public int minCut(String s) {

        int n = s.length();
        boolean[][] pdp = new boolean[n][n];

        isSubstringPalindrome(s, pdp); // To fill the palindrome dp so that we can get in O(1) if a substring is a
                                       // palindrome or not.
        int[] dp = new int[n + 1];
        // Here used 1-D dp since only the starting index varies.
        Arrays.fill(dp, -1);

        return minCut(s, 0, n - 1, pdp, dp);

    }

    public static void isSubstringPalindrome_(String str, boolean[][] pdp) {

        int n = str.length();
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; i < n && j < n; i++, j++) {
                if (gap == 0) {
                    pdp[i][j] = true;
                    continue;
                }

                char ch1 = str.charAt(i);
                char ch2 = str.charAt(j);

                if (ch1 == ch2) {
                    if (gap == 1) {
                        pdp[i][j] = true;
                    } else {
                        pdp[i][j] = pdp[i + 1][j - 1];
                    }
                } else {
                    pdp[i][j] = false;
                }
            }
        }
    }

    // b <================= Palindrome Partitioning III ================>

    public static void palinromicSubstring(String str, boolean[][] dp) { // Wrote just to dry run the concept.
        int n = str.length();
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; j < n && i < n; i++, j++) {
                if (gap == 0) {
                    dp[i][j] = true;
                    continue;
                }

                char ch1 = str.charAt(i);
                char ch2 = str.charAt(j);

                if (ch1 != ch2) {
                    dp[i][j] = false;
                } else {
                    if (gap == 1)
                        dp[i][j] = true;
                    else
                        dp[i][j] = dp[i + 1][j - 1];
                }
            }
        }
    }

    // Is question mai pehle hume minChanges ki dp fill karni hogi taki hume
    // minChanges kisi bhi substring ke liye O(1) mai mila jaye.

    // # Faith :

    // Maine apni ek string kati aur maiine ek bachi hui string ko bola ki tum apne
    // aap ko k string mai break karke apni minChange ki value return karde. Main
    // sabme se min value nikal ke return kardunga.

    public static void minChanges(String str, int[][] dp) {
        // YE function bilkul same hai jaise palindromic substring ki dp fill karte
        // hain. Usko dry run kar lena, ye bhi same waise he hai.
        int n = str.length();
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; i < n && j < n; i++, j++) {
                if (gap == 0) {
                    dp[i][j] = 0; // har single character ek palindrome hai, to 0 changes required hai isko
                                  // palindrome mai convert karne ke liye
                    continue;
                }

                char ch1 = str.charAt(i);
                char ch2 = str.charAt(j);

                if (ch1 != ch2) {
                    // Agar mere dono character equal nhi hai to mujhe
                    // For ex : "abbbc" to iske liye mujhe jo beech ki string ke min changes + 1
                    // + 1 isiliye kyunki palindrome ke liye ya to a c mai convert hoga ya c a mai.
                    // To isiliye +1 kiya.
                    dp[i][j] = dp[i + 1][j - 1] + 1;
                } else {

                    // Ab gar mere dono character equal hai, to mujhe palindrome mai convert karne
                    // ke liye utne he changes chahiyein honge jitne meri beech ki string ko chahiye
                    // honge.
                    if (gap == 1) {
                        dp[i][j] = 0; // for condition like "ee", dono character equal hai to no changes required.
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
            }
        }
    }

    public int palindromePartition(String str, int k, int si, int ei, int[][] dp, int[][] minChangesDp) {

        if (si == str.length()) // Agar meri string he khatam ho gyi, to answer kabhi milega he nhi.
            return (int) 1e9;

        if (str.length() - si < k) // For condition like "abc, 5", to abc ko 5 set mai break karna hai, which is
                                   // not possible.
            return (int) 1e9;

        // # Rather than having two checks, bhaiya wrorte a single check like this below.
        // if (str.length() - si <= k) { 
        //     return dp[si][k] = str.length() - si == k ? 0 : (int) 1e9;
        // }
        
        if (k == 1) // Ab agar K==1 bacha hai to sidhe uske min changes he return kardo.
            return dp[k][si] = minChangesDp[si][ei];

        if (dp[k][si] != -1)
            return dp[k][si];

        int minAns = (int) 1e9;
        for (int cut = si; cut <= ei; cut++) {

            int myStringCutValue = minChangesDp[si][cut];
            int remainingStringValue = palindromePartition(str, k - 1, cut + 1, ei, dp, minChangesDp);

            if (remainingStringValue != (int) 1e9) // Since hum chahte hain ki whi answer evaluate ho jiska answer ban
                                                   // sakta hai. To agar remainingStringValue ki value (int)1e9 hai to
                                                   // `uper se wali condition hit hui hai jiska matlba hai ki answer
                                                   // create he nhi ho sakta.
                minAns = Math.min(minAns, myStringCutValue + remainingStringValue);
        }

        return dp[k][si] = minAns;
    }

    public int palindromePartition(String str, int k) {
        int n = str.length();
        int[][] minChangesDp = new int[n][n];
        minChanges(str, minChangesDp);

        int[][] dp = new int[k + 1][n + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        return palindromePartition(str, k, 0, n - 1, dp, minChangesDp);
    }

    // b <============== Count subsequences of type a^i, b^j, c^k ==============>
    // https://practice.geeksforgeeks.org/problems/count-subsequences-of-type-ai-bj-ck4425/1

    // # Is question ko thoda aise soch :

    // # Terepe already kuch sequence ka count hai. For Example :

    // a^i has already 4 subsequence. (aCount)
    // a^i b^j has already 3 subsequence (bCount)
    // a^i b^j c^k has already 5 subsequence (cCount)

    // ? Ab agar charater c aata hai, to uske pass do choice hongi kyunki humko
    // ? subsequence nikalne hain. Ya to wo aayega ya to wo nhi aayega.

    // # Ab agar c nhi aata hai, to already jitne a^i b^j c^k ke subsequence the, wo
    // # to rahenge he.

    // # Ab agar c aata hai to, wo a^i b^j ke sath attach hoke a^i b^j c^k wala
    // # subsequence bana sakta hai. Aur wo a^i b^j c^k walon ke sath bhi attach
    // # hoke subsequence bana sakta hai.

    // Therefore isiliye cCount= cCount{Jab wo nhi aayega} + (bCount (jab wo a^i b^j
    // ke sath attach hoga) + cCount(jab wo a^i b^j c^k ke sath attach hoga.))

    // ? Similarly Ab agar charater b aata hai, to uske pass do choice hongi kyunki
    // ? humko subsequence nikalne hain. Ya to wo aayega ya to wo nhi aayega.

    // # Ab agar b nhi aata hai, to already jitne a^i b^j ke subsequence the, wo
    // # to rahenge he.

    // # Ab agar b aata hai to, wo a^i ke sath attach hoke a^i b^j wala
    // # subsequence bana sakta hai. Aur wo a^i b^j walon ke sath bhi attach
    // # hoke subsequence bana sakta hai.

    // Therefore isiliye bCount= bCount{Jab wo nhi aayega} + (aCount (jab wo a^i
    // ke sath attach hoga) + bCount(jab wo a^i b^j ke sath attach hoga.))

    // ? Similarly Ab agar charater a aata hai, to uske pass do choice hongi kyunki
    // ? humko subsequence nikalne hain. Ya to wo aayega ya to wo nhi aayega.

    // # Ab agar a nhi aata hai, to already jitne a^i ke subsequence the, wo
    // # to rahenge he.

    // # Ab agar a aata hai to, wo " " ke sath attach hoke a^i wala
    // # subsequence bana sakta hai. Aur wo a^i walon ke sath bhi attach
    // # hoke subsequence bana sakta hai.

    // Therefore isiliye aCount= aCount{Jab wo nhi aayega} + (emptyCount (jab wo ""
    // ke sath attach hoga) + aCount(jab wo a^i ke sath attach hoga.))

    // # How to apply Mod

    // Mod lagane ka simple rule, pehle overflow hone do value ko uske baad mod
    // karke range mai leke aao

    // aCount = (aCount + (emptyCount + aCount) % mod) % mod;
    // (emptyCount + aCount) == > Pehle ispe mod lagaya kyunki inka addition range
    // ke bahar ja sakta hain

    // ab iske baad jo sum aayega usko jab aCount ke sath add kiya, to wo bhi range
    // ke bahar jaa sakta hai. Isiliye usko bhi mod kiya.

    public int fun(String str) {

        int n = str.length();
        long emptyCount = 1, aCount = 0, bCount = 0, cCount = 0;
        int mod = (int) (1e9) + 7;
        for (int i = 0; i < n; i++) {

            char ch = str.charAt(i);

            if (ch == 'a')
                aCount = (aCount + (emptyCount + aCount) % mod) % mod;
            else if (ch == 'b')
                bCount = (bCount + (aCount + bCount) % mod) % mod;
            else if (ch == 'c')
                cCount = (cCount + (bCount + cCount) % mod) % mod;
        }

        return (int) cCount;
    }

    // ! Important Point :

    // Its follow up will be :
    // # To Count subsequences of type a^i, b^j, c^k, d^l, e^m, f^n

    // ? To resolve this use array. Rest the logic remains the same.

    // b <============ Word Break ===============>
    // https://leetcode.com/problems/word-break/

    // ! Recursion :

    // ? Faith
    // # The faith remains the same.
    // # Maine bola agar mera substring exist karta hai set mai to mai bachi hui
    // # string ko bolunga ki tu apne aap ko break karke leya. Agar wo le aayega to
    // # simple true return kar denge.

    public boolean wordBreak_recu(String str, int si, HashSet<String> dict) {

        if (si == str.length())
            return true;

        boolean res = false;
        for (int cut = si; cut < str.length(); cut++) {
            if (dict.contains(str.substring(si, cut + 1))) {
                res = res || wordBreak_recu(str, cut + 1, dict);
            }
        }

        return res;

    }

    public boolean wordBreak_recu(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>();
        for (String str : wordDict) {
            set.add(str);
        }

        return wordBreak_recu(s, 0, set);
    }

    // ! Memoisation :

    // # Since true and false can be the part of the answer, therefore cannot make a
    // # boolean dp here. Always remember, fill the initial value of dp with such
    // # value that cannot be the part of the answer.

    // # Here true denotes that word can be breaked and false denotes that word
    // # cannot be breaked. Therefore cannot use boolean dp here.

    public int wordBreak_memo(String str, int si, HashSet<String> dict, int[] dp) {

        if (si == str.length())
            return dp[si] = 1;

        if (dp[si] != -1)
            return dp[si];
        boolean res = false;
        for (int cut = si; cut < str.length(); cut++) {
            if (dict.contains(str.substring(si, cut + 1))) {
                res = res || wordBreak_memo(str, cut + 1, dict, dp) == 1;
            }
        }
        return dp[si] = res ? 1 : 0;
    }

    public boolean wordBreak_memo(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>();
        for (String str : wordDict) {
            set.add(str);
        }
        int n = s.length();
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        return wordBreak_memo(s, 0, set, dp) == 1 ? true : false;
    }

    // ! Observation And Tabulation :

    public int wordBreak_tabu(String str, int SI, HashSet<String> dict, int[] dp) {
        for (int si = str.length(); si >= 0; si--) {
            if (si == str.length()) {
                dp[si] = 1;
                continue;
            }
            boolean res = false;
            for (int cut = si; cut < str.length(); cut++) {
                if (dict.contains(str.substring(si, cut + 1))) {
                    res = res || dp[cut + 1] == 1;
                }
            }
            dp[si] = res ? 1 : 0;
        }
        return dp[SI];
    }

    public boolean wordBreak_tabu(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>();
        for (String str : wordDict) {
            set.add(str);
        }
        int n = s.length();
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        return wordBreak_tabu(s, 0, set, dp) == 1 ? true : false;
    }

    // As discussed, we can do the same thing taking length as a parameter.

    public boolean wordBreak_recu_length(String str, int n, HashSet<String> dict) {
        if (n == 0)
            return true;

        boolean res = false;
        for (int cut = n; cut > 0; cut--) {
            if (dict.contains(str.substring(cut - 1, n))) {
                res = res || wordBreak_recu_length(str, cut - 1, dict);
            }
        }

        return res;
    }

    public boolean wordBreak_recu_length(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>();
        for (String str : wordDict) {
            set.add(str);
        }
        int n = s.length();
        return wordBreak_recu_length(s, n, set);
    }

    // ! Max Length check to optimize

    // Yahan pe bhi hum do fill kar rahe hain. Aur jahan pe word break ho sakta hai,
    // wahan pe true mark kar de rahe hain.

    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length(), len = 0;

        HashSet<String> dict = new HashSet<>();
        for (String str : wordDict) {
            dict.add(str);
            len = Math.max(len, str.length()); // calculating the max length of the string. Ab hume ye pata chal gaya ki
                                               // max length string ki kya ho sakti hai jisme hume word mile dictionary
                                               // mai
        }

        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 0; i <= n; i++) {
            if (!dp[i]) {
                continue;
                // First iteration ke baad jahan pe word break ho sakta hai wahan pe true mark
                // kar diya hoga. To jo false wali state hongi unse call lagane ki jaroorat he
                // nhi hai kyunki wahan pe word break ho he nhi sakta.
            }
            for (int l = 1; l <= len && i + l <= n; l++) { // yahan pe max length tak ka loop lagaya hai, kyunki uske
                                                           // ` baad word milega he nhi dictionary mai
                String str = s.substring(i, i + l);
                if (dict.contains(str)) {
                    dp[i + l] = true;
                }
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        mazePath();
    }

    // b <============== BackEngineering ==============>

    // # Humne dp ka istemaal kiya ye pata lagane ke liye ki hume kahan jana
    // # chahiye.
    // # Iska use string find karne ke liye hota hai.

    // Ek bar agar humne dp fill karli, to ab bas back engineering kar use karna hai
    // string print karane ke liye.

    // ? Iske liye most importantly hume dubara us raste se traverse karna hoga
    // ? jisse hume wo longest answer mila hai.

    // # To iske liye humne yahan pe dekha, agar character equal hai to hum shi
    // # raste per hain kyunki hume palindrome chahiye tha.

    // # Ab agar hum aise point pe pahunche jahan pe character equal nhi hai to
    // # humne dekha ki dp mai sabse badi value kahan ki hai aur humne call wahan ki
    // # lagayi taki hume longest wali value mile. Bas aisa karte rahe, aur sath mai
    // # return karte time string bhi attach karte rahe answer banane ke liye.

    // # Jaise he edge case mai pahunche, us character ko return kar diya.

    // ? Faith :
    // To maine bola ki tu longest string leke aaja. Agar character equal hue to mai
    // dono side (left mai aur right mai) apne aap ko attach kar dunga for longest
    // subsequence. Agar character equal nhi hai to jis dp ki value greater hai use
    // return karde.

    // ! Note :

    // Ek aur method hai, recursion mai ek visiter array rakh lo aur use mark karte
    // raho. jahan jahan pe mark kiya use un character ko add karke end mai string
    // ` bana dena.

    // b <<=========Question
    // https://leetcode.com/problems/longest-palindromic-subsequence/
    public int longestPalindromeSubseq_(String str, int I, int J, int[][] dp) {
        int n = str.length();
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; i++, j++) {
                if (i == j || i > j) {
                    dp[i][j] = i == j ? 1 : 0;
                    continue;
                }

                char ch1 = str.charAt(i);
                char ch2 = str.charAt(j);

                int count = 0;

                if (ch1 == ch2) {
                    count = dp[i + 1][j - 1] + 2;
                } else {
                    int a = dp[i + 1][j];
                    int b = dp[i][j - 1];
                    count = Math.max(a, b);
                }

                dp[i][j] = count;
            }
        }

        return dp[I][J];
    }

    public String lpss_backEngineering(String str, int i, int j, int[][] dp) {

        if (i == j || i > j) {
            return i == j ? str.charAt(i) + "" : "";
        }

        char ch1 = str.charAt(i);
        char ch2 = str.charAt(j);

        if (ch1 == ch2) {
            return ch1 + lpss_backEngineering(str, i + 1, j - 1, dp) + ch2;
        } else if (dp[i + 1][j] > dp[i][j - 1]) {
            return lpss_backEngineering(str, i + 1, j, dp);
        } else {
            return lpss_backEngineering(str, i, j - 1, dp);
        }
    }

    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        int ans = longestPalindromeSubseq_(s, 0, n - 1, dp);
        String longestPalindromeString = lpss_backEngineering(s, 0, n - 1, dp);
        System.out.print(longestPalindromeString);
        return ans;
    }

    // b <============== Gold Mine Back Engineering ==============>
    // https://practice.geeksforgeeks.org/problems/gold-mine-problem2608/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // # Back Enginering ka ek simple rule answer banane ke liye. Jahan pe max value
    // # dikh rahi sirf wahan call lagani hai aur answer create karna hai Kyunki max
    // # value se he hume max answer mila tha dp mai.

    // {{1, 3, 3},
    // {2, 1, 4},
    // {0, 6, 4}};

    // For the above example, the dp will be
    // {{8, 7, 3},
    // {12, 5, 4},
    // {10, 10, 4}};

    static int maxGold(int sr, int sc, int n, int m, int M[][], int[][] dir, int[][] dp) {

        if (dp[sr][sc] != -1)
            return dp[sr][sc];

        int max = 0;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < M.length && c < M[0].length) {
                max = Math.max(max, maxGold(r, c, n, m, M, dir, dp));
            }
        }

        return dp[sr][sc] = max + M[sr][sc];
    }

    static String maxGold_backEngineering(int sr, int sc, int[][] M, int[][] dir, int[][] dp) {

        int maxI = 0;
        int maxJ = 0;
        int max = -1;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < M.length && c < M[0].length) {
                if (dp[r][c] > max) {
                    max = dp[r][c];
                    maxI = r;
                    maxJ = c;
                }
            }
        }

        return max != -1 ? "(" + maxI + "," + maxJ + ")" + maxGold_backEngineering(maxI, maxJ, M, dir, dp) : "";

    }

    static int maxGold(int n, int m, int M[][]) {
        // code here

        int[][] dir = { { -1, 1 }, { 0, 1 }, { 1, 1 } };
        int[][] dp = new int[n][m];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        int max = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, maxGold(i, 0, n, m, M, dir, dp));
        }

        // For backEngineering

        // # Initially row ka index nikala jiske liye maximum mila. Ab wo mera starting
        // # point hoga.
        int maxI = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i][0] == max)
                maxI = i;
        }

        // # Ab kyunki starting point mil gaya hai, to ab wahan se recursion start kiya
        // # aur sirf jo max dp wali hogi sirf usko call lagaya aur recursively answer
        // # bana liya.
        String path = "(" + maxI + "," + 0 + ")" + maxGold_backEngineering(maxI, 0, M, dir, dp);
        System.out.print(path);
        return max;
    }

    // b <=================== Word Break II ===============>
    // https://leetcode.com/problems/word-break-ii/

    // ! Recursion :

    // ? Faith :
    // # Maine sare substring ko check kiya aur jahan pe bhi word cut ho sakta hai,
    // # maine use nikalke bachi hui string ko bola ki tum apne aap ko break karke
    // # le aao.

    public void wordBreak_II(String str, int i, HashSet<String> dict, String asf, List<String> ans, int maxLen) {
        int n = str.length();

        if (i == n) {
            ans.add(asf);
            return;
        }
        for (int l = 1; l <= maxLen && i + l <= n; l++) {
            String subStr = str.substring(i, i + l);
            if (dict.contains(subStr)) {
                wordBreak_II(str, i + l, dict, asf + subStr + (i + l == n ? "" : " "), ans, maxLen);
            }
        }
    }

    public List<String> wordBreak(String s, List<String> wordDict) {

        HashSet<String> set = new HashSet<>();
        int maxLen = 0;
        for (String str : wordDict) {
            set.add(str);
            maxLen = Math.max(maxLen, str.length());
        }

        List<String> ans = new ArrayList<>();
        wordBreak_II(s, 0, set, "", ans, maxLen);
        return ans;
    }

    // ? Now solved using back engineering. Same recursion to be followed as above.

    public void wordBreak_backEngg(String s, int idx, boolean[] dp, int maxLen, List<String> wordDict,
            HashSet<String> set, String ssf, List<String> ans) {
        if (idx >= s.length()) {
            ans.add(ssf.substring(0, ssf.length() - 1));
            return;
        }

        for (int l = 1; l <= maxLen && idx + l <= s.length(); l++) {
            if (dp[idx + l]) {
                String substr = s.substring(idx, idx + l);
                if (set.contains(substr)) {
                    wordBreak_backEngg(s, idx + l, dp, maxLen, wordDict, set, ssf + substr + " ", ans);
                }
            }
        }
    }

    public List<String> wordBreak_using_backEngineering(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>();
        int len = 0, n = s.length();
        for (String ss : wordDict) {
            set.add(ss);
            len = Math.max(ss.length(), len);
        }

        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 0; i <= n; i++) {
            if (!dp[i])
                continue;

            for (int l = 1; l <= len && i + l <= n; l++) {
                String substr = s.substring(i, i + l);
                if (set.contains(substr)) {
                    dp[i + l] = true;
                }
            }
        }
        // Same concept. Fill the initial dp and then recursively get the answer.
        List<String> ans = new ArrayList<>();
        if (dp[n])
            wordBreak_backEngg(s, 0, dp, len, wordDict, set, "", ans);

        return ans;
    }

}