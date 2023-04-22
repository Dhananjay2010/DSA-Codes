import java.util.ArrayList;

public class Heap {

    private ArrayList<Integer> arr;
    private boolean isMax = true;

    Heap(boolean isMax) {
        this.arr = new ArrayList<>();
        this.isMax = isMax;
    }

    // O(n + nLog(n)) === > O(n)
    // The actual complexity is O(n).
    // Theoritically, since the largest group of nodes that are at the bottom,
    // namely(2^H), did no work since a single node is always a heap.

    Heap(int[] arr, boolean isMax) { // To convert the given array in heap.
        this(isMax); // Calling the default constructor to new the arraylist.
        for (int ele : arr) // O(n)
            this.arr.add(ele);

        for (int i = this.arr.size() - 1; i >= 0; i--) {
            downHeapify(i);
        }
    }

    // O(1)
    public boolean compareTo(int x, int y) {
        // In java, by default the priority queue is of min type.
        if (isMax)
            return this.arr.get(x) > this.arr.get(y);
        else
            return this.arr.get(y) > this.arr.get(x);
    }

    // O(1)
    private void swap(int x, int y) {
        int v1 = this.arr.get(x);
        int v2 = this.arr.get(y);

        this.arr.set(x, v2);
        this.arr.set(y, v1);
    }

    // O(log(n))
    private void downHeapify(int pi) {
        // since my left and right child, both are heap, therefore I have to just
        // compare with the 3 elements. Parent Index, left child index and right child
        // index.

        // finding the max among these three and the largest index parent index.
        int lci = 2 * pi + 1;
        int rci = 2 * pi + 2;
        int maxIdx = pi;

        if (lci < this.arr.size() && this.compareTo(lci, maxIdx))
            maxIdx = lci;
        if (rci < this.arr.size() && this.compareTo(rci, maxIdx))
            maxIdx = rci;

        if (pi != maxIdx) {
            this.swap(pi, maxIdx);
            // Ab kyunki maine swap kiya hai parent index and maxIndex ko.
            // Swap isiliye hua hai kyunki parent index value was smaller than maxindex
            // value.
            // Ab kyunki jo pehle maxIndex ka subtree tha, uska head ab parent index hai, to
            // ho sakta hai ki swapping ke baad ab wo subtree heap na rahe. Kyunki pi ki
            // value smaller thi max index se. To isiliye maxindex ko dubara heap mai
            // convert karne ke liye, downHeapify chalaya.
            downHeapify(maxIdx);
        }
    }

    // O(log(n))
    public int remove() { // # To remove the top element of the heap.
        // To remove the maxHeap element, since it is placed at the top of the tree. i.e
        // at 0 index, therefore deleting the first element in the arraylist would
        // creating shifting and that's complexity will be O(n). Therefore, what we do
        // is swap the first element with the last element and then delete the last
        // element. Now since last element was smaller and now it is placed at the apex
        // of the tree, now downHeapify has to be called to make the whole tree heap
        // again.

        int re = this.arr.get(0);
        this.swap(re, this.arr.size() - 1);
        this.arr.remove(this.arr.size() - 1);
        downHeapify(0);
        return re;
    }

    // O(log(n))
    private void upHeapify(int ci) { // ci is the child index, pi is the parent index
        int pi = (ci - 1) / 2;
        if (pi >= 0 && this.compareTo(ci, pi)) {
            this.swap(ci, pi);
            upHeapify(pi);
        }
    }

    // O(log(n))
    public void add(int data) {
        // Maine end mai data ko add kiya. Ab agar mai maxHeap bana raha hun, to maine
        // nye element ka parent index nikala. Ab agar parent mujhse chota aata hai, to
        // maine swap kiya aur maine nye parent ke liye dubara Same kaam kiya kyunki
        // aisa ho sakta hai ki jo nya element add hua hai wo bada ho apne nye parent
        // se. To mai upheapify call karta gaya jab tak nya element apni shi jagah pe
        // nhi pahunch jata aur pura arraylist dubara maxheap nhi ban jata.

        // Same iski complexity bhi log(n) he hogi since ye bhi max height of the whole
        // tree tak he travel kar payega.
        this.arr.add(data);
        upHeapify(this.arr.size() - 1);
    }

    // O(1)
    public int peek() {
        return this.arr.get(0);
    }

    public int size() {
        return this.arr.size();
    }

}