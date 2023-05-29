public class user {

    public static void main(String[] args) throws Exception {
        DynamicStack st = new DynamicStack(4);
        for (int i = 0; i < 10; i++) {
            st.push(i * 10);
        }
        st.pop();
        st.display();
    }
}
