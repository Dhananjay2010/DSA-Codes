import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class l008_GTree {

    // Like a folder structure in computer
    public class Node {

        int val = 0;
        ArrayList<Node> children;

        Node(int val) {
            this.val = val;
            this.children = new ArrayList<>();
        }

    }

    public static int height(Node root) {

        int maxHeight = -1;
        for (Node child : root.children) {
            maxHeight = Math.max(maxHeight, height(child));
        }

        return maxHeight + 1;
    }

    public static int size(Node root) {
        int size = 0;
        for (Node child : root.children) {
            size += size(child);
        }

        return size + 1;
    }

    public static int maximumEle(Node root) {
        int maxEle = root.val;

        for (Node child : root.children) {
            maxEle = Math.max(maxEle, maximumEle(child));
        }

        return maxEle;
    }

    public static boolean findData(Node root, int data) {

        if (root.val == data) {
            return true;
        }

        boolean res = false;
        for (Node child : root.children) {
            res = res || findData(child, data);
        }
        return res;

    }

    public static boolean rootToNodePath(Node root, int data, ArrayList<Node> ans) {

        if (root.val == data) {
            ans.add(root);
            return true;
        }

        boolean res = false;

        for (Node child : root.children) {
            res = res || rootToNodePath(child, data, ans);
        }

        if (res) {
            ans.add(root);
        }
        return res;
    }

    // b <============Diameter of generic tree====================>
    // https://www.pepcoding.com/resources/online-java-foundation/generic-tree/diameter-of-generic-tree-official/ojquestion

    // ! Bhaiya Method :

    // Faith aisa rakha ki mujhe sare root ke children apn diamaeter or height
    // return karenge. Ab mai sare diameter ko compare karke max diamter nikalunga.
    // Aur is max diameter ko jo bhi do max height hongi inme 2 add karke diameter
    // return karunga

    // To basicallly hume sare childs ki height mai se do max heights nikalni hai
    // aur mera code easily ho jayega

    // {diameter, height}
    public static int[] diameter_Generic_Tree(Node root) {

        int h1 = -1, h2 = -1, d = 0;
        for (Node child : root.children) {
            int[] cp = diameter_Generic_Tree(child); // child pair(cp)
            // To make it easy to calculate the two max height, always consider that h1 is
            // greater than h2.

            if (cp[1] > h1) { // for test case like if h1=10 and h2=8 and then cp[1] is 12. Then the greater
                              // height of the three will be 10 and 12.
                h2 = h1;
                h1 = cp[1];
            } else if (cp[1] > h2) { // For test case like if h1 =10 and h2=8 and then cp[1] is 9, then the greater
                                     // two hieght of the two will be 10 and 9.
                h2 = cp[1];
            }

            d = Math.max(d, cp[0]);
        }

        return new int[] { Math.max(d, h1 + h2 + 2), Math.max(h1, h2) + 1 };
    }

    // By Use of static variable

    // ? Faith remain the same . Static ne sare child ke diameter ka max pehle se he
    // ? store kiya hai. use bas to max height se pass hota hua diameter se compare
    // ? karna hai bas

    static int d = 0;

    public static int diameter_Generic_Tree_static(Node root) {

        int h1 = -1, h2 = -1;
        for (Node child : root.children) {
            int ch = diameter_Generic_Tree_static(child); // child height(ch)
            // To make it easy to calculate the two max height, always consider that h1 is
            // greater than h2.

            if (ch > h1) { // for test case like if h1=10 and h2=8 and then ch is 12. Then the greater
                           // height of the three will be 10 and 12.
                h2 = h1;
                h1 = ch;
            } else if (ch > h2) { // For test case like if h1 =10 and h2=8 and then ch is 9, then the greater
                                  // two hieght of the two will be 10 and 9.
                h2 = ch;
            }
        }
        d = Math.max(d, h1 + h2 + 2);

        return Math.max(h1, h2) + 1;
    }

    // b <==============Serialize And Deserialize N - Ary Tree =============>
    // https://pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/trees/serialize-and-deserialize-n-ary-tree/ojquestion

    // Generic tree mai simple preorder ki call lagake serialize likh diya. Aur jab
    // sare child traverseho jaye to null dala indication ke liye ki. Iske bina hum
    // ye decide ni kar pate ki kon kiska child hai.

    // Aur deserialize karne ke liye, use basically split marke array mai convert
    // kiya. Ab agar array ki value null nhi hai to stack mai dala aur aur agar null
    // hai to stack ke top ke element ko remove kiya aur ab jo element top pe hai
    // iske children mai add kar diya

    // Encodes a tree to a single string.
    public static void serialize(Node root, StringBuilder sb) {

        sb.append(root.val + " ");
        for (Node child : root.children) {
            serialize(child, sb);
        }

        sb.append(null + " "); // This null signifies that the root has traversed through all the children and
                               // has no child left to traverse.
    }

    public static String serialize(Node root) {

        if (root == null)
            return " ";
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);

        return sb.toString();

    }

    public static Node deserialize_(String[] arr) {

        LinkedList<Node> st = new LinkedList<>();

        for (int i = 0; i < arr.length - 1; i++) { // isko length -1 tak isiliye chalaya kyunki end mai jo null hoga wo
                                                   // root ka hoga. To basically , humne root ko end mai return karne ke
                                                   // liye.
            if (!arr[i].equals("null")) {
                st.addFirst(new Node(Integer.parseInt(arr[i])));
            } else {
                Node rn = st.removeFirst();
                st.getFirst().children.add(rn);
            }
        }

        return st.removeFirst();
    }

    // Decodes your encoded data to tree.
    public static Node deserialize(String str) {
        if (str == " ")
            return null;
        String[] arr = str.split(" ");
        return deserialize_(arr);
    }

    // b <<<<<<<<<< === BFS in Generic Tree ===>>>>>>>>>>>>>>>

    public static void BFS_Generic_Tree(Node root) {
        LinkedList<Node> que = new LinkedList<>();
        que.addLast(root);
        int level = 0;
        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                Node rn = que.removeFirst();

                for (Node child : rn.children) {
                    que.addLast(child);
                }
            }
            level++;
        }
    }

    // b <==================Generic Tree Zigzag Level Order Traversal ===========>
    // https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/

    // We will be using two LinkedList one for queue and another for stack.
    // We will always be inserting the elements in the stack except for the root
    // element.

    // Now the trick is to swap the two linkedlist after the level is complete.
    // Now by doing dry run, we figured out that we if the level is odd, then the
    // child will be pushed to stack from right to left

    // Similarly , if the level is even, then the child will be push from left to
    // right

    // ? The key point is that the node is always pushed to the stack.

    public List<List<Integer>> zigzagLevelOrder(Node root) {

        List<List<Integer>> ans = new ArrayList<>();
        LinkedList<Node> que = new LinkedList<>();
        LinkedList<Node> st = new LinkedList<>();

        que.addLast(root);
        int level = 0;
        while (que.size() != 0) {
            int size = que.size();
            ArrayList<Integer> myAns = new ArrayList<>();
            while (size-- > 0) {
                Node rn = que.removeFirst();
                myAns.add(rn.val);

                if (level % 2 == 0) {
                    for (Node child : rn.children) { // Level even == > children pushed to L to R
                        st.addFirst(child); // # Always pushed to the stack
                    }
                } else {
                    for (int i = rn.children.size() - 1; i >= 0; i--) { // Level odd == > children pushed to R to L
                        st.addFirst(rn.children.get(i)); // # Always pushed to the stack
                    }
                }
            }

            ans.add(myAns);
            LinkedList<Node> temp = st;
            st = que;
            que = temp;
            level++;
        }

        return ans;
    }

    // b <====================Flatten Generic Tree to Linked List =================>

    // O(n²) tab hota agar hum tail nikalne ke liye bar bar cget tail naam ka
    // function call karte

    // b Method 1 :

    // ? Time Complexity : O(n²)
    public static void linearize_nSquare(Node node) {
        for (Node child : node.children) {
            linearize(child);
        }

        for (int i = node.children.size() - 1; i >= 0; i--) {
            Node tail = getTail(node.children.get(i - 1));
            tail.children.add(node.children.get(i));
            node.children.remove(i);
        }
    }

    private static Node getTail(Node node) {
        while (node.children.size() != 0) {
            node = node.children.get(0);
        }

        return node;
    }

    // b Method 2 :

    // Humne basically kiya ye ye faith rakha ki child apne aap ko linearize karke
    // le aayega aur sath mai tail bhi return karega.

    // Humne yahan pe loop reverse se isiliye chalaya hai taki mai linearize karne
    // ke sath sath mai parent child connnection ko break kar paun

    // Humne pehle he global tail nikladi taki hume badme sidhe whi return karna
    // pade. Aur Global tail jo hui wo sabse right wale child ki he hogi humesha.

    // Now just dry run and figure out.

    // ? Time Complexity : O(n)
    public static Node linearize_(Node root) {

        if (root.children.size() == 0)
            return root;

        Node gTail = linearize_(root.children.get(root.children.size() - 1)); // Global Tail

        for (int i = root.children.size() - 2; i >= 0; i--) {

            Node tail = linearize_(root.children.get(i));
            tail.children.add(root.children.get(i + 1)); // Connection linearize root tail to the next child of root
            root.children.remove(root.children.size() - 1); // To break parent child connection

        }

        return gTail;
    }

    public static void linearize(Node node) {

        linearize_(node);
    }

    // b <<================Are Two Trees Similar in shape =================>

    // We have to check if the two trees that are given are similar in shape or not.
    // So to check that we are going to check if the number of children of each
    // nodes are same or not. If they are same, then the tree is similar in shape.

    public static boolean areSimilar(Node n1, Node n2) {

        if (n1.children.size() != n2.children.size()) {
            return false;
        }

        int size = n1.children.size(); // yahan pe tabhi aa payenge jab uper wale if ko cross karenge. Isisliye n1 ka
                                       // size lo ya n2 ka size, dono ka size same hoga
        boolean res = true; // True isiliye rakha kyunki hume end tak sare nodes ko check karna hai.
        for (int i = 0; i < size; i++) {
            res = res && areSimilar(n1.children.get(i), n2.children.get(i)); // Aur isiliye yahan pe && use hua, naki
                                                                             // ||. Aur agar ek bhi false hua to wo aage
                                                                             // call he ni lagayega
        }

        return res;
    }

    // b <<===================== Are two trees Mirror of each other ========>>

    public static boolean areMirror(Node n1, Node n2) {
        if (n1.children.size() != n2.children.size()) { // Agar bolte ki values bhi same honi chahiye to yahan pe values
                                                        // ka check bhi add hota
            return false;
        }

        int size = n1.children.size();
        boolean res = true;
        for (int i = 0, j = size - 1; i < size && j >= 0; i++, j--) {
            res = res && areMirror(n1.children.get(i), n2.children.get(j));
        }

        return res;
    }

    // b <<================ Is Tree Symmetric =====================>

    // Agar koi tree apna he mirror image hai to wo to symmetric to hoga he

    public static boolean IsSymmetric(Node node) {

        return areMirror(node, node);
    }

    // b << ==================== Kth Largest Element in Tree ==============>>
    // ? Has to be done without using the priority queue.

    // Ab iss question mai hum kth Largest nikalna hai. To iss question ko thoda
    // aise sochna hai.

    // ∞ se just choti value hui (A) i.e 1st largest
    // (A) se just choti value hui (B) i.e. 2nd Largest
    // (B) se just choti value hui (C) i.e 3rd largest

    // ? Matlab hume bas floor nikalte jana hai k times aur upperBound ko change
    // ? karke bhejte rehna hai

    // Time Complexity : O(kn)
    public static int floor(Node node, int ub) {

        int max = -(int) 1e9;
        for (Node child : node.children) {
            max = Math.max(max, floor(child, ub));
        }

        return node.val < ub ? Math.max(node.val, max) : max;
    }

    public static int kthLargest(Node node, int k) {

        int ub = (int) 1e9; // ` upper bound
        for (int i = 0; i < k; i++) {
            ub = floor(node, ub);
        }

        return ub;
    }

    // b <<<=====================Ceil And Floor ==============>>>

    // # Ceil : Jo bhi value data se badi hai, unme se jo sabse choti value
    // # hogi(Math.min) use ceil bolte hain

    // # Floor : Jo bhi values data se choti hain, unme se jo sabse badi value
    // # hogi(Math.max), use floor bolte hain

    static int ceil;
    static int floor;

    public static void ceilAndFloor(Node node, int data) {
        if (node.val > data) {
            ceil = Math.min(ceil, node.val);
        }

        if (node.val < data) {
            floor = Math.max(floor, node.val);
        }

        for (Node child : node.children) {
            ceilAndFloor(child, data);
        }
    }

}