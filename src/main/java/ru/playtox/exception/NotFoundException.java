package ru.playtox.exception;

import java.util.UUID;

public class NotFoundException extends Exception {
    public NotFoundException(UUID id) {
        super("Account " + id.toString() + " not found");
    }
}
