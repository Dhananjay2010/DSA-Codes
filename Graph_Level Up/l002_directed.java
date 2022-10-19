import java.util.ArrayList;
import java.util.LinkedList;

public class l002_directed {

    public static class Edge {
        int v, w;

        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }

        Edge() {

        }
    }

    public static void addEdge(ArrayList<Edge>[] graph, int u, int v, int w) {
        graph[u].add(new Edge(v, w));
    }

    // O(2E) == O(E)
    public static void display(ArrayList<Edge>[] graph, int V) {
        for (int i = 0; i < V; i++) {
            System.out.print(i + " -> ");
            for (Edge e : graph[i]) {
                System.out.print("(" + e.v + "," + e.w + ") ");
            }
            System.out.println();
        }
    }

    // b <================ Topological Order ===================>

    // ? What is topological order ?
    // Topological order basically wahan pe use hota hai janan pe dependencies hoti
    // hain.

    // Dependencies ka matlab ye hai ki mere kaam pure hone ke liye mujhe kisi aur
    // pe dependent hun.

    // ? For ex :

    // 1. A Program execution during compilation. Aisa bahut bar hota hai ki kisi
    // function ko pehle pure execute hone ke liye wo dusre function pe dependent
    // hota hai

    // 2. In Excel, the cells are sometimes dependent on the another cells for the
    // answer and if there a cycle exists then the the excel shows the error.

    // 3. To study the book of higher class, we need to have studied the lower class
    // `books. So we are dependent on the lower class books in order to understand
    // the higher class books

    // ! So importantly the game of dependency is everywhere.

    // ! The definition :

    // # To ye aisa order nikal ke deta hai ki agar aap usse execution start karo to
    // # aapko answer mil jayega

    // So pehle meri sari dependencies resolve ho tabhi mai apna kaam kar paunga.

    // ? Now this type of graph becomes directed. But why ??

    // Consider ki do variables hain A and B. Aur A dependent hai B ke uper ki jab B
    // ki value aajeyegi tabhi hum A ko calculate kar payenge. Per agar B A pe
    // dependent nikla to ab ye possilbe he nhi hai calculate karna. To graph ke
    // terms mai cycle exist kar jayegi.

    // # Therefore a directed graph.

    // ! Now to get a topological order, we add the vertices in post order. Why?

    // Kyunki pehle mai apne neighbours ki dependencies ko reolve karunga tab jake
    // to mai apni dependencies ko resolve kar paunga na

    // Just like a tree, pehle mere sare children resolve hue tab maine apne aap ko
    // revolve kiya

    // ! Important point :

    public static void topo_DFS(int src, ArrayList<Edge>[] graph, boolean[] vis, ArrayList<Integer> ans) {
        vis[src] = true;
        for (Edge e : graph[src]) {
            if (!vis[e.v]) {
                topo_DFS(e.v, graph, vis, ans);
            }
        }
        ans.add(src); // Added in post order

        // # This normal dfs call does not detect cycle and hence gives a wrong
        // # topological order whenever there is a cycle present in the graph.
    }

    public static void topologicalOrder(int n, ArrayList<Edge>[] graph) {
        boolean[] vis = new boolean[n];
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) { // Hum yahan pe vertices ko randomly call bhi laga sakte hai and tabhi bhi
                                      // answer same he aayega. Aisa isiliye kyunki hum postorder mai kaam kar rahe
                                      // hain aur usme pehle sare children/neightbours visit honge tab hum
            if (!vis[i]) {
                topo_DFS(i, graph, vis, ans);
            }
        }

        for (int i = ans.size() - 1; i >= 0; i--) {
            System.out.println(ans.get(i));
        }
    }

    // ! How to check if the topological order found is right or wrong :

    // Humne egdes bana ke dekha us order mai. aur agar sare edges same direction
    // mai aaye to order shi hai, nhi to order galat hai

    // b<============== Khan's Algorithm =========================>

    // Since the above normal postorder dfs does not determine the correct order in
    // the
    // case of cycle present in graph, therefore khan's algo helps to get the
    // correct
    // topological order

    // # Indegree : Merepe kitne log dependent hain (Merepe kitne pointed arrows aa
    // # rahe hain)

    // # outDegree : Mai kitno pe dependent hun. (Merese kitne outer arrows nikal
    // # rahe hain)

    // ? Steps in Kahn's Algo :

    // 1. Find the indegree of every vertices
    // 2. Jitno ki bhi indergee 0 hai unhe que mai dala
    // 3. Ab jaise he que se element nikala, jo wo vertex jin logon pe dependent hun
    // na mai unke edges ko relax karke aata hun. Matlab mai man leta hun ki wo kaam
    // ho jayega. To maine jin edges ko relax kiya unki vertex mai jake -1 karunga
    // kyunki ab mai unpe dependent nhi raha.

    // To mai aisa maan ke chal raha hun ki mera kaam to ho he jayega agar mai jin
    // logon pe denpendent hun agar wo apna kaam shi se karlein to. To ab mai tumpe
    // dependent rah he nhi isiliye -1 kiya

    // 4. Jiski indegree ki value 0 ho jayegi, use utha ke queue mai dal denge
    // kyunki ab wo independent and resolve ho gya hai isiliye

    // 5. Jaise he hum queue se element nikalte hain use usi time ek arraylist mai
    // store karte jayege taki hume topological order mil jaye

    // 6. Aur ab agar end mai jo answer humne banaya hai uska size agar number of
    // vertices ke equal nhi hai, to iska matlab sari dependencies resolve nhi hui
    // hain,to matlab cycle exist karti hai.

    // ! Important Point : What does the level indicate in the kahn's algo ???

    // # Ek Level ke andar jo elements hai ye indicate karta hai wo sari process
    // # jinko aap parallely kar sakte ho to save time and money because they are
    // # independent of each other.

    // So if we consider them machine, so the all the machine that come in the same
    // level, they can be run parallel(simultaneously) with each other since they
    // are independent of each other considering that their dependencies are
    // resolved in the previous level. Hence we can save time for the whole process.

    // ! Very important point, many questions can be resolved with this.

    //

    public static ArrayList<Integer> kahnsAlgo(int n, ArrayList<Edge>[] graph) {
        int[] indegree = new int[n];
        for (ArrayList<Edge> edgesList : graph) {
            for (Edge e : edgesList) {
                indegree[e.v]++;
            }
        }

        LinkedList<Integer> que = new LinkedList<>();
        ArrayList<Integer> ans = new ArrayList<>();

        for (int i = 0; i < n; i++)
            if (indegree[i] == 0)
                que.addLast(i);

        int level = 0;
        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {
                int vtx = que.removeFirst();
                ans.add(vtx);

                for (Edge e : graph[vtx]) {
                    if (--indegree[e.v] == 0)
                        que.addLast(e.v);
                }

            }
            level++;
        }

        if (ans.size() != n) {
            System.out.println("Topological Order is not possible due to Cycle");
            ans.clear();
        }

        return ans;
    }

    // b <============Kahn's Algo with level importance =================>

    public static void kahnsAlgo_01(int n, ArrayList<Edge>[] graph) {
        int[] indegree = new int[n];
        for (ArrayList<Edge> edgesList : graph) {
            for (Edge e : edgesList) {
                indegree[e.v]++;
            }
        }

        LinkedList<Integer> que = new LinkedList<>();
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();

        for (int i = 0; i < n; i++)
            if (indegree[i] == 0)
                que.addLast(i);

        int level = 0;
        while (que.size() != 0) {
            int size = que.size();
            ArrayList<Integer> smallAns = new ArrayList<>();
            while (size-- > 0) {
                int vtx = que.removeFirst();
                smallAns.add(vtx);

                for (Edge e : graph[vtx]) {
                    if (--indegree[e.v] == 0)
                        que.addLast(e.v);
                }

            }
            ans.add(smallAns);
            level++;
        }

        if (ans.size() != n) {
            System.out.println("Topological Order is not possible due to Cycle");
            ans.clear();
        }
    }

    // b <================ KosaRaju Algo ======================>

    // ? It is used to find number of strongly connected components in a graph.
    // ? It converts cyclic graph into acyclic graph.

    // Wo component jisme aap har vertex ka use karke har vertex tak reach kar paa
    // rahe ho.

    // # To ye Strongly connected components banate hain.

    // A single vertex is a strongly connected component.

    // 1. Find the topological order (With post Order)
    // 2. Find the inverse of the graph
    // 3. Run the Dfs on the topological order of the original graph on the inverse
    // graph.

    // ! Important Point :

    // # Jab mai graph ko inverse karta hun na, bas cycle ko farak nhi padta, baki
    // # sabko farak padta hai.

    // To ab jab mai original graph ke topological order ko mante hue mai dfs run
    // karunga inverse wale graph mai to jo single roads hongi wo rasta rok denge
    // ` baki aur cycles ko access karne ka.

    // ! Important point :

    // # When we studied topological order,we found out that when there is cycle
    // # present, dfs does not give the right answer. So why used here ?

    // Humko dfs traversal wali cheez to shi bata raha hota hai. Matlab isse besahq
    // topological order galat aayega, per jab humne graph ko reverse kiya, to cycle
    // ko koi farak nhi pada.

    // ! Steps for KosaRaju algo

    // 1. Find the topological order from postorder.
    // 2. Inverse the graph
    // 3. Using that topological order, again call dfs and get the scc count.

    // # scc : - Strongly Connected Components
    public static void dfs_01(int src, ArrayList<Edge>[] graph, boolean[] vis, LinkedList<Integer> st) { // To get the
                                                                                                         // postorder
                                                                                                         // topological
                                                                                                         // order.
        vis[src] = true;
        for (Edge e : graph[src]) {
            if (!vis[e.v])
                dfs_01(e.v, graph, vis, st);
        }

        st.addFirst(src);
    }

    public static void dfs_02(int src, ArrayList<Edge>[] graph, boolean[] vis) { // Just to traverse to each scc
                                                                                 // component
        vis[src] = true;
        for (Edge e : graph[src]) {
            if (!vis[e.v])
                dfs_02(e.v, graph, vis);
        }
    }

    public static void KosaRajuAlgo(int n, ArrayList<Edge>[] graph) {
        boolean[] vis = new boolean[n];
        LinkedList<Integer> st = new LinkedList<>(); // Created a stack to store the topological order.

        for (int i = 0; i < n; i++) {
            if (!vis[i])
                dfs_01(i, graph, vis, st);
        }

        // Inverse Of a graph
        ArrayList<Edge>[] nGraph = new ArrayList[n]; // Inverse the graph so that one cycle may not reach other.
        for (int i = 0; i < n; i++) {
            for (Edge e : graph[i]) {
                int v = e.v, wt = e.w;
                nGraph[v].add(new Edge(i, wt));
            }
        }

        int scc = 0; // To maintain scc count
        vis = new boolean[n]; // Created a new visited
        while (st.size() != 0) { // So now we will start traversing vertex as stored in stack. (The topological
                                 // order)
            int vtx = st.removeFirst();
            if (!vis[vtx]) {
                dfs_02(vtx, nGraph, vis);
                scc++;
            }
        }
    }

    // b <================== Mother Vertex ==================>
    // https://practice.geeksforgeeks.org/problems/mother-vertex/1

    // To maine wo last vertex nikala jisse graph traverse ho raha hai pura.

    // Kyunki mai initially 0 se start kiya hai aur hum sequentially chal rahe hain
    // to ye ensure karta hai ki mujhe vertex min he milega, agar multiple mother
    // vertex hain to.

    // To bas jaise he last vertex mili ab ye confirm karne ke liye ki wo mother
    // vertex hai, pure graph ko dubara se traverse kareke size nikala. agar ab size
    // equal aata hain total number of vertex ke, to matlab vertex mother vertex hai
    
    public static void dfs_motherVertex_01(int src, int V, ArrayList<ArrayList<Integer>> graph, boolean[] vis) {

        vis[src] = true;
        for (int vtx : graph.get(src)) {
            if (!vis[vtx])
                dfs_motherVertex_01(vtx, V, graph, vis);
        }
    }

    public static int dfs_motherVertex_02(int src, int V, ArrayList<ArrayList<Integer>> graph, boolean[] vis) {

        vis[src] = true;
        int size = 0;
        for (int vtx : graph.get(src)) {
            if (!vis[vtx])
                size += dfs_motherVertex_02(vtx, V, graph, vis);
        }

        return size + 1;
    }

    public static int findMotherVertex(int V, ArrayList<ArrayList<Integer>> graph) {
        // Code here

        boolean[] vis = new boolean[V];
        int lastVertex = -1;
        for (int i = 0; i < V; i++) {
            if (!vis[i]) {
                dfs_motherVertex_01(i, V, graph, vis);
                lastVertex = i; // Since we are starting from 0, this ensures that we have smallest element at
                                // the last that may traverse the whole graph
            }
        }

        vis = new boolean[V];
        int size = dfs_motherVertex_02(lastVertex, V, graph, vis);

        return size == V ? lastVertex : -1;
    }

}