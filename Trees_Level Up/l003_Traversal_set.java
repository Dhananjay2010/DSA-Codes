import java.util.ArrayList;
import java.util.LinkedList;

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

    public static boolean isBST_valid_static(TreeNode root) {
        if (root == null) {
            return true;
        }

        boolean left = isBST_valid_static(root.left);
        if (!left) {
            return false;
        }

        if (prev != null && prev.val >= root.val) { // equal to for test case that every node has same value. Then it
                                                    // should not be a BST
            return false;
        }

        prev = root;

        boolean right = isBST_valid_static(root.right);
        if (!right) {
            return false;
        }

        return true;
    }

    // Is valid BST by morris inOrderTraversal
    // Solved using the same property that inorder of BST is sorted.
    // Prev ko curr se compare karna hai taki agar koi bhi agar prev se chota mile,
    // to false return kar denge. Same uper ki tarah.

    public boolean isValidBST_Morris(TreeNode root) {
        return morrisInOrderTraversal(root);
    }

    public static TreeNode validBST_Morris_Rightmost(TreeNode left, TreeNode curr) {
        while (left.right != null && curr != left.right) {
            left = left.right;
        }

        return left;
    }

    public boolean morrisInOrderTraversal(TreeNode root) {
        TreeNode prev = null;
        TreeNode curr = root;
        while (curr != null) {

            if (prev != null && prev.val >= curr.val) {
                return false;
            }
            TreeNode left = curr.left;

            if (left == null) {
                // ans.add(curr.val);
                prev = curr;
                curr = curr.right;
            } else {
                TreeNode currKeLeftKaRightMost = validBST_Morris_Rightmost(left, curr);
                if (currKeLeftKaRightMost.right == null) {
                    currKeLeftKaRightMost.right = curr; // thread creation
                    curr = curr.left;
                } else {
                    currKeLeftKaRightMost.right = null;
                    // ans.add(curr.val);
                    prev = curr;
                    curr = curr.right;
                }
            }
        }

        return true;
    }

    // Another iterative method for inorder traversal
    // Sabse pehle root ko dala aur root ke left ke sare nodes ko dala
    // ab hum iske baad sidhe left mai pahunch gaye
    // Humne element ko remove kiya aur agar uske right hai to right ko dala aur
    // right ke sare left ko dal diya
    // Aur aisa hum tab tak karte rahenge jab tak stack khali nhi ho jata

    // Basically yahan pe hum recursive stack ko emulate kar rahe hain, so iterative
    // hai to ye thoda fast hoga per best solution is morris traversal

    // It is used when the interviewer says that the modification to tree is not
    // allowed. Hence cannot use morris traversal

    public static void addNodeAndItsAllLeft(TreeNode node, LinkedList<TreeNode> st) {
        while (node != null) {
            st.addFirst(node);
            node = node.left;
        }
    }

    public static boolean Tree_Inorder_stack_method(TreeNode root) {
        LinkedList<TreeNode> st = new LinkedList<>();
        addNodeAndItsAllLeft(root, st);
        TreeNode prev = null;
        while (st.size() != 0) {
            TreeNode node = st.removeFirst();
            if (prev != null && prev.val >= node.val) {
                return false;
            }
            prev = node;
            // System.out.println(node);

            addNodeAndItsAllLeft(node.right, st);

        }

        return true;
    }

    // Binary Search Iterator
    // https://leetcode.com/problems/binary-search-tree-iterator/
    // Same logic of stack is used as used for inorder traversal

    class BSTIterator {
        // always use private variabe for those which cannot be accessed by outside
        // world and are can only be accessed by its own class.
        private LinkedList<TreeNode> st = new LinkedList<>();

        private void addNodeAndItsAllLeft(TreeNode node, LinkedList<TreeNode> st) {
            while (node != null) {
                st.addFirst(node);
                node = node.left;
            }
        }

        public BSTIterator(TreeNode root) {
            addNodeAndItsAllLeft(root, st);
        }

        public int next() {
            TreeNode node = st.removeFirst();
            addNodeAndItsAllLeft(node.right, st);
            return node.val;
        }

        public boolean hasNext() {
            return st.size() != 0;
        }
    }

    // Solving BST iterator using Morris Traversal
    // Try to dry run code for tree with only one node

    // Logic is jahan jahan pe answer mmil raha hai wahan pe break laga do taki bas
    // ek bar answer mile
    // Bas itna he karna tha

    class BSTIterator_Morris_Traversal {

        private TreeNode curr = null;

        private TreeNode rightMost(TreeNode left, TreeNode curr) {
            while (left.right != null && left.right != curr) {
                left = left.right;
            }

            return left;
        }

        private int Morris_Order_Traversal() {
            // Rememmber multiple return statement is not good practice. Try to have just
            // one return statement in a function. Therefore a variable rv is used and
            // returned at last.

            int rv = (int) 1e9;
            while (curr != null) {
                TreeNode left = curr.left;
                if (left == null) {
                    // ans.add(curr);
                    rv = curr.val;
                    curr = curr.right;
                    break;
                } else {
                    TreeNode currKeLeftKaRightMostElement = rightMost(left, curr);

                    if (currKeLeftKaRightMostElement.right == null) {
                        currKeLeftKaRightMostElement.right = curr; // thread creation
                        curr = curr.left;
                    } else {
                        currKeLeftKaRightMostElement.right = null; // thread break
                        // ans.add(curr);
                        rv = curr.val;
                        curr = curr.right;
                        break;
                    }
                }
            }
            return rv;
        }

        public BSTIterator_Morris_Traversal(TreeNode root) {
            this.curr = root;
        }

        public int next() {
            return Morris_Order_Traversal();
        }

        public boolean hasNext() {
            return curr != null;
        }
    }

    // https://leetcode.com/problems/kth-smallest-element-in-a-bst
    // Logic is same. Inorder of BST is sorted.

    // Simply solved by using morris traversal
    // If told to not do modification in tree, use stack method for inorder
    public static int kthSmallest(TreeNode root, int k) {
        int kSmallest = -(int) 1e9;
        TreeNode curr = root;
        while (k > 0) {
            TreeNode left = curr.left;
            if (left == null) {
                // print
                kSmallest = curr.val;
                k--;
                curr = curr.right;
            } else {
                TreeNode currKeLeftKaRightMost = rightMostNode(left, curr);

                if (currKeLeftKaRightMost.right == null) {
                    currKeLeftKaRightMost.right = curr; // thread creation
                    curr = curr.left;
                } else {
                    currKeLeftKaRightMost.right = null;
                    // print
                    kSmallest = curr.val;
                    k--;
                    curr = curr.right;
                }
            }
        }

        return kSmallest;
    }

    // Kth Largest element in BST
    // https://practice.geeksforgeeks.org/problems/kth-largest-element-in-bst/1/

    public static int size(TreeNode root) {
        return root == null ? 0 : size(root.left) + size(root.right) + 1;
    }

    public static int kthLargest(TreeNode root, int k) {

        int treeSize = size(root);
        int elementFromFirst = treeSize - k + 1;
        int kLargest = (int) 1e9;
        TreeNode curr = root;
        while (elementFromFirst > 0) {
            TreeNode left = curr.left;
            if (left == null) {
                // print
                kLargest = curr.val;
                elementFromFirst--;
                curr = curr.right;
            } else {
                TreeNode currKeLeftKaRightMost = rightMostNode(left, curr);

                if (currKeLeftKaRightMost.right == null) {
                    currKeLeftKaRightMost.right = curr; // thread creation
                    curr = curr.left;
                } else {
                    currKeLeftKaRightMost.right = null;
                    // print
                    kLargest = curr.val;
                    elementFromFirst--;
                    curr = curr.right;
                }
            }
        }

        return kLargest;
    }

    // https://leetcode.ca/2017-01-29-426-Convert-Binary-Search-Tree-to-Sorted-Doubly-Linked-List/
    // https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/trees/convert-binary-search-tree-to-doubly-linked-list/ojquestion

    // Convert a BST to a sorted Doubly Linked List
    // What we did was create a dummy pointer and used it
    // Now dummy pointer is firstly attched to the first inorder node
    // after that pointers are attached and the prev is moved to the next pointer
    // and curr is also changed since we always remove one element from the stack

    // Hence therefore the Doubly linked list is made

    // Method 1 : stack

    public static void insertAllLeft(TreeNode node, LinkedList<TreeNode> st) {
        while (node != null) {
            st.addFirst(node);
            node = node.left;
        }
    }

    public static TreeNode BST_To_DLL(TreeNode root) {
        LinkedList<TreeNode> st = new LinkedList<>();
        insertAllLeft(root, st);
        TreeNode prev = new TreeNode(-1), dp = prev;
        while (st.size() > 0) {
            TreeNode rv = st.removeFirst();
            prev.right = rv;
            rv.left = prev;
            prev = prev.right;
            insertAllLeft(rv.right, st);
        }

        // This extra is done to convert it into circular linkedlist
        prev.right = dp.right;
        dp.right.left = prev;
        TreeNode nHead = dp.right;
        dp.right = null;
        return nHead;
    }

    // Method 2 : Morris Traversal

    public static TreeNode right_most_node(TreeNode left, TreeNode curr) {
        while (left.right != null && curr != left.right) {
            left = left.right;
        }

        return left;
    }

    public static TreeNode BST_To_DLL_Morris_Traversal(TreeNode root) {

        TreeNode curr = root;
        TreeNode prev = new TreeNode(-1), dp = prev;

        while (curr != null) {
            TreeNode left = curr.left;

            if (left == null) {
                // Print
                prev.right = curr;
                curr.left = prev;
                prev = prev.right;
                curr = curr.right;
            } else {
                TreeNode currKeLeftKaRightMost = right_most_node(left, curr);

                if (currKeLeftKaRightMost.right == null) {
                    currKeLeftKaRightMost.right = curr; // thread creation
                    curr = curr.left;
                } else {
                    currKeLeftKaRightMost.right = null; // thread break
                    // print
                    prev.right = curr;
                    curr.left = prev;
                    prev = prev.right;
                    curr = curr.right;
                }
            }
        }

        prev.right = dp.right;
        dp.right.left = prev;
        TreeNode nHead = dp.right;
        dp.right = null;
        return nHead;
    }

    // Methhod : 3
    // Purely recursive method using static variable

    public static TreeNode bToDLL(TreeNode root) {
        BST_To_DLL(root);
        prev.right = dp.right;
        dp.right.left = prev;
        TreeNode nHead = dp.right;
        dp.right = null;
        return nHead;
    }

    static TreeNode prev = new TreeNode(-1);
    static TreeNode dp = prev;

    public static void BST_To_DLL_recursive(TreeNode root) {

        if (root == null) {
            return;
        }

        BST_To_DLL_recursive(root.left);

        prev.right = root;
        root.left = prev;
        prev = prev.right;

        BST_To_DLL_recursive(root.right);
    }
}
