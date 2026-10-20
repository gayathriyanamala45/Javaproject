package JdbcProject;

import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {

    private static final String URL =
            "jdbc:mysql://localhost:3306/?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Y Gayathri45";

    private static final Scanner sc = new Scanner(System.in);

    // Database connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_management_db?serverTimezone=UTC",
                USER, PASSWORD);
    }

    // Create database and all five tables
    private static void initializeDatabase() throws SQLException {

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = con.createStatement()) {

            st.executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS student_management_db");
        }

        try (Connection con = getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS students (
                    student_id INT PRIMARY KEY AUTO_INCREMENT,
                    student_name VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    phone VARCHAR(15),
                    city VARCHAR(50),
                    registration_date DATE NOT NULL
                )
                """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS courses (
                    course_id INT PRIMARY KEY AUTO_INCREMENT,
                    course_name VARCHAR(100) NOT NULL,
                    duration_months INT NOT NULL,
                    fee DECIMAL(10,2) NOT NULL
                )
                """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS enrollments (
                    enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
                    student_id INT NOT NULL,
                    course_id INT NOT NULL,
                    enrollment_date DATE NOT NULL,
                    UNIQUE (student_id, course_id),
                    FOREIGN KEY (student_id)
                        REFERENCES students(student_id),
                    FOREIGN KEY (course_id)
                        REFERENCES courses(course_id)
                )
                """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS attendance (
                    attendance_id INT PRIMARY KEY AUTO_INCREMENT,
                    student_id INT NOT NULL,
                    course_id INT NOT NULL,
                    attendance_date DATE NOT NULL,
                    status ENUM('Present','Absent') NOT NULL,
                    UNIQUE (student_id, course_id, attendance_date),
                    FOREIGN KEY (student_id)
                        REFERENCES students(student_id),
                    FOREIGN KEY (course_id)
                        REFERENCES courses(course_id)
                )
                """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS marks (
                    mark_id INT PRIMARY KEY AUTO_INCREMENT,
                    student_id INT NOT NULL,
                    course_id INT NOT NULL,
                    total_marks DECIMAL(6,2) NOT NULL,
                    marks_obtained DECIMAL(6,2) NOT NULL,
                    UNIQUE (student_id, course_id),
                    FOREIGN KEY (student_id)
                        REFERENCES students(student_id),
                    FOREIGN KEY (course_id)
                        REFERENCES courses(course_id),
                    CHECK (total_marks > 0),
                    CHECK (marks_obtained >= 0),
                    CHECK (marks_obtained <= total_marks)
                )
                """);

            // Insert sample courses only when the table is empty
            st.executeUpdate("""
                INSERT INTO courses (course_name, duration_months, fee)
                SELECT 'Java Full Stack', 6, 25000.00
                WHERE NOT EXISTS (SELECT 1 FROM courses)
                """);

            st.executeUpdate("""
                INSERT INTO courses (course_name, duration_months, fee)
                SELECT 'Python Full Stack', 5, 20000.00
                WHERE NOT EXISTS (
                    SELECT 1 FROM courses WHERE course_name = 'Python Full Stack'
                )
                """);
        }
    }

    // 1. Register student
    private static void registerStudent() {
        String sql = """
                INSERT INTO students
                (student_name, email, phone, city, registration_date)
                VALUES (?, ?, ?, ?, CURDATE())
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter student name: ");
            ps.setString(1, sc.nextLine());

            System.out.print("Enter email: ");
            ps.setString(2, sc.nextLine());

            System.out.print("Enter phone: ");
            ps.setString(3, sc.nextLine());

            System.out.print("Enter city: ");
            ps.setString(4, sc.nextLine());

            ps.executeUpdate();
            System.out.println("Student registered successfully!");

        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
        }
    }

    // 2. View students
    private static void viewStudents() {
        String sql = "SELECT * FROM students ORDER BY student_id";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nID | Name | Email | Phone | City | Registration Date");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("student_id") + " | " +
                        rs.getString("student_name") + " | " +
                        rs.getString("email") + " | " +
                        rs.getString("phone") + " | " +
                        rs.getString("city") + " | " +
                        rs.getDate("registration_date"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 3. Update student
    private static void updateStudent() {
        String sql = """
                UPDATE students
                SET student_name=?, email=?, phone=?, city=?
                WHERE student_id=?
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter student ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Enter new name: ");
            ps.setString(1, sc.nextLine());

            System.out.print("Enter new email: ");
            ps.setString(2, sc.nextLine());

            System.out.print("Enter new phone: ");
            ps.setString(3, sc.nextLine());

            System.out.print("Enter new city: ");
            ps.setString(4, sc.nextLine());

            ps.setInt(5, id);

            System.out.println(ps.executeUpdate() > 0
                    ? "Student updated successfully!"
                    : "Student not found.");

        } catch (SQLException | NumberFormatException e) {
            System.out.println("Update error: " + e.getMessage());
        }
    }

    // 4. Delete student
    private static void deleteStudent() {
        String sql = "DELETE FROM students WHERE student_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter student ID: ");
            ps.setInt(1, Integer.parseInt(sc.nextLine()));

            System.out.println(ps.executeUpdate() > 0
                    ? "Student deleted successfully!"
                    : "Student not found.");

        } catch (SQLException | NumberFormatException e) {
            System.out.println("Delete error: " + e.getMessage());
        }
    }

    // 5. View courses
    private static void viewCourses() {
        String sql = "SELECT * FROM courses ORDER BY course_id";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nID | Course | Duration | Fee");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("course_id") + " | " +
                        rs.getString("course_name") + " | " +
                        rs.getInt("duration_months") + " months | " +
                        rs.getBigDecimal("fee"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 6. Enroll student using a transaction
    private static void enrollStudent() {
        String sql = """
                INSERT INTO enrollments
                (student_id, course_id, enrollment_date)
                VALUES (?, ?, CURDATE())
                """;

        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                System.out.print("Enter student ID: ");
                ps.setInt(1, Integer.parseInt(sc.nextLine()));

                System.out.print("Enter course ID: ");
                ps.setInt(2, Integer.parseInt(sc.nextLine()));

                ps.executeUpdate();
                con.commit();

                System.out.println("Enrollment successful!");

            } catch (SQLException | NumberFormatException e) {
                con.rollback();
                System.out.println("Enrollment failed: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // 7. View enrollments using JOIN
    private static void viewEnrollments() {
        String sql = """
                SELECT s.student_name, c.course_name, e.enrollment_date
                FROM enrollments e
                JOIN students s ON e.student_id = s.student_id
                JOIN courses c ON e.course_id = c.course_id
                ORDER BY e.enrollment_id
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getString("student_name") + " | " +
                        rs.getString("course_name") + " | " +
                        rs.getDate("enrollment_date"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 8. Mark attendance
    private static void markAttendance() {
        String sql = """
                INSERT INTO attendance
                (student_id, course_id, attendance_date, status)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter student ID: ");
            ps.setInt(1, Integer.parseInt(sc.nextLine()));

            System.out.print("Enter course ID: ");
            ps.setInt(2, Integer.parseInt(sc.nextLine()));

            System.out.print("Enter date (YYYY-MM-DD): ");
            ps.setDate(3, Date.valueOf(sc.nextLine()));

            System.out.print("Enter status (Present/Absent): ");
            String status = sc.nextLine();

            if (!status.equalsIgnoreCase("Present")
                    && !status.equalsIgnoreCase("Absent")) {
                System.out.println("Status must be Present or Absent.");
                return;
            }

            ps.setString(4, status.equalsIgnoreCase("Present")
                    ? "Present" : "Absent");

            ps.executeUpdate();
            System.out.println("Attendance recorded successfully!");

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Attendance error: " + e.getMessage());
        }
    }

    // 9. View attendance
    private static void viewAttendance() {
        String sql = """
                SELECT s.student_name, c.course_name,
                       a.attendance_date, a.status
                FROM attendance a
                JOIN students s ON a.student_id = s.student_id
                JOIN courses c ON a.course_id = c.course_id
                ORDER BY a.attendance_date
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getString("student_name") + " | " +
                        rs.getString("course_name") + " | " +
                        rs.getDate("attendance_date") + " | " +
                        rs.getString("status"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 10. Enter or update marks
    private static void enterMarks() {
        String sql = """
                INSERT INTO marks
                (student_id, course_id, total_marks, marks_obtained)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                total_marks = ?,
                marks_obtained = ?
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter student ID: ");
            int studentId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter course ID: ");
            int courseId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter total marks: ");
            double total = Double.parseDouble(sc.nextLine());

            System.out.print("Enter marks obtained: ");
            double obtained = Double.parseDouble(sc.nextLine());

            if (!Double.isFinite(total) || !Double.isFinite(obtained)
                    || total <= 0 || obtained < 0 || obtained > total) {
                System.out.println("Invalid marks.");
                return;
            }

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setDouble(3, total);
            ps.setDouble(4, obtained);
            ps.setDouble(5, total);
            ps.setDouble(6, obtained);

            ps.executeUpdate();
            System.out.println("Marks saved successfully!");

        } catch (SQLException | NumberFormatException e) {
            System.out.println("Marks error: " + e.getMessage());
        }
    }

    // 11. Generate results
    private static void generateResults() {
        String sql = """
                SELECT s.student_name, c.course_name,
                       m.marks_obtained, m.total_marks,
                       ROUND(m.marks_obtained * 100.0 /
                             m.total_marks, 2) AS percentage,
                       CASE
                           WHEN m.marks_obtained >= m.total_marks * 0.40
                           THEN 'PASS'
                           ELSE 'FAIL'
                       END AS result
                FROM marks m
                JOIN students s ON m.student_id = s.student_id
                JOIN courses c ON m.course_id = c.course_id
                ORDER BY s.student_name
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getString("student_name") + " | " +
                        rs.getString("course_name") + " | Marks: " +
                        rs.getBigDecimal("marks_obtained") + "/" +
                        rs.getBigDecimal("total_marks") + " | Percentage: " +
                        rs.getBigDecimal("percentage") + "% | Result: " +
                        rs.getString("result"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 12. Calculate attendance percentage
    private static void attendancePercentage() {
        String sql = """
                SELECT s.student_name, c.course_name,
                       ROUND(
                           100.0 * SUM(
                               CASE WHEN a.status = 'Present'
                                    THEN 1 ELSE 0 END
                           ) / COUNT(*), 2
                       ) AS percentage
                FROM attendance a
                JOIN students s ON a.student_id = s.student_id
                JOIN courses c ON a.course_id = c.course_id
                GROUP BY s.student_id, s.student_name,
                         c.course_id, c.course_name
                ORDER BY s.student_name
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getString("student_name") + " | " +
                        rs.getString("course_name") + " | Attendance: " +
                        rs.getBigDecimal("percentage") + "%");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Main menu
    public static void main(String[] args) {

        try {
            initializeDatabase();
            System.out.println("Database and tables are ready.");
        } catch (SQLException e) {
            System.out.println("Database setup failed: " + e.getMessage());
            System.out.println(
                    "Check MySQL Server, username, password, and JDBC driver.");
            return;
        }

        while (true) {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Register Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. View Courses");
            System.out.println("6. Enroll Student");
            System.out.println("7. View Enrollments");
            System.out.println("8. Mark Attendance");
            System.out.println("9. View Attendance");
            System.out.println("10. Enter Marks");
            System.out.println("11. Generate Results");
            System.out.println("12. Attendance Percentage");
            System.out.println("13. Exit");

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> registerStudent();
                case "2" -> viewStudents();
                case "3" -> updateStudent();
                case "4" -> deleteStudent();
                case "5" -> viewCourses();
                case "6" -> enrollStudent();
                case "7" -> viewEnrollments();
                case "8" -> markAttendance();
                case "9" -> viewAttendance();
                case "10" -> enterMarks();
                case "11" -> generateResults();
                case "12" -> attendancePercentage();
                case "13" -> {
                    System.out.println("Thank you for using the system!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}