package com.desafio.dev.exceptions;

public class SituacaoCanceladaNotAvailableException extends RuntimeException {
    public SituacaoCanceladaNotAvailableException() {
        super("A sitaucao 'CANCELADA' não pode ser usada ao criar uma nova conta");
    }
}
