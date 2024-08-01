package org.javaacademy.wonder_field;

import org.javaacademy.player.AnswerType;
import org.javaacademy.player.Player;
import org.javaacademy.player.PlayerAnswer;
import org.javaacademy.wonder_field.cylinder.Cylinder;
import org.javaacademy.wonder_field.tableau.LetterException;
import org.javaacademy.wonder_field.tableau.Tableau;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static final int ROUNDS = 4;
    private static final int PLAYERS = 3;
    private static final int GROUP_ROUNDS = 3;
    private static final int FINAL_ROUND_ID = 3;
    private static final int SLEEP_TIME = 5000;

    private final Tableau tableau = new Tableau();
    private final Yakubovich yakubovich = Yakubovich.getInstance();
    private final Cylinder cylinder = Cylinder.getInstance();

    private final Question[] questions = new Question[ROUNDS];
    private final Player[] winners = new Player[GROUP_ROUNDS];
    private Question superQuestion;

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
            players[i] = new Player(playerInfo[0], playerInfo[1]);
        }
        return players;
    }

    private boolean isWin() {
        return !this.tableau.isContainUnknownLetter();
    }

    private boolean spinCylinder(Player player, Cylinder cylinder) {
        boolean conditionForMove = player.spinCylinder(cylinder);
        yakubovich.declareSection();
        return conditionForMove;
    }

    private Box[] generateBox() {
        Random rnd = new Random();
        boolean condition = rnd.nextBoolean();
        return new Box[]{new Box(condition), new Box(!condition)};
    }

    private boolean playerMakeMove(Player player, Question question) {
        boolean hasException = false;
        int letterCounter = 0;
        while (!isWin()) {
            if (!hasException) {
                if (!spinCylinder(player, cylinder)) {
                    return false;
                }
            }
            PlayerAnswer playerAnswer = player.move(SCANNER);
            try {
                boolean isRight = yakubovich.checkAnswer(playerAnswer, this.tableau, question);
                if (isRight) {
                    if (playerAnswer.getType() == AnswerType.LETTER) {
                        tableau.showTableau();
                        hasException = false;
                        letterCounter++;
                        if (letterCounter % 3 == 0) {
                            yakubovich.declareBox(player, generateBox(), SCANNER);
                        }
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }
            } catch (LetterException ex) {
                System.out.println(ex.getMessage());
                hasException = true;
            }
        }
        return true;
    }

    private void playRound(Player[] players, int round) {
        Question question = questions[round - 1];

        this.tableau.initTableau(question);
        this.yakubovich.invitePlayers(players, round);
        this.yakubovich.askPlayers(question);
        this.tableau.showTableau();

        while (true) {
            for (Player player : players) {
                while (playerMakeMove(player, question)) {
                    if (isWin()) {
                        winners[round - 1] = player;
                        yakubovich.announceWinner(player, false);
                        return;
                    } else {
                        this.tableau.showTableau();
                    }
                }
            }
        }
    }

    private void playGroupRounds() {
        for (int i = 1; i <= FINAL_ROUND_ID; i++) {
            Player[] players = generatePlayers();
            playRound(players, i);
        }
    }

    private void playFinalRound() {
        Question question = questions[ROUNDS - 1];

        this.tableau.initTableau(question);
        this.yakubovich.invitePlayers(winners, ROUNDS);
        this.yakubovich.askPlayers(question);
        this.tableau.showTableau();

        while (true) {
            for (Player player : winners) {
                while (playerMakeMove(player, question)) {
                    if (isWin()) {
                        yakubovich.announceWinner(player, true);
                        return;
                    } else {
                        this.tableau.showTableau();
                    }
                }
            }
        }
    }

    public void run() throws InterruptedException {
        init();
        yakubovich.startShow();
        playGroupRounds();
        playFinalRound();
        SCANNER.close();
        yakubovich.endShow();
    }
}