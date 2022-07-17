public class test {

    public static Node getLeftMost(Node root) {
        if (root == null)
            return null;

        Node curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }

        return curr;
    }

    public static Node getRightMost(Node root) {
        if (root == null)
            return null;

        Node curr = root;

        while (curr.right != null) {
            curr = curr.right;
        }

        return curr;
    }

    public static void pre_succ_in_BST(Node root, Node key) {

        Node curr = root;
        Node pred = null;
        Node succ = null;
        while (curr != null) {

            if (curr == key) {

                Node currKeRightKaLeftMost = getLeftMost(curr.right);
                succ = currKeRightKaLeftMost != null ? currKeRightKaLeftMost : succ;

                Node currKeLeftKaRightMost = getRightMost(curr.left);
                pred = currKeLeftKaRightMost != null ? currKeLeftKaRightMost
                        : pred;
            } else if (curr.data < key.data) {
                pred = curr;
                curr = curr.right;

            } else {
                succ = curr;
                curr = curr.left;
            }
        }
    }

}
