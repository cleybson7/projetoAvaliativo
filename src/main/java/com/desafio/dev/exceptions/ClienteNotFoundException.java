package com.desafio.dev.exceptions;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException() {
        super("Cliente não encontrado");
    }
}
