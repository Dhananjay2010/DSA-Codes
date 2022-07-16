public class test {

    public static Node rightMostElement(Node left, Node curr) {

        while (left.right != null && curr != left.right) {
            left = left.right;
        }

        return left;
    }

    public static void Morris_Traversal_Pre_Succ(Node root, Node value) {

        Node curr = root;
        Node predecessor = null, successor = null;

        while (curr != null) {

            Node left = curr.left;
            if (left == null) {
                // print;
                if (curr.data == value.data) {
                    successor = curr.right;
                    break;
                }
                predecessor = curr;
                curr = curr.right;
            } else {
                Node currKeLeftKaRightMost = rightMostElement(left, curr);

                if (currKeLeftKaRightMost.right == null) {
                    currKeLeftKaRightMost.right = curr; // Thread creation
                    curr = curr.left;
                } else {
                    currKeLeftKaRightMost.right = null; // thread destroy
                    if (curr.data == value.data) {
                        successor = curr.right;
                        break;
                    }
                    predecessor = curr;
                    // print;
                    curr = curr.right;
                }
            }
        }
    }

}
