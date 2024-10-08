package org.javaacademy.wonder_field;

import org.javaacademy.player.AnswerType;
import org.javaacademy.player.Player;
import org.javaacademy.player.PlayerAnswer;
import org.javaacademy.player.PlayerMoveException;
import org.javaacademy.wonder_field.cylinder.Cylinder;
import org.javaacademy.wonder_field.supergame.Prize;
import org.javaacademy.wonder_field.supergame.SuperPrize;
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
    private Player superWinner;
    private Prize[] prizes = Prize.values();

    private void init() throws InterruptedException {
        System.out.println("Запуск игры \"Поле Чудес\"");

        for (int i = 1; i <= ROUNDS; i++) {
            System.out.println("Введите вопрос #" + i);
            String questions = SCANNER.nextLine();

            System.out.println("Введите ответ вопрос #" + i);
            String answer = SCANNER.nextLine().toUpperCase();

            this.questions[i - 1] = new Question(questions, answer);
        }

        System.out.println("Введите супер вопрос:");
        String questions = SCANNER.nextLine();
        System.out.println("Введите ответ на супер вопрос:");
        String answer = SCANNER.nextLine().toUpperCase();
        this.superQuestion = new Question(questions, answer);

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

    private Box[] generateBox() {
        Random rnd = new Random();
        boolean condition = rnd.nextBoolean();
        return new Box[]{new Box(condition), new Box(!condition)};
    }

    /**
     * Игрок может делать ход, пока на барабане отличное от 0 количество очков.
     * если на барабане 0 очков, выбрасывается исключение PlayerMoveException
     */
    private void playerMakeMove(Player player, Question question) throws PlayerMoveException {
        int letterCounter = 0;

        while (!isWin()) {
            PlayerAnswer playerAnswer = player.move(SCANNER, cylinder);
            yakubovich.declareSection();
            boolean isRight = false;

            try {
                isRight = yakubovich.checkAnswer(playerAnswer, this.tableau, question);
            } catch (LetterException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (isRight) {
                if (playerAnswer.getType() == AnswerType.WORD) {
                    return;
                }
                tableau.showTableau();
                letterCounter++;
                if (letterCounter % 3 == 0) {
                    yakubovich.declareBox(player, generateBox(), SCANNER);
                }
            } else {
                return;
            }
        }
    }

    private void playRound(Player[] players, int round) {
        Question question = questions[round - 1];

        this.tableau.initTableau(question);
        this.yakubovich.invitePlayers(players, round);
        this.yakubovich.askPlayers(question);
        this.tableau.showTableau();

        while (true) {
            for (Player player : players) {
                try {
                    playerMakeMove(player, question);
                    if (isWin()) {
                        winners[round - 1] = player;
                        yakubovich.announceWinner(player, false);
                        return;
                    } else {
                        this.tableau.showTableau();
                    }
                } catch (PlayerMoveException e) {
                    yakubovich.declareSection();
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
                try {
                    playerMakeMove(player, question);
                    if (isWin()) {
                        yakubovich.announceWinner(player, true);
                        superWinner = player;
                        return;
                    } else {
                        this.tableau.showTableau();
                    }
                } catch (PlayerMoveException e) {
                    yakubovich.declareSection();
                }
            }
        }
    }

    private void playSuperGame() {
        superWinnerChoosePrizes();
        if (yakubovich.suggestSuperGame(SCANNER)) {
            winSuperPrize();
//            играем
        }
        printInfoSupperWinner();
    }

    private void superWinnerChoosePrizes() {
        printPrizeList();
        choosePrize();
    }

    private void winSuperPrize() {
        Random random = new Random();
        int numberSuperPrize = random.nextInt(SuperPrize.values().length);
        superWinner.setSuperPrize(SuperPrize.values()[numberSuperPrize]);
    }

    private void printPrizeList() {
        System.out.println("Список призов!!!");
        for (Prize prize : Prize.values()) {
            System.out.printf("№%s %s цена: %s\n", prize.ordinal() + 1, prize.getDescription(), prize.getPrice());
        }
    }

    private void choosePrize() {
        while (findMinimalPriceForPrize() <= superWinner.getRating()) {
            System.out.println("Выберите позицию");
            int number = SCANNER.nextInt() - 1;

            Prize prize = prizes[number];

            if (superWinner.getRating() - prize.getPrice() >= 0) {
                superWinner.reduceRating(prize.getPrice());
                superWinner.choosePrize(prize);
                System.out.printf("у вас осталось %s баллов\n", superWinner.getRating());
            } else {
                System.out.println("Не хватает баллов");
            }
        }
        System.out.println("У вас ни на что не хватает баллов баллов");
    }

    private int findMinimalPriceForPrize() {
        int minPrice = prizes[0].getPrice();
        for (Prize prize : prizes) {
            if (prize.getPrice() < minPrice) {
                minPrice = prize.getPrice();
            }
        }
        return minPrice;
    }

    private void printInfoSupperWinner() {
        System.out.println("Победитель выиграл :");
        System.out.println("Призы: ");
        for (Prize prize : superWinner.getPrizes()) {
            if (prize != null) {
                System.out.printf("%s\n", prize.getDescription());
            }
        }
        if (superWinner.getSuperPrize() != null) {
            System.out.printf("Супер приз: %s\n", superWinner.getSuperPrize().getDescription());
        }
        System.out.printf("Деньги: %s\n", superWinner.getMoney());
    }

    public void run() throws InterruptedException {
        init();
        yakubovich.startShow();
        playGroupRounds();
        playFinalRound();
        playSuperGame();
        SCANNER.close();
        yakubovich.endShow();
    }
}