package without_module;

import com.google.gson.annotations.SerializedName;

import java.util.*;

public class Module {
    @SerializedName("id")
    private String id;
    @SerializedName("banks")
    private List<Bank> banks;
    private Scanner scan;

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
        if (which != null && which.getQuestionsEng().size() != 0 && which.getQuestionsWel().size() != 0) {
            which.removeQuestion();
        } else if (which != null && which.getQuestionsEng().size() == 0 && which.getQuestionsWel().size() == 0) {
            System.err.println("No questions to be removed.");
        } else {
            System.err.println("This bank does not exist.");
        }
    }

    /**
     * Get the banks of a certain module with all its information
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
        Bank which = searchForBank(scan.nextLine());
        Scanner scan = new Scanner(System.in);
        String nextAns = "";
        do {
            if (which != null) {
                System.out.println("Pick the type of the question ( s - single choice, fb - fill in the blanks):");
                String type = scan.nextLine().toLowerCase();
                switch (type) {
                    case "s" -> {
                        createQuestion(which, new SingleChoice(), "english");
                        createQuestion(which, new SingleChoice(), "welsh");
                    }
                    case "fb" -> {
                        createQuestion(which, new FillTheBlanks(), "english");
                        createQuestion(which, new FillTheBlanks(), "welsh");
                    }
                    default -> System.err.println("Wrong type.");
                }
            } else {
                System.err.println("This bank does not exist.");
            }
            System.out.println("Another question (Y / N)");
            nextAns = scan.nextLine().toUpperCase();
        } while (!nextAns.equals("N"));
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
            if (which != null && which.getQuestionsEng().size() == 0 && which.getQuestionsWel().size() == 0) {
                banks.remove(which);
                System.out.println("Removed: " + which.getID());
            } else if (which != null && which.getQuestionsEng().size() != 0 && which.getQuestionsWel().size() != 0) {
                System.err.println("This bank is not empty.");
            } else {
                System.err.println("This bank does not exist.");
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    /*
        Helper function to search for a bank, used for listing a questions in a bank and in deleting a bank
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
    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "Module{" +
                "id='" + id + '\'' +
                ", banks=" + banks +
                '}';
    }

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
