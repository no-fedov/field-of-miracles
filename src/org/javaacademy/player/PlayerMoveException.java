package org.javaacademy.player;

public class PlayerMoveException extends Exception {
    private static final String MESSAGE = "Игрок пропускает ход, на барабане 0 очков";
    public PlayerMoveException() {
        super(MESSAGE);
    }
}
