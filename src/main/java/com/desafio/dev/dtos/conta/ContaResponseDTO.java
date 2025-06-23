package com.desafio.dev.dtos.conta;

import com.desafio.dev.ENUMS.Situacao;

import java.math.BigDecimal;

public record ContaResponseDTO(
    Long id,
    String referencia,
    BigDecimal valor,
    Situacao situacao
) {}
