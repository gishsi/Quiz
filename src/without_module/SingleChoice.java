package without_module;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SingleChoice extends Question{
    private List<String> possibleAnswers;
    private String correctAnswer;
    private int answersCount = 0;
    private static final int MAX_ANSWERS = 10;


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

    /**
     * Adding possible answers
     */
    public void addPossibleAnswer() {
        Scanner scan = new Scanner(System.in);
        String posAns = "";
        String nextAns = "";

        do {
            System.out.println("Enter a possible answer (up to 10): ");
            posAns = scan.nextLine();
            if(!posAns.equals("")) {
                possibleAnswers.add(posAns);
            }
            answersCount++;
            System.out.println("Another possible answer (Y / N)");
            nextAns = scan.nextLine().toUpperCase();
        } while(!nextAns.equals("N") && answersCount < MAX_ANSWERS);
    }

    /**
     * A new readKeyboard for the Single Choice type
     */
    @Override
    public void readKeyboard(){
        Scanner scan = new Scanner(System.in);
        super.readKeyboard();
        addPossibleAnswer();
        System.out.println("=========================================");
        System.out.println("Enter the correct answer");
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
     *  Return the correct answer
     * @return correctAnswer
     */
    public String getAnswer() {
        return this.correctAnswer;
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

    /**
     *  Display the possible answers and the content of the question
     */
    @Override
    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.content);
        System.out.println(this.content);
        for (String pos: possibleAnswers) {
            sb.append("\n").append(pos);
        }
        return  sb.toString();
    }

    public void setPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    @Override
    public String toString() {
        return super.toString() + ", correct answer to that question: " + correctAnswer;
    }

}
