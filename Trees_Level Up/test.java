public class test {


    static int maxLevel = -1;

    public static void leftView_dfs(Node root, int level, ArrayList<Integer> ans) {

        if (root == null)
            return;

        if (level > maxLevel) {
            ans.add(root.data);
            maxLevel = level;
        }

        leftView_dfs(root.left, level + 1, ans);
        leftView_dfs(root.right, level + 1, ans);

    }

    ArrayList<Integer> leftView_dfs(Node root) {
        // Your code here

        ArrayList<Integer> ans = new ArrayList<>();
        leftView_dfs(root, 0, ans);
    }

}
