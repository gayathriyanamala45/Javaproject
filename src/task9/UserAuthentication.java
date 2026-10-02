package task9;

public class UserAuthentication {

    void login(String email, String password) {
    	
        if (email.equals("user@gmail.com") && password.equals("12345")) {
        	
            System.out.println("Login successful using Email and Password");
            
        } else {
        	
            System.out.println("Invalid Email or Password");
        }
    }

    void login(long phone, int otp) {
    	
        if (phone == 9876543210L && otp == 0234) {
        	
            System.out.println("Login successful using Phone and OTP");
        } else {
        	
            System.out.println("Invalid Phone Number or OTP");
        }
    }

    void login(String socialId) {
    	
        if (socialId.equals("google456")) {
        	
            System.out.println("Login successful using Social ID");
            
        } else {
        	
            System.out.println("Invalid Social ID");
        }
    }

    public static void main(String[] args) {

        UserAuthentication u = new UserAuthentication();

        u.login("user@gmail.com", "1234");

        u.login(9876543210L, 1234);
        

        u.login("google123");
        
    }
}