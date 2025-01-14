/* Name: Naz Serrano
 * ID: 110129474
 * Due Date: Dec 5 2024
 */

package assignment_2;

import java.util.ArrayList;
import java.util.Scanner;

// Class Date with 3 instance Variables
class Date {
    private int day;
    private int month;
    private int year;

    // Constructor for day, month, and year
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    // Copy Constructor in Date Class
    public Date(Date other) {
        this.day = other.day;
        this.month = other.month;
        this.year = other.year;
    }

    //Getters for each date part
    public int getYear(){
        return year;
    }

    public int getMonth(){
        return month;
    }

    public int getDay(){
        return day;
    }
    // Method to validate the date using Calendar and checking leap years
    public boolean isValid() {
        // First check for invalid month or day
        if (month < 1 || month > 12 || day < 1) {
            return false;
        }

        // Check for leap year validity for February
        if (month == 2) {
            if (isLeapYear(year)) {
                if (day > 29) return false;  // February 29 is only valid in leap years
            } else {
                if (day > 28) return false;  // February has only 28 days in a non-leap year
            }
        }

        // For months with 30 days (April, June, September, November)
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
            return false;
        }

        // For months with 31 days (January, March, May, July, August, October, December)
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (day > 31) {
                return false;
            }
        }

        return true;
    }

    // Method to check if the year is a leap year
    private boolean isLeapYear(int year) {
        // A year is a leap year if it is divisible by 4, except when it is divisible by 100 unless it is divisible by 400
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public boolean isEarlierThan(Date other) {
        if (this.year < other.year) return true;
        if (this.year == other.year && this.month < other.month) return true;
        if (this.year == other.year && this.month == other.month && this.day < other.day) return true;
        return false;
    }

    // Override toString() to display the date
    @Override
    public String toString() {
        return String.format("%02d/%02d/%d", day, month, year);  // Format as DD/MM/YYYY
    }

    // Equals method to compare two Date objects
    public boolean equals(Date other) {
        return this.day == other.day && this.month == other.month && this.year == other.year;
    }

    // Constructor with String Parameter for Date Format
    public static Date fromString(String dateString) {
        dateString = dateString.trim();  // Clean up any extra spaces
        String[] parts;

        // Try parsing DD/MM/YYYY format
        if (dateString.contains("/")) {
            parts = dateString.split("/");  // Try to split DD/MM/YYYY format
            if (parts.length == 3) {
                try {
                    int day = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int year = Integer.parseInt(parts[2]);
                    return new Date(day, month, year);
                } catch (NumberFormatException e) {
                    return null;  // Invalid format
                }
            }
        }

        // Try parsing MMM DD, YYYY format
        else {
            parts = dateString.split(" ");  // Try to split MMM DD, YYYY format
            if (parts.length == 3) {
                try {
                    int day = Integer.parseInt(parts[1].replace(",", ""));  // Remove any comma from the day
                    int year = Integer.parseInt(parts[2]);
                    int month = getMonthFromString(parts[0]);
                    if (month == -1) return null; // Invalid month string
                    return new Date(day, month, year);
                } catch (NumberFormatException e) {
                    return null;  // Invalid format
                }
            }
        }

        return null;  // If neither format worked, return null
    }

    // Convert month string like "Jan" to month number
    private static int getMonthFromString(String monthString) {
        switch (monthString.toLowerCase()) {
            case "jan": return 1;
            case "feb": return 2;
            case "mar": return 3;
            case "apr": return 4;
            case "may": return 5;
            case "jun": return 6;
            case "jul": return 7;
            case "aug": return 8;
            case "sep": return 9;
            case "oct": return 10;
            case "nov": return 11;
            case "dec": return 12;
            default: return -1;  // Invalid month string
        }
    }
}

// Abstract Person class to represent a person
abstract class Person {
    private String name;
    private Date birthday;
    private String country;
    private int difficulty;

    // Constructor with difficulty parameter
    public Person(String name, Date birthday, String country, int difficulty) {
        this.name = name;
        this.birthday = new Date(birthday);
        this.country = country;
        this.difficulty = difficulty;
    }

    // Copy Constructor
    public Person(Person other) {
        this.name = other.name;
        this.birthday = new Date(other.birthday);
        this.country = other.country;
        this.difficulty = other.difficulty;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for birthday
    public Date getBirthday() {
        return birthday;
    }

    // Getter for country
    public String getCountry() {
        return country;
    }

    // Getter for difficulty
    public int getDifficulty() {
        return difficulty;
    }

    // Abstract method for the person type
    public abstract String personType();

    // Abstract method for cloning the person
    public abstract Person clone();

    // Welcome message method
    public String welcomeMessage() {
        return "Welcome! Guess the birthday of " + personType() + " named " + name + " in difficulty " + difficulty;
    }

    // Success message method
    public String successMessage() {
        return "Wow! You are able to successfully guess the birthday of " + this.toString();
    }

    // toString method
    @Override
    public String toString() {
        return name + ", born on " + birthday + " in " + country;
    }
}

// Politician class extending Person
class Politician extends Person {
    private String party;

    // Constructor for Politician
    public Politician(String name, Date birthday, String country, int difficulty, String party) {
        super(name, birthday, country, difficulty);
        this.party = party;
    }

    // Copy Constructor for Politician
    public Politician(Politician other) {
        super(other);
        this.party = other.party;
    }

    // Implement personType method
    @Override
    public String personType() {
        return "politician";
    }

    // Implement clone method
    @Override
    public Politician clone() {
        return new Politician(this);
    }

    // toString method for Politician
    @Override
    public String toString() {
        return super.toString() + " is a member of Party " + party + ".";
    }
}

// Player class extending Person
class Player extends Person {
    private String game;
    private String team;

    // Constructor for Player
    public Player(String name, Date birthday, String country, int difficulty, String game, String team) {
        super(name, birthday, country, difficulty);
        this.game = game;
        this.team = team;
    }

    // Copy Constructor for Player
    public Player(Player other) {
        super(other);
        this.game = other.game;
        this.team = other.team;
    }

    // Implement personType method
    @Override
    public String personType() {
        return "player";
    }

    // Implement clone method
    @Override
    public Player clone() {
        return new Player(this);
    }

    // toString method for Player
    @Override
    public String toString() {
        return super.toString() + " is a Player of Team " + team + ".";
    }
}

// GuessMaker2 class to manage the game
class GuessMaker2 {
    private ArrayList<Person> persons;
    private int currentPerson;
    private Scanner scanner;

    public GuessMaker2() {
        persons = new ArrayList<>();
        currentPerson = 0;
        scanner = new Scanner(System.in);
    }

    public void addPerson(Person person) {
        persons.add(person);
    }

    //Controls Game Loop
    public void startGame() {
        System.out.println("Welcome to the Birthday Guessing Game!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a new person");
            System.out.println("2. Guess a person's birthday");
            System.out.println("3. Exit");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addNewPerson();
                    break;
                case 2:
                    guessBirthday();
                    break;
                case 3:
                    System.out.println("Exiting the game. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
//Method to add more people
    private void addNewPerson() {
        System.out.println("\nAdd a new person:");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter birthday (DD/MM/YYYY): ");
        String birthdayInput = scanner.nextLine();
        Date birthday = Date.fromString(birthdayInput);

        //Makes sure the user inputs a valid birthday format
        while (birthday == null || !birthday.isValid()) {
            System.out.println("Invalid date format. Please enter DD/MM/YYYY or MMM DD, YYYY.");
            birthdayInput = scanner.nextLine();
            birthday = Date.fromString(birthdayInput);
        }

        System.out.print("Enter country: ");
        String country = scanner.nextLine();

        System.out.print("Enter difficulty level (1-3): ");
        int difficulty = scanner.nextInt();
        while (difficulty < 1 || difficulty > 3){
            System.out.println("Invalid difficulty level. Please enter a level from 1-3");
            difficulty = scanner.nextInt();
        }
        scanner.nextLine(); // Consume newline

        System.out.println("1. Politician");
        System.out.println("2. Player");

        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        //Makes sure that the user is only allowed to input 1 or 2 for politician/player
        while (typeChoice != 1 && typeChoice != 2){
            System.out.println("Please enter either 1 or 2");
            typeChoice = scanner.nextInt();
            scanner.nextLine();
        }

        //1 is for politician, 2 is for player person logic
        if (typeChoice == 1) {
            System.out.print("Enter political party: ");
            String party = scanner.nextLine();
            Politician politician = new Politician(name, birthday, country, difficulty, party);
            persons.add(politician);
            System.out.println("Politician added successfully.");
        } else {
            System.out.print("Enter game: ");
            String game = scanner.nextLine();
            System.out.print("Enter team: ");
            String team = scanner.nextLine();
            Player player = new Player(name, birthday, country, difficulty, game, team);
            persons.add(player);
            System.out.println("Player added successfully.");
        }
    }

    //Birthday Guessing Menu
    private void guessBirthday() {
        System.out.println("\nChoose a person to guess their birthday:");
        for (int i = 0; i < persons.size(); i++) {
            System.out.println((i + 1) + ". " + persons.get(i).getName() + " on difficulty " + persons.get(i).getDifficulty());
        }

        System.out.println((persons.size() + 1) + ". Go back to main menu");

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            continue;
        }

        currentPerson = scanner.nextInt();
        scanner.nextLine();


        if (currentPerson == persons.size() + 1) {
            return;
        }

        if (currentPerson < 1 || currentPerson > persons.size()) {
            System.out.println("Invalid choice. Returning to main menu.");
            return;
        }

        Person person = persons.get(currentPerson - 1);
        System.out.println(person.welcomeMessage());

        boolean correct = false;
        //Game Loop for guessing.
        while (!correct) {
            System.out.print("Enter your guess (DD/MM/YYYY): ");
            String guess = scanner.nextLine();
            Date guessedDate = Date.fromString(guess);

            if (guessedDate == null) {
                System.out.println("Invalid date format. Try again.");
                continue;
            }

            if (guessedDate.equals(person.getBirthday())) {
                System.out.println(person.successMessage());
                correct = true;
            } else {
                System.out.println("Incorrect guess.");
                provideHints(person.getBirthday(), guessedDate, person.getDifficulty());
            }
        }
    }

    //Method that gives the user hints based on difficulty chosen
    private void provideHints(Date correctDate, Date guessedDate, int difficulty) {
        int correctYear = correctDate.getYear();
        int guessedYear = guessedDate.getYear();

        if (difficulty >= 1) { // Provide hints for the year
            if (guessedYear < correctYear) {
                System.out.println("Select a later year.");
            } else if (guessedYear > correctYear) {
                System.out.println("Select an earlier year.");
            } else if (guessedYear == correctYear){
                System.out.println("The year is correct");
            }
        }

        if (difficulty >= 2) { // Provide hints for the month
            int correctMonth = correctDate.getMonth();
            int guessedMonth = guessedDate.getMonth();
            if (guessedMonth < correctMonth) {
                System.out.println("Select a later month.");
            } else if (guessedMonth > correctMonth) {
                System.out.println("Select an earlier month.");
            } else if (guessedMonth == correctMonth){
                System.out.println("The month is correct");
            }
        }

        if (difficulty >= 3) { // Provide hints for the date
            int correctDay = correctDate.getDay();
            int guessedDay = guessedDate.getDay();
            if (guessedDay < correctDay) {
                System.out.println("Select a later day.");
            } else if (guessedDay > correctDay) {
                System.out.println("Select an earlier day.");
            } else if (guessedDay == correctDay){
                System.out.println("The day is correct");
            }
        }
    }
}


// Main class to test
public class Main {
    public static void main(String[] args) {
        Politician trudeau = new Politician("Justin Trudeau", new Date(25, 12, 1971), "Canada", 1, "Liberal");
        Player ronaldo = new Player("Cristiano Ronaldo", new Date(5, 2, 1985), "Portugal", 2, "Soccer" , "Al Nassr");
        Politician pierre = new Politician("Pierre Poilievre", new Date(3, 6, 1979), "Canada", 3, "Conservative");
        GuessMaker2 gm = new GuessMaker2();
        gm.addPerson(trudeau);
        gm.addPerson(ronaldo);
        gm.addPerson(pierre);
        gm.startGame();
    }
}
