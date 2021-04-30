package without_module;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Question {
    String type;
    String content;
    boolean answered;
    /**
     * No arg constructor
     */
    public Question() {
        type = "No type";
        content = "No content";
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    /**
     * Get the content of a question
     * @return content - the question
     */
    public String getContent() {
        return content;
    }

    /**
     * Read the properties of the question
     */
    public void readKeyboard() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the content:");
        // I guess this try could be omitted? Could not find an exception
        try {
            this.content = scan.nextLine();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function overwritten in every sub-class, responsible for getting the correct answer to a question
     * @return an empty string
     */
    public String getAnswer() {
        return "";
    }

    public String display() {
        return this.content;
    }


    /**
     * toString method return information about a question
     * @return
     */
    @Override
    public String toString() {
        return "Question's type: " + type + ", content: " + content;
    }
}
