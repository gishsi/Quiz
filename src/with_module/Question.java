package with_module;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Question {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "with_module.Question{" +
                "content='" + content + '\'' +
                '}';
    }
}
