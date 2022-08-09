import java.util.ArrayList;
import java.util.LinkedList;

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

}