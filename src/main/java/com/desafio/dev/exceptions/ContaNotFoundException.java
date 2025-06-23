package com.desafio.dev.exceptions;

public class ContaNotFoundException extends RuntimeException {
    public ContaNotFoundException() {
        super("Conta não encontrada");
    }
}
