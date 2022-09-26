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
}