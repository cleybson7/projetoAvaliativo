package com.desafio.dev.repository;

import com.desafio.dev.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    Optional<Cliente> findByCpf(String cpf);
    //usados para teste
    boolean existsByCpf(String s);
}
