package task13;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Candidate {
    private int id;
    private String name;

    public Candidate(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

public class Project1 {

    Scanner sc = new Scanner(System.in);

    Map<String, User> users = new HashMap<>();

    Map<String, Boolean> userVoted = new HashMap<>();

    Map<Integer, Candidate> candidates = new HashMap<>();

    Map<String, Integer> candidateVotes = new HashMap<>();

    public static void main(String[] args) {

        Project1 system = new Project1();

        system.addCandidates();

        system.startVotingSystem();
    }
    void addCandidates() {

        Candidate c1 = new Candidate(1, "Gayathri");
        Candidate c2 = new Candidate(2, "Priya");
        Candidate c3 = new Candidate(3, "Anil");

        candidates.put(c1.getId(), c1);
        candidates.put(c2.getId(), c2);
        candidates.put(c3.getId(), c3);

        candidateVotes.put(c1.getName(), 0);
        candidateVotes.put(c2.getName(), 0);
        candidateVotes.put(c3.getName(), 0);
    }

    void startVotingSystem() {

        while (true) {

            System.out.println("\n==============");
            System.out.println("      ONLINE VOTING SYSTEM");
            System.out.println("================");

            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Candidate List");
            System.out.println("4. View Total Votes");
            System.out.println("5. Display Result");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        register();
                        break;

                    case 2:
                        login();
                        break;

                    case 3:
                        displayCandidates();
                        break;

                    case 4:
                        viewTotalVotes();
                        break;

                    case 5:
                        displayResult();
                        break;

                    case 6:
                        System.out.println("Thank you for using Online Voting System.");
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (NumberFormatException e) {

                System.out.println("Please enter numbers only.");

            } catch (Exception e) {

                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }

    void register() {

        System.out.println("\n REGISTRATION");

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }

        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
            return;
        }

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            return;
        }

        User user = new User(username, password);

        users.put(username, user);

        userVoted.put(username, false);

        System.out.println("Registration successful!");
    }

    void login() {

        System.out.println("\n LOGIN");

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user = users.get(username);

        if (user != null && user.getPassword().equals(password)) {

            System.out.println("Login successful!");
            System.out.println("Welcome, " + username);

            userMenu(username);

        } else {

            System.out.println("Invalid username or password.");
        }
    }

    void userMenu(String username) {

        while (true) {

            System.out.println("\n USER MENU");

            System.out.println("1. View Candidates");
            System.out.println("2. Cast Vote");
            System.out.println("3. Logout");

            System.out.print("Enter choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        displayCandidates();
                        break;

                    case 2:
                        castVote(username);
                        break;

                    case 3:
                        System.out.println("Logged out successfully.");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");

            } catch (Exception e) {

                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    void displayCandidates() {

        System.out.println("\n CANDIDATE LIST");

        for (Candidate candidate : candidates.values()) {

            System.out.println(
                candidate.getId() + ". " +
                candidate.getName()
            );
        }
    }

    void castVote(String username) {

        if (userVoted.get(username)) {

            System.out.println("You have already voted.");
            System.out.println("Each student can vote only once.");

            return;
        }

        displayCandidates();

        System.out.print("\nEnter candidate ID: ");

        try {

            int candidateId = Integer.parseInt(sc.nextLine());

            Candidate candidate = candidates.get(candidateId);

            if (candidate == null) {

                System.out.println("Invalid candidate ID.");
                return;
            }

            String candidateName = candidate.getName();

            // Increase candidate vote count
            int currentVotes = candidateVotes.get(candidateName);

            candidateVotes.put(
                candidateName,
                currentVotes + 1
            );

            userVoted.put(username, true);

            System.out.println("\nVote cast successfully!");
            System.out.println("You voted for: " + candidateName);

        } catch (NumberFormatException e) {

            System.out.println("Please enter a valid candidate ID.");

        } catch (Exception e) {

            System.out.println("Error while casting vote: " + e.getMessage());
        }
    }

    void viewTotalVotes() {

        System.out.println("\nTOTAL VOTES");

        int totalVotes = 0;

        for (Map.Entry<String, Integer> entry : candidateVotes.entrySet()) {

            System.out.println(
                entry.getKey() + " : " +
                entry.getValue() + " votes"
            );

            totalVotes += entry.getValue();
        }

        System.out.println("-------------------------------");
        System.out.println("Total Votes = " + totalVotes);
    }

    void displayResult() {

        System.out.println("\nELECTION RESULT");

        if (candidateVotes.isEmpty()) {

            System.out.println("No candidates available.");
            return;
        }

        String winner = null;
        String runnerUp = null;

        int highestVotes = -1;
        int secondHighestVotes = -1;

        for (Map.Entry<String, Integer> entry : candidateVotes.entrySet()) {

            String candidateName = entry.getKey();
            int votes = entry.getValue();

            if (votes > highestVotes) {

                secondHighestVotes = highestVotes;
                runnerUp = winner;

                highestVotes = votes;
                winner = candidateName;

            } else if (votes > secondHighestVotes) {

                secondHighestVotes = votes;
                runnerUp = candidateName;
            }
        }

        viewTotalVotes();

        System.out.println("\nFINAL RESULT");

        System.out.println(
            "Finalist / Winner : " +
            winner +
            " (" + highestVotes + " votes)"
        );

        if (runnerUp != null) {

            System.out.println(
                "Runner-Up : " +
                runnerUp +
                " (" + secondHighestVotes + " votes)"
            );
        }

        System.out.println("=============");
    }
}

