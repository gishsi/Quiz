package without_module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Question {
    @SerializedName("type")
    @Expose
    String type;
    @SerializedName("content")
    @Expose
    String content;
    @SerializedName("answered")
    @Expose
    boolean answered;
    /**
     * No arg constructor
     */
    public Question() {
        type = "No type";
        content = "No content";
    }

    /**
     * Used in takeQuiz() to determine if a question has already been answered
     * @returntrue if answered
     */
    public boolean isAnswered() {
        return answered;
    }
    /**
     * Used in takeQuiz() to change the 'status' of a question: answered or not
     */
    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    /**
     * Read the properties of the question
     */
    public void readKeyboard() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the content:");
        boolean correct = false;
        while(!correct) {
            String cont = scan.nextLine();
            if(cont.equals("")) {
                System.out.println("Content cannot be empty: enter content again.");
            } else {
               this.content = cont;
               correct = true;
            }
        }
    }

    /**
     * Function overwritten in every sub-class, responsible for getting the correct answer to a question
     * @return an empty string
     */
    public String getAnswer() {
        return "";
    }

    /**
     * This funtion is used to display the content of the question
     * @return
     */
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
