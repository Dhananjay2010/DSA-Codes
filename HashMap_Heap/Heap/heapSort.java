public class heapSort {

    // ! Benifits of heap sort :

    // # 1. In place sorting hai.
    // # 2. Gives more flexiblity since by changing the value of increasing, I can
    // # have accending as well as decending array.
    // # 3. Easy to write since we only have to write the comparator.
    // # 4. O(nLog(n)) 

    public static boolean compareTo(int[] arr, int x, int y, boolean isIncreasing) {

        if (isIncreasing)
            return arr[x] > arr[y];
        else
            return arr[y] > arr[x];
    }

    public static void swap(int[] arr, int x, int y) {
        int v = arr[x];
        arr[x] = arr[y];
        arr[y] = v;
    }

    public static void downHeapify(int[] arr, int pi, int li, boolean isIncreasing) {
        int lci = 2 * pi + 1;
        int rci = 2 * pi + 2;
        int maxIdx = pi;

        if (lci <= li && compareTo(arr, lci, maxIdx, isIncreasing))
            maxIdx = lci;
        if (rci <= li && compareTo(arr, rci, maxIdx, isIncreasing))
            maxIdx = rci;

        if (pi != maxIdx) {
            swap(arr, pi, maxIdx);
            downHeapify(arr, maxIdx, li, isIncreasing);
        }
    }

    public static void main(String[] args) {
        int[] arr = { 10, 20, 30, -2, -3, -4, 5, 6, 7, 8, 9, 22, 11, 13, 14 };

        // Heap Sort ke liye simple kaam kiya.
        // Pehle pure array ko heap mai convert kiya. Usme hume lagega O(n) ki jaan.

        boolean isIncreasing = true;
        int li = arr.length - 1;

        for (int i = li; i >= 0; i--) { // converting the array into heap.
            downHeapify(arr, i, li, isIncreasing);
        }

        // Manle humne max heap banaya uper. Top sabse top pe jo element hoga wo pure
        // array ka max elemeny hoga. To agar mujhe array increasing chahiye, to sabse
        // max element ko sabse last mai hona chahiye. To iske liye humne last element
        // ke sath swap kiya. Usse sab max element last mai chala gaya. Ab kyun last
        // element jo tha wo chota raha hoga aur ab swapping ke baad sabse top pe hai,
        // to humne use element ke jo ab 0th index mai dubara downheapify call kiya. Per
        // ab jo maine range pass ki hai wo 0 se li-- ki hai. Aisa isiliye taki mera max
        // element end mai he rahe aur wo downheapify ka part na bane.

        // Downheapify karne se array dubara (0 se li-1) tak heap mai convert ho gaya
        // hoga with second max element at the top. Same steps repeat karenge. Aise
        // karte karte humara array sort ho jeyaga.

        while (li >= 0) {
            swap(arr, li, 0);
            li--;
            downHeapify(arr, 0, li, isIncreasing);
        }

        for (int ele : arr)
            System.out.println(ele + " ");
    }
}
