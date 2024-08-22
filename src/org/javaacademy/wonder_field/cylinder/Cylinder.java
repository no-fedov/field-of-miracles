package org.javaacademy.wonder_field.cylinder;

import java.util.Random;

public class Cylinder {
    private static Section position;
    private static Cylinder CYLINDER;
    private final Section[] values = Section.values();
    private final Random random = new Random();

    private Cylinder() {
    }

    public Section getPosition() {
        return position;
    }

    public static Cylinder getInstance() {
        if (CYLINDER == null) {
            CYLINDER = new Cylinder();
        }
        return CYLINDER;
    }

    public Section spin() {
        int position = random.nextInt(values.length + 1);
        Cylinder.position = values[position];
        return Cylinder.position;
    }
}