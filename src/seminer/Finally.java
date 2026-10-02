package seminer;

class Finally {
    public static void main(String[] args) {

        try {
            int a = 10 / 0;
            System.out.println(a);
        }
        catch (ArithmeticException e) {
            System.out.println("Exception occurred");
        }
        finally {
            System.out.println("Finally block executed");
        }
    }
}