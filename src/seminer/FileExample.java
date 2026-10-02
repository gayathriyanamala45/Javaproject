package seminer;


import java.io.*;

public class FileExample {

    public static void main(String[] args) {

        File file = new File("student.txt");

        try {
            if (file.createNewFile()) {
                System.out.println("File Created Successfully");
            } else {
                System.out.println("File Already Exists");
            }

            System.out.println("File Name : " + file.getName());
            System.out.println("File Path : " + file.getAbsolutePath());
            System.out.println("Readable : " + file.canRead());
            System.out.println("Writable : " + file.canWrite());

        } catch (IOException e) {
            System.out.println("Error Occurred");
        }
    }
}