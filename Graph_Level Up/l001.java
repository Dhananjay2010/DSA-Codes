import java.util.ArrayList;
import java.util.LinkedList;

public class l001 {

    public static class Edge {
        int v, w;

        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    public static void addEdge(ArrayList<Edge>[] graph, int u, int v, int w) {

        graph[u].add(new Edge(v, w));
        graph[v].add(new Edge(u, w)); // Made the graph undirected
    }

    // O(2E) ==> O(E) since we are considering the undirected graph, so the total
    // number of edges will be 2E therefore the time complexity will be O(2E) which
    // in Big O is O(n)
    public static void display(ArrayList<Edge>[] graph, int V) {

        for (int i = 0; i < V; i++) {
            System.out.print(i + " -> ");
            for (Edge e : graph[i]) {
                System.out.print("(" + e.v + "," + e.w + ")" + " ");
            }

            System.out.println();
        }
    }

    public static int findEdge(ArrayList<Edge>[] graph, int u, int v) { // to return the index of the edge

        for (int i = 0; i < graph[u].size(); i++) {
            Edge e = graph[u].get(i);
            if (e.v == v) {
                return i;
            }
        }

        return -1;
    }

    public static void removeEdge(ArrayList<Edge>[] graph, int u, int v) {

        graph[u].remove(findEdge(graph, u, v));
        graph[v].remove(findEdge(graph, v, u));
    }

    public static void removeVertex(ArrayList<Edge>[] graph, int u) {

        // removing the edge from the front will cause shifting in the arraylist causing
        // the index value to change.

        // Iske karan kuch edge delete nhi hogi kyuki jo edge delete karne aaye uska
        // index change ho sakta hai.

        for (int i = graph[u].size() - 1; i >= 0; i--) {
            Edge e = graph[u].get(i);
            removeEdge(graph, u, e.v);
        }
    }

    // b <================= DFS ========================>
    // # 1. Mark karo
    // # 2. For all unvisited neighbours,
    // # 2.1 call dfs for neighbours
    // # 3. Unmark karo ===> Only do when we have to explore all the path

    // b <========== Has Path ===================>

    // ? Iska faith kuch aisa hoga ki maine apne childs se pucha ki kya tu
    // ? destination tak pahunch sakta hai. Agar han to mai bhi pahunch jaunga.

    // Yahan pe end mai vis[src]=false isiliye mark nhi kiya kyunki mujhe sare path
    // traverse nhi karne. Bas agar koi ek mil jaya to wo bahut hai.

    // ! Time complexity : O(2E) == > O(E)
    // ` Because we have traversed all the edges.
    public static boolean hasPath(ArrayList<Edge>[] graph, int src, int dest, boolean[] vis) {

        if (src == dest)
            return true;

        vis[src] = true;
        boolean res = false;

        for (Edge e : graph[src]) {
            if (!vis[e.v]) {
                res = res || hasPath(graph, e.v, dest, vis);
            }
        }

        return res;
    }

    public static int printAllPath(ArrayList<Edge>[] graph, int src, int dest, String psf, boolean[] vis) {

        if (src == dest) {
            System.out.println(psf + src);
            return 1;
        }

        vis[src] = true;
        int count = 0;
        for (Edge e : graph[src]) {
            if (!vis[e.v]) {
                count += printAllPath(graph, e.v, dest, psf + src, vis);
            }
        }

        vis[src] = false;
        return count;
    }

    public static void preOrder(ArrayList<Edge>[] graph, int src, boolean[] vis, int wsf, String psf) {
        System.out.println(src + " -> " + (psf + src) + "@" + wsf);
        vis[src] = true;
        for (Edge e : graph[src]) {
            if (!vis[e.v])
                preOrder(graph, e.v, vis, wsf + e.w, psf + src);
        }

        vis[src] = false;
    }

    public static void postOrder(ArrayList<Edge>[] graph, int src, boolean[] vis, int wsf, String psf) {
        vis[src] = true;
        for (Edge e : graph[src]) {
            if (!vis[e.v])
                postOrder(graph, e.v, vis, wsf + e.w, psf + src);
        }

        System.out.println(src + " -> " + (psf + src) + "@" + wsf);
        vis[src] = false;
    }

    // b <============== Heaviest Path in terms of weight ============>

    // ? Faith ye lagaya ki mujhe mere sare neighbours apne se pass hota hua
    // ? destination tak ka heaviest path nikal ke leke aayenge. Ab mai unhe compare
    // ? karke sabse max value ko store kar linga aur return kar dunga

    public static class Pair {
        int wsf = -1; // Weight so far
        String psf = ""; // Path so far

        Pair() {

        }

        Pair(int wsf, String psf) {
            this.wsf = wsf;
            this.psf = psf;
        }
    }

    public static Pair heaviestPath(ArrayList<Edge>[] graph, int src, int dest, boolean[] vis) {

        if (src == dest) {
            return new Pair(0, "" + src);
        }

        vis[src] = true;
        Pair ans = new Pair();
        for (Edge e : graph[src]) {
            if (!vis[e.v]) {
                Pair recAns = heaviestPath(graph, e.v, dest, vis);

                if (recAns.wsf != -1 && recAns.wsf + e.w > ans.wsf) { // recAns.wsf != -1 == > ye check isiliye lagaya
                                                                      // kyunki manle kabhi agar destination mila he
                                                                      // nhi, to use answer calculate karna he nhi
                                                                      // chahiye
                    ans.wsf = recAns.wsf + e.w;
                    ans.psf = src + recAns.psf;
                }
            }
        }

        vis[src] = false;
        return ans;
    }

    // b <=============== Hamiltonian Path and Cycle ===========================>
    // # Also know as single source algorithm since it is source dependent and the
    // # answer can change w.r.t to source.

    // A hamiltonian path is such which visits all vertices without visiting any
    // twice. A hamiltonian path becomes a cycle if there is an edge between first
    // and last vertex.

    public static boolean hasHamiltonianCycle(ArrayList<Edge>[] graph, int src, int dest) {

        for (Edge e : graph[src]) {
            if (e.v == dest) {
                return true;
            }
        }

        return false;
    }

    // osrc ==> original src to check the hamiltonian cycle.
    // Agar mere total number of edges is total vertex - 1 so then only i can that i
    // have traversed all the vertices.

    // E=V-1;
    public static void hamiltonianPathAndCycle(ArrayList<Edge>[] graph, int src, int osrc, boolean[] vis,
            int edgeCount, String psf) {

        if (edgeCount == graph.length - 1) {

            if (hasHamiltonianCycle(graph, osrc, src)) {
                System.out.println(psf + src + "*"); // Cycle
            } else {
                System.out.println(psf + src + "."); // Path
            }
        }

        vis[src] = true;

        for (Edge e : graph[src]) {
            if (!vis[e.v]) {
                hamiltonianPathAndCycle(graph, e.v, osrc, vis, edgeCount + 1, psf + src);
            }
        }
        vis[src] = false;
    }

    // ! Hashmap is good if the the number of elements is less than 10³. After that
    // ! the number of collission increase in it, hence increasing the complexity.
    // ! So hashmap O(1) can be equal to O(n).

    // b <==============Get Connected Components=============>

    // O(E)
    public static void dfs_compo(ArrayList<Edge>[] graph, int src, boolean[] vis) {

        vis[src] = true;

        for (Edge e : graph[src]) {
            if (!vis[e.v]) {
                dfs_compo(graph, e.v, vis);
            }
        }
    }

    // Get Connected Components
    public static int gcc(ArrayList<Edge>[] graph) {
        int N = graph.length;
        boolean[] vis = new boolean[N];

        int components = 0;

        // This for loop is of complexity O(V + E) since all the vertices are traversed
        // and all the edges are traversed by dfs_compo for loop.
        for (int i = 0; i < N; i++) {
            if (!vis[i]) {
                components++;
                dfs_compo(graph, i, vis);
            }
        }

        return components;
    }

    // b <========================= Bfs ===========================================>

    // ? It gives up us the shortest path from the source vtx to the destination
    // ? vtx.

    // Mai Bbfs mai radius wise move karta hain. Isiliye mujhe shortest path milta
    // hai us vertex ka.

    // Where radius is in terms of edges.

    // To pehle mujhe sare wo vertices milenge jo mujhe ek edge ki duri pe hai. Tab
    // wo milengi jo mujhse 2 edge ki dduri pe hai and so on ...

    // # Agar koi vertex already visited hai aur wo dubara visit hoti hai, to us
    // # graph mai cycle hai. Matlab us vertex tak pahunchne ke do raste hai from
    // # the fixed source

    // To hum jab cycle aayegi to continue kar denge kyunki mai nhi chahta ki dubara
    // ` us vertex ke neighbours dalein kyunki wo pehle se dal chuke hain. Agar aisa
    // nhi kiya to infinite loop jaa sakta hai

    // Level in Bfs indicate the minimum number of edges required to reach from that
    // source vertex

    // For ex : 2 tal pahunchne ke do raste hai 0 se. 012 and 032.

    // ! iski complexity hogi O(E) kyunki aisa hua hai ki maine vertex ko ek se
    // ! jyada bar visit kiya hai. To approximately ise O(E) bolenge

    // This to be used when we need to detect cycle in a question.
    public static void bfs(ArrayList<Edge>[] graph, int src, boolean[] vis) {
        // Yahan pe jaise he maine kisi element ko que se nikala use maine mark kiya

        LinkedList<Integer> que = new LinkedList<>();
        que.add(src);

        int level = 0;
        boolean isCyclePresent = false;
        while (que.size() != 0) {
            int size = que.size();
            System.out.print("Level: " + level + " ->");

            while (size-- > 0) {
                int vtx = que.removeFirst();
                if (vis[vtx]) {
                    System.out.println("cycle");
                    isCyclePresent = true;
                    continue;
                }

                System.out.print(vtx + ", ");

                vis[vtx] = true;
                for (Edge e : graph[vtx]) {
                    if (!vis[e.v])
                        que.addLast(e.v);
                }
            }

            level++;
            System.out.println();
        }
    }

    // b <===============bfs_withoutCycle=======================>

    // Yahan pe humne source ko dalte time que mai he mark kiya and jab bhi kisi
    // vertex ko que mai dal rahe hain, to pehle mark kar rahe hai aur tab queue mai
    // dal rahe hain

    // Shortest path to a vertex ye bhi batata hai

    // ! Its advantage :

    // ? It is slightly faster since every vertex is just visited once therofore the
    // ? complexity is O(V), means it is faster than the above one.

    public static void bfs_withouCycle(ArrayList<Edge>[] graph, int src, boolean[] vis) {
        LinkedList<Integer> que = new LinkedList<>();
        que.add(src);
        vis[src] = true;

        int level = 0;
        while (que.size() != 0) {
            int size = que.size();
            System.out.print("Level: " + level + " ->");

            while (size-- > 0) {
                int vtx = que.removeFirst();
                System.out.print(vtx + ", ");

                for (Edge e : graph[vtx]) {
                    if (!vis[e.v]) {
                        vis[e.v] = true;
                        que.addLast(e.v);
                    }
                }
            }

            level++;
            System.out.println();
        }
    }

    // b <============== Bi-partite and odd and even cycle ==============>
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

    
    public static void bipartite(ArrayList<Edge>[] graph, int src, int[] vis) {
        LinkedList<Integer> que = new LinkedList<>();
        que.addLast(src);

        // No Color : -1 , Red : 0, Green : 1
        int color = 0;
        boolean isCycle = false, isBipartite = true;

        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {
                int rvtx = que.removeFirst();
                if (vis[rvtx] != -1) {
                    isCycle = true;
                    if (color != vis[rvtx]) // conflict
                        isBipartite = false;
                    continue;
                }
                vis[rvtx] = color;
                for (Edge e : graph[rvtx]) {
                    if (vis[e.v] == -1) {
                        que.addLast(e.v);
                    }
                }
            }
            color = (color + 1) % 2;
        }
        if (!isCycle)
            System.out.println("Bipartite Graph with no cycle");
        else {
            if (isBipartite)
                System.out.println("Bipartite Graph with Even Length cycle");
            else
                System.out.println("Non Bipartite Graph with Odd Length cycle");
        }
    }

    public static void constructGraph() {
        int V = 9; // denoting the number of vertex in graph

        ArrayList<Edge>[] graph = new ArrayList[V];
        for (int i = 0; i < V; i++)
            graph[i] = new ArrayList<>();

        addEdge(graph, 0, 1, 10);
        addEdge(graph, 0, 3, 10);
        addEdge(graph, 1, 2, 10);
        addEdge(graph, 2, 3, 40);

        addEdge(graph, 2, 7, 2);
        addEdge(graph, 2, 8, 4);
        addEdge(graph, 7, 8, 3);

        addEdge(graph, 3, 4, 2);
        addEdge(graph, 4, 5, 2);
        addEdge(graph, 4, 6, 8);
        addEdge(graph, 5, 6, 3);

        // addEdge(graph, 0, 6, 3); // cycle

        display(graph, V);

    }

    public static void main(String[] args) {
        constructGraph();
    }
}