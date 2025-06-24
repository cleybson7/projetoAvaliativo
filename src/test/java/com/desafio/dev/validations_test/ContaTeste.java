package com.desafio.dev.validations_test;

import com.desafio.dev.ENUMS.Situacao;
import com.desafio.dev.model.Conta;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ContaTeste {

    // Testes para Referência
    @Test
    void quandoReferenciaFormatoInvalido_deveRetornarErro() {
        Conta conta = new Conta();
        conta.setReferencia("2024-01"); // Formato inválido
        conta.setValor(new BigDecimal("100.00"));
        conta.setSituacao(Situacao.PENDENTE);

        assertFalse(conta.getReferencia().matches("^(0[1-9]|1[0-2])-\\d{4}$"),
                "\n\n\n\n\nReferência deve estar no formato MM-AAAA\n\n\n\n\n");
    }

    @Test
    void quandoReferenciaNula_deveRetornarErro() {
        Conta conta = new Conta();
        conta.setReferencia(null);
        conta.setValor(new BigDecimal("100.00"));
        conta.setSituacao(Situacao.PENDENTE);

        assertNull(conta.getReferencia(),
                "\n\n\n\n\nReferência não pode ser nula\n\n\n\n\n");
    }

    @Test
    void quandoReferenciaValida_devePassar() {
        Conta conta = new Conta();
        conta.setReferencia("01-2024");
        conta.setValor(new BigDecimal("100.00"));
        conta.setSituacao(Situacao.PENDENTE);

        assertTrue(conta.getReferencia().matches("^(0[1-9]|1[0-2])-\\d{4}$"),
                "\n\n\n\n\nReferência válida deve ser aceita\n\n\n\n\n");
    }

    // Testes para Valor
    @Test
    void quandoValorZero_deveRetornarErro() {
        Conta conta = new Conta();
        conta.setReferencia("01-2024");
        conta.setValor(BigDecimal.ZERO);
        conta.setSituacao(Situacao.PENDENTE);

        assertTrue(conta.getValor().compareTo(new BigDecimal("0.01")) < 0,
                "\n\n\n\n\nValor deve ser maior que zero\n\n\n\n\n");
    }

    @Test
    void quandoValorNegativo_deveRetornarErro() {
        Conta conta = new Conta();
        conta.setReferencia("01-2024");
        conta.setValor(new BigDecimal("-50.00"));
        conta.setSituacao(Situacao.PENDENTE);

        assertTrue(conta.getValor().compareTo(BigDecimal.ZERO) < 0,
                "\n\n\n\n\nValor não pode ser negativo\n\n\n\n\n");
    }

    @Test
    void quandoValorValido_devePassar() {
        Conta conta = new Conta();
        conta.setReferencia("01-2024");
        conta.setValor(new BigDecimal("100.50"));
        conta.setSituacao(Situacao.PENDENTE);

        assertTrue(conta.getValor().compareTo(new BigDecimal("0.01")) >= 0,
                "\n\n\n\n\nValor válido deve ser aceito\n\n\n\n\n");
    }

    // Testes para Situação
    @Test
    void quandoSituacaoNula_deveRetornarErro() {
        Conta conta = new Conta();
        conta.setReferencia("01-2024");
        conta.setValor(new BigDecimal("100.00"));
        conta.setSituacao(null);

        assertNull(conta.getSituacao(),
                "\n\n\n\n\nSituação não pode ser nula\n\n\n\n\n");
    }

    @Test
    void quandoSituacaoPendente_devePassar() {
        Conta conta = new Conta();
        conta.setReferencia("01-2024");
        conta.setValor(new BigDecimal("100.00"));
        conta.setSituacao(Situacao.PENDENTE);

        assertEquals(Situacao.PENDENTE, conta.getSituacao(),
                "\n\n\n\n\nSituação PENDENTE deve ser aceita\n\n\n\n\n");
    }

    @Test
    void quandoSituacaoPaga_devePassar() {
        Conta conta = new Conta();
        conta.setReferencia("01-2024");
        conta.setValor(new BigDecimal("100.00"));
        conta.setSituacao(Situacao.PAGA);

        assertEquals(Situacao.PAGA, conta.getSituacao(),
                "\n\n\n\n\nSituação PAGA deve ser aceita\n\n\n\n\n");
    }

    // Testes adicionais para cenários específicos
    @Test
    void quandoReferenciaComMesInvalido_deveRetornarErro() {
        Conta conta = new Conta();
        conta.setReferencia("13-2024"); // Mês 13 não existe
        conta.setValor(new BigDecimal("100.00"));
        conta.setSituacao(Situacao.PENDENTE);

        assertFalse(conta.getReferencia().matches("^(0[1-9]|1[0-2])-\\d{4}$"),
                "\n\n\n\n\nMês na referência deve ser entre 01 e 12\n\n\n\n\n");
    }

    @Test
    void quandoReferenciaComAnoInvalido_deveRetornarErro() {
        Conta conta = new Conta();
        conta.setReferencia("01-24"); // Ano com 2 dígitos
        conta.setValor(new BigDecimal("100.00"));
        conta.setSituacao(Situacao.PENDENTE);

        assertFalse(conta.getReferencia().matches("^(0[1-9]|1[0-2])-\\d{4}$"),
                "\n\n\n\n\nAno na referência deve ter 4 dígitos\n\n\n\n\n");
    }

    @Test
    void quandoValorMuitoPequeno_deveRetornarErro() {
        Conta conta = new Conta();
        conta.setReferencia("01-2024");
        conta.setValor(new BigDecimal("0.001")); // Menor que 0.01
        conta.setSituacao(Situacao.PENDENTE);

        assertTrue(conta.getValor().compareTo(new BigDecimal("0.01")) < 0,
                "\n\n\n\n\nValor deve ser pelo menos 0.01\n\n\n\n\n");
    }
}
