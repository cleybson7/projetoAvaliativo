package com.desafio.dev.dtos.cliente;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String email
) {}
