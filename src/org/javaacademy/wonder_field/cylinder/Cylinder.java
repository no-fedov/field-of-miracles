package org.javaacademy.wonder_field.cylinder;

public class Cylinder {
    private static Section position;
    private static Cylinder CYLINDER;
    private final Section[] values = Section.values();

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
        int position = (int) (values.length * Math.random());
        Cylinder.position = values[position];
        return Cylinder.position;
    }
}