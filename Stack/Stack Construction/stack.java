public class stack {

    private int[] arr;
    private int tos;
    private int noOfElememts;
    private int maxCapacity;

    stack(int size) {

    }

    stack() {
        this(15);
    }

    protected void initialize(int size) {
        this.arr = new int[size];
        this.tos = -1;
        this.noOfElememts = 0;
        this.maxCapacity = size;
    }

}
