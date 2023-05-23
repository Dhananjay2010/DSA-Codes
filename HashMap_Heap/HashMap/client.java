public class client {

    public static void main(String[] args) {
        hashmap hm = new hashmap();

        hm.put(10, 100);
        hm.put(20, 200);
        hm.put(30, 300);
        hm.put(40, 400);
        hm.put(50, 100);
        hm.put(60, 200);
        hm.put(70, 300);
        hm.put(80, 400);

        hm.put(90, 100);
        hm.put(110, 200);
        hm.put(130, 200);
        hm.put(140, 200);
        hm.put(150, 200);
        hm.put(160, 200);
        hm.put(170, 200);
        hm.put(180, 200);
        hm.put(120, 300);

        hm.putIfAbsent(40, 700);

        System.out.println(hm.display());
        System.out.println(hm.get(20));
        System.out.println(hm.keySet());

    }
}
