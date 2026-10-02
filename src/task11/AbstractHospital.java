package task11;

public class AbstractHospital {

}
abstract class Hospital {

    abstract void treatment();

    void hospitalName() {
        System.out.println("Hospital: Apollo Hospital");
    }
}

class Patient extends Hospital {

    void treatment() {
        System.out.println("Patient is receiving treatment");
    }

    public static void main(String[] args) {

        Patient p = new Patient();

        p.hospitalName();
        p.treatment();
    }
}