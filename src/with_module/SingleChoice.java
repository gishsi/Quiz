package with_module;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SingleChoice extends  Question{
    private String correctAnswer;
    private List<String> possibleAnswers;

    public SingleChoice(){
        possibleAnswers = new ArrayList<>();
    }

    /**
     * A method to add possible answers
     */
    public void addPossibleAnswers(String posAnswer) {
        if(posAnswer != null || posAnswer != "") {
            possibleAnswers.add(posAnswer.toLowerCase(Locale.ROOT));
        }
    }

    /**
     * Add the correct answer
     * @param answer - this is the correct answer given by the teacher.
     */
    public void addCorrectAnswer(String answer) {
        if(answer!=null || answer !="") {
            correctAnswer = answer.toLowerCase(Locale.ROOT);
        }
    }


}
