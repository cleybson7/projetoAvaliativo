package com.desafio.dev.service.interfaces;

import com.desafio.dev.dtos.cliente.ClienteRequestDTO;
import com.desafio.dev.dtos.cliente.ClienteResponseDTO;
import com.desafio.dev.exceptions.ClienteAlreadyExistsException;
import com.desafio.dev.exceptions.ClienteNotFoundException;

import java.util.List;

/**
 * Interface que define as operações de serviço disponíveis para a entidade Cliente.
 * 
 * Esta interface estabelece o contrato para as operações básicas de CRUD
 * (Create, Read, Update, Delete) relacionadas aos clientes do sistema.
 */
public interface ClienteService {
    /**
     * Salva um novo cliente no sistema.
     * 
     * @param clienteRequestDTO DTO contendo os dados do cliente a ser criado
     * @return DTO com os dados do cliente criado, incluindo seu ID
     * @throws ClienteAlreadyExistsException se já existir um cliente com o CPF informado
     */
    ClienteResponseDTO save(ClienteRequestDTO clienteRequestDTO);
    /**
     * Retorna uma lista com todos os clientes cadastrados no sistema.
     * 
     * @return Lista de DTOs contendo os dados de todos os clientes
     */
    List<ClienteResponseDTO> findAll();
    /**
     * Atualiza os dados de um cliente existente.
     * 
     * @param id ID do cliente a ser atualizado
     * @param clienteRequestDTO DTO contendo os novos dados do cliente
     * @return DTO com os dados atualizados do cliente
     * @throws ClienteNotFoundException se o cliente não for encontrado
     */
    ClienteResponseDTO update(Long id, ClienteRequestDTO clienteRequestDTO);
    /**
     * Remove um cliente do sistema.
     * 
     * @param id ID do cliente a ser removido
     * @throws ClienteNotFoundException se o cliente não for encontrado
     */
    void delete(Long id);
}
