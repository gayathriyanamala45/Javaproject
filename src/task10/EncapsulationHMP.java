package task10;

public class EncapsulationHMP {


}
class Patient {

    private int patientId;
    
    private String patientName;
    
    private String disease;
    
    private double billAmount;

    Patient(int patientId, String patientName, String disease, double billAmount) {
    	
        this.patientId = patientId;
        
        this.patientName = patientName;
        
        this.disease = disease;
        
        this.billAmount = billAmount;
    }

    void setBillAmount(double billAmount) {
    	
        this.billAmount = billAmount;
    }

    double getBillAmount() {
    	
        return billAmount;
    }

    void displayPatientDetails() {
    	
        System.out.println("Patient ID: " + patientId);
        
        System.out.println("Patient Name: " + patientName);
        
        System.out.println("Disease: " + disease);
        
        System.out.println("Bill Amount: " + billAmount);
    }


    public static void main(String[] args) {

        Patient p = new Patient(101, "Gayathri", "Fever", 5000);

        p.displayPatientDetails();

        
        System.out.println("Old Bill Amount: " + p.getBillAmount());

        p.setBillAmount(6000);
        

        System.out.println("New Bill Amount: " + p.getBillAmount());
    }
}