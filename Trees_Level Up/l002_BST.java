// Some Properties of BST
// Left SubTree ka sabse bada node mujhe chota hoga
// Right SubTree ka sabse choti value mujhse badi hogi
// BST ka inorder sorted hota hai
// BST ka sabse choti value == > leftmost value
// BST ki sabse badi value == > right most value

// Why BST ?? 
// BST mai insertion log(n) ka hota hai jabki sorted array mai insertion O(n) ka hota hai kyunki array mai shifing hogi
// log(n) isiliye kyunki max tree ki height he traverse karni padegi aur insert karke aa jayega
// BST is derived from binary search.

// BST ke sare question iterative kare ja sakte hain to iterative he karenge.
// YE acha isiliye hai kyunki mai yahan pe recursive stack nhi use kar raha. To faster hoga

import java.util.ArrayList;

public class l002_BST {

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

    public static int maximum(TreeNode root) { // Meri rightmost value max hoti hai pure bst mai
        TreeNode curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }

        return curr.val;
    }

    public static int minimum(TreeNode root) { // Meri leftmost value max hoti hai pure bst mai
        TreeNode curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }

        return curr.val;
    }

    public static boolean findData(TreeNode root, int data) {
        TreeNode curr = root;

        while (curr != null) {
            if (curr.val == data) {
                return true;
            } else if (curr.val >= data) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        return false;
    }

    public static boolean rootToNodePath(TreeNode root, int data, ArrayList<Integer> ans) {
        TreeNode curr = root;

        while (curr != null) {
            ans.add(curr.val);
            if (curr.val == data) {
                return true;
            } else if (curr.val >= data) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        return false;
    }

    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode lca = null;
        TreeNode curr = root;

        while (curr != null) {
            // jahan pe bhi diversion create hua, wo mera lca node hoga
            if (curr.val > q.val && curr.val > p.val) {
                curr = curr.left;
            } else if (curr.val < q.val && curr.val < p.val) {
                curr = curr.right;
            } else {
                lca = curr;
                break;
            }
        }
        return (lca != null && findData(lca, p.val) && findData(lca, q.val)) ? lca : null;
        // Agar bola jata hai ki p and q may be present to ye bhi check karna padega ki
        // p or q hai ki nhi

        // findData mai lca isliye bheja ki jo element hoga to wo lca ke left ya right
        // mai he hoga. root se traverse karne ki jaroorat nhi hai

        // To agar lca != null && findData(lca, p.val) && findData(lca, q.val) ye teeno
        // condition true hongi to jo mujhe lca mila hai wo shi mila hai
    }

}
