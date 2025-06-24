package com.desafio.dev.ENUMS;

/**
 * Enum que representa os possíveis estados de uma conta no sistema.
 *
 * Este enum é utilizado para controlar o ciclo de vida de uma conta:
 * - PENDENTE: Indica que a conta foi criada mas ainda não foi paga
 * - PAGA: Indica que a conta foi devidamente quitada
 * - CANCELADA: Indica que a conta foi cancelada e não está mais ativa
 *
 * @see com.desafio.dev.model.Conta
 */
public enum Situacao {
    /** Representa uma conta que ainda não foi paga */
    PENDENTE,
    
    /** Representa uma conta que já foi quitada */
    PAGA,
    
    /** Representa uma conta que foi cancelada */
    CANCELADA
}
