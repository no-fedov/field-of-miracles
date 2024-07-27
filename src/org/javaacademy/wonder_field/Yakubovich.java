package org.javaacademy.wonder_field;

import org.javaacademy.player.AnswerType;
import org.javaacademy.player.Player;
import org.javaacademy.player.PlayerAnswer;

public final class Yakubovich {

    // прочиттать как правильно делать синглтон
    public static final Yakubovich YAKUBOVICH = new Yakubovich();

    private Yakubovich() {
    }

    public void startShow() {
        System.out.println("Якубович: Здравствуйте, уважаемые дамы и господа! Пятница! "
                + "В эфире капитал-шоу «Поле чудес»!");
    }

    public void endShow() {
        System.out.println("Якубович: Мы прощаемся с вами ровно на одну неделю! "
                + "Здоровья вам, до встречи!");
    }

    public void invitePlayers(Player[] players, int round) {
        String playerNames = "";

        // последнюю запятую убрать
        for (Player player : players) {
            playerNames += player.getName() + ", ";
        }

        if (round == 4) {
            System.out.println("Якубович: приглашаю победителей групповых этапов: " + playerNames);
        }
        System.out.println("Якубович: приглашаю " + round + "-ю тройку игроков: " + playerNames);
    }

    public void askPlayers(Question question) {
        System.out.println("Якубович: Внимание вопрос!");
        System.out.println(question.getQuestion());
    }

    public void announceWinner(Player player, boolean isFinalRound) {
        if (isFinalRound) {
            System.out.println("Якубович: И перед нами победитель Капитал шоу поле чудес! Это "
                    + player.getName() + " из " + player.getCity());
            return;
        }
        System.out.println("Якубович: Молодец! " + player.getName() + " из "
                + player.getCity() + " проходит в финал!");
    }

    public boolean checkAnswer(PlayerAnswer playerAnswer, Tableau tableau, Question question) {

        if (playerAnswer.getType() == AnswerType.WORD) {
            if (question.getAnswer().equals(playerAnswer.getAnswer())) {
                System.out.println("Якубович: " + playerAnswer.getAnswer() + "! Абсолютно верно!");
                System.out.println("__________________________________");
                tableau.showAnswer();
                return true;
            }
            System.out.println("Якубович: Неверно! Следующий игрок!");
            return false;
        }

        if (tableau.openLetter(playerAnswer.getAnswer().charAt(0))) {
            System.out.println("Якубович: Есть такая буква, откройте ее!");
            return true;
        }

        System.out.println("Якубович: Нет такой буквы! Следующий игрок, крутите барабан!");
        System.out.println("__________________________________");
        return false;
    }
}