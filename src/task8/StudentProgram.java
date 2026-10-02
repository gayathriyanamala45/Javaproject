package task8;

public class StudentProgram {

    public static void main(String[] args) {

        int[][] marks = {
            {80, 75, 90},
            {70, 85, 88},
            {95, 80, 85}
        };

        for (int i = 0; i < marks.length; i++) {

            int total = 0;

            for (int j = 0; j < marks[i].length; j++) {
                total = total + marks[i][j];
            }

            System.out.println("Student " + (i + 1));
            System.out.println("Total Marks = " + total);
            System.out.println("Average = " + (total / 3.0));
            System.out.println();
        }
    }
}