package org.javaacademy.player;

import org.javaacademy.wonder_field.cylinder.Cylinder;
import org.javaacademy.wonder_field.cylinder.Section;
import org.javaacademy.wonder_field.supergame.Prize;
import org.javaacademy.wonder_field.supergame.SuperPrize;
import org.javaacademy.wonder_field.tableau.Alphabet;

import java.util.Scanner;

public class Player {
    private static final String LETTER = "б";
    private static final String WORD = "с";

    private final String name;
    private final String city;
    private int rating;
    private int money;
    private final Prize[] prizes = new Prize[Prize.values().length];
    private int prizeCounter;
    private SuperPrize superPrize;

    public Player(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public SuperPrize getSuperPrize() {
        return superPrize;
    }

    public void setSuperPrize(SuperPrize superPrize) {
        this.superPrize = superPrize;
    }

    public int getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void reduceRating(int prizePrice) {
        this.rating -= prizePrice;
    }

    public Prize[] getPrizes() {
        return prizes;
    }

    public void choosePrize(Prize prize) {
        if (prizeCounter == Prize.values().length) {
            throw new RuntimeException("Больше призов не забрать");
        }
        prizes[prizeCounter] = prize;
        prizeCounter++;
    }

    public PlayerAnswer move(Scanner scanner, Cylinder cylinder) throws PlayerMoveException {
        if (!this.spinCylinder(cylinder)) {
            throw new PlayerMoveException();
        }

        System.out.println("Ход игрока " + this.name + ", " + this.city);
        System.out.println("Если хотите букву нажмите 'б' и enter,"
                + " если хотите слово нажмите 'c' и enter");
        String choice;

        while (true) {
            choice = scanner.nextLine();
            if (choice.equals(LETTER) || choice.equals(WORD)) {
                break;
            } else {
                System.out.println("Некорректное значение, введите 'б' или 'с'");
            }
        }

        if (choice.equals(LETTER)) {
            return new PlayerAnswer(this.sayLatter(scanner).toUpperCase(), AnswerType.LETTER);
        }
        return new PlayerAnswer(this.sayWord(scanner).toUpperCase(), AnswerType.WORD);
    }

    private String sayLatter(Scanner scanner) {
        String letter = "";
        boolean flag = false;

        while (!flag) {
            letter = scanner.nextLine().toUpperCase();
            if (letter.length() == 1) {
                for (char letters : Alphabet.ALPHABET) {
                    if (letters == letter.charAt(0)) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                System.out.println("Ошибка! это не русская буква, введите русскую букву");
            }
        }

        System.out.println("Игрок " + name + ": буква - " + letter);
        return letter;
    }

    private String sayWord(Scanner scanner) {
        String word = scanner.nextLine().toUpperCase();
        System.out.println("Игрок " + name + ": слово - " + word);
        return word;
    }

    private boolean spinCylinder(Cylinder cylinder) {
        Section section = cylinder.spin();
        switch (section) {
            case X_2:
                this.rating *= 2;
                return true;
            case SKIP:
                return false;
            default:
                this.rating += section.getValue();
        }
        return true;
    }
}