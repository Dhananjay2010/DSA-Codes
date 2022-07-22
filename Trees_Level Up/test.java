import java.util.ArrayList;
import java.util.LinkedList;

public class test {

    public static ArrayList<Integer> diagonalSum(Node root) {

        ArrayList<Integer> ans = new ArrayList<>();

        if (root == null) {
            return ans;
        }

        LinkedList<Node> que = new LinkedList<>();
        que.addLast(root);

        while (que.size() != 0) {
            int size = que.size();

            int sum = 0;
            while (size-- > 0) {
                Node rn = que.removeFirst();

                while (rn != null) {
                    sum += rn.data;
                    if (rn.left != null) {
                        que.add(rn.left);

                    }
                    rn = rn.right;
                }
            }

            ans.add(sum);
        }

        return ans;
    }

}
