package without_module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FillTheBlanks extends Question{
    @SerializedName("blankWords")
    @Expose
    private List<String> blankWords;
    /**
     * No argument constructor
     */
    public FillTheBlanks() {
        this("FillTheBlanks", "No content");
    }
    /**
     * Constructor that allows us to make a new Single Choice question
     * @param content - Question
     * @param type  - type of the question for this specific subclass
     */
    public FillTheBlanks(String type, String content) {
        this.type = "FillTheBlanks";
        this.content = content;
        blankWords = new ArrayList<>();
    }

    /**
     * Used to create a FillInTheBlanks question
     */
    @Override
    public void readKeyboard() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the text, indicate the blanks with an underscore character (_):");
        boolean properFormat = false;
        while (!properFormat) {
            content = scan.nextLine();
            if(!content.contains("_")) {
                System.out.println("Text must have some blanks.");
            } else {
                properFormat = true;
            }
        }
        this.addBlankWords();
    }

    /**
     * Set the blankWords
     * @param blankWords - the words to be entered
     */
    public void setBlankWords(List<String> blankWords) {
        this.blankWords = blankWords;
    }


    /**
     * This is an overridden version of display() from the Question class
     * @return the content of the question and the number of blank words inside it
     */
    @Override
    public String display() {
        return super.display() + "\nNumber of blank words: " + blankWords.size();
    }

    /**
     * Adding the blank words
     */
    public void addBlankWords() {
        Scanner scan = new Scanner(System.in);
        String blank = "";
        String nextAns = "";
        do {
            // user should enter as many blank words as there were in the text
            System.out.println("Enter a blank word: ");
            blank = scan.nextLine();
            if (blank.equals("")) {
                System.out.println("Blank cannot be empty");
            } else if (blank.contains(" ")){
                System.out.println("It must only be one word!");
            } else{
                blankWords.add(blank);
            }
            System.out.println("Another blank word? (Y / N)");
            nextAns = scan.nextLine().toUpperCase();
        } while(!nextAns.equals("N"));
    }

    /**
     *  Blank words separated by a space
     * @return the answer
     */
    public String getAnswer() {
        StringBuilder sb = new StringBuilder();
        for (String s: blankWords) {
            sb.append(s).append(" ");
        }
        return sb.toString().trim();
    }

    /**
     *  method to display information about a class
     * @return content and blank words
     */
    @Override
    public String toString() {
        return super.toString() +
                ", blankWords: " + blankWords;
    }
}
