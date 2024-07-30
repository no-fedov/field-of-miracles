package org.javaacademy.wonder_field.cylinder;

public class Cylinder {
    public static Section position;
    private final Section[] values = Section.values();
    private static Cylinder CYLINDER;

    private Cylinder() {
    }

    public static Cylinder getInstance() {
        if (CYLINDER == null) {
            CYLINDER = new Cylinder();
        }
        return CYLINDER;
    }

    public Section spin() {
        int position = (int) (values.length * Math.random());
        return values[position];
    }
}