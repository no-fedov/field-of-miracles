package org.javaacademy.wonder_field;

public class Question {
    private final String question;
    private final String answer;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer.toUpperCase();
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }
}