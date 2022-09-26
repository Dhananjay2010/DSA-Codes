import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DSUQuestions {

    // ! Jahan pe bhi grouping dikh rahi hai, whan pe union find lago do. Abhi tak
    // ! to yhi dikh raha hai

    // b <==============684. Redundant Connection ===================>
    // https://leetcode.com/problems/redundant-connection/

    // Same union find algo lagayi. Bas merege karte time size ka check nhi lagaya,
    // aise hemerge kar diya.

    static int[] par, size;

    public static int findLeaderParent(int u) { // Will find the leader of the set.
        return par[u] == u ? u : (par[u] = findLeaderParent(par[u]));
    }

    public int[] findRedundantConnection(int[][] edges) {
        int N = edges.length + 1;
        par = new int[N];

        for (int i = 0; i < N; i++) {
            par[i] = i;
        }

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];

            int p1 = findLeaderParent(u);
            int p2 = findLeaderParent(v);

            if (p1 != p2) {
                par[p2] = p1; // Humne union merge ki jagah pe kisi ko bhi leader set kar diya. Size ko check
                              // ni kiya. Isse complexity O(6 ya 7) pahunchti hai max. par[p1] = p2; bhi likh
                              // dete to code same he run karta.
            } else {
                return new int[] { u, v };
            }
        }

        return new int[] {};
    }

    // b<===========1061. Lexicographically Smallest Equivalent String =====>
    // https://leetcode.ca/all/1061.html

    // So kiya ye ki pattern find kiya.

    // Agar hum edges create karlein since A[i] and B[i] are equivalent characters.
    // To jinke bhi beech mai edge hogi wo sab same country ko belong karte honge.
    // Aur country mai jo sabse chota hoga, use uska leader set kar denge to
    // lexographically order mil jayega kyunki sabse chota he chahiye humesha humko.

    // Taki easy ho jaye humne integer mai convert kar rahe hai taki array use kar
    // payein aur fast rahe. Baki logic same union find ka hai.

    public static int findLeader_(int u) {
        return par[u] == u ? u : (par[u] = findLeader_(par[u]));
    }

    public String smallestEquivalentString(String s1, String s2, String baseStr) {

        ArrayList<int[]> edges = new ArrayList<>();
        for (int i = 0; i < s1.length(); i++) {
            char ch1 = s1.charAt(i);
            char ch2 = s2.charAt(i);

            edges.add(new int[] { ch1 - 'a', ch2 - 'a' });
        }

        par = new int[26];
        for (int i = 0; i < 26; i++) {
            par[i] = i;
        }

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            int p1 = findLeader_(u);
            int p2 = findLeader_(v);

            if (p1 != p2) {
                if (p1 < p2) {
                    par[p2] = p1;
                } else {
                    par[p1] = p2;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < baseStr.length(); i++) {
            char ch = baseStr.charAt(i);
            int parent = findLeader_(ch - 'a');
            sb.append((char) ('a' + parent));
        }

        return sb.toString();

    }

    // Bhaiya Method :

    public String smallestEquivalentString_(String s1, String s2, String baseStr) { // Kiya same hai meri tarah
        for (int i = 0; i < 26; i++)
            par[i] = i;
        for (int i = 0; i < s1.length(); i++) { // Maine bas edges create kari thi more understanding ke liye aur taki
                                                // code same rahe, baki same jo tune kiya hai. Edges na create karke
                                                // direct aise bhi kar sakte hain, jyada better way hai
            char c1 = s1.charAt(i), c2 = s1.charAt(i);
            int p1 = findLeader_(c1 - 'a'), p2 = findLeader_(c2 - 'a');
            par[p1] = Math.min(p1, p2);
            par[p2] = Math.min(p1, p2);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < baseStr.length(); i++) {
            char ch = baseStr.charAt(i);
            int parent = findLeader_(ch - 'a');
            sb.append((char) ('a' + parent));
        }

        return sb.toString();
    }

    // b <=============Number of Islands ===================>
    // https://leetcode.com/problems/number-of-islands/

    // ? Humne yahan pe basically same 2-d to 1-d mai convert kiya.

    // Jitne bhi 1 hain wo abhi independent island hain. To abhi wo khud ke he
    // parent hain.

    // Jaise he hum unhe group
    // karte jayenge(ek he island mai merge karte jayenge), utne he single island
    // kam honge

    // # To end mai jitne set bachenge, utne he seperate island honge kyunki jo same
    // # island ko belong karte honge unka alag se set ban gaya hoga.

    public int numIslands(char[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        par = new int[n * m];

        for (int i = 0; i < n * m; i++) { // Har ek cell ko vertex man liya
            par[i] = i;
        }

        int noOfSets = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') // Jitne 1 honge whi to island bana payenge. To Starting mai mana jitne 1 utne
                                       // island.
                    noOfSets++;
            }
        }

        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    int p1 = findLeaderParent(i * m + j);

                    for (int d = 0; d < dir.length; d++) {
                        int r = i + dir[d][0];
                        int c = j + dir[d][1];

                        if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == '1') {

                            int p2 = findLeaderParent(r * m + c);
                            if (p1 != p2) { // Agar p1=p2 aaya matlab wo pehle se same island ka part tha kyunki unka
                                            // parent same aaya hai. Aur p1!=p2 aaya matlab wo pehle mere island ka part
                                            // nhi tha(kyunki dono kar parent different hai), per kyunki wo mere
                                            // adjacent mai hai to mai use apne island ka part bana sakta hun. To paren
                                            // same kiya aur kyunki ab maine 2 sets ko ek mai merge kar diya hai to
                                            // number of sets to decrease to honge he by 1;
                                par[p2] = p1;
                                // Cannot write par[p1]=p2 since agar aise likha to pehle valid direction ke
                                // `baad set ka leader p2 ho jata. Agar aisa likhan hai to p1 ka duabra
                                // findLeaderParent call karna hoga update karne ke liye.

                                // par[p2] = p1 mai humeshaa global parent p1 he bana rahega.

                                // Dry run with three sets. you will understand.
                                noOfSets--;
                            }
                        }
                    }
                }
            }
        }

        return noOfSets;
    }

    // b <================695. Max Area of Island===============>
    // https://leetcode.com/problems/max-area-of-island/

    // Same as number of islands. Bas ek size ke array aur rakh diya taki size bhi
    // calculate kar sakein.

    // Kyunki humne initially size ko 1 rakha hai, to jaise he merge karte hain,
    // leader ke size ko increase kar dete hain by the merging set size.

    // To bas end mai maxSize he return karna hai

    public int maxAreaOfIsland(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        par = new int[m * n];
        size = new int[m * n];
        for (int i = 0; i < m * n; i++) {
            par[i] = i;
            size[i] = 1;
        }

        int maxSize = 0;
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    int p1 = findLeaderParent(i * m + j);
                    for (int d = 0; d < dir.length; d++) {
                        int r = i + dir[d][0];
                        int c = j + dir[d][1];

                        if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 1) {
                            int p2 = findLeaderParent(r * m + c);
                            if (p1 != p2) {
                                par[p2] = p1;
                                size[p1] += size[p2];
                            }
                        }
                    }
                    maxSize = Math.max(maxSize, size[p1]);
                }
            }
        }

        return maxSize;
    }

    // b <==============839. Similar String Groups ===========>
    // https://leetcode.com/problems/similar-string-groups/

    // Jab bhi hum union find lagane ki sochte hain, to hum edges dhundhte hain
    // To ab jitne bhi combination ho sakte the, humne unko edge manke chalaya.
    // Ab lekin jo similar hai unko he group karna tha, to bas tabhi he group kiya.

    // To Baki pura same hai union find algo ki tarah. Same concept.

    // Parent ka array bhi integer rakha kyunki string ka role bas similar check
    // karne mai hai jise hum given string ke array se nikal sakte hain easily.
    // Therefore index he vertex(yahan pe string) ko denote kar raha hai.

    // ! Important Point :

    // # Initially har koi apni country ka leader tha, to jo number of sets the wo n
    // # the.

    // # Ab hum jaise he merge karte gaye, to same country wale ek he set mai aa
    // # gaye. To jitni bar merge perform hua, utni bar kisi already existing set
    // # mai koi add hua. Therefore har merge mai ek set decrease hua.

    // # So total sets end mai n - total merge operation

    public static boolean isSimilar(String s1, String s2) {

        // Since it is given that the two strings are always anagram of each other.
        // Isiliye agar same index pe character same nhi hai aur uska count 2 se jyada
        // hai, to hum ek swap mai to dubara string to bana he nhi payenge, to similar
        // nhi hogi

        // t a r s
        // r a t s

        // 0th and 2nd index are not similar and its count is 2 therefore are similar.

        // t a r s
        // a r t s

        // 0th, 1st, and 2nd index are not similar, count =3, so not similar

        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i) && ++count > 2)
                return false;
        }

        return true;
    }

    public int numSimilarGroups(String[] strs) {

        int n = strs.length;

        for (int i = 0; i < n; i++) {
            par[i] = i;
        }

        int numberOfSets = n;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isSimilar(strs[i], strs[j])) {
                    int p1 = findLeaderParent(i);
                    int p2 = findLeaderParent(j);

                    if (p1 != p2) { // Merge operation
                        par[p1] = p2;
                        numberOfSets--;
                    }
                }
            }
        }

        return numberOfSets;
    }

    // b <================ 1905. Count Sub Islands =======================>
    // https://leetcode.com/problems/count-sub-islands/

    // Humko yahan pe basically recursion ko rokna nhi tha. Chalne dena tha taki hum
    // grid 2 mai pura island ek mark karlein aur pata karlein island ka.

    // ? To bas ek bar jab mark karke aa gaye, to check kiya ki mere 1 ke liye grid
    // ? 1 mai koi 1 hai ki nhi. Agar grid 1 mai 0 mila to iska matlab mai jis
    // ? island ko traverse kar raha hun, wo grid1 ke island ka part nhi ban sakta.
    // ? Ab kyunki mujhe grid2 wla island to pura mark karna he hoga, isiliye mai
    // ? recursion ko pura chalne dunga.

    // Aur agar kahin se bhi false milta hai to whi return karwaunga

    public static boolean dfs_countSubIslands(int[][] grid1, int[][] grid2, int[][] dir, int sr, int sc) {
        grid2[sr][sc] = 2; // Mark kiya

        boolean res = true;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r < grid2.length && c < grid2[0].length && grid2[r][c] == 1) {
                res = dfs_countSubIslands(grid1, grid2, dir, r, c) && res; // Agar ek direction se false aaya aur agle
                                                                           // direction se true aay, to hume to fales he
                                                                           // return karwana hai, per recursion ko rokna
                                                                           // nhi hai
            }
        }

        return res && grid1[sr][sc] == 1;
    }

    public int countSubIslands(int[][] grid1, int[][] grid2) {

        int n = grid2.length, m = grid2[0].length;
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

        int noOfSubIslands = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid2[i][j] == 1) {
                    boolean res = dfs_countSubIslands(grid1, grid2, dir, i, j);
                    if (res)
                        noOfSubIslands++;
                }
            }
        }

        return noOfSubIslands;
    }

    // b <================== Number of Islands II ===================>
    // https://www.lintcode.com/problem/434/

    // ? Yahan pe kiya kya ??

    // Jaise he hum koi edge nikal rahe hain(means cell jo land mai convert hogi),
    // hun count mai +1 kar rahe hain indicating ki ek nya island add hua hai. Ab
    // hum us island ke surrounding mai dekehnge, agar wo kisi aur island ka part
    // ` ban sakta hai ki nhi. Agar ban payega to use merge karke count mai -1 kar
    // denge.

    // Jaise he hum edge nikalte jayenge, waise he hum apni grid mai update bhi
    // karte jayenge. Taki hume pata rahe ki 1 kahan kahan pe hai.

    // Ek edge case dhyan rakhna, agar koi already land hai aur use dubara land mai
    // convert karna hai, to koi bhi changes nhi honge.

    // # This is using grid
    public List<Integer> numIslands2(int n, int m, int[][] operators) {

        List<Integer> ans = new ArrayList<>();
        int[][] grid = new int[n][m];

        par = new int[n * m];
        for (int i = 0; i < n * m; i++) {
            par[i] = i;
        }

        int count = 0;
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        for (int[] p : operators) {
            int i = p[0], j = p[1];

            if (grid[i][j] == 1) { // if there is already a land where we are going then nothing should be updated.
                ans.add(count);
                continue;
            }
            grid[i][j] = 1;

            count++;
            int p1 = findLeaderParent(i * m + j);
            for (int d = 0; d < dir.length; d++) {
                int r = i + dir[d][0];
                int c = j + dir[d][1];

                if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 1) {
                    int p2 = findLeaderParent(r * m + c);

                    if (p1 != p2) {
                        par[p2] = p1;
                        count--;
                    }
                }
            }
            ans.add(count);
        }
        return ans;
    }

    // # Without using the grid space.

    // kyunki ab sare check parent ke array se lagene honge, to initially parent
    // array ko -1 se fill kiya
    public List<Integer> numIslands2_(int n, int m, int[][] operators) {

        List<Integer> ans = new ArrayList<>();

        par = new int[n * m];
        for (int i = 0; i < n * m; i++) {
            par[i] = -1;
        }

        int count = 0;
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        for (int[] p : operators) {
            int i = p[0], j = p[1];

            if (par[i * m + j] != -1) { // Agar parent array pehle se he visited ha us point mai, matlab wahan pe pehle
                                        // se he land hai
                ans.add(count);
                continue;
            }

            par[i * m + j] = i * m + j;

            count++;
            int p1 = i * m + j; // Jo aaya humne use he apna global parent bana diya agar uske aa pass 1 hai to
            for (int d = 0; d < dir.length; d++) {
                int r = i + dir[d][0];
                int c = j + dir[d][1];

                if (r >= 0 && c >= 0 && r < n && c < m && par[r * m + c] != -1) {
                    int p2 = findLeaderParent(r * m + c);
                    if (p1 != p2) {
                        par[p2] = p1;
                        count--;
                    }
                }
            }
            ans.add(count);
        }

        return ans;
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

    // b <======== 1168. Optimize Water Distribution in a Village=========>
    // https://leetcode.ca/all/1168.html
    // https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/graphs/optimize-water-distribution-official/ojquestion

    // ! To yahan pe kiya kya ???

    // Yahan pe hume basically min cost nikalni hai water ko har ek ghar tak
    // pahunchane ke liye.

    // Humpe do option hain ya to well bana lete hain ya pipe ka use karke kisi aur
    // ke well se pani le lenge

    // To humko dikh raha hai ki hume min cost nikalni hai. To hum yahan pe mst wla
    // concept use kar sakte hain. Per mera graph to connected he nhi hai.

    // To iske liye humne kiya ye ki ek aur vertex manli(a bigger well that will
    // supply to other wells in the village)

    // Ab humne sare edges jo us bigger well se sare houses ko ja sakte the, humne
    // `unko bhi add kar diya. Humne ye mana ki bigger well se house tak pipe se
    // transfer karne ki cost hai wells ke array mai.

    // ? To ab ye ek pipes ki problem mai convert ho gyi aur humara graph connected
    // ? ho gya. Ab agar hum isme mst lagayenge to hume humara answer mil jayega.
    // ? Bas jab hum merge operation karenge, tab he us edge (pipe connection) ko
    // ? add karne ki cost bhi add karte jayenge aur end mai whi return kar denge

    public static int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {

        ArrayList<int[]> edges = new ArrayList<>();
        for (int[] pipe : pipes) {
            edges.add(pipe);
        }

        for (int i = 0; i < wells.length; i++) {
            edges.add(new int[] { 0, i + 1, wells[i] }); // Added the new pipe connection to all the houses from the
                                                         // ` bigger well.
        }

        edges.sort((a, b) -> { // Sorted the array for MST
            return a[2] - b[2];
        });

        // ! Baki pura union find algo hai
        par = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            par[i] = i;
        }
        int minCost = 0;
        for (int[] edge : edges) {

            int u = edge[0], v = edge[1], w = edge[2];
            int p1 = findLeaderParent(u);
            int p2 = findLeaderParent(v);

            if (p1 != p2) {
                par[p1] = p2;
                minCost += w;
            }
        }

        return minCost;
    }

    // b <============== Mr. President ================>
    // https://www.hackerearth.com/practice/algorithms/graphs/minimum-spanning-tree/practice-problems/algorithm/mr-president/

    // ! So humne kiya kya ?
    // Humko ek graph de rakha tha cities ke beech mai. Wahan road bhi de rakh thi.
    // Consider them like edges.

    // To humne pehle ek spanning tree create kiya taki hume sari roads jinhe hum
    // demolish kar sakte the unhe nikal dein. Ab hume K se kam karni hai cost of
    // maintainence. To ek sorted arraylist humne create ki while creating the
    // spanning tree. Ab agar K se kam value lani hai with minimum conversion, to
    // pehle sabse badi value ko decrease karna chahiye. to bas whi kiya

    public static int mr_president() {

        // N = > Cities
        // M => bidirectional Roads
        // K => Maintainence cost of the road should be less than k (Achievement)
        // can convert a road to a super road( that has maintainence 1)
        // you can demolish roads
        // In order to get the achievement, find the minimum number of road
        // transformation of standard road to super road

        Scanner scn = new Scanner(System.in);
        int N = scn.nextInt();
        int M = scn.nextInt();
        long k = scn.nextLong();

        ArrayList<int[]> Edges = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int u = scn.nextInt(), v = scn.nextInt(), w = scn.nextInt();
            Edges.add(new int[] { u, v, w });
        }

        Collections.sort(Edges, (a, b) -> {
            return a[2] - b[2];
        });

        par = new int[N + 1];

        for (int i = 0; i < N + 1; i++) {
            par[i] = i;
        }

        int numberOfComponents = N;
        long totalMaintainenceCost = 0;
        ArrayList<Integer> costArray = new ArrayList<>();
        for (int[] edge : Edges) {
            int u = edge[0], v = edge[1], w = edge[2];

            int p1 = findLeaderParent(u);
            int p2 = findLeaderParent(v);

            if (p1 != p2) {
                par[p1] = p2;
                numberOfComponents--;
                totalMaintainenceCost += w;
                costArray.add(w);
            }
        }

        // Why we need to store the weight of the cost of the created spanning tree?

        // Let sau that the spannig trees values are 10,10,10,20,20,20,20.
        // And the value of k is 30.

        // Now dry run and figure out.

        if (numberOfComponents > 1) { // Agar graph mai he number of compoennts jyada hai to sari cities connected reh
                                      // he nhi payegi
            return -1;
        }
        int numberOfTransformation = 0;
        for (int i = costArray.size() - 1; i >= 0; i--) {
            if (totalMaintainenceCost > k) {
                totalMaintainenceCost = totalMaintainenceCost - costArray.get(i) + 1; // +1 for maintainence of the new
                                                                                      // super road.
                numberOfTransformation++;
            } else {
                break;
            }
        }

        return totalMaintainenceCost > k ? -1 : numberOfTransformation;
    }

    // ! Remember a spanning tree has N-1 number of edges

    // b <================ 1584. Min Cost to Connect All Points ============>
    // https://leetcode.com/problems/min-cost-to-connect-all-points/

    // So sabse pehle apna connected graph create kiya. Ab bas simple MST ka logic
    // laga diya.

    // ? Here mapped every point to index. So this makes it very easy so to keep
    // ? track of the point.

    public int minCostConnectPoints(int[][] points) {
        int n = points.length;

        ArrayList<int[]> Edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int u = i, v = j, w = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                // w is the distance between the two points. So i.e weight of the edge.
                Edges.add(new int[] { u, v, w }); // Sare points ko ek dusre se connect karke ek dense graph create
                                                  // kiya.
            }
        }

        par = new int[n];
        for (int i = 0; i < n; i++) {
            par[i] = i;
        }
        Collections.sort(Edges, (a, b) -> {
            return a[2] - b[2];
        });

        int minCost = 0;
        for (int[] edge : Edges) {
            int u = edge[0], v = edge[1], w = edge[2];

            int p1 = findLeaderParent(u);
            int p2 = findLeaderParent(v);

            if (p1 != p2) {
                par[p2] = p1;
                minCost += w;
            }
        }

        return minCost;
    }

    // b <============== 924. Minimize Malware Spread =====================>
    // https://leetcode.com/problems/minimize-malware-spread/

    // As already discussed, Union find wahan pe ache se kaam karta hai jahan pe
    // grouping hoti hai

    // Ab is question ko aise soch ki bahut sari country hai aur har country mai
    // kafi bahut se log reh rahe hain.

    // Aur har country mai ek ya ek se jyada log initially infect ho sakta hain. To
    // main kis country ko save karun ki mai max logon ki jaan bacha sakun

    // Main unko save karna prefer karunga jinme sirf ek he infected hai, taki mai
    // ` use remove karke puri country ko save kar paun.

    // Aur agar aise bahut sari country hain jisme sirf ek infected person hai, to
    // ` unme se jiski population jyada hogi mai usi ko to save karunga kyunki whin
    // pe max logon ki jaan bach payegi.

    // ? Aap sirf ek he node ko cure kar sakte ho

    static int[] population; // Yahan pe har country ka size store kar rahe hai

    public int minMalwareSpread(int[][] graph, int[] initial) {
        // M(initial) == > final nodes that got infected by the malwaare
        // We have to minimize M(initial)
        // Only we can remove one initial infected node.
        // return the node on which removal M(initial) is minimized.
        // In question given the matrix representation of the graph

        int n = graph.length, m = graph[0].length;

        par = new int[n];
        population = new int[n];

        for (int i = 0; i < n; i++) {
            par[i] = i;
            population[i] = 1;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == j || graph[i][j] == 0)
                    continue;

                int p1 = findLeaderParent(i);
                int p2 = findLeaderParent(j);

                if (p1 != p2) { // Yahan pe same country ke logon ko ek sath karke unka ek similar leader bana
                                // diya
                    par[p2] = p1;
                    population[p1] += population[p2];
                }
            }
        }

        // Here have found the infected count of every country.
        int[] infectedCount = new int[n];
        for (int i = 0; i < initial.length; i++) {
            int parent = findLeaderParent(initial[i]);
            infectedCount[parent]++;
        }

        Arrays.sort(initial); // Agar merepe aisa case aaya ki sari country ka size same hai aur sabke
                              // infected log bhi same hain, to mai usko return karunga jiska index small ho.

        int ans = initial[0];
        int maxPopulation = 0;
        for (int i = 0; i < initial.length; i++) {
            int ele = initial[i];
            int parent = findLeaderParent(ele);
            if (infectedCount[parent] == 1 && population[parent] > maxPopulation) { // Yahan pe compare kiya ki agar
                                                                                    // ` bahut sari country hai jinme
                                                                                    // sirf ek he infected hai, to
                                                                                    // mai us country ko bachaunga
                                                                                    // jiski population jyada hogi
                ans = ele;
                maxPopulation = population[parent];
            }
        }

        return ans;
    }

    // b <============= 959. Regions Cut By Slashes ================>
    // https://leetcode.com/problems/regions-cut-by-slashes/

    // # Bfs and DSU can find number of distinct cycles, not the overlapping one.

    // ! If I get two points, then only I can create a line .
    // ! So Basically I would be needing two points.
    // ! Ek line banane ke liye do points to chahiye he honge.

    // # So Agar tu number of distincts cycles nikal de, to tereko wo number of
    // # regions mil jayenge jo slashes ne cut kiya hoga

    // ? Per ab isko karein kaise ????

    // Kuch aise grid de rakhi hai grid = ["/\\","\\/"]
    // Now consider this grid as a 2-d matrix. With string as an array stroring some
    // values in 0th and 1st index.

    // Ab forward slash and backward slash kuch region cut kar rahe hain.
    // let n=grid.length
    // ` Unko show karne ke liye ek +1 size ki matrix ki jaroorat hogi

    // Let N=n+1
    // To humne N*N ki matrix consider ki aur usme points mark kiye.

    // Agar grid mai (0,0) mai / hai to wo (x,y+1) and (x+1,y) points pe draw hoga
    // Agar grid mai (0,1) mai \\ hai to wo (x,y) and (x+1,y+1) points pe draw hoga

    // Ab bas same union find algo lagaya aur grid se ek ek edge nikalte rahe.
    // Aur number of cycles count kar li.

    // # Initially cycle ka count 1 hoga kyunki boundary bhi to ek cycle bana rahi
    // # hogi

    // Ab kyunki boundary ek cycle hai to aur sare edges ek dusre se connected hain
    // to unka parent ek he hoga initially.

    public static int mergeUnion(int p1, int p2) {
        if (p1 != p2) {
            par[p2] = p1;
            return 0;
        }
        return 1; // Agar cycle hui to 1 return karega
    }

    public int regionsBySlashes(String[] grid) {
        int n = grid.length;
        int N = n + 1;

        par = new int[N * N];
        for (int i = 0; i < N * N; i++) {
            if (i / N == 0 || i % N == 0 || i / N == N - 1 || i % N == N - 1) { // Sare boundary elements ka parent 0 ko
                                                                                // maan liya kyunki wo ek sath connected
                                                                                // hain
                par[i] = 0;
            } else {
                par[i] = i;
            }
        }

        int numberOfCycles = 1; // 1 se isiliye start kiya kyunki ek cycle to already hai jo boundary hai
        for (int i = 0; i < n; i++) {
            String str = grid[i];
            for (int j = 0; j < str.length(); j++) {
                char ch = str.charAt(j);
                if (ch == '/') {
                    // Dono points nikale ki kahan pe line draw hogi aur tab merge kara
                    int p1 = findLeaderParent(i * N + j + 1);
                    int p2 = findLeaderParent((i + 1) * N + j);
                    numberOfCycles += mergeUnion(p1, p2);
                } else if (ch == '\\') {
                    int p1 = findLeaderParent(i * N + j);
                    int p2 = findLeaderParent((i + 1) * N + j + 1);
                    numberOfCycles += mergeUnion(p1, p2);
                }
            }
        }

        return numberOfCycles;
    }

    // b <============= 990. Satisfiability of Equality Equations ============>
    // https://leetcode.com/problems/satisfiability-of-equality-equations/

    public boolean equationsPossible(String[] equations) {

        par = new int[26];
        for (int i = 0; i < 26; i++) {
            par[i] = i;
        }

        int[] notArray = new int[26];

        for (String str : equations) { // Pehle jo bhi equal hai un sabko same set ke andar dala
            if (str.charAt(1) == '=') {
                int p1 = findLeaderParent(str.charAt(0) - 'a');
                int p2 = findLeaderParent(str.charAt(3) - 'a');

                if (p1 != p2) {
                    par[p2] = p1;
                }
            }
        }

        for (String str : equations) {// Ab agar maine jo sabhi equal unko ek set mai rakh diya hai to agar ab mujhe
                                      // koi bhi milega jiske parent same honge(reason because they have == between
                                      // them), aur umse ! ka sign aaya, to wo equation false ho jayegi.
            if (str.charAt(1) == '!') {
                int p1 = findLeaderParent(str.charAt(0) - 'a');
                int p2 = findLeaderParent(str.charAt(3) - 'a');

                if (p1 == p2) {
                    return false;
                }
            }
        }
        return true;
    }

    // b <<<<============= Dijikstra =====================>>>>

    // MST pure graph ke weight ko minimise karne ki try karta hai. Ye sabhi edges
    // ke sum ko minimise karne ki try karta hai

    // ! It is a single source algorithm.
    // # So it gives the lightest(minimum) weight path from source to every vertex.

    // It does not work for -ve edges.

    // Simple hai wsf(weight so far) ke terms mai priority queue se bahar nikalte
    // rehna hai simple bfs mai. Bas isse hume ek specific source se kisi vertex tak
    // ka shhortest path mil jata hai

    // ! Prims
    // Iske andar source matter nhi karta
    // Jo edge aap graph mai insert kar rehe ho wo minimum weight wala hona chahiye

    // Prims aur kruskal dono MST banate hain

    // Dono mai difference kya hai ?

    // Prims ke liye hume graph ki requirement hoti hai aur ye dense graph mai bahut
    // acha perform karta hai

    // Kruskal ke liye mujhe sirf edges required hoti hain aur ye sparse graph mai
    // acha perform kata hai

}
