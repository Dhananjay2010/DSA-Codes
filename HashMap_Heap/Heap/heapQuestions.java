import java.util.PriorityQueue;

public class heapQuestions {

    // b <================ Kth smallest element ==============>
    // https://practice.geeksforgeeks.org/problems/kth-smallest-element5635/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // Simple k size ka priority queue maintain karna hai aur mai usko fill karte
    // gaya. Jaise he greater hua size k se , mai priority queue se element nikalta
    // gaya. Hence loop ke end mai k elements hogne priority Queue mai.

    // # In java, the default behaviour of priority Queue is of min type.

    // ! O(Nlog(k))
    public static int kthSmallest(int[] arr, int l, int r, int k) {
        // Your code here

        // # max Priority Queue.
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
            return b - a;
        });

        while (l <= r) {
            int val = arr[l];
            pq.add(val);

            if (pq.size() > k)
                pq.remove();
            l++;
        }

        return pq.peek();

    }

    // ! O(n + klog(n)) using downheapify, which is faster than above.

    public static boolean compareTo(int[] arr, int x, int y, boolean isMax) {

        return isMax ? arr[x] > arr[y] : arr[y] > arr[x];
    }

    public static void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

    public static void downHeapify(int pi, int li, boolean isMax, int[] arr) {

        int lci = 2 * pi + 1;
        int rci = 2 * pi + 2;
        int maxIdx = pi;

        if (lci <= li && compareTo(arr, lci, maxIdx, isMax))
            maxIdx = lci;
        if (rci <= li && compareTo(arr, rci, maxIdx, isMax))
            maxIdx = rci;

        if (pi != maxIdx) {
            swap(arr, pi, maxIdx);
            downHeapify(maxIdx, li, isMax, arr);
        }
    }

    public static int kthSmallest_Optimized(int[] arr, int l, int r, int k) {

        boolean isMax = false;

        int li = arr.length - 1, n = arr.length;
        for (int i = li; i >= 0; i--) {
            downHeapify(i, li, isMax, arr);
        }

        // Pehle pure array ko min heap mai convert kiya.
        // Ab whi jaise hum heap sort likhte hain waise he kiya.
        // is bar jo sabse chote element hoga wo top pe hoga kyunki min heap hai.
        // ab k times humne element ko last wale se swap kiya. To end mai array ke [n-k]
        // position pe hume kth smallest element mil jayega.

        int K = k;

        while (li >= 0 && K-- > 0) {
            swap(arr, 0, li);
            li--;
            downHeapify(0, li, isMax, arr);
        }

        return arr[n - k];
    }

    // b<============= 215. Kth Largest Element in an Array ===========>
    // https://leetcode.com/problems/kth-largest-element-in-an-array/description/

    // # For kth largest, just change the isMax value to true.

    public int findKthLargest(int[] arr, int k) {
        boolean isMax = true;

        int li = arr.length - 1, n = arr.length;
        for (int i = li; i >= 0; i--) {
            downHeapify(i, li, isMax, arr);
        }

        int K = k;

        while (li >= 0 && K-- > 0) {
            swap(arr, 0, li);
            li--;
            downHeapify(0, li, isMax, arr);
        }

        return arr[n - k];
    }

    // B <================703. Kth Largest Element in a Stream =========>
    // https://leetcode.com/problems/kth-largest-element-in-a-stream/description/

    // # Same Priority Queue banayi min type ki aur uska size K maintain kiya aur
    // # har baar top ka element return karte gaye.

    class KthLargest {

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int K;

        public KthLargest(int k, int[] nums) {
            K = k;
            for (int ele : nums) {
                pq.add(ele);
                if (pq.size() > K)
                    pq.remove();
            }
        }

        public int add(int ele) {
            pq.add(ele);
            if (pq.size() > K)
                pq.remove();

            return pq.peek();
        }
    }

    // b <==============378. Kth Smallest Element in a Sorted Matrix===>
    // https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/description/

    // # Here what we are doing is traversing the array in the sorted order.

    // Humne har ek row ke first element ko priority queue mai dala.
    // Ab jaise he kisi element ko remove kiya, to jo element bahar aaya uska next
    // element in the same row ko add kar diya.
    // Aisa karne se kya hua ki hum sorted order mai matrix ko traverse kar payenge.
    // Since hum element ko priority queue mai dal rahe hain.

    // # To rather than using pair, we are smartly storing the 1-D inedx in the
    // # priority queue and we are removing element from it in ascending order.

    // Now we can get the 2-D index from 1-D index hence we can access the element
    // value stored at the matrix in the particular row and column.

    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length, m = matrix[0].length;
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
            int i1 = a / m, j1 = a % m;
            int i2 = b / m, j2 = b % m;

            return matrix[i1][j1] - matrix[i2][j2];
        });

        for (int i = 0; i < n; i++)
            pq.add(i * m + 0);

        int r = 0, c = 0;
        while (k-- > 0) {
            int idx = pq.remove();
            r = idx / m;
            c = idx % m;
            if (c + 1 < m)
                pq.add(r * m + c + 1);
        }

        return matrix[r][c];
    }

}
