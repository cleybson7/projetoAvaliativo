package com.desafio.dev.exceptions;

public class ContaEmptyException extends RuntimeException {
    public ContaEmptyException() {
        super("Nenhuma Conta Encontrada");
    }
}
