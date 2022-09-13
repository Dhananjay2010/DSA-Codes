import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class questions {

    public static class TreeNode {

        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val, TreeNode left, TreeNode right) {
            this(val);
            this.left = left;
            this.right = right;
        }

        TreeNode(int val) {
            this.val = val;
        }
    }

    // b <<============== 968. Binary Tree Cameras (Very Good Question)===========>>
    // https://leetcode.com/problems/binary-tree-cameras/

    // So basically what are the conditions that can happen. A node can has a camera
    // and a node does not have a camera.

    // So if a node has camera, then it is good it can cover itself.
    // ` But what if the node does not have the camera, so it can be the two
    // possibility. One is that its child has a camera and another possibility is
    // that its child doesnot have the camera and it requires camera cover.

    // Therefore to maintain the three status we got to know here, we are using the
    // three values -1, 0 ,1 .

    // So a node can return any of the three values, depending upon.

    // So a parent makes a decision of itself by looking at the status of the child

    // ? The basic faith maine apne dono child se unke status mangawa diye. Ab unki
    // ? status dekh ke mai apna status apne parent ko send karunga.

    // # -1 : Require Camera cover, 0 : has Camera , 1 : Dosen't requrire Camera
    // cover

    static int camera = 0;

    public int minCameraCover_(TreeNode root) {
        if (root == null)
            return 1; // doesnot require any camera cover

        int lc = minCameraCover_(root.left);
        int rc = minCameraCover_(root.right);

        if (lc == -1 || rc == -1) { // If my any child says that it needs camera cover, so will return -1.
            camera++;
            return 0;
        }

        // if we come down by passing the above if, it means that none of my child
        // require camera. So it means that either my child has a camera or or child's
        // child has a camera

        if (lc == 0 || rc == 0) { // If my any child says that it has a camera, then i don't need cover. So will
                                  // return 1
            return 1;
        }

        return -1; // If child's child has camera
    }

    public int minCameraCover(TreeNode root) {
        camera = 0;

        if (minCameraCover_(root) == -1) { // edge case if my root requires a camera and root does not have a parent, so
                                           // will give camera to root.
                                           // For test case : [a,b,null,c,null,d,e]
            camera++;
        }
        return camera;
    }

    // b <===========House Robber III =======================>
    // https://leetcode.com/problems/house-robber-iii/

    // In total there can be two conditions, we can rob a house or we cannot. And by
    // going to both the condition, we have to find the max robbery amount that the
    // thief can take with him.

    // Yahan pe basically, do cheez dhyan rakhni hai. Mai rob karke kitna kama raha
    // hun aur bina rob kiye kitna kama raha hun.

    // ? Consider a test case : [2,7,5,2,6,null,9,null, null, 5,11,4,null]

    // Now when we reach to rob 7. The left subtree returns {2,0} and the right
    // subtree returns {6,16}.

    // Now if we choose to rob 7, then the total amount generated will be 7 + 0 +
    // 16=23 since we cannot rob the just bottom house. So niche se jo bhi without
    // rob aaya humne use calculate kiye.

    // Now if we choose not to rob 7, then we can rob the bottom two houses. So when
    // we rob them the ans will be max(2,0) + max(6,16)=18.

    // Why max ???
    // The case arises where the it not good to rob 6 since we would then not be
    // able to rob its children which have more value than it.

    // Manle ki tereko 7 pe rob nhi karna, to tune 2 pe rob kiye(left child) per
    // tune 6 ko rob nhi kiya(right child) since 6 ke childs ki value jyada hai.
    // Isiliye tune wahan pe max use kiya ye check karne ke liye ki tereko rob karne
    // mai jyada fayda hai ya bina rob karne mai.

    // # {withRob, withoutRob}
    public int[] rob_(TreeNode root) {

        if (root == null)
            return new int[] { 0, 0 };

        if (root.left == null && root.right == null) {
            return new int[] { root.val, 0 };
        }

        int[] lc = rob_(root.left);
        int[] rc = rob_(root.right);

        int[] ans = new int[2];
        ans[0] = root.val + lc[1] + rc[1];
        ans[1] = Math.max(lc[0], lc[1]) + Math.max(rc[0], rc[1]); // Math.max kyunki mai rob kar bhi sakta hun aur nhi
                                                                  // ` bhi kar sakta
        return ans;
    }

    public int rob(TreeNode root) {
        int[] ans = rob_(root);

        return Math.max(ans[0], ans[1]);
    }

    // b <===============Longest ZigZag Path in a Binary Tree=============>
    // https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/

    // Isko thoda aise soch ki tera dono child tereko teen cheezein return karayega
    // namely {forwardSlope, backwardSlope, maxZigZagPathLength}

    // Mere forward slope mai end hota hua path hoga mere left child ka
    // ` backward slope path + 1

    // Mere backward slope mai end hota hua path hoga mere right child ka forward
    // slpoe ka path + 1

    // Ab max ZigZag length ko calculate karne ke liye, max nikala jo merepe end hua
    // forward slope path hai, jo merepe end hota hua backward slope path hai aur
    // Math.max(leftChild ka maxZigzag path, rightChild ka maxZigZag path)

    // {forwardSlope, backwardSlope, maxZigZagPathLength}
    public int[] longestZigZag_(TreeNode root) {

        if (root == null)
            return new int[] { -1, -1, -1 };

        int[] lc = longestZigZag_(root.left);
        int[] rc = longestZigZag_(root.right);

        int[] ans = new int[3];
        ans[0] = lc[1] + 1;
        ans[1] = rc[0] + 1;
        ans[2] = Math.max(Math.max(ans[0], ans[1]), Math.max(lc[2], rc[2])); // This can be removed if we use static
                                                                             // variable since it will store the max
                                                                             // already as said in faith.

        return ans;
    }

    public int longestZigZag(TreeNode root) {
        int[] ans = longestZigZag_(root);
        return ans[2];
    }

    // b <================Distribute Coins in BinaryTree========================>
    // https://leetcode.com/problems/distribute-coins-in-binary-tree/

    // Yahan pe basically ye logic lagaya hai ki maine apne left se kitne jyada
    // coins hai ya kitne kam coins hain wo manga liye. Aur maine right se bhi same
    // cheez manga li. Ab maine apne aap pe net coins nikale aur apne aaap ka -1 bhi
    // kiya aur wo return karwa diya

    // Agar hum dekhenge, jo total number of moves honge wo jo mere right child ka
    // net result coins ka aur left child ka net coins ka resultant ko add karke
    // aayega

    // Dry run on test case : [1,0,0,null,3]

    // So basically 3 ne return kiya 2 kyunki uske pass 2 excess the apne ko include
    // karne ke baad. Ab 3 ke uper wale 0 ne 1 return kiya apne aap ke 1 include
    // karke. 1 ke right wale 0 ne -1 return kiya kyunki usko 1 coins ki jaroorat
    // thi, to net 1 pe resultant aaya 0;

    // Ab agar moves count karein to 3 ne 0 ko ek ek karke do bar 2 total do coins
    // diye. To iske move hue 2. ab 0 ne ek coin 1 ko diya . 1 move aur aur ab 1 ne
    // ye coin 0 ko diya to ek move aur, therefore total 4 moves.

    static int moves = 0;

    public int distributeCoins_(TreeNode root) {

        if (root == null)
            return 0;

        int leftRequiredExcess = distributeCoins_(root.left);
        int rightRequiredExcess = distributeCoins_(root.right);

        moves = moves + Math.abs(leftRequiredExcess) + Math.abs(rightRequiredExcess);
        return leftRequiredExcess + rightRequiredExcess + root.val - 1;
    }

    public int distributeCoins(TreeNode root) {
        moves = 0;
        distributeCoins_(root);
        return moves;
    }

    // b <=============Minimum Time to Collect All Apples in a Tree==============>
    // https://leetcode.com/problems/minimum-time-to-collect-all-apples-in-a-tree/

    // ? Iska Faith

    // Faith ye rakha ki mera sare child mujhe min time return karenge apple collect
    // karne ka. Ab mai apne parent ko return karunga jo mera time lagega agar apple
    // mila niche to.

    public int minTime_(int root, ArrayList<Integer>[] tree, List<Boolean> hasApple, boolean[] vis) {

        int time = 0;

        vis[root] = true; // visited isiliye rakhna pada kyunki question mai undirected tree bola hai.

        for (int child : tree[root]) {
            if (!vis[child]) { // sirf unki call lagayi jo abhi visit nhi hue hain
                time += minTime_(child, tree, hasApple, vis);
            }
        }

        if (time != 0) {
            // agar time!=0 hai matlab use niche kahin pe apple mila hai to +2 karke return
            // kiya kyunki usne uss apple ko collect karne ke liye niche gaya hoga aur uper
            // `bhi aaya hoga
            return time + 2;
        } else if (hasApple.get(root)) { // Agar root he apple hai to +2 to return karna he hoga kyunki according to
                                         // faith maine apne se parent tak jane ka time bata raha hun
            return 2;
        } else {
            return 0;
        }
    }

    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {

        ArrayList<Integer>[] tree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int[] e : edges) { // Created a tree with edges given. Since in question it is given that the tree
                                // is undirected, so have to add both the edge connection like in a graph.
            tree[e[0]].add(e[1]);
            tree[e[1]].add(e[0]);
        }

        boolean[] vis = new boolean[n];

        int ans = minTime_(0, tree, hasApple, vis);
        return ans != 0 ? ans - 2 : ans; // ans -2 since humne top root ka bhi +2 kar diya tha return karte time.
    }

    // b <============Longest Univalue Path============>
    // https://leetcode.com/problems/longest-univalue-path/

    // ? faith is simple : maine apne child ko kaha mujhe height aur maxLongestPath
    // ? till apne node ka nikalke dede. Mai uske baad check karke shi answer
    // ? return kardunga.

    // Test case where code phata for first time : [1,null,1,1,1,1,1,1]

    // Ek cheez soch agar kisi node se connect longest univalue nikali in terms of
    // edges, to max value ya to us node ki height ho sakti hai ya fir usse pass
    // hoke jani wali length.

    // To whi kiya hai teen cheezein return karwayi hain.{value us not necessary to
    // return, can be accessed by {root.left.val and root.right.val} }

    // {heightOfSameValueNode} == > Basically ye height return karega. Per agar
    // group of node ki same value hai to unki height sath sath nikalta jayega per
    // jaise he node ki value change hui height dubara 0 se start hogi kyunko single
    // node ki height 0 hoti hai {height in terms of edges}

    // {maxLongetPath} == > Ye max value ko store karne ke liye hai. Static use kar
    // sakte hain iski jagah pe

    // # Agar thoda soche to it is like calculating diameter of a tree. Bus thoda
    // # twist hai

    // {value, heightOfSameValueNode, maxLongestPath}
    public int[] longestUnivaluePath_(TreeNode root) {
        if (root == null) {
            return new int[] { -(int) 1e9, -1, 0 };
        }

        if (root.left == null && root.right == null) {
            return new int[] { root.val, 0, 0 };
        }

        int[] lc = longestUnivaluePath_(root.left);
        int[] rc = longestUnivaluePath_(root.right);

        int[] ans = new int[3];

        ans[0] = root.val;
        ans[2] = Math.max(lc[2], rc[2]);
        if (root.val == lc[0] && root.val == rc[0]) {
            ans[2] = Math.max(ans[2], lc[1] + rc[1] + 2); // +2 for 2 edges that have been added
            ans[1] = Math.max(lc[1], rc[1]) + 1;
        } else if (root.val == lc[0]) {
            ans[2] = Math.max(ans[2], lc[1] + 1);
            ans[1] = lc[1] + 1;
        } else if (root.val == rc[0]) {
            ans[2] = Math.max(ans[2], rc[1] + 1);
            ans[1] = rc[1] + 1;
        }

        return ans;

    }

    public int longestUnivaluePath(TreeNode root) {

        return longestUnivaluePath_(root)[2];
    }

    // b <=============Maximum Product of Splitted Binary Tree=========>
    // https://leetcode.com/problems/maximum-product-of-splitted-binary-tree/

    // Humne yahan pe basically sare combinations try kar liye. Iske liye Tree ka
    // total sum chahiye tha, to pehle wo nikala .

    // Total sum nikalne ke baad ab jitne bhi subtree ke combinations ho sakte the,
    // wo un sab ko compare karke max nikalte gaye.

    // ? Agar hum total sum mai se kisi bhi subtree ko minus karte hain to remaining
    // ? value dusre remaining subtree ki hogi. Bas ye logic se sare combinations ka
    // ? max nikal liya

    // === >> Yahan pe long use kiya kyunki values range se bahar jaa rahi thi

    public int sum(TreeNode root) {

        if (root == null)
            return 0;

        return sum(root.left) + sum(root.right) + root.val;
    }

    // {subtreeSum, MaxProduct}
    public long[] maxProduct_(TreeNode root, int sum) {

        if (root == null)
            return new long[] { 0, 0 };

        long[] lc = maxProduct_(root.left, sum);
        long[] rc = maxProduct_(root.right, sum);

        long[] ans = new long[2];

        ans[1] = Math.max(lc[1], rc[1]);
        ans[0] = lc[0] + rc[0] + root.val;
        ans[1] = Math.max(ans[1], (sum - ans[0]) * ans[0]);

        return ans;
    }

    public int maxProduct(TreeNode root) {

        int sum = sum(root);

        return (int) (maxProduct_(root, sum)[1] % (int) (1e9 + 7));
    }

    // b <===========Boundary Traversal of binary tree===============>
    // https://practice.geeksforgeeks.org/problems/boundary-traversal-of-binary-tree/1

    ArrayList<Integer> boundary(TreeNode node) {

        if (node == null)
            return new ArrayList<>();

        ArrayList<Integer> ans = new ArrayList<>();
        LinkedList<TreeNode> que = new LinkedList<>();
        LinkedList<TreeNode> st = new LinkedList<>();
        que.addLast(node);

        while (que.size() != 0) {
            int size = que.size();
            ans.add(que.getFirst().val);
            while (size-- > 0) {
                TreeNode rn = que.removeFirst();

                if (size == 0) {
                    st.addFirst(rn);
                }

                if (rn.left != null) {
                    que.add(rn.left);
                }
                if (rn.right != null) {
                    que.add(rn.right);
                }
            }
        }

    }
}