package org.javaacademy.wonder_field;

import org.javaacademy.player.Player;
import org.javaacademy.player.PlayerAnswer;

import java.util.Scanner;

public class Game {
    private static final Scanner scanner = new Scanner(System.in);

    private static final int PLAYERS = 3;
    private static final int ROUNDS = 4;
    private static final int GROUP_ROUNDS = 3;
    private static final int FINAL_ROUND_ID = 3;

    private final String[] questions = new String[ROUNDS];
    private final String[] answers = new String[ROUNDS];

    private final Tableau tableau = new Tableau();
    public final Yakubovich yakubovich = Yakubovich.YAKUBOVICH;

    private final Player[] winners = new Player[ROUNDS];

    private void init() throws InterruptedException {
        System.out.println("Запуск игры \"Поле Чудес\"");

        for (int i = 1; i <= ROUNDS; i++) {

            System.out.println("Введите вопрос #" + i);
            questions[i - 1] = scanner.nextLine();

            System.out.println("Введите ответ вопрос #" + i);
            answers[i - 1] = scanner.nextLine().toUpperCase();

        }

        System.out.println("Иницализация закончена, игра начнется через 5 секунд");
        Thread.sleep(5000);
        System.out.println("\n".repeat(50));
    }

    private void fakeInitQuestionsAndAnswers() throws InterruptedException {
        System.out.println("Запуск игры \"Поле Чудес\"");

        questions[0] = "В Полотняной стране\n" +
                "По реке Простыне\n" +
                "Плывет пароход\n" +
                "То назад, то вперед,\n" +
                "А за ним такая гладь —\n" +
                "Ни морщинки не видать.";
        answers[0] = "УТЮГ";

        questions[1] = "В брюшке — баня,\n" +
                "В носу — решето,\n" +
                "Нос — хоботок,\n" +
                "На голове — пупок,\n" +
                "Всего одна рука\n" +
                "Без пальчиков,\n" +
                "И та — на спине\n" +
                "Калачиком.";
        answers[1] = "ЧАЙНИК";

        questions[2] = "Стоит дуб,\n" +
                "В нем двенадцать гнезд,\n" +
                "В каждом гнезде\n" +
                "По четыре яйца,\n" +
                "В каждом яйце\n" +
                "По семи цыпленков.";
        answers[2] = "ГОД";

        questions[3] = "Вдруг из черной темноты\n" +
                "В небе выросли кусты.\n" +
                "А на них-то голубые,\n" +
                "Пунцовые, золотые\n" +
                "Распускаются цветы\n" +
                "Небывалой красоты.\n" +
                "И все улицы под ними\n" +
                "Тоже стали голубыми,\n" +
                "Пунцовыми, золотыми,\n" +
                "Разноцветными.";
        answers[3] = "САЛЮТ";

        System.out.println("Иницализация закончена, игра начнется через 5 секунд");
        Thread.sleep(5000);
        System.out.println("\n".repeat(50));
    }

    private Player[] generatePlayers() {
        Player[] players = new Player[PLAYERS];

        for (int i = 0; i < PLAYERS; i++) {
            System.out.println("Игрок №" + (i + 1) + " представьтесь: имя,город.");
            String nameAndCity = scanner.nextLine();
            String[] playerInfo = nameAndCity.split(",");
            assert playerInfo.length == 2;
            players[i] = new Player(playerInfo[0], playerInfo[1], scanner);
        }
        return players;
    }

    private String[] getPlayerNames(Player[] players) {
        String[] playerNames = new String[players.length];

        int counter = 0;

        for (Player player : players) {
            playerNames[counter] = player.getName();
            counter++;
        }
        return playerNames;
    }

    private boolean isWinner() {
        return !this.tableau.isContainUnknownLetter();
    }

    private boolean playerMakeMove(Player player, String question) {
        System.out.println(question);

        boolean isRight;

        do {
            PlayerAnswer playerAnswer = player.move();

            isRight = yakubovich.checkAnswer(playerAnswer, tableau);

            if (isRight) {

                if (playerAnswer.getType() == PlayerAnswer.AnswerType.LETTER) {
                    tableau.showTableau();
                } else {
                    tableau.showAnswer();
                    return true;
                }

            } else {
                return false;
            }

        } while (!tableau.isContainUnknownLetter());

        return isRight;
    }

    private void playRound(Player[] players, int round) {

    }

    public void run() {

    }
}