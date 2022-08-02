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

    int diameter = 0;

    public int diameterOfBinaryTree_03(TreeNode root) {
        if (root == null)
            return -1;

        int ld = diameterOfBinaryTree_03(root.left);
        int rd = diameterOfBinaryTree_03(root.right);

        diameter = Math.max(diameter, ld + rd + 2);
        return Math.max(ld, rd) + 1;
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
    // # Good Question(Pehli bar mai nhi hua)
    // https://www.geeksforgeeks.org/find-maximum-path-sum-two-leaves-binary-tree/

    // ? Merepe basically teen chezein hain compare karne ke liye.
    // Aisa ho sakta hai ki mera max between two leaves left ke subtree ke 2 leaf ke
    // `beech mai ho

    // Ya fir aisa ho sakta hai ki mera max between two leaves right ke subtree ke 2
    // leaf ke beech mai ho

    // Ya fir aisa bhi ho sakta hai ki mera max between two leaves mujhe include
    // karke mera left ke subtree ke k leaf se right ke subtree ke dusre leaf tak
    // ho sakta hai

    // {LeafToLeafMaxPathSum, NodeToLeadMaxPathSum}
    public static int[] maxPathSum(TreeNode root) {

        int[] lp = maxPathSum(root.left);
        int[] rp = maxPathSum(root.right);

        int[] myAns = new int[2];
        
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