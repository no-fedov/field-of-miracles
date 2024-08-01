package org.javaacademy.wonder_field;

public class Box {
    private static int MONEY = 5000;
    private int money;

    public Box(boolean condition) {
        if (condition) {
            money = MONEY;
        } else {
            money = 0;
        }
    }

    public int getMoney() {
        return money;
    }
}
