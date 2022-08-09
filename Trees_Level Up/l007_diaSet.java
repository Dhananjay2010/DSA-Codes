import java.util.List;
import java.util.ArrayList
import java.util.LinkedList;

public class l007_diaSet {

    public static class TreeNode {

        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static int size(TreeNode root) {
        return root == null ? 0 : size(root.left) + size(root.right) + 1;
    }

    public static int height(TreeNode root) {
        return root == null ? -1 : Math.max(height(root.left), height(root.right)) + 1;
    }

    // b <=============Diameter of a Tree===========================>

    // Diameter of the tree is that minimun length of which a circle is drawn, the
    // circumference will cover all the nodes in the tree if the endpoints of the
    // diameter of the tree are streched.

    // # So basically faith ye rakha hai left se diameter mangaya, right se diameter
    // # mangaya, aur dono ka max nikala.

    // ? Per ek diameter wo bhi to ho sakta hai jo merese pass hoke jayega, usko bhi
    // ? compare karna padega, isiliye lh + rh + 2 se compare kiya end mai.

    // The complexity of the tree here is nlogn. If we consider a skew tree, its
    // complexity will move to n².

    // ? Why ?????

    // Since for every node we are calculating the height. So in complete binary
    // tree, it will be a logN process but for skew treee, it will be a O(n)
    // processs.

    public int diameterOfBinaryTree(TreeNode root) {

        if (root == null) {
            return 0;
        }

        int ld = diameterOfBinaryTree(root.left);
        int rd = diameterOfBinaryTree(root.right);

        int lh = height(root.left);
        int rh = height(root.right);

        return Math.max(Math.max(ld, rd), lh + rh + 2);
    }

    // b 2. Array Method

    // Now what we did was to remove the height function, we are also storing the
    // height recursively. Therefore the operation of calculating height reduces to
    // O(1);

    // ! Now important point to Note :

    // # If in interview , always use the class method as it is more redable rather
    // # than this.

    // So what should we do is to create a class that will store a diameter and
    // height variable

    // {d,h}
    public int[] diameterOfBinaryTree_2(TreeNode root) {

        if (root == null) {
            return new int[] { 0, -1 };
        }

        int[] lr = diameterOfBinaryTree_2(root.left);
        int[] rr = diameterOfBinaryTree_2(root.right);

        int[] myAns = new int[2];
        myAns[0] = Math.max(Math.max(lr[0], rr[0]), lr[1] + rr[1] + 2);
        myAns[1] = Math.max(lr[1], rr[1]) + 1;

        return myAns;
    }

    // b 3. Using a static variable

    // What we did was use a static variable to store the diameter and wrote the
    // function to calculate the height.

    // Jab Hum static use karte hain, to faith ke words thode change ho jate hain.

    // To yahan pe static diameter humare left diameter aur right diameter ke max to
    // store karke chalta hai.

    // ` Bas humne use root se pass hoti hui height se compare kiya hai

    int diameter = 0;

    public int diameterOfBinaryTree_03(TreeNode root) {
        if (root == null)
            return -1;

        int lh = diameterOfBinaryTree_03(root.left);
        int rh = diameterOfBinaryTree_03(root.right);

        diameter = Math.max(diameter, lh + rh + 2);
        return Math.max(lh, rh) + 1;
    }

    public int diameterOfBinaryTree_static(TreeNode root) {
        int x = diameterOfBinaryTree_03(root);

        return diameter;
    }

    // b <==============Path Sum I===============>
    // https://leetcode.com/problems/path-sum/

    public boolean hasPathSum(TreeNode root, int targetSum) {

        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null && targetSum - root.val == 0) {
            return true;
        }

        boolean res = hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);

        return res;
    }

    // b <=============Path Sum II ===================>
    // https://leetcode.com/problems/path-sum-ii/

    // ? It is same as the above problem.Just ArrayList of ArrayList return karna
    // hai

    public static void pathSum(TreeNode root, int targetSum, List<List<Integer>> ans, ArrayList<Integer> myAns) {

        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null && targetSum - root.val == 0) {

            myAns.add(root.val);
            ArrayList<Integer> newAns = new ArrayList<>(myAns);
            ans.add(newAns);
            myAns.remove(myAns.size() - 1); // Yahan pe remove karna tu bhul gaya tha.

            return;
        }

        myAns.add(root.val);
        pathSum(root.left, targetSum - root.val, ans, myAns);
        pathSum(root.right, targetSum - root.val, ans, myAns);
        myAns.remove(myAns.size() - 1);
    }

    public List<List<Integer>> pathSum_II(TreeNode root, int targetSum) {

        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }

        ArrayList<Integer> myAns = new ArrayList<>();

        pathSum(root, targetSum, ans, myAns);

        return ans;
    }

    // b <===============Maximum path sum between two leaves =================>
    // # Good Question(Pehli bar mai nhi hua, Dusri bar mai bhi nhi hua)
    // https://www.geeksforgeeks.org/find-maximum-path-sum-two-leaves-binary-tree/

    // # Basic faith ye tha maine apne left ko bola ki mujhe to apna lefttoleft max
    // # nikal ke dede. same maine right ko bola. Aur tab maine use apne se pass
    // # hota hua leaf to leaf se compare kiya

    // Apne se pass hone wale ke liye maine NodeToLeafMaxPathSum bhi return karwaya
    // sath mai

    // ? Merepe basically teen chezein hain compare karne ke liye.
    // Aisa ho sakta hai ki mera max between two leaves left ke subtree ke 2 leaf ke
    // `beech mai ho

    // Ya fir aisa ho sakta hai ki mera max between two leaves right ke subtree ke 2
    // leaf ke beech mai ho

    // Ya fir aisa bhi ho sakta hai ki mera max between two leaves mujhe include
    // karke mera left ke subtree ke k leaf se right ke subtree ke dusre leaf tak
    // ho sakta hai

    // {LeafToLeafMaxPathSum, NodeToLeafMaxPathSum}
    public static int[] maxPathSum(TreeNode root) {

        if (root == null) {
            return new int[] { -(int) 1e9, -(int) 1e9 };
        }

        if (root.left == null && root.right == null) {
            return new int[] { -(int) 1e9, root.val }; // Agar mai ek leaf node hun to mai leafToleaf max path sum to
                                                       // nhi nikal sakta, aur mera NodeToLeafMaxPathSum meri value ke
                                                       // equall hoga
        }

        int[] lp = maxPathSum(root.left);
        int[] rp = maxPathSum(root.right);

        int[] myAns = new int[2];

        // Agar mera left null hai ya right null hai to mai kabhi bhi apne se pass hota
        // hua max path sum between two leaves to nikal he ni paunga. Aisa isiliye
        // kyunki manle agar mera left null hai to mere left mai to koi leaf present to
        // hoga he nhi. To isiliye ye wla Math.max() if se bahar likha hai

        // For ex:- 9 in the gfg question diagram. Iske liye apply hoga ye.
        // ! Important point
        // For ex:- -1 kabhi apne se pass hota hua leaftoleaf bana he nhi payega. To

        // isiliye if wali conction lagayi hai. Isiliya yahan se -∞,9 return hua.
        myAns[0] = Math.max(lp[0], rp[0]);
        if (root.left != null && root.right != null) {
            myAns[0] = Math.max(myAns[0], lp[1] + rp[1] + root.val);
        }

        myAns[1] = Math.max(lp[1], rp[1]) + root.val; // Mere se kisi bhi leaf ka max kya hoga?? Jo bhi left aur right
                                                      // se aaya, unka max nikala aut maine apni value mai add kar diya.

        return myAns;

    }

    // ? Method 2 : Using the static variable

    // # Yahan pe faith ke words change ho jate hain. yahan pe maine bola, jo maera
    // # static variable hai, wo left ka LeafToLeafMaxPathSum aur right ka
    // # LeafToLeafMaxPathSum ka max apne mai store kar leta hai. Bas mujhe yahan pe
    // # use apne se pass hota hua leaf to leaf path se compare karna hai bas

    // Humne yahan pe basically nodeToleaf return kara rahe hain bas

    static int LeafToLeafMaxPathSum = -(int) 1e9;

    public static int maxPathSum_02(TreeNode root) {
        if (root == null) {
            return -(int) 1e9;
        }

        if (root.left == null && root.right == null) {
            return root.val;
        }

        int lnl = maxPathSum_02(root.left); // left node to leaf
        int rnl = maxPathSum_02(root.right);// right node to leaf

        if (root.left != null && root.right != null) {
            LeafToLeafMaxPathSum = Math.max(LeafToLeafMaxPathSum, lnl + root.val + rnl);
        }

        return Math.max(lnl, rnl) + root.val;
    }

    public static int maxPathSum_gfg(TreeNode root) {
        LeafToLeafMaxPathSum = -(int) 1e9;

        // ! Below is a good way to tackle the situation
        if (root.left == null || root.right == null) { // This done to pass in gfg. They have considered if the root has
                                                       // only single child, then they are considering it a leaf node.
                                                       // So therefore attached a node with 0 value.

            // For test case like 5 N 6 -5 5, their ans was 16
            if (root.left == null) {
                root.left = new TreeNode(0);
            } else {
                root.right = new TreeNode(0);
            }
        }

        int ans = maxPathSum_02(root);
        // Also for a skew tree, there test case was giving an ans of root to leaf path,
        // therefore added the below condition to pass.
        return LeafToLeafMaxPathSum == -(int) 1e9 ? ans : LeafToLeafMaxPathSum;

    }

    // b <==============Binary Tree Maximum Path Sum=======================>
    // https://leetcode.com/problems/binary-tree-maximum-path-sum/

    // ? Basic faith ye hai ki left se maine {NodeToNodeMaxPathSum,
    // ? RootToNodeMaxPathSum} mangaya aur right se maine {NodeToNodeMaxPathSum,
    // ? RootToNodeMaxPathSum} mangaya. Ab maine sare NodeToNodeMaxPath jo bhi ho
    // ? sakte the unhe compare kiya. Aur max nikalke returnkar diya

    // # Yahan pe humpe bahaut sare cases hain compare karne ke liye

    // 1. Mera NodeToNodeMaxPath mujhe left subtree mai mile
    // 2. Mera NodeToNodeMaxPath mujhe right subtree mai mile
    // 3. Ya mai he NodeToNodeMaxPath hun (sirf root.val)
    // 4. Root se nikalta hua left mai end hua NodeToNodeMaxPath
    // 5. Root se nikalta hua right mai end hua NodeToNodeMaxPath
    // 6. Left se nikalke root ko milake right mai end hone wala NodeToNodeMaxPath

    // To basically humne do size ka array rakha taki hum {NodeToNodeMaxPathSum,
    // RootToNodeMaxPathSum} sath sath nikal sakein

    // RootToNodeMaxPathSum isiliye chahiye kyunki iske bina 4th, 5th and 6th point
    // ki value nikal he ni pate

    // Agar hum static rakhte, to bas RootToNodeMaxPathSum he return karwate, aur
    // kuch nhi

    public static int max(int... arr) {

        int maximum = arr[0];
        for (int e : arr) {
            maximum = Math.max(e, maximum);
        }
        return maximum;
    }

    // {NodeToNodeMaxPathSum, RootToNodeMaxPathSum}
    public int[] maxPathSum_NodeToNode(TreeNode root) {

        if (root == null) {
            return new int[] { -(int) 1e9, 0 };
            // Yahan pe initially 0 isiliye return karwaya kyunki agar -(int) 1e9 rakhte to
            // jab hum add karte isme root.val, to ek additional check add karna hota ki
            // agar value -(int) 1e9 hai to 0 add karo warna value add karo
        }

        int[] lp = maxPathSum_NodeToNode(root.left);
        int[] rp = maxPathSum_NodeToNode(root.right);

        int[] myAns = new int[2];
        int rootToNode = Math.max(lp[1], rp[1]) + root.val; // 4th and 5th point

        myAns[0] = max(lp[0], rp[0], rootToNode, lp[1] + rp[1] + root.val, root.val);
        myAns[1] = Math.max(root.val, rootToNode);
        return myAns;
    }

    public int maxPathSum_NodeToNode_(TreeNode root) {
        return maxPathSum_NodeToNode(root)[0];
    }

    // b Method 2 : Using the static variable

    // Yahan pe thoda sa faith change ho jayega. Maine yayah mana hai ki 1st aur 2nd
    // point ka max static pehle se he apne mai store karke rakhega. Baki maine same
    // left se RootToNodeMaxPathSum mangaya aur right se bhi RootToNodeMaxPathSum
    // mangaya. Aur sari values ko compare kakre static mai store kardiya.

    static int NodeToNodeMaxPathSum = -(int) 1e9;

    public static int maxPathSum_(TreeNode root) {
        if (root == null)
            return 0; // Yahan pe initially 0 isiliye return karwaya kyunki agar -(int) 1e9 rakhte to
                      // jab hum add karte isme root.val, to ek additional check add karna hota ki
                      // agar value -(int) 1e9 hai to 0 add karo warna value add karo

        int lrtn = maxPathSum_(root.left); // left root To Node
        int rrtn = maxPathSum_(root.right); // right root To Node

        int rootToNode = Math.max(lrtn, rrtn) + root.val;
        NodeToNodeMaxPathSum = max(NodeToNodeMaxPathSum, rootToNode, root.val, lrtn + root.val + rrtn);

        return max(rootToNode, root.val);
    }

    public static int maxPathSum_static(TreeNode root) {
        maxPathSum_(root);
        return NodeToNodeMaxPathSum;
    }

    // b <================= Recover BST ============================>
    // https://leetcode.com/problems/recover-binary-search-tree/submissions/

    // ? Simple thing to try when we have a BSt question is to think it of a sorted
    // ? array.

    // Ab do sorted array ki value swap hui hain. Ab array ko dubara sort karna hai.

    // Iske liye bas jahan pe swap hua hai, wahan ke index nikal lo aur unhe dubara
    // swap kardo.

    // Similarly BST mai bhi aise he kiya. Dono swap nodes pata lagaye aur unke data
    // ko swap kar diya.

    // Same prev ka pointer rakha aur a aur b , swap index ko store karne ke liye

    // Dry run to test case :
    // 1. Adjacent index swap (a heartbeat graph)
    // 2. any two index swap (a distant heartbeat graph)

    // Sorted array has a straight line graph

    public static TreeNode rightMost(TreeNode left, TreeNode curr) {

        while (left.right != null && curr != left.right) {
            left = left.right;
        }
        return left;
    }

    public void recoverTree(TreeNode root) {
        TreeNode curr = root;
        TreeNode prev = null, a = null, b = null;
        while (curr != null) {

            TreeNode left = curr.left;
            if (left == null) {
                // print
                if (prev != null && prev.val > curr.val) {
                    if (a == null)
                        a = prev;
                    b = curr;
                }
                prev = curr;
                curr = curr.right;
            } else {

                TreeNode currKeLeftKaRightMost = rightMost(left, curr);

                if (currKeLeftKaRightMost.right == null) {
                    currKeLeftKaRightMost.right = curr; // thread creation block
                    curr = curr.left;
                } else {
                    currKeLeftKaRightMost.right = null;
                    // print
                    if (prev != null && prev.val > curr.val) {
                        if (a == null)
                            a = prev;
                        b = curr;
                    }
                    prev = curr;
                    curr = curr.right;
                }
            }
        }

        if (a != null) {
            int temp = a.val;
            a.val = b.val;
            b.val = temp;
        }
    }

    // b <================== Maximum Width of Binary Tree ==================>
    // https://leetcode.com/problems/maximum-width-of-binary-tree/

    // So Basically the logic is simple. Humne kya kiya ki humne ek class banayi aur
    // `us class mai node ke sath uska index store kiya. Now this index is basically
    // the index if the the tree is seen as an array. Like in heap.

    // So if pi denotes the parent index then left child will be at index pi*2 + 1
    // and the right child will be at index pi * 2 + 2.

    // And for to calculate max width, what we did is stored the leftmost and the
    // rightmost index of a level and calculated the difference between them. And to
    // calcualate the max, we compared the difference at every level.

    // Do the basic dry run, you will figure out after that.

    public static class Pair {
        TreeNode node = null;
        int index = 0;

        Pair(TreeNode node, int index) {
            this.node = node;
            this.index = index;
        }
    }

    public int widthOfBinaryTree(TreeNode root) {

        LinkedList<Pair> que = new LinkedList<>();
        que.addLast(new Pair(root, 0));

        int max = 0;
        while (que.size() != 0) {
            int size = que.size();
            int leftMostIndex = que.getFirst().index;
            int rightMostIndex = que.getFirst().index;
            while (size-- > 0) {

                Pair rn = que.removeFirst();
                TreeNode node = rn.node;
                int pi = rn.index;
                if (node.left != null) {
                    que.addLast(new Pair(node.left, pi * 2 + 1));
                }
                if (node.right != null) {
                    que.addLast(new Pair(node.right, pi * 2 + 2));
                }
                rightMostIndex = pi;
            }
            max = Math.max(max, rightMostIndex - leftMostIndex + 1);
        }
        return max;
    }

    // b <================Path Sum III (Not solved yet..) =====================>
    // https://leetcode.com/problems/path-sum-iii/
    static int count = 0;

    public void pathSum(TreeNode root, int targetSum) {

        if (root == null || targetSum < 0) {
            return;
        }

        if (targetSum == 0) {
            count++;
        }

        pathSum(root.left, targetSum - root.val);
        pathSum(root.right, targetSum - root.val);
        pathSum(root.left, targetSum);
        pathSum(root.right, targetSum);
    }

}