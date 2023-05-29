public class DynamicStack extends stack {

    // If I am extending any class, it needs to have same amount of constructor as
    // the class it extends.

    // means that Dynamic Stack should have same constructor as stack.

    // # Super refers to the class we have extended.

    // So DynamicStack class will be able to use all the public and protected
    // function and variables of stack class.

    DynamicStack(int size) {
        super(size); // calling the stack class constructor.
    }

    DynamicStack() {
        super();
    }

    @Override // Telling the Dynamic stack to override the stack push method.
    public void push(int data) throws Exception {
        if (super.size() == super.capacity()) {
            // If the stack is full, then we have to transfer the data to another array and
            // resize the array with twice the size and retransfer the data.

            int[] temp = new int[super.size()];

            int i = super.size() - 1;
            while (super.size() != 0) {
                temp[i--] = super.pop();
            }

            super.initialize(temp.length * 2);
            for (int ele : temp) {
                super.push(ele);
            }
        }
        super.push(data);
    }

    // Agar koi A class hai wo B to extend kar rhi hai, to B ke sare functions A
    // wali class use kar sakti hai, per B class A ke functions use nhi kar sakta.

    // A a = new B(); // == > This statement is invalid.
    // B b = new A (); // == > This statement is valid.

    // So therefore

    // stack st = new DynamicStack(); is a valid statement, not vise-versa.

    // Aisa isliye kyunki Dynamic stack ne stack class ke sare functions already
    // inherit kar liye hain.

    // To st stack class ke bhi functions ko run kar sakta hai aur dynamic class ke
    // bhi.

    // DynamicStack st= new stack(); is a invalid statement.
    // Per ab st sirf stack class ke he use kar payega per wo hai Dynaminc stack ka,
    // to isiliye ye statement in valid hai.

}
