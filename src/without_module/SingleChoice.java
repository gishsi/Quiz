package without_module;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SingleChoice extends Question{
    private List<String> possibleAnswers;
    private String correctAnswer;

    /**
     * No argument constructor
     */
    public SingleChoice() {
        this("SingleChoice", "No content", "No answer");
    }
    /**
     * Constructor that allows us to make a new Single Choice question
     * @param content - Question
     * @param correctAnswer - the answer
     * @param type  - type of the question for this specific subclass
     */
    public SingleChoice(String type, String content, String correctAnswer) {
        possibleAnswers = new ArrayList<>();
        this.type = "SingleChoice";
        this.content = content;
        this.correctAnswer = correctAnswer;
    }

    public void addPossibleAnswer() {
        Scanner scan = new Scanner(System.in);
        // needs to be (0, 10>
        // needs to be a string
        String posAns = "";
        String nextAns = "";
        do {
            System.out.println("Enter a possible answer: ");
            posAns = scan.nextLine();
            if(!posAns.equals("")) {
                possibleAnswers.add(posAns);
            }
            System.out.println("Another possible answer (Y / N)");
            nextAns = scan.nextLine().toUpperCase();
        } while(!nextAns.equals("N"));
    }

    /**
     * A new readKeyboard for the Single Choice type
     */
    public void readKeyboard(){
        Scanner scan = new Scanner(System.in);
        super.readKeyboard();
        // here i should read possible answers
        addPossibleAnswer();
        System.out.println("Enter the correct answer");
        // I guess this try could be omitted? Could not find an exception
        try {
            this.correctAnswer = scan.nextLine();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", correct answer to that question: " + correctAnswer;
    }
}
