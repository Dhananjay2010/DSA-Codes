import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

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

}