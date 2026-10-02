package seminer;



class Garbage {
    public static void main(String[] args) {

        Garbage d1 = new Garbage();
        Garbage d2 = new Garbage();

        d1 = null;
        d2 = null;

        System.gc();

        System.out.println("Garbage Collection requested");
    }
}