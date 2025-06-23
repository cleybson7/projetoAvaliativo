package com.desafio.dev.repository;

import com.desafio.dev.ENUMS.Situacao;
import com.desafio.dev.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findAllByClienteIdAndSituacaoIn(Long id, List<Situacao> situacoes);
}
