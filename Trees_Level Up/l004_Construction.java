import java.util.LinkedList;
import java.util.ArrayList;

public class l004_Construction {

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // Construct Tree form inorder . i.e sorted array

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

    // *** Important question {Was not solved in first, second , .... }
    // Practice this question

    // Contruct a BST from DLL
    // https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/trees/convert-sorted-dll-to-bst/ojquestion

    // If asked to convert a circular doubly linkedlist to BST, then first berak the
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

        TreeNode root = middle_Node_LinkedList(head);
        // Most important condition. Not easily figurable. Dry run.
        TreeNode leftDLLHead = head == root ? null : head; // To get free from infinite loop that was comming because of
                                                           // when there was single node. The node will start to point
                                                           // itself, hence becoming an infinite loop condition.
                                                           // Therefore null is passed if the head is same as the node
                                                           // value
        TreeNode rightDLLHead = root.right;

        if (root.left != null) // Jaroori nhi hai ki humesha root ka left exist kare. For a single node, left
                               // to hoga he nhi, to head ka left null point exception de dega
            root.left.right = null;
        root.left = root.right = rightDLLHead.left = null; // To break all the links of doubly link list. After all
                                                           // links are broken, treat root as a single node

        root.left = sorted_DLL_To_BST(leftDLLHead);
        root.right = sorted_DLL_To_BST(rightDLLHead);

        return root;
    }

    // Convert a binary Tree into BST
    // Fitst convert Binary Tree to Doubly linked list
    // Then convert Doubly linked list to sorted doubly linked list by using mege
    // sort in doubly linked list
    // then finally convert the sorted doubly linkedlist to BST

    // BT ===> DLL == > SDL ==> BST

    public static TreeNode rightMost(TreeNode left, TreeNode curr) {
        while (left.right != null && curr != left.right) {
            left = left.right;
        }

        return left;
    }

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

    public static TreeNode sort_Doubly_LinkedList(TreeNode head) {

        if (head == null || head.right == null) {
            return head;
        }
        TreeNode mid = middle_Node_LinkedList(head);
        TreeNode prev = mid.left, forw = mid.right;
        mid.left = mid.right = forw.left = null;
        if (prev != null) {
            prev.right = null;
        }

        TreeNode nHead = forw;
        TreeNode left = sort_Doubly_LinkedList(head == mid ? null : head);
        TreeNode right = sort_Doubly_LinkedList(nHead);

        return merge_two_sorted_doubly_linkedlist(left, right);
    }

    public static void main(String[] args) {

    }
}