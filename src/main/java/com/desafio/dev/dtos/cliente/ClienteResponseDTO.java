package com.desafio.dev.dtos.cliente;

/**
 * Record que representa a resposta da API com os dados de um cliente.
 *
 * Este DTO (Data Transfer Object) é utilizado para retornar os dados do cliente
 * nas respostas das requisições HTTP. Ele inclui todos os campos relevantes
 * do cliente, incluindo seu identificador único.
 *
 * @see com.desafio.dev.model.Cliente
 */
public record ClienteResponseDTO(
        /** Identificador único do cliente no banco de dados */
        Long id,
        
        /** Nome completo do cliente */
        String nome,
        
        /** CPF do cliente no formato xxx.xxx.xxx-xx */
        String cpf,
        
        /** Telefone do cliente no formato (xx)xxxxx-xxxx ou (xx)xxxx-xxxx */
        String telefone,
        
        /** Endereço de e-mail do cliente */
        String email
) {}
