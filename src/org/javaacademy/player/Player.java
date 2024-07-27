package org.javaacademy.player;

import org.javaacademy.wonder_field.Alphabet;

import java.util.Scanner;

public class Player {
    private static final String LETTER = "б";
    private static final String WORD = "c";

    private final String name;
    private final String city;

    public Player(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public PlayerAnswer move(Scanner scanner) {
        System.out.println("Ход игрока " + this.name + ", " + this.city);
        System.out.println("Если хотите букву нажмите 'б' и enter,"
                + " если хотите слово нажмите 'c' и enter");
        String choice;

        while (true) {
            choice = scanner.next();

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
            letter = scanner.next().toUpperCase();
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
        String word = scanner.next().toUpperCase();
        System.out.println("Игрок " + name + ": слово - " + word);
        return word;
    }
}