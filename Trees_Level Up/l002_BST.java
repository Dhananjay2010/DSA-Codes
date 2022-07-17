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

    // ! Anytime you want to do insertion and deletion in BST, always use recursion.
    // ! Never go for iterative method.

    // b <================Insert a Node in BST====================>

    // `Simple recursion use kiya aur jahan wo element probably mil sakta tha, wahan
    // jake use place kar diya.

    // ? Issse humara nya node leaf pe he add hoga. Kahin beech mai add nhi hoga.

    public TreeNode insertIntoBST(TreeNode root, int val) {

        if (root == null) {
            return new TreeNode(val);
        }
        if (val < root.val) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }

        return root;
    }

    // b <=======================Delete a Node in BST================>

    // Here there can be 4 conditions:
    // 1. If the node we want to delete has 2 childs.
    // 2. If the node we want to delete has only left child.
    // 3. If the node we want to delete has only right child.
    // 4. If the node we want to delete has no child.

    // ? The most important is to figure out the 1st condition.
    // ` Here we calculated the left most of root.right to get the min value and
    // then replaced that value with root. Then we gave root.right and the min value
    // to get itself deleted.

    // Why left most of root.right ?
    // Since leftmost will give us the minumum value and replacing it with root.val
    // can make our Tree remain BST. But we also therefore have to remove the
    // duplicate node at the end. So therefore, ran delete node again.

    // ? For Test case : 8,3,10,1,6,null,14,null,null,4,7,13,null (Level order of a
    // ? test case to get a tree)

    // Now we are given to delete the node 8
    // So it will pass the first two if statement and then arrive at the last block
    // of if where we have to delete a parent having both child.

    // So now, what it will do is calculate the minValue of its just right element
    // that is 10.

    // Now we will replace 8 with 10.

    // Now having replaced 8 with 10, we have duplicate 10. Therefore we again get a
    // call of delete node, this time to delete the actual 10 value and then attach
    // `it to right side of our root..

    // Hence 14 and 13 joins to the right part of the node.

    // Now imagine if 10 has a left child 9, so we would have replaced 8 with 9 and
    // given the 10 as root to get the 9 duplicate node deleted.

    // ? Thinking it in one more way. We know that BST inorder is sorted.
    // So like a sorted array : 1,3,4,6,7,8,10,13,14

    // Now if we want to remove 8 in array, there are two potential candidate that
    // can replace 8.
    // ? 7 and 10.

    // Therefore it does not matter ki hum 8 ke left ka rightmost nikalein ya 8 ke
    // right ka leftmost.

    // So both the answers can be true.

    // The following code would have been for if we choose 7 to replace 8.

    // TreeNode rightKaLeftMost = rightMost(root.left);
    // root.val = rightKaLeftMost.val;
    // root.left = deleteNode(root.left, root.val);

    public static TreeNode leftMost(TreeNode root) {
        if (root == null)
            return null;

        while (root.left != null) {
            root = root.left;
        }

        return root;
    }

    public TreeNode deleteNode(TreeNode root, int key) {

        if (root == null) {
            return null;
        }

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else if (root.val == key) {

            if (root.left == null && root.right == null) { // ? To handle left nodes(No child)
                return null;
            } else if (root.left == null || root.right == null) {// ? To handle single parent(only one child)

                TreeNode rnNode = root.right == null ? root.left : root.right;
                root.right = root.left = null; // Just to make the node not pointing to any other node. Not to do
                                               // necessary, but is a good habit to remove any pointers
                return rnNode;
            } else {

                TreeNode rightKaLeftMost = leftMost(root.right);
                root.val = rightKaLeftMost.val;
                root.right = deleteNode(root.right, root.val); // ! Important is ki root ke right mai connect kiya jo
                                                               // ! bhi aayega use

                return root;
            }

        }

        return root;
    }

    // b <===================Inorder Successor in BST II ===============>
    // https://wentao-shao.gitbook.io/leetcode/binary-tree/510.inorder-successor-in-bst-ii

    // * We have been given node, not the root. And we have to find the inorder
    // * successor of the node.

    // * The given node has val, left,right,parent.

    // Agar node ka right exist karta hai to node ke right ka leftmost is the
    // successor
    // Agar nhi karta to hum parent pe traverse karenge and jo sabse pehle bada
    // parent mila, wo humara successor

    // ` In the below method we have used val.

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }

    public static Node leftMost(Node root) {
        if (root == null)
            return null;

        while (root.left != null) {
            root = root.left;
        }

        return root;
    }

    public static Node justGreaterParent(Node node, int value) {
        if (node == null)
            return null;

        Node par = null;
        while (node.parent != null) {
            node = node.parent;
            if (node.val > value) { // just pehla parent jo node ki value se bada hoga, wo humara successor hoga
                par = node;
                break;
            }
        }

        return par;
    }

    public static Node inorder_Successor(Node node) {

        if (node.right != null) {
            return leftMost(node.right);
        } else {
            return justGreaterParent(node, node.val);
        }
    }

    // ! Now the follow question is that we have to not use val and find the inorder
    // ! successor in the same above question.

    // ? The thing that we should consider is that ki agar mai ek node hun aur agar
    // ? find node mere right mai exist karta hai to mere successor hone ka chance
    // ? to gya kyunki mai humesha usse chota hunga

    // ? Aur agar mere left mai exist karta hai to mai successor ho sakta hun kyunko
    // ? mai usse bada hun.

    // Bas yahi condition ka niche use kiya hai

    public static Node inorder_Successor_withoutVal(Node node) {

        if (node.right != null) {
            return leftMost(node.right);
        } else {
            while (node.parent != null && node == node.parent.right) {
                // Jaise he node ke parent ka right not equal hua node ke, to parent ne left ki
                // call lagayi hogi node tak pahunchne ke liye isiliye node.parent humara
                // successor hoga
                node = node.parent;
            }
            return node.parent;

        }
    }


    // ?  Home work : insert node and delete node -> T : O(LogN), S : O(1) (Iterative Solution)

}
