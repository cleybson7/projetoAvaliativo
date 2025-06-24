package com.desafio.dev.repository;

import com.desafio.dev.ENUMS.Situacao;
import com.desafio.dev.model.Cliente;
import com.desafio.dev.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações de persistência da entidade Conta.
 * 
 * Esta interface estende JpaRepository para fornecer operações básicas de CRUD
 * e adiciona métodos personalizados para consultas específicas relacionadas
 * a contas, incluindo filtros por cliente e situação.
 */
@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findAllByClienteIdAndSituacaoIn(Long id, List<Situacao> situacoes);
    //Usados para teste
    List<Conta> findBySituacao(Situacao situacao);
    List<Conta> findByCliente(Cliente cliente);
    List<Conta> findByReferencia(String referencia);
    List<Conta> findByClienteAndSituacao(Cliente clienteSalvo, Situacao situacao);
}
