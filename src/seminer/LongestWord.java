package seminer;


import java.io.*;

public class LongestWord {

    public static void main(String[] args) {

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("student.txt"));

            String line;
            String longestWord = "";

            while ((line = br.readLine()) != null) {

                String[] words = line.split(" ");

                for (String word : words) {

                    if (word.length() > longestWord.length()) {
                        longestWord = word;
                    }
                }
            }

            br.close();

            System.out.println("Longest Word : " + longestWord);
            System.out.println("Length : " + longestWord.length());

        } catch (IOException e) {
            System.out.println("Error processing file.");
        }
    }
}