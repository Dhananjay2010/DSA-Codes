public class LeetcodeContest {

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
}
