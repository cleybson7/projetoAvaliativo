package com.desafio.dev.exceptions;

public class ClienteAlreadyExistsException extends RuntimeException {
    public ClienteAlreadyExistsException() {
        super("Cliente já cadastrado");
    }
}
