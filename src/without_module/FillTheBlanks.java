package without_module;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FillTheBlanks extends Question{
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

    public List<String> getBlankWords() {
        return blankWords;
    }

    public void setBlankWords(List<String> blankWords) {
        this.blankWords = blankWords;
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
            if(!blank.equals("")) {
                blankWords.add(blank);
            }
            // this should not be here at all, the only check: while( blankWords.size() < blankWordsCount)
            System.out.println("Another possible answer (Y / N)");
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
}
