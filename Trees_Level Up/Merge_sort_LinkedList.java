public class Merge_sort_LinkedList {

    public static class ListNode {

        int val = 0;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static ListNode Merge_Two_Sorted_Lists(ListNode head1, ListNode head2) {

        if (head1 == null || head2 == null) {
            return head1 == null ? head2 : head1;
        }

        ListNode curr1 = head1;
        ListNode curr2 = head2;

        ListNode prev = new ListNode(-1), dp = prev;

        while (curr1 != null && curr2 != null) {

            if (curr1.val <= curr2.val) {
                prev.next = curr1;
                curr1 = curr1.next;
            } else {
                prev.next = curr2;
                curr2 = curr2.next;
            }
            prev = prev.next;
        }

        prev.next = curr1 == null ? curr2 : curr1;

        ListNode head = dp.next;
        dp.next = null;
        return head;
    }

    public static ListNode middle_node_LinkedList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    public static ListNode mergeSort(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode mid = middle_node_LinkedList(head);
        ListNode nHead = mid.next;
        mid.next = null;

        ListNode left = mergeSort(head);
        ListNode right = mergeSort(nHead);

        return Merge_Two_Sorted_Lists(left, right);
    }

    public static void main(String[] args) {

    }
}