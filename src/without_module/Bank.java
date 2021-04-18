package without_module;

import java.util.*;

public class Bank {
    private String ID;
    private List<Question> questions;

    /**
     * No argument constructor
     */
    public Bank() {
        questions = new ArrayList<>();
        ID = "No ID";
    }

    /**
     * Constructor that allows us to make a new Bank
     */
    public Bank(String ID) {
        questions = new ArrayList<>();
        this.ID = ID;
    }

    /**
     * Allows the user to set ID
     *
     * @param ID - the ID of the bank
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Get the banks ID
     */
    public String getID() {
        return ID;
    }

    /**
     *  List all the questions from the bank
     * @return questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Read keyboard allows us to set custom properties to the bank
     */
    public void readKeyboard() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the ID of the bank");
        // I guess this try could be omitted? Could not find an exception
        try {
            this.ID = scan.nextLine();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new question
     */
    public void addNewQuestion(Question newQuestion) {
        if (newQuestion != null) {
            questions.add(newQuestion);
        } else {
            System.out.println("Failed to add the new question to the bank.");
        }

    }

    /**
     * List questions
     *
     * @return a String of questions
     */
    public String listQuestions() {
        StringBuilder sb = new StringBuilder();
        // helper to count (for printing only)
        int i = 1;
        for (Question q : questions) {
            sb.append("\n").append(i).append(". ").append(q.toString()).append("\n");
            i++;
        }
        return sb.toString();
    }

    public void removeQuestion() {
        System.out.println("Which question to remove?");
        Scanner scan = new Scanner(System.in);
        try {
            int question = Integer.parseInt(scan.nextLine());
            // questions are number 1 - question.size()
            if(question <= questions.size() && question >= 1) {
                questions.remove(question - 1);
            }

        } catch(InputMismatchException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "BankID: " + this.ID + listQuestions();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Bank other = (Bank)obj;


        if (ID == null) {
            if (other.ID != null)
                return false;
        } else if (!ID.equals(other.ID))
            return false;
        return true;
    }

}