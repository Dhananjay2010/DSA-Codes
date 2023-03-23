import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.HashMap;

public class questions {

    // B <================ Rotate A Array ==============>

    // # If array is [9,7,2,6,8,3]
    // # For r=+1, the array will be [7,2,6,8,3,9];
    // Value front se piche jati hai

    // # for r=-1, the array will be [3,9,7,2,6,8]
    // value back se front aati hai.

    // # in O(1) space and O(n) time, r can be +ve and -ve.
    // for r<0, (r % n + n) will give us the same +ve rotation.

    // To pehle pure array ko reverse kiya.
    // Tab array ke alag alag portion ko reverse kiya.
    // To resultant array rotated mil gaya.

    // Ab agar array ka size n=6 hai,
    // to r=-4 is equal to r=2 == > (-4 % 6 + 6)

    // (a-b) % c = (a % c - b % c + c) % c

    // b % c + c Here added + c to make b positive.

    public static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void reverse(int[] arr, int si, int ei) {
        while (si < ei) {
            swap(arr, si++, ei--);
        }
    }

    public static void rotateByK(int[] arr, int r) {

        int n = arr.length;
        r = r % n;
        if (r < 0)
            r = r + n; // To make r +ve.

        reverse(arr, 0, n - 1); // Pure array ko reverse kiya.
        reverse(arr, 0, n - r - 1); // Left range ko reverse kiya.
        reverse(arr, n - r, n - 1); // right range ko reverse kiya.
    }

    // b <================= Rotate Array =================>
    // https://leetcode.com/problems/rotate-array/description/

    public void rotate(int[] nums, int k) {
        rotateByK(nums, -k);
    }

    // b <========== Segregate 0 and 1 numbers in the array =============>

    // Here we have to define the interval for each of the numbers and then using
    // two pointers we can segreegate them.

    // Hum element ki range define kar dete hain aur jo bhi element aata hai, usko
    // ` uski shi range mai dalte hain aur sath sath range bho increase karte rehte
    // hain.

    // (0,p) ∈ 0
    // (p+1, itr -1) ∈ 1
    // (itr, n-1) ∈ ∞ (unexplored region)

    // To agar mujhe ∞ (unexplored region) mai 0 milta hai, to mai swap karunga ++p
    // ko itr ke sath taki 0 ka area expand ho, 1 ka same rahe aur ∞ (unexplored
    // region) wala decrease ho.

    // Aur agar ∞ (unexplored region) mai 1 milta hai, to itr++ kardo, wo 1 ka area
    // apne aap he increase kar dega.

    public static void segregate01(int[] arr) {
        int p = -1, itr = 0, n = arr.length;
        while (itr < n) {
            if (arr[itr] == 0) {
                swap(arr, ++p, itr);
            }
            itr++;
        }
    }

    // b <=========Segregate Positive and Negative integers in the array ========>

    // # Now it will be solved using the same above techinique. No change at all.

    public static void segregatePositiveAndNegative(int[] arr) {
        int n = arr.length, pt = -1, itr = 0;
        while (itr < n) {
            if (arr[itr] < 0)
                swap(arr, ++pt, itr);
            itr++;
        }
    }

    // b <= Segregate Zero, One and Two in the array. (One pass solution Needed) =>
    // b <==================== Sort Colors =============>

    // https://leetcode.com/problems/sort-colors/description/

    // Logic is same as above. Just have added one more pointer and dry run on that.
    // (0,p1) ∈ 0
    // (p1+1, itr -1) ∈ 1
    // (itr, p2 - 1) ∈ ∞ (unexplored region)
    // (p2, n-1) ∈ 2

    public static void segregateZeroOneTwo(int[] arr) {

        int p1 = -1, n = arr.length, p2 = n, itr = 0;

        while (itr < p2) {
            if (arr[itr] == 2) {
                swap(arr, --p2, itr); // When swapping for two, we don't want to increse the itr pointer since I don't
                                      // know what value I have swapped with. So I want to again that swapped value.
            } else if (arr[itr] == 0) {
                swap(arr, ++p1, itr++);
            } else
                itr++;
        }

    }

    // b <==================Max sum in the configuration =================>
    // https://practice.geeksforgeeks.org/problems/max-sum-in-the-configuration/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // Consider an array of [a,b,c,d,e]
    // Its total Sum will be a + b + c + d + e

    // Now total sum1 will be 0*a + 1*b + 2*c + 3*d + 4*e

    // Now if I rotate it by 1, the total sum will be
    // 0*b + 1*c + 2*d + 3*e + 4*a ? so do we get it from the previous sum ???

    // # (0*a + 1*b + 2*c + 3*d + 4*e) - (a + b + c + d + e) + 5 * a
    // # = 0*b + 1*c + 2*d + 3*e + 4*a

    // Hence we have got the formula to get it from the previous sum.

    int max_sum(int arr[], int n) {
        // Your code here

        int sum = 0, sum1 = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            sum1 += arr[i] * i;
        }
        int max = sum1;

        for (int i = 1; i < n; i++) {
            sum1 = sum1 - sum + (n * arr[i - 1]);
            max = Math.max(max, sum1);
        }

        return max;
    }

    // b <======================= Container With Most Water =================>
    // https://leetcode.com/problems/container-with-most-water/

    // Yahan pe two pointer approach use kiya.

    // Ek container utna he to pani hold kar payega jitna uski left aur right height
    // ki min value hogi.

    // # Like Area is l*b.
    // # To formula hoga totalWater = Math.min(arr[i], arr[j]) * (j - i);

    // To har iteration ke liye , total water nikalte gaye.

    // arr[i] = 1 aur arr[j]= 7 , to mai right ki taraf search
    // karne jaaunga. kyun ??

    // Shayad mujhe ek aise bar mil jaye jo meri height se bada ho. To maine i++
    // kiya.

    // Else maine j-- kiya.

    public int maxArea(int[] arr) {
        int n = arr.length;
        int i = 0, j = n - 1;

        int maxWater = 0;
        while (i < j) {
            int totalWater = Math.min(arr[i], arr[j]) * (j - i);
            maxWater = Math.max(maxWater, totalWater);
            if (arr[i] <= arr[j])
                i++;
            else
                j--;
        }

        return maxWater;
    }

    // b <========== Longest Substring Without Repeating Characters ==============>
    // https://leetcode.com/problems/longest-substring-without-repeating-characters/

    // Window utni he rehni chahiye jitne merepe unique character ho.

    // Jaise he mujhe koi repeating character milta hai, mai apni window ko shrink
    // karna start karta hun jab tak again meri windo mai unique character nhi reh
    // jate.

    // To hume character ke count ko khin pe track karna padega, iske liue humne
    // array liya hai 128 size ka.

    // # 128 isiliye kyunki likha hai ki s consists of English letters, digits,
    // # symbols and spaces. So 128 coveers every value.

    // Maine ek count variable use kiya hai jo indicate karega ki mujhe repeating
    // character mila hai ki nhi.
    // Agar count ==1, to matlab repeating character mila hai, aur agar 0 hai to
    // repeating character nhi mila hai.

    // To jaise he count 1 hoga, matlab meri window mai repeating character hai. To
    // mai apni window ko shrink karna start karunga, jab tak jis element ke wajah
    // se count increase hua tha, us element ki freq 1 nhi ho jati, kyunki whi to
    // repeating character tha.

    // # window : [si,ei) ==> si included, ei not included.

    // To ei tab tak increase karega jab tak meri window valid hai. Jab tak koi
    // repeating element nhi milta.

    // Jaise he koi repeating element milta hai, waise he mai si ko tab tak increase
    // karunga jab tak meri window dubara se unique nhi ho jati. Matlab meri window
    // dubara valid nhi ho jati.

    public int lengthOfLongestSubstring(String str) {

        if (str.length() <= 1)
            return str.length();

        int si = 0, ei = 0, len = 0, count = 0, n = str.length();
        int[] freq = new int[128];
        while (ei < n) {

            if (freq[str.charAt(ei++)]++ > 0) // Jaise he mai use character ki frequency bhadhane aaya jiski freq 1 thi,
                                              // maine frequency increase ki, ei ko ++ kiya aur count ko increase karke
                                              // 1 kiya taki pata laga jaye ki repeating element mil gaya hai.
                count++;
            while (count > 0) { // count greater that 1 hai. Matlab repeating character hai.
                if (freq[str.charAt(si++)]-- > 1) // Window ko shrink karna start kiya. Jaise he wo character mila jiski
                                                  // frequency 2 thi, waise he si++ kiya aur frequency ko decrease kiya
                                                  // and then count ko dubara -- karke 0 pe restore kar diya indicting
                                                  // ki ab koi bhi repeating character nhi hai window mai.
                    count--;
            }

            len = Math.max(len, ei - si); // Har window mai length nikal ke use max se compare karte raho.
        }

        return len;

    }

    // # (freq[str.charAt(ei++)]++ > 0)
    // Now matter ye condition true ho ya false, freq[str.charAt(ei++)]++ statement
    // will behave like :

    // freq[str.charAt(ei)] > 0 will be checked and then all the increment will
    // happen. First the freaqecny for ei will increase by 1 then ei will be
    // incremented by one.

    // # To print the longest substring, we will take global si and global ei to
    // # store the index of max len substring as shown below.

    public int lengthOfLongestSubstring_(String s) {
        if (s.length() <= 1)
            return s.length();

        int n = s.length(), si = 0, ei = 0, count = 0, len = 0;
        int[] freq = new int[128]; // vector<int> freq(128,0);

        int gsi = 0, gei = 0;
        while (ei < n) {
            if (freq[s.charAt(ei++)]++ > 0)
                count++;

            while (count > 0)
                if (freq[s.charAt(si++)]-- > 1)
                    count--;

            if (ei - si > len) {
                len = ei - si;
                gsi = si;
                gei = ei;
            }
        }

        // System.out.println(s.substring(gsi,gei));

        return len;
    }

    // b <======== Longest Substring with At Most Two Distinct Characters=====>
    // https://leetcode.ca/all/159.html

    // Dekh jo logic hai same hai uper ki tarah.
    // Hume bola hai two distinct characters.

    // To count yahan pe distinct characters ko denote kar raha hai.

    // Jaise he mujhe koi distinct charater mila, maine count ko increase kiya.
    // Ab jaise he distinct charater ki value greater than 2 hui, waise he mujhe
    // teesra distinct character mila. To maine same apni window ko shrink karna
    // start kiya jab tak mere distinct character ki value 2 na ho jaye.

    // To humesha window mai ek time mai do he distinct character rehne chahiye.
    // Jaise he third aaya, humne dubara window ko shrink karke do distinct
    // character pe leke aaye.

    public int lengthOfLongestSubstringTwoDistinct(String str) {

        if (str.length() <= 2)
            return str.length();

        int[] freq = new int[128];
        int si = 0, ei = 0, len = 0, count = 0, n = str.length();

        while (ei < n) {
            if (freq[str.charAt(ei++)]++ == 0) // Jaise he pata laga ki nya element mila hai, count ++ kardo.
                count++;

            while (count > 2) {
                if (freq[str.charAt(si++)]-- == 1)
                    count--;
            }
            len = Math.max(len, ei - si);
        }
    }

    // b <=============== 76. Minimum Window Substring ===================>
    // https://leetcode.com/problems/minimum-window-substring/description/

    // # Same template use hoga uper wale questions ki tarah.

    // Pehle jo required string hai t uski frequency array bharli aur usme jitne bhi
    // characters hai use count mai store kar liya.

    // To abhi ke liye frequency un character ki greater hai jo humne required hai s
    // string mai.

    // To ab ei ko move karna start kiya. To jaise he Hume wo element mila jiski
    // freq greater hai, humne count ko -- kiya, kyunki whi to required character
    // tha dusri string mai.

    // Sath sath jo character mujhe excess mai mila, excess in the sense jiski
    // requirement nhi hai, per wo abhi bhi substring ka part ban raha hai, mai usko
    // ` bhi sath sath calculate karte gaya, kyunki shayad in excess elementes ko
    // drop karke mujhe koi choti substring mil jaye.

    // Excess elements ki frequecy ko mai -1 karte gaya. To agar kisi element ki
    // frequency -2 hai, to uska matlab wo 2 excess mai hai.

    // Ab jaise he mera count 0 hua, mujhe meri pehli valid window mili, matlab wo
    // substring jisme t ke sare characters hain.

    // To ab pehle to maine len ko save kiya aur window ko decrease karta gaya.
    // Matlab sii ko increase karta gaya. To ab mai jo bhi excess elements hai unhe
    // drop kar sakta hun, by increasing the frequency.

    // Ab jaise he mujhe koi aisa element mila jiski freq 0 thi, aur mai usko drop
    // karne gaya, iska matlab wo elelment ab mujhe required hai valid window ke
    // liye. To maine count ko bhi increase kiya.

    // To ab ei tab tak increase karega jab tak count dubara 0 nhi ho jata.

    // Humne ek gsi aur gei rakha hai to know the index of the substring. To
    // calculate it.

    public String minWindow(String s, String t) {

        if (s.length() < t.length())
            return "";

        int si = 0, ei = 0, len = (int) 1e9, n = s.length(), gsi = 0, gei = 0;
        int[] freq = new int[128];
        for (int i = 0; i < t.length(); i++) // Filling the freq array for t string.
            freq[t.charAt(i)]++;

        int count = t.length();

        while (ei < n) {
            if (freq[s.charAt(ei++)]-- > 0) // Decreasing the frequency.
                count--;

            while (count == 0) {
                if (ei - si < len) { // ` Updating the length.
                    len = ei - si;
                    gsi = si;
                    gei = ei;
                }
                if (freq[s.charAt(si++)]++ == 0) // Increasing the frequency.
                    count++;
            }
        }

        return gei != 0 ? s.substring(gsi, gei) : "";
    }

    // # More explanatory method :
    public String minWindow2(String s, String t) {
        int ns = s.length(), nt = t.length();
        if (ns < nt)
            return "";
        int[] freq = new int[128];
        for (int i = 0; i < nt; i++)
            freq[t.charAt(i)]++;

        int si = 0, ei = 0, count = nt, len = (int) 1e9, gsi = 0;
        while (ei < ns) {
            if (freq[s.charAt(ei)] > 0)
                count--;
            freq[s.charAt(ei)]--;
            ei++;

            while (count == 0) {
                if (ei - si < len) {
                    len = ei - si;
                    gsi = si;
                }

                if (freq[s.charAt(si)] == 0)
                    count++;
                freq[s.charAt(si)]++;
                si++;
            }
        }
        return len == (int) 1e9 ? "" : s.substring(gsi, gsi + len);
    }

    // b <============= Smallest distinct window ==================>
    // https://practice.geeksforgeeks.org/problems/smallest-distant-window3132/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // # Code same as the above question. Just pehle distict character nikal liye
    // # string mai.

    public int findSubString(String s) {

        int si = 0, ei = 0, len = (int) 1e9, n = s.length(), gsi = 0, gei = 0, count = 0;
        int[] freq = new int[128];
        for (int i = 0; i < s.length(); i++) {// Filling the freq array for t string.
            if (freq[s.charAt(i)] < 1) {
                freq[s.charAt(i)]++;
                count++;
            }
        }

        while (ei < n) {
            if (freq[s.charAt(ei++)]-- > 0) // Decreasing the frequency.
                count--;
            while (count == 0) {
                if (ei - si < len) { // ` Updating the length.
                    len = ei - si;
                    gsi = si;
                    gei = ei;
                }
                if (freq[s.charAt(si++)]++ == 0) // Increasing the frequency.
                    count++;
            }
        }

        return len;
    }

    // b <=============== 209. Minimum Size Subarray Sum ================>
    // https://leetcode.com/problems/minimum-size-subarray-sum/description/

    public int minSubArrayLen(int target, int[] nums) {

        int n = nums.length;
        int si = 0, ei = 0;
        int sum = 0, minLen = (int) 1e9;
        while (ei < n) {
            sum += nums[ei++];
            while (sum >= target) {
                minLen = Math.min(minLen, ei - si);
                sum -= nums[si++];
            }
        }

        return minLen == (int) 1e9 ? 0 : minLen;
    }

    // b <=============Longest K unique characters substring ================>
    // https://practice.geeksforgeeks.org/problems/longest-k-unique-characters-substring0853/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // # This question is same like At most two distinct characters above.
    // # The difference is that here exactly k characters needs to be present.

    public int longestkSubstr(String str, int k) {
        int si = 0, ei = 0, count = 0, n = str.length(), len = -1;
        int[] freq = new int[128];
        while (ei < n) {

            if (freq[str.charAt(ei++)]++ == 0)
                count++;

            while (count > k) {
                if (freq[str.charAt(si++)]-- == 1)
                    count--;
            }

            if (count == k) {
                len = Math.max(len, ei - si);
            }

        }
        return len;
    }

    // b <========== Longest Substring with At Most K Distinct Characters =========>
    // https://leetcode.ca/all/340.html

    // # Same as atmost 2 distinct characters. Bas yahan pe 2 ki jagah k aa jayega.

    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s.length() <= k)
            return s.length();

        int n = s.length(), si = 0, ei = 0, len = 0, count = 0;
        int[] freq = new int[128];

        while (ei < n) {
            if (freq[s.charAt(ei++)]++ == 0)
                count++;

            while (count > k) {
                if (freq[s.charAt(si++)]-- == 1)
                    count--;
            }

            len = Math.max(len, ei - si);
        }
        return len;
    }

    // b <==== 1456. Maximum Number of Vowels in a Substring of Given Length ======>
    // https://leetcode.com/problems/maximum-number-of-vowels-in-a-substring-of-given-length/description/

    // Window ka size constant rakhke vowel count check karte rahe.

    public int maxVowels(String str, int k) {
        if (str.length() < k)
            return 0;

        int count = 0, si = 0, ei = 0, n = str.length();
        HashSet<Character> set = new HashSet<>();
        set.add('a');
        set.add('e');
        set.add('i');
        set.add('o');
        set.add('u');

        for (int i = 0; i < k; i++) { // Pehle initial k length mai pata lagaya ki kitne vowels hai.
            if (set.contains(str.charAt(ei++)))
                count++;
        }
        int len = count;

        while (ei < n) {
            // Window ka size same rakhke, count ko manipulate karte gaye.
            if (set.contains(str.charAt(si++)))
                count--;
            if (set.contains(str.charAt(ei++)))
                count++;

            len = Math.max(len, count);
        }

        return len;
    }

    // ! Using hasVowel function :

    public static boolean hasVowel(char ch) {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }

    public int maxVowels_(String str, int k) {

        if (str.length() < k)
            return 0;

        int count = 0, si = 0, ei = 0, n = str.length();

        for (int i = 0; i < k; i++) {
            if (hasVowel(str.charAt(ei++)))
                count++;
        }
        int len = count;

        while (ei < n) {
            if (hasVowel(str.charAt(si++)))
                count--;
            if (hasVowel(str.charAt(ei++)))
                count++;

            len = Math.max(len, count);
        }

        return len;
    }

    // ! Bhaiya Method :
    public static boolean isVowel(Character ch) {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }

    public int maxVowels__(String s, int k) {
        int n = s.length(), si = 0, ei = 0, vowelCount = 0, maxVowelCount = 0;
        while (ei < n) {
            if (isVowel(s.charAt(ei++)))
                vowelCount++;

            if (ei - si == k) {
                maxVowelCount = Math.max(maxVowelCount, vowelCount);
                if (isVowel(s.charAt(si++)))
                    vowelCount--;
            }
        }

        return maxVowelCount;
    }

    // b <================ Subarrays with K Different Integers =============>
    // https://leetcode.com/problems/subarrays-with-k-different-integers/description/

    // # To solve this question we are going to calculate the number of subarrays
    // # for atMost k different integers and the number of subarrays for atmost k-1
    // # different integers.

    // If we subtract k(atMost) with k-1(atMost), we will get exactly k subarrays
    // that is asked in the question.

    // ! So the Problem statement is now to solve number of subarrays with At most K
    // ! distinct integers.

    // Now We will use the same sliding window techinique.

    // Jab tak humari window valid hai, tab tak hum sub arrays ko add karte rahenge
    // aur ei ko increase karte rahenge.

    // Jaise he window invalid hui, si ko increase karenge tab tak jab tak window
    // valid nhi ho jati.

    // For Ex : [a,b,a,b,c] with index (0,1,2,3,4) respectively.

    // si aur ei initially 0 index pe hain dono.
    // Mai ek count variable mai distinct elements ka count store karta rahunga.

    // totalSubArrayCount naam ka variable rakha hai count subArray ke count ko
    // store karne ke liye.

    // Jaise he hum window ko increase karte gaye, hum subarray bhi calculate karte
    // gaye. Jo hai ei-si for each window.

    // So agar ek window mai si constant hai, to subarray till valid window will be
    // submission of (ei - si) upto the valid window index.

    // Matlab jab tak har window valid hai, tab tak number of subarrays jo honge wo
    // hone ei - si for har iteration of ei.

    // To isiliye hum sabko add karte jate hain.

    // For example : for k=2,

    // [1,2,1,2,3]
    // The number of subArrays till index 3(including) will be :

    // si =0, and ei will vary.
    // Total Subarrays : (1- 0) + (2-0) + (3 -0) + (4-0)= Total 10

    // (1 -0) == > [1]
    // (2 -0) == > [1,2], [2],
    // (3-0) ==> [1,2,1], [2,1], [1]
    // (4-0) == > [1,2,1,2],[2,1,2], [1,2], [2];

    // To bas aise he count

    // Agar frequency 0 hai to matlab, distinct element hai. To maine count ko
    // increase kiya. Aur agar 0 se jyada hai to bas uski frequecy increase ki.

    public int withAtMostKDistinct(int[] nums, int k) {

        int si = 0, ei = 0, count = 0, totalSubArrayCount = 0, n = nums.length;
        int[] freq = new int[n + 1];

        while (ei < n) {

            if (freq[nums[ei++]]++ == 0)
                count++;

            while (count > k) {
                if (freq[nums[si++]]-- == 1)
                    count--;
            }

            totalSubArrayCount += ei - si;
        }

        return totalSubArrayCount;
    }

    public int subarraysWithKDistinct(int[] nums, int k) {
        return withAtMostKDistinct(nums, k) - withAtMostKDistinct(nums, k - 1);
    }

    // b <=================1248. Count Number of Nice Subarrays =================>
    // https://leetcode.com/problems/count-number-of-nice-subarrays/

    // # The logic is same as above. No changes. Counting the subarray is same as
    // # mentioned above.

    public static int atMostKOddNumbers(int[] nums, int k) {

        int si = 0, ei = 0, count = 0, noOfSubArray = 0, n = nums.length;
        while (ei < n) {

            if (nums[ei++] % 2 == 1)
                count++;

            while (count > k) {
                if (nums[si++] % 2 == 1)
                    count--;
            }
            noOfSubArray += ei - si;
        }
        return noOfSubArray;
    }

    public int numberOfSubarrays(int[] nums, int k) {

        return atMostKOddNumbers(nums, k) - atMostKOddNumbers(nums, k - 1);
    }

    // ! Important Point :

    // We can use logical & to know if the number is even or odd.

    // 000 == > 0
    // 001 == > 1
    // 010 == > 2
    // 011 == > 3
    // 100 == > 4
    // 101 == > 5
    // 110 == > 6
    // 111 == > 7

    // If the number is even, its last bit digit is always 0.
    // If the number is odd, its last bit digit is always 1.

    // So the above question can also be solved as :

    // # Logical & is so much faster than the % operator.
    // ? Since the priority of bit operator are very less, don't forget to enclose
    // ? them in brackets

    public int atMostOdd(int[] arr, int k) {
        int n = arr.length, si = 0, ei = 0, count = 0, ans = 0;
        while (ei < n) {
            if ((arr[ei++] & 1) != 0)
                count++;

            while (count > k) {
                if ((arr[si++] & 1) != 0)
                    count--;
            }

            ans += ei - si;
        }

        return ans;
    }

    public int numberOfSubarrays_(int[] nums, int k) {
        return atMostOdd(nums, k) - atMostOdd(nums, k - 1);
    }

    // b <========================== Fruit Into Baskets =============>
    // https://leetcode.com/problems/fruit-into-baskets/description/

    // # Basically the question is to find longest interval with atmost two unique
    // # elements.

    // Same like Leetcode 159. Longest Substring with At Most Two Distinct
    // Characters. The code is exactly same. Dry run and you will understand.

    // Since it is given that Once you reach a tree with fruit that cannot fit in
    // your baskets, you must stop. Yhi condition hai jo kehti hai ki lagataar 2
    // elements he hone chahiye.

    public int totalFruit(int[] fruits) {

        int si = 0, ei = 0, maxFruits = 0, count = 0, n = fruits.length;
        int[] freq = new int[n];

        while (ei < n) {

            if (freq[fruits[ei++]]++ == 0)
                count++;

            while (count > 2) {
                if (freq[fruits[si++]]-- == 1)
                    count--;
            }

            maxFruits = Math.max(maxFruits, ei - si);
        }
        return maxFruits;
    }

    // b <=============== 930. Binary Subarrays With Sum ===============>
    // https://leetcode.com/problems/binary-subarrays-with-sum/description/

    // Same like number of nice subArrays and Subarrays with K Different Integers

    // Same logic of finding AtMost subarrays for K and K-1 values and then
    // returning (k) - (k-1).

    // ! Jahan pe bhi subarray mai bola ki exactly sum ke equal ya exactly itne
    // ! integers, whan pe (k) - (k-1 ) us kar lena.

    public int atMostSum(int[] nums, int goal) {

        int si = 0, ei = 0, sum = 0, totalSubArray = 0, n = nums.length;

        while (ei < n) {
            sum += nums[ei++];

            while (sum > goal) {
                sum -= nums[si++];
            }
            totalSubArray += ei - si;
        }
        return totalSubArray;
    }

    public int numSubarraysWithSum(int[] nums, int goal) {
        return atMostSum(nums, goal) - (goal - 1 > -1 ? atMostSum(nums, goal - 1) : 0);
        // goal > -1 check when the actual goal is 0 , so the goal becomes -1 at the
        // atMostFunction, which is not valid.
    }

    // b <=============== 2302. Count Subarrays With Score Less Than K ===========>
    // https://leetcode.com/problems/count-subarrays-with-score-less-than-k/description/

    // ! Same like Binary Subarrays With Sum. (Leetcode 930)

    // # Yahan pe hume nikalna hai ki score AtMost K se kam hone chahiye. Bas Baki
    // # logic is pura same.

    public long countSubarrays(int[] nums, long k) {

        int si = 0, ei = 0, n = nums.length;
        long sum = 0, totalCount = 0;

        while (ei < n) {
            sum += nums[ei++];

            while (sum * (ei - si) >= k) {
                sum -= nums[si++];
            }

            totalCount += ei - si;
        }
        return totalCount;
    }

    // b <=========== 485. Max Consecutive Ones ============>
    // https://leetcode.com/problems/max-consecutive-ones/

    // Same logic as above.
    // Jaise he 0 mila, window ko shrink kara jab tak wo dubara valid nhi ho jati.
    // Aur agar window valid hai to ei ++ karte raho, aur niche length ki update
    // karte raho.

    public int findMaxConsecutiveOnes(int[] nums) {

        int si = 0, ei = 0, len = 0, count = 0, n = nums.length;

        while (ei < n) {
            if (nums[ei++] == 0)
                count++;

            while (count == 1) {
                if (nums[si++] == 0)
                    count--;
            }
            len = Math.max(len, ei - si);
        }

        return len;
    }

    // ! More Optimized :

    // # Hume pta chala ki jaise he 0 mila, si aur ei ko dono ko ek sath rakh do
    // # next iteration mai. si ko ei tak traverse karne ki jarrorat he nhi hai.

    public int findMaxConsecutiveOnes_02(int[] nums) {

        int si = 0, ei = 0, len = 0, n = nums.length;

        while (ei < n) {
            if (nums[ei++] == 0)
                si = ei;
            len = Math.max(len, ei - si);
        }

        return len;
    }

    // b <================487 - Max Consecutive Ones II==============>
    // https://leetcode.ca/2017-03-31-487-Max-Consecutive-Ones-II/

    // # Is question ko aise reframe karo
    // # Ek aisa sub array ki length batao ki bas ek he zero allowed hai sare 1 ke
    // # sath.

    public int findMaxConsecutiveOnes_II(int[] nums) {

        int si = 0, ei = 0, len = 0, count = 0, n = nums.length;

        while (ei < n) {
            if (nums[ei++] == 0)
                count++;
            while (count == 2) {
                if (nums[si++] == 0)
                    count--;
            }
            len = Math.max(len, ei - si);
        }

        return len;
    }

    // b <================= 1004. Max Consecutive Ones III ============>
    // https://leetcode.com/problems/max-consecutive-ones-iii/

    // ? Same as . 487 - Max Consecutive Ones II
    // # Isko aise consider karo ki aisa subarray ki length batani hai jisme ones ke
    // # sath atmost k 0 ho sakte hain.

    public int longestOnes(int[] nums, int k) {

        int si = 0, ei = 0, len = 0, count = 0, n = nums.length;

        while (ei < n) {
            if (nums[ei++] == 0)
                count++;

            while (count > k) {
                if (nums[si++] == 0)
                    count--;
            }

            len = Math.max(len, ei - si);
        }

        return len;
    }

    // b <================ 974. Subarray Sums Divisible by K ==========>
    // https://leetcode.com/problems/subarray-sums-divisible-by-k/description/

    // Manle ek array hai aur uske do index hai. x1 and x2. (x2 > x1);
    // a is the sum of the elements upto x1 and b is the sum of the elements upto
    // x2.

    // Agar a % k =y and b % k=y, iska matlab ye hoga ki (b-a) ke beech jitne bhi
    // elements aaye hain, unka contribution is zero. Therefore ek subarray exist
    // karta hai jo divisible hai k se (b - a).

    // a + (b-a)=b
    // (a + (b-a)) % k =b % k
    // a % k + (b-a) % k = b % k;
    // y + (b-a) % k = y

    // (b-a) % k =0;

    // # That can only happen if b-a is the multiple of k.
    // # (k * (b-a) ) % k =0;

    // Hume ye bhi pata hai ki agar hum kisi number ko k se modulus karte hain to
    // `uski range, (0, k-1) tak hoti hai.

    // To hum har ek remainder value ki frequency nikalenge aur unke combination
    // nikalke sabko add kar denge.

    // Jaise ki merepe 4 remainder ki frequency total 3 aayi , to total subarray jo
    // divisible by k honge will be (3)(3-1)/2. Basically nC2. That will be 3.

    // Hum ise remainder frequecy ke sath sath bhi calculate kar sakte hain.
    // Manle mujhe 4 remainder, 3rd time mila, to ye wala 4 remainder pichele do 4
    // ke sath he to combination bana sakta hai, isiliye +2 kardo answer mai.

    // # One edge case :
    // What if the array is 5,5,5,5,5;

    // Then initially for first element, the answer shoulld be 1. because 5 itself
    // is a subarray that is divisible by 5. Therefore rem[0]=1 initially rakha hai,
    // ` baki remainder initially 0 honge.

    // ? Aise he karte - karte answer create kiya.

    // (a+b) % k= (a% k + b% k ) % k; == (a+b) % k= (a% k + b% k + k ) % k
    // (a-b) % k= (a% k - (b % k + k)) % k;

    // # here we can also store the index and print all the subarrays.
    // # if the two points are x1 and x2, (x2 > x1), index will be (x1,x2];
    // # including x2 and excluding x1.

    public int subarraysDivByK(int[] arr, int k) {
        int[] rem = new int[k];
        int n = arr.length, ei = 0, sum = 0, ans = 0;
        rem[0] = 1;
        while (ei < n) {
            sum += arr[ei++];
            int r = (sum % k + k) % k;// + k for -ve sum so that it comes in range.

            ans += rem[r];
            rem[r]++;
        }

        return ans;
    }

    // ? what if we have to calculate the length of longest subarray divisible by k.

    // Jo har remainder ki pehli occurance hogi, uske index ko store kar liye. To jo
    // longest hogi wo first occurance index - last occurance index to hogi.

    // 0 reminder ko -1 already rakha
    // For test case 1,2,2,
    // The sum array 1,3,5
    // Remainder 1,3,0

    // So the subarray will be 2 -(-1)=3

    public int longestSubarraysDivByK(int[] arr, int k) {
        int[] rem = new int[k];
        int n = arr.length, ei = 0, sum = 0, len = 0;
        Arrays.fill(rem, -2); // filled with -2 taki pata rahe ki first index mila ki nhi.
        rem[0] = -1;
        while (ei < n) {
            sum += arr[ei];
            int r = (sum % k + k) % k;

            if (rem[r] == -2) // Agar first occurance hai index ki ko store kiya,
                rem[r] = ei;
            else // else length ko calculate kiya.
                len = Math.max(len, ei - rem[r]);
            ei++;
        }

        return len;
    }

    // ! Using hashmap :

    public int subarraysDivByK_map(int[] arr, int k) {
        HashMap<Integer, Integer> rem = new HashMap<>();
        int n = arr.length, ei = 0, sum = 0, ans = 0;
        rem.put(0, 1);
        while (ei < n) {
            sum += arr[ei++];
            int r = (sum % k + k) % k;

            ans += rem.getOrDefault(r, 0);
            rem.put(r, rem.getOrDefault(r, 0) + 1);
        }

        return ans;
    }

    public int longestSubarraysDivByK_map(int[] arr, int k) {
        HashMap<Integer, Integer> rem = new HashMap<>();
        int n = arr.length, ei = 0, sum = 0, len = 0;
        rem.put(0, -1);
        while (ei < n) {
            sum += arr[ei];
            int r = (sum % k + k) % k;

            rem.putIfAbsent(r, ei);
            len = Math.max(len, ei - rem.get(r));
            ei++;
        }

        return len;
    }

    // b <=============560. Subarray Sum Equals K ==================>
    // https://leetcode.com/problems/subarray-sum-equals-k/description/

    // Manle ek array hai aur uske do index hai. x1 and x2. (x2 > x1);
    // a is the sum of the elements upto x1 and b+k is the sum of the elements upto
    // x2.

    // So jo elements aayenge x2 aur x1 ke beech mai, unka sum ke equal hoga.
    // So the subarray will be (x1, x2], excluding the x1 index and including the x2
    // index.

    // To mai har index tak ka sum nikalta gaya aur maine us sum ke occurance ka
    // count bhi store karta gaya.

    // Maine dekha ki sum - k khain piche already present to nhi hai. Agar hai to
    // ` uska jo count hoga wo mai subarray ke count mai add karta jaaunga. Maine
    // aisa isiliye kiya kyunki agar sum - k agar already piche present hai, iska
    // matlab wo ek aisa point hoga jisme agar mai k add karun to wo sum ke equal
    // aayega. Matlab ye jo beech ka subarray hai, uska sum k hai.

    // (sum - k) + (k) = sum;

    // Humne Already 0 ko 1 rakha hashmap mai taki hume agar starting se ek aisa
    // subarray mile jiska sum exactly k ke equal aaye to hum uska 1 add kardein.

    // # Dry run on [9,4,20,3,10,5] with k =33,
    // # Prefix sum [9,13,33,36,46,51];

    // # SubArray formed : [9,4,20], [20,3,10];
    // # index wise : [0,2], [2,4]

    // # here we can also store the index and print all the subarrays.
    // # if the two points are x1 and x2, (x2 > x1), index will be (x1,x2];
    // # including x2 and excluding x1.

    // ! Also here tow pointer approach will not work. Like we used k and k-1
    // ! approach. This is due to the fact that on increasing the window,sum
    // ! increase bhi kar sakta hai aur decrease bhi kar sakta hai. Why ? because
    // ! there are -ve elements as well.

    public int subarraySum(int[] nums, int k) {

        HashMap<Integer, Integer> map = new HashMap<>();
        int n = nums.length, ei = 0, sum = 0, totalSubArray = 0;
        map.put(0, 1);
        while (ei < n) {
            sum += nums[ei++];
            int diff = sum - k;
            if (map.containsKey(diff))
                totalSubArray += map.get(diff);

            if (map.containsKey(sum)) {
                map.put(sum, map.get(sum) + 1);
            } else {
                map.put(sum, 1);
            }
            // The above if else statement can be replaced by the below statement.
            // map.put(sum,map.getOrDefault(sum,0)+1);
        }

        return totalSubArray;
    }

    // B <=============Subarrays with equal 1s and 0s============>
    // https://practice.geeksforgeeks.org/problems/count-subarrays-with-equal-number-of-1s-and-0s-1587115620/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // Logic ye hai ki replace 0 with -1.
    // Ab same logic use karna hai.

    // Agar do index ka sum same hai, to iska matlab ye hai unke beech ke elements
    // ka contribution 0 hai. Therefore unke beech ke elements ek subarray
    // ` banayenge, jiska total sum 0 hoga. Hence hume whi count karne hai since we
    // have replaced 0 with -1 , so agar equal 0 or 1 hoge subarray mai to total sum
    // 0 aayega.

    static int countSubarrWithEqualZeroAndOne(int arr[], int n) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (arr[i] == 0)
                arr[i] = -1;
        }

        int ei = 0, totalSubArray = 0, sum = 0;
        map.put(0, 1);
        while (ei < n) {
            sum += arr[ei++];

            if (map.containsKey(sum)) {
                totalSubArray += map.get(sum);
            }

            map.put(sum, (map.get(sum) == null ? 0 : map.get(sum)) + 1);

        }
        return totalSubArray;
    }

    // # Bhaiya Code of same complexity

    static int countSubarrWithEqualZeroAndOne_(int arr[], int n) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int ei = 0, count = 0, sum = 0;

        while (ei < n) {
            int val = arr[ei++];
            sum += val;
            if (val == 0)
                sum += -1;

            count += map.getOrDefault(sum, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return count;
    }

    // b <===========525. Contiguous Array ==============>
    // https://leetcode.com/problems/contiguous-array/description/

    // Same uper wala concept. Bas hume yahan pe largest length niklani hai subarray
    // ki. To sare sum ka first index karenge store or agar wo sum dubara khin pe
    // repeat hua to uski length nikalke compare karte rahenge.

    public int findMaxLength(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (arr[i] == 0)
                arr[i] = -1;
        }

        int ei = 0, sum = 0, len = 0;
        map.put(0, -1); //
        while (ei < n) {
            sum += arr[ei];
            if (map.containsKey(sum)) {
                len = Math.max(len, ei - map.get(sum));
            } else {
                map.put(sum, ei);
            }
            ei++;
        }
        return len;
    }

    // # Bhaiya Code of same complexity :

    public int findMaxLength_(int[] arr) {

        // rem, firstIndex
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int n = arr.length, ei = 0, sum = 0, len = 0;

        while (ei < n) {
            int val = arr[ei];
            sum += val;
            if (val == 0)
                sum += -1;

            map.putIfAbsent(sum, ei); // if(map.find(sum) == map.end()) map[sum] = ei;
            len = Math.max(len, ei - map.get(sum));
            ei++;
        }

        return len;
    }

    // b <============== Sliding Window Maximum ===================>
    // https://leetcode.com/problems/sliding-window-maximum/

    // ! Time = > O(KN), Space : O(1)

    // It gives TLE

    // # Har ek Window ke liye max nikalte rahe in another loop.

    public int[] maxSlidingWindow_KN(int[] nums, int k) {

        int n = nums.length;
        int[] ans = new int[n - k + 1];

        int idx = 0;
        for (int i = 0; i + k <= n; i++) {
            int max = -(int) (1e9);
            for (int j = i; j < i + k; j++) {
                int val = nums[j];
                max = Math.max(max, val);
            }
            ans[idx++] = max;
        }
        return ans;
    }

    // ! Time : O(NlogN), Space : O(N)

    // # Main ek element ko uske index ke sath priority queue mai dalta rahunga. Ab
    // # maine element nikala priority queue se. Agar us priority queue ke element
    // # ka index meri window ke index ke andar aaata hai to whi mera max element
    // # hai nhi to mai priority queue se elements nikalta rahunga.

    // ! If doing this question in interview, use class pair instead of array as it
    // ! is more easy to understand.

    public int[] maxSlidingWindow(int[] nums, int k) {

        int n = nums.length;
        // index, value
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            return b[1] - a[1]; // To get max element according to the value.
        });
        for (int i = 0; i < k - 1; i++) {
            int value = nums[i];
            pq.add(new int[] { i, value });
        }
        int[] ans = new int[n - k + 1];
        int idx = 0;
        for (int i = 0; i + k <= n; i++) {
            int startIndex = i, endIndex = i + k - 1;
            int value = nums[endIndex];
            pq.add(new int[] { endIndex, value });

            while (pq.size() != 0 && pq.peek()[0] < startIndex) {
                pq.remove();
            }
            ans[idx++] = pq.peek()[1];
        }
        return ans;
    }

    // ! Time : O(NlogN), Space : O(N)

    // Same complexity, but slightly better code.
    // Rather than adding an integer array in priority queue, add index.

    // PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
    // return nums[b] - nums[a];
    // # The above line makes does the same work. Will return index whose value is
    // # largest.
    // });

    // Concept is same as above.

    public int[] maxSlidingWindow_codeBetter(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
            return nums[b] - nums[a]; //
        });

        int n = nums.length, idx = 0;
        int[] ans = new int[n - k + 1];
        for (int i = 0; i < nums.length; i++) {
            while (pq.size() != 0 && pq.peek() <= i - k)
                pq.remove();

            pq.add(i);

            if (i >= k - 1)
                ans[idx++] = nums[pq.peek()];
        }
        return ans;
    }

    // ! Time : O(Nlogk), Space : O(k)

    // # Using TreeMap, At a time, in our map, we have at max k elements so the time
    // # of retrival will be logK

    public int[] maxSlidingWindow_TreeMap(int[] nums, int k) {

        int n = nums.length, idx = 0;
        int[] ans = new int[n - k + 1];

        // value, frequency of the value
        TreeMap<Integer, Integer> map = new TreeMap<>();

        for (int i = 0; i < k - 1; i++) {
            int value = nums[i];
            if (map.containsKey(value)) {
                map.put(value, map.get(value) + 1);
            } else
                map.put(value, 1);
        }
        for (int i = 0; i + k <= n; i++) {
            int startIndex = i, endIndex = i + k - 1;
            int value = nums[endIndex];
            if (map.containsKey(value)) {
                map.put(value, map.get(value) + 1);
            } else
                map.put(value, 1);

            ans[idx++] = map.lastKey();
            int notRequiredValue = nums[startIndex]; // Jaise he humne window ka max nikala, wiase he ab hume startIndex
                                                     // ki requirement nhi hai, kyunki wo next window ka part nhi
                                                     // `banega.
            if (map.get(notRequiredValue) == 1)
                map.remove(notRequiredValue);
            else
                map.put(notRequiredValue, map.get(notRequiredValue) - 1);// Agar repeated elements honge window mai, to
                                                                         // `unki frequency jyada hogi. [5,5,5,5,5,5]
        }
        return ans;
    }

    // ! Time : O(N), Space : O(1)

    // # Deque has all property of queue. But one extra property, we can remove from
    // # last also, unlike queue.

    // # Sabse bada element jo abhi tak mila hai wo front pe rahega.

    public int[] maxSlidingWindow_ON(int[] nums, int k) {
        LinkedList<Integer> deque = new LinkedList<>();

        int n = nums.length, idx = 0;
        int[] ans = new int[n - k + 1];
        for (int i = 0; i < nums.length; i++) {
            while (deque.size() != 0 && deque.getFirst() <= i - k)
                // Agar koi mera index window range se he bahar hai, to use remove he kardo,
                // kyunki wo kabhi bhi mere window ka max nhi ho sakta.
                deque.removeFirst();

            while (deque.size() != 0 && nums[i] >= nums[deque.getLast()])
                // Agar koi index aisa aaya jiski value greater hai deque mai rakhi hui index ki
                // value se, to jo rakhi hui value hai wo kabhi khi bhi aane wali windom mai max
                // nhi hogi, to use remove kardo.
                deque.removeLast();

            deque.addLast(i);

            if (i >= k - 1)
                ans[idx++] = nums[deque.getFirst()];
        }
        return ans;
    }

    // ! Kadanes Algo

    // # It is used to get max-subarray sum in an array.

    // ? Case 1 : [-1,-7,-8,-9] -> max sum Subarray is 0(no subarray exist);

    // # Jab tak mera sum 0 nhi hota, mai us umeeed mai aage badta rahunga ki muhje
    // # koi aage +ve number mil jaye jiski wajah se mera sum greater ho jaye.

    // # Jaise he mujhe zero mila, ab jo current subarray hai uska contribution to
    // # zero hai. To us subarray mai add karne ki jaroorat he nhi hai. Next
    // # iteration se nya subarray ka sum start karenge.

    // Agar sirf -ve elements hain array mai to answer 0 aayega.
    // Test case to dry run : [-2,-3,4,-1,-2,1,5,-3]
    public static int kadanesAlgo(int[] arr) {
        int gSum = 0, currSum = 0;
        for (int ele : arr) {
            currSum += ele;
            if (currSum > gSum)
                gSum = currSum;
            if (currSum <= 0)
                currSum = 0;
        }
        return gSum;
    }

    // ! Now what if I wanted to get the subarray :

    public static int kadanesAlgo_SubArray(int[] arr) {

        int gSum = 0, currSum = 0, gsi = 0, gei = 0, csi = 0;
        for (int i = 0; i < arr.length; i++) {
            int ele = arr[i];
            currSum += ele;
            if (currSum > gSum) {
                gSum = currSum;
                gsi = csi;
                gei = i;
            }

            if (currSum <= 0) {
                currSum = 0;
                csi = i + 1;
            }
        }

        return gSum;
    }

    // ? Case 2 : when -ve number can be maximum

    // [-1,-7,-8,-9] -> max sum Subarray if -1 (0,0);
    public static int kadanesAlgoGeneric(int[] arr) {
        int gSum = -(int) 1e9, cSum = 0;
        for (int ele : arr) {
            cSum = Math.max(ele, cSum + ele);
            gSum = Math.max(gSum, cSum);
        }

        return gSum;
    }

    public static int[] kadanesAlgoGenericSubarray(int[] arr) {
        int gSum = -(int) 1e9, cSum = 0, gsi = 0, gei = 0, csi = 0;
        for (int i = 0; i < arr.length; i++) {
            int ele = arr[i];
            cSum += ele;
            if (ele >= cSum) {
                cSum = ele;
                csi = i;
            }

            if (cSum > gSum) {
                gSum = cSum;
                gsi = csi;
                gei = i;
            }
        }

        return new int[] { gSum, gsi, gei };
    }

    // b <=============== K-Concatenation Maximum Sum ==============>
    // https://leetcode.com/problems/k-concatenation-maximum-sum/

    // Kadanes algo chalaya k=3 tak ek liye. Ab agar uske baad koi 3 se bada k hoga,
    // to uske liye jo formula derive kiya hai wo laga diya.

    // Yahan pe teen cases ho sakte hain :

    // 1. Jab k=1 ke liye thoda arraya contribute kare s1, k=2 ke liye s1 + s2
    // contribute kare, k=3 ke liye s1 + s2 + x contribute kare.
    // 2. Pura Array contribute kare
    // 3. Ki thoda array contribute kare. Aur kitne bhi array concat ho jayein, utna
    // he contribution aayega.

    static int mod = (int) 1e9 + 7;

    public int kadanesSum(int[] arr, int k) {
        int n = arr.length;
        long gsum = 0, csum = 0;
        int mod = (int) 1e9 + 7;

        for (int i = 0; i < k * n; i++) {
            int ele = arr[i % n];
            csum += ele;

            if (csum > gsum)
                gsum = csum;
            if (csum <= 0)
                csum = 0;
        }

        return (int) gsum % mod;
    }

    public int kConcatenationMaxSum(int[] arr, int k) {
        long prevSum = 0, sum = 0;
        for (int i = 1; i <= 3; i++) {
            prevSum = sum;
            sum = kadanesSum(arr, i);

            if (i == k)
                return (int) sum;
        }

        return (int) ((prevSum + (k - 2) * (sum - prevSum)) % mod);
    }

    // b <=============== Maximum sum Rectangle =================>
    // https://practice.geeksforgeeks.org/problems/maximum-sum-rectangle2948/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article

    // # Yahan pe bhi same kadanes algo use kiya hai, jisme answer -ve bhi aata hai.

    // [[1,2,-1,-4,-20],
    // [-8,-3,4,2,1],
    // [3,8,10,1,3],
    // [-4,-1,1,7,-6]]

    // Ek rectangle tabhi create hota hai, jab uske angle 90 degree ke hote hain.
    // To basically maine har ek rectangle ka sum nikalke, usme kadane algo lagayi
    // taki mujhe max sum mike us rectangle ka.

    // Aur aisa maine har ek possible rectangle ke sath kiya.

    // Sare rectangle nikalne ke liye maine pehle pehle top row ko fix kiya, aur
    // ` bottom tak jitne bhi rectangle create ho sakte the, unpe kadanes algo
    // chalayi.

    // Next iteration mai maine same 2nd row ko fix kiya aur end row tak jitne bhi
    // rectangle create ho sakte the sabpe kadanes algo chalayi.

    // Aisa maine sabke liye kiya aur end mai max return kar diya.

    // # Important point :

    // Agar row 1 aur row 2 se ek pura rectangle create hota hai to, kadanes ke liye
    // ` uske jo array aayego wo dono row ke same index ke elements ke sum ka array
    // aayega

    public static int kadanesAlgoForNegative(int[] arr) {
        int gSum = -(int) 1e9, cSum = 0;
        for (int ele : arr) {
            cSum = Math.max(ele, cSum + ele);
            gSum = Math.max(gSum, cSum);
        }

        return gSum;
    }

    // Time Complxity : T(n*m*n)
    int maximumSumRectangle(int R, int C, int arr[][]) {
        int n = R, m = C, maxSum = -(int) 1e9;
        for (int fixRow = 0; fixRow < n; fixRow++) { // Fixing row
            int[] colPrefixSum = new int[m];

            for (int row = fixRow; row < n; row++) {
                for (int col = 0; col < m; col++)
                    colPrefixSum[col] += arr[row][col]; // Creating array ko kadanes

                int sum = kadanesAlgoForNegative(colPrefixSum);
                maxSum = Math.max(maxSum, sum);

            }
        }

        return maxSum;
    }

    // ! if we want to print matrix

    // # Simple kadanes se max sum ka index mangaya aur values ko update mar diya.
    int maximumSumRectangle_02(int R, int C, int arr[][]) {
        int n = R, m = C, maxSum = -(int) 1e9;
        int[] colPrefixSum = new int[m];

        int r1 = 0, c1 = 0, r2 = 0, c2 = 0;

        for (int fixRow = 0; fixRow < n; fixRow++) {

            Arrays.fill(colPrefixSum, 0);

            for (int row = fixRow; row < n; row++) {
                for (int col = 0; col < m; col++)
                    colPrefixSum[col] += arr[row][col];

                int[] res = kadanesAlgoGenericSubarray(colPrefixSum);
                if (res[0] >= maxSum) {
                    maxSum = res[0];
                    r1 = fixRow;
                    c1 = res[1];
                    r2 = row;
                    c2 = res[2];
                }
            }
        }

        for (int i = r1; i <= r2; i++) {
            for (int j = c1; j <= c2; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }

        return maxSum;
    }

    // b <============ 85. Maximal Rectangle ============>
    // https://leetcode.com/problems/maximal-rectangle/description/

    // # Logic is same as above;

    public static int getMaxArea(int[] arr) {
        int maxArea = 0, sum = 0;

        for (int ele : arr) {
            if (ele == 0)
                sum = 0;
            else
                sum += ele;
            maxArea = Math.max(maxArea, sum);
        }

        return maxArea;
    }

    public int maximalRectangle(char[][] arr) {

        int n = arr.length, m = arr[0].length;
        int total = 0;
        for (int fixRow = 0; fixRow < n; fixRow++) {
            int[] colPrefixSum = new int[m];
            for (int row = fixRow; row < n; row++) {
                for (int col = 0; col < m; col++) {
                    if (row == fixRow) {
                        colPrefixSum[col] += arr[row][col] - '0';
                    } else {
                        if (colPrefixSum[col] == 0)
                            continue;
                        else if (arr[row][col] == '0')
                            colPrefixSum[col] = 0;
                        else
                            colPrefixSum[col] += 1;
                    }
                }

                total = Math.max(total, getMaxArea(colPrefixSum));
            }
        }

        return total;
    }

    // b <=========== 1074. Number of Submatrices That Sum to Target =============>
    // https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/description/

    // # Concept is used same as above.
    // Pehle ek row ko fix kiya, sare combination nikale sum row ko add karke aur
    // har ek ke liye subArraySumEqualK call kiya.

    // End mai total sum return kar diya.

    // ! Important point :

    // ? Agar khin bhi matrix sum ya rectange sum pucha jaye, to ye fix row wala
    // ? logic use kar sakte hain.

    public static int subArraySumEqualK(int[] arr, int tar) { // Ek array mai wo sare subarray nikale jinka sum tar ke
                                                              // equal ho. Same as Leetcode 560
        HashMap<Integer, Integer> map = new HashMap<>();
        int sum = 0, totalSubArray = 0;
        map.put(0, 1);
        for (int ele : arr) {
            sum += ele;
            int diff = sum - tar;

            if (map.containsKey(diff))
                totalSubArray += map.get(diff);
            if (map.containsKey(sum))
                map.put(sum, map.get(sum) + 1);
            else
                map.put(sum, 1);

        }
        return totalSubArray;
    }

    public int numSubmatrixSumTarget(int[][] arr, int tar) {

        int n = arr.length, m = arr[0].length;
        int total = 0;
        for (int fixRow = 0; fixRow < n; fixRow++) {
            int[] colPrefixSum = new int[m];
            for (int row = fixRow; row < n; row++) {
                for (int col = 0; col < m; col++) {
                    colPrefixSum[col] += arr[row][col];
                }

                total += subArraySumEqualK(colPrefixSum, tar);
            }
        }

        return total;
    }

    // b <========= 781. Rabbits in Forest ===========>
    // https://leetcode.com/problems/rabbits-in-forest/description/

    // [10,10,10]

    // For the above test case, there can be total 11 people that can say that they
    // have another 10 that have the same colour.

    public int numRabbits(int[] answers) {
        int[] ans = new int[10001];
        int minRabbits = 0;
        for (int ele : answers) {
            if (ans[ele] == 0) // If first occurance, then there will be minimum that ele + 1 amount of rabbit.
                               // +1 for mine.
                minRabbits += ele + 1;
            ans[ele]++;

            if (ans[ele] == ele + 1) { // If the total ele frequency has reached the ele + 1, then the all the people
                                       // have been found. If now someone claims to have same ele value, then it will
                                       // definitely be of another color.
                ans[ele] = 0;
            }
        }

        return minRabbits;
    }

    public int numRabbits_(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = arr.length, ans = 0;

        for (int ele : arr) {

            if (!map.containsKey(ele)) {
                ans += 1 + ele;
                map.put(ele, 1);
            } else {
                map.put(ele, map.get(ele) + 1);
            }

            if (map.get(ele) == ele + 1) {
                map.remove(ele);
            }
        }

        return ans;
    }

    public int kadanesAlgoWithSumUnderK(int[] arr, int k) {
        int gsum = -(int) 1e9, csum = 0;
        for (int ele : arr) {
            csum += ele;
            csum = Math.max(csum, ele);
            gsum = Math.max(gsum, csum);

            if (gsum >= k)
                return gsum;
        }

        return gsum;
    }

    public int maxSumSubmatrix(int[][] arr, int k) {
        int n = arr.length, m = arr[0].length;
        int maxRes = 0;

        for (int fixedRow = 0; fixedRow < n; fixedRow++) {

            int[] prefixColArray = new int[m];
            for (int row = fixedRow; row < n; row++) {
                for (int col = 0; col < m; col++)
                    prefixColArray[col] += arr[row][col];

                int sum = kadanesAlgoWithSumUnderK(prefixColArray, k);

                if (sum == k)
                    return sum;
                else if (sum < k) {
                    maxRes = Math.max(maxRes, sum);
                    continue;
                }

                // ????
                // # We have to add the condition if the kadanes sum is greater than k
                // For Test case :

                // [2,2,-1] with k=0, the ans will be -1.
                // [2,2,-2,1] with k=0, the ans will be -1.

            }
        }

        return maxRes;

    }

}