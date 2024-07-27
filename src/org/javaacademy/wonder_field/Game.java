package org.javaacademy.wonder_field;

import org.javaacademy.player.AnswerType;
import org.javaacademy.player.Player;
import org.javaacademy.player.PlayerAnswer;

import java.util.Scanner;

public class Game {
    private static final Scanner SCANNER = new Scanner(System.in);

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
            assert playerInfo.length > 2;
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
        this.tableau.initTableau(questions[round - 1]);
        this.yakubovich.invitePlayers(players, round);
        this.yakubovich.askPlayers(questions[round - 1]);
        this.tableau.showTableau();

        while (this.tableau.isContainUnknownLetter()) {
            for (Player player : players) {
                boolean isRightAnswer = true;

                while (isRightAnswer) {
                    isRightAnswer = yakubovich.checkAnswer(player.move(SCANNER), this.tableau
                            , this.questions[round - 1]);

                    if (isRightAnswer && tableau.isContainUnknownLetter()) {
                        this.tableau.showTableau();
                    }

                    if (isWinner()) {
                        if (round <= FINAL_ROUND_ID) {
                            winners[round - 1] = player;
                        }
                        yakubovich.announceWinner(player, false);
                        return;
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
        this.tableau.initTableau(questions[ROUNDS - 1]);
        this.yakubovich.invitePlayers(winners, ROUNDS);
        this.yakubovich.askPlayers(questions[ROUNDS - 1]);
        this.tableau.showTableau();

        while (this.tableau.isContainUnknownLetter()) {
            for (Player player : winners) {
                boolean isRightAnswer = true;

                while (isRightAnswer) {
                    isRightAnswer = yakubovich.checkAnswer(player.move(SCANNER), this.tableau
                            , this.questions[ROUNDS - 1]);

                    if (isRightAnswer && tableau.isContainUnknownLetter()) {
                        this.tableau.showTableau();
                    }

                    if (isWinner()) {
                        yakubovich.announceWinner(player, true);
                        return;
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