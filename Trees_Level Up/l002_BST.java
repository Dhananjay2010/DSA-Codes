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

    // b <================Insert a Node in BST(Recursive)====================>

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

    // b <=======================Delete a Node in BST(Recursive)================>

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

    // b <================ Insert a Node in BST(Iterative Method) ================>

    // Same logic as recursive
    // Humesha node ko leaf node ki tarah he insert karna hai

    public TreeNode insertIntoBST_iterative(TreeNode root, int val) {

        if (root == null)
            return new TreeNode(val); // ? Agar root empty haoi to single node return karna hai

        TreeNode curr = root;

        while (curr != null) {
            if (val > curr.val) {
                if (curr.right == null) {
                    curr.right = new TreeNode(val);
                    break;
                }
                curr = curr.right;
            } else {
                if (curr.left == null) {
                    curr.left = new TreeNode(val);
                    break;
                }
                curr = curr.left;
            }
        }
        return root;
    }

    // b <================ Delete a Node in BST(Iterative Method) ================>

    // ` Here the logic is same as recurision one. Just have maintained a parent to
    // know wherer to attach the rest of the node of the tree.

    public static TreeNode leftMost_iterative(TreeNode root) {
        if (root == null)
            return null;

        while (root.left != null) {
            root = root.left;
        }

        return root;
    }

    public TreeNode deleteNode_iterative(TreeNode root, int key) {

        if (root == null) {
            return null;
        }

        TreeNode curr = root;
        TreeNode parent = null;

        while (curr != null) {
            if (key > curr.val) {
                parent = curr;
                curr = curr.right;
            } else if (key < curr.val) {
                parent = curr;
                curr = curr.left;
            } else if (curr.val == key) {
                TreeNode nodeToAttach = null;
                int currentValue = curr.val;
                if (curr.left == null || curr.right == null) { // Handling both leaf nodes as well as single parent
                                                               // condition
                    nodeToAttach = curr.left == null ? curr.right : curr.left;
                    curr.left = curr.right = null;
                    if (parent == null) { // Agar manlo koi single node ka tree hai aur usi node ko remove karne ko bola
                                          // to uska to parent koi hoga he nhi. ex =[8] and 8 is told to delete

                        // Same goes for case like [8,7,null] and 8 is told to delete so no parent
                        // Same goes for case like [8,null,7] and 8 is told to delete
                        return nodeToAttach;
                    }
                    if (parent.val < currentValue) {
                        parent.right = nodeToAttach;
                    } else {
                        parent.left = nodeToAttach;
                    }
                } else {

                    TreeNode currKeRightKaLeftMost = leftMost_iterative(curr.right);
                    curr.val = currKeRightKaLeftMost.val;
                    curr.right = deleteNode_iterative(curr.right, curr.val);
                }

                break;

            }
        }

        return root;
    }

    // b <=======================Balance a Binary Tree========================>
    // https://leetcode.com/problems/balance-a-binary-search-tree/
    // Leetcode 1382

    // ? Remember that we can convert BST to a sorted array and then convert it into
    // ? balanced BST. Because when converting a sorted array into BST, we find the
    // ? middle and then split it into two halfs. Therefore the BST formed is
    // ? balanced. This will be of O(n) complexity.

    // # But here we would have taken the extra space of array.

    // So we can also convert the BST into sorted doubly linked list, inplace and
    // then convert the sorted DLL to BST

    // # But here it can also be solved by using AVL.

    // ! But the question is HOW ???

    // So that we can calculate the height in O(1), we will be keeping an array of
    // size of the range of nodes value since node value will be distinct.

    // Otherwise we will have to calculate the height everytime and that will an
    // operation of logN itself. Calculating height everytime will get us the space
    // complexity of O(1)

    // We are going to do as we did in AVL.

    // We are going to rotate the tree such that our tree remains balanced.

    // So we have just called the simple post order call.

    // ? Why post order ???

    // # It is because to calculate the balance factor we will be needing the height
    // # of both left and right subtree.

    // And whenever we are returing the root, we Rotate it using the getRotation
    // function to get the balance BST.

    // ! Important Note : To solve this using the AVL, please dry on the skew BST
    // ! tree and draw stack diagram to get more idea

    public static void updateHeight(TreeNode root, int[] height) {

        int lh = root.left != null ? height[root.left.val] : -1;
        int rh = root.right != null ? height[root.right.val] : -1;

        height[root.val] = Math.max(lh, rh) + 1;
    }

    public static int getBalance(TreeNode root, int[] height) {

        int lh = root.left != null ? height[root.left.val] : -1;
        int rh = root.right != null ? height[root.right.val] : -1;

        int bal = lh - rh;

        return bal;
    }

    public static TreeNode leftRotation(TreeNode A, int[] height) {

        TreeNode B = A.right;
        TreeNode BKaLeft = B.left;

        B.left = A;
        A.right = BKaLeft;

        // Now we have called the getRotation function again because
        // Aisa ho sakta hai ki rotation ke baad bhi mera tree unbalanced ho sakta hai
        // to use dubara balance karne ke liye getRotation call kiya

        B.left = getRotation(A, height);
        return getRotation(B, height);
    }

    public static TreeNode rightRotation(TreeNode A, int[] height) {

        TreeNode B = A.left;
        TreeNode BKaRight = B.right;

        B.right = A;
        A.left = BKaRight;

        // Now we have called the getRotation function again because
        // Aisa ho sakta hai ki rotation ke baad bhi mera tree unbalanced ho sakta hai
        // to use dubara balance karne ke liye getRotation call kiya

        B.right = getRotation(A, height);
        return getRotation(B, height);

    }

    public static TreeNode getRotation(TreeNode root, int[] height) {
        // Now since here we are converting an already BSt to a balanced BST, so the
        // ` balance factor can be greater than 2 and can be less that -2. Other wise
        // the call remains the same as in AVL tree

        updateHeight(root, height);
        if (getBalance(root, height) >= 2) {
            if (getBalance(root.left, height) >= 1) {
                return rightRotation(root, height);
            } else {
                root.left = leftRotation(root.left, height);
                return rightRotation(root, height);
            }
        } else if (getBalance(root, height) <= -2) {
            if (getBalance(root.right, height) <= -1) {
                return leftRotation(root, height);
            } else {
                root.right = rightRotation(root.right, height);
                return leftRotation(root, height);
            }
        }

        return root;
    }

    public TreeNode balanceBST(TreeNode root, int[] height) {

        if (root == null) {
            return null;
        }

        root.left = balanceBST(root.left, height);
        root.right = balanceBST(root.right, height);

        return getRotation(root, height);
    }

    public TreeNode balanceBST(TreeNode root) {

        int[] height = new int[(int) 1e5 + 1];
        return balanceBST(root, height);
    }

}
