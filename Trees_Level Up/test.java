public class test {

    public int[] findMode(TreeNode root) {

        if (root == null) {
            return new int[0];
        }

        ArrayList<Integer> ans = new ArrayList<>();
        int currentCount = 0;
        int maxCount = 0;
        int prev = -(int) 1e9;
        TreeNode curr = root;
        while (curr != null) {

            TreeNode left = curr.left;
            if (left == null) {
                // print;
                if (prev != -(int) 1e9 && prev != curr.val) {
                    if (currentCount == maxCount) {
                        ans.add(prev);
                    } else if (currentCount > maxCount) {
                        maxCount = currentCount;
                        ans.clear();
                        ans.add(prev);
                    }
                    currentCount = 0;
                }
                currentCount++;
                prev = curr.val;
                curr = curr.right;
            } else {

                TreeNode currKeLeftKaRightMost = getRightMost(left, curr);

                if (currKeLeftKaRightMost.right == null) {
                    currKeLeftKaRightMost.right = curr;
                    curr = curr.left;
                } else {
                    currKeLeftKaRightMost.right = null;
                    if (prev != -(int) 1e9 && prev != curr.val) {
                        if (currentCount == maxCount) {
                            ans.add(prev);
                        } else if (currentCount > maxCount) {
                            maxCount = currentCount;
                            ans.clear();
                            ans.add(prev);
                        }
                        currentCount = 0;
                    }
                    curr = curr.right;
                }
            }
        }

        if (currentCount == maxCount) {
            ans.add(prev);
        } else if (currentCount > maxCount) {
            maxCount = currentCount;
            ans.clear();
            ans.add(prev);
        }

        int[] arr = new int[ans.size()];

        for (int i = 0; i < ans.size(); i++) {
            arr[i] = ans.get(i);
        }

        return arr;
    }

}
