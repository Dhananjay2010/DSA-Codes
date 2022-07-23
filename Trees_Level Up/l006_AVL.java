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

    // 2. ll [A,B,null,C,null]
    // 3. lr [A,B,null,null,C]
    // 4. rr [A,null,B,null,C]
    // 5. rl [A,null,B,C,null]

    // Write the balance and height for each node in above unbalanced trees
    // structures.

    // After writing the height and balance, then consider other child of A,B,C.
    // Not before that because it will complicate the structure.

    // Now start writing code.

    // ? small b is the balance factor

    // If our balance factor at top{A} is 2, then it can be only ll or lr
    // if b=1 for a.left, then it is ll
    // if b=-1 for a.left, then it is lr

    // If our balance factor at top{A} is -2, then it can be only rr or rl
    // if b=-1 for a.right, then it is rr
    // if b=1 for a.right, then it is rl

    // ! Important Note :

    // Agar ll structure hota hai to hume right rotation lagana chahiye
    // Agar rr structure hota hai to hume left rotation lagana chahiye

    // Agar rl structure hota hai to pehle root.right ko right rotation. Tab ye rr
    // ki form le lega tab isme left rotation lagao balance karne ke liye

    // Agar lr structure hota hai to pehle root.left ko left rotation. Tab ye ll
    // ki form le lega tab isme right rotation lagao balance karne ke liye

    // ! Very Important to do :

    // Dry run by doing the stack diagram while adding node.

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        int bal = 0;
        int height = 0;

        TreeNode() {

        }

        TreeNode(int val) {
            this(val, null, null, 0, 0);
        }

        TreeNode(int val, TreeNode left, TreeNode right, int bal, int height) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.bal = bal;
            this.height = height;
        }
    }

    // O(1)
    public static void updateBalAndHeight(TreeNode root) {
        int lh = root.left != null ? root.left.height : -1;
        int rh = root.right != null ? root.right.height : -1;

        int bal = lh - rh;

        root.height = Math.max(lh, rh) + 1;
        root.bal = bal;
    }

    // O(1)
    public static TreeNode rightRotation(TreeNode A) {

        TreeNode B = A.left;
        TreeNode BKaRight = B.right;

        B.right = A;
        A.left = BKaRight;

        updateBalAndHeight(A); // Kyunki ab A B ka child hai to pehle A ka bal aur height update hoga tab jake
                               // ` B ka hoga
        updateBalAndHeight(B);

        return B;
    }

    // O(1)
    public static TreeNode leftRotation(TreeNode A) {

        TreeNode B = A.right;
        TreeNode BKaLeft = B.left;

        B.left = A;
        A.right = BKaLeft;

        updateBalAndHeight(A); // Kyunki ab A B ka child hai to pehle A ka bal aur height update hoga tab jake
                               // ` B ka hoga
        updateBalAndHeight(B);

        return B;
    }

    // O(1)
    // # Basically get rotation node ki bal aur height ko update karta hai agar node
    // ki bal ki value 2 nhi hai to

    // Balance Node : -1<=node.val<=1

    // # Aur agar node out of balance hai to use balance karta hai get rotation.
    public static TreeNode getRotation(TreeNode root) {
        updateBalAndHeight(root);

        if (root.bal == 2) { // ll or lr
            if (root.left.bal == 1) { // ll
                return rightRotation(root);
            } else { // lr

                // Pehle root ke left ko left rotate karke ll wale structure mai convert kiya
                // ` uske baad usme right rotation kiya

                root.left = leftRotation(root.left);
                return rightRotation(root);
            }
        } else if (root.bal == -2) { // rr or rl
            if (root.right.bal == -1) { // rr
                return leftRotation(root);
            } else { // rl

                // pehle root ke right ko right rotation kiya aur root ke right mai attach kiya.
                // ` uske baad root mai left rotation kiya

                root.right = rightRotation(root.right);
                return leftRotation(root);
            }
        }

        return root;
    }

    // ? Adding Data in AVL Tree :

    // Manle ki tree pehle empty hai.

    // Ab maine 50 ko addData call kiya. To mujhe ek Node mila 50 ka jiski bal aur
    // height (0,0) hui inintially

    // Ab maine 70 pe addData call kiya.

    // to iske liye pehle stack mai 50 ke right mai gye aur iske baad null mila to
    // 70 return hua jiske bal aur height (0,0) hogi initially

    // ab ye 70 jab return hua to ye 50 ke right mai jake hua attach.
    // ab 50 ke liye get rotation call hua jiske karan 50 ki bal aur height (-1,1)
    // ho gyi

    // Abhi tak to tree pura balanced he tha

    // Ab maine 80 ke uper addData call kiya
    // to iske liye pehle stack mai 50 ke right mai gye tab 70 mila to 70 ke bhi
    // right mai gaye to tab 80 ka new node banaya aur return kiya. 80 ki abhi bal
    // aur height (0,0) hogi initially.

    // 80 ab 70 ke right mai jake hua attach. 70 ke liye getRotation call hua jisse
    // 70 ki bal aur height (-1,1) ho gaya

    // ab 70 return hua aur 50 ke right mai jake attach hua aur 50 ke liye
    // getRotation call hua to pehle 50 ki bal aur height update hoke (-2,2) bane.

    // Ab kyunki 50 ki bal ki value -2 hui, to ab wo 4 mai se ek structure ko follow
    // karega.

    // kyunki ye rr mai pada to ab isme left rotation call hua.

    // Left rotation call hone ke baad 70 root node bana jiska left child hua 50 aur
    // right child hua 80

    // ab 50 aur 70 ne apne bal aur height ko update kiya
    // 50 ka bana bal aur height (0,0)
    // 70 ka bana bal aur height (0,1)

    // Similary aise he aur nodes agar add karte jayein to ye tree apne aap ko
    // ` balance karte rehta hai.

    public static TreeNode addData(TreeNode root, int data) { // Same as BST

        if (root == null) {
            return new TreeNode(data);
        }

        if (data < root.val) {
            root.left = addData(root.left, data);
        } else {
            root.right = addData(root.right, data);
        }

        return getRotation(root); // ? Bas yahan pe getRotation ko call kiya root return karte time
    }

    public static TreeNode leftMost(TreeNode root) {

        if (root == null) {
            return null;
        }

        while (root.left != null) {
            root = root.left;
        }

        return root;
    }

    public static TreeNode removeNode(TreeNode root, int data) { // Same as BST

        if (root == null) {
            return root;
        }

        if (data < root.val) {
            removeNode(root.left, data);
        } else if (data > root.val) {
            removeNode(root.right, data);
        } else {

            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null || root.right == null) {
                return root.left == null ? root.right : root.left;
            } else {

                TreeNode rightKaLeftMost = leftMost(root.right);
                root.val = rightKaLeftMost.val;
                root.right = removeNode(root.right, root.val);
            }
        }

        return getRotation(root); // ? Bas yahan pe getRotation ko call kiya root return karte time
    }

    public static void display(TreeNode root) {
        if (root == null)
            return;

        String ans = "";
        ans += root.left != null ? root.left.val : ".";
        ans += " -> " + root.val + " <- ";
        ans += root.right != null ? root.right.val : ".";

        System.out.println(ans);

        display(root.left);
        display(root.right);
    }

    public static void main(String[] args) {

        TreeNode root = null;
        for (int i = 1; i <= 15; i++) {
            root = addData(root, i * 10);
        }
        display(root);
    }

}