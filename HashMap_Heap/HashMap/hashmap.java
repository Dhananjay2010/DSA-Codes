import java.util.ArrayList;

public class hashmap {

    // Series to write the function of hashmap.
    // 1. hashcode
    // 2. group
    // 3. containsKey
    // 4. get
    // 5. getOrDefault
    // 6. keySet
    // 7. putIfAbsent
    // 8. put
    // 9. rehash
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
                this.head = node.next;
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

    private linkedList[] containers;
    private int sizeOfHM = 0;

    public void assignValues(int size) { // Initial function to create a container and new the linkedlist
        this.containers = new linkedList[size];

        for (int i = 0; i < size; i++) {
            containers[i] = new linkedList();
        }
    }

    hashmap() {
        assignValues(10);
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int count = 0;
        for (int i = 0; i < this.containers.length; i++) {
            linkedList group = this.containers[i];
            int size = group.size();

            while (size-- > 0) {
                Node node = group.removeFirst();
                sb.append("{" + node.key + "=" + node.value + "}");
                count++;

                if (count != sizeOfHM)
                    sb.append(",");
                group.addLast(node);
            }
        }

        sb.append("]");
        return sb.toString();
    }

    private void rehash() {
        linkedList[] backup = this.containers; // For backup of current hashmap.
        assignValues(2 * containers.length); // Newed the hashmap with twice the size.

        for (int i = 0; i < backup.length; i++) { // getting all the keys from previous hashmap and then assigning it to
                                                  // new one.
            linkedList group = backup[i];
            int size = group.size();

            while (size-- > 0) {
                Node node = group.removeFirst();
                put(node.key, node.value); // Adding keys to new hashmap.
            }
        }

    }

    public void put(Integer key, Integer value) {
        boolean hasKey = containsKey(key);
        linkedList group = group(key);
        if (hasKey)
            group.head.value = value;
        else {
            group.addLast(new Node(key, value));
            this.sizeOfHM++;

            // agar mere group ka size jyada increase ho gya to usme search karna efficient
            // nhi rahega. Usko kehte hain ki number of collission increase ho jate hain.
            // Hence hashmap ki complexity increase ho jati hai.
            // To jaise he group ka size kisi value lamda se bada hota hai, pure hashmap ko
            // rehash kar dete hain.
            // rehash karne se sare elements ka dubara hashcode generate hota hai and hence
            // har key dubara alag alag group mai jake assign hoti hai. Issse number of
            // groups increase ho jate hain and number of elements in a group reduce ho jate
            // hain, hence search efficient ho jata hai.

            double lamda = (group.size() / this.containers.length * 1.0);
            if (lamda > 0.725)
                rehash();
        }
    }

    public void putIfAbsent(Integer key, Integer defaultValue) {
        boolean hasKey = containsKey(key);
        if (!hasKey)
            put(key, defaultValue);
    }

    public ArrayList<Integer> keySet() {

        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < this.containers.length; i++) {
            linkedList group = containers[i];
            int size = group.size();
            while (size-- > 0) {
                Node node = group.removeFirst();
                ans.add(node.key);
                group.addLast(node);
            }
        }
        return ans;
    }

    public Integer remove(Integer key) {
        boolean hasKey = containsKey(key);
        linkedList group = group(key);
        if (!hasKey)
            return null;

        Node node = group.removeFirst();
        this.sizeOfHM--;
        return node.key;
    }

    public Integer getOrDefault(Integer key, Integer defaultValue) {
        // Agar key hai to uski value nhi hai to default value ko return karo.
        Integer value = get(key);
        return value == null ? defaultValue : value;
    }

    public Integer get(Integer key) {
        boolean hasKey = containsKey(key);
        // Jab humne contains key run kiya hoga aur key present hogi to wo ab head ban
        // chuki hogi kyunki hum contains key mai addLast removeFirst kar rahe hain
        // jisse searched key sabse top mai hogi.
        linkedList group = group(key);

        return hasKey ? group.head.value : null;
    }

    public boolean containsKey(Integer key) {
        linkedList group = group(key);

        int size = group.size();

        while (size-- > 0) {
            if (key == group.getFirst())
                return true;

            group.addLast(group.removeFirst());
        }
        return false;
    }

    private linkedList group(Integer key) {
        int code = hashcode(key);
        return this.containers[code];

    }

    private int hashcode(Integer key) {
        int value = key.hashCode();
        return value % this.containers.length;
    }

}
