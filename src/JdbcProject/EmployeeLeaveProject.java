package JdbcProject;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class EmployeeLeaveProject {

    private static final String URL =
            "jdbc:mysql://localhost:3306/employee_leave_db";

    private static final String USER = "root";

    private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("==========");
            System.out.println(" EMPLOYEE LEAVE MANAGEMENT SYSTEM");
            System.out.println("===========");

            while (true) {
                System.out.println("\n1. Register Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Apply for Leave");
                System.out.println("4. View Leave Requests");
                System.out.println("5. Approve Leave");
                System.out.println("6. Reject Leave");
                System.out.println("7. View Leave Balance");
                System.out.println("8. Update Employee");
                System.out.println("9. Delete Employee");
                System.out.println("10. Exit");

                int choice = readInt("Enter your choice: ");

                switch (choice) {
                    case 1:
                        registerEmployee();
                        break;
                    case 2:
                        viewEmployees();
                        break;
                    case 3:
                        applyLeave();
                        break;
                    case 4:
                        viewLeaveRequests();
                        break;
                    case 5:
                        processLeave(true);
                        break;
                    case 6:
                        processLeave(false);
                        break;
                    case 7:
                        viewLeaveBalance();
                        break;
                    case 8:
                        updateEmployee();
                        break;
                    case 9:
                        deleteEmployee();
                        break;
                    case 10:
                        System.out.println("Application closed.");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found.");
            System.out.println("Add Connector/J to the project Build Path.");
        }
    }

    private static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    private static String readText(String message) {
        while (true) {
            System.out.print(message);
            String value = sc.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This field cannot be empty.");
        }
    }

    private static LocalDate readDate(String message) {
        while (true) {
            try {
                System.out.print(message);
                return LocalDate.parse(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println(
                        "Enter a valid date in YYYY-MM-DD format.");
            }
        }
    }

    // 1. Employee Registration

    private static void registerEmployee() {

        String sql = "INSERT INTO employees "
                + "(employee_name, email, department, joining_date) "
                + "VALUES (?, ?, ?, ?)";

        String balanceSql = "INSERT INTO leave_balance "
                + "(employee_id, total_leaves, used_leaves, remaining_leaves) "
                + "VALUES (?, 20, 0, 20)";

        try (Connection con = getConnection()) {

            con.setAutoCommit(false);

            try {
                int employeeId;

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     sql,
                                     Statement.RETURN_GENERATED_KEYS)) {

                    ps.setString(1, readText("Employee name: "));
                    ps.setString(2, readText("Email: "));
                    ps.setString(3, readText("Department: "));
                    ps.setDate(4, Date.valueOf(
                            readDate("Joining date (YYYY-MM-DD): ")));

                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                    "Employee ID was not generated.");
                        }
                        employeeId = rs.getInt(1);
                    }
                }

                try (PreparedStatement ps =
                             con.prepareStatement(balanceSql)) {

                    ps.setInt(1, employeeId);
                    ps.executeUpdate();
                }

                con.commit();

                System.out.println(
                        "Employee registered successfully. ID: "
                                + employeeId);

            } catch (Exception e) {
                con.rollback();
                System.out.println(
                        "Registration failed. Changes rolled back.");
                System.out.println(e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // 2. View Employees

    private static void viewEmployees() {

        String sql = "SELECT * FROM employees ORDER BY employee_id";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- EMPLOYEE DETAILS ---");

            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("employee_id")
                        + " | Name: " + rs.getString("employee_name")
                        + " | Email: " + rs.getString("email")
                        + " | Department: " + rs.getString("department")
                        + " | Joining Date: " + rs.getDate("joining_date"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 3. Apply for Leave

    private static void applyLeave() {

        int employeeId = readInt("Employee ID: ");

        String leaveType = readText(
                "Leave type (CASUAL/SICK/ANNUAL): ");

        LocalDate startDate = readDate(
                "Start date (YYYY-MM-DD): ");

        LocalDate endDate = readDate(
                "End date (YYYY-MM-DD): ");

        String reason = readText("Reason: ");

        if (endDate.isBefore(startDate)) {
            System.out.println(
                    "End date cannot be before start date.");
            return;
        }

        if (startDate.isBefore(LocalDate.now())) {
            System.out.println(
                    "Leave cannot start in the past.");
            return;
        }

        String checkSql =
                "SELECT employee_id FROM employees WHERE employee_id = ?";

        String sql = "INSERT INTO leave_requests "
                + "(employee_id, leave_type, start_date, end_date, "
                + "leave_days, reason) VALUES (?, ?, ?, ?, ?, ?)";

        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        if (days > Integer.MAX_VALUE) {
            System.out.println("Invalid leave duration.");
            return;
        }

        try (Connection con = getConnection()) {

            try (PreparedStatement check =
                         con.prepareStatement(checkSql)) {

                check.setInt(1, employeeId);

                try (ResultSet rs = check.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Employee not found.");
                        return;
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, employeeId);
                ps.setString(2, leaveType.toUpperCase());
                ps.setDate(3, Date.valueOf(startDate));
                ps.setDate(4, Date.valueOf(endDate));
                ps.setInt(5, (int) days);
                ps.setString(6, reason);

                ps.executeUpdate();

                System.out.println(
                        "Leave request submitted successfully.");
                System.out.println(
                        "Status: PENDING | Calendar days: " + days);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 4. View Leave Requests

    private static void viewLeaveRequests() {

        String sql = "SELECT lr.request_id, e.employee_name, "
                + "lr.leave_type, lr.start_date, lr.end_date, "
                + "lr.leave_days, lr.reason, lr.status "
                + "FROM leave_requests lr "
                + "JOIN employees e ON lr.employee_id = e.employee_id "
                + "ORDER BY lr.request_id";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- LEAVE REQUESTS ---");

            while (rs.next()) {
                System.out.println(
                        "Request ID: " + rs.getInt("request_id")
                        + " | Employee: " + rs.getString("employee_name")
                        + " | Type: " + rs.getString("leave_type")
                        + " | From: " + rs.getDate("start_date")
                        + " | To: " + rs.getDate("end_date")
                        + " | Days: " + rs.getInt("leave_days")
                        + " | Status: " + rs.getString("status")
                        + " | Reason: " + rs.getString("reason"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 5 and 6. Approve or Reject Leave

    private static void processLeave(boolean approve) {

        int requestId = readInt("Leave request ID: ");

        String newStatus = approve ? "APPROVED" : "REJECTED";

        try (Connection con = getConnection()) {

            con.setAutoCommit(false);

            try {
                int employeeId;
                int days;

                String requestSql =
                        "SELECT employee_id, leave_days, status "
                        + "FROM leave_requests "
                        + "WHERE request_id = ? FOR UPDATE";

                try (PreparedStatement ps =
                             con.prepareStatement(requestSql)) {

                    ps.setInt(1, requestId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {
                            System.out.println("Request not found.");
                            con.rollback();
                            return;
                        }

                        if (!"PENDING".equals(rs.getString("status"))) {
                            System.out.println(
                                    "This request has already been processed.");
                            con.rollback();
                            return;
                        }

                        employeeId = rs.getInt("employee_id");
                        days = rs.getInt("leave_days");
                    }
                }

                if (approve) {

                    String balanceSql =
                            "SELECT remaining_leaves FROM leave_balance "
                            + "WHERE employee_id = ? FOR UPDATE";

                    try (PreparedStatement ps =
                                 con.prepareStatement(balanceSql)) {

                        ps.setInt(1, employeeId);

                        try (ResultSet rs = ps.executeQuery()) {

                            if (!rs.next()) {
                                throw new SQLException(
                                        "Leave balance record not found.");
                            }

                            if (rs.getInt("remaining_leaves") < days) {
                                System.out.println(
                                        "Insufficient leave balance.");
                                con.rollback();
                                return;
                            }
                        }
                    }

                    String updateBalance =
                            "UPDATE leave_balance "
                            + "SET used_leaves = used_leaves + ?, "
                            + "remaining_leaves = remaining_leaves - ? "
                            + "WHERE employee_id = ? "
                            + "AND remaining_leaves >= ?";

                    try (PreparedStatement ps =
                                 con.prepareStatement(updateBalance)) {

                        ps.setInt(1, days);
                        ps.setInt(2, days);
                        ps.setInt(3, employeeId);
                        ps.setInt(4, days);

                        if (ps.executeUpdate() != 1) {
                            throw new SQLException(
                                    "Could not update leave balance.");
                        }
                    }
                }

                String updateRequest =
                        "UPDATE leave_requests SET status = ? "
                        + "WHERE request_id = ? AND status = 'PENDING'";

                try (PreparedStatement ps =
                             con.prepareStatement(updateRequest)) {

                    ps.setString(1, newStatus);
                    ps.setInt(2, requestId);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "Could not update leave request.");
                    }
                }

                con.commit();

                System.out.println(
                        "Leave request " + newStatus.toLowerCase()
                                + " successfully.");

            } catch (Exception e) {
                con.rollback();
                System.out.println(
                        "Operation failed. Changes rolled back.");
                System.out.println(e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // 7. View Leave Balance

    private static void viewLeaveBalance() {

        int employeeId = readInt("Employee ID: ");

        String sql = "SELECT e.employee_name, lb.total_leaves, "
                + "lb.used_leaves, lb.remaining_leaves "
                + "FROM employees e "
                + "JOIN leave_balance lb "
                + "ON e.employee_id = lb.employee_id "
                + "WHERE e.employee_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("\n--- LEAVE BALANCE ---");
                    System.out.println(
                            "Employee: " + rs.getString("employee_name"));
                    System.out.println(
                            "Total leaves: " + rs.getInt("total_leaves"));
                    System.out.println(
                            "Used leaves: " + rs.getInt("used_leaves"));
                    System.out.println(
                            "Remaining leaves: "
                                    + rs.getInt("remaining_leaves"));
                } else {
                    System.out.println("Employee not found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 8. Update Employee

    private static void updateEmployee() {

        int employeeId = readInt("Employee ID to update: ");

        String name = readText("New employee name: ");
        String email = readText("New email: ");
        String department = readText("New department: ");

        String sql = "UPDATE employees "
                + "SET employee_name = ?, email = ?, department = ? "
                + "WHERE employee_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, department);
            ps.setInt(4, employeeId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 9. Delete Employee

    private static void deleteEmployee() {

        int employeeId = readInt("Employee ID to delete: ");

        try (Connection con = getConnection()) {

            con.setAutoCommit(false);

            try {
                String checkSql =
                        "SELECT employee_id FROM employees "
                        + "WHERE employee_id = ? FOR UPDATE";

                try (PreparedStatement ps =
                             con.prepareStatement(checkSql)) {

                    ps.setInt(1, employeeId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("Employee not found.");
                            con.rollback();
                            return;
                        }
                    }
                }

                String requestCheck =
                        "SELECT request_id FROM leave_requests "
                        + "WHERE employee_id = ? LIMIT 1";

                try (PreparedStatement ps =
                             con.prepareStatement(requestCheck)) {

                    ps.setInt(1, employeeId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            System.out.println(
                                    "Cannot delete: leave request history exists.");
                            con.rollback();
                            return;
                        }
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM leave_balance WHERE employee_id = ?")) {

                    ps.setInt(1, employeeId);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM employees WHERE employee_id = ?")) {

                    ps.setInt(1, employeeId);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "Employee deletion failed.");
                    }
                }

                con.commit();
                System.out.println("Employee deleted successfully.");

            } catch (Exception e) {
                con.rollback();
                System.out.println("Delete failed: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}