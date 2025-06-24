package com.desafio.dev.service.interfaces;

import com.desafio.dev.dtos.conta.ContaRequestDTO;
import com.desafio.dev.dtos.conta.ContaResponseDTO;

import java.util.List;

/**
 * Interface que define as operações de serviço disponíveis para a entidade Conta.
 * 
 * Esta interface estabelece o contrato para as operações básicas de CRUD
 * (Create, Read, Update, Delete) relacionadas às contas do sistema, sempre
 * considerando sua associação com um cliente.
 */
public interface ContaService {
    /**
     * Salva uma nova conta associada a um cliente específico.
     * 
     * @param id ID do cliente ao qual a conta será associada
     * @param contaRequestDTO DTO contendo os dados da conta a ser criada
     * @return DTO com os dados da conta criada, incluindo seu ID
     * @throws ClienteNotFoundException se o cliente não for encontrado
     * @throws SituacaoCanceladaNotAvailableException se tentar criar uma conta com situação CANCELADA
     * @throws SituacaoNotAvailableException se a situação informada não for válida para criação
     */
    ContaResponseDTO save(Long id, ContaRequestDTO contaRequestDTO);
    /**
     * Retorna uma lista com todas as contas não canceladas de um cliente específico.
     * 
     * @param id ID do cliente para buscar as contas
     * @return Lista de DTOs contendo os dados das contas do cliente
     * @throws ContaEmptyException se o cliente não possuir contas ativas
     */
    List<ContaResponseDTO> findAllByClienteId(Long id);
    /**
     * Atualiza os dados de uma conta existente.
     * 
     * @param id ID da conta a ser atualizada
     * @param contaRequestDTO DTO contendo os novos dados da conta
     * @return DTO com os dados atualizados da conta
     * @throws ContaNotFoundException se a conta não for encontrada
     */
    ContaResponseDTO update(Long id, ContaRequestDTO contaRequestDTO);
    /**
     * Marca uma conta como CANCELADA no sistema.
     * 
     * @param id ID da conta a ser cancelada
     * @throws ContaNotFoundException se a conta não for encontrada
     */
    void delete(Long id);
}
