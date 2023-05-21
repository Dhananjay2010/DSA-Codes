import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

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

    // b <=========347. Top K Frequent Elements =============>
    // https://leetcode.com/problems/top-k-frequent-elements/description/

    // # Frequency nikalke map mai store kiya
    // # Then Priority queue ko aise min frequency wala banaya taki sabse kam
    // # frequency wala element sabse pehle bahar nikle.
    // # Ab bas 2 size ke array ko insert kiya priority queue mai aur pq ka size k
    // # fix kara. Jaise he size k se bada hoga, hum element ko remove karte
    // # rahenge. End mai k he elements bachenge queue mai. Unko array mai dalke
    // # return kar denge.

    // ! Time complexity is O(n + nlog(k)) == > O(nlog(k))

    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int ele : nums) {
            if (map.containsKey(ele))
                map.put(ele, map.get(ele) + 1);
            else
                map.put(ele, 1);
        }
        // {ele, freq}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            return a[1] - b[1];
        });

        for (Integer key : map.keySet()) {
            pq.add(new int[] { key, map.get(key) });
            if (pq.size() > k)
                pq.remove();
        }

        int[] ans = new int[k];
        while (k-- > 0) {
            int[] kams = pq.remove();
            ans[k] = kams[0];
        }
        return ans;
    }

    // b <============= 451. Sort Characters By Frequency ===========>
    // https://leetcode.com/problems/sort-characters-by-frequency/description/

    // # Same as above pe yahan pe jiski sabse max frequency hai, use pehle bahar
    // # nikala.

    public String frequencySort(String str) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            int ele = str.charAt(i - 'a'); // 'b' - 'a'= 1, and so on...
            if (map.containsKey(ele))
                map.put(ele, map.get(ele) + 1);
            else
                map.put(ele, 1);
        }
        // {ele, freq}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            return b[1] - a[1];
        });

        for (Integer key : map.keySet()) {
            pq.add(new int[] { key, map.get(key) });
        }

        StringBuilder sb = new StringBuilder();
        while (pq.size() != 0) {
            int[] rn = pq.remove();
            char ch = (char) (rn[0] + 'a');
            for (int i = 0; i < rn[1]; i++)
                sb.append(ch);
        }

        return sb.toString();
    }

    // b <===========973. K Closest Points to Origin ============>
    // https://leetcode.com/problems/k-closest-points-to-origin/description/

    // # The pair with the largest distance from origin is taken out first.

    public int[][] kClosest(int[][] points, int k) {

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            int d1 = (a[0] * a[0]) + (a[1] * a[1]);
            int d2 = (b[0] * b[0]) + (b[1] * b[1]);
            return d2 - d1;
        });

        for (int[] point : points) {
            pq.add(point);
            if (pq.size() > k)
                pq.remove();
        }
        // At the end of above loop, pq will have only k elements.

        int[][] ans = new int[k][2];
        while (k-- > 0)
            ans[k] = pq.remove();
        return ans;
    }

    // # In the above scenario, we have used extra space of int[] in priority queue.
    // # Rather we should have stored index in the priority queue since we can
    // # access points array anytime from index.

    public int[][] kClosest_(int[][] points, int k) {

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
            int d1 = (points[a][0] * points[a][0]) + (points[a][1] * points[a][1]);
            int d2 = (points[b][0] * points[b][0]) + (points[b][1] * points[b][1]);

            return d2 - d1;
        });

        for (int i = 0; i < points.length; i++) {
            pq.add(i);
            if (pq.size() > k)
                pq.remove();
        }

        int[][] ans = new int[k][2];
        while (k-- > 0)
            ans[k] = points[pq.remove()];
        return ans;
    }

    // b <============ 692. Top K Frequent Words ============>
    // https://leetcode.com/problems/top-k-frequent-words/

    // ! Good question
    // # Dry run properly ans you will get the answer.
    public List<String> topKFrequent(String[] words, int k) {

        HashMap<String, Integer> map = new HashMap<>();
        for (String s : words)
            map.put(s, map.getOrDefault(s, 0) + 1);

        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> {
            if (map.get(a) == map.get(b))
                return b.compareTo(a); // I want the string that is higher lexographically to get removed first. For ex
                                       // : in "coding" and "leetcode", I want "leetcode" to get removed first
            return map.get(a) - map.get(b); // I want letter with less frequency to get removed first.
        });

        for (String s : map.keySet()) {
            pq.add(s);
            if (pq.size() > k)
                pq.remove();
        }

        List<String> ans = new LinkedList<>(); // Since the one with higher frequency is asked to be placed first and
                                               // our pq is of min type, therefore we have to add first to get the
                                               // required answer.
        while (k-- > 0) {
            ans.add(0, pq.remove());
        }

        return ans;
    }

    // b <============ Swim in rising Water ===============>
    // https://leetcode.com/problems/swim-in-rising-water/

    // # Jo max element source aur destination ke beech aayega, whi mera max time
    // # hoga.

    // # Since hum element ko priority queue ke andar dal rahe hain, to hume pata
    // # hai pehle sabse chota element he bahar aayega. Hence sabse pehle kam time
    // # wale he traverse honge. Hence min time he milega.

    // # To source se destination jane ke liye simple Bfs run kiya hai Priority
    // # Queue ke sath.

    public int swimInWater(int[][] grid) {

        int n = grid.length, m = n;
        int[][] dir = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
            int i1 = a / m, j1 = a % m;
            int i2 = b / m, j2 = b % m;

            return grid[i1][j1] - grid[i2][j2]; // min element will come out first.
        });

        boolean[][] vis = new boolean[n][m];
        pq.add(0);
        vis[0][0] = true;

        int maxHeight = 0;
        while (pq.size() != 0) {
            int idx = pq.remove();
            int sr = idx / m, sc = idx % m;
            int height = grid[sr][sc];

            maxHeight = Math.max(maxHeight, height);

            if (sr == n - 1 && sc == n - 1)
                break;

            for (int d = 0; d < dir.length; d++) {
                int r = sr + dir[d][0];
                int c = sc + dir[d][1];

                if (r >= 0 && c >= 0 && r < n && c < m && !vis[r][c]) {
                    vis[r][c] = true;
                    pq.add(r * m + c);
                }
            }
        }

        return maxHeight;
    }

    // b <===========1642. Furthest Building You Can Reach======>
    // https://leetcode.com/problems/furthest-building-you-can-reach/description/

    // ! A very good question

    // # Trick ye thi hume chote heights ke liye brick use karni thi aur badi
    // # heights ke liye ladder.

    // To humne min size ka priority queue rakha aur choti heights ke liye bricks
    // lagate gaye.

    // Per jaise he priority queue ka size bada hua number of ladders se, to hume
    // remove kara elements ko aur min ka bahar nikala aur uske liye bricks use
    // kiya.. To hum chah rahe hain ki sabse badi value end mai ladder ke liye bachi
    // rahe. Ab aagr brick kar count -ve hua, iska matlab ye hoga ki ab sirf hum
    // ladder he use kar sakte hain. Or humare priority queue mai utne he elements
    // ` bache honge jitne ladder honge. Hence whi humara answer hoga.

    public int furthestBuilding(int[] heights, int bricks, int ladders) {

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int n = heights.length;
        for (int i = 1; i < heights.length; i++) {
            int diff = heights[i] - heights[i - 1];
            if (diff > 0) {
                pq.add(diff);
                if (pq.size() > ladders) {
                    bricks -= pq.remove();
                }
                if (bricks < 0)
                    return i - 1;
            }
        }

        return n - 1;
    }

    // b <============632. Smallest Range Covering Elements from K Lists==>
    // https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/description/

    // Yahan pe basically logic ye hai ki kyunki list hume sorted di hai, therefore
    // hum ab puri matrix ko sorted travel karenge.

    // Matrix mai jitni row hogi utna he priority queue ka size rakhenge aur min
    // type ka priority queue banayenge. Taki sabse small element pehle bahar nikle
    // and pura matrix sorted order mai traverse ho.

    // # n is the rows in the matrix
    // Ab har time n elements honge pq mai. To us mai min aur max maintain karke
    // apni smallest range return kardenge.

    // min to hume mil jayega jab hum pq se element nikalenge.
    // max hume tab milega jab hum elements ko dalenge pq mai.

    // # Rest is dry run and figure out.

    public int[] smallestRange(List<List<Integer>> nums) {
        // row, column
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            int r1 = a[0], c1 = a[1];
            int r2 = b[0], c2 = b[1];
            return nums.get(r1).get(c1) - nums.get(r2).get(c2);
        });

        int n = nums.size();
        int max = -(int) 1e9, range = (int) 1e9, gmax = -(int) 1e9, gmin = (int) 1e9;
        for (int i = 0; i < n; i++) {
            pq.add(new int[] { i, 0 });
            max = Math.max(max, nums.get(i).get(0));
        }

        while (true) {
            int[] rn = pq.remove();
            int sr = rn[0], sc = rn[1];
            int val = nums.get(sr).get(sc);

            if (max - val < range) {
                range = max - val;
                gmin = val;
                gmax = max;
            }

            int colSize = nums.get(sr).size();
            if (sc + 1 >= colSize) // Jaise he humne sare list ke sare elements ko use kar liya, ab aage aur koi
                                   // nhi range nhi milegi jisme har row ka kam se kam ek element aaye
                break;
            pq.add(new int[] { sr, sc + 1 });
            int addVal = nums.get(sr).get(sc + 1);
            if (addVal > max) // Agar jis element ko dala, wo already max element jo pq mai hai usse bada hai
                              // to max ko update karo.
                max = addVal;
        }

        return new int[] { gmin, gmax };
    }

    // ! Bhaiya Code

    public int[] smallestRange_(List<List<Integer>> nums) {

        int n = nums.size();

        // {r,c} // {ele, r,c};
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> { // priority_queue<vector<int>,vector<vector<int>>,greater<vector<int>>>
                                                                  // pq;
            int r1 = a[0], c1 = a[1];
            int r2 = b[0], c2 = b[1];

            return nums.get(r1).get(c1) - nums.get(r2).get(c2);

        });

        int maxValue = -(int) 1e9;

        for (int i = 0; i < n; i++) {
            pq.add(new int[] { i, 0 });
            maxValue = Math.max(maxValue, nums.get(i).get(0));
        }

        int range = (int) 1e9, sp = -1, ep = -1;
        while (pq.size() == n) {

            int[] re = pq.remove();
            int r = re[0], c = re[1], val = nums.get(r).get(c);

            if (maxValue - val < range) {
                range = maxValue - val;
                sp = val;
                ep = maxValue;
            }

            c++;
            if (c < nums.get(r).size()) {
                pq.add(new int[] { r, c });
                maxValue = Math.max(maxValue, nums.get(r).get(c));
            }
        }

        return new int[] { sp, ep };
    }

    // b <===========295. Find Median from Data Stream ===============>
    // https://leetcode.com/problems/find-median-from-data-stream/description/

    // Consider that we have to find median. Now to find median, we need to have two
    // ranges, left range and right range. Now median will be either in left range
    // or in right range or in between two.

    // We need to know the max of the left range and the min of right range.
    // The only DS that will help us is the priority queue.

    // We will have two priority queue. Min and Max.

    // We are considering the median to be left oriented. Means that either the
    // median will lie in the left range(left range max) or in between the left max
    // or right min(for even number values.)

    // Now to imagine what is hapenning, draw a number line and start adding numbers
    // to it. also start marking the median changes that happen when you add the
    // numbers.

    // So values less than equal to median, goes to maxPriority queue.
    // Values greater than median, goes to minPriority queue.

    // Now to make sure that the values less than equal to median lies in maxPq, so
    // shifting has to be done.

    // Adding the element greater than median shifts the median to the right.
    // Adding the element less than median shifts the median to the left.

    // In case we start adding (1,2,3,4,10) in sequence, we find that median
    // shifting occurs so one element has to be moved to max priority queue from
    // min pq.

    // Now if we add (-3,-4) to the same sequence, now the median shift to the left
    // and one element has to be monved to min priority queue from max priority
    // queue.

    // # If all integer numbers from the stream are between 0 and 100, how would you
    // # optimize it?

    // ` bucket sort, create an array of bucket of length 101, keep the count of
    // numbers in each bucket, and the count of overall numbers, then it's easy to
    // locate the bucket where the median number resides and find the median by
    // looping through the array-> O(1)

    class MedianFinder {

        private PriorityQueue<Integer> maxpq;
        private PriorityQueue<Integer> minpq;

        public MedianFinder() {
            maxpq = new PriorityQueue<>((a, b) -> { // initializing max priority queue.
                return b - a;
            });

            minpq = new PriorityQueue<>();
        }

        public void addNum(int num) {
            if (maxpq.size() == 0 || num <= maxpq.peek())
                maxpq.add(num);
            else
                minpq.add(num);

            if (maxpq.size() - minpq.size() == -1)
                maxpq.add(minpq.remove());
            if (maxpq.size() - minpq.size() == 2)
                minpq.add(maxpq.remove());

        }

        public double findMedian() {
            if (maxpq.size() == minpq.size())
                return (maxpq.peek() + minpq.peek()) / 2.0;
            else
                return maxpq.peek() * 1.0;
        }
    }

    // b <============== 480. Sliding Window Median =============>
    // https://leetcode.com/problems/sliding-window-median/description/

    // Logic same sa above.
    // Ab hume element ko remove nbhi karna hai, to isliye tree set ka use kiya
    // kyunki treeset mai removal logN ka hota hai. In Priority Queue mai removal of
    // a specific element n + Logn ka hota hai.

    // N element ko dhundhne ke liye aur logN removal ke liye.

    // Kyunki hum tree set use kar rahe hain aur humare array mai duplicates values
    // ho sakti hai, to hum index ko dalenge treeset mai.

    // Baki pura logic same hai uper wale ki tarah.

    public double[] medianSlidingWindow(int[] nums, int k) {
        int len = nums.length;
        double[] result = new double[len - k + 1];
        if (k == 1) {
            for (int i = 0; i < len; i++) {
                result[i] = (double) nums[i];
            }
            return result;
        }

        // Wrote this comparator since -2^31 <= nums[i] <= 2^31 - 1. Hence so comparison
        // are of higher number.
        Comparator<Integer> comparator = (a,
                b) -> (nums[a] != nums[b] ? Integer.compare(nums[a], nums[b]) : Integer.compare(a, b));
        TreeSet<Integer> minSet = new TreeSet<>(comparator);
        TreeSet<Integer> maxSet = new TreeSet<>(comparator.reversed());
        int n = nums.length;
        double[] ans = new double[n - k + 1];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (maxSet.size() + minSet.size() == k)
                remove(minSet, maxSet, i - k, k, nums);

            addNumber(minSet, maxSet, i, k, nums);
            if (i >= k - 1)
                ans[j++] = getMedian(minSet, maxSet, nums);
        }
        return ans;
    }

    private static void addNumber(TreeSet<Integer> minSet, TreeSet<Integer> maxSet, int idx, int k, int[] nums) {
        if (idx == 0 || nums[idx] <= nums[maxSet.first()])
            maxSet.add(idx);
        else
            minSet.add(idx);

        if (maxSet.size() - minSet.size() == 2)
            minSet.add(maxSet.pollFirst());
        if (maxSet.size() - minSet.size() == -1)
            maxSet.add(minSet.pollFirst());
    }

    private static void remove(TreeSet<Integer> minSet, TreeSet<Integer> maxSet, int idx, int k, int[] nums) {
        if (nums[idx] <= nums[maxSet.first()])
            maxSet.remove(idx);
        else
            minSet.remove(idx);

        if (maxSet.size() - minSet.size() == 2)
            minSet.add(maxSet.pollFirst());
        if (maxSet.size() - minSet.size() == -1)
            maxSet.add(minSet.pollFirst());
    }

    private static double getMedian(TreeSet<Integer> minSet, TreeSet<Integer> maxSet, int[] nums) {
        if (minSet.size() == maxSet.size())
            return ((double) nums[minSet.first()] + (double) nums[maxSet.first()]) / 2.0;
        else
            return nums[maxSet.first()] * 1.0;
    }
}
