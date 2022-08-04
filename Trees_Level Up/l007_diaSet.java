import java.util.List;
import java.util.ArrayList

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