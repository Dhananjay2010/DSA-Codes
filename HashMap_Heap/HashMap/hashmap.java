import java.util.ArrayList;

public class hashmap {

    // Consider a circumstance we have a given array with 10th strings in it. We
    // want to know if a particular string is present in that array or not for that
    // what we will have to do if we loop through the array and for every element in
    // the array check if that match to the string required or not now this time
    // complexity will you be off n no consider this array fill with one lakh
    // elements and to search in that array will be a very tedius task so what we do
    // this instead of putting those elements and array before them in a hashmap so
    // what hashmap does this it groups the elements what grouping means is that it
    // is going to divide all the elements to certain groups according to a formula
    // decided by us.

    // So when we try to add an element in a hash map what happens is it as that
    // element to a particular group and instead of searching in the whole array or
    // the whole hash map research the element in that particular group which it
    // would have existed or would have been present. so it optimises the search in
    // a very efficient way.

    // So now what to do is to create a hash map we take an array of in starting a
    // fixed size each index of that array has a group represented in the form of a
    // linked list now when we talk about link list each link list has very number
    // of nodes and since hashmap is a key value pair therefore each node of that
    // link list will have key and value and next that will store the address of the
    // next node.

    // For now, since we are creating an integer versus integer hash map so we need
    // something to identify what group a specific element or key belongs to do that
    // what we do we take a hashing function and apply it to our key. And since we
    // only have containers dot length number of groups so to find the group what we
    // do with be modulus it by the containers. length hence it will only give us
    // the groups between zero and containers. length -1.

    // Now once we have found the group and we know that each group is a linked list
    // we will add that key to that linked list by using the add last function of
    // the linked list. hence in this way, a hashmap is formed.

    // # Why rehashing ??

    // Now what if my group size starts to increase so now each group will have more
    // elements now more elements mean the searching in that group will not be
    // optimised any again we are stuck in the same situation as an array that we
    // have to iterate through the whole array to in to find the element so what we
    // have to do is whenever our number of elements in a group exceeds a certain
    // amount that we decide what we do is we rehash the whole hash map.

    // Now what hashing means is to increase the length of the container so that the
    // number of groups in the hashmap increases and again iterate to all the keys
    // present in that hashmap that was earlier present and assign them to new
    // groups now this is a computer process which takes time, therefore, is not
    // always a fine solution we should use it but carefully

    // The number of increasing elements in a group is also known as collisions so
    // as the number of elements in a group increases the collision increases to
    // reduce the collisions in that group will hash the whole hash map so that the
    // number of elements in a group reduces and hands the collision reduces and
    // hence the searching time in that group decreases hence optimising the
    // searching time.

    // # Series to write the function of hashmap.
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

    // # Hashmap of string is a very costly combination.
    // Since jab bhi contains ya get likhte hain, tab hashcode nikalna padta hains
    // string ka, joki costly operation hota hai.

    // # Primitive data type ka hashcode generate nhi hota. ISiliye Integer use kiya
    // # hai.

}
