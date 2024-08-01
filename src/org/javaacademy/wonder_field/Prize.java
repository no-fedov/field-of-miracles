package org.javaacademy.wonder_field;

public enum Prize {
    LAPTOP("Ноутбук", 4000),
    ELECTRIC_GENERATOR("Электрогенератор", 3500),
    TV("Телевизор", 3000),
    AIR_CONDITIONER("Кондиционер", 2000),
    ELECTRIC_SCOOTER("Электросамокат", 1400),
    SYNTHESIZER("Синтезатор", 1200),
    SMARTPHONE("Смартфон", 900),
    LAWN_MOWER("Газонокосилка", 700),
    IRON("Утюг", 600),
    FISHING_SET("Набор для рыбалки", 400),
    TEA_SET("Чайный сервиз", 300);

    private final String description;
    private final int price;

    Prize(String description, int price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}