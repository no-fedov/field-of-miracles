package org.javaacademy.wonder_field;

public class Tableau {
    private String rightAnswer;
    private char[] lettersOnTableau;

    public void initTableau(Question question) {
        rightAnswer = question.getAnswer();
        lettersOnTableau = new char[rightAnswer.length()];

        for (int i = 0; i < lettersOnTableau.length; i++) {
            lettersOnTableau[i] = '_';
        }
    }

    public void showTableau() {
        checkAttributes();
        String tableauForShow = " ";

        for (char letter : lettersOnTableau) {
            tableauForShow = tableauForShow + letter + " ";
        }

        System.out.println(tableauForShow);
    }

    public boolean openLetter(char letter) {
        checkAttributes();
        boolean flag = false;

        for (int i = 0; i < lettersOnTableau.length; i++) {
            if (letter == rightAnswer.charAt(i)) {
                lettersOnTableau[i] = letter;
                flag = true;
            }
        }

        return flag;
    }

    public void showAnswer() {
        String whitespace = " ";
        String answer = "";

        for (int i = 0; i < rightAnswer.length(); i++) {
            lettersOnTableau[i] = rightAnswer.charAt(i);
            answer += whitespace + rightAnswer.charAt(i);
        }

        System.out.println(answer);
    }

    public boolean isContainUnknownLetter() {
        for (char onTableau : lettersOnTableau) {
            if (onTableau == '_') {
                return true;
            }
        }
        return false;
    }

    private void checkAttributes() {
        if (rightAnswer == null || lettersOnTableau == null) {
            throw new RuntimeException("Пустое табло");
        }
    }
}