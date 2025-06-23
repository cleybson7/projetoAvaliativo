package com.desafio.dev.dtos.cliente;

public record ClienteRequestDTO(
        String nome,
        String cpf,
        String telefone,
        String email
) {}
