public class l001 {

    // b <================ Binary Search ============>

    // # Always works on the sorted array.(Graph of array always increasing)
    // # If searching data is present in array, will find the element.
    // # If the searching data is not present in the array, will return the floor
    // # ceil value in the array

    public static int binarySearch(int[] arr, int data) {

        int n = arr.length;
        int si = 0, ei = n - 1;

        while (si <= ei) {
            int mid = (si + ei) / 2; // (si + ((ei - si) / 2) )
            if (arr[mid] == data)
                return mid;
            else if (arr[mid] < data)
                si = mid + 1;
            else
                ei = mid - 1;
        }

        return -1; // If data not found return -1;
    }

    // b <===== 34. Find First and Last Position of Element in Sorted Array =======>
    // https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/description/

    public static int firstPosition(int[] arr, int data) {

        int n = arr.length;
        int si = 0, ei = n - 1;

        while (si <= ei) {
            int mid = (si + ei) / 2;
            if (arr[mid] == data) {
                if (mid == 0 || arr[mid - 1] != data) // Agar mai first element hua ya merese pehle koi aur element hai,
                                                      // to mai he first index hun
                    return mid;
                else
                    ei = mid - 1; // ei ko piche isiliye move kiya kyunki mid ke piche aur duplicate elements hain
            } else if (arr[mid] < data) {
                si = mid + 1;
            } else {
                ei = mid - 1;
            }
        }

        return -1;
    }

    public static int lastPosition(int[] arr, int data) {
        int n = arr.length;
        int si = 0, ei = n - 1;

        while (si <= ei) {
            int mid = (si + ei) / 2;

            if (arr[mid] == data) {
                if (mid == n - 1 || arr[mid + 1] != data) // Agar mai last element hua ya fir mere aage koi dusra
                                                          // eleemnt hai, to mai he last index hun
                    return mid;
                else
                    si = mid + 1; // si ko aage isiliye move kiya kyuki mid ke aage aur same duplicate elements
                                  // hain
            } else if (arr[mid] < data) {
                si = mid + 1;
            } else {
                ei = mid - 1;
            }
        }
        return -1;
    }

    public int[] searchRange(int[] arr, int target) {
        return new int[] { firstPosition(arr, target), lastPosition(arr, target) };
    }

    // b <===============35. Search Insert Position ============>
    // https://leetcode.com/problems/search-insert-position/description/

    // # Question is : If data present, return the index, otherwise return the
    // # insert position of the data.

    public int searchInsert(int[] arr, int data) {
        int n = arr.length;
        int si = 0, ei = n - 1;

        while (si <= ei) {

            int mid = (si + ei) / 2;
            if (arr[mid] <= data)
                si = mid + 1;
            else
                ei = mid - 1;
        }

        // The position of si after this binary search is such that if we find the data,
        // it will be always on the (si - 1) index and if we do not find the data, si
        // will give the insert index.

        // # Also if data present (si -1) is the last index of the data. So we can also
        // # use it to find the last index of the element.
        return si - 1 >= 0 && arr[si - 1] == data ? si - 1 : si;
    }

    // ! Same thing can be written as :

    // ! important
    public int insertLocation(int[] arr, int data) {
        int n = arr.length, si = 0, ei = n - 1;
        while (si <= ei) {
            int mid = (si + ei) / 2;
            if (arr[mid] <= data)
                si = mid + 1;
            else
                ei = mid - 1;
        }

        return si; // # We know that insert location will be si, whether the data is found or not.
    }

    public int perfectPosOfElement(int[] arr, int data) {
        int insertPos = insertLocation(arr, data);
        int lastIndex = insertPos - 1;

        return (lastIndex >= 0 && arr[lastIndex] == data) ? lastIndex : insertPos;
    }

    // b <=================== Nearest Element ==============>
    
    public int nearestElement(int[] arr, int data) {
        int n = arr.length;
        if (n == 0)
            return 0;

        if (data <= arr[0] || data >= arr[n - 1]) // Corner Edge cases
            return data <= arr[0] ? arr[0] : arr[n - 1];

        int si = 0, ei = n - 1;
        while (si <= ei) {
            int mid = (si + ei) / 2;
            if (arr[mid] <= data)
                si = mid + 1;
            else
                ei = mid - 1;
        }
        // si will give the ceil value and the ei will give the floor value.
        // So to find the nearest element we will return the value where the difference
        // is least.
        return data - arr[ei] <= arr[si] - data ? arr[ei] : arr[si];
    }

    

}