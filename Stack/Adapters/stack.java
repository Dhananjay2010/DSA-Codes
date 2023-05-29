public class stack {

    // Implementing dynamic stack using linkedlist.
    // Only two functions are needed. Add Last and RemoveFirst.

    private class Node {
        int data;
        Node next;

        Node() {
            this.data = (int) 1e9;
            this.next = null;
        }

        Node(int data) {
            this.data = data;
        }
    }

    private int size = 0;
    private Node head = null;
    private Node tail = null;

    public void display() {
        if (head == null)
            return;
        else {
            Node curr = this.head;
            while (curr != null) {
                System.out.print(curr.data + " ");
                curr = curr.next;
            }
        }
    }

    public static void stackUnderflow() throws Exception {
        throw new Exception("Stack is empty");
    }

    public void addFirst(Node node) {
        if (this.head == null) {
            this.head = this.tail = node;
        } else {
            node.next = this.head;
            this.head = node;
        }
        this.size++;
    }

    public Node removeFirst() throws Exception {
        Node node = this.head;
        if (this.head == null)
            stackUnderflow();
        else if (this.size == 1)
            this.head = this.tail = null;
        else {
            this.head = node.next;
            node.next = null;
        }

        this.size--;
        return node;
    }

    public int size() {
        return this.size;

    }

    public int peek() {
        return this.head.data;
    }

    public void push(int data) {
        this.addFirst(new Node(data));
    }

    public int pop() throws Exception {
        return this.removeFirst().data;
    }

}