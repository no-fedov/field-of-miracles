package org.javaacademy.player;

import java.util.Scanner;

public class Player {
    private final String name;
    private final String city;
    private final Scanner scanner;

    public Player(String name, String city, Scanner scanner) {
        this.name = name;
        this.city = city;
        this.scanner = scanner;
    }

    public String getName() {
        return name;
    }

    public String sayLatter() {

        String letter = null;

        int counter = 0;
        do {
            if (counter != 0) {
                System.out.println("Ошибка! это не русская буква, введите русскую букву");
            }
            letter = scanner.next().toLowerCase();
            counter++;
        } while (letter.length() != 1
                || letter.charAt(0) < 'а'
                || letter.charAt(0) > 'я');

        letter = letter.toUpperCase();

        System.out.println("Игрок " + name + ": буква - " + letter);

        return letter;
    }

    public String sayWord() {
        String word = scanner.next();
        System.out.println("Игрок " + name + ": слово - " + word);
        return word;
    }

    public PlayerAnswer move() {
        System.out.println("Ход игрока " + name + ", " + city);

        System.out.println("Если хотите букву нажмите 'б' и enter," +
                " если хотите слово нажмите 'c' и enter");

        String choice;

        int counter = 0;

        while (true) {

            if (counter != 0) {
                System.out.println("Некорректное значение, введите 'б' или 'с'");
            }

            choice = scanner.next();

            if (choice.equals("б") || choice.equals("с")) {
                break;
            }

            counter++;
        }

        String answer;

        if (choice.equals("б")) {
            answer = this.sayLatter();
            return new PlayerAnswer(answer.toUpperCase(), PlayerAnswer.AnswerType.LETTER);
        } else {
            answer = this.sayWord();
            return new PlayerAnswer(answer.toUpperCase(), PlayerAnswer.AnswerType.WORD);
        }
    }
}