package jud28;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Score {
    @SerializedName("score")
    @Expose
    private int score;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("unanswered")
    @Expose
    private int unanswered;

    public Score (String time, int score, int unanswered) {
        this.time = time;
        this.score = score;
        this.unanswered = unanswered;
    }
}
