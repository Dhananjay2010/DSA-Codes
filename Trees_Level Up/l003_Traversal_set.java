import java.util.ArrayList;

public class l003_Traversal_set {

    public static class TreeNode {
        int val = 0;
        TreeNode right = null;
        TreeNode left = null;

        TreeNode(int val) {
            this.val = val;

        }
    }

    // Moris order Traversal

    // Ye inorder O(n) time complexity aur O(1) space mai karke de deta hai
    // Normal dfs O(n) time complexity aur O(h) h== > height of tree ki space leta
    // hai

    // Dry run and then code
    // Will be used in many questions

    // So first thing is to create a requirement
    // Create a thread : because since we are iterating so we need to find a way to
    // get back to previous node. This is the reason we create a thread.
    // Destroying a thread : Since we have add addittional detail to tree, we need
    // to destroy the thread to make it like before.

    // Three condition to be checked :--
    // 1. If curr ka left == null, if not then
    // 2. if currkeleftka right most node agar null ke equal hai to thread banao;
    // 3. if not equal to thread destroy karo

    // The sequence will be same as dfs.
    // dfs(ldft)
    // syso(ans)
    // dfs(right)

    // This is the inorder
    // complexity of this is O(4n) to O(6n) since each node is visited approximately
    // 4 to 6 times.
    // so on average we say O(5n)
    public static TreeNode rightMostNode(TreeNode left, TreeNode curr) {
        // curr!=left.right = > agar left ka rightmost dhundthe dhundte agar curr pe
        // wapas aa gaye to matlab thread tha pehle se
        // This is a important condition
        while (left.right != null && curr != left.right) {
            left = left.right;
        }
        return left;
    }

    public static void morrisInOrderTraversal(TreeNode root, ArrayList<Integer> ans) {

        TreeNode curr = root;
        while (curr != null) {
            TreeNode left = curr.left;
            if (left == null) {
                ans.add(curr.val);
                curr = curr.right;
            } else {
                TreeNode currKeLeftKaRightMostNode = rightMostNode(left, curr);
                if (currKeLeftKaRightMostNode.right == null) { // thread creation block
                    currKeLeftKaRightMostNode.right = curr; // thread creation
                    curr = curr.left;
                } else { // thread destroy block
                    currKeLeftKaRightMostNode.right = null; // thread break
                    ans.add(curr.val);
                    curr = curr.right;
                }
            }
        }
    }

    // preOrderTraversal
    public static void morrisPreOrderTraversal(TreeNode root, ArrayList<Integer> ans) {

        TreeNode curr = root;
        while (curr != null) {
            TreeNode left = curr.left;
            if (left == null) {
                ans.add(curr.val);
                curr = curr.right;
            } else {
                TreeNode currKeLeftKaRightMostNode = rightMostNode(left, curr);
                if (currKeLeftKaRightMostNode.right == null) { // thread creation block
                    currKeLeftKaRightMostNode.right = curr; // thread creation
                    ans.add(curr.val); // Extra line added from inorder
                    curr = curr.left;
                } else { // thread destroy block
                    currKeLeftKaRightMostNode.right = null; // thread break
                    curr = curr.right;
                }
            }
        }
    }

    // https://leetcode.com/problems/validate-binary-search-tree/
    // Validate Binarh Search Tree

    public boolean isValidBST(TreeNode root) {
        return isValidBST_(root).isBST;
    }

    public static class BST_Pair {
        boolean isBST = true;
        long min = (long) 1e14;
        long max = -(long) 1e14;
    }

    public BST_Pair isValidBST_(TreeNode root) {

        if (root == null) {
            return new BST_Pair();
        }

        BST_Pair left = isValidBST_(root.left);
        BST_Pair right = isValidBST_(root.right);

        if (left.isBST == false) {
            return left;
        }

        if (right.isBST == false) {
            return right;
        }

        BST_Pair ans = new BST_Pair();
        ans.isBST = false;

        if (left.isBST && right.isBST && left.max < root.val && right.min > root.val) {
            ans.isBST = true;
            ans.min = Math.min(root.val, left.min);
            ans.max = Math.max(root.val, right.max);
        }

        return ans;
    }

    // By using static variable
    // Using the property that inorder of BST is sorted

    static TreeNode prev = null;

    public static boolean isBST_static(TreeNode root) {
        if (root == null) {
            return true;
        }

        boolean left = isBST_static(root.left);
        if (!left) {
            return false;
        }

        if (prev != null && prev.val >= root.val) { // equal to for test case that every node has same value. Then it
                                                    // should not be a BST
            return false;
        }

        prev = root;

        boolean right = isBST_static(root.right);
        if (!right) {
            return false;
        }

        return true;
    }

    // Is valid BST by morris inOrderTraversal
    // Solved using the same property that inorder of BST is sorted. 
    // Prev ko curr se compare karna hai taki agar koi bhi agar prev se chota mile, to false return kar denge. Same uper ki tarah.

    
    public boolean isValidBST_Morris(TreeNode root) {
        return morrisInOrderTraversal(root);
    }
    
    public static TreeNode validBST_Morris_Rightmost(TreeNode left, TreeNode curr) {
        while (left.right != null && curr != left.right) {
            left = left.right;
        }

        return left;
    }

    public boolean morrisInOrderTraversal(TreeNode root){
        TreeNode prev=null;
        TreeNode curr=root;
        while(curr!=null){
            
            if(prev!=null && prev.val >= curr.val){
                return false;
            }
            TreeNode left=curr.left;
            
            if(left == null){
                // ans.add(curr.val);
                prev=curr;
                curr=curr.right;
            }else{
                TreeNode currKeLeftKaRightMost= validBST_Morris_Rightmost(left, curr);
                if(currKeLeftKaRightMost.right == null){
                    currKeLeftKaRightMost.right=curr; // thread creation
                    curr=curr.left;
                }else{
                    currKeLeftKaRightMost.right=null;
                    // ans.add(curr.val);
                    prev=curr;
                    curr=curr.right;
                }
            }
        }
        
        return true;
    }

    // Second way of inorder
    // aate he root node ke sare left dal diye
    // agar mera left  == null hai to print kara or apne aap ko remove kiya
    // agar merepe right hai to pehle maine apne app ko remove kiya aur tab apne right ko dalo aur right ke sare left ko dal diya
    // 
}
