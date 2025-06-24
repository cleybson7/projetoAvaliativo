package com.desafio.dev.dtos.cliente;

/**
 * Record que representa os dados necessários para criar ou atualizar um cliente no sistema.
 *
 * Este DTO (Data Transfer Object) é utilizado para receber os dados do cliente
 * através das requisições HTTP, garantindo que apenas os campos necessários
 * sejam expostos na API.
 */
public record ClienteRequestDTO(
        /** Nome completo do cliente */
        String nome,
        
        /** CPF do cliente no formato xxx.xxx.xxx-xx */
        String cpf,
        
        /** Telefone do cliente no formato (xx)xxxxx-xxxx ou (xx)xxxx-xxxx */
        String telefone,
        
        /** Endereço de e-mail do cliente */
        String email
) {}
