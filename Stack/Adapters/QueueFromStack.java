import java.util.LinkedList;

public class QueueFromStack {

    // The only functions that I can use is AddFirst and RemoveFirst.
    LinkedList<Integer> st = new LinkedList<>();
    LinkedList<Integer> temp = new LinkedList<>();
    int top = -1;

    QueueFromStack() {

    }

    public void push(int x) {
        st.addFirst(x);
        if (st.size() == 1)
            top = x;
    }

    public int pop() {
        int size = st.size();
        while (size-- > 1) {
            temp.addFirst(st.removeFirst());
        }

        int rn = st.removeFirst();
        while (temp.size() != 0)
            this.push(temp.removeFirst());

        return rn;
    }

    public int peek() {
        return top;
    }

    public boolean empty() {
        return st.size() == 0;
    }

}
