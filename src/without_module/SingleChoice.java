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

        // Check if there is an answer that matches the possible answers
        boolean answerExists = false;
        while(!answerExists) {
            String answer = scan.nextLine();
            if(checkAnswer(answer)) {
                this.correctAnswer = answer;
                answerExists = true;
            } else {
                System.out.println("Enter a correct answer that matches one of the possible answers:");
                answerExists = false;
            }
        }
    }

    /**
     * Checks if the correct answer already existed in the possible answers array
     * @return true if it does and allows the creation of the question
     */
    private boolean checkAnswer(String answer) {
        for (String posAns: possibleAnswers) {
            if(posAns.equals(answer)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return super.toString() + ", correct answer to that question: " + correctAnswer;
    }
}
