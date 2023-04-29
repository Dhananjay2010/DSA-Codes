import java.util.HashSet;

public class HashMapQuestions {

    // b <============128. Longest Consecutive Sequence ===========>
    // https://leetcode.com/problems/longest-consecutive-sequence/description/

    // # Simple kaam kiya hai.
    // # Pehle sare elements ko dala set mai.

    // Ab koi element hai uska mai prev aur next set kiya.
    // Maine while loop mai dekha ki mai prev -- kab tak present hai hashset mai.
    // same maine next ke liye kiya.

    // At the end, mujhe mera length mil jayegi.

    // # Hum set se remove isiliye kar rahe hain, since isse searching improve hogi
    // # hashset mai.

    // ! Important Point

    // for(int ele : set) == > We cannot use this statement when we have to remove
    // element from hashSet int the same loop.
    // Doing this gives concurrent modification exception.


    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();

        for (int ele : nums)
            set.add(ele);

        int max = 0;
        for (int ele : nums) {
            if (set.contains(ele)) {
                set.remove(ele);
                int prev = ele - 1, next = ele + 1;
                while (set.contains(prev))
                    set.remove(prev--);

                while (set.contains(next))
                    set.remove(next++);

                max = Math.max(max, next - prev - 1);
            }
        }

        return max;
    }

}
