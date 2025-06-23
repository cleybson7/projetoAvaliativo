package com.desafio.dev.exceptions;

public class ClienteEmptyException extends RuntimeException {
    public ClienteEmptyException() {
        super("Nenhum cliente cadastrado");
    }
}
