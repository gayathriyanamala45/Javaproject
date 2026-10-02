package seminer;

class Demo {

    protected void finalize() {
        System.out.println("Finalize method called");
    }

    public static void main(String[] args) {

        Demo d = new Demo();

        d = null;

        System.gc();

        System.out.println("Garbage Collection requested");
    }
}