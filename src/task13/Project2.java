package task13;




import java.io.*;
import java.util.*;
import java.io.*;
import java.util.*;


public class Project2{

    // =====================
    // ENUM - STUDENT STATUS
    // =====================
    enum StudentStatus {
        ACTIVE,
        INACTIVE,
        COMPLETED
    }

    // ====================
    // CUSTOM EXCEPTION
    // ====================
    static class StudentException extends Exception {

        public StudentException(String message) {
            super(message);
        }
    }

    // ==================
    // COURSE CLASS
    // ==================
    static class Course {

        private int courseId;
        private String courseName;

        public Course(int courseId, String courseName) {
            this.courseId = courseId;
            this.courseName = courseName;
        }

        public int getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        @Override
        public String toString() {
            return courseName;
        }
    }

    static class Student {

        private int id;
        private String name;
        private String email;
        private double marks;
        private int attendance;
        private StudentStatus status;
        private Course course;

        public Student(int id,
                       String name,
                       String email,
                       double marks,
                       int attendance,
                       StudentStatus status,
                       Course course) {

            this.id = id;
            this.name = name;
            this.email = email;
            this.marks = marks;
            this.attendance = attendance;
            this.status = status;
            this.course = course;
        }


        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public double getMarks() {
            return marks;
        }

        public int getAttendance() {
            return attendance;
        }

        public StudentStatus getStatus() {
            return status;
        }

        public Course getCourse() {
            return course;
        }

        // ---- SETTERS ----

        public void setMarks(double marks) {
            this.marks = marks;
        }

        public void setAttendance(int attendance) {
            this.attendance = attendance;
        }

        public void setStatus(StudentStatus status) {
            this.status = status;
        }

        // ---- GRADE ----

        public String calculateGrade() {

            if (marks >= 90) {
                return "A+";
            } else if (marks >= 80) {
                return "A";
            } else if (marks >= 70) {
                return "B";
            } else if (marks >= 60) {
                return "C";
            } else if (marks >= 50) {
                return "D";
            } else {
                return "F";
            }
        }

        // ---- RESULT ----

        public String getResult() {

            if (marks >= 50 && attendance >= 75) {
                return "PASS";
            }

            return "FAIL";
        }

        // ---- ATTENDANCE ELIGIBILITY ----

        public String getAttendanceStatus() {

            if (attendance >= 75) {
                return "ELIGIBLE";
            }

            return "NOT ELIGIBLE";
        }

        // ----- DISPLAY -----

        @Override
        public String toString() {

            return "\n-------------" +
                   "\nStudent ID       : " + id +
                   "\nName             : " + name +
                   "\nEmail            : " + email +
                   "\nCourse           : " + course +
                   "\nMarks            : " + marks +
                   "\nGrade            : " + calculateGrade() +
                   "\nAttendance       : " + attendance + "%" +
                   "\nAttendance Status: " + getAttendanceStatus() +
                   "\nResult           : " + getResult() +
                   "\nStatus           : " + status +
                   "\n--------------";
        }
    }

    // ======================
    // STUDENT SERVICE
    // ======================
    static class StudentService {

        private HashMap<Integer, Student> students =
                new HashMap<>();

        // ----- ADD ----

        public void addStudent(Student student)
                throws StudentException {

            if (students.containsKey(student.getId())) {

                throw new StudentException(
                        "Student ID already exists!");
            }

            students.put(student.getId(), student);

            System.out.println(
                    "Student added successfully.");
        }

        // ---- DISPLAY ALL ----

        public void displayAllStudents() {

            if (students.isEmpty()) {

                System.out.println(
                        "No students available.");

                return;
            }

            for (Student student : students.values()) {
                System.out.println(student);
            }
        }

        // ----- SEARCH ID -------

        public Student searchById(int id)
                throws StudentException {

            Student student = students.get(id);

            if (student == null) {

                throw new StudentException(
                        "Student with ID " + id +
                        " not found!");
            }

            return student;
        }

        // ----- SEARCH NAME ----

        public void searchByName(String name) {

            boolean found = false;

            for (Student student : students.values()) {

                if (student.getName()
                        .toLowerCase()
                        .contains(name.toLowerCase())) {

                    System.out.println(student);
                    found = true;
                }
            }

            if (!found) {

                System.out.println(
                        "No student found with name: "
                        + name);
            }
        }

        // ---- UPDATE MARKS ----

        public void updateMarks(int id, double marks)
                throws StudentException {

            validateMarks(marks);

            Student student = searchById(id);

            student.setMarks(marks);

            System.out.println(
                    "Marks updated successfully.");
        }

        // ---- UPDATE ATTENDANCE ----

        public void updateAttendance(
                int id, int attendance)
                throws StudentException {

            validateAttendance(attendance);

            Student student = searchById(id);

            student.setAttendance(attendance);

            System.out.println(
                    "Attendance updated successfully.");
        }

        // ---- UPDATE STATUS ----

        public void updateStatus(
                int id, StudentStatus status)
                throws StudentException {

            Student student = searchById(id);

            student.setStatus(status);

            System.out.println(
                    "Status updated successfully.");
        }

        // ---- CALCULATE GRADE ----

        public void calculateGrade(int id)
                throws StudentException {

            Student student = searchById(id);

            System.out.println(
                    "\nStudent Name : "
                    + student.getName());

            System.out.println(
                    "Marks        : "
                    + student.getMarks());

            System.out.println(
                    "Grade        : "
                    + student.calculateGrade());

            System.out.println(
                    "Result       : "
                    + student.getResult());
        }

        // ---- REMOVE ----

        public void removeStudent(int id)
                throws StudentException {

            if (students.remove(id) == null) {

                throw new StudentException(
                        "Student not found!");
            }

            System.out.println(
                    "Student removed successfully.");
        }

        // ----- TOPPER ----

        public void displayTopper() {

            if (students.isEmpty()) {

                System.out.println(
                        "No students available.");

                return;
            }

            Student topper = null;

            for (Student student : students.values()) {

                if (topper == null ||
                    student.getMarks() >
                    topper.getMarks()) {

                    topper = student;
                }
            }

            System.out.println("\n========== TOPPER ==========");
            System.out.println(topper);
        }

        // ---- SORT BY MARKS -----

        public void displayStudentsByMarks() {

            if (students.isEmpty()) {

                System.out.println(
                        "No students available.");

                return;
            }

            List<Student> list =
                    new ArrayList<>(students.values());

            list.sort(
                    Comparator.comparingDouble(
                            Student::getMarks
                    ).reversed()
            );

            System.out.println(
                    "\n===== STUDENTS BY MARKS =====");

            for (Student student : list) {
                System.out.println(student);
            }
        }

        // ---- COUNT ----

        public void displayStudentCount() {

            System.out.println(
                    "Total Students: "
                    + students.size());
        }

        //----- VALIDATE MARKS ----

        private void validateMarks(double marks)
                throws StudentException {

            if (marks < 0 || marks > 100) {

                throw new StudentException(
                        "Marks must be between 0 and 100.");
            }
        }

        // ---- VALIDATE ATTENDANCE ----

        private void validateAttendance(int attendance)
                throws StudentException {

            if (attendance < 0 ||
                attendance > 100) {

                throw new StudentException(
                        "Attendance must be between 0 and 100.");
            }
        }

        public HashMap<Integer, Student> getStudents() {
            return students;
        }

        public void setStudents(
                HashMap<Integer, Student> students) {

            this.students = students;
        }
    }

    // ============
    // FILE SERVICE
    // ============
    static class StudentFileService {

        private static final String FILE_NAME =
                "students.txt";

        // ----- SAVE ----

        public void saveStudents(
                HashMap<Integer, Student> students) {

            try (BufferedWriter writer =
                         new BufferedWriter(
                                 new FileWriter(FILE_NAME))) {

                for (Student student :
                        students.values()) {

                    writer.write(
                            student.getId() + "|" +
                            student.getName() + "|" +
                            student.getEmail() + "|" +
                            student.getMarks() + "|" +
                            student.getAttendance() + "|" +
                            student.getStatus() + "|" +
                            student.getCourse().getCourseId() + "|" +
                            student.getCourse().getCourseName()
                    );

                    writer.newLine();
                }

                System.out.println(
                        "Student data saved successfully.");

            } catch (IOException e) {

                System.out.println(
                        "File saving error: "
                        + e.getMessage());
            }
        }

        // ---- LOAD ----

        public HashMap<Integer, Student>
        loadStudents() {

            HashMap<Integer, Student> students =
                    new HashMap<>();

            File file = new File(FILE_NAME);

            if (!file.exists()) {
                return students;
            }

            try (BufferedReader reader =
                         new BufferedReader(
                                 new FileReader(FILE_NAME))) {

                String line;

                while ((line =
                        reader.readLine()) != null) {

                    String[] data =
                            line.split("\\|");

                    if (data.length < 8) {
                        continue;
                    }

                    int id =
                            Integer.parseInt(data[0]);

                    String name = data[1];

                    String email = data[2];

                    double marks =
                            Double.parseDouble(data[3]);

                    int attendance =
                            Integer.parseInt(data[4]);

                    StudentStatus status =
                            StudentStatus.valueOf(data[5]);

                    int courseId =
                            Integer.parseInt(data[6]);

                    String courseName =
                            data[7];

                    Course course =
                            new Course(
                                    courseId,
                                    courseName
                            );

                    Student student =
                            new Student(
                                    id,
                                    name,
                                    email,
                                    marks,
                                    attendance,
                                    status,
                                    course
                            );

                    students.put(id, student);
                }

                System.out.println(
                        "Previous student data loaded.");

            } catch (Exception e) {

                System.out.println(
                        "File loading error: "
                        + e.getMessage());
            }

            return students;
        }
    }

    // ========================
    // INPUT VALIDATION METHODS
    // ========================

    static int readInt(Scanner sc, String message) {

        while (true) {

            try {

                System.out.print(message);

                return sc.nextInt();

            } catch (InputMismatchException e) {

                System.out.println(
                        "Please enter a valid integer.");

                sc.nextLine();
            }
        }
    }

    static double readDouble(
            Scanner sc, String message) {

        while (true) {

            try {

                System.out.print(message);

                return sc.nextDouble();

            } catch (InputMismatchException e) {

                System.out.println(
                        "Please enter a valid number.");

                sc.nextLine();
            }
        }
    }

    static String readString(
            Scanner sc, String message) {

        System.out.print(message);

        return sc.nextLine().trim();
    }

    // ===========
    // MAIN METHOD
    // ===========
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentService service =
                new StudentService();

        StudentFileService fileService =
                new StudentFileService();

        // Load previous data
        service.setStudents(
                fileService.loadStudents()
        );

        int choice;

        do {

            System.out.println(
                    "\n=============");

            System.out.println(
                    "       STUDENT MANAGEMENT SYSTEM");

            System.out.println(
                    "==============");

            System.out.println("1.  Add Student");
            System.out.println("2.  Display All Students");
            System.out.println("3.  Search Student by ID");
            System.out.println("4.  Search Student by Name");
            System.out.println("5.  Update Marks");
            System.out.println("6.  Update Attendance");
            System.out.println("7.  Update Student Status");
            System.out.println("8.  Calculate Grade & Result");
            System.out.println("9.  Remove Student");
            System.out.println("10. Display Topper");
            System.out.println("11. Sort Students by Marks");
            System.out.println("12. Display Student Count");
            System.out.println("13. Save Students");
            System.out.println("14. Exit");

            System.out.println(
                    "==============");

            choice = readInt(
                    sc,
                    "Enter your choice: "
            );

            sc.nextLine();

            try {

                switch (choice) {

                    // ===========
                    // ADD
                    // ==========

                    case 1:

                        int id =
                                readInt(
                                        sc,
                                        "Enter Student ID: "
                                );

                        sc.nextLine();

                        String name =
                                readString(
                                        sc,
                                        "Enter Student Name: "
                                );

                        String email =
                                readString(
                                        sc,
                                        "Enter Email: "
                                );

                        double marks =
                                readDouble(
                                        sc,
                                        "Enter Marks: "
                                );

                        int attendance =
                                readInt(
                                        sc,
                                        "Enter Attendance %: "
                                );

                        sc.nextLine();

                        if (name.isEmpty()) {

                            throw new StudentException(
                                    "Name cannot be empty.");
                        }

                        if (!email.contains("@")) {

                            throw new StudentException(
                                    "Invalid email address.");
                        }

                        if (marks < 0 ||
                            marks > 100) {

                            throw new StudentException(
                                    "Marks must be between 0 and 100.");
                        }

                        if (attendance < 0 ||
                            attendance > 100) {

                            throw new StudentException(
                                    "Attendance must be between 0 and 100.");
                        }

                        System.out.println(
                                "\nSelect Student Status:");

                        System.out.println(
                                "1. ACTIVE");

                        System.out.println(
                                "2. INACTIVE");

                        System.out.println(
                                "3. COMPLETED");

                        int statusChoice =
                                readInt(
                                        sc,
                                        "Enter choice: "
                                );

                        sc.nextLine();

                        StudentStatus status;

                        switch (statusChoice) {

                            case 1:
                                status =
                                        StudentStatus.ACTIVE;
                                break;

                            case 2:
                                status =
                                        StudentStatus.INACTIVE;
                                break;

                            case 3:
                                status =
                                        StudentStatus.COMPLETED;
                                break;

                            default:
                                throw new StudentException(
                                        "Invalid status choice.");
                        }

                        int courseId =
                                readInt(
                                        sc,
                                        "Enter Course ID: "
                                );

                        sc.nextLine();

                        String courseName =
                                readString(
                                        sc,
                                        "Enter Course Name: "
                                );

                        if (courseName.isEmpty()) {

                            throw new StudentException(
                                    "Course name cannot be empty.");
                        }

                        Course course =
                                new Course(
                                        courseId,
                                        courseName
                                );

                        Student student =
                                new Student(
                                        id,
                                        name,
                                        email,
                                        marks,
                                        attendance,
                                        status,
                                        course
                                );

                        service.addStudent(student);

                        break;

                    // ============
                    // DISPLAY
                    // ============

                    case 2:

                        service.displayAllStudents();

                        break;

                    // ===========
                    // SEARCH ID
                    // ===========

                    case 3:

                        int searchId =
                                readInt(
                                        sc,
                                        "Enter Student ID: "
                                );

                        sc.nextLine();

                        System.out.println(
                                service.searchById(
                                        searchId
                                )
                        );

                        break;

                    // ============
                    // SEARCH NAME
                    // =============

                    case 4:

                        String searchName =
                                readString(
                                        sc,
                                        "Enter Student Name: "
                                );

                        service.searchByName(
                                searchName
                        );

                        break;

                    // ============
                    // UPDATE MARKS
                    // ============

                    case 5:

                        int marksId =
                                readInt(
                                        sc,
                                        "Enter Student ID: "
                                );

                        double newMarks =
                                readDouble(
                                        sc,
                                        "Enter New Marks: "
                                );

                        sc.nextLine();

                        service.updateMarks(
                                marksId,
                                newMarks
                        );

                        break;

                    // =================
                    // UPDATE ATTENDANCE
                    // =================

                    case 6:

                        int attendanceId =
                                readInt(
                                        sc,
                                        "Enter Student ID: "
                                );

                        int newAttendance =
                                readInt(
                                        sc,
                                        "Enter New Attendance %: "
                                );

                        sc.nextLine();

                        service.updateAttendance(
                                attendanceId,
                                newAttendance
                        );

                        break;

                    // =============
                    // UPDATE STATUS
                    // =============

                    case 7:

                        int statusId =
                                readInt(
                                        sc,
                                        "Enter Student ID: "
                                );

                        sc.nextLine();

                        System.out.println(
                                "1. ACTIVE");

                        System.out.println(
                                "2. INACTIVE");

                        System.out.println(
                                "3. COMPLETED");

                        int newStatus =
                                readInt(
                                        sc,
                                        "Enter status: "
                                );

                        sc.nextLine();

                        StudentStatus updatedStatus;

                        if (newStatus == 1) {

                            updatedStatus =
                                    StudentStatus.ACTIVE;

                        } else if (newStatus == 2) {

                            updatedStatus =
                                    StudentStatus.INACTIVE;

                        } else if (newStatus == 3) {

                            updatedStatus =
                                    StudentStatus.COMPLETED;

                        } else {

                            throw new StudentException(
                                    "Invalid status.");
                        }

                        service.updateStatus(
                                statusId,
                                updatedStatus
                        );

                        break;

                    // =====
                    // GRADE
                    // =====

                    case 8:

                        int gradeId =
                                readInt(
                                        sc,
                                        "Enter Student ID: "
                                );

                        sc.nextLine();

                        service.calculateGrade(
                                gradeId
                        );

                        break;

                    // ======
                    // REMOVE
                    // ======

                    case 9:

                        int removeId =
                                readInt(
                                        sc,
                                        "Enter Student ID: "
                                );

                        sc.nextLine();

                        service.removeStudent(
                                removeId
                        );

                        break;

                    // ======
                    // TOPPER
                    // ======

                    case 10:

                        service.displayTopper();

                        break;

                    // ====
                    // SORT
                    // ====

                    case 11:

                        service.displayStudentsByMarks();

                        break;

                    // =====
                    // COUNT
                    // =====

                    case 12:

                        service.displayStudentCount();

                        break;

                    // ====
                    // SAVE
                    // ====

                    case 13:

                        fileService.saveStudents(
                                service.getStudents()
                        );

                        break;

                    // ====
                    // EXIT
                    // ====

                    case 14:

                        fileService.saveStudents(
                                service.getStudents()
                        );

                        System.out.println(
                                "\nStudent data saved.");

                        System.out.println(
                                "Thank you for using "
                                + "Student Management System!");

                        break;

                    default:

                        System.out.println(
                                "Invalid choice. "
                                + "Please select 1-14.");
                }

            } catch (StudentException e) {

                System.out.println(
                        "Error: " + e.getMessage());

            } catch (Exception e) {

                System.out.println(
                        "Unexpected error: "
                        + e.getMessage());
            }

        } while (choice != 14);

        sc.close();
    }
}

