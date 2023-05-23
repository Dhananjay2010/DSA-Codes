public class hashmap {

    private class Node {

        private Integer key;
        private Integer value;
        private Node next;

        Node() {

        }

        Node(Integer key, Integer value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        Node(Node node) {
            this.key = node.key;
            this.value = node.value;
            this.next = node.next;
        }
    }

    private class linkedList {

        public Node head;
        public Node tail;
        public int numberOfElements;

        linkedList() {

            this.head = null;
            this.tail = null;
            this.numberOfElements = 0;
        }

        linkedList(Node head, Node tail) {
            this.head = head;
            this.tail = tail;
        }

        public int size() {
            return this.numberOfElements;
        }

        public int getFirst() {
            return this.head.key;
        }

        public Node removeFirst() {
            Node node = this.head;

            if (this.numberOfElements == 0)
                this.head = this.tail = null;
            else {
                this.head.next = node.next;
                node.next = null;
            }

            this.numberOfElements--;
            return node;
        }

        public void addLast(Node node) {
            if (this.head == null) {
                this.head = this.tail = node;
            } else {
                this.tail.next = node;
                this.tail = node;
            }
            this.numberOfElements++;
        }
    }

    
}
