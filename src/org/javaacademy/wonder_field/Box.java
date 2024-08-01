package org.javaacademy.wonder_field;

public class Box {
    private final int money;

    public Box(boolean condition) {
        if (condition) {
            money = 5000;
        } else {
            money = 0;
        }
    }

    public int getMoney() {
        return money;
    }
}
