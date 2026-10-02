package task8;

public class TemparatureP {

    public static void main(String[] args) {

        int[][] temperature = {
            {30, 32, 31},
            {28, 29, 30},
            {35, 34, 36}
        };

        for (int i = 0; i < temperature.length; i++) {

            int total = 0;

            for (int j = 0; j < temperature[i].length; j++) {
                total = total + temperature[i][j];
            }

            double average = total / 3.0;

            System.out.println("Day " + (i + 1));
            System.out.println("Average Temperature = " + average);
            System.out.println();
        }
    }
}