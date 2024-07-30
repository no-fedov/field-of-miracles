package org.javaacademy.wonder_field;

import org.javaacademy.player.AnswerType;
import org.javaacademy.player.Player;
import org.javaacademy.player.PlayerAnswer;
import org.javaacademy.wonder_field.cylinder.Cylinder;
import org.javaacademy.wonder_field.tableau.Tableau;

public final class Yakubovich {
    private static Yakubovich YAKUBOVICH;

    private Yakubovich() {
    }

    public static Yakubovich getInstance() {
        if (YAKUBOVICH == null) {
            YAKUBOVICH = new Yakubovich();
        }
        return YAKUBOVICH;
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

        for (Player player : players) {
            playerNames += player.getName() + ", ";
        }
        playerNames = playerNames.substring(0, playerNames.length() - 2);

        if (round == Game.ROUNDS) {
            System.out.println("Якубович: приглашаю победителей групповых этапов: " + playerNames);
            return;
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
                    + player.getName() + " из " + player.getCity()
                    + ". Он набрал " + player.getRating() + " очков.");
            return;
        }
        System.out.println("Якубович: Молодец! " + player.getName() + " из "
                + player.getCity() + " проходит в финал! "
                + "Он набрал " + player.getRating() + " очков.");
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

    public void declareSection() {
        System.out.println("Якубович: " + Cylinder.getInstance().getPosition().getDescription());
    }
}