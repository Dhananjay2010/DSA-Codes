import java.util.Arrays;

public class contest {

    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // b <======== 2458. Height of Binary Tree After Subtree Removal Queries=======>
    // https://leetcode.com/problems/height-of-binary-tree-after-subtree-removal-queries/

    static int[] h, l, maxH1, maxH2;

    public int height(TreeNode root, int level) { // Simple function to calculate the height of a tree.
                                                  // Added level
        // to know the node level. Just simply filled the values of the
        // array and calculate the height at the tree queries function.

        if (root == null)
            return -1;

        int left = height(root.left, level + 1);
        int right = height(root.right, level + 1);

        h[root.val] = Math.max(left, right) + 1;
        l[root.val] = level;

        if (h[root.val] > maxH1[level]) {
            maxH2[level] = maxH1[level];
            maxH1[level] = h[root.val];
        } else if (h[root.val] > maxH2[level]) {
            maxH2[level] = h[root.val];
        }

        return h[root.val];
    }

    public int[] treeQueries(TreeNode root, int[] queries) {
        h = new int[100001];
        l = new int[100001];
        maxH1 = new int[100001];
        maxH2 = new int[100001];
        Arrays.fill(maxH2, -1);
        Arrays.fill(maxH1, -1);
        height(root, 0);

        for (int i = 0; i < queries.length; i++) {

            int val = queries[i];

            int hh = h[val];
            int lev = l[val];

            int levMaxH1 = maxH1[lev];
            int levMaxH2 = maxH2[lev];

            // Two scenarios to be checked. If the level has only one node or it has node
            // greater than one.
            // So to check this we have to keep the value of maxH1 such that we have it does
            // not include the answer of the height, therefore we can prefill the maxH1 with
            // -1, since -1 cannot be the height of a node. Now we can get the indication if
            // there more than one node in a level, then the height of maxH1 will be
            // different that -1. Otherwise if there is only single node present, the value
            // of maxH1 will remain -1. This one is the most important scenario.

            if (levMaxH2 != -1) {
                if (hh == levMaxH1) {
                    queries[i] = lev + levMaxH2;
                } else {
                    queries[i] = lev + levMaxH1;
                }
            } else {
                queries[i] = lev - 1;
            }
        }

        return queries;
    }
}
