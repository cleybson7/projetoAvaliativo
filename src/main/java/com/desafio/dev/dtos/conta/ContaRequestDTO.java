package com.desafio.dev.dtos.conta;

import com.desafio.dev.ENUMS.Situacao;

import java.math.BigDecimal;

/**
 * Record que representa os dados necessários para criar ou atualizar uma conta no sistema.
 *
 * Este DTO (Data Transfer Object) é utilizado para receber os dados da conta
 * através das requisições HTTP. O formato da referência deve seguir o padrão MM-AAAA
 * e a situação deve ser PENDENTE ou PAGA para novas contas.
 *
 * @see com.desafio.dev.model.Conta
 * @see com.desafio.dev.ENUMS.Situacao
 */
public record ContaRequestDTO(
    /** Referência da conta no formato MM-AAAA (mês-ano) */
    String referencia,
    
    /** Valor monetário da conta utilizando BigDecimal para precisão */
    BigDecimal valor,
    
    /** Situação atual da conta (PENDENTE ou PAGA para novas contas) */
    Situacao situacao
) {}
