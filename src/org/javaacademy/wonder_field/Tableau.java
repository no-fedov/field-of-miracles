package org.javaacademy.wonder_field;

public class Tableau {
    private String rightAnswer;
    private char[] lettersOnTableau;

    public String getRightAnswer() {
        return rightAnswer;
    }

    public char[] getLettersOnTableau() {
        return lettersOnTableau;
    }

    public void initTableau(String word) {
        rightAnswer = word.toUpperCase();
        lettersOnTableau = new char[word.length()];

        for (int i = 0; i < lettersOnTableau.length; i++) {
            lettersOnTableau[i] = '_';
        }
    }

    public void showTableau() {
        if (isEmptyAttributes()) {
            return;
        }

        String tableauForShow = " ";

        for (char letter : lettersOnTableau) {
            tableauForShow = tableauForShow + letter + " ";
        }

        System.out.println(tableauForShow);
    }

    public boolean openLetter(char letter) {
        if (isEmptyAttributes()) {
            return false;
        }

        boolean flag = false;

        for (int i = 0; i < lettersOnTableau.length; i++) {
            if (letter == rightAnswer.charAt(i)) {
                lettersOnTableau[i] = letter;
                flag = true;
            }
        }
        return true;
    }

    public void showAnswer() {
        String answer = " ";

        for (int i = 0; i < rightAnswer.length(); i++) {
            answer += rightAnswer.charAt(i);
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

    private boolean isEmptyAttributes() {
        return rightAnswer == null || lettersOnTableau == null;
    }
}
