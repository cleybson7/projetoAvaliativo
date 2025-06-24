package com.desafio.dev.dtos.conta;

import com.desafio.dev.ENUMS.Situacao;

import java.math.BigDecimal;

/**
 * Record que representa a resposta da API com os dados de uma conta.
 *
 * Este DTO (Data Transfer Object) é utilizado para retornar os dados da conta
 * nas respostas das requisições HTTP. Ele inclui todos os campos relevantes
 * da conta, incluindo seu identificador único, referência, valor e situação atual.
 *
 * @see com.desafio.dev.model.Conta
 * @see com.desafio.dev.ENUMS.Situacao
 */
public record ContaResponseDTO(
    /** Identificador único da conta no banco de dados */
    Long id,
    
    /** Referência da conta no formato MM-AAAA (mês-ano) */
    String referencia,
    
    /** Valor monetário da conta */
    BigDecimal valor,
    
    /** Situação atual da conta (PENDENTE, PAGA ou CANCELADA) */
    Situacao situacao
) {}
