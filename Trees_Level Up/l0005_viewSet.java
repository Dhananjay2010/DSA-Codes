import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class l0005_viewSet {

    public static class TreeNode {

        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this(val, null, null); // constructor chaining
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // b <================Level Order Traversal of Binary Tree ==============>

    public List<List<Integer>> levelOrder(TreeNode root) {

        List<List<Integer>> ans = new ArrayList<>();
        LinkedList<TreeNode> que = new LinkedList<>();
        // add last, remove first

        que.addLast(root);

        while (que.size() != 0) {
            int size = que.size();
            ArrayList<Integer> myAns = new ArrayList<>();
            while (size-- > 0) {
                TreeNode rn = que.removeFirst();

                if (rn == null) {
                    continue;
                }

                myAns.add(rn.val);
                que.addLast(rn.left);
                que.addLast(rn.right);
            }
            if (myAns.size() > 0) {
                ans.add(myAns);
            }
        }
        return ans;
    }

    public static void levelOrderLineWise(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);
        int level = 0;

        while (que.size() != 0) {
            int size = que.size();
            System.out.print("Level " + level + ": ");
            while (size-- > 0) {
                TreeNode rn = que.removeFirst();

                System.out.print(rn.val + ", ");
                if (rn.left != null) // Yahan pe check karke dal rahe hain to jaroorat nhi hai continue likhne ki
                    que.addLast(rn.left);
                if (rn.right != null)
                    que.addLast(rn.right);
            }
            level++;
        }
    }

    // b <=====================Left View of Binary Tree ================>
    // https://practice.geeksforgeeks.org/problems/left-view-of-binary-tree/1

    // * Basically the logic is that the in level order traversal, the first element
    // * of every level is the left view of the tree.

    // ? For Test case : 8,3,10,1,6,null,14,null,null,4,7,13,null (Level order of a
    // ? test case to get a tree)

    // So the level order will be :
    // level 0 : 8
    // level 1 : 3,10
    // level 2 : 1,6,14
    // level 3 : 4,7,13

    // So the answer will be [8,3,1,4]

    public static ArrayList<Integer> leftView(TreeNode root) {

        if (root == null)
            return new ArrayList<>();
        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);

        ArrayList<Integer> ans = new ArrayList<>();
        while (que.size() != 0) {
            int size = que.size();
            ans.add(que.getFirst().val);
            while (size-- > 0) {
                TreeNode rn = que.removeFirst();

                if (rn.left != null)
                    que.addLast(rn.left);
                if (rn.right != null)
                    que.addLast(rn.right);
            }

        }

        return ans;
    }

    // * Dfs Method :

    // Left View using normal dfs.
    // just a static variable to maintain the check of the level visited.

    // Other than this, this is the simple dfs preorder call.

    static int maxLevel = -1; // ? can take one size array instead of this

    public static void leftView_dfs(TreeNode root, int level, ArrayList<Integer> ans) {

        if (root == null)
            return;

        if (level > maxLevel) {
            ans.add(root.val);
            maxLevel = level;
        }

        leftView_dfs(root.left, level + 1, ans);
        leftView_dfs(root.right, level + 1, ans);

    }

    ArrayList<Integer> leftView_dfs(TreeNode root) {
        // Your code here
        maxLevel = -1;
        ArrayList<Integer> ans = new ArrayList<>();
        leftView_dfs(root, 0, ans);

        return ans;
    }

    // b <================== Binary Tree Right Side View ===================>
    // https://leetcode.com/problems/binary-tree-right-side-view/

    // Just printing the last element in each level in level order gives us the
    // right side view

    public List<Integer> rightSideView(TreeNode root) {

        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> ans = new ArrayList<>();
        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);

        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                TreeNode rn = que.removeFirst();

                if (size == 0) {
                    ans.add(rn.val);
                }
                if (rn.left != null) {
                    que.addLast(rn.left);
                }

                if (rn.right != null) {
                    que.addLast(rn.right);
                }
            }
        }

        return ans;
    }

    // ? Bhaiya Method

    // Just have inserted the right child first and then the left child.
    // So therefore the first element in the every level now is the right side view

    public static void rightView(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);

        ArrayList<Integer> ans = new ArrayList<>();
        while (que.size() != 0) {
            int size = que.size();
            ans.add(que.getFirst().val);
            while (size-- > 0) {
                TreeNode rn = que.removeFirst();

                if (rn.right != null)
                    que.addLast(rn.right);
                if (rn.left != null)
                    que.addLast(rn.left);
            }

        }
    }

    // * Dfs method :

    // ` Used same concept as above dfs for right view
    // Just made the right call first to get the right side view

    public void rightSideView_dfs(TreeNode root, int level, int[] maxLevel, List<Integer> ans) {

        if (root == null)
            return;

        if (level > maxLevel[0]) {
            ans.add(root.val);
            maxLevel[0] = level;
        }

        rightSideView_dfs(root.right, level + 1, maxLevel, ans);
        rightSideView_dfs(root.left, level + 1, maxLevel, ans);
    }

    public List<Integer> rightSideView_dfs(TreeNode root) {

        if (root == null)
            return new ArrayList<>();

        List<Integer> ans = new ArrayList<>();
        int[] arr = new int[] { -1 };
        rightSideView_dfs(root, 0, arr, ans);

        return ans;

    }

    // ! Important Point to note : See the one note section

    // ? If told to consider the 45° of tree gometry, then the left and right view
    // ? might change. 


    

    // b <=================Vertical Order of Binary Tree =================>

    // Given the root of a binary tree, calculate the vertical order traversal of
    // the binary tree.

    // For each node at position (row, col), its left and right children will be at
    // positions (row + 1, col - 1) and (row + 1, col + 1) respectively. The root of
    // the tree is at (0, 0).

    // ? Left child will be at an angle of -45° and right child will be at an angle
    // ? of +45°. It is like plotting the tree in graph.

    public static void main(String[] args) {

    }

}
