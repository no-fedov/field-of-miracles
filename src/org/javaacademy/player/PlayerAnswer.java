package org.javaacademy.player;

public class PlayerAnswer {
    private final String answer;
    private final AnswerType type;

    public PlayerAnswer(String answer, AnswerType type) {
        this.answer = answer;
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public AnswerType getType() {
        return type;
    }
}
