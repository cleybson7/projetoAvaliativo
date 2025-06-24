package com.desafio.dev.repository;

import com.desafio.dev.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações de persistência da entidade Cliente.
 * 
 * Esta interface estende JpaRepository para fornecer operações básicas de CRUD
 * e adiciona métodos personalizados para consultas específicas relacionadas
 * a clientes.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    /**
     * Busca um cliente pelo seu CPF.
     * 
     * @param cpf CPF do cliente a ser buscado
     * @return Optional contendo o cliente se encontrado, ou vazio se não existir
     */
    Optional<Cliente> findByCpf(String cpf);
}
