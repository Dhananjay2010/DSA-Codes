import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;

public class l004_Construction {

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static class ListNode {
        int val = 0;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    // Construct Tree form inorder of a BST . i.e sorted array

    // We know that inorder of BST is sorted.

    // Basic faith :
    // Maine sabse middle wale ko root banaya.
    // Jo middle se left wale index hain wo sare left subtree ka part banaege kyunki
    // wo middle se chote hain because array is sorted

    // Jo middle se right wale hain wo right subtree ka part banenge

    // Isi kaam ko karne ke liye mai index ko mask karke bhej raha hun
    // Therefore starting index and ending index are used.

    // Best is to do stack dry run for the following test case :
    // 10, 20, 28, 31, 40, 42, 56, 68, 90

    public static TreeNode construct_BST_from_sorted_array(int[] arr, int si, int ei) {

        if (si > ei)
            return null;
        int mid = (si + ei) / 2;
        TreeNode root = new TreeNode(arr[mid]);

        root.left = construct_BST_from_sorted_array(arr, si, mid - 1);
        root.right = construct_BST_from_sorted_array(arr, mid + 1, ei);

        return root;
    }

    public static TreeNode construct_BST_from_sorted_array(int[] arr) {
        return construct_BST_from_sorted_array(arr, 0, arr.length - 1);
    }

    // * Important question {Was not solved in first, second , .... }
    // Practice this question

    // Contruct a BST from DLL
    // https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/trees/convert-sorted-dll-to-bst/ojquestion

    // 'If asked to convert a circular doubly linkedlist to BST, then first berak
    // the
    // circle then proceed the same
    public static TreeNode middle_Node_LinkedList(TreeNode head) {

        if (head == null || head.right == null) {
            return head;
        }

        TreeNode slow = head, fast = head;

        while (fast.right != null && fast.right.right != null) {
            slow = slow.right;
            fast = fast.right.right;
        }

        return slow;
    }

    // dry run for two nodes {10,20}
    // 10 aaya mid, ab 10 ke right mai 20 aayega aur uski call lagegi to head.right
    // wali condition head return karke sambhal legi aur kyunki hum mid se break kar
    // rehe hain to head ka left to null hoga he, per ab left mai to head he pass
    // hoga na joki abhi bhi 10 he hai isiliye niche root ko null kiya tab call
    // lagayi taki head ==null ye wali infinite concition sambhal le.
    public static TreeNode sorted_DLL_To_BST(TreeNode head) {
        if (head == null || head.right == null) // Very important condition
            return head;

        TreeNode mid = middle_Node_LinkedList(head);
        // Most important condition. Not easily figurable. Dry run.
        TreeNode leftDLLHead = head == mid ? null : head; // To get free from infinite loop that was comming because of
                                                          // when there was single node. The node will start to point
                                                          // itself, hence becoming an infinite loop condition.
                                                          // Therefore null is passed if the head is same as the node
                                                          // value.
                                                          // == > Always remember mai mid ke left aur right ki call laga
                                                          // raha hun isislye ye wala check important hai
        TreeNode rightDLLHead = mid.right;

        if (mid.left != null) // Jaroori nhi hai ki humesha root ka left exist kare. For a single node, left
                              // to hoga he nhi, to head ka left null point exception de dega
            mid.left.right = null;
        mid.left = mid.right = rightDLLHead.left = null; // To break all the links of doubly link list. After all
                                                         // links are broken, treat root as a single node

        mid.left = sorted_DLL_To_BST(leftDLLHead);
        mid.right = sorted_DLL_To_BST(rightDLLHead);

        return mid;
    }

    // Sorted List to BST
    // https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/
    // Leetcode 109
    // Dry run on Test case : -10, -3, 0, 5, 9

    public TreeNode sortedListToBST(ListNode head) {

        if (head == null || head.next == null) {
            return head == null ? null : new TreeNode(head.val);
        }

        ListNode slow = head, fast = head;
        ListNode prev = null;
        while (fast.next != null && fast.next.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode mid = slow;
        ListNode nhead = mid.next;
        mid.next = null;
        if (prev != null) { // Also important because in some cases prev will not exist. Like for two node
                            // list.
            prev.next = null;
        }

        TreeNode root = new TreeNode(mid.val);
        root.left = sortedListToBST(head == mid ? null : head); // very important condition (head == mid ? null : head)
        root.right = sortedListToBST(nhead);

        return root;

    }

    // <============================== Important Question ========================>

    // Convert a binary Tree into BST
    // Fitst convert Binary Tree to Doubly linked list
    // Then convert Doubly linked list to sorted doubly linked list by using mege
    // sort in doubly linked list
    // then finally convert the sorted doubly linkedlist to BST

    // BT ===> DLL == > SDL ==> BST

    // Now write seperate function for all of the conversions
    // When writing a function, just think about that function only. Nothing else.

    public static TreeNode rightMost(TreeNode left, TreeNode curr) {
        while (left.right != null && curr != left.right) {
            left = left.right;
        }

        return left;
    }

    // converting the binary tree to doubly linked list
    // Simply taking a dummy node and whenever we find a inorder node, we attach a
    // pointer and keep moving forward
    public static TreeNode morrisTraversal_BinaryTree_To_DoublyLinkedList(TreeNode head) {

        TreeNode curr = head;
        TreeNode prev = new TreeNode(-1), dp = prev;
        while (curr != null) {
            TreeNode left = curr.left;
            if (left == null) {
                // print
                prev.right = curr;
                curr.left = prev;
                prev = prev.right;
                curr = curr.right;
            } else {
                TreeNode currKeLeftKaRightMost = rightMost(left, curr);

                if (currKeLeftKaRightMost.right == null) {
                    currKeLeftKaRightMost.right = curr; // thread creation
                    curr = curr.left;
                } else {
                    currKeLeftKaRightMost.right = null; // thread break
                    prev.right = curr;
                    curr.left = prev;
                    prev = prev.right; // Move prev to next node
                    // print;
                    curr = curr.right;
                }
            }
        }

        TreeNode nHead = dp.right; // connection break;
        dp.right = null;
        return nHead;
    }

    // Dry run. It is mostly same as merge two linked list.
    // But here extra pointers have to be managed so dry run and then write the code
    public static TreeNode merge_two_sorted_doubly_linkedlist(TreeNode head1, TreeNode head2) {

        if (head1 == null || head2 == null) {
            return head1 == null ? head2 : head1;
        }

        TreeNode curr1 = head1, curr2 = head2;

        TreeNode prev = new TreeNode(-1), dp = prev;
        while (curr1 != null && curr2 != null) {
            if (curr1.val <= curr2.val) {
                prev.right = curr1;
                curr1.left = prev;
                curr1 = curr1.right;
            } else {
                prev.right = curr2;
                curr2.left = prev;
                curr2 = curr2.right;
            }

            prev = prev.right;
        }

        if (curr1 == null) {
            prev.right = curr2;
            curr2.left = prev;
        } else {
            prev.right = curr1;
            curr1.left = prev;
        }

        TreeNode nHead = dp.right;
        dp.right = null;
        nHead.left = null;

        return nHead;
    }

    // Sorting the doubly linked list is done by using the merge sort techinique.
    // Logic is every single node is already sorted so then merge it in a sorted
    // way. This way whole DLL can be sorted.
    // Bottom Up.
    // If possible, first think of how to sort linked list. It is same like it, but
    // here two pointers have to be managed.
    // Dry run and then figure out. Think of what is required. Nothing else.
    // Simple merge sort.

    // Basic faith is maine apne left walon ko bola ki tum apne aap ko sort karke le
    // aao aur right ko bola ki tum apne aap ko sort karke leke aao aur agar mai
    // tumhe sort_merge kardunga to mujhe sorted DLL mil jayega
    public static TreeNode merge_sort_Doubly_LinkedList(TreeNode head) {

        if (head == null || head.right == null) {
            return head;
        }
        TreeNode mid = middle_Node_LinkedList(head);
        TreeNode forw = mid.right;
        mid.right = forw.left = null;

        TreeNode nHead = forw;
        TreeNode left = merge_sort_Doubly_LinkedList(head); // again an important condition.
        TreeNode right = merge_sort_Doubly_LinkedList(nHead);

        return merge_two_sorted_doubly_linkedlist(left, right);
    }

    public static TreeNode BT_To_BST(TreeNode root) {

        // These three function gives us the BT to BST.

        if (root == null) {
            return root;
        }

        TreeNode head = morrisTraversal_BinaryTree_To_DoublyLinkedList(root); // form binary tree to doubly linked list
        head = merge_sort_Doubly_LinkedList(head); // doubly Linked List to Sorted Doubly Linked List
        return sorted_DLL_To_BST(head); // sorted dll to BST
    }

    // After sorting the doublt list, then call the function of converting the dll
    // to BST. Then the fucntion becomes complete.

    // Thats how you convert the Binary tree to BST.

    // ========================= Here the questions ends==========================

    // How to print a tree
    // Just move in preorder and print left and right child of every preorder node.

    // 2 -> 4 <- 6 ==> 4 has two child 2(left) and 6(right)
    // 1 -> 2 <- 3 ==> 2 has two child 1(left) and 3(right)
    // . -> 1 <- . ==> 1 has no child
    // . -> 3 <- . ==> 3 has no child
    // 5 -> 6 <- 7 ==> 6 has two child 5(left) and 7(right)
    // . -> 5 <- . ==> 5 has no child
    // . -> 7 <- . ==> 7 has no child

    // Everything goes in inorder and the things are printed. So we can print tree
    // like this.

    public static void display(TreeNode node) {
        if (node == null)
            return;

        String str = "";
        str += ((node.left != null ? Integer.toString(node.left.val) : "."));
        str += (" -> " + Integer.toString(node.val) + " <- ");
        str += ((node.right != null ? Integer.toString(node.right.val) : "."));

        System.out.println(str);

        display(node.left);
        display(node.right);
    }

    public static void display1(TreeNode node) {
        if (node == null)
            return;

        StringBuilder sb = new StringBuilder();
        sb.append((node.left != null ? node.left.val : "."));
        sb.append(" -> " + node.val + " <- ");
        sb.append((node.right != null ? node.right.val : "."));

        System.out.println(sb.toString());

        display1(node.left);
        display1(node.right);

    }

    // <====================Given a preorder of a BST, construct a BST.

    // https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/trees/construct-bst-from-preorder-traversal/ojquestion
    // https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
    // Leetcode 1008

    // One can think to sort the array given and then convert it to BST, but it's
    // complexity will be high and importantly the shape of the tree will be changed
    // and we would not get the exact same BST, therefore cannot be used.

    // So what we will do is decide the range of every element and then try to
    // construct a bst.

    // Taking an example of an array [8,3,1,6,4,7,10,14,13]

    // 8 will be the root node since it is orevorder ao it will have range of -∞ to
    // ∞ .
    // We will also be keeping a static pointer over the array, which can be easily
    // be replaced by array of size 1.

    // So now the pointer moves to 3.
    // The first thing to consider is that node just left to 8 will have the range
    // of -∞ to 8. Why because we are considering the BST and it is sorted. So the
    // left subtree will have values less than 8

    // Now the first call of 8 left is made.

    // Now because 3 comes in range of -∞ to 8, therefore next call of 3 left is
    // made and the pointer moves to 1.

    // Why range -∞ to 3 ? It is because all nodes to the left of 3 will be less
    // than 3, since we are considering a BST.

    // Now because 1 comes in range of -∞ to 3, therefore next call of 1 left is
    // made and the pointer moves to 6.

    // Now because 6 does not come in range of -∞ and 1, therefore it returned back
    // and right call of 1 is made. But because 6 does not come in range of 1 to 3
    // therefore is returned back null.

    // Now both call of 1 is made, now the node is created and returned and
    // connected to 3.

    // Now 3 right call is made.

    // Now because 6 is in the range of 3 to 8, therefore the call is made of 6
    // left and the pointer moves to 4.
    // Why 3 to 8 ? It is because the elements at the right side of 3 will be
    // greater than 3, but ofcourse will be less than 8 since we are considering a
    // BST

    // Hence the whole dry run goes on similary for all the elements by passing the
    // ranges.

    static int p = 0; // rather than using a static variable, use an array of length 1.

    public TreeNode bstFromPreorder(int[] preorder, int leftRange, int rightRange) {

        if (p > preorder.length - 1 || preorder[p] < leftRange || preorder[p] > rightRange) {
            return null;
        }

        TreeNode node = new TreeNode(preorder[p]);
        p++;

        node.left = bstFromPreorder(preorder, leftRange, node.val);
        node.right = bstFromPreorder(preorder, node.val, rightRange);

        return node;
    }

    public TreeNode bstFromPreorder(int[] preorder) {
        return bstFromPreorder(preorder, -(int) 1e9, (int) 1e9);
    }

    // <============================Construct BST from Postorder

    // What we are goinn to do is similar to above, but rather with a silght change.
    // We are going to keep the pointer from the end of the array because last node
    // will be the root nod because of post order.
    // Then we going to keep decreasing the pointer.

    // Range concept remains the same.

    // Here we are going to make right call first since becacuse the right subtree
    // elements are first going to be touched when we traverse the postorder array
    // reverse.

    // Dry run for test case [1,4,7,6,3,13,14,10,8] same tree postorder as above

    public static TreeNode bstFromPostorder(int[] postorder, int leftRange, int rightRange, int[] idx) {
        int i = idx[0];
        if (i <= -1 || postorder[i] < leftRange || postorder[i] > rightRange)
            return null;

        TreeNode root = new TreeNode(postorder[i]);
        idx[0]--;

        root.right = bstFromPostorder(postorder, root.val, rightRange, idx);
        root.left = bstFromPostorder(postorder, leftRange, root.val, idx);

        return root;
    }

    public static TreeNode bstFromPostorder(int[] postorder) {
        int[] idx = new int[1];
        idx[0] = postorder.length - 1;
        return bstFromPostorder(postorder, -(int) 1e9, (int) 1e9, idx);
    }

    //b <================================Construct a BST from level order

    // https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/trees/construct-bst-from-levelorder-traversal/ojquestion

    // What we are again going to do is have range for each element.
    // But How in levelorder??

    // Basically what is done is again range concept is used. But level order is
    // given therefore queue is used to store the range.

    // Therefore a class pair is made consisting of a parent node and the left and
    // right ranges.

    // considering a test case of [8,3,10,1,6,14,4,7,13]
    // Maintaining a variable for moving the pointer in array.

    // Now 8 will be our root node. Therefore already the range of -∞ to ∞ is added
    // in the queue. Now we check if the top pair of queue is in the range of our
    // first array element, if yes then we remove the first element from queue and
    // then in queue its ranges to its left child and right child, with parent.

    // Then the array iterator is increased.

    // Parent because we need to where the child needs to be attached.

    // Next comes the second element. Now we check if the top pair of queue is in
    // the range of our array element, if yes then we remove the pair. Now because
    // the pair parent is
    // not null, we have to decide where to add the current array element. If the
    // array value is less than the parent, it is attached to left of the parent
    // (because in BST, every node is a BST), otherwise attached to the right of the
    // parent.

    // Then we add the range of left and right child in the queue with parent as
    // current element and then increase the iterator.

    // Same thing goes on.

    // You did the whole thing right, but the put & in place of || in while
    // condition. Always check for these type of sily mistakes.
    public static class level_order_pair {

        TreeNode par = null;
        int leftRange = -(int) 1e9;
        int rightRange = (int) 1e9;

        level_order_pair(TreeNode node, int leftRange, int rightRange) {
            this(leftRange, rightRange); // Constructor call must be the first statement in a constructor
            this.par = node;
        }

        level_order_pair(int leftRange, int rightRange) {
            this.leftRange = leftRange;
            this.rightRange = rightRange;
        }

    }

    public static TreeNode bstFromLevelorder(int[] levelorder, LinkedList<level_order_pair> q) {

        int l = levelorder.length;
        int i = 0;
        TreeNode head = null;
        q.addLast(new level_order_pair(null, -(int) 1e9, (int) 1e9));
        while (i < l) {
            while (q.size() != 0
                    && (levelorder[i] < q.getFirst().leftRange || levelorder[i] > q.getFirst().rightRange)) {
                // While loop used to remove all not viable pair inserted in queue. Aise pair jo
                // humare array ke element ki range ko satosfy na kare unhe ek sath nikalne ke
                // liye

                level_order_pair noUseNode = q.removeFirst();
            }

            level_order_pair rp = q.removeFirst();
            TreeNode node = new TreeNode(levelorder[i]);

            if (rp.par != null) {
                if (levelorder[i] < rp.par.val) {
                    rp.par.left = node;
                } else {
                    rp.par.right = node;
                }
            } else {
                head = node;
            }

            q.addLast(new level_order_pair(node, rp.leftRange, levelorder[i]));
            q.addLast(new level_order_pair(node, levelorder[i], rp.rightRange));
            i++;
        }

        return head;
    }

    public static TreeNode bstFromLevelorder(int[] levelorder) {

        LinkedList<level_order_pair> q = new LinkedList<>();
        return bstFromLevelorder(levelorder, q);
    }

    // <=================Serialize and Deserialize============================
    // https://leetcode.com/problems/serialize-and-deserialize-bst/
    // Leetcode 449

    // Basically karna kya hai ki pehle preorder ke elements ki ek single string
    // bana do. Isko kehte hain serialize.

    // To get the preorder string any method can be used. Stack or morris or simple
    // recursion

    // Ab badme usi string se dubara tree banana is called deserialize.
    // This can be easily done by first converting the string into an array of
    // integers and then applying the same above method to convert to BST from
    // preorder.

    // ***** Don't use inorder as the structure of the tree changes.

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {

        StringBuilder sb = new StringBuilder();

        serialize(root, sb);

        return sb.toString();
    }

    private void serialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            return;
        }

        sb.append(root.val + " ");
        serialize(root.left, sb);
        serialize(root.right, sb);
    }

    private TreeNode convertToBSTFromPreorder(int[] preorder, int leftRange, int rightRange, int[] index) {

        int i = index[0];

        if (i > preorder.length - 1 || preorder[i] < leftRange || preorder[i] > rightRange) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[i]);
        index[0]++;
        root.left = convertToBSTFromPreorder(preorder, leftRange, root.val, index);
        root.right = convertToBSTFromPreorder(preorder, root.val, rightRange, index);

        return root;
    }

    public TreeNode deserialize(String data) {

        if (data == "") { // if node data, return
            return null;
        }
        String[] arr = data.split(" ");
        int[] preorder = new int[arr.length];

        for (int i = 0; i < preorder.length; i++) {
            preorder[i] = Integer.parseInt(arr[i]);
        }

        int[] index = new int[] { 0 };
        TreeNode head = convertToBSTFromPreorder(preorder, -(int) 1e9, (int) 1e9, index);
        return head;
    }

    // <=============Verify is the given preorder is the pre order of BST=========>

    // https://www.lintcode.com/problem/1307/
    // By dry run, we figured out that if the array is traversed fully, then all the
    // nodes get connected to the parent node. Therefore the preorder is a BST.

    // And if array is not fully traversed, then every node is not able to attached
    // to the parent. Therefore not a BST.

    // Same code as above. Nothing changed.

    // There is also a stack method for this. But wo samaj ni aaya. Try next time.

    public static boolean canRepresentBST(int arr[], int N) {
        // code here
        int[] index = new int[] { 0 };
        verifyPreorder(arr, -(int) 1e9, (int) 1e9, index);

        return index[0] > arr.length - 1 ? true : false;
    }

    public static void verifyPreorder(int[] preorder, int leftRange, int rightRange, int[] index) {

        int i = index[0];
        if (i > preorder.length - 1 || preorder[i] < leftRange || preorder[i] > rightRange) {
            return;
        }

        index[0]++;
        verifyPreorder(preorder, leftRange, preorder[i], index);
        verifyPreorder(preorder, preorder[i], rightRange, index);
    }

    // <==========================================Binary Tree Construction=========>

    // <================construct Binary Tree from Preorder and inorder

    // Inorder tells me the nodes that are going to lie in the left subtree and
    // rightsubtree. How ? . By finding root node in the inorder and then all values
    // to the left to the nodes consist of the left subtree of the root and all the
    // values to the right consist of the right subtree

    // Therefore inorder is needed. Otherwise I won't be able to figure out that
    // which node belongs to left subtree or right subtree.
    // Preorder first node is always the root node.

    // So what to do ?

    // First node in preorder will be alwasys the root node. Then we need to define
    // that in preorder array, upto which range it belongs to the left subtree. To
    // do that we search the root element in the inorder. So the index found in the
    // inorder, the left to that index, they will belong to left subtree and to the
    // right will belong to the right subtree. Bu we need to define the preorder
    // range. So to calculate its range, we calculate the number of elements in
    // inorder from start to root value index. That will help to get the preorder
    // range of the left subtree. If this count comes 2, then psi + 1 to psi + 2
    // will be the reange for left subtree and psi + 2 + 1 to pei will be the range
    // of right subtree for preorder.

    // For range for inorder is easy. esi to index - 1 will constitute the left
    // subtree
    // index + 1 to iei will constitute the right sub tree range.

    // Its complexity will be O(nlogn)?
    // How == >

    // T(n)= n + T(n/2) + T(n/2); Solving this gives us time complexity.
    // == > n for while loop. T(n/2) for each left and right call.

    // But the worst case coomplexity is O(n²).
    // When one recursive call gives T(1) and another gives T(n-1) complexity.
    // T(1) means one element in one call and n -1 element in second call.

    // If we use an hashmap to store the values of inorder and its index, we can
    // reduce this n for while loop to O(1), hence reducing the overall complexity
    // to O(n).

    // psi : preorder starting index
    // pei : preorder ending index
    // isi : inorder starting index
    // iei : inorder ending index
    public static TreeNode buildTreeFromPreAndInOrder(int[] preorder, int psi, int pei, int[] inorder, int isi,
            int iei) {
        if (psi > pei) // Since the root element is always from the preorder, so just checking the
                       // preorder index is fine.
            return null;

        int idx = isi;
        while (inorder[idx] != preorder[psi])
            // While loop written to find the root element index in the inorder
            // Don't check everytime from 0 to n in inorder. Just check in range
            // given.
            idx++;

        int tnel = idx - isi; // total no of elements in the left subtree
        TreeNode root = new TreeNode(preorder[psi]);
        root.left = buildTreeFromPreAndInOrder(preorder, psi + 1, psi + tnel, inorder, isi, idx - 1);
        root.right = buildTreeFromPreAndInOrder(preorder, psi + tnel + 1, pei, inorder, idx + 1, iei);

        return root;
    }

    public static TreeNode buildTreePreIn(int[] preorder, int[] inorder) {
        int n = preorder.length;
        return buildTreeFromPreAndInOrder(preorder, 0, n - 1, inorder, 0, n - 1);
    }

    // <=== Construct the tree from post and inorder======================>

    // The logic remains the same.Just the ranges changes, which have to be figured
    // out by dry run

    // But now the pei have to be made root since it is
    // postorder and the last element in the postorder is the root element

    public TreeNode buildTreePostIn(int[] postorder, int psi, int pei, int[] inorder, int isi, int iei) {

        if (psi > pei) {
            return null;
        }

        int idx = isi;
        while (inorder[idx] != postorder[pei]) {
            idx++;
        }
        int tnel = idx - isi;
        TreeNode root = new TreeNode(postorder[pei]);
        root.left = buildTreePostIn(postorder, psi, psi + tnel - 1, inorder, isi, idx - 1);
        root.right = buildTreePostIn(postorder, psi + tnel, pei - 1, inorder, idx + 1, iei);
        return root;
    }

    public TreeNode buildTreePostIn(int[] postorder, int[] inorder) {
        return buildTreePostIn(postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1);
    }

    // By using hashmap, reducing the complexity to O(n)

    public TreeNode buildTreePostIn_Hashmap(int[] postorder, int psi, int pei, int[] inorder, int isi, int iei,
            HashMap<Integer, Integer> map) {

        if (psi > pei) {
            return null;
        }

        int idx = map.get(postorder[pei]);

        int tnel = idx - isi;
        TreeNode root = new TreeNode(postorder[pei]);
        root.left = buildTreePostIn_Hashmap(postorder, psi, psi + tnel - 1, inorder, isi, idx - 1, map);
        root.right = buildTreePostIn_Hashmap(postorder, psi + tnel, pei - 1, inorder, idx + 1, iei, map);
        return root;
    }

    public TreeNode buildTreePostIn_Hashmap(int[] postorder, int[] inorder) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTreePostIn_Hashmap(postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1, map);
    }

    // b <=======Construct Binary Tree From Pre and Post======>
    // https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/
    // pre : 8,3,1,6,4,7,10,14,13
    // post: 1,4,7,6,3,13,14,10,8

    // Logic is same as above. We are jsut passing the starting and ending index of
    // the post and preorder. And we are making the first element of the preorder
    // the root node and setting

    // But now we have postorder instead of inorder.

    // Therefore we are searching the psi + 1 element in postorder array. Why ?

    // Taking an above two array for pre and post order, making 8 as the top most
    // root. Now we will want to know the number of elements in the left subtree so
    // that we can have the range. Now psi + 1 is 3. We search it in postorder
    // array. So the number to the left of 3 represent the number of elements below
    // 3 in the tree since it is post order. Hence we got the count. But since we
    // have to include 3 also, therefore we added +1 to the tnel.

    // Just dry run and you will find the range.

    // Important Note :
    // In this question the tree structure can change if the pre and post order
    // given are not of complete binary tree.
    // Tree will definetly form but the structure can change. why?

    // See the notes section in oneNote.

    // But if you provide full binary tree, then tree form is of exact shape.

    public TreeNode constructFromPrePost(int[] preorder, int psi, int pei, int[] postorder, int posi, int poei) {

        if (psi > pei) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[psi]);

        if (psi == pei) { // Important condition. Found after dry run.
            return root;
        }
        int idx = posi;
        while (postorder[idx] != preorder[psi + 1]) { // for one size array psi + 1 will not exist.Therefore, root is
                                                      // returned before in the previous check.
            idx++;
        }

        int tnel = idx - posi + 1; // Total number of elements in left subtree
        root.left = constructFromPrePost(preorder, psi + 1, psi + tnel, postorder, posi, idx);
        root.right = constructFromPrePost(preorder, psi + tnel + 1, pei, postorder, idx + 1, poei - 1);

        return root;
    }

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        return constructFromPrePost(preorder, 0, preorder.length - 1, postorder, 0, postorder.length - 1);
    }

    // b <=====Serialize and DeserializeBinary Tree =====================>
    // https://leetcode.com/problems/serialize-and-deserialize-binary-tree/

    // Serialize means to create a string so that the tree can be transfered easily.
    // Deserialize means to create the tree again from serialize string.

    // Complexity wise both the method are same.

    // b Method 1 :

    // To serialize the tree, what we are going to do is create a string of
    // preorder. But with a little twist.

    // We are going to add # where we find null in our preorder.

    // So the string would look like this after the preorder.

    // 8 3 1 # # 6 4 # # 7 # # 10 # 14 13 # # #

    // So first we can convert it into a array from string.

    // Next to deserialize it, what we are going to do is keep a static variable and
    // move it along the array.
    // And wherever we find #, we are going to return null.

    // So logic is to move in preorder and create node and make left and right call
    // and if # comes, return return null;

    // Encodes a tree to a single string.

    private static void serialize_Binary_Tree(TreeNode root, StringBuilder sb) {

        if (root == null) {
            sb.append("#");
            sb.append(" ");
            return;
        }

        sb.append(root.val + " ");
        serialize_Binary_Tree(root.left, sb);
        serialize_Binary_Tree(root.right, sb);
    }

    public static String serialize_Binary_Tree(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize_Binary_Tree(root, sb);

        return sb.toString();
    }

    private TreeNode deserialize_Binary_Tree(String[] arr, int[] index) {

        int idx = index[0];

        if (idx > arr.length - 1)
            return null;
        if (arr[idx].equals("#")) { // use equals rather than == for string comparison
            index[0]++;
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(arr[idx]));
        index[0]++;
        root.left = deserialize_Binary_Tree(arr, index);
        root.right = deserialize_Binary_Tree(arr, index);

        return root;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize_Binary_Tree(String data) {

        String[] arr = data.split(" ");

        int[] index = new int[] { 0 };

        return deserialize_Binary_Tree(arr, index);
    }

    //b Method 2:

    // What we did is simple get the levelorder string to serialize the binary tree.
    // Wherever we encountered null, we replaced # with it and got the level order
    // string

    // Now what to do deserialize it ???

    // We simply applied the bfs approach and maintained the queue. Now in bfs as
    // soon as we remove the node, we have to connect the left and right child.

    // Also we will also moving our index at the same time. Index is of array we got
    // when we split the string by space.

    // Now adding the left and right child in the queue. Same old bfs.

    // Just keep a check of checking the value # since TreeNode will not accept it.
    // So apply checks for it.

    // Other than this is pure dry run.

    public String level_order_traversal_Binary_Tree_serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        LinkedList<TreeNode> que = new LinkedList<>();

        que.addLast(root);

        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                TreeNode rn = que.removeFirst();
                if (rn == null) {
                    sb.append("# ");
                    continue;
                }
                sb.append(rn.val + " ");
                que.addLast(rn.left);
                que.addLast(rn.right);
            }
        }

        return sb.toString();
    }

    // Encodes a tree to a single string.
    public String serialize_level_order(TreeNode root) {
        return level_order_traversal_Binary_Tree_serialize(root);
    }

    public TreeNode deserialize_level_order_Binary_Tree(String[] arr, LinkedList<TreeNode> que) {

        int idx = 0;
        TreeNode root = new TreeNode(Integer.parseInt(arr[idx]));
        idx++;
        que.addLast(root);

        while (que.size() != 0) {

            int size = que.size();

            while (size-- > 0) {
                TreeNode rn = que.removeFirst();

                if (rn == null) {
                    continue;
                }

                TreeNode leftChild = arr[idx].equals("#") ? null : new TreeNode(Integer.parseInt(arr[idx]));
                idx++;
                TreeNode rightChild = arr[idx].equals("#") ? null : new TreeNode(Integer.parseInt(arr[idx]));
                idx++;

                rn.left = leftChild;
                rn.right = rightChild;

                que.add(leftChild);
                que.addLast(rightChild);

            }
        }
        return root;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize_level_order_Binary_Tree(String data) {

        if (data.length() == 0) {
            return null;
        }
        String[] arr = data.split(" ");

        LinkedList<TreeNode> que = new LinkedList<>();

        return deserialize_level_order_Binary_Tree(arr, que);
    }

    // < ============= Check if Binary Tree is Balanced ========>

    // Same basic recursion question.

    // Maine left ko bola tu balanced hai aur right ko bola tu balanced hai/
    // Aur agar tum dono balanced ho to ab height ka difference check karte hain.
    // Agar 1 se equal yakam hai to mai balanced hun aur tab mai apni height nikalke
    // set karunga, warna aise he return kar lunga

    public class pair {
        boolean isBal = true; // ek single node to balanced hota hai
        int height = -1;
    }

    public pair isBalanced_pair(TreeNode root) {

        if (root == null) {
            return new pair();
        }

        pair left = isBalanced_pair(root.left);
        if (left.isBal == false) {
            return left;
        }

        pair right = isBalanced_pair(root.right);
        if (!right.isBal) {
            return right;
        }

        pair ans = new pair();
        ans.isBal = false; // Pehle assume kiya ki mai balanced nhi hun aur tab condition check ki.

        if (Math.abs(left.height - right.height) <= 1) {
            ans.isBal = true;
            ans.height = Math.max(left.height, right.height) + 1;
        }

        return ans;
    }

    public boolean isBalanced(TreeNode root) {

        return isBalanced_pair(root).isBal;
    }

    // b <==============Given a binary Tree, Find the largest BST subtree=====>
    // https://practice.geeksforgeeks.org/problems/largest-bst/1

    // Just have to return the size of largest BST subtree.
    // A single node is a BST. Always remember.

    // Same basic recursion call.

    // Largest BST nikalne ke liye hume pehle ye bhi pata hona chahiye ki mere left
    // aur right BST hain ki nhin.

    // Agar mere left aur right BST he nhi hain to mai to BST ho he nhi sakta.
    // 'Isiliye, isBST naam ka pair mai rakha hai, aur min max bhi isiliye rakha hai

    public static class largestBST_Pair {
        boolean isBST = true;
        int min = (int) 1e9;
        int max = -(int) 1e9;
        int size = 0;
        TreeNode largestRoot = null; // if largest BST node is required
    }

    public static largestBST_Pair largestBST(TreeNode root) {

        if (root == null) {
            return new largestBST_Pair();
        }

        largestBST_Pair left = largestBST(root.left);
        largestBST_Pair right = largestBST(root.right);

        largestBST_Pair ans = new largestBST_Pair();

        ans.isBST = false;

        if (left.isBST && right.isBST && root.val > left.max && root.val < right.min) { // to check if I am BST
            ans.isBST = true;
            ans.min = Math.min(root.val, left.min);
            ans.max = Math.max(root.val, right.max);
            ans.size = left.size + right.size + 1;
            ans.largestRoot = root;
        } else { // otherwise just return the max of both.
            // `Because if I am not BST, then there is nothing to add in size
            // So returning the max of both.
            // ans.size = Math.max(left.size, right.size); // Agar sirf size managa hai
            // largest BST ka to

            if (left.size > right.size) {
                ans.size = left.size;
                ans.largestRoot = left.largestRoot;
            } else {
                ans.size = right.size;
                ans.largestRoot = right.largestRoot;
            }
        }

        return ans;
    }

    static int largestBst(TreeNode root) {
        return largestBST(root).size;
    }

    public static void main(String[] args) {

    }
}