import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Arrays;

public class l004Algos {

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

    public static class pair {
        int src = 0;
        int par = 0;
        int w = 0;
        int wsf = 0;

        // dijikstra
        pair(int src, int par, int w, int wsf) {
            this.src = src;
            this.par = par;
            this.w = w;
            this.wsf = wsf;
        }

        // This one to be used in prims algo
        pair(int src, int par, int w) {
            this(src, par, w, 0); // constructor chaining
        }
    }

    // b <<<<============= Dijikstra(Normal One) =====================>>>>

    // MST pure graph ke weight ko minimise karne ki try karta hai. Ye(MST) sabhi
    // edges ke sum ko minimise karne ki try karta hai

    // ! It(Dijikstra) is a single source algorithm.
    // # So it gives the lightest(minimum) weight path from source to every vertex.

    // It does not work for -ve edges.

    // Simple hai wsf(weight so far) ke terms mai priority queue se bahar nikalte
    // rehna hai simple bfs mai. Bas isse hume ek specific source se kisi vertex tak
    // ka shhortest path(in terms of weight) mil jata hai. Preferred to say the
    // lightest path

    public static void dijikstra(ArrayList<Edge>[] graph, int V, int src) {
        ArrayList<Edge>[] mygraph = new ArrayList[V];
        for (int i = 0; i < V; i++)
            graph[i] = new ArrayList<>();

        boolean[] vis = new boolean[V];
        PriorityQueue<pair> pq = new PriorityQueue<>((a, b) -> {
            return a.wsf - b.wsf; // This - other concept
        });

        pq.add(new pair(src, -1, 0, 0));
        while (pq.size() != 0) {
            pair p = pq.remove();
            if (vis[p.src])
                continue;

            if (p.par != -1) // To create the new(The lightest path from the source to every vertex)
                             // graph(generally not needed).
                addEdge(mygraph, p.src, p.par, p.w);

            vis[p.src] = true;
            for (Edge e : graph[p.src]) {
                if (!vis[e.v])
                    pq.add(new pair(e.v, p.src, e.w, p.wsf + e.w));
            }
        }
    }

    // b <============== Prims (Normal One)=================>

    // ! Prims

    // # Iske andar source matter nhi karta
    // # Jo edge aap graph mai insert kar rehe ho wo minimum weight wala hona
    // # chahiye

    // ! Prims aur kruskal dono MST banate hain

    // b Dono mai difference kya hai ?

    // ? Prims ke liye hume graph ki requirement hoti hai aur ye dense graph mai
    // ? bahut acha perform karta hai

    // # Kruskal ke liye mujhe sirf edges required hoti hain aur ye sparse graph mai
    // # acha perform kata hai

    // Dijikstra mai wsf ke anusar pq se nikalte the, yahan pe w se hisab se nikalte
    // hain

    public static void prims(ArrayList<Edge>[] graph, int V, int src) {
        ArrayList<Edge>[] mygraph = new ArrayList[V];
        for (int i = 0; i < V; i++)
            graph[i] = new ArrayList<>();

        boolean[] vis = new boolean[V];
        PriorityQueue<pair> pq = new PriorityQueue<>((a, b) -> {
            return a.w - b.w; // Just the difference
        });

        pq.add(new pair(src, -1, 0, 0));
        while (pq.size() != 0) {
            pair p = pq.remove();
            if (vis[p.src])
                continue;

            if (p.par != -1)
                addEdge(mygraph, p.src, p.par, p.w);

            vis[p.src] = true;
            for (Edge e : graph[p.src]) {
                if (!vis[e.v])
                    pq.add(new pair(e.v, p.src, e.w, p.wsf + e.w));
            }
        }
    }

    // b <============= 743. Network Delay Time ==================>
    // https://leetcode.com/problems/network-delay-time/

    // # Here created edge and the pair of both integer array. Good for online test.
    // # But create a class in front of interviewer.

    // ! Also try to break it in function.

    public int networkDelayTime(int[][] times, int n, int k) {

        // Edge : {v,w}
        ArrayList<int[]>[] graph = new ArrayList[n + 1];

        for (int i = 0; i < n + 1; i++)
            graph[i] = new ArrayList<>();

        for (int[] time : times) {
            // times[i] = (ui, vi, wi)
            graph[time[0]].add(new int[] { time[1], time[2] }); // Apna graph create kiya
        }

        // ! Baki simple Dijistra lagaya. niche ka pura hum ek function mai likh sakte
        // ! hain.

        // pair : {v,wsf}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            return a[1] - b[1];
        });

        boolean[] vis = new boolean[n + 1];
        int[] dis = new int[n + 1]; // Keeping a distance array to store the min distance(with weight) of every
                                    // vertex.
        Arrays.fill(dis, (int) 1e9);

        pq.add(new int[] { k, 0 });

        while (pq.size() != 0) {
            int[] rn = pq.remove();
            int u = rn[0], wsf = rn[1];
            if (vis[u])
                continue;

            vis[u] = true;
            dis[u] = wsf;

            for (int[] e : graph[u]) {
                int wt = e[1], v = e[0];
                if (!vis[v]) {
                    pq.add(new int[] { v, wsf + wt });
                }
            }
        }

        int maxDistance = 0;
        for (int i = 1; i < n + 1; i++) {
            if (dis[i] == (int) 1e9) // if any value is still (int)1e9, it means that that vertex was never reached.
                return -1;

            maxDistance = Math.max(maxDistance, dis[i]);
        }

        return maxDistance;
    }

    // b <=========== Dijikstra Better ==============>
    // Jaise he mai elements ko queue mai dalunga, waise he mai apna distance ka
    // array update marunga

    // Intially mera distance ka array ∞ hoga. Ab jaise he koi element aaya, mai ye
    // check karunga ki wo element jis wsf ke sath aaya hai wo distance array mai jo
    // value rakhi hai wo usse better hai ki nhi

    // ? So basically ye distance ka array kuch elements jinki need nhi hai unko
    // ? dalne ne nhi deta priority queue mai

    // # Parent naam ka array mujhe path shortest path in terms of weight nikalne
    // # mai help karta hai.
    // # Distance naam ka array mujhe O(1) mai min distance(lightest path ka
    // # distance) nikal ke deta hai.
}