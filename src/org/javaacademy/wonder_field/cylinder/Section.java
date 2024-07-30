package org.javaacademy.wonder_field.cylinder;

public enum Section {
    SECTION_100(100, "100 очков на барабане!"),
    SECTION_200(200, "200 очков на барабане!"),
    SECTION_300(300, "300 очков на барабане!"),
    SECTION_400(400, "400 очков на барабане!"),
    SECTION_500(500, "500 очков на барабане!"),
    SECTION_600(600, "600 очков на барабане!"),
    SECTION_700(700, "700 очков на барабане!"),
    SECTION_800(800, "800 очков на барабане!"),
    SECTION_900(900, "900 очков на барабане!"),
    SECTION_1000(1000, "1000 очков на барабане!"),
    SECTION_1100(1100, "1100 очков на барабане!"),
    SECTION_1200(1200, "1200 очков на барабане!"),
    SKIP(null, "Не повезло, вы пропускаете ход"),
    X_2(null, "Ваш рейтинг увеличивается в 2 раза!");

    private final Integer value;
    private final String description;

    Section(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}