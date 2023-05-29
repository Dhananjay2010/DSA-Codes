public class user {

    public static void main(String[] args) throws Exception {

        stack st = new stack();
        queue q = new queue();
        for (int i = 1; i <= 10; i++) {
            st.push(i * 10);
            q.push(i * 10);
        }
        // System.out.println(st.pop());
        // System.out.println(st.peek());
        // System.out.println(st.size());
        // st.display();

        System.out.println(q.peek());
        System.out.println(q.pop());
        q.display();
    }
}
