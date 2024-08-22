package org.javaacademy.wonder_field.supergame;

public enum SuperPrize {
    AUTO("Автомобиль"),
    MOTO("Мотоцикл"),
    FRIDGE("Холодильник");

    private final String description;

    public String getDescription() {
        return description;
    }

    SuperPrize(String description) {
        this.description = description;
    }
}
