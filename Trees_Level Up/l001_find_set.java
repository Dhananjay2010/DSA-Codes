import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.lang.StringBuilder;

public class l001_find_set {

    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this.val = val;
        }

        // TreeNode node = new TreeNode(10); == > To create a object of TreeNode class;
        // this always refer to the current object. It is always used to resolve
        // conflict

        // new TreeNode(10) == > It is created in heap and this throws an address that
        // is catched by node variable.
    }

    public static int size(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int left = size(node.left);
        int right = size(node.right);

        return left + right + 1;
    }

    public static int height(TreeNode node) {
        // Height is always calculated in terms of number of edges.
        if (node == null) {
            return -1;
        }

        int height_left = height(node.left);
        int height_right = height(node.right);

        return Math.max(height_left, height_right) + 1;
    }

    public static int max(TreeNode node) {

        if (node == null) {
            return -(int) 1e9;
        }

        int left_max = max(node.left);
        int right_max = max(node.right);

        return Math.max(node.val, Math.max(left_max, right_max));
    }

    public static boolean find(TreeNode node, int data) {
        if (node == null) {
            return false;
        }

        if (node.val == data) {
            return true;
        }

        return find(node.left, data) || find(node.right, data);
    }

    public static ArrayList<TreeNode> NodeToRootPath(TreeNode root, int data) {

        if (root == null) {
            return new ArrayList<>();
        }
        if (root.val == data) {
            ArrayList<TreeNode> ans = new ArrayList<>();
            ans.add(root);
            return ans;
        }

        ArrayList<TreeNode> left = NodeToRootPath(root.left, data);
        if (left.size() > 0) {
            left.add(root);
            return left;
        }

        ArrayList<TreeNode> right = NodeToRootPath(root.right, data);
        if (right.size() > 0) {
            right.add(root);
            return right;
        }
        return new ArrayList<>();
    }

    boolean nodeToRootPath_(TreeNode root, int data, ArrayList<TreeNode> ans) {

        if (root == null)
            return false;

        if (root.val == data) {
            ans.add(root);
            return true;
        }

        // if (nodeToRootPath_(root.left, data, ans))
        // {
        // ans.add(root);
        // return true;
        // }

        // if (nodeToRootPath_(root.right, data, ans))
        // {
        // ans.add(root);
        // return true;
        // }

        // return false;

        boolean res = nodeToRootPath_(root.left, data, ans) || nodeToRootPath_(root.right, data, ans);

        if (res)
            ans.add(root);
        return res;
    }

    public static ArrayList<Integer> exactlyOneChild(TreeNode root) {
        // We needed to add parent who has exactly on child.
        // Just going by the faith in recursion
        // Faith :-
        // Maine left ko bola ki tu apne sare single parent leke aaja
        // Maine right ko bola ki tu apne sare single parent leke aaja
        // badmne maine apne aap ko check kiye ki agar mai single parent hun to mai apne
        // aap ko add kar lunga
        if (root.left == null && root.right == null) {
            return new ArrayList<>();
        }
        ArrayList<Integer> ans = new ArrayList<>();

        if (root.left != null) {
            ArrayList<Integer> left = exactlyOneChild(root.left);
            if (left.size() > 0) {
                for (int e : left) {
                    ans.add(e);
                }
            }
        }
        if (root.right != null) {
            ArrayList<Integer> right = exactlyOneChild(root.right);
            if (right.size() > 0) {
                for (int e : right) {
                    ans.add(e);
                }
            }
        }

        if (root.left == null || root.right == null) { // This can be checked in pre-order also(before left and right
                                                       // calls)
            ans.add(root.val);
        }
        return ans;
    }

    public static int countExactlyOneChild(TreeNode node) {
        // Logic is same as above. Recursion runs the same.
        if (node.left == null && node.right == null) {
            return 0;
        }

        // if(node == null || (node.left == null && node.right == null)){ == > With this
        // check we don't need bottom node.left != null and node.right != null check.
        // return 0
        // }

        int count = 0;

        if (node.left != null) { // Not required if at the top there is check of root==null, return 0;
            int left = countExactlyOneChild(node.left);
            if (left > 0) {
                count += left;
            }
        }

        if (node.right != null) { // Not required if at the top there is check of root==null, return 0;
            int right = countExactlyOneChild(node.right);
            if (right > 0) {
                count += right;
            }
        }

        if (node.left == null || node.right == null) {
            count += 1;
        }
        return count;
    }

    // Leetcode 257 easy
    public void binaryTreePaths(TreeNode root, List<String> ans, String str) {
        // String operation is too costly since string are immutable. We can use string
        // Builder in place of string.

        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            ans.add(str + root.val);
            return;
        }

        binaryTreePaths(root.left, ans, str + root.val + "->");
        // Hum yahan pe root.left==null check nhi kar rahe kyuki uper root ==null ka
        // check hai jo return kar dega agar root.left null hua to.
        // Same niche wali call ke liye
        binaryTreePaths(root.right, ans, str + root.val + "->");
    }

    public List<String> binaryTreePaths(TreeNode root) {
        // Logic of the code is pure recursion.
        List<String> ans = new ArrayList<>();
        binaryTreePaths(root, ans, "");
        return ans;
    }

    // 863. All Nodes Distance K in Binary Tree
    // https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
    // Node to Root path, count exactly one child are the prerequisite to this.
    // Time Complexity : O(n), Space complexity : O(1)
    public int distanceK(TreeNode root, TreeNode target, int k, List<Integer> ans) {
        // This is done without using an extra space. We have interger type return type
        // to make it possible.
        if (root == null) {
            return -1;
        }

        if (root == target) {
            kLevelDown(root, k, ans, null);
            return 1;
        }

        int lc = distanceK(root.left, target, k, ans);
        if (lc != -1) {
            kLevelDown(root, k - lc, ans, root.left); // k-lc is done so that k value decreases as it moves upwards
                                                      // toward root. root.left here is the block node since we don't
                                                      // want the previous node to go to this visited path.
            return lc + 1;
        }

        int rc = distanceK(root.right, target, k, ans);
        if (rc != -1) {
            kLevelDown(root, k - rc, ans, root.right);
            return rc + 1;
        }

        return -1;

    }

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<Integer> ans = new ArrayList<>();
        int count = distanceK(root, target, k, ans);
        return ans;
    }

    public void kLevelDown(TreeNode root, int k, List<Integer> ans, TreeNode block) {
        if (root == null || k < 0 || root == block) {
            return;
        }
        if (k == 0) {
            ans.add(root.val);
            return;
        }
        kLevelDown(root.left, k - 1, ans, block);
        kLevelDown(root.right, k - 1, ans, block);
    }

    // One more solution to this question would be to call root to node path for the
    // target node and then for every node call the k level down function but here
    // we would have used extra space to store node to root path values
    // It's Time Complexity : O(n), Space complexity : O(n)

    public List<Integer> distanceK_extraSpace(TreeNode root, TreeNode target, int k) {

        ArrayList<TreeNode> path = new ArrayList<>();
        nodeToRootPath_(root, target.val, path);

        List<Integer> ans = new ArrayList<>();
        TreeNode block = null;
        for (int i = 0; i < path.size(); i++) {
            kLevelDown(path.get(i), k - i, ans, block);
            block = path.get(i);
        }

        return ans;
    }

    // Burning Tree
    // https://www.geeksforgeeks.org/burn-the-binary-tree-starting-from-the-target-node/
    // It is same like distance k integer return function. First just search the
    // elememt and then proceed.
    // Try to break into different function.

    // This is the pure recursive method.
    // The easier way would be to get root to node path and call the below function.
    // public static void burningTree(TreeNode root, TreeNode block,
    // ArrayList<ArrayList<Integer>> ans, int time)

    public static void burningTree(TreeNode root, TreeNode block, ArrayList<ArrayList<Integer>> ans, int time) {

        if (root == null || root == block) {
            return;
        }

        if (time == ans.size())
            ans.add(new ArrayList<>());
        ans.get(time).add(root.val); // Agar koi node mera (root == null || root == block) is wale check ko pass kar
                                     // raha hai, to wo mere ans ka part to banega hi.

        burningTree(root.left, block, ans, time + 1);
        burningTree(root.right, block, ans, time + 1);
    }

    public static int burningTree(TreeNode root, TreeNode target, ArrayList<ArrayList<Integer>> ans) {
        // This code is same as the distance k. Same concept but just a little tweak.
        // If not getting the concept, do the stack dry run. Here also there will be
        // block node to not get to the already visited node.
        if (root == null) {
            return -1;
        }

        if (root == target) {
            burningTree(root, null, ans, 0);
            return 1;
        }

        int lc = burningTree(root.left, target, ans);
        if (lc != -1) {
            burningTree(root, root.left, ans, lc);
            return lc + 1;
        }

        int rc = burningTree(root.right, target, ans);
        if (rc != -1) {
            burningTree(root, root.right, ans, rc);
            return rc + 1;
        }

        return -1;
    }

    // Agar tree mai kabhi bhi aisa ho ki tumhe root node ki taraf jate hua(matlab
    // uper ki taraf) time kabhi
    // bhi agar dubara niche jana pade to uske liye dusra function likho
    // Jaisa humne k distance mai aur burning tree mai kiya hai
    public static void burningTree(TreeNode root, TreeNode Target) {

        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        burningTree(root, Target, ans);

        // Worst case complexity : O(2n) == > O(n);
        // Is when we have complete binary tree and our first call is of left and then
        // right. And now in this case if we have our target node to be the rightmost
        // element, then O(n) will be required to traverse the whole tree and another
        // O(n) would be required to get the time in burning the tree therefore O(2n) is
        // the complexity
    }

    // Follow up question : - Burning Tree and water

    public static void burningTreeAndWater(TreeNode root, TreeNode block, ArrayList<ArrayList<Integer>> ans, int time,
            HashSet<Integer> water) {
        if (root == null || root == block || water.contains(root.val)) {
            return;
        }

        if (ans.size() == time)
            ans.add(new ArrayList<>());
        ans.get(time).add(root.val);

        burningTreeAndWater(root.left, block, ans, time + 1, water);
        burningTreeAndWater(root.right, block, ans, time + 1, water);
    }

    public static int burningTreeAndWater(TreeNode root, int target, HashSet<Integer> water,
            ArrayList<ArrayList<Integer>> ans) {

        if (root == null)
            return -1;

        if (root.val == target) {
            if (!water.contains(root.val)) {
                burningTreeAndWater(root, null, ans, 0, water);
                return 1;
            }
            return -2; // fire node is present but it has water.
        }

        int lc = burningTreeAndWater(root.left, target, water, ans);
        if (lc > 0) { // > 0 == > It will only work if we have answer, means +ve value
            if (water.contains(root.val)) {
                return -2;
            } else {
                burningTreeAndWater(root, root.left, ans, lc, water);
                return lc + 1;
            }
        }

        if (lc == -2) {
            return -2;
        }

        int rc = burningTreeAndWater(root.right, target, water, ans);
        if (rc > 0) {
            if (water.contains(root.val)) {
                return -2;
            } else {
                burningTreeAndWater(root, root.left, ans, rc, water);
                return rc + 1;
            }
        }
        if (rc == -2) {
            return -2;
        }

        return -1;
    }

    public static void burningTreeAndWater(TreeNode root, int target, ArrayList<Integer> water) {

        // It is same as like burning tree. The only difference is when we return the
        // after we reach the fire node. Jaise he fire node pe pahunche, hunme niche ke
        // sare elements ke liye call laga di. ab jab return kiya 1, lc ya rc ne catch
        // kiya hoga. tab hume check karna hoga ki root water node to nhi hai. Kyun
        // ki agar water node hua to hume kuch aur return karna hoga jaise ki -2 taki
        // hume pata rahe ki water mila hai niche, to fire spread nhi ho payegi. Aur hum
        // sidhe he return kar lenge. Gfg wala tree uthana burning tree question ka aur
        // usme alag alag node ko water aur fire node banake test karna.

        // -1 indicates that mujhe fire node mila he nho
        // - 2 indicates that mujhe fire node mila but water bhi mila khin pe
        // one more good test case is the skew tree to understand it properly.
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        HashSet<Integer> waterSet = new HashSet<>();
        for (int e : water) {
            waterSet.add(e);
        }

        int count = burningTreeAndWater(root, target, waterSet, ans);
    }

    // Lowest Common Ancestor (LCA)
    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
    // Brute Force
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        ArrayList<TreeNode> pNodePath = new ArrayList<>();
        ArrayList<TreeNode> qNodePath = new ArrayList<>();

        boolean res1 = nodeToRootPath(root, p, pNodePath);
        boolean res2 = nodeToRootPath(root, q, qNodePath);

        int pSize = pNodePath.size() - 1;
        int qSize = qNodePath.size() - 1;

        TreeNode lca = null;
        while (pSize >= 0 && qSize >= 0) {
            if (pNodePath.get(pSize) == qNodePath.get(qSize)) {
                lca = pNodePath.get(pSize);
            }
            pSize--;
            qSize--;
        }

        return lca;
    }

    public boolean nodeToRootPath(TreeNode root, TreeNode target, ArrayList<TreeNode> ans) {

        if (root == null) {
            return false;
        }
        if (root == target) {
            ans.add(root);
            return true;
        }
        boolean res = nodeToRootPath(root.left, target, ans) || nodeToRootPath(root.right, target, ans);
        if (res) {
            ans.add(root);
        }

        return res;
    }

    // Mine optimized way (Bad Code)

    TreeNode lca = null;

    public TreeNode lowestCommonAncestor_opti(TreeNode root, TreeNode p, TreeNode q) {
        int l = lowestCommonAncestor_main(root, p, q);
        return lca;
    }

    public int lowestCommonAncestor_main(TreeNode root, TreeNode p, TreeNode q) {

        if (root == null) {
            return -1;
        }

        int left = lowestCommonAncestor_main(root.left, p, q);
        int right = lowestCommonAncestor_main(root.right, p, q);

        if ((root == p || root == q) && (left > 0 || right > 0)) {
            // Agar mai he p || q ke equal hun aur left ya right mai se koi true hai to mai
            // lca hun
            lca = root;
        }

        if (root == p || root == q) {
            // 1 denotes p mil gaya
            // 2 denotes q mil gaya
            return root == p ? 1 : 2;
        }

        if (left > 0 && right > 0) { // agar dono mil gaye, to mai lca
            lca = root;
            return left + right;
        }

        if ((left > 0 && right < 0) || (right > 0 && left < 0)) { // Agar left true hai aur right false, to mujhe left
                                                                  // return karna hoga indicatig that ki left mil gaya
            return (left > 0 && right < 0) ? left : right;
        }

        return -1;
    }

    // bhaiya code(thoda clean hai)

    public TreeNode lowestCommonAncestor_bhaiya_opti(TreeNode root, TreeNode p, TreeNode q) {
        boolean l = lowestCommonAncestor_main_opti(root, p, q);
        // No extra space is used other than recursive stack space
        return lca;
    }

    public boolean lowestCommonAncestor_main_opti(TreeNode root, TreeNode p, TreeNode q) {

        if (root == null) {
            return false;
        }

        boolean selfPresent = (root == p || root == q);

        boolean leftPresent = lowestCommonAncestor_main_opti(root.left, p, q);
        boolean rightPresent = lowestCommonAncestor_main_opti(root.right, p, q);

        if ((leftPresent && rightPresent) || (selfPresent && leftPresent) || (selfPresent && rightPresent)) {
            lca = root;
        }

        return leftPresent || rightPresent || selfPresent;

    }

    public static void main(String args) {

    }
}