import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class LeetcodeContest {

    private class TreeNode {

        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        TreeNode(int val) {
            this(val, null, null);
        }
    }

    // b<===== 2457. Minimum Addition to Make Integer Beautiful =============>
    // https://leetcode.com/problems/minimum-addition-to-make-integer-beautiful/

    // # Logic : if we want a smaller digit sum we have to visit its nearest tens ,
    // # hundreds , thousands and so on and check digit sum at those places, because
    // # we can obtain lesser digit sum at nearest distance at these places only.

    public int digitSum(long n) { // Simple function to calculate the sum
        int sum = 0;
        while (n != 0) {
            sum += n % 10;
            n = n / 10;
        }
        return sum;
    }

    public long makeIntegerBeautiful(long n, int target) {

        long x = n;
        int i = 0; // To calculate the number of iteration that the original number has been
                   // divided by 10.
        while (digitSum(x) > target) {
            // Dividing by 10, reduces 467 to 46.
            // Added + 1 to make it to 47 since the nearest ten for 467 is 470.
            // Then in second iteration, 47 reduces to 4.
            // + 1 makes it 5, since the nearest ten for 46 is 50.
            x = x / 10 + 1;
            i++;
        }
        // Multiplied with Math.pow to get the new number back to the original number of
        // digits.
        // If the initial n is 467, the by the end of while loop, the x will be 5 .
        // So to get the answer, we have to multiply 5 by hundered and subtract it with
        // 467.
        return (long) (x * Math.pow(10, i) - n);
    }

    // b<======Minimum Number of Operations to Sort a Binary Tree by Level=======>
    // https://leetcode.com/problems/minimum-number-of-operations-to-sort-a-binary-tree-by-level/

    // Basically the Problem has been reduced to sort the array with min swaps.

    // What we did first stored the original (index,value) pair in array and sorted
    // it value wise, so that we know the sorted position of values.

    private class pair implements Comparable<pair> {

        int index = -1;
        int value = 0;

        pair(int index, int value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(pair p) {
            return this.value - p.value;
        }
    }

    // Consider it to a similar graph problem
    public int getMinSwapsToGetArraySorted(int[] arr) {
        int minSwaps = 0;

        pair[] indexMapArr = new pair[arr.length];
        for (int i = 0; i < arr.length; i++) { // created a index value pair array
            pair indexValue = new pair(i, arr[i]);
            indexMapArr[i] = indexValue;
        }

        // # Used visited so that once the node has been visited, we don't again start
        // # from it. Otherwise it may again get swapped and we will not get the right
        // # answer.

        Arrays.sort(indexMapArr);
        boolean[] vis = new boolean[indexMapArr.length];
        for (int i = 0; i < indexMapArr.length; i++) {
            pair indexValue = indexMapArr[i];
            int index = indexValue.index; // index before sorted
            if (vis[i] || i == index) // if the sorted index i and unsorted index is same, no need for swaps
                continue;

            vis[i] = true;
            int cycleLength = 0;
            while (i != index) { // To calculate the cycle length and marking the used index.
                vis[index] = true;
                pair nextIndexVal = indexMapArr[index];
                index = nextIndexVal.index;
                cycleLength++;
            }
            minSwaps += cycleLength;
            // Just added cycle length since we do not count the last edge to make a cycle
            // in cycle length.
        }

        return minSwaps;
    }

    public int minimumOperations(TreeNode root) {

        if (root == null || (root.left == null && root.right == null))
            return 0;
        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);
        int level = 0;
        int totalOperation = 0;
        while (que.size() != 0) {
            int size = que.size();
            ArrayList<Integer> myAns = new ArrayList<>();
            while (size-- > 0) {
                TreeNode rn = que.removeFirst();
                myAns.add(rn.val);

                if (rn.left != null)
                    que.addLast(rn.left);
                if (rn.right != null)
                    que.addLast(rn.right);
            }

            int[] arr = new int[myAns.size()];
            int i = 0;
            for (int e : myAns) {
                arr[i] = e;
                i++;
            }
            totalOperation += getMinSwapsToGetArraySorted(arr);
            level++;
        }

        return totalOperation;
    }
}
