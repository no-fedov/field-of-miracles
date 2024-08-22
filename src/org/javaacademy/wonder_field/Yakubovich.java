package org.javaacademy.wonder_field;

import org.javaacademy.player.AnswerType;
import org.javaacademy.player.Player;
import org.javaacademy.player.PlayerAnswer;
import org.javaacademy.wonder_field.cylinder.Cylinder;
import org.javaacademy.wonder_field.tableau.LetterException;
import org.javaacademy.wonder_field.tableau.Tableau;

import java.util.Scanner;

public final class Yakubovich {
    private static Yakubovich YAKUBOVICH;
    private static final String LEFT = "л";
    private static final String RIGHT = "п";

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

    public boolean checkAnswer(PlayerAnswer playerAnswer, Tableau tableau, Question question) throws LetterException {
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

    public boolean suggestSuperGame(Scanner scanner) {
        return false;
    }

    public void declareSection() {
        System.out.println("Якубович: " + Cylinder.getInstance().getPosition().getDescription());
    }

    public void declareBox(Player player, Box[] box, Scanner scanner) {
        System.out.println(player.getName() + ", Вы отгадали 3 буквы подряд. Выберите левую или правую шкатулку");
        System.out.println("Введите 'л' - если хотите левую, введите 'п' - если хотите правую.");
        String choice;

        while (true) {
            choice = scanner.nextLine();

            if (choice.equals(LEFT) || choice.equals(RIGHT)) {
                break;
            } else {
                System.out.println("Некорректное значение, введите 'л' или 'п'.");
            }
        }

        if (choice.equals(LEFT)) {
            openBox(player, box[0]);
        } else {
            openBox(player, box[1]);
        }
    }

    private void openBox(Player player, Box box) {
        if (box.getMoney() > 0) {
            System.out.println("Вам сегодня невероятно везет, вы угадали шкатулку, заберите ваши деньги!");
            player.setMoney(box.getMoney());
        } else {
            System.out.println("Не хочется Вас огорчать, шкатулка пустая");
        }
    }
}