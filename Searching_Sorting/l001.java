import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

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

    // b <===================== Quick sort ===================>
    // # To be studied in QuickSort.java

    // b <================ 74. Search a 2D Matrix ===============>
    // https://leetcode.com/problems/search-a-2d-matrix/description/

    // # Simple binary Search lagaya.
    // 2-d matrix to single array mai convert kiya bas.

    public boolean searchMatrix(int[][] matrix, int target) {

        int n = matrix.length, m = matrix[0].length;

        int si = 0, ei = n * m - 1;
        while (si <= ei) {

            int mid = (si + ei) / 2;
            int r = mid / m, c = mid % m;

            if (matrix[r][c] == target)
                return true;
            else if (matrix[r][c] < target)
                si = mid + 1;
            else
                ei = mid - 1;
        }

        return false;
    }

    // b <============ 240. Search a 2D Matrix II =============>
    // https://leetcode.com/problems/search-a-2d-matrix-ii/description/

    // Agar mai top left se ya bottom right se start karun to mujhe nhi pata lagta
    // ki kis direction mai mujhe move karna hai.

    // Isiliye Mai sirf bottom left se ya top right se start kar sakta hun, jahan
    // mujhe kahan move karna hai pata hoga.

    // # Yahan pe hum binary search nhi laga sakte kyunki agar hum pure 2-d ko 1-d
    // # mai convert karte hain, to array sorted nho hoga aur binary search sird
    // # sorted array mai he lagta hai.

    public boolean searchMatrix_(int[][] matrix, int target) {

        int n = matrix.length, m = matrix[0].length, r = n - 1, c = 0; // Started from bottom left.

        while (r >= 0 && c >= 0 && r < n && c < m) { // Jab tak r aur c dono matrix ke andar hai to target element ko
                                                     // dhundhte raho.
            int data = matrix[r][c];
            if (data == target)
                return true;
            else if (data < target)
                c++;
            else
                r--;
        }

        return false;
    }

    // b <=========== Merge Sort (912. Sort an Array) =============>
    // https://leetcode.com/problems/sort-an-array/description/

    public static int[] mergeTwoSortedArrays(int[] arr1, int[] arr2) {

        int n = arr1.length, m = arr2.length;
        int[] arr = new int[n + m];

        int i = 0, j = 0, k = 0;
        while (i < n && j < m) {
            if (arr1[i] <= arr2[j])
                arr[k++] = arr1[i++];
            else
                arr[k++] = arr2[j++];
        }
        while (i < n)
            arr[k++] = arr1[i++];
        while (j < m)
            arr[k++] = arr2[j++];

        return arr;
    }

    // # Basic faith :
    // ? Maine left ko bola tu apne aap ko sort karke le aa, maine right ko bola tu
    // ? bhi apne aap ko sort karke le aa. Ab mai agar dono array ko
    // ? mergeTwoSortedArray kardunga, to mujhe pura array sorted mil jayega.

    public static int[] mergeSort(int[] arr, int si, int ei) { // : O(N)
        if (ei == si)
            return new int[] { arr[si] };

        int mid = (si + ei) / 2;
        int[] left = mergeSort(arr, si, mid); // O(N/2)
        int[] right = mergeSort(arr, mid + 1, ei); // O(N/2)

        return mergeTwoSortedArrays(left, right); // O(N)
    }

    public int[] sortArray(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    // b <================ Count Inversions ===============>
    // https://practice.geeksforgeeks.org/problems/inversion-of-array-1587115620/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // ! A very good question.

    // # Faith :
    // # Maine left se bola mujhe tu apni range mai inversion count nikal ke dede
    // # aur apni range ko sort karke leke aaja.

    // # Same faith maine right se rakha.

    // # Ab agar mujhe dono arrays ki range ke accross inversion count mil jaye, to
    // # muhje total inversion count mil jayega.

    // ? Aur kyunki dono range sorted hain, to inversion count nikalna easy ho
    // ? jayega.

    // Consider two array [-9,2,5,10,22] and [5,7,28,1100]

    // inversionAccross dhundne ke liye hum left array ke indexex ko right array ke
    // index ke sath compare karenge.

    // -9 se koi choti value nhi hai right array mai, so iski waajh se count 0.
    // 2 se koi choti value nhi hai right array mai, so iski waajh se count 0.
    // 5 se koi choti value nhi hai right array mai, so iski waajh se count 0.

    // 5 ke equal value hai 5 next array mai, per hum left wale index ko he move
    // karenge kyunki shayad left wale array mai 5 se bada koi value aaye jo ki
    // right array wale 5 ke sath inversion create kare.

    // Now we move to 10. Now 10 bada hai 5 in the next array. To ek inversion hoga.
    // Ab kyunki left array sorted hai to , 10 ke baad jitni bhi values aayengi, wo
    // ` bhi inversion create karengi 5 ke sath. Therefore, total count will be 2 of
    // 10 and 22 creating inversion with 5 in the next array.

    // Ab hum right array mai gaye 7 ke pass. Same whi jo uper five ke sath hua. It
    // will also contribute 2 inversion with both 10 and 22.

    // Now we come to 28. Since 10 is less that 28, so no inversion.
    // Now we come ti 22. Since 22 is less that 28, so no inversion.

    // Hence we have found all the inversion.

    public static long inversionAcrossArray(long[] arr, int si, int ei, int mid) {

        // # Sorting the two already sorted ranges together is same like merge two
        // # sorted arrays. Main issue is to find the inversion.

        long count = 0;
        int lsi = si, lei = mid, rsi = mid + 1, rei = ei;

        long[] sorted = new long[ei - si + 1];
        int k = 0;

        while (lsi <= lei && rsi <= rei) {
            if (arr[lsi] > arr[rsi]) { // Inversion condition.
                count += lei - lsi + 1; // kyunki array already sorted hai to left element ke aage wale sare element
                                        // hai, wo current right element ke sath inversion create karenge.
                sorted[k++] = arr[rsi++];
            } else {
                sorted[k++] = arr[lsi++];
            }
        }

        while (lsi <= lei)
            sorted[k++] = arr[lsi++];
        while (rsi <= rei)
            sorted[k++] = arr[rsi++];

        k = 0;
        for (int i = si; i <= ei; i++) { // Sorted array ko actual array moi copy kar diya, taki actual array bhi sorted
                                         // ho jaye.
            arr[i] = sorted[k++];
        }

        return count;
    }

    static long inversionCount(long arr[], int si, int ei) {
        if (si >= ei)
            return 0;

        int mid = (si + ei) / 2;
        long left = inversionCount(arr, si, mid);
        long right = inversionCount(arr, mid + 1, ei);

        return left + right + inversionAcrossArray(arr, si, ei, mid);

    }

    static long inversionCount(long arr[], long N) {
        if (N <= 1)
            return 0;
        return inversionCount(arr, 0, (int) (N - 1));
    }

    // ! Bhaiy Method :

    public static long InversionAcrossArray(long[] arr, int l, int r, int mid, long[] sortedArray) {
        // Ek sorte array pass kiye taki bar bar array creation ka time na lage.
        // Sorted array yahan pe bar bar override hota rahega. But that is not an issue.
        int lsi = l, lei = mid;
        int rsi = mid + 1, rei = r;

        int k = 0;
        long count = 0;
        while (lsi <= lei && rsi <= rei) {
            if (arr[lsi] > arr[rsi]) {
                count += (lei - lsi + 1);
                sortedArray[k++] = arr[rsi++];
            } else
                sortedArray[k++] = arr[lsi++];
        }

        while (lsi <= lei)
            sortedArray[k++] = arr[lsi++];
        while (rsi <= rei)
            sortedArray[k++] = arr[rsi++];

        // for (k = 0; k < sortedArray.length; k++)
        // arr[k + l] = sortedArray[k];

        k = 0;
        for (int i = l; i <= r; i++)
            arr[i] = sortedArray[k++];

        return count;
    }

    public static long inversionCount(long[] arr, int si, int ei, long[] sortedArray) {
        if (si >= ei)
            return 0;

        int mid = (si + ei) / 2;
        long ICL = inversionCount(arr, si, mid, sortedArray); // IC : Inversion Count, L = left , R = Right
        long ICR = inversionCount(arr, mid + 1, ei, sortedArray);

        return (ICL + ICR + InversionAcrossArray(arr, si, ei, mid, sortedArray));
    }

    public static long inversionCount_(long arr[], long N) {
        if (N == 0)
            return 0;

        long[] sortedArray = new long[(int) N]; // Array size will be only of integer type.
        return inversionCount(arr, 0, (int) N - 1, sortedArray);
    }

    // b <===============33. Search in Rotated Sorted Array ===========>
    // https://leetcode.com/problems/search-in-rotated-sorted-array/description/

    // # Same binary Search ki tarah hai, per thoda change hai.

    // Since array rotated hai, to hume pehle ye pata lagana hoga ki kaunsa region
    // sorted hai.

    // Agar humne ye pata kar liya, to hum apne search ka area define kar sakte
    // hain. Which will be same problem as binary search.

    public int search(int[] nums, int target) {
        int n = nums.length, si = 0, ei = n - 1;

        while (si <= ei) {
            int mid = (si + ei) / 2;
            if (nums[mid] == target)
                return mid;
            else if (nums[si] <= nums[mid]) { // It means mid se left wala region sorted hai
                if (nums[si] <= target && nums[mid] > target) // aur agar data si se mid tak lie karta hai, to hume
                                                              // wahan pe search karna chahiye.
                    ei = mid - 1;
                else
                    si = mid + 1;
            } else { // It means mid se right wala region sorted hai.
                if (nums[ei] >= target && nums[mid] < target) // aur agar data mid se ei tak hai to hume wahan pe search
                                                              // karna chahiye nhi to dusre area mai.
                    si = mid + 1;
                else
                    ei = mid - 1;
            }
        }
        return -1;
    }

    // b <============ 81. Search in Rotated Sorted Array II ===============>
    // https://leetcode.com/problems/search-in-rotated-sorted-array-ii/description/

    // # Same as above, we try to find the perfect search area where we can find the
    // # element.

    // # Test case to dry run on :

    // [1,0] , target : 0
    // [1,0,1,1,1] , target : 0
    // [8,8,8,8,8,8,8,8,8,7,8], target : 7

    public boolean search_(int[] nums, int target) {
        int n = nums.length, si = 0, ei = n - 1;

        while (si <= ei) {
            int mid = (si + ei) / 2;
            if (nums[mid] == target)
                return true;
            else if (nums[si] < nums[mid]) {
                if (nums[si] <= target && nums[mid] > target)
                    ei = mid - 1;
                else
                    si = mid + 1;
            } else if (nums[mid] < nums[ei]) {
                if (nums[ei] >= target && nums[mid] < target)
                    si = mid + 1;
                else
                    ei = mid - 1;
            } else {
                // [8,8,8,8,8,8,8,8,8,7,8], target : 7
                // Since when both si and mid elements are equal, we cannot decide where to
                // move. So what we do is increement the si by 1 to shorten the search area.
                // Now therefore the complexity can go to O(n) due to this.
                if (nums[si] == target)
                    return true;
                else
                    si++;
            }
        }
        return false;
    }

    public boolean search_81(int[] nums, int target) {

        int n = nums.length, si = 0, ei = n - 1;
        while (si <= ei) {
            int mid = (si + ei) / 2;
            if (nums[mid] == target || nums[si] == target) // have moved the same check above.
                return true;
            else if (nums[si] < nums[mid]) {
                if (nums[si] <= target && nums[mid] > target)
                    ei = mid - 1;
                else
                    si = mid + 1;
            } else if (nums[mid] < nums[ei]) {
                if (nums[ei] >= target && nums[mid] < target)
                    si = mid + 1;
                else
                    ei = mid - 1;
            } else {

                si++;
            }
        }
        return false;
    }

    // b <===========153. Find Minimum in Rotated Sorted Array============>
    // https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/

    // # Simple hai
    // # Mujhe jaise he pata lga koi region sorted hai, maine us region ki sabse
    // # choti value ko min se compare kiya.

    public int findMin(int[] nums) {

        int n = nums.length, si = 0, ei = n - 1;
        int min = (int) 1e9;

        while (si <= ei) {
            int mid = (si + ei) / 2;
            if (nums[si] <= nums[mid]) {
                // iska matlab mid se left wala region sorted hai, to nums[si] he lowest value
                // hogi. Hence nums[si] ko maine min se compare lar liya.
                // Ab mujhe sabse choti value mid ke aage he mil sakti hai, to mai si ko mid + 1
                // mai le aaya.
                min = Math.min(min, nums[si]);
                si = mid + 1;
            } else {
                // Agar mera right wala region sorted hua to sabse min value mid he hogi. Hence
                // maine use min se compare kiya aur ei ko mid - 1 mai move kiya.
                min = Math.min(min, nums[mid]);
                ei = mid - 1;
            }
        }
        return min;
    }

    // ! Bhaiya Code :

    int findMin_I(int[] arr) {
        int n = arr.length, si = 0, ei = n - 1;
        if (arr[si] <= arr[ei])
            return arr[si];

        while (si < ei) {
            int mid = (si + ei) / 2;
            if (arr[mid] < arr[ei])
                ei = mid;
            else
                si = mid + 1; // (arr[si] <= arr[mid])
        }
        return arr[si];
    }

    // b <==========154. Find Minimum in Rotated Sorted Array II =========>
    // https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/description/

    // # Same logic as above. Per ab jaise [8,8,8,8,8,8,8,8,8,7,8] test case mai,
    // # jab hume pata nhi chalega ki kahan jhana hai, tab hum sirf si++ karke array
    // # ko shrink karte hain, jiske karan complexity can go to O(n).
    public int findMin_(int[] nums) {

        int n = nums.length, si = 0, ei = n - 1;
        int min = (int) 1e9;

        while (si <= ei) {
            int mid = (si + ei) / 2;
            if (nums[si] < nums[mid]) {
                min = Math.min(min, nums[si]);
                si = mid + 1;
            } else if (nums[ei] > nums[mid]) {
                min = Math.min(min, nums[mid]);
                ei = mid - 1;
            } else {
                min = Math.min(min, nums[si]); // isme humne min use kiya hai isiliye hum si++ lga sakte hai.
                si++;
            }
        }
        return min;
    }

    // ! Bhaiya Code :

    int findMin_II(int[] arr) {
        int n = arr.length, si = 0, ei = n - 1;
        if (arr[si] < arr[ei])
            return arr[si];

        while (si < ei) {
            int mid = (si + ei) / 2;
            if (arr[mid] < arr[ei])
                ei = mid;
            else if (arr[mid] > arr[ei])
                si = mid + 1;
            else
                ei--; // ei koi he -- kiya since whi muhje mere answer ke close leke aayega. Mai si ko
                      // increase nhi kar sakta.
        }
        return arr[si];
    }

    // b <============== 167. Two Sum II - Input Array Is Sorted ==============>
    // https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/

    // # Yahan pe simple two pointer approach use kiya hai
    public int[] twoSum(int[] arr, int target) {

        int n = arr.length, si = 0, ei = n - 1;
        while (si < ei) {
            int sum = arr[si] + arr[ei];
            if (sum == target)
                return new int[] { si + 1, ei + 1 }; // + 1 since answer demanded has index starting from 1.
            else if (sum > target) // Since array is sorted, to move closer to target, we will move to e--.
                ei--;
            else
                si++;
        }
        return null;
    }

    // b <============ Two Sum Getting All Unique Pairs ==============>

    // if a + b = target
    // Manlo a + b = target hai, to a aur b mil gya hume ek pair.
    // Agar mujhe next unique pair chahiye jo target ke equal ho, to mai a ko
    // increase karunga aur b ko decrease taki mera total sum constant rahe.

    // To mujhe dono end mai unique pair dhundhne honge.

    public ArrayList<int[]> twoSum_allPairs(int[] arr, int target) {
        ArrayList<int[]> ans = new ArrayList<>();

        int n = arr.length, si = 0, ei = n - 1;
        while (si < ei) {// Here we will not add equal to check. Since we don't want the same element to
            // contitute of sum. For test case [-2,0,2,4], target =0, Dry run and get the
            // answer.
            int sum = arr[si] + arr[ei];
            if (sum == target) {
                ans.add(new int[] { si + 1, ei + 1 });
                si++;
                ei--;
                while (si < ei && arr[si] == arr[si - 1]) // removing all duplicates
                    si++;
                while (si < ei && arr[ei] == arr[ei + 1]) // removing all duplicates
                    ei--;
            } else if (sum > target)
                ei--;
            else
                si++;
        }
        return ans;
    }

    // # Same just passed si and ei as parameters
    public List<List<Integer>> twoSum_allPairs_withRange(int[] arr, int target, int si, int ei) {
        List<List<Integer>> ans = new ArrayList<>();

        while (si < ei) {
            int sum = arr[si] + arr[ei];
            if (sum == target) {
                ans.add(new ArrayList<>(Arrays.asList(arr[si], arr[ei]))); // returning arralist with initial values.
                si++;
                ei--;
                while (si < ei && arr[si] == arr[si - 1])
                    si++;
                while (si < ei && arr[ei] == arr[ei + 1])
                    ei--;
            } else if (sum > target)
                ei--;
            else
                si++;
        }
        return ans;
    }

    // b <========== 15. 3Sum =============>
    // https://leetcode.com/problems/3sum/description/

    // To iska initial logic bhi same hai.

    // Hume find karna hai a + b + c = target.
    // To the equation will be a + b = target - c;
    // So which we can say a + b = target`

    public static void prepareAns(List<List<Integer>> ans, List<List<Integer>> myAns, int fixedElement) {

        for (List<Integer> list : myAns) {
            List<Integer> newAns = new ArrayList<>(list);
            newAns.add(fixedElement);
            ans.add(newAns);
        }

        // for (List<Integer> list : myAns) {
        // list.add(fixedElement);
        // ans.add(list);
        // }
    }

    public List<List<Integer>> threeSum(int[] arr, int target, int si, int ei) {
        List<List<Integer>> ans = new ArrayList<>();

        for (int i = si; i < ei;) {
            List<List<Integer>> myAns = twoSum_allPairs_withRange(arr, target - arr[i], i + 1, ei);
            prepareAns(ans, myAns, arr[i]);
            i++; // # Taki element shi jagah pe aaye. Dry run on duplicate test case and you will
                 // # understand.
            while (i < ei && arr[i] == arr[i - 1]) // To remove duplicate elements.
                // Ek bar maine -1 ko fix element manke call laga di, to usne apni sari
                // possibilities dhundh li hain. To ab hume dubara -1 ki call lagane ki jaroorat
                // nhi hai.
                i++;
        }
        return ans;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        return threeSum(nums, 0, 0, nums.length - 1);
    }

    // b <============== 18. 4Sum ==============>
    // https://leetcode.com/problems/4sum/

    // To iska initial logic bhi same hai.

    // Hume find karna hai a + b + c + d = target.
    // To the equation will be a + b + c = target - d= target`
    // To the equation will be a + b = target` - c = target``
    // So which we can say a + b = target``.

    // # For 4Sum, rest all the things are same.
    // THere was test case where target was exceeding the integer limitation.
    // Therefore has to convert the target into long and also the sum of tow
    // elements to be of long type.

    public List<List<Integer>> twoSum_allPairs_withRange(int[] arr, long target, int si, int ei) {
        List<List<Integer>> ans = new ArrayList<>();

        while (si < ei) {
            long sum = arr[si] + arr[ei];
            if (sum == target) {
                ans.add(new ArrayList<>(Arrays.asList(arr[si], arr[ei]))); // returning arralist with initial values.
                si++;
                ei--;
                while (si < ei && arr[si] == arr[si - 1])
                    si++;
                while (si < ei && arr[ei] == arr[ei + 1])
                    ei--;
            } else if (sum > target)
                ei--;
            else
                si++;
        }
        return ans;
    }

    public List<List<Integer>> threeSum(int[] arr, long target, int si, int ei) {
        List<List<Integer>> ans = new ArrayList<>();

        for (int i = si; i < ei;) {
            long newTarget = target - arr[i];
            List<List<Integer>> myAns = twoSum_allPairs_withRange(arr, newTarget, i + 1, ei);
            prepareAns(ans, myAns, arr[i]);
            i++;
            while (i < ei && arr[i] == arr[i - 1])
                i++;
        }

        return ans;
    }

    public List<List<Integer>> fourSum(int[] arr, int target, int si, int ei) {
        List<List<Integer>> ans = new ArrayList<>();

        for (int i = si; i < ei;) {
            long newTarget = target - arr[i];
            List<List<Integer>> myAns = threeSum(arr, newTarget, i + 1, ei);
            prepareAns(ans, myAns, arr[i]);
            i++;
            while (i < ei && arr[i] == arr[i - 1])
                i++;
        }

        return ans;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        return fourSum(nums, target, 0, nums.length - 1);
    }

    // ! Time Complexity :

    // # 2Sum : O(n)
    // # 3Sum : O(n^2)
    // # 2Sum : O(n^3)

    // b <================ Kth Sum =====================>

    // Simple recursion ka use kiya to calculate the kth sum.
    // Same uper wala code likha hai. Bas jaise he k==2 ho jaye, to humne 2Sum ko
    // call kar diya.

    public List<List<Integer>> kth_sum(int[] arr, int target, int si, int ei, int k) {
        if (k == 2) // ` Base case
            return twoSum_allPairs_withRange(arr, target, si, ei);

        List<List<Integer>> ans = new ArrayList<>();

        for (int i = si; i < ei;) {
            List<List<Integer>> myAns = kth_sum(arr, target - arr[i], i + 1, ei, k - 1);
            prepareAns(ans, myAns, arr[i]);
            i++;
            while (i < ei && arr[i] == arr[i - 1])
                i++;
        }

        return ans;
    }

    // b <=============== 16. 3Sum Closest =============>
    // https://leetcode.com/problems/3sum-closest/description/

    // # Same 2Sum, 3Sum wala logic use kiya hai, but with a little twist.
    public int[] twoSumClosest(int[] arr, int target, int si, int ei) {

        int minSum = (int) 1e9;
        int minDiff = (int) 1e9;
        // target - sum
        while (si < ei) {
            int sum = arr[si] + arr[ei];
            int diff = Math.abs(target - sum);
            // Why math.abs since -2 bhi 1 se same distance pe hai aur 4 bhi 1 se same
            // distance pe hai. To dono ka weightage equal hai.
            if (diff < minDiff) { // Jiska diff sabse kam, mujhe whi sum chahiye.
                minDiff = diff;
                minSum = sum;
            }
            if (sum < target)
                si++;
            else
                ei--;
        }
        return new int[] { minSum, minDiff };
    }

    public int threeSumClosest(int[] arr, int target) {
        Arrays.sort(arr);
        int n = arr.length, si = 0, ei = n - 1;
        int minSum = (int) 1e9, minDiff = (int) 1e9;
        for (int i = si; i < ei;) {
            int[] ans = twoSumClosest(arr, target - arr[i], i + 1, ei);
            // {minSum, minDiff}
            if (ans[1] < minDiff) { // Jiska diff sabse kam, mujhe whi sum chahiye.
                minDiff = ans[1];
                minSum = ans[0] + arr[i]; // Creating our 3 Sum
            }
            i++;
            while (i < ei && arr[i] == arr[i - 1]) // To remove duplicates since they will yeild the same answer.
                i++;
        }
        return minSum;
    }

    // b <==================== TwoSum Count ==============>
    // # A pair will be (one element from nums1, one element from num2)
    // # Hume do array mile hain. Hume nikalna hai ki aise kitne pair ban sakte
    // # hain, So that their total sum is total to the target.

    // ! Here we cannot use two pointer appraoch, since we cannot decide which
    // ! direction to move.

    // Dry run on : 1=[-1,1,2,4], B=[-3,-2,-1,0]

    // To Hum is problem ko kaise solve karte.
    // Manle humpe do array hai A and B.
    // A ka ek element ko fix karte, for ex say A[i]
    // Then Target - A[i] so search karte B mai.

    // Ab Humpe search karne ke 2 ways hain.
    // Ya to hashmap mai daldo A ke element ko ferquency count ke sath aur tab B mai
    // iterate karke Target - B[i] ko map mai dhundho. Agar milta hai to uske count
    // ko add kardo.

    // Ya to hum element ko binary search se dhundh sakte hain.(Which will more
    // costly)

    public static int twoSumCount(int[] nums1, int[] nums2) {

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int ele : nums1)
            map.put(ele, map.getOrDefault(ele, 0) + 1);

        int count = 0, target = 0;
        for (int ele : nums2) {
            if (map.containsKey(target - ele))
                count += map.get(target - ele);
        }

        return count;
    }

    // b <============ 454. 4Sum II (4SumCount) ===========>
    // https://leetcode.com/problems/4sum-ii/

    // # Yahan pe bhi same whi concept hai.
    // # Ab kyunki 4 arrays given hai.
    // # To pehle 2 arrays ke sare combination ke sum ko map mai save kiya
    // # Au tab same bache hua do arrays ke combination ko target se subtract karke
    // # map mai dhundha aur count mai add kar diya.

    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int ele1 : nums1)
            for (int ele2 : nums2)
                map.put(ele1 + ele2, map.getOrDefault(ele1 + ele2, 0) + 1);

        int count = 0, target = 0;
        for (int ele3 : nums3)
            for (int ele4 : nums4)
                if (map.containsKey(target - (ele3 + ele4)))
                    count += map.get(target - (ele3 + ele4));

        return count;
    }


    // # Four Sum Using the Two sum problem
    int fourSumCount_(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        HashMap<Integer, Integer> map = new HashMap<>();

        int n = nums1.length, idx = 0;
        int[] A = new int[n * n];
        int[] B = new int[n * n];

        for (int e1 : nums1)
            for (int e2 : nums2)
                A[idx++] = e1 + e2;

        idx = 0;
        for (int e1 : nums3)
            for (int e2 : nums4)
                B[idx++] = e1 + e2;

        return twoSumCount(A, B);
    }
}