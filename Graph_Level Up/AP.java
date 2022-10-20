import java.util.ArrayList;
import java.util.List;

public class AP {

    // # Koi aisa point jisko nikalne se ya hatane se mere graph ke number of
    // # components increase karte hain, use articulation point bolte hain.

    // Ek graph mai multiple articulation point ho sakta hain.

    // Ek aisa edge jisko nikalke number of components increase ho jate hain to use
    // articulation edge bolte hain

    // ! How the question can be formed ?

    // 1. Jaise ki manle bahut sari cities aapsa mai connected hain in a country aur
    // war ho gayi. To war mai kisi city ke uper bomb gira diya. Ab meri country mai
    // ek headquater city hai jahan se food supply hota hai. To kya hai bomb wali
    // city ko chodke us country ke har ek city tak food pahuncha paunga?

    // 2. Uper wale question aise bhi frame ho sakta hai ki two connecting cities
    // ke pull pe bomb gira. Ab batao ki food har ek city tak pahunch payega.

    // 3. Manlo bahut sare planets hain aur unka aapas mai communication ho raha
    // hai. Ab ek metriod aaya aur kisi planet se aake takraya aur destroy hua. To
    // kya abhi bhi sare planets ek dusre se communicate kar payenge???

    // ! Important Points :

    // # Discovery Time : Jab maine chalna start kiya, to kaunse vertex mai kis time
    // # mai discover kiya

    // ===> Jiski Discovery Pehle, wo jyada powerful
    // ===> Jiski Discovery Badme, wo jyada weak

    // # Low Time : Mai kis powerful person ko janta hun. (Tum apne se pehle kisi
    // # powerfull person ko jante ho)
    // ===> Iske bare mai hum tabhi sochenge jab hum backtrack karenge.

    // ? Agar koi vertex mera parent nhi hai aur wo already visited hai aur mera
    // ? neighbour hai to wo heiarchy mai mujse pehle/uper raha hoga.

    // # Jitne dots ek vertex pe honge(denoting that it is a articulation point
    // # w.r.t other vertex), the no. of components formed by removing that vertex
    // # will be (dots + 1). In Code we have maintained AP array to calculate the
    // # dots.

    // ! Important Point :
    // This will be only true for all vertex except the root vertex.

    // ? The normal dfs call make the root a articulation point even if the number
    // ? of call from it is just one, which is wrong so therefore we have to handle
    // ? root differently

    // Agar root se number of calls is more than one, then it is a articulation
    // point, otherwise not. For example take a example of nodes in a single line.
    // If the root node are the endpoints node, then those points cannot be the
    // articulation points since removing them, the number of components does not
    // increase.

    // If the number of calls is greater than one, than the total number of
    // components formed by removing the root is equal to the number of dots.
    // Therefore due to this, AP[root]=-1 in code to gernalize for everyNode in the
    // graph.

    // ! Important Point :

    // # Kyunki hume mostly sirf ye pucha jayega ki ye point articulation point hai
    // # ki nhi, to hum boolean ka array use kar sakte hain aur wahan pe rootcalls
    // # wali condition important ho jati hai root vale case ko handle karne mai

    // ! Why have we compared with discovery time, not low time.

    // In the notes section, consider the test case.
    // Agar manle mai low time se compare karta hunto (5,2) ki jagah pe (5,0) hota.
    // Similarly, (4,4) update hota (4,0) mai
    // Similarly, (3,3) update hota (3,0) mai

    // Ab agar tu (2,0) mai hota to tu (3,0) ko dekh ke ye sochta ki 3 ke pass aur
    // koi rasta hai 0 tak pahunchne ka jo ki mujhse bhi powerful hai. To 3 ko meri
    // need he nhi hai

    // # But actual mai aisa to hai he nhi. 2 wo jaria hai jiski wajah se 3 uper
    // # wale components se connected hai. Therefore ye galat hai.

    // ? Isiliye hum discovery ko compare karte hain. Naki low time ko.

    // ! Important Point :
    // # Do articuation point ke beech mai ek articuation edge exist kare humesha ye
    // # baat sach nhi hogi.Like in the below test case.

    public static class Edge {
        int v, w;

        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    public static void addEdge(ArrayList<Edge>[] graph, int u, int v, int w) {
        graph[u].add(new Edge(v, w));
        graph[v].add(new Edge(u, w));
    }

    private static int[] low, disc, AP;
    private static int time = 0, rootCalls;
    private static boolean[] vis, APoints;

    public static void dfs(int src, int par, ArrayList<Edge>[] graph) {
        disc[src] = low[src] = time;
        vis[src] = true;
        for (Edge e : graph[src]) {
            if (!vis[e.v]) {
                dfs(e.v, src, graph);

                if (par == -1) // Agar root he parent to parent to -1 he hoga na.
                    rootCalls++;

                // Articualation Point
                if (disc[src] <= low[e.v]) { // Agar meri discovery pehle hui hai jis most powerful vertex ko tu janta
                                             // hai to mai to ek articuation point to honga he
                    AP[src]++;
                    APoints[src] = true;
                }

                // Articulation Edge
                if (disc[src] < low[e.v]) {
                    System.out.println("Articulation Edge : (" + src + "," + e.v + ") ");
                    // e.v is a articuation edge with src
                }

                low[src] = Math.min(low[src], low[e.v]);

            } else if (e.v != par) { // Agar e.v mera parent nhi hai aur wo already visited hai, to mai apne aap ko
                                     // discovery time se update kar lunga
                low[src] = Math.min(low[src], disc[e.v]);
            }
        }
    }

    public static void APB(ArrayList<Edge>[] graph, int N) {

        low = new int[N];
        disc = new int[N];
        AP = new int[N];
        vis = new boolean[N]; // Not needed. We can just fill the disc Array with -1 initially.
        APoints = new boolean[N];

        for (int i = 0; i < N; i++) {
            if (!vis[i]) {
                AP[i] = -1; // For specific root to generalize the formula of components formed to (dots +
                            // 1) where in code dots means AP[i] + 1;

                dfs(i, -1, graph);
                if (rootCalls == 1) // For the condition since this dfs, it tells that the root is also a
                                    // articuation point evenif it is not. Consider a single line graph with nodes.
                                    // So if the number of calls are greater than one, then the root is also a
                                    // articulation point.
                    APoints[i] = false;

            }

        }
    }

    // b <=======Critical Connections in a Network ==================>
    // https://leetcode.com/problems/critical-connections-in-a-network/

    public static void criticalConnections_dfs(int src, int par, ArrayList<Integer>[] connections,
            List<List<Integer>> ans) {
        disc[src] = low[src] = time++;
        vis[src] = true;
        for (int v : connections[src]) {
            if (!vis[v]) {
                criticalConnections_dfs(v, src, connections, ans);
                if (disc[src] < low[v]) {
                    List<Integer> myAns = new ArrayList<>();
                    myAns.add(src);
                    myAns.add(v);
                    ans.add(myAns);
                }
                low[src] = Math.min(low[src], low[v]);
            } else if (v != par)
                low[src] = Math.min(low[src], disc[v]);
        }
    }

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {

        List<List<Integer>> ans = new ArrayList<>();
        time = 0;
        vis = new boolean[n];
        disc = new int[n];
        low = new int[n];

        ArrayList<Integer>[] graph = new ArrayList[n]; // Created our own graph from edges
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (List<Integer> e : connections) {
            int u = e.get(0), v = e.get(1);
            graph[u].add(v);
            graph[v].add(u);
        }

        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                criticalConnections_dfs(i, -1, graph, ans);
            }
        }

        return ans;
    }

    // b <================= Minimum Number of Days to Disconnect Island ==========>
    // https://leetcode.com/problems/minimum-number-of-days-to-disconnect-island/

    // ! Brute Force, passed since the test case and constraints are small.

    public static void dfs_numsIsland(int sr, int sc, int[][] grid, int[][] dir, boolean[][] vis) {
        vis[sr][sc] = true;

        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] == 1 && !vis[r][c]) {
                dfs_numsIsland(r, c, grid, dir, vis);
            }
        }
    }

    public static int numOfIslands(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        boolean[][] vis = new boolean[n][m];

        int noOfIslands = 0;
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, -1 }, { 0, 1 } };
        for (int i = 0; i < n * m; i++) {
            int r = i / m, c = i % m;
            if (!vis[r][c] && grid[r][c] == 1) {
                dfs_numsIsland(r, c, grid, dir, vis);
                noOfIslands++;
            }
        }

        return noOfIslands;
    }

    public int minDays_(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int initialComponents = numOfIslands(grid);
        if (initialComponents > 1 || initialComponents == 0)
            return 0;

        for (int i = 0; i < n * m; i++) {
            int r = i / m, c = i % m;

            if (grid[r][c] == 1) {
                grid[r][c] = 0;
                int noOfComponents = numOfIslands(grid);
                if (noOfComponents > 1 || noOfComponents == 0)
                    return 1;
                grid[r][c] = 1;
            }
        }
        return 2;
    }

    // B Optimized using the articulation Point

    private static int[] low, disc;
    private static int time = 0;
    private static boolean[] vis;

    public static int dfs_size(int idx, int[][] grid, boolean[] vis) {
        int n = grid.length, m = grid[0].length;
        int sr = idx / m, sc = idx % m;

        vis[idx] = true;

        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, -1 }, { 0, 1 } };
        int count = 0;

        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] == 1 && !vis[r * m + c]) {
                count += dfs_size(r * m + c, grid, vis);
            }
        }

        return count + 1;
    }

    public static boolean tarjans(int src, int par, int[][] grid) {
        int n = grid.length, m = grid[0].length;
        disc[src] = low[src] = time++;
        vis[src] = true;

        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, -1 }, { 0, 1 } };

        boolean res = false;
        for (int d = 0; d < dir.length; d++) {
            int sr = src / m, sc = src % m;

            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 1) {
                int nbr = r * m + c;
                if (!vis[nbr]) {
                    res = res || tarjans(nbr, src, grid);
                    if (disc[src] < low[nbr]) { // Yahan pe equal to sign nhi kiya use. Why? ==> Kyunki hume cycle wale
                                                // structure ke liye bhi true return karna tha kyunki wahan pe do vertex
                                                // ko nikal ke graph disconnected ban sakta hai. Example is of a square,
                                                // removing the diagonal vertex will make component disconnected.
                        return true;
                    }
                    low[src] = Math.min(low[nbr], low[src]);
                } else if (nbr != par) {
                    low[src] = Math.min(low[src], disc[nbr]);
                }
            }

        }
        return res;
    }

    public int minDays(int[][] grid) {
        int n = grid.length, m = grid[0].length;

        disc = new int[n * m];
        low = new int[n * m];
        vis = new boolean[n * m];
        int root = -1;
        int noOfComponents = 0, size = 0;
        for (int i = 0; i < n * m; i++) {
            int r = i / m, c = i % m;

            if (grid[r][c] == 1 && !vis[i]) {
                root = i;
                size += dfs_size(i, grid, vis);
                noOfComponents++;
            }
        }

        if (noOfComponents == 0 || noOfComponents > 1) // Agar mera component 0 hai ya 1 ha se bada hai to mai already
                                                       // disconnected hun, to 0 retun kardo
            return 0;
        else if (size <= 2) // Ab kyunki mai uper component ka check karke aaya hun to mai sure hun ki ab ek
                            // he single component hai graph mai. To agar component ka size 1 ya 2 hua, to
                            // ` utne he din lagte use disconnect karne mai jitna size hota.
            return size;

        vis = new boolean[n * m];
        boolean res = tarjans(root, -1, grid);
        return res ? 1 : 2; // Ab agar mujhe articulation point milta hai to mai to mai 1 return kardunga,
                            // aur agar nhi milta hai to mai 2 return kardunga kyunki at most mera answer 2
                            // ho sakta hai.

    }

}