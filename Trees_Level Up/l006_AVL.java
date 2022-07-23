public class l006_AVL {

    // # The first question to be asked is in which minimum number of nodes the
    // # structure of the tree gets disbalanced.

    // Not in one node, not in two.

    // ? Whenever there are min of three node, the structure of the tree can be
    // ? unbalanced.

    // So whenever we have 3 nodes, we can total have 5 different formation.

    // 1. Having a node as root and root has left and right child. This one will be
    // ` balanced.

    // ! To calculate the balance of a node
    // # bal=height(leftChild) - height(rightChild);

    // ? Rest of the four will be uunbalanced.

    // 2. ll
    // 3. lr
    // 4. rr
    // 5. rl

    // Write the balance and height for each node in above unbalanced trees
    // structures.

    //

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        int bal = 0;
        int height = 0;

        TreeNode(int val, TreeNode left, TreeNode right, int bal, int height) {
            this.val = val;
            this.left = left;
        }
    }

    public static class oen {

    }

}