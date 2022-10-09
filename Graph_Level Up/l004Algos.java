import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

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

        // dijikstra better
        pair(int src, int wsf) {
            this(src, -1, 0, wsf);
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
    // ? dalne ne nhi deta priority queue mai. Isse iski complexity thodi si better
    // ? ho jati hai.

    // Per iski jo Best complexity aati hai wo AVL Tree/ BST ko use karke he aati
    // hai ya fir priority queue aur hashmap ko use karke. Per kyunki hashmap ki
    // complexity already he bekar hoti hai to, to pehla wali jyada achi complexity
    // deta hai. Per worst case mai dono ki same hoti hai.

    // # Parent naam ka array mujhe shortest path in terms of weight nikalne
    // # mai help karta hai.

    // Agar mujhe kisi vertex tak ka path chahiye, to mai usse puchunga ki tera
    // parent kaun hai.Tab wo jo answer dega mai usse dubara question karrunga ki
    // tera parent kaun hai. Aisa mai tab tak karta rahunga jab tak mai source ke
    // parent tak nhi pahunch jata. joki hai -1. Aise muhje pura path mil jayega.

    // # Distance naam ka array mujhe O(1) mai min distance(lightest path ka
    // # distance) nikal ke deta hai.

    public static void dijikstra_Btr(ArrayList<Edge>[] graph, int V, int src) {
        ArrayList<Edge>[] mygraph = new ArrayList[V];
        for (int i = 0; i < V; i++)
            graph[i] = new ArrayList<>();

        boolean[] vis = new boolean[V];

        // {vtx, wsf} Pair used
        int[] dis = new int[V];
        int[] par = new int[V];

        Arrays.fill(dis, (int) (1e9));
        Arrays.fill(par, -1);

        PriorityQueue<pair> pq = new PriorityQueue<>((a, b) -> {
            return a.wsf - b.wsf;
        });

        pq.add(new pair(src, 0));
        par[src] = -1; // initially source ka parent -1
        dis[src] = 0; // source ka kudh se distance 0.

        while (pq.size() != 0) {

            pair rn = pq.remove();
            int vtx = rn.src, wsf = rn.wsf;

            if (vis[vtx]) // wsf>=dis[vtx] == > basically agar mera wsf dis[vtx] se bada ya equal hai to
                          // mujhe distance array ko update karne ki jaroorat he nhi hai to continue kar
                          // do. To matlab cycle hai
                continue;

            vis[vtx] = true;
            for (Edge e : graph[vtx]) {
                int v = e.v, w = e.w;
                if (!vis[v] && wsf + w < dis[v]) { // Agar mera distance wsf + w se jyada hai, iska matlab mujhe koi
                                                   // aisa mila hai jo meretak current dis[v] se bhi lightest path ke
                                                   // sath pahunch jayego, to maine tab apne distance ko mara update;
                    dis[v] = wsf + w;
                    par[v] = vtx; // Aur parent ko bhi sath mai update mar diya.
                    pq.add(new pair(v, wsf + w));
                }
            }
        }
    }

    // b <============= 743. Network Delay Time(with better complexity)===========>
    // https://leetcode.com/problems/network-delay-time/

    public int networkDelayTime_(int[][] times, int n, int k) {

        // Edge : {v,w}
        ArrayList<int[]>[] graph = new ArrayList[n + 1];

        for (int i = 0; i < n + 1; i++)
            graph[i] = new ArrayList<>();

        for (int[] time : times) {
            graph[time[0]].add(new int[] { time[1], time[2] });
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            return a[1] - b[1];
        });

        boolean[] vis = new boolean[n + 1];
        int[] dis = new int[n + 1];

        Arrays.fill(dis, (int) 1e9);

        pq.add(new int[] { k, 0 });
        dis[k] = 0;
        while (pq.size() != 0) {
            int[] rn = pq.remove();
            int u = rn[0], wsf = rn[1];
            if (vis[u])
                continue;

            vis[u] = true;

            for (int[] e : graph[u]) {
                int wt = e[1], v = e[0];
                if (!vis[v] && wt + wsf < dis[v]) {
                    dis[v] = wt + wsf;
                    pq.add(new int[] { v, wsf + wt });
                }
            }
        }

        int maxDistance = 0;
        for (int i = 1; i < n + 1; i++) {
            if (dis[i] == (int) 1e9)
                return -1;

            maxDistance = Math.max(maxDistance, dis[i]);
        }
        return maxDistance;
    }

    // ! Important Point to note :-
    // ! Directed graph mai visited ki waise jaroorat nhi hoti hai. (in Most cases.)

    // b <============= 787. Cheapest Flights Within K Stops ====>
    // https://leetcode.com/problems/cheapest-flights-within-k-stops

    // Good test case to dry run

    // 4
    // [[0,1,1],[0,2,5],[1,2,1],[2,3,1]]
    // 0
    // 3
    // 1

    // Jo uper test case hai uspe dry run kar. Usse tereko samaj aayega ki hum na
    // distance array ko use kar sakte hai aur na he visited ko. To agar isko simply
    // he karna start kar denge to TLE aaya.

    // vis aur distance use karne se ye hoga ki jo mere edges jo shi wt aur steps ke
    // sath badme aa rahe the, unhe wo queue mai dalne he nhi dega. Jisse mujhe
    // answer kabhi mil he nhi payega

    // To aisa kya karein ab ??

    // ? The code below will give TLE since the number of comparison have incereased
    // ? since their is no boundation of which edge can enter the queue. All the
    // ? edge are entering the queue.

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        ArrayList<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        // Edge {v,w}
        for (int[] flight : flights) {
            int u = flight[0], v = flight[1], w = flight[2];
            graph[u].add(new int[] { v, w });
        }

        // Pair {vtx,wsf,steps}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            return a[1] - b[1];
        });

        boolean[] vis = new boolean[n];

        pq.add(new int[] { src, 0, -1 });

        int cheapPrice = (int) 1e9;
        while (pq.size() != 0) {
            int[] rn = pq.remove();
            int vtx = rn[0], wsf = rn[1], steps = rn[2];

            if (vtx == dst && steps <= k && wsf < cheapPrice) {
                cheapPrice = wsf;
            }
            for (int[] Edge : graph[vtx]) {
                int v = Edge[0], w = Edge[1];
                pq.add(new int[] { v, wsf + w, k + 1 });
            }
        }

        return cheapPrice == (int) 1e9 ? -1 : cheapPrice;
    }

    // b <============= Belman Ford Algo ================>

    // Dijikastra hume agar -ve edges hote hain to shi answer nikal ke nhi deta hai.
    // Auur agar dijkstra mai -ve weight ki cycle milti hai to ∞ loop chal jata hai.
    // Jiskko hum apne aap rok to sakte hai pr tabhi bhi wo shi answer nhi nikal ke
    // deta.

    // # Dijkstra may aur may not correct answer dede in -ve edges.
    // This may be true when the cycle overall weight will be +ve. Sirf correct
    // answer aane ki possibility.

    // Belman ford ye bataata hai ki graph ke andar -ve weight ka cycle hai ki nhi.
    // Iske liye Graph edges ki form mai he hona chahiye.

    // ? Agar mere graph mai -ve cycle hai (overall cycle weight is -ve) to answer
    // ? possible he nhi hai. Matlab jo bhi answer aayega wo galat aayega.

    // Dijkstra humesha ek spannig tree nikal ke deta hai aur ek spanning tree mai
    // V - 1 edges ki requirement hoti hai to reach every vertex. Matlab mai at most
    // V -1 edges ka use karke har vertex tak jaa sakta hun.

    // So agar V-1 ke baad koi bhi update hua, iska matlab ye hai ki -ve weight ki
    // cycle hai.

    // ! Belmanford is also a single source algo.

    // Yahan pe edges chahiye hoti hain.

    // Yahan pe do array use honge. Prev aur current.

    // ? Matrix is used to just get a good idea of about what is happening. Hume bas
    // ? do array use karne hai prev aur current. Aur har iteration ki starting mai
    // ? prev ko current mai copy kar dena hai.

    // # How to dry run
    // Jitni vertex hain, utni kar sari edges ko traverse karna hai
    // Ab manle AB edge aayi with wt = -1. To hum prev mai check karenge ki A kis
    // weight ke sath aaya hai. Aur usme AB edge ki wt add kardenge to B mai us
    // value ke sath pahunchenge. To Agar kam weight ke sath B pahunchenge to B ko
    // current wale array mai update kar denge.

    // # If current array does not update
    // Agar Kahin pe bhi current wala array update nhi hota hai, to matlab aage aur
    // traverse karne ki jaroorat nhi hai. Just whin pe break kar do. For ex :
    // Agar ek graph hai . Ek single vertex ko sare vertex point kar rahe hon.
    // To isi case mai hume aage traverse nhi karna hai.

    // # For Negative Cycle
    // Agar last edge jo hum extra traverse kar rahe hain. (extra isiliye kyunki
    // hume sirf V - 1 edges he chahiye hote hain), agar usme current wala array ek
    // ` bar bhi update hota hai, to iska matlab ye hai ki negativeCycle Exist karti
    // hai. To bas tab hume sahi answer nhi mil sakta kyunki jaise ki uper bataya ki
    // ∞ loop chalta hai to wo har bar he array ko update karta rahega. To answer
    // galat milega.

    // # Kyunki Number of edges = V -1 for spanning tree that is created by dikstra.
    // Therefore yahan pe edgeCount ye signify kar raha hai ki atmost kitni edge ke
    // sath mai kisi vertex pe sabse kam wt ke sath pahunch sakta hun

    // # edge : {src,dest,weight}
    public static void bellmanFord(int[][] edges, int N, int src) {
        int[] prev = new int[N];
        Arrays.fill(prev, (int) 1e9);
        prev[src] = 0;

        boolean isNegativeCycle = false;
        for (int edgeCount = 1; edgeCount <= N; edgeCount++) { // edgeCount ki value ye denote karti hai ki mai atmost
                                                               // kitni edges ko use karke mai minimum weight ke sath us
                                                               // vertex tak pahuchunga
            int[] curr = new int[N];
            for (int i = 0; i < N; i++)
                curr[i] = prev[i];

            boolean isAnyUpdate = false;
            for (int[] e : edges) {
                int u = e[0], v = e[1], w = e[2];
                if (prev[u] + w < curr[v]) { // mai prev mai jis weight ke sath aaya tha, agar usme mai current edge ka
                                             // weight add kardun aur wo curr[v] se kam hai to use update kardo.
                    curr[v] = prev[u] + w;
                    isAnyUpdate = true;
                }
            }

            prev = curr; // Prev ko ab current mai point kara diya aur ab next iteration mai prev ko
                         // current mai dubara copy kar denge

            if (edgeCount == N && isAnyUpdate) // Jo last edgeCount hai wo excess hai kyunki hume at most V - 1 edges
                                               // required hoti hain sari vertex tak pahunchne ke liye. To agar edge
                                               // count == N hua aur koi bhi update hua us time current array mai, to
                                               // -ve cycle hai. Aisa isiliye kyunki update tabhi hoga jab aur kam
                                               // weight ke sath aayenge, to wo ek loop chalta rahega.
                isNegativeCycle = true;

            if (!isAnyUpdate) // Agar kabhi bhi aisa hua ki curr ek bar kabhi bhi update nhi hua to mujhe aage
                              // traverse karne ki jaroorat he nhi hai. Whin pe break kardo. Ye kab hoga jab
                              // Agar ek graph hai . Ek single vertex ko sare vertex point kar rahe hon.To isi
                              // case mai hume aage traverse nhi karna hai.
                break;
        }
    }

    // b <============= 787. Cheapest Flights Within K Stops ====>
    // https://leetcode.com/problems/cheapest-flights-within-k-stops

    // Here used belman ford algo
    // edgeCount denote atmost kitne edges ko use karke hum vertex tak kitne minimum
    // weight ke sath pahunche

    public int findCheapestPrice_(int n, int[][] flights, int src, int dest, int k) {

        int[] prev = new int[n];
        Arrays.fill(prev, (int) 1e9);
        prev[src] = 0;

        for (int edgeCount = 1; edgeCount <= n; edgeCount++) {
            int[] curr = new int[n];
            for (int i = 0; i < n; i++)
                curr[i] = prev[i];

            for (int[] edge : flights) {
                int u = edge[0], v = edge[1], w = edge[2];

                if (prev[u] + w < curr[v]) {
                    curr[v] = prev[u] + w;
                }
            }
            prev = curr;

            if (edgeCount == k + 1)
                break;
        }

        return prev[dest] == (int) 1e9 ? -1 : prev[dest];

    }

    // b <===================== The Maze ===================>
    // https://www.lintcode.com/problem/787/

    // ! My method
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        // write your code here
        int n = maze.length, m = maze[0].length;
        int er = destination[0], ec = destination[1];
        LinkedList<int[]> que = new LinkedList<>();
        que.add(start);

        boolean[][] vis = new boolean[n][m];
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        vis[start[0]][start[1]] = true;
        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {

                int[] rn = que.removeFirst();
                int sr = rn[0], sc = rn[1];

                for (int d = 0; d < dir.length; d++) {
                    int r = sr, c = sc;
                    for (int rad = 1; rad < Math.max(n, m); rad++) {
                        r = sr + rad * dir[d][0];
                        c = sc + rad * dir[d][1];

                        if (r >= 0 && c >= 0 && r < n && c < m) {
                            if (maze[r][c] == 0)
                                continue;
                            else
                                break;
                        } else
                            break;
                    }

                    r -= dir[d][0];
                    c -= dir[d][1];

                    if (vis[r][c])
                        continue;
                    vis[r][c] = true;
                    que.addLast(new int[] { r, c });
                    if (r == er && c == ec)
                        return true;
                }

            }
        }
        return false;
    }

    // Bhaiya Method
    // 490

    // Maine kiya kya ki kyunki yahan pe friction less surface de rakhi hai to jahan
    // pe bhi ek push mai ball jaki rukeggi usko mai mark kar dunga

    // yahi karna hai simple kaam
    // ? Yahan pe humne visited ka array liya hai to mark. Why didn't we make
    // ? changes in the maze Array?

    // Aisa isiliye kiya kyunki agar mai aisa karta to ball jahan pe bhi rukti to
    // mai use mark kar deta, aur next time wo mere liye wall ka kaam karta jo ki
    // galat ho kyunki wall to wahan pe hain he nhi. Isse hume galat answer milta

    public boolean hasPath_(int[][] maze, int[] start, int[] destination) {
        int n = maze.length, m = maze[0].length, sr = start[0], sc = start[1], er = destination[0], ec = destination[1];
        LinkedList<Integer> que = new LinkedList<>();
        boolean[][] vis = new boolean[n][m];
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

        que.add(sr * m + sc); // converted it into 1-d
        vis[sr][sc] = true;

        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {
                int idx = que.removeFirst(), i = idx / m, j = idx % m;
                for (int[] d : dir) {

                    int r = i, c = j;
                    if (r == er && c == ec)
                        return true;
                    while (r >= 0 && c >= 0 && r < n && c < m && maze[r][c] == 0) { // The radius loop in a different
                                                                                    // way
                        r += d[0];
                        c += d[1];
                    }

                    // Direction minus isiliye kiya Taki mai 1 se pehle ka just 0 tha wahan pe aaun
                    // aur mujhe wo r and c mil jaye
                    r -= d[0];
                    c -= d[1];

                    if (vis[r][c])
                        continue;

                    vis[r][c] = true;
                    que.addLast(r * m + c);

                }

            }
        }
        return false;
    }

    // b <================== Maze II ==================>
    // https://www.lintcode.com/problem/788/description

    // ! Important Point :

    // # In dijikstra, you cannot use bfs without cycle. It will always give you
    // # wrong result.

    // ? Why ? Kyunki agar mai pehle he ek vertex ko mark kar diya to wo vertex
    // ? dubara dalega he nhi chahe wo baad mai kam weight ke sath he kyun na aaye.
    // ? To isiliye bfs with cycle use karte hain.

    // Dry run kar samaj mai aa jayega. Take a basic rectangle graph.

    // Baki question same uper wale ki tarah hai.

    // ! To Dijikstra mai hum bfs without cycle use karte hain ya to distance array
    // ! ka use karte hain.

    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        // write your code here
        int n = maze.length, m = maze[0].length, sr = start[0], sc = start[1], er = destination[0], ec = destination[1];
        PriorityQueue<int[]> que = new PriorityQueue<>((a, b) -> {
            return a[2] - b[2];
        });
        boolean[][] vis = new boolean[n][m];
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

        // {row, columnm totalSteps}
        que.add(new int[] { sr, sc, 0 });
        vis[sr][sc] = true;

        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {
                int[] rn = que.remove();
                int i = rn[0], j = rn[1], totalSteps = rn[2];

                if (i == er && j == ec)
                    return totalSteps;

                vis[i][j] = true;
                for (int d = 0; d < dir.length; d++) {
                    int r = i, c = j;
                    int steps = 0;
                    while (r >= 0 && c >= 0 && r < n && c < m && maze[r][c] == 0) {
                        r += dir[d][0];
                        c += dir[d][1];
                        steps++;
                    }

                    r -= dir[d][0];
                    c -= dir[d][1];
                    steps--;
                    if (!vis[r][c])
                        que.add(new int[] { r, c, totalSteps + steps });

                }

            }
        }
        return -1;
    }

    // b With Distance Array : Bhaiya's Code

    public static class pair_ implements Comparable<pair_> {

        int r = 0, c = 0, steps = 0;

        pair_(int r, int c, int steps) {
            this.r = r;
            this.c = c;
            this.steps = steps;
        }

        @Override // Override to check if the override function name is same as written in java
        public int compareTo(pair_ o) {
            return this.steps - o.steps; // same this - other concept
        }

    }

    public int shortestDistance_(int[][] maze, int[] start, int[] destination) {
        int n = maze.length, m = maze[0].length, sr = start[0], sc = start[1], er = destination[0], ec = destination[1];
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

        int[][] dis = new int[n][m];
        for (int[] d : dis)
            Arrays.fill(d, (int) 1e8);

        PriorityQueue<pair_> que = new PriorityQueue<>();
        que.add(new pair_(sr, sc, 0));
        dis[sr][sc] = 0;

        while (que.size() != 0) {
            pair_ p = que.remove();
            if (p.r == er && p.c == ec)
                return p.steps;
            for (int[] d : dir) {
                int r = p.r, c = p.c, steps = p.steps;
                while (r >= 0 && c >= 0 && r < n && c < m && maze[r][c] == 0) {
                    r += d[0];
                    c += d[1];
                    steps++;
                }

                r -= d[0];
                c -= d[1];
                steps--;

                if (steps >= dis[r][c])
                    continue;

                que.add(new pair_(r, c, steps));
                dis[r][c] = steps;
            }
        }

        return -1;
    }

    // b <=================== Maze III =========================>
    // Leetcode 499

    // Yahan pe basically lexographically sort wali first string return karni hai
    // agar kahin pe jane ke liye steps equal hain. To isiliye comparator mai string
    // ka comparison add kiya taki agar steps equal ho to wo pehle wali string
    // nikale jo lexographically pehle aaye

    // ? Baki puar same hai. Thode se hole ke check add kiye hain. Bas. Same uper
    // ? wale question ki tarah hai.

    public static class stringPair implements Comparable<stringPair> {
        int r = 0, c = 0, steps = 0;
        String str = "";

        stringPair(int r, int c, int steps, String str) {
            this.r = r;
            this.c = c;
            this.steps = steps;
            this.str = str;
        }

        @Override
        public int compareTo(stringPair p) {
            if (this.steps != p.steps)
                return this.steps - p.steps;
            return this.str.compareTo(p.str); // To compare two strings lexographically. Same this - other concept.
        }
    }

    public static String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        int sr = ball[0], sc = ball[1], er = hole[0], ec = hole[1], n = maze.length, m = maze[0].length;
        PriorityQueue<stringPair> que = new PriorityQueue<>();
        que.add(new stringPair(sr, sc, 0, ""));
        int[][] dis = new int[n][m];
        for (int[] d : dis) {
            Arrays.fill(d, (int) 1e9);
        }

        dis[sr][sc] = 0;

        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        String[] dirS = { "d", "u", "r", "l" };

        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                stringPair rn = que.remove();
                int i = rn.r, j = rn.c, totalSteps = rn.steps;
                String str = rn.str;

                if (i == er && j == ec)
                    return str;

                for (int d = 0; d < dir.length; d++) {
                    int r = i, c = j;

                    int steps = 0;
                    // Jaise he hole milta hai to break karna hai to isisliye !(r == er && c == ec)
                    // check add kiya.

                    // !(r == er && c == ec) check can also be written as (r != er) || (c != ec)
                    while (r >= 0 && c >= 0 && r < n && c < m && maze[r][c] == 0 && !(r == er && c == ec)) {
                        r += dir[d][0];
                        c += dir[d][1];
                        steps++;
                    }

                    // Agar hole milta hai to use sidhe queue mai add karna hai
                    if (!(r == er && c == ec)) { // Agar hole nhi hai to he mai minus karunga
                        r -= dir[d][0];
                        c -= dir[d][1];
                        steps--;
                    }

                    if (totalSteps + steps > dis[r][c]) // equal to check not added since we want the lexographically
                                                        // string first for same steps to the hole
                        continue;

                    que.add(new stringPair(r, c, totalSteps + steps, str + dirS[d]));
                    dis[r][c] = totalSteps + steps;
                }
            }
        }
        return "impossible";
    }

    // Bhaiya's Method :

    // Yahan pe jo update kar rehe hain dis[r][c] with pointPair wo karne ki
    // jaroorat nhi hai waise kyunki jo humne class mai comparable likha hai wo sab
    // handle kar lega agar do string aise aayi jinke steps equal hue to

    // Aur hum yahan pe pura bfs run hone de rahe hain jab answer mil gaya tab bhi
    // joki bilkul bhi requierd nhi hai.

    // ? Isko Simple visited se bhi kar sakte hain

    public static class pointPair implements Comparable<pointPair> {
        int r = 0, c = 0, steps = 0;
        String psf = "";

        pointPair(int r, int c, int steps, String psf) {
            this.r = r;
            this.c = c;
            this.steps = steps;
            this.psf = psf;
        }

        @Override
        public int compareTo(pointPair o) {
            if (this.steps != o.steps)
                return this.steps - o.steps;
            else
                return this.psf.compareTo(o.psf);
        }
    }

    public String findShortestWay_(int[][] maze, int[] start, int[] destination) {
        int n = maze.length, m = maze[0].length, sr = start[0], sc = start[1], er = destination[0], ec = destination[1];
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        String[] dirS = { "d", "u", "r", "l" };
        pointPair[][] dis = new pointPair[n][m];
        for (int i = 0; i < n * m; i++) // Good way to add some data in 2-D array
            dis[i / m][i % m] = new pointPair(i / m, i % m, (int) 1e8, "");

        PriorityQueue<pointPair> que = new PriorityQueue<>();
        pointPair src = new pointPair(sr, sc, 0, "");

        que.add(src);
        dis[sr][sc] = src;

        while (que.size() != 0) {
            pointPair p = que.remove();
            for (int i = 0; i < 4; i++) {
                int[] d = dir[i];

                int r = p.r, c = p.c, steps = p.steps;
                while (r >= 0 && c >= 0 && r < n && c < m && maze[r][c] == 0 && !(r == er && c == ec)) { // !(r == er &&
                                                                                                         // c == ec) ==
                                                                                                         // (r != er ||
                                                                                                         // c != ec)
                    r += d[0];
                    c += d[1];
                    steps++;
                }

                if (!(r == er && c == ec)) {
                    r -= d[0];
                    c -= d[1];
                    steps--;
                }

                pointPair np = new pointPair(r, c, steps, p.psf + dirS[i]);
                if (steps > dis[r][c].steps || dis[r][c].compareTo(np) <= 0)
                    continue;
                // This dis[r][c].compareTo(np) <= 0 check added since hume dis[r][c] ko tabhi
                // ` update karna hai ya to steps kam ho ya agar steps equal ho aur jo string aa
                // rahi hai wo pehle wali string se lexographically achi ho.

                // For example agar pehle dis[r][c] ki string thi "ul" aur ab aayi "lul" to
                // {"ul" - "lul" > 0} by this - other concept. To ab update karna hai kyunki
                // "lul" achi hai lexographically "ul" se.

                que.add(np);
                dis[r][c] = np;
            }
        }

        pointPair ans = dis[er][ec]; // end mai answer return kara
        return ans.steps != (int) 1e8 ? ans.psf : "impossible";
    }
}