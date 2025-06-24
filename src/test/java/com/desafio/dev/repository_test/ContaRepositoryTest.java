package com.desafio.dev.repository_test;

import com.desafio.dev.ENUMS.Situacao;
import com.desafio.dev.model.Cliente;
import com.desafio.dev.model.Conta;
import com.desafio.dev.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class ContaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContaRepository contaRepository;

    @Test
    public void deveSalvarContaComDadosValidos() {
        // Given
        Cliente cliente = criarClienteBasico();
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente);

        Conta conta = new Conta();
        conta.setReferencia("01-2024");
        conta.setValor(new BigDecimal("150.50"));
        conta.setSituacao(Situacao.PENDENTE);
        conta.setCliente(clienteSalvo);

        // When
        Conta contaSalva = contaRepository.save(conta);

        // Then
        assertThat(contaSalva.getId()).isNotNull();
        assertThat(contaSalva.getReferencia()).isEqualTo("01-2024");
        assertThat(contaSalva.getValor()).isEqualByComparingTo(new BigDecimal("150.50"));
        assertThat(contaSalva.getSituacao()).isEqualTo(Situacao.PENDENTE);
    }

    @Test
    public void deveBuscarContaPorId() {
        // Given
        Cliente cliente = criarClienteBasico();
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente);

        Conta conta = new Conta();
        conta.setReferencia("02-2024");
        conta.setValor(new BigDecimal("200.00"));
        conta.setSituacao(Situacao.PAGA);
        conta.setCliente(clienteSalvo);
        Conta contaSalva = entityManager.persistAndFlush(conta);

        // When
        Optional<Conta> contaEncontrada = contaRepository.findById(contaSalva.getId());

        // Then
        assertThat(contaEncontrada).isPresent();
        assertThat(contaEncontrada.get().getReferencia()).isEqualTo("02-2024");
    }

    @Test
    public void deveBuscarContasPorSituacao() {
        // Given
        Cliente cliente = criarClienteBasico();
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente);

        Conta conta1 = criarConta("03-2024", "100.00", Situacao.PENDENTE, clienteSalvo);
        Conta conta2 = criarConta("04-2024", "250.00", Situacao.PENDENTE, clienteSalvo);
        Conta conta3 = criarConta("05-2024", "300.00", Situacao.PAGA, clienteSalvo);

        entityManager.persistAndFlush(conta1);
        entityManager.persistAndFlush(conta2);
        entityManager.persistAndFlush(conta3);

        // When
        List<Conta> contasPendentes = contaRepository.findBySituacao(Situacao.PENDENTE);

        // Then
        assertThat(contasPendentes).hasSize(2);
        assertThat(contasPendentes).allMatch(conta -> conta.getSituacao() == Situacao.PENDENTE);
    }

    @Test
    public void deveBuscarContasPorCliente() {
        // Given
        Cliente cliente1 = criarClienteBasico();
        cliente1.setCpf("111.111.111-11");
        Cliente cliente1Salvo = entityManager.persistAndFlush(cliente1);

        Cliente cliente2 = criarClienteBasico();
        cliente2.setCpf("222.222.222-22");
        cliente2.setNome("Outro Cliente");
        Cliente cliente2Salvo = entityManager.persistAndFlush(cliente2);

        Conta conta1 = criarConta("06-2024", "100.00", Situacao.PENDENTE, cliente1Salvo);
        Conta conta2 = criarConta("07-2024", "200.00", Situacao.PAGA, cliente1Salvo);
        Conta conta3 = criarConta("08-2024", "300.00", Situacao.CANCELADA, cliente2Salvo);

        entityManager.persistAndFlush(conta1);
        entityManager.persistAndFlush(conta2);
        entityManager.persistAndFlush(conta3);

        // When
        List<Conta> contasDoCliente1 = contaRepository.findByCliente(cliente1Salvo);

        // Then
        assertThat(contasDoCliente1).hasSize(2);
        assertThat(contasDoCliente1).allMatch(conta -> conta.getCliente().getId().equals(cliente1Salvo.getId()));
    }

    @Test
    public void deveBuscarContasPorClienteESituacao() {
        // Given
        Cliente cliente = criarClienteBasico();
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente);

        Conta conta1 = criarConta("09-2024", "100.00", Situacao.PENDENTE, clienteSalvo);
        Conta conta2 = criarConta("10-2024", "200.00", Situacao.PAGA, clienteSalvo);
        Conta conta3 = criarConta("11-2024", "300.00", Situacao.PENDENTE, clienteSalvo);

        entityManager.persistAndFlush(conta1);
        entityManager.persistAndFlush(conta2);
        entityManager.persistAndFlush(conta3);

        // When
        List<Conta> contasPendentesDoCliente = contaRepository.findByClienteAndSituacao(clienteSalvo, Situacao.PENDENTE);

        // Then
        assertThat(contasPendentesDoCliente).hasSize(2);
        assertThat(contasPendentesDoCliente).allMatch(conta ->
                conta.getCliente().getId().equals(clienteSalvo.getId()) &&
                        conta.getSituacao() == Situacao.PENDENTE
        );
    }

    @Test
    public void deveBuscarContasPorReferencia() {
        // Given
        Cliente cliente = criarClienteBasico();
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente);

        Conta conta1 = criarConta("12-2024", "100.00", Situacao.PENDENTE, clienteSalvo);
        Conta conta2 = criarConta("12-2024", "200.00", Situacao.PAGA, clienteSalvo);
        Conta conta3 = criarConta("01-2025", "300.00", Situacao.PENDENTE, clienteSalvo);

        entityManager.persistAndFlush(conta1);
        entityManager.persistAndFlush(conta2);
        entityManager.persistAndFlush(conta3);

        // When
        List<Conta> contasReferencia12_2024 = contaRepository.findByReferencia("12-2024");

        // Then
        assertThat(contasReferencia12_2024).hasSize(2);
        assertThat(contasReferencia12_2024).allMatch(conta -> conta.getReferencia().equals("12-2024"));
    }

    @Test
    public void deveAtualizarSituacaoDaConta() {
        // Given
        Cliente cliente = criarClienteBasico();
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente);

        Conta conta = criarConta("01-2025", "500.00", Situacao.PENDENTE, clienteSalvo);
        Conta contaSalva = entityManager.persistAndFlush(conta);

        // When
        contaSalva.setSituacao(Situacao.PAGA);
        Conta contaAtualizada = contaRepository.save(contaSalva);

        // Then
        assertThat(contaAtualizada.getSituacao()).isEqualTo(Situacao.PAGA);
    }

    @Test
    public void deveDeletarConta() {
        // Given
        Cliente cliente = criarClienteBasico();
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente);

        Conta conta = criarConta("02-2025", "400.00", Situacao.CANCELADA, clienteSalvo);
        Conta contaSalva = entityManager.persistAndFlush(conta);

        // When
        contaRepository.deleteById(contaSalva.getId());

        // Then
        Optional<Conta> contaDeletada = contaRepository.findById(contaSalva.getId());
        assertThat(contaDeletada).isNotPresent();
    }

    @Test
    public void deveListarTodasAsContas() {
        // Given
        Cliente cliente = criarClienteBasico();
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente);

        Conta conta1 = criarConta("03-2025", "100.00", Situacao.PENDENTE, clienteSalvo);
        Conta conta2 = criarConta("04-2025", "200.00", Situacao.PAGA, clienteSalvo);

        entityManager.persistAndFlush(conta1);
        entityManager.persistAndFlush(conta2);

        // When
        Iterable<Conta> contas = contaRepository.findAll();

        // Then
        assertThat(contas).hasSize(2);
    }

    @Test
    public void deveContarTotalDeContas() {
        // Given
        Cliente cliente = criarClienteBasico();
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente);

        Conta conta1 = criarConta("05-2025", "150.00", Situacao.PENDENTE, clienteSalvo);
        Conta conta2 = criarConta("06-2025", "250.00", Situacao.PAGA, clienteSalvo);
        Conta conta3 = criarConta("07-2025", "350.00", Situacao.CANCELADA, clienteSalvo);

        entityManager.persistAndFlush(conta1);
        entityManager.persistAndFlush(conta2);
        entityManager.persistAndFlush(conta3);

        // When
        long total = contaRepository.count();

        // Then
        assertThat(total).isEqualTo(3);
    }

    // Métodos auxiliares
    private Cliente criarClienteBasico() {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Teste");
        cliente.setCpf("123.456.789-00");
        return cliente;
    }

    private Conta criarConta(String referencia, String valor, Situacao situacao, Cliente cliente) {
        Conta conta = new Conta();
        conta.setReferencia(referencia);
        conta.setValor(new BigDecimal(valor));
        conta.setSituacao(situacao);
        conta.setCliente(cliente);
        return conta;
    }
}
