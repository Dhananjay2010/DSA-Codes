import java.util.LinkedList;

public class StackFromQueue {

    // The functions we can use are addLast and RemoveFirst.
    LinkedList<Integer> que = new LinkedList<>();
    LinkedList<Integer> temp = new LinkedList<>();

    int topEle = 0;

    public StackFromQueue() {

    }

    /** Push element x onto stack. */
    // This is push efficient.
    public void push(int x) {
        topEle = x;
        que.addLast(x);
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        while (que.size() > 1)
            temp.addLast(que.removeFirst());

        int rEle = que.removeFirst();

        while (temp.size() != 0)
            this.push(temp.removeFirst());

        return rEle;

    }

    /** Get the top element. */
    public int top() {
        return topEle;
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return que.size() == 0;
    }
}
