package org.javaacademy.wonder_field;

import org.javaacademy.player.AnswerType;
import org.javaacademy.player.Player;
import org.javaacademy.player.PlayerAnswer;

import java.util.Scanner;

public class Game {
    public static final Scanner SCANNER = new Scanner(System.in);

    private static final int PLAYERS = 3;
    private static final int ROUNDS = 4;
    private static final int GROUP_ROUNDS = 3;
    private static final int FINAL_ROUND_ID = 3;
    private static final int SLEEP_TIME = 5000;

    private final Tableau tableau = new Tableau();
    private final Yakubovich yakubovich = Yakubovich.YAKUBOVICH;

    private final Question[] questions = new Question[ROUNDS];
    private final Player[] winners = new Player[GROUP_ROUNDS];

    private void init() throws InterruptedException {
        System.out.println("Запуск игры \"Поле Чудес\"");

        for (int i = 1; i <= ROUNDS; i++) {
            System.out.println("Введите вопрос #" + i);
            String questions = SCANNER.nextLine();

            System.out.println("Введите ответ вопрос #" + i);
            String answer = SCANNER.nextLine().toUpperCase();

            this.questions[i - 1] = new Question(questions, answer);
        }

        System.out.println("Иницализация закончена, игра начнется через 5 секунд");
        Thread.sleep(SLEEP_TIME);
        System.out.println("\n".repeat(50));
    }

    private Player[] generatePlayers() {
        Player[] players = new Player[PLAYERS];

        for (int i = 0; i < PLAYERS; i++) {
            System.out.println("Игрок №" + (i + 1) + " представьтесь: имя,город.");
            String nameAndCity = SCANNER.nextLine();
            String[] playerInfo = nameAndCity.split(",");
            assert playerInfo.length == 2;
            players[i] = new Player(playerInfo[0], playerInfo[1]);
        }
        return players;
    }

    private String[] getPlayerNames(Player[] players) {
        String[] playerNames = new String[players.length];

        for (int i = 0; i < PLAYERS; i++) {
            playerNames[i] = players[i].getName();
        }

        return playerNames;
    }

    private boolean isWinner() {
        return !this.tableau.isContainUnknownLetter();
    }

    private boolean playerMakeMove(Player player, Question question) {
        boolean isRight;

        do {
            PlayerAnswer playerAnswer = player.move(SCANNER);
            isRight = yakubovich.checkAnswer(playerAnswer, this.tableau, question);

            if (isRight) {
                if (playerAnswer.getType() == AnswerType.LETTER) {
                    tableau.showTableau();
                } else {
                    return true;
                }
            } else {
                return false;
            }

        } while (tableau.isContainUnknownLetter());

        return isRight;
    }

    private void playRound(Player[] players, int round) {
        this.tableau.initTableau(questions[round]);
        yakubovich.askPlayers();
    }

    public void run() {

    }

    private void fakeInitQuestionsAndAnswers() throws InterruptedException {
        System.out.println("Запуск игры \"Поле Чудес\"");

        questions[0] = new Question("В Полотняной стране\n" +
                "По реке Простыне\n" +
                "Плывет пароход\n" +
                "То назад, то вперед,\n" +
                "А за ним такая гладь —\n" +
                "Ни морщинки не видать.", "УТЮГ");

        questions[1] = new Question("В брюшке — баня,\n" +
                "В носу — решето,\n" +
                "Нос — хоботок,\n" +
                "На голове — пупок,\n" +
                "Всего одна рука\n" +
                "Без пальчиков,\n" +
                "И та — на спине\n" +
                "Калачиком.", "ЧАЙНИК")

        questions[2] = new Question("Стоит дуб,\n" +
                "В нем двенадцать гнезд,\n" +
                "В каждом гнезде\n" +
                "По четыре яйца,\n" +
                "В каждом яйце\n" +
                "По семи цыпленков.", "ГОД");

        questions[3] = new Question("Вдруг из черной темноты\n" +
                "В небе выросли кусты.\n" +
                "А на них-то голубые,\n" +
                "Пунцовые, золотые\n" +
                "Распускаются цветы\n" +
                "Небывалой красоты.\n" +
                "И все улицы под ними\n" +
                "Тоже стали голубыми,\n" +
                "Пунцовыми, золотыми,\n" +
                "Разноцветными.", "САЛЮТ");

        System.out.println("Иницализация закончена, игра начнется через 5 секунд");
        Thread.sleep(SLEEP_TIME);
        System.out.println("\n".repeat(50));
    }
}