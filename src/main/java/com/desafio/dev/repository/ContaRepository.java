package com.desafio.dev.repository;

import com.desafio.dev.ENUMS.Situacao;
import com.desafio.dev.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório para operações de persistência da entidade Conta.
 * 
 * Esta interface estende JpaRepository para fornecer operações básicas de CRUD
 * e adiciona métodos personalizados para consultas específicas relacionadas
 * a contas, incluindo filtros por cliente e situação.
 */
public interface ContaRepository extends JpaRepository<Conta, Long> {
    /**
     * Busca todas as contas de um cliente específico que estejam em determinadas situações.
     * 
     * @param id ID do cliente para buscar as contas
     * @param situacoes Lista de situações para filtrar as contas
     * @return Lista de contas que atendem aos critérios de busca
     */
    List<Conta> findAllByClienteIdAndSituacaoIn(Long id, List<Situacao> situacoes);
}
