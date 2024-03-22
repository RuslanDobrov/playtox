package ru.playtox.exception;

import java.util.UUID;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(UUID id) {
        super("Insufficient funds in account " + id.toString());
    }
}
