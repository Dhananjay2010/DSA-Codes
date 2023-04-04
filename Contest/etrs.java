public class etrs {

    public boolean searchMatrix(int[][] matrix, int target) {

        int n = matrix.length, m = matrix[0].length, r = n - 1, c = 0;

        while (r >= 0 && c >= 0 && r < n && c < m) {
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
}
