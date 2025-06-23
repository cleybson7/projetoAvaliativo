package com.desafio.dev.exceptions;

public class SituacaoNotAvailableException extends RuntimeException {
    public SituacaoNotAvailableException(String message) {
        super("A situação: " + message + " não está disponível ao criar uma conta");
    }
}
