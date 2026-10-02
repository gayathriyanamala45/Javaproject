package seminer;


import java.io.*;

public class EvenOdd{

    public static void main(String[] args) {

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("numbers.txt"));

            BufferedWriter even = new BufferedWriter(
                    new FileWriter("even.txt"));

            BufferedWriter odd = new BufferedWriter(
                    new FileWriter("odd.txt"));

            String line;

            while ((line = br.readLine()) != null) {

                int number = Integer.parseInt(line);

                if (number % 2 == 0) {
                    even.write(String.valueOf(number));
                    even.newLine();
                } else {
                    odd.write(String.valueOf(number));
                    odd.newLine();
                }
            }

            br.close();
            even.close();
            odd.close();

            System.out.println("Numbers separated successfully.");

        } catch (IOException e) {
            System.out.println("Error processing file.");
        }
    }
}