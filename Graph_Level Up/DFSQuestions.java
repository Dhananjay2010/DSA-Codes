import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class DFSQuestions {

    // b <============= Number of Islands ======================>
    // https://leetcode.com/problems/number-of-islands/

    // This is same as get connected compoenents.
    // The whole one component is visited from one dfs call and we have to get all
    // the components.

    public static void dfs_NumberOfIslands(char[][] grid, boolean[][] vis, int[][] dir, int sr, int sc) {

        vis[sr][sc] = true;

        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r <= grid.length - 1 && c <= grid[0].length - 1 && grid[r][c] != '0'
                    && !vis[r][c]) {
                dfs_NumberOfIslands(grid, vis, dir, r, c);
            }
        }
    }

    public int numIslands(char[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        boolean[][] vis = new boolean[n][m]; // This is used since we should not modify the original data. Otherwise, we
                                             // would have to make changes in the original grid array.

        int numberOfIslands = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!vis[i][j] && grid[i][j] != '0') {
                    numberOfIslands++;
                    dfs_NumberOfIslands(grid, vis, dir, i, j);
                }
            }
        }

        return numberOfIslands;
    }

    // b <============= 695. Max Area of Island ===============>
    // https://leetcode.com/problems/max-area-of-island/

    // Same as the number of islands but here we also have to calculat the area of
    // each component and then find the maximum area.

    // ? Faith ye rakha maine apne neighbours ko bola ki tum mujhe apna area aur jo
    // ? tumse touched one hain unka area lake dedo. Ab mai total area nikalne ke
    //
    // liye
    // ? jo total area mere neighours se aaya hai mai usme 1 add kar dunga.

    public static int dfs_maxAreaOfIsland(int[][] grid, boolean[][] vis, int[][] dir, int sr, int sc) {

        vis[sr][sc] = true;

        int count = 0;

        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r <= grid.length - 1 && c <= grid[0].length - 1 && grid[r][c] != 0
                    && !vis[r][c]) {
                count += dfs_maxAreaOfIsland(grid, vis, dir, r, c);
            }
        }

        return count + 1;
    }

    public int maxAreaOfIsland(int[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        boolean[][] vis = new boolean[n][m]; // This is used since we should not modify the original data. Otherwise,
                                             // we would have to make changes in the original grid array.

        int maxArea = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!vis[i][j] && grid[i][j] != 0) {
                    int area = dfs_maxAreaOfIsland(grid, vis, dir, i, j);
                    maxArea = Math.max(maxArea, area);
                }
            }
        }

        return maxArea;
    }

    // b <================ 463. Island Perimeter ===============>
    // https://leetcode.com/problems/island-perimeter/

    // Yahan pe humne basically simple logic lagaya hai. Agar humpe 1 square hai to
    // `uska perimeter will be 4. Agar hume 2 seperate squares ha, to un donno ka
    // perimeter will be 2*4=8.

    // Per agar hum dono ko merge kardein, then the two edges perimeter will be
    // gone. So total will be 2*4 -2(one edge for each sqaures). There will be total
    // 2 neighbours 1 for each square.

    // So what we have done is counted the total number of squares that have 1 as
    // their value denoting land. Now whenever we find 1, we check the number of
    // neighbours of the square and add them.

    // At last, we calcalated 4 * oneCount - nbrCount to get the total perimeter.

    public int islandPerimeter(int[][] grid) {

        int n = grid.length, m = grid[0].length, oneCount = 0, nbrCount = 0;
        int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    oneCount++;
                    for (int d = 0; d < dir.length; d++) {
                        int r = i + dir[d][0];
                        int c = j + dir[d][1];

                        if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 1)
                            nbrCount++;
                    }
                }
            }
        }

        return 4 * oneCount - nbrCount;
    }

    // b <============= 130. Surrounded Regions ======================>
    // https://leetcode.com/problems/surrounded-regions/

    // Agar koi basically boundary waale O se connected O hai, wo to kabhi bhi
    // surround to ho he nhi payega.

    // ? To hum yahan pe boundary wale O se call lagate hain. Per humne center wale
    // ? O jo O Beech mai hain unse call kyun nhi lagayienn ???

    // Iska answer ye hain ki agar mai manle center se call lagake boundary wale O
    // tak pahunch gaya, to backtrack karte time hum un O ko mark karenge ki wo
    // ` boundary wale O se connected the. Per wapis mark karte time jo already
    // ` backtrack ho chuke hain (matlab backtrack karne ke baad boundary wale O tak
    // puhunche), unhe mark karna hum miss kar denge. Isiliye hum boundary wale O se
    // call lagate hain bas.

    // To kiya ye ki jo bhi sare O jo boundary wale O se connected hain unhe $ mark
    // kiya kyunki wo to acpure nhi ho sakte. Ab jo O iske baad bach gaye wo sare
    // capture ho sakte hain isiliye unhe badme X mai convert kiya aur $ ko O mai
    // dubara convert kar diya

    public static void dfs_Surrounded(char[][] grid, int[][] dir, int sr, int sc) {

        grid[sr][sc] = '$';

        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] == 'O') {
                dfs_Surrounded(grid, dir, r, c);
            }
        }
    }

    public void solve(char[][] grid) {

        int n = grid.length, m = grid[0].length;
        int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 || j == 0 || i == n - 1 || j == m - 1) {
                    if (grid[i][j] == 'O') {
                        dfs_Surrounded(grid, dir, i, j);
                    }

                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '$') {
                    grid[i][j] = 'O';
                } else if (grid[i][j] == 'O') {
                    grid[i][j] = 'X';
                }
            }
        }
    }

    // b <============== Number of Distinct Islands ========================>
    // https://www.lintcode.com/problem/860/

    // ye basically same hai numbre of islands ki tarah. Bas ab yahan pe distinct
    // islands batane hain. To iske liye hume har island ka path store karna padega.
    // Iske liye humne hashset ka use kiya taki agar koi bhi duplicate path wala aa
    // jaye to wo dubara count he na ho

    // End mai humne hashset ka size return karwaya hai

    // ! Important point : Why added sb.append("X"); statement ?????

    // consider a test case :

    // 0 1 1 0 1 1
    // 1 1 0 0 0 0
    // 1 0 0 0 0 1
    // 0 1 1 0 1 1

    // For 1 placed in (0,1) ,the string will be RDLD.

    // 0 1 1 0 1 1
    // 0 1 1 0 0 0
    // 0 1 0 0 0 1
    // 0 0 1 0 1 1

    // ` But the for this test case for 1 placed in (0,1), the string will again be
    // RDLD. But they are two different structures. Therefore sb.append("X")
    // statement was added so that we can tell that the direction was changed and
    // now we have backtracked from it.

    // Now for top one the string will be RXDLDXXX
    // For the bottom test case the string will be RDLDXXXX

    // Hence Denoting the two structures are different.

    public static void dfs_numberofDistinctIslands(int[][] grid, char[] dirS, int[][] dir, int sr, int sc,
            StringBuilder sb) {

        grid[sr][sc] = 2;

        for (int d = 0; d < dir.length; d++) {

            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] == 1) {
                sb.append(dirS[d]);
                dfs_numberofDistinctIslands(grid, dirS, dir, r, c, sb);
                sb.append("X");
            }
        }
    }

    public int numberofDistinctIslands(int[][] grid) {

        char[] dirS = { 'U', 'R', 'D', 'L' };
        int[][] dir = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

        int n = grid.length, m = grid[0].length;
        HashSet<String> set = new HashSet<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    StringBuilder sb = new StringBuilder();
                    dfs_numberofDistinctIslands(grid, dirS, dir, i, j, sb);
                    set.add(sb.toString());
                }
            }
        }

        return set.size();
    }

    // b<===============Rotten Oranges =========================>
    // https://leetcode.com/problems/rotting-oranges/

    // So basically kiya ye ki jitne bhi rotten oranges hai unse call lagayi aur
    // time + 1 karke grid mai h save karate rahe time ko. Isiliye time ko 2 se
    // start kiya hai.

    // # Important Point :-

    // Ab aisa bhi ho sakta hai ki rotten oranges bahut sare ho aur pehle wale ne he
    // sare fresh ko visit karke apna time save kara diya ho grid mai. Iske liye
    // (grid[r][c] == 1 || grid[r][c] > time + 1) ye check add kiya hai ki agar
    // fresh ho to call lagao ya fir agar current time ki value jahan jaa rahe hain
    // grid mai usse kam hai to call lagao taki grid mai uska time update ho jaye

    // ! For test case : [[2,1,1],[1,1,1],[0,1,2]], Dry run and the see the
    // ! importance of the check.

    public static void dfs_orangesRotting(int[][] grid, int sr, int sc, int[][] dir, int time) {

        grid[sr][sc] = time;

        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length
                    && (grid[r][c] == 1 || grid[r][c] > time + 1)) {
                dfs_orangesRotting(grid, r, c, dir, time + 1);
            }
        }
    }

    public int orangesRotting(int[][] grid) {

        int n = grid.length, m = grid[0].length;
        int[][] dir = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
        int minTimeTakenToRot = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 2) {
                    dfs_orangesRotting(grid, i, j, dir, 2);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    return -1;
                }
                minTimeTakenToRot = Math.max(grid[i][j], minTimeTakenToRot);
            }
        }

        // Agar uper sab ke baad bhi minTimeTakenToRot 0 aaya matlab, koi bhi fresh
        // oranges tha he nhi.

        return minTimeTakenToRot == 0 ? 0 : minTimeTakenToRot - 2; // -2 since we started the time from 2 and considered
                                                                   // it as 0th second

    }

    // b <=================== 1020. Number of Enclaves ====================>
    // https://leetcode.com/problems/number-of-enclaves/

    // ? Ye basically surrounded regions ki tarah hai.

    // Isme hume wo land cells ka count return karna hai jinse hum grid se bahar nhi
    // jaa sakate.

    // So basically ye wo land cells honge jo agar boundary land cell se connected
    // nhi honge.

    // To humne kiya ye jo bhi land cells boundary wale land se connected thi, unko
    // mark kar diya.

    // # Ab jo land cells bachengi wo boundary wle land se connected nhi hongi, to
    // # unhe count karke return kar diya.

    public static void dfs_numEnclaves(int[][] grid, int sr, int sc, int[][] dir) {
        grid[sr][sc] = 2;

        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] == 1) {
                dfs_numEnclaves(grid, r, c, dir);
            }
        }
    }

    public int numEnclaves(int[][] grid) {

        int n = grid.length, m = grid[0].length;
        int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if ((i == 0 || j == 0 || i == n - 1 || j == m - 1) && grid[i][j] == 1) { // Called dfs for all land
                                                                                         // cells in the boundary.
                    dfs_numEnclaves(grid, i, j, dir);
                }
            }
        }

        int landCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    landCount++;
                }
            }
        }

        return landCount;
    }

    // b <========== Journey to the Moon ==================>
    // https://www.hackerrank.com/challenges/journey-to-the-moon/problem

    // ? Hai kya question mai ??
    // Hume basically manga ye hai ki number of pairs batao jo ban sakte hain. Aur
    // condition ye hai ki pair mai dono different country ke hone chahiye

    // aur humko jo do size ka array diya hai wo ye denote karta hai ki dono
    // element(candidate) same country ko belong karte hain

    // Ab hum agar isko ek edge ki tarah mante hain to hum apna graph create kar
    // sakte hain aur different conponent of graph different country ko represent
    // karte hain

    // To hume itna pata lag gaya hai to bas ab combination nikalne baki hain.

    // To for ex : Agar A, B , C, D ,E ,F different country hain aur inka size kuch
    // a, b,c,d,e,f hai

    // To jo total different pair banenge wo honge :
    // a(b+c+d+e+f) + b(c+d+e+f) + c(d+e+f) + d(e+f) + e(f) + f(0)

    // since the last element f will not have anything to pair with so multiplied it
    // with 0 to get our equation


    public static int dfs_journeyToMoon(ArrayList<Integer>[] graph, int src, boolean[] vis) {

        vis[src] = true;
        int size = 0;

        for (int v : graph[src]) {
            if (!vis[v]) {
                size += dfs_journeyToMoon(graph, v, vis);
            }
        }

        return size + 1;
    }

    public static long journeyToMoon(int n, List<List<Integer>> astronaut) {
        // Write your code here

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (List<Integer> e : astronaut) {
            int vtx = e.get(0);
            int nbr = e.get(1);

            graph[vtx].add(nbr);
            graph[nbr].add(vtx);
        }

        boolean[] vis = new boolean[n];
        long sum = 0, ans = 0;
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                int size = dfs_journeyToMoon(graph, i, vis); // Return the size of the component.
                ans += size * sum;
                sum = sum + size;
            }
        }

        return ans;

    }

}