package org.javaacademy.wonder_field;

import org.javaacademy.player.Player;
import org.javaacademy.player.PlayerAnswer;

public final class Yakubovich {

    public static final Yakubovich YAKUBOVICH = new Yakubovich();

    private Yakubovich() {
    }

    public void startShow() {
        System.out.println("Якубович: Здравствуйте, уважаемые дамы и господа! Пятница! " +
                "В эфире капитал-шоу «Поле чудес»!");
    }

    public void endShow() {
        System.out.println("Якубович: Мы прощаемся с вами ровно на одну неделю! " +
                "Здоровья вам, до встречи!");
    }

    public Player[] invitePlayers(Player[] players, int round) {

        String playerNames = "";

        // последнюю запятую убрать
        for (Player player : players) {
            playerNames += player.getName() + ", ";
        }

        if (round == 4) {
            System.out.println("Якубович: приглашаю победителей групповых этапов: " + playerNames);

        }

        System.out.println("Якубович: приглашаю " + round + "-ю тройку игроков: " + playerNames);
        return players;
    }

    public void askPlayers(String question) {
        System.out.println("Якубович: Внимание вопрос!");
        System.out.println(question);
    }

    public void announceWinner(String name, String city, boolean isFinalRound) {
        if (isFinalRound) {
            System.out.println("Якубович: И перед нами победитель Капитал шоу поле чудес! Это " + name + " из " + city);
            return;
        }
        System.out.println("Якубович: Молодец! " + name + " из " + city + " проходит в финал!");
    }

    public boolean checkAnswer(PlayerAnswer playerAnswer, Tableau tableau) {
        boolean flag = false;

        String answer = tableau.getRightAnswer();

        if (playerAnswer.getType() == PlayerAnswer.AnswerType.WORD) {
            if (tableau.getRightAnswer().equals(answer)) {
                System.out.println("Якубович: "+ answer +"! Абсолютно верно!");
                flag = true;
            } else {
                System.out.println("Якубович: Неверно! Следующий игрок!");
            }
            System.out.println("__________________________________");
            return flag;
        }

        if (tableau.openLetter(answer.charAt(0))) {
            System.out.println("Якубович: Есть такая буква, откройте ее!");
            flag = true;
        } else {
            System.out.println("Якубович: Нет такой буквы! Следующий игрок, крутите барабан!");
        }
        System.out.println("__________________________________");
        return flag;
    }
}