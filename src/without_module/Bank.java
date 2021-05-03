package without_module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.*;

public class Bank {
    @SerializedName("id")
    @Expose
    private String ID;
    @SerializedName("questionsEng")
    @Expose
    private List<Question> questionsEng;
    @SerializedName("questionsPL")
    @Expose
    private List<Question> questionsPL;

    /**
     * No argument constructor
     */
    public Bank() {
        this("No id");
    }

    /**
     * Constructor that allows us to make a new Bank
     */
    public Bank(String ID) {
        questionsEng = new ArrayList<>();
        questionsPL = new ArrayList<>();
        this.ID = ID;
    }

    /**
     * Get the banks ID
     */
    public String getID() {
        return ID;
    }

    /**
     *  List all the questions in english from the bank
     * @return questionsEng
     */
    public List<Question> getQuestionsEng() {
        return questionsEng;
    }
    /**
     *  List all the questions in polish from the bank
     * @return questionsPl
     */
    public List<Question> getQuestionsPL() {
        return questionsPL;
    }

    /**
     * Read keyboard allows us to set custom properties to the bank
     */
    public void readKeyboard() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the ID of the bank");
        // I guess this try could be omitted? Could not find an exception
        String id = scan.nextLine();
        // if the ID provided by user is an empty string it should not be used as the bank id
        if(id.equals("")) {
            System.err.println("ID cannot be empty.");
            return;
        }
        this.ID = id;
    }

    /**
     * Add a new question
     */
    public void addNewQuestion(Question newQuestion, String language) {
        if (newQuestion != null && !language.equals("")) {
            switch(language) {
                case "english":
                    questionsEng.add(newQuestion);
                    break;
                case "polish":
                    questionsPL.add(newQuestion);
                    break;
                default:
                    System.out.println("Something went wrong");
                    break;
            }
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
        // variable to count (for printing only)
        int i = 1;
        sb.append("Question in language one:");
        for (Question q : questionsEng) {
            sb.append("\n").append(i).append(". ").append(q.toString());
            i++;
        }
        i = 1;
        sb.append("\n\nQuestion in language two:");
        for (Question q : questionsPL) {
            sb.append("\n").append(i).append(". ").append(q.toString());
            i++;
        }
        return sb.toString();
    }

    /**
     * Removes a question - needs to be removing both english and polish version
     */
    public void removeQuestion() {
        System.out.println(listQuestions());
        System.out.println("Which question to remove?");
        Scanner scan = new Scanner(System.in);
        try {
            int questionNumber = Integer.parseInt(scan.nextLine());
            // questions are number 1 - question.size()
            if(questionNumber <= questionsEng.size() && questionNumber >= 1) {
                // just add the other array in here as well
                System.out.println("Removed" + questionsEng.get(questionNumber - 1));
                System.out.println("Removed" + questionsPL.get(questionNumber - 1));
                questionsEng.remove(questionNumber - 1);
                questionsPL.remove(questionNumber - 1);
            } else {
                System.err.println("Wrong question number");
            }
        } catch(InputMismatchException | NumberFormatException e) {
            System.err.println("Invalid input");
        }
    }


    /**
     * Information about a bank
     * @return BankID and the questions
     */
    @Override
    public String toString() {
        return "BankID: " + this.ID + listQuestions();
    }

    /**
     * Used when searching for a particular bank
     * @param obj
     * @return
     */
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