import java.util.ArrayList;
import java.util.LinkedList;

public class test {

    public void diagonal(Node root, int diagonalLevel, HashMap<Integer, ArrayList<Integer>> map) {
        // add your code here.

        if (root == null) {
            return;
        }

        if (!map.containsKey(diagonalLevel)) {
            map.put(diagonalLevel, new ArrayList<>());
        }

        map.get(diagonalLevel).add(root.data);

        diagonal(root.left, diagonalLevel + 1, map);
        diagonal(root.right, diagonalLevel, map);

    }

    public ArrayList<Integer> diagonal(Node root) {

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

}
