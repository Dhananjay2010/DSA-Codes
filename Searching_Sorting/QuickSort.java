public class QuickSort {

    public void swap(int[] arr, int p1, int p2) {
        int k = arr[p1];
        arr[p1] = arr[p2];
        arr[p2] = k;
    }

    public int segregateData(int[] arr, int si, int ei, int pivot) {
        // This function will place the pivot in the correct position in the array and
        // will return the correct postion of the pivot element as if the array was
        // sorted.

        // This is same like segregate 0 and 1. We define our region.
        // (0,p) ∈ {values <= data}
        // (p+1, itr -1) ∈ {values > data}
        // (itr, n-1) ∈ ∞ (unexplored region)

        swap(arr, ei, pivot); // Swapped with the end element since we want to pivot element to be in right
                              // place as if the array was sorted
        int itr = si, p = si - 1;
        while (itr <= ei) {
            if (arr[itr] <= arr[ei])
                swap(arr, ++p, itr);
            itr++;
        }
        return p;
    }

    public boolean isArraySorted(int[] arr, int si, int ei) { // To check if the array is sorted or not.
        int prev = si, curr = si + 1;
        boolean isSorted = true;
        while (curr <= ei) {
            if (arr[prev] >= arr[curr]) {
                isSorted = false;
                break;
            }
            prev++;
            curr++;
        }
        return isSorted;
    }

    // # Simple faith rakkha.
    // # Maine bola ki mai pivot element ko uske shi index pe leke aa jaunga. Ab
    // # agar jo baki bacha hua array hai wo apne aap ko sort karke le aaye to mai
    // # pura sort ho jaaunga.

    public void quickSort(int[] arr, int si, int ei) {
        if (si > ei)
            return;

        if (isArraySorted(arr, si, ei)) // If the array is already sorted no need to sort it.
            return;

        int pivot = ei;
        int pIdx = segregateData(arr, si, ei, pivot);

        quickSort(arr, si, pIdx - 1);
        quickSort(arr, pIdx + 1, ei);
    }

    public int[] sortArray(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    // ! Time complexity :

    // Generally if the division of array is equal the quick sort recursive call,
    // the comlexity is nlog(n)

    // If the array is decreasing, the time complexity is O(n^2);
}