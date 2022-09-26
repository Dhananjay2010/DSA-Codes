import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class l003unionFind {

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

    public static void display(ArrayList<Edge>[] graph, int V) {

        for (int i = 0; i < V; i++) {
            System.out.print(i + " -> ");
            for (Edge e : graph[i]) {
                System.out.print("(" + e.v + "," + e.w + ")" + " ");
            }

            System.out.println();
        }
    }

    // b <============= Union Find Algo =============>

    // ? It works better when the edges are gives. The whole graph is not required.

    // Here AUB(A union B) != BUA (B union A)

    // # But why ?
    // Consider A to be size of 10³ and B to be size of 10. Then adding 10³ elements
    // in B is is a big operation and is of high complexity. So the answer remains
    // the same but the complexity are different.

    // # Ab union hoga kaise. Iske liye mai har ek set ka leader bana raha hun aur
    // # jo bhi query hogi us set ke elements ki, wo leader se hogi

    // So agar mai ek set ko country se denote karta hun to agar mai us country ka
    // size puchta hun to mujhe leader answer karega. Aur koi ni.

    // ! Dono ke leader alag alag hain aur unke beech mai edge exist karta hai, to
    // ! matlab wo dono same country(set) ko belong karte honge.

    // ! Aur agar dono ka leader same hai aur unke beech mai edge exist karti hai to
    // ! cycle hai aur hum cycle wali edge ko add he nhi karte

    // # Now what we do is use path compression for to find the leader of the
    // # childs.

    // Manle ki h height ka tree hai aur us tree ka leader root node hai. Ab
    // agar leaf se pucha leader ke bare mai to wo apne parent se puchega and same
    // goes on till the leader is reached. Ab jaise he leader mila, aate samay sare
    // root to node path ke parents honge unka leader set karte hue aayenge hum.
    // Isse kya hoga ki humari searching optimize ho jayegi kyuki ab us path mai jo
    // `bhi node hoga uska leader nikalna humare liye just O(1) ka operation hoga
    // kunki humne aate samay leader set karte hue aaye the.

    // Ab isse ek aur cheez hui ki tree ki height reduce ho gyi(taki leader jaldi
    // mil jaye)

    // ! Isi cheez ko path compression bolte hain. Jo humari complexity high hone se
    // ! bacha leta hai.

    // # Ab agar maine parent find time parent pointers ko update nhi kiya aur maine
    // # size ka bhi koi check ni lagaya (matlab tree ki height ko control karne ke
    // # liye), to ek find ki complexity jati hai O(n).

    // Height control karne ka matlab ye hai. Agar manle do tree hai ek ki height H
    // hai aur ek single node ka hai. Aur dono ke apne apne leader hain. To join
    // karte time, agar single node ke leader ko pure ka leader banayenge to combine
    // tree ki height increase ho jayegi. Per agar Jyada height wale ke leader ko
    // combine ka leader banaya, to height increase nhi hogi.

    // ! Important Point
    // Aagr hum path compression bhi use karte hain aur sath mai union karte time
    // size of the tree ko bhi factor mai lete hain(matlab tree ki height control
    // karne ke liye), to jo find ki complexity jati hai wo hoti hai log*(N) means
    // amortized logN. and this will be always less that O(4).

    // # How less than 4 . Assuming the worst case here.

    // Ab manle ki ek tree hai aue use similar tree mila, to humne jab uska union
    // kiya to simple leader ko join kar diya or ye sab O(1) mai hua. Aise he karte
    // karte agar height 10^9 ko reach kari to humko 10^9 unit of time laga tree
    // ko banane mai. Ab agar kisi leaf pe find parent ka operation hua to 10^9 unit
    // of time lagega parent ko find karne mai. To agar hum total operation dekhein,
    // 10^9 + 1 aur total time lage 2 * 10^9. Therefore ek operation ke liye jo time
    // laga hai wo hoga O(2) which is less than 4.

    // ? The find complexity will be log*(N){with size union and path compression}
    // ? and log*(N) will be always less the O(4)

    // ? If we ignore the path compression, the complexity becomes log(N)

    // # The graph formed at the end of union find algo is called spanning tree(a
    // # graph with no cycle).

    // ! Union Find algo basic steps :

    // 1. Find the leader of the set. (To do it, use path compression is necessary)
    // 2. Then while doing the union of the two sets, use size check in order to
    // identify who will the new leader of the set.
    // 3. If two vertices have same leader and a edge exist between them, then there
    // is a cycle.
    // 4. If two vertices have different leader and an edge exist between them, then
    // ` both of the vertices belong to the same country(same set).

    // It is a linear algo. Complexity is V + E log(N)== V + E O(4)==V + E

    static int[] par, size;

    // find parent with path compression.
    public static int findLeaderParent(int u) {
        return par[u] == u ? u : (par[u] = findLeaderParent(par[u])); // (par[u] = findParent(par[u])); This here used
                                                                      // is the
        // path compression.
    }

    public static void merge_Union(int p1, int p2) { // To merge(union) the two sets.

        if (size[p1] > size[p2]) {
            par[p2] = p1;
            size[p1] += size[p2];
        } else {
            par[p1] = p2;
            size[p2] += size[p1];
        }
    }

    // {u,v,w}
    public static void unionFind(int[][] Edges, int N) {
        ArrayList<Edge>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }

        int[] par = new int[N];
        int[] size = new int[N];

        for (int i = 0; i < N; i++) { // Initially mera parent mai khud he hun aur mera size 1 hai
            par[i] = i;
            size[i] = 1;
        }

        for (int[] edge : Edges) {
            int u = edge[0], v = edge[1], w = edge[2];

            int p1 = findLeaderParent(u);
            int p2 = findLeaderParent(v);

            if (p1 != p2) { // dono ke leader alag hai per dono ke beech mai edge exist karti hai to dono
                            // same set ko belong karte hain

                merge_Union(p1, p2); // Sari jo questioning hogi wo leader se hogi
                addEdge(graph, u, v, w); // To create a union set graph
            }
        }
    }

    // B <=============MST(Minimum Spanning Tree) ===============>

    // # Graph connected with no cycle is called spanning tree.
    // # Ab agar spanning tree mai edges ke weight ka sum minimum hua to MST

    // ! Remember the number of components remain same in MST.
    // The components in the original graph and the components after the union find
    // algo

    // Aisa spanning tree, jiska edges ki weight ka sum mimimum ho, use bolte hain
    // minumum spanning tree.

    // Ek graph ke to bahut sare spanning tree ban sakte hain, to jo miminum wala
    // hoga with edges weight is called MST.

    // ! Condition ek ye hain ki graph connected he rehna chahiye.

    /*
     * Ab Mera union find algo is baat ki guarentee to deta he hai ki spanning tree
     * banega. Aur agar mai edges ko sorted by weight pass karun, to wo minimum
     * spanning tree dega. Per aisa kyun ??????
     */

    // ? Aisa isiliye kyunki jab bhi mai union find algo lagata hun aur mai, tab mai
    // ? jo edges cycle ke liye resposible hoti hai, mai use include he nhi karta
    // ? hun. Ab agar maine weight wise sorted kiya, to jo sari heavy edges hai wo
    // ? baad mai aayengei aur kyunki wo cycle ke liye responsible ho sakti hain to
    // ? hum unhe include he nhi karenge. Isi reason ki wajah se MST Create Ho jata
    // ? hai edeges ko sorted rakhne se.

    // b <============= Kruskal Algo ==================>

    // It finds the minimum spanning tree.
    // How ? Same by passing the sorted edges array for the reason mentioned above.

    public static void kruskalAlgo(int[][] edges, int N) {
        // {{u,v,w}}
        Arrays.sort(edges, (a, b) -> {
            return a[2] - b[2];
        });
    }

}