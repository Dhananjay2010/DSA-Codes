import java.util.HashSet;

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

}