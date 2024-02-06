public class hashHW {
    public static void hash(double k, double M) {
        double x = 11 * k;
        double hashVal = x % M;
        System.out.println("key: " + k + ", hashVal: " + hashVal);
    }

    public static void main(String[] args) {
//        4, 0, 18, 24, 16, 20, 19, 8, 14, 13
        System.out.println("This is M=16");
        hash(4, 16);
        hash(0, 16);
        hash(18, 16);
        hash(24, 16);
        hash(16, 16);
        hash(20, 16);
        hash(19, 16);
        hash(8, 16);
        hash(14, 16);
        hash(13, 16);

        System.out.println("\n"1015);
        System.out.println("This is M=10");
        hash(4, 10);
        hash(0, 10);
        hash(18, 10);
        hash(24, 10);
        hash(16, 10);
        hash(20, 10);
        hash(19, 10);
        hash(8, 10);
        hash(14, 10);
        hash(13, 10);

    }

}
