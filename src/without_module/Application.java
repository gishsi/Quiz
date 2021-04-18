package without_module;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    private Scanner scan;
    // Store banks
    private List<Bank> banks;


    /**
     * Constructor for the Application class
     */
    public Application() {
        scan = new Scanner(System.in);
        banks = new ArrayList<>();
    }

    /////////////////////////////////////// MAIN ///////////////////////////////////////////
    public static void main(String[] args) {
        Application app = new Application();
        app.runTest();
        app.runMenu();
    }

    /////////////////////////////////////// MENU ///////////////////////////////////////////
    /**
     * Print the menu
     */
    public void printMenu() {
        System.out.println("1. Create a new question bank.");
        // make this so that we can add more than 1 question at a time, this will also support 2 languages
        System.out.println("2. Add a new question to an existing bank.");
        System.out.println("3. Delete a bank (must be empty).");
        // there must be modules
        System.out.println("4. List all the question banks for a specific module.");
        System.out.println("5. Remove a question from a bank.");
        System.out.println("6. List all the banks");
        System.out.println("q - Quit");
    }

    /**
     * Run the menu
     */
    public void runMenu() {
        String option = "";
        System.out.println("Welcome to the Quiz application (Teacher)");
        System.out.println("These are your options: ");
        do {
            printMenu();
            option = scan.nextLine().toUpperCase();
            switch (option) {
                case "1":
                    addBank();
                    break;
                case "2":
                    addQuestion();
                    break;
                case "3":
                    deleteBank();
                    break;
                case "4":
                    // every bank must be specific for a module -> either a module class or string
                    // list the banks
                    System.out.println(listBanks());
                    System.out.println("Pick a bank");
                    Bank which = searchForBank(scan.nextLine());
                    listQuestions(which);
                    break;
                case "5":
                    // list the banks
                    System.out.println(listBanks());
                    System.out.println("Pick a bank");
                    which = searchForBank(scan.nextLine());
                    listQuestions(which);
                    // remove the question
                    removeQuestion(which);
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Wrong choice");
                    break;
            }
        } while (!(option.equals("Q")));
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    private void bankExample() {
        // a couple of banks for testing
        Bank bank1 = new Bank();
        bank1.setID("Bank1");
        Bank bank2 = new Bank();
        bank2.setID("Bank2");
        Bank bank3 = new Bank();
        bank3.setID("Bank3");
        banks.add(bank1);
        banks.add(bank2);
        banks.add(bank3);
        // making the questions this way is only used here to provide data to work with
        Question newQuestion = new SingleChoice("SingleChoice", "What is your fav colour?", "Blue");
        Question newQuestion1 = new SingleChoice("SingleChoice", "Where do you live?", "The fuck bro");
        Question newQuestion2 = new SingleChoice("SingleChoice", "Can I be your pet?", "THE FUCK BRO");
        bank1.addNewQuestion(newQuestion);
        bank1.addNewQuestion(newQuestion1);
        bank1.addNewQuestion(newQuestion2);
        bank2.addNewQuestion(newQuestion1);
        bank2.addNewQuestion(newQuestion2);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Method that runs tests in our application so that main could be as short as possible
     */
    public void runTest() {
        bankExample();
    }

    /**
     * Removes a question
     */
    public void removeQuestion(Bank which) {
        if (which != null) {
            which.removeQuestion();
        } else {
            System.err.println("This bank does not exist.");
        }
    }
    /**
     * Add a new question
     */
    public void addQuestion() {
        listBanks();
        System.out.println("Pick a bank");
        Bank which = searchForBank(scan.nextLine());

        if (which != null) {
            Question newQuestion = new SingleChoice();
            newQuestion.readKeyboard();
            which.addNewQuestion(newQuestion);
        } else {
            System.err.println("This bank does not exist.");
        }
    }

    /**
     * Adds a new bank to the banks array list
     */
    public void addBank() {
        Bank newBank = new Bank();
        newBank.readKeyboard();
        banks.add(newBank);
    }

    /**
     * List all the banks
     *
     * @return return a string that has all the banks in it
     */
    public String listBanks() {
        StringBuilder sb = new StringBuilder();
        for (Bank bank : banks) {
            sb.append(bank.getID()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Listing all the questions for a specific bank
     */
    public void listQuestions(Bank which) {
        if (which != null && which.getQuestions().size() != 0) {
            System.out.println(which.listQuestions());
        } else if (which != null && which.getQuestions().size() == 0) {
            System.err.println("This bank has no questions.");
        } else {
            System.err.println("This bank does not exist.");
        }
    }

    /**
     * Method for deleting a bank
     */
    public void deleteBank() {
        listBanks();
        System.out.println("Enter the ID of the bank that you want to delete:");
        Bank which = searchForBank(scan.nextLine());
        if (which != null && which.getQuestions().size() == 0) {
            banks.remove(which);
            System.out.println("Removed: " + which.getID());
        } else if (which != null && which.getQuestions().size() != 0) {
            System.err.println("This bank is not empty.");
        } else {
            System.err.println("This bank does not exist.");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    /*
        Helper function to search for a bank, used for listing a questions in a bank and in deleting a bank
     */
    private Bank searchForBank(String who) {
        Bank bankToDelete = new Bank(who);
        Bank which = null;
        // getting the right bank into the which variable
        for (Bank b : banks) {
            if (bankToDelete.equals(b)) {
                which = b;
            }
        }
        return which;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////
}
