import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class l0005_viewSet {

    public static class TreeNode {

        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this(val, null, null); // constructor chaining
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // b <================Level Order Traversal of Binary Tree ==============>

    public List<List<Integer>> levelOrder(TreeNode root) {

        List<List<Integer>> ans = new ArrayList<>();
        LinkedList<TreeNode> que = new LinkedList<>();
        // add last, remove first

        que.addLast(root);

        while (que.size() != 0) {
            int size = que.size();
            ArrayList<Integer> myAns = new ArrayList<>();
            while (size-- > 0) {
                TreeNode rn = que.removeFirst();

                if (rn == null) {
                    continue;
                }

                myAns.add(rn.val);
                que.addLast(rn.left);
                que.addLast(rn.right);
            }
            if (myAns.size() > 0) {
                ans.add(myAns);
            }
        }
        return ans;
    }

    public static void levelOrderLineWise(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);
        int level = 0;

        while (que.size() != 0) {
            int size = que.size();
            System.out.print("Level " + level + ": ");
            while (size-- > 0) {
                TreeNode rn = que.removeFirst();

                System.out.print(rn.val + ", ");
                if (rn.left != null) // Yahan pe check karke dal rahe hain to jaroorat nhi hai continue likhne ki
                    que.addLast(rn.left);
                if (rn.right != null)
                    que.addLast(rn.right);
            }
            level++;
        }
    }

    // b <=====================Left View of Binary Tree ================>
    // https://practice.geeksforgeeks.org/problems/left-view-of-binary-tree/1

    // * Basically the logic is that the in level order traversal, the first element
    // * of every level is the left view of the tree.

    // ? For Test case : 8,3,10,1,6,null,14,null,null,4,7,13,null (Level order of a
    // ? test case to get a tree)

    // So the level order will be :
    // level 0 : 8
    // level 1 : 3,10
    // level 2 : 1,6,14
    // level 3 : 4,7,13

    // So the answer will be [8,3,1,4]

    public static ArrayList<Integer> leftView(TreeNode root) {

        if (root == null)
            return new ArrayList<>();
        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);

        ArrayList<Integer> ans = new ArrayList<>();
        while (que.size() != 0) {
            int size = que.size();
            ans.add(que.getFirst().val);
            while (size-- > 0) {
                TreeNode rn = que.removeFirst();

                if (rn.left != null)
                    que.addLast(rn.left);
                if (rn.right != null)
                    que.addLast(rn.right);
            }

        }

        return ans;
    }

    // * Dfs Method :

    // Left View using normal dfs.
    // just a static variable to maintain the check of the level visited.

    // Other than this, this is the simple dfs preorder call.

    static int maxLevel = -1; // ? can take one size array instead of this

    public static void leftView_dfs(TreeNode root, int level, ArrayList<Integer> ans) {

        if (root == null)
            return;

        if (level > maxLevel) {
            ans.add(root.val);
            maxLevel = level;
        }

        leftView_dfs(root.left, level + 1, ans);
        leftView_dfs(root.right, level + 1, ans);

    }

    ArrayList<Integer> leftView_dfs(TreeNode root) {
        // Your code here
        maxLevel = -1;
        ArrayList<Integer> ans = new ArrayList<>();
        leftView_dfs(root, 0, ans);

        return ans;
    }

    // b <================== Binary Tree Right Side View ===================>
    // https://leetcode.com/problems/binary-tree-right-side-view/

    // Just printing the last element in each level in level order gives us the
    // right side view

    public List<Integer> rightSideView(TreeNode root) {

        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> ans = new ArrayList<>();
        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);

        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                TreeNode rn = que.removeFirst();

                if (size == 0) {
                    ans.add(rn.val);
                }
                if (rn.left != null) {
                    que.addLast(rn.left);
                }

                if (rn.right != null) {
                    que.addLast(rn.right);
                }
            }
        }

        return ans;
    }

    // ? Bhaiya Method

    // Just have inserted the right child first and then the left child.
    // So therefore the first element in the every level now is the right side view

    public static void rightView(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);

        ArrayList<Integer> ans = new ArrayList<>();
        while (que.size() != 0) {
            int size = que.size();
            ans.add(que.getFirst().val);
            while (size-- > 0) {
                TreeNode rn = que.removeFirst();

                if (rn.right != null)
                    que.addLast(rn.right);
                if (rn.left != null)
                    que.addLast(rn.left);
            }

        }
    }

    // * Dfs method :

    // ` Used same concept as above dfs for right view
    // Just made the right call first to get the right side view

    public void rightSideView_dfs(TreeNode root, int level, int[] maxLevel, List<Integer> ans) {

        if (root == null)
            return;

        if (level > maxLevel[0]) {
            ans.add(root.val);
            maxLevel[0] = level;
        }

        rightSideView_dfs(root.right, level + 1, maxLevel, ans);
        rightSideView_dfs(root.left, level + 1, maxLevel, ans);
    }

    public List<Integer> rightSideView_dfs(TreeNode root) {

        if (root == null)
            return new ArrayList<>();

        List<Integer> ans = new ArrayList<>();
        int[] arr = new int[] { -1 };
        rightSideView_dfs(root, 0, arr, ans);

        return ans;

    }

    // ! Important Point to note : See the one note section

    // ? If told to consider the 45° of tree gometry, then the left and right view
    // ? might change.

    // b <=================Vertical Order of Binary Tree =================>

    // Given the root of a binary tree, calculate the vertical order traversal of
    // the binary tree.

    // For each node at position (row, col), its left and right children will be at
    // positions (row + 1, col - 1) and (row + 1, col + 1) respectively. The root of
    // the tree is at (0, 0).

    // ? Left child will be at an angle of -45° and right child will be at an angle
    // ? of +45°. It is like plotting the tree in graph.

    // To do this, first we have to calculate the width of shadow of the tree.

    // Shadow means that if at the top of the tree there is light, so we have to
    // calculate the widht of the shadow that will be formed at the bottom

    // width[0] indicates the max width on left side {in -ve}
    // widht[1] indicatest the max width on right side { in +ve}

    // So the total width will be width[1] - width[0] + 1
    // So we are going to create Arraylist with this total width size.

    // Since each index represent the vertical order, so we will initialize an
    // arraylist in each index to store the nodes value in each level

    // After we have calculated the width of shadow of the tree, what we will do is
    // apply normal bfs.

    // So basically what we are going to do is insert the verticalPair in the queue.

    // Vertical pair has basically a node and vl.
    // vl is for the storing the vertical level of the current node.

    // ! Important point to note :

    // We will be assigning the root node with the level of -width[0] since it is
    // the maximum that we can go to the left. Therefore -width[0] will be a +ve
    // value.

    // Ye isiliye kiya kyunki arraylist mai -ve index nhi hote hain aur hume
    // leftmost vertical level 0 mile

    // ! Important point :

    // Don't try to solve vertical order traversal with dfs(recursion).
    // ? It fails for following test case.

    // Test case : [5,7,6,null,8,null,null,null,9]

    // The actual vertical order for this should be :

    // 0 : 7
    // 1 : 5,8
    // 2 : 6,9

    // ` But in dfs, we have our left call first and then the second call, we get
    // the order as

    // 0 : 7
    // 1 : 5,8
    // 2 : 9, 6

    // The second level comes wrong due to this.

    // ? Therefore we don't try to solve it with dfs. We can solve it but more and
    // ? more conditions have to be checked and that is of no use.

    public static void widthOfShadow(TreeNode root, int verticalLevel, int[] width) {

        if (root == null) {
            return;
        }

        if (verticalLevel < 0 && verticalLevel < width[0]) {
            width[0] = verticalLevel;
        }
        if (verticalLevel > 0 && verticalLevel > width[1]) {
            width[1] = verticalLevel;
        }

        widthOfShadow(root.left, verticalLevel - 1, width);
        widthOfShadow(root.right, verticalLevel + 1, width);
    }

    public static class verticalPair {
        int vl = 0;
        TreeNode node = null;

        verticalPair(TreeNode node, int vl) {
            this(node);
            this.vl = vl;
        }

        verticalPair(TreeNode node) {
            this.node = node;
        }
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {

        if (root == null) {
            return new ArrayList<>();
        }

        int[] width = new int[] { 0, 0 };

        widthOfShadow(root, 0, width);

        int totalWidth = width[1] - width[0] + 1;
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < totalWidth; i++) {
            ans.add(new ArrayList<>());
        }

        LinkedList<verticalPair> que = new LinkedList<>();
        que.addLast(new verticalPair(root, -width[0]));

        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                verticalPair rn = que.removeFirst();
                TreeNode node = rn.node;
                int vl = rn.vl;

                ans.get(rn.vl).add(rn.node.val);

                if (node.left != null) {
                    que.addLast(new verticalPair(node.left, vl - 1)); // left wala node insert kiya to vertical level se
                                                                      // -1 kiya
                }
                if (node.right != null) {
                    que.addLast(new verticalPair(node.right, vl + 1));// right wala node insert kiya to vertical level
                                                                      // se +1 kiya
                }
            }
        }

        return ans;

    }

    // ? Bhaiya code : Code written more beautifully

    // {min,max}
    public static void widthOfShadow_bhaiya(TreeNode root, int vl, int[] minMax) {
        if (root == null)
            return;

        minMax[0] = Math.min(minMax[0], vl);
        minMax[1] = Math.max(minMax[1], vl);

        widthOfShadow_bhaiya(root.left, vl - 1, minMax);
        widthOfShadow_bhaiya(root.right, vl + 1, minMax);
    }

    public static class vpair {
        TreeNode node = null;
        int vl = 0;

        vpair(TreeNode node, int vl) {
            this.node = node;
            this.vl = vl;
        }
    }

    public static ArrayList<ArrayList<Integer>> verticalOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        if (root == null)
            return ans;

        int[] minMax = new int[2];
        widthOfShadow(root, 0, minMax);
        int width = minMax[1] - minMax[0] + 1;
        for (int i = 0; i < width; i++)
            ans.add(new ArrayList<>());

        LinkedList<vpair> que = new LinkedList<>();
        que.addLast(new vpair(root, Math.abs(minMax[0])));

        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {
                vpair p = que.removeFirst();
                TreeNode node = p.node;
                int vl = p.vl;

                ans.get(vl).add(node.val);
                if (node.left != null)
                    que.addLast(new vpair(node.left, vl - 1));
                if (node.right != null)
                    que.addLast(new vpair(node.right, vl + 1));
            }
        }

        return ans;
    }

    // b <==================== Bottom View of Tree =========================>
    // https://practice.geeksforgeeks.org/problems/bottom-view-of-binary-tree/1

    // ? Basically the bottom view is just the last element in the vertical order of
    // ? every column.

    // So basically updating the value in the array for each vertical level will
    // give us the bottom view.

    // So we did the same thing as done in vertical order above.

    // Considering the test case :
    // [8,3,10,1,6,11,14,null,null,4,7,15,16,13,null]

    // Here we have considered the last node in level order(for same vertical level)
    // to be the part of bottom view

    // ? In the above test case, [7,16,13] are in the bottom most of vertical level
    // ? 3. So we have taken 13 as a part of bottom view since the it comes last in
    // ? the level order. Hence we have solved the problem taking this into
    // ? consideration.

    // ! Important point to Note : Solved below the normal botttom view

    // Conditionally if asked that we need all the nodes occuring in the bottom most
    // of a vertical level, so what we need to do now is keep the horizontal level
    // also with us.

    // So now what the question want is that if [7,16,13] are in the bottom most of
    // vertical level 3, then all the three elements 7, 16,13 should be stored in
    // the arraylist. Not just one element.

    // So how to keep track of previous horizontal level???

    // One Way would be to store in the vertical pair class that we are using. But
    // ` it will take space. So what we should do ??

    // ` Best way would be to maintain an array of size total Width and update the
    // array when adding the value to the arraylist.

    // Taking an example of vertical level 3 in above tree.
    // We will have array of array list

    // Firstly we will insert 10 in the array list index 3. Now we will update the
    // horizontal array 3rd index with 1(indicating the horizontal level)

    // Now 7 will come first due to level order for the sam vertcal level3.
    // Now because 7 has greater horizontal level, we are going to remove 10 by
    // clearing the arraylist and add 7 to the arraylist. We will also update the
    // horozontal level array with the current horizontal level for vertical level
    // index.

    // Now 16 will come and since it has horizontal level same as the previous, we
    // will add it to the same list.

    // Hence the logic continues.

    // ! Basically what we need is three variables : Node, vertical Level ,
    // ! Horizontal level to solve any type of problem in bottom view.

    // We can be asked to just to have max of [7,16,13] for the bottom view,
    // Anything can be solved using the 3 variables.

    public static void shadowOfTree(TreeNode root, int verticalLevel, int[] width) {

        if (root == null) {
            return;
        }

        width[0] = Math.min(width[0], verticalLevel);
        width[1] = Math.max(width[1], verticalLevel);

        shadowOfTree(root.left, verticalLevel - 1, width);
        shadowOfTree(root.right, verticalLevel + 1, width);
    }

    public static class bottomVerticalPair {

        int vl = 0;
        TreeNode node = null;

        bottomVerticalPair(TreeNode node, int vl) {
            this.node = node;
            this.vl = vl;
        }
    }

    public ArrayList<Integer> bottomView(TreeNode root) {

        if (root == null) {
            return new ArrayList<>();
        }

        int[] width = new int[] { 0, 0 };
        shadowOfTree(root, 0, width);

        int totalWidth = width[1] - width[0] + 1;
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < totalWidth; i++) {
            ans.add(null);
        }

        LinkedList<bottomVerticalPair> que = new LinkedList<>();
        que.addLast(new bottomVerticalPair(root, Math.abs(width[0])));

        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                bottomVerticalPair rn = que.removeFirst();
                TreeNode node = rn.node;
                int vl = rn.vl;

                ans.set(vl, node.val); // Just updating the value in a particular level.
                if (node.left != null) {
                    que.addLast(new bottomVerticalPair(node.left, vl - 1));
                }
                if (node.right != null) {
                    que.addLast(new bottomVerticalPair(node.right, vl + 1));
                }

            }
        }

        return ans;
        // Code here
    }

    // ? Bottom View with all value at same level

    public ArrayList<ArrayList<Integer>> bottomView_allValue_at_horizontal_level(TreeNode root) {

        if (root == null) {
            return new ArrayList<>();
        }

        int[] width = new int[2];

        shadowOfTree(root, 0, width);

        int totalWidth = width[1] - width[0] + 1;

        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();

        for (int i = 0; i < totalWidth; i++) {
            ans.add(new ArrayList<>());
        }

        int[] hLevel = new int[totalWidth];
        Arrays.fill(hLevel, -1);

        LinkedList<verticalPair> que = new LinkedList<>();
        que.addLast(new verticalPair(root, Math.abs(width[0])));

        int level = 0;
        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {

                verticalPair rn = que.removeFirst();
                TreeNode node = rn.node;
                int vl = rn.vl;

                if (hLevel[vl] < level) {
                    ans.get(vl).clear();
                    hLevel[vl] = level;
                }
                ans.get(vl).add(node.val);

                if (node.left != null) {
                    que.addLast(new verticalPair(node.left, vl - 1));
                }

                if (node.right != null) {
                    que.addLast(new verticalPair(node.right, vl + 1));
                }

            }

            level++;
        }

        return ans;
    }

    // b<==============================Top View ==========================>
    // https://practice.geeksforgeeks.org/problems/top-view-of-binary-tree/1

    // ? The logic is simple. Just getting the first element for each column in
    // ? vertical order traversal.

    // Just dry run and you will get it.

    public static class topVerticalPair {
        int vl = 0;
        TreeNode node = null;

        topVerticalPair(TreeNode node, int vl) {
            this.node = node;
            this.vl = vl;
        }
    }

    public static ArrayList<Integer> topView(TreeNode root) {

        if (root == null) {
            return new ArrayList<>();
        }

        int[] width = new int[] { 0, 0 };
        shadowOfTree(root, 0, width);

        int totalWidth = width[1] - width[0] + 1;

        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < totalWidth; i++) {
            ans.add(null);
        }

        LinkedList<topVerticalPair> que = new LinkedList<>();
        que.addLast(new topVerticalPair(root, Math.abs(width[0])));

        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                topVerticalPair rn = que.removeFirst();
                TreeNode node = rn.node;
                int vl = rn.vl;

                if (ans.get(vl) == null) {
                    ans.set(vl, node.val);
                }

                if (node.left != null) {
                    que.addLast(new topVerticalPair(node.left, vl - 1));
                }
                if (node.right != null) {
                    que.addLast(new topVerticalPair(node.right, vl + 1));
                }
            }
        }

        return ans;

    }

    // b <========================Vertical Sum ====================>

    /*
     * Given a Binary Tree, find vertical sum of the nodes that are in same vertical
     * line. Print all sums through different vertical lines starting from left-most
     * vertical line to right-most vertical line.
     */

    // ? Simple hai .Har vertical column ke sare elements ka sum karke arraylist mai
    // ? store karana hai.

    // Isko recursion se bhi kar sakte hai kyunki isme order matter nhi karta

    public ArrayList<Integer> verticalSum(TreeNode root) {

        if (root == null) {
            return new ArrayList<>();
        }

        int[] width = new int[2];

        shadowOfTree(root, 0, width);

        int totalWidth = width[1] - width[0] + 1;
        ArrayList<Integer> ans = new ArrayList<>();

        for (int i = 0; i < totalWidth; i++) {
            ans.add(0); // Initiatlizing the array list with 0.
        }
        LinkedList<verticalPair> que = new LinkedList<>();
        que.addLast(new verticalPair(root, Math.abs(width[0])));

        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                verticalPair rn = que.removeFirst();
                TreeNode node = rn.node;
                int vl = rn.vl;

                ans.set(vl, ans.get(vl) + node.val);

                if (node.left != null) {
                    que.addLast(new verticalPair(node.left, vl - 1));
                }

                if (node.right != null) {
                    que.addLast(new verticalPair(node.right, vl + 1));
                }
            }
        }

        return ans;
        // add your code here
    }

    // b<=======================Diagonal Order of Tree ================>
    // https://practice.geeksforgeeks.org/problems/diagonal-traversal-of-binary-tree/1

    // ? Below is your method that was intuitive to you.
    // The logic is simple. As you move to your left, increase the diagonal level by
    // 1 and as you move to your right, the diagonal level remains same because it
    // is part of same diagonal.

    // HashMap is used here in dfs because we don't know that how many diagonals
    // will be there.

    // ! Important note :

    // ? The biggest mistake you did was to consider that width of shadow of tree
    // ? width[0] will be equal to the number of diagonals. This is not true.

    public void diagonal(TreeNode root, int diagonalLevel, HashMap<Integer, ArrayList<Integer>> map) {
        // add your code here.

        if (root == null) {
            return;
        }

        if (!map.containsKey(diagonalLevel)) {
            map.put(diagonalLevel, new ArrayList<>());
        }

        map.get(diagonalLevel).add(root.val);

        diagonal(root.left, diagonalLevel + 1, map);
        diagonal(root.right, diagonalLevel, map);

    }

    public ArrayList<Integer> diagonal(TreeNode root) {

        if (root == null) {
            return new ArrayList<>();
        }

        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();

        diagonal(root, 0, map);
        ArrayList<Integer> myans = new ArrayList<>();

        for (int i = 0; i < map.size(); i++) {
            for (int e : map.get(i)) {
                myans.add(e);
            }
        }

        return myans;
        // add your code here.
    }

    // ? Using the BFS method to solve it.

    // So what we did was simple.

    // Considering the test case :
    // [8,3,10,1,6,11,14,null,null,4,7,15,16,13,null]

    // The answer should look like this for each diagonal

    // 1: 8,10,14
    // 2: 3,6,7,11,16,13
    // 3: 1,4,15

    // So what we did was we are first going to insert the root node in the queue.
    // Then we will follow the same process of bfs but with a little twist.

    // When we removed 8, we are going to the right until we find null and are going
    // to insert left of every node in the queue we find while going right. Also
    // remember to insert the node val in an arraylist.

    // Now when we traverse from 8 ,10, 14, We have inserted 3 , 11 and 13 in the
    // queue.

    // 8 10 14 form the first diagonal.

    // Now comes 3, we remove it and move to the right and adding left if exist and
    // the node value to arraylist. Same goes for values 11 and 13.

    // Hence the second diagonal is formed
    // They contain value 3 6 7 11 16 13

    // Always remember the order is important

    // First we exhaust the right of 3, then 11 and at last of 13.

    // So my size -- while loop will complete one whole diagonal each time it runs.

    // Therefore the story continues like this ....

    public ArrayList<Integer> diagonal_bfs(TreeNode root) {

        ArrayList<Integer> ans = new ArrayList<>();

        if (root == null) {
            return ans;
        }

        ArrayList<ArrayList<Integer>> myans = new ArrayList<>();
        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);

        while (que.size() != 0) {
            int size = que.size();

            ArrayList<Integer> diagonal = new ArrayList<>();
            while (size-- > 0) { // Every time this runs, it will complete my one whole diagonal
                TreeNode rn = que.removeFirst();

                while (rn != null) {
                    diagonal.add(rn.val);
                    if (rn.left != null) {
                        que.add(rn.left);
                    }
                    rn = rn.right;
                }
            }

            myans.add(diagonal);

        }

        // myans has all the diagonals in form of arraylist

        for (int i = 0; i < myans.size(); i++) {
            for (int e : myans.get(i)) {
                ans.add(e);
            }
        }

        return ans; // Just for geeks ans;
    }

    // b <=====================Diagonal Sum ========================>
    // https://practice.geeksforgeeks.org/problems/diagonal-sum-in-binary-tree/1

    // Sum of the diagonals of the tree.

    // ? What we did is traverse the diagonal order and sum its element

    public static ArrayList<Integer> diagonalSum(TreeNode root) {

        ArrayList<Integer> ans = new ArrayList<>();

        if (root == null) {
            return ans;
        }

        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);

        while (que.size() != 0) {
            int size = que.size();

            int sum = 0;
            while (size-- > 0) {
                TreeNode rn = que.removeFirst();

                while (rn != null) {
                    sum += rn.val;
                    if (rn.left != null) {
                        que.add(rn.left);

                    }
                    rn = rn.right;
                }
            }

            ans.add(sum);
        }

        return ans;
    }

    // b<======================= Diagonal order anticlockwise ==================>

    // Diagonal order clockwise is the above diagonal order done where we have to
    // consider left to right diagonals

    // ? here we have to consider the right to left diagonal

    // Now the change is in the innermost while loop we will go to the leftmost and
    // add the right if exist.

    // Everthing remain same as normal diagonal order. Just the above tweak.

    public static ArrayList<ArrayList<Integer>> diagonalOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> myans = new ArrayList<>();

        if (root == null) {
            return myans;
        }

        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);

        while (que.size() != 0) {
            int size = que.size();

            ArrayList<Integer> diagonal = new ArrayList<>();
            while (size-- > 0) { // Every time this runs, it will complete my one whole diagonal
                TreeNode rn = que.removeFirst();

                while (rn != null) {
                    diagonal.add(rn.val);
                    if (rn.right != null) {
                        que.add(rn.right);
                    }
                    rn = rn.left;
                }
            }

            myans.add(diagonal);

        }
        return myans;
    }

    // b<=============Vertical Order ii Leetcode =============>
    // https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/

    // ? It is same as the vertical order but with a little twist.
    // ? If we have multiple nodes that have same horizontal level and same vertical
    // ? level, then we need them in sorting order for a vertcal

    // Considering the test case :
    // [8,3,10,1,6,11,14,null,null,4,7,0,16,13,null]

    // The vertical order should be :

    // 0 : 1
    // 1 : 3,0,4
    // 2 : 8,6,11
    // 3 : 10,7,13,16
    // 4 : 14

    // Taking an example : there were two nodes 4, 0 for horizontal level 3 and
    // vertical level 1 so they needs to be in sorted order when storing in
    // arraylist.

    // Normal vertical order will give 4,0 and store in the arraylist.

    // So what do we do for this behaviour ???

    // First thing we do is keep an extra horizontal level in class pair. So we will
    // `be adding (node/hLevel/vLevel) in the queue.

    // Secondly and most importantly, we will be needing a priority queue.

    // Now applying the same bfs as in normal vertical order and adding/removing
    // elements from queue.

    // ? but now what will change is the priority that the elements from the queue
    // ? come out.

    // Since we want it to behave like normal vertical order, therefore we want
    // nodes with less horizontal level comes out first.

    // And if the horizontal level are same, then the one with less vertical order
    // comes out first

    // And if horizontal and vertical level both are same, then the node with less
    // value comes out first giving us the sorted order

    // ! To do all this we have used lambda operator in priority queue.

    // (a,b) -> {some logic to set priority}

    // Decision is always made of first element.i.e a;

    // a-b ==> this - other always gives us the default behaviour.i.e increasing

    // `b-a ==> other - this giver us the opposite of default behaviour. Decreasing

    // ! Time Complexity : NlogN;
    // Why ??

    // ? Insertion and removal in priority queue is of logN.
    // so during the whole function every element is removed and inserted. for every
    // element, the work is don 2logN.

    // And there are N elements so the complexity will be 2nlogn. In Big o == >
    // o(nlogn)

    public static void shadowOfTree_practice_vertical_level_ii(TreeNode root, int verticalLevel, int[] width) {

        if (root == null) {
            return;
        }

        width[0] = Math.min(width[0], verticalLevel);
        width[1] = Math.max(width[1], verticalLevel);

        shadowOfTree_practice_vertical_level_ii(root.left, verticalLevel - 1, width);
        shadowOfTree_practice_vertical_level_ii(root.right, verticalLevel + 1, width);
    }

    public static class verticalPair2 {

        TreeNode node = null;
        int vl = 0;
        int hl = 0;

        verticalPair2(TreeNode node, int hl, int vl) {
            this.node = node;
            this.vl = vl;
            this.hl = hl;
        }
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {

        List<List<Integer>> ans = new ArrayList<>();

        if (root == null) {
            return ans;
        }

        int[] width = new int[2];
        shadowOfTree_practice_vertical_level_ii(root, 0, width);

        int totalWidth = width[1] - width[0] + 1;

        for (int i = 0; i < totalWidth; i++) {
            ans.add(new ArrayList<>());
        }

        PriorityQueue<verticalPair2> que = new PriorityQueue<>((a, b) -> {
            if (a.hl != b.hl) {
                return a.hl - b.hl; // this - other == > default behaviour(increasing). pair with less value of hl
                                    // will come first
            } else if (a.vl != b.vl) {
                return a.vl - b.vl;// this - other == > default behaviour(increasing). pair with less value of vl
                                   // will come first
            } else {
                return a.node.val - b.node.val;// this - other == > default behaviour(increasing). pair with less value
                                               // of node.val will come first out of priority queue
            }
        });
        que.add(new verticalPair2(root, 0, Math.abs(width[0])));

        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                verticalPair2 rn = que.remove();
                TreeNode node = rn.node;
                int vl = rn.vl;
                int hl = rn.hl;

                ans.get(vl).add(node.val);
                if (node.left != null) {
                    que.add(new verticalPair2(node.left, hl + 1, vl - 1));
                }
                if (node.right != null) {
                    que.add(new verticalPair2(node.right, hl + 1, vl + 1));
                }

            }
        }
        return ans;
    }

    public static void main(String[] args) {

    }

}
