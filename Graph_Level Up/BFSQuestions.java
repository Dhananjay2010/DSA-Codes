import java.util.ArrayList;
import java.util.LinkedList;

public class BFSQuestions {

    // b <======================Is Graph Bipartite?==================>
    // https://leetcode.com/problems/is-graph-bipartite/

    // ` Basically agar tu graph ki sari vertices ko 2 aise sets mai devide kar
    // sakta hai such that ki within a set, koi bhi edge form na ho vertices ,
    // ke beech mai use bi-partite graph bolte hain.

    // Jaise koi directed graph hai jaise ki tree, jisme cycle exist he nhi karti wo
    // humesha bi-partite hoga.

    // Agar even length ki cycle mili, tab bhi wo bi-partite hoga
    // Per agar odd ki cycle mili, to wo bi-partite nhi hoga

    // # To yahan pe kiya kya???

    // To kiya ye ki hume pata hai ki agar ek vertex agar A set mai hai to uske sare
    // neighbours B set mai hone chahiye bipartite hone ke liye. To set ko mark
    // karne ke liye colours ka use kiya. Aur agar kahin pe bho conflict aaya
    // (matlab
    // aisa hua ki vertex ko red hona tha per wo blue hai ya vice versa), to graph
    // `bipartite nhi hoga

    // Level radius ki tarah soch

    // Level ye indicate karta hai ki uska color kya hona chahiye. Same radius wala
    // comcept.

    // ? 0 : Not visited, 1 : red Marked, 2 : Blue Marked
    public boolean isBipartite_(int[][] graph, int src, int[] vis) {

        LinkedList<Integer> que = new LinkedList<>();
        que.addLast(src);
        int level = 0;

        while (que.size() != 0) {

            int size = que.size();

            while (size-- > 0) {
                int vtx = que.removeFirst();

                if (vis[vtx] != 0) {
                    int colorShouldBe = level % 2 == 0 ? 1
                            : 2;
                    int actualColor = vis[vtx];

                    if (colorShouldBe != actualColor) // conflict
                        return false;
                    continue;
                }
                vis[vtx] = level % 2 == 0 ? 1 : 2;

                for (int e : graph[vtx]) {
                    if (vis[e] == 0) {
                        que.addLast(e);
                    }
                }
            }
            level++;
        }
        return true;
    }

    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] vis = new int[n];

        for (int i = 0; i < n; i++) {
            if (vis[i] == 0 && !isBipartite_(graph, i, vis)) { // Agar koi ek bhi component bi-partite nhi hua to pura
                                                               // graph he bi-partitie nhi hhoga
                return false;
            }
        }
        return true;
    }

    // Bhaiya Code :

    public boolean bipartite(int[][] graph, int src, int[] vis) {
        LinkedList<Integer> que = new LinkedList<>();
        que.addLast(src);

        // No Color : -1 , Red : 0, Green : 1
        int color = 0;
        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {
                int rvtx = que.removeFirst();
                if (vis[rvtx] != -1) {
                    if (color != vis[rvtx]) // conflict
                        return false;
                    continue;
                }

                vis[rvtx] = color;
                for (int v : graph[rvtx]) {
                    if (vis[v] == -1) {
                        que.addLast(v);
                    }
                }
            }

            color = (color + 1) % 2; // Here using the % to have 0 and 1 as values of color
        }

        return true;
    }

    // b <================= Rotten Oranges ================>
    // https://leetcode.com/problems/rotting-oranges/

    // # Here using bfs without cycle

    // ? Here we are going to use parallel bfs

    // ? Means we are going to have multiple starting source and for each of them we
    // ? are going to run bfs.

    // To basically kiya ye ki jitne bhi initially rotten oranges hain unhe que mai
    // store kara. Tab sabki sath mai bfs run kiya.

    // Level ye denote karega ki kitna time lga hai rot hone mai.

    // 0th level mai sare initial rotten aayenge.
    // 1st level mai wo aayenge jo time 1 mai rot hue.

    // Aisa isiliye kyunki bfs mai yhi to hota hai ki hum apne sare neighbours ko
    // agle level mai dal dete hain. Hence hume min time mil jayega

    // To ye hume minimum time nikalke dega jab humare sare oranges rot ho jayenge

    // Humne 2-D ko 1-D mai convert karke index dale hain que ke andar

    // r=idx / m, c=idx % m, idx=r * m + c;

    public int orangesRotting(int[][] grid) {

        LinkedList<Integer> que = new LinkedList<>();
        int n = grid.length, m = grid[0].length;
        int oneCount = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 2) {
                    que.addLast(i * m + j);
                } else if (grid[i][j] == 1) {
                    oneCount++; // To calculate the number of fresh oranges
                }
            }
        }

        if (oneCount == 0)
            return 0;

        int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
        int time = 0; // denoting level;
        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                int rvtx = que.removeFirst();
                int sr = rvtx / m;
                int sc = rvtx % m;
                for (int d = 0; d < dir.length; d++) {

                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];

                    if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] == 1) {
                        grid[r][c] = 2;
                        que.addLast(r * m + c);
                        oneCount--;
                        if (oneCount == 0) { // if by any chance the number reduces to 0, it means all are rotten.
                            return time + 1;
                        }
                    }

                }
            }

            time++;
        }

        // Agar uper wale while loop se koi bahar aata hai, iksa matlab ye hoga ki abhi
        // ` bhi oneCount 0 nhi hai matlab fresh oranges hain
        return -1;
    }

    // b<============ 1091. Shortest Path in Binary Matrix ==========>
    // https://leetcode.com/problems/shortest-path-in-binary-matrix/

    // yahan pe basically ye logic lagaya ki bfs hume shortest path nikal ke deta
    // hai kisi vertex ka.

    // So again Humne 2-D ko 1-D mai convert karke index dale hain que ke andar

    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length, m = grid[0].length;

        if (grid[0][0] == 1 || grid[n - 1][m - 1] == 1) {
            return -1;
        }

        int[][] dir = { { -1, 0 }, { 1, 0 }, { -1, 1 }, { 1, -1 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { -1, -1 } };

        LinkedList<Integer> que = new LinkedList<>();
        que.addLast(0);

        int shortestPath = 1;
        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {

                int rvtx = que.removeFirst();

                int sr = rvtx / m, sc = rvtx % m;
                if (sr == n - 1 && sc == m - 1) { // agar bottom right cell mila to return kara level ka size which here
                                                  // indicate the shortest path
                    return shortestPath;
                }

                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];

                    if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] == 0) {
                        grid[r][c] = 1;
                        que.addLast(r * m + c);
                    }
                }
            }
            shortestPath++;
        }

        // Agar uper wale loop ke bahar aaya iska matlab use answer mila he nhi, to
        // return -1 kiya.
        return -1;
    }

    // b <============ 542. 01 Matrix ===============>
    // https://leetcode.com/problems/01-matrix/

    // ? Again applied the concept of parellel bfs

    // kiya kya ki sare 0 ko pehle he dal diya que mia aur ek vis ka array banake
    // ` unko mark kiya. Ab parallel bfs run kiya sare 0 ke liye. To jitne bhi 1 0
    // se one distance pe the wo level 1 mai aayenge. Similarly jo 2 ki distance mai
    // the wo level 2 mai aayenge. and so goes on.

    // # So again Humne 2-D ko 1-D mai convert karke index dale hain que ke andar
    // # r=idx/m, c=idx%m, idx=r*m + c, where m is the number of columns

    // ! Baki dry run and figure out.

    // ---------------------------------------------------------------------------

    // Ek aur tareeka tha per wo bahut he high complexity ka jaa raha tha.

    // Humse jitne bhi one hain, unke liye individually bfs call karte aur nearest 0
    // nikalte. So basically ye humara n² approach hota.

    public int[][] updateMatrix(int[][] grid) {

        int n = grid.length, m = grid[0].length;
        if (n == 0 || m == 0) {
            return grid;
        }

        int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
        LinkedList<Integer> que = new LinkedList<>();
        boolean[][] vis = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) {
                    que.addLast(i * m + j);
                    vis[i][j] = true;
                }
            }
        }

        int distance = 0;
        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {
                int rvtx = que.removeFirst();
                int sr = rvtx / m, sc = rvtx % m;

                grid[sr][sc] = distance; // ` updated the grid with the distance(level of bfs)

                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];

                    if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && !vis[r][c]) {
                        vis[r][c] = true; // Marked so that the vertex that is visited is not added again in the queue.
                        que.addLast(r * m + c);
                    }
                }
            }
            distance++;
        }
        return grid;
    }

    // b <<================ 663 · Walls and Gates ===================>
    // https://www.lintcode.com/problem/663/

    // ? Same as rotten oranges, parallel bfs run kiya hai. Aur bfs_wihout cycle use
    // ? kiya hai.

    // Sabse pehle sare gates ko que mai dala uske baad bfs run karke find kiya ki
    // empty cells ki distance kya hai gates se.

    // ! Ek bar dry run karego to sub clear.

    public void wallsAndGates(int[][] grid) {
        // write your code here

        int n = grid.length, m = grid[0].length;
        if (n == 0 || m == 0)
            return;

        int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
        LinkedList<Integer> que = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) {
                    que.addLast(i * m + j);
                }
            }
        }

        int shortestPath = 0; // Level : denotes radius. Ki mujse kitni distance pe hai.
        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                int rvtx = que.removeFirst();
                int sr = rvtx / m, sc = rvtx % m;
                for (int d = 0; d < dir.length; d++) {

                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];

                    if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] == 2147483647) {
                        grid[r][c] = shortestPath + 1; // Can also be written as grid[sr][sc] + 1. Matlab main jitni
                                                       // distance pe hun usse + 1 mai hai mera neighbour isiliye
                        que.addLast(r * m + c);
                    }
                }
            }
            shortestPath++;
        }
    }

    // b <<========== Course Schedule I =================>
    // https://leetcode.com/problems/course-schedule/

    // Here used the same kahn's algo
    public boolean canFinish(int n, int[][] prerequisites) {

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        int[] inDegree = new int[n];
        for (int[] e : prerequisites) {
            graph[e[0]].add(e[1]);
            inDegree[e[1]]++;
        }

        ArrayList<Integer> ans = new ArrayList<>();// This is used for more explanation. Rather than this, use a vertex
                                                   // count variable to count the number of vertex that are resolved.
        LinkedList<Integer> que = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                que.addLast(i);
            }
        }
        int vertexCount = 0;

        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {
                int rvtx = que.removeFirst();
                ans.add(rvtx);
                vertexCount++;

                for (int e : graph[rvtx]) {
                    if (--inDegree[e] == 0) {
                        que.addLast(e);
                    }
                }
            }
        }
        // return vertexCount==n;
        if (ans.size() != n) {
            return false;
        }
        return true;
    }

    // b <<========== Course Schedule II =================>
    // https://leetcode.com/problems/course-schedule-ii/

    // Here used Kahn's algo

    public int[] findOrder(int n, int[][] prerequisites) {
        ArrayList<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        int[] inDegree = new int[n];
        for (int[] e : prerequisites) {
            graph[e[0]].add(e[1]);
            inDegree[e[1]]++;
        }

        // Rather than reversing the answer at the end, we can change the edges to get
        // the right answer

        // for (int[] e : prerequisites) {
        // graph[e[1]].add(e[0]);
        // inDegree[e[0]]++;
        // }

        LinkedList<Integer> que = new LinkedList<>();
        ArrayList<Integer> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                que.addLast(i);
            }
        }

        int level = 0;

        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {
                int rvtx = que.removeFirst();
                ans.add(rvtx);

                for (int v : graph[rvtx]) {
                    if (--inDegree[v] == 0) {
                        que.addLast(v);
                    }
                }
            }
            level++;
        }

        if (ans.size() != n) {
            ans.clear();
        }

        int[] myans = new int[ans.size()];
        int count = 0;
        for (int i = ans.size() - 1; i >= 0; i--) {
            myans[count++] = ans.get(i);
        }

        return myans;
    }

    // b<================ dfs cycle detection =============>

    // Simply hum ek path ka array bhi use kar sakte the along with visited, to
    // indicate that the vertex is the part of the current path. But the same thing
    // can be handled using the integer array which we can mark 0, 1, 2 values.

    // 0 for unvisited
    // 1 for is part of the current path and tells us that the vertex has been
    // visited
    // 2 for after backtrack indicating that the vertex has been visited earlier

    // Jaise he mai kisi vertex mai aaya maine us 1 se mark kiya indicating ki wo
    // mere current path ka part hai. Ab jaise he mai usse backtrack karunga, mai
    // ab use 2 se mark karunga indicating that ki ye vertex visit ho chuki hai per
    // ye ab mere current path ka part he nhi hai

    public static boolean dfs_findOrder_isCyclePresent(ArrayList<Integer>[] graph, int src, ArrayList<Integer> ans,
            int[] vis) {

        vis[src] = 1;

        boolean res = false;
        for (int v : graph[src]) {
            if (vis[v] == 1) { // Agar jis vertex pe jaa rha hai wo already path ka part hai to matlab wo cycle
                               // hai
                return true;
            } else if (vis[v] == 0) {
                res = res || dfs_findOrder_isCyclePresent(graph, v, ans, vis);
            }
        }

        ans.add(src);
        vis[src] = 2;
        return res;
    }

    // Leetcode 210
    public int[] findOrder_(int n, int[][] prerequisites) {

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : prerequisites) {
            graph[e[0]].add(e[1]);
        }

        ArrayList<Integer> ans = new ArrayList<>();
        int[] vis = new int[n];

        boolean isCycle = false;
        for (int i = 0; i < n; i++) {
            if (vis[i] == 0) {
                isCycle = isCycle || dfs_findOrder_isCyclePresent(graph, i, ans, vis);
            }
        }

        if (isCycle)
            return new int[] {};

        int[] myans = new int[ans.size()];
        int count = 0;
        for (int i = 0; i < ans.size(); i++) {
            myans[count++] = ans.get(i);
        }

        return myans;
    }

    // b <= Kahn's Algo in 2-Matrix (329. Longest Increasing Path in a Matrix)==>
    // https://leetcode.com/problems/longest-increasing-path-in-a-matrix/

    // ? Treat a cell in the matrix as a node,
    // ? a directed edge from node x to node y if x and y are adjacent and
    // ? x's value < y's value Then a graph is formed.

    // No cycles can exist in the graph, i.e. a DAG is formed.
    // # DAG : Directed acyclic graph

    // The problem becomes to get the longest path in the DAG.

    // Topological sort can iterate the vertices of a DAG in the linear ordering.

    // Here Using Kahn's algorithm(BFS) to implement topological sort while counting
    // the levels can give us the longest chain of nodes in the DAG.

    // ! To yahan pe kiya kya??

    // Yahan pe basically humne har ek cell ko vertex ki tarah treat kiya hai aur ek
    // graph mai convert kiya hai. Ab ye jo graph hoga, usme hum edges define
    // karenge. Kyunki hume increasing path nikalna hai to agar koi meri adjacent
    // value mujse kam hai, to wo edge uuse merepe banega.

    // Aisa karke humne indegree calculate kiya hai. Ab humne kahn's algo lagayi
    // simple to calculate the longest path since jo end mai level aayega wo uska
    // longest path denote karege in terms of node/vertex.

    public int longestIncreasingPath(int[][] matrix) {

        int n = matrix.length, m = matrix[0].length;
        int[][] indegree = new int[n][m];

        int[][] dir = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
        LinkedList<Integer> que = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int d = 0; d < dir.length; d++) {
                    int r = i + dir[d][0];
                    int c = j + dir[d][1];
                    if (r >= 0 && c >= 0 && r < n && c < m) {
                        if (matrix[r][c] < matrix[i][j]) // Jahan mai jaa raha hun agar uski value mujhe kam hai to meri
                                                         // indegree++ hogi kyunki wo edge usse merepe aayega. Pointer
                                                         // meri taraf aayega
                            indegree[i][j]++;
                    }
                }

                if (indegree[i][j] == 0)
                    que.addLast(i * m + j);
            }
        }

        int level = 0;
        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                int rvtx = que.removeFirst();
                int sr = rvtx / m, sc = rvtx % m;

                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];

                    // Hum sirf wahin jayenge jiski value humse badi hogi
                    if (r >= 0 && c >= 0 && r < n && c < m && matrix[r][c] > matrix[sr][sc]) { // Dry run on test case
                                                                                               // [[0],[1],[5],[5]] to
                                                                                               // figure out the last
                                                                                               // check
                        if (--indegree[r][c] == 0) {
                            que.addLast(r * m + c);
                        }
                    }
                }
            }

            level++;
        }

        return level;
    }
}