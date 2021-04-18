package with_module;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String id;
    private List<Question> questionsEng;
    private List<Question> questionsPl;


    public Bank() {
        questionsEng = new ArrayList<>();
        questionsPl = new ArrayList<>();
    }
    public Bank(String id) {
        this.id = id;
        questionsEng = new ArrayList<>();
        questionsPl = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Add a new question
     * @param newQuestion
     */
    public void addEngBank(Question newQuestion) {
        if(newQuestion!=null) {
            questionsEng.add(newQuestion);
        }
    }
    public void addPlBank(Question newQuestion) {
        if(newQuestion!=null) {
            questionsPl.add(newQuestion);
        }
    }


    @Override
    public boolean equals(Object obj) {
        Bank other = (Bank) obj;
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
        //return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "\n\t\twith_module.Bank{" +
                "\n\t\tid='" + id + '\'' +
                ",\n\t\t questionsEng=" + questionsEng +
                ",\n\t\t questionsPl=" + questionsPl +
                "\n\t";
    }
}
