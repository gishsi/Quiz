package without_module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.*;

public class Module {
    private Scanner scan;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("banks")
    @Expose
    private List<Bank> banks;


    /**
     * No arg constructor
     */
    public Module() {
        this("No id");
    }

    /**
     * Creates a Module
     *
     * @param id - id of the module
     */
    public Module(String id) {
        this.id = id;
        banks = new ArrayList<>();
        scan = new Scanner(System.in);
    }

    /**
     * Used to get the ID of the module
     *
     * @return id of the module
     */
    public String getId() {
        return id;
    }

    /**
     * Removes a question
     */
    public void removeQuestion(Bank which) {
        if (which != null && which.getQuestionsEng().size() != 0 && which.getQuestionsPL().size() != 0) {
            which.removeQuestion();
        } else if (which != null && which.getQuestionsEng().size() == 0 && which.getQuestionsPL().size() == 0) {
            System.err.println("No questions to be removed.");
        } else {
            System.err.println("This bank does not exist.");
        }
    }

    /**
     * Get the banks of a certain module with all its information
     *
     * @return list of banks
     */
    public List<Bank> getBanks() {
        return banks;
    }

    /**
     * Add a new question
     */
    public void addQuestion() {
        listBanks();
        System.out.println("Pick a bank");
        Bank which = searchForBank("Bank1"); //"Bank1"scan.nextLine()
        Scanner scan = new Scanner(System.in);
        String nextQuestion = "";
        if (which != null) {
            do {
                System.out.println("Pick the type of the question ( s - single choice, fb - fill in the blanks):");
                String type = scan.nextLine().toLowerCase();
                switch (type) {
                    case "s" -> {
                        createQuestion(which, new SingleChoice(), "english");
                        createQuestion(which, new SingleChoice(), "polish");
                    }
                    case "fb" -> {
                        createQuestion(which, new FillTheBlanks(), "english");
                        createQuestion(which, new FillTheBlanks(), "polish");
                    }
                    default -> System.out.println("Wrong type.");
                }
                System.out.println("Another question (Y / N)");
                nextQuestion = scan.nextLine().toUpperCase();
            } while (!nextQuestion.equals("N"));
        } else {
            System.err.println("This bank does not exist.");
        }
    }

    /**
     * This helper function helps to avoid code duplication with two languages
     *
     * @param which    - the current bank
     * @param type     - type of the question
     * @param language - language of the question
     */
    private void createQuestion(Bank which, Question type, String language) {
        System.out.println("Enter the question in " + language);
        type.readKeyboard();
        which.addNewQuestion(type, language);
    }

    /**
     * Adds a new bank to the banks array list
     */
    public void addBank() {
        Bank newBank = new Bank();
        newBank.readKeyboard();
        if (newBank.getID().equals("No id")) {
            System.err.println("Could not create a new bank.");
            return;
        }
        for (Bank b : banks) {
            if (b.getID().equals(newBank.getID())) {
                System.err.println("A bank with that ID already exists.");
                return;
            }
        }
        System.out.println("Added bank with id: " + newBank.getID());
        banks.add(newBank);
    }

    /**
     * List all the banks
     */
    public void listBanks() {
        StringBuilder sb = new StringBuilder();
        if (banks.size() != 0) {
            for (Bank bank : banks) {
                sb.append(bank.getID()).append("\n");
            }
            System.out.println(sb.toString());
        } else {
            System.err.println("This module has no banks.");
        }
    }

    /**
     * Listing all the questions for a specific bank
     */
    public void listQuestions(Bank which) {
        if (which != null && which.getQuestionsEng().size() != 0) {
            System.out.println(which.listQuestions());
        } else if (which != null && which.getQuestionsEng().size() == 0) {
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
        if (banks.size() != 0) {
            System.out.println("Enter the ID of the bank that you want to delete:");
            Bank which = searchForBank(scan.nextLine());
            if (which != null && which.getQuestionsEng().size() == 0 && which.getQuestionsPL().size() == 0) {
                banks.remove(which);
                System.out.println("Removed: " + which.getID());
            } else if (which != null && which.getQuestionsEng().size() != 0 && which.getQuestionsPL().size() != 0) {
                System.err.println("This bank is not empty.");
            } else {
                System.err.println("This bank does not exist.");
            }
        }
    }

    /**
     * Helper function to search for a bank, used for listing a questions in a bank and in deleting a bank
     */
    public Bank searchForBank(String who) {
        Bank bank = new Bank(who);
        Bank which = null;
        // getting the right bank into the which variable
        for (Bank b : banks) {
            if (bank.equals(b)) {
                which = b;
            }
        }
        return which;
    }

    /**
     * Information about a module
     * @return string with module id and banks
     */
    @Override
    public String toString() {
        return "Module{" +
                "id='" + id + '\'' +
                ", banks=" + banks +
                '}';
    }

    /**
     * Used while searching for the right module
     * @param obj
     * @return true if found
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Module other = (Module) obj;


        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
