package com.desafio.dev.repository_test;

import com.desafio.dev.model.Cliente;
import com.desafio.dev.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class ClienteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void deveSalvarClienteComDadosValidos() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("(11)99999-9999");
        cliente.setEmail("joao@email.com");

        // When
        Cliente clienteSalvo = clienteRepository.save(cliente);

        // Then
        assertThat(clienteSalvo.getId()).isNotNull();
        assertThat(clienteSalvo.getNome()).isEqualTo("João Silva");
        assertThat(clienteSalvo.getCpf()).isEqualTo("123.456.789-00");
    }

    @Test
    public void deveBuscarClientePorId() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNome("Maria Santos");
        cliente.setCpf("987.654.321-00");
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente);

        // When
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(clienteSalvo.getId());

        // Then
        assertThat(clienteEncontrado).isPresent();
        assertThat(clienteEncontrado.get().getNome()).isEqualTo("Maria Santos");
    }

    @Test
    public void deveBuscarClientePorCpf() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNome("Pedro Oliveira");
        cliente.setCpf("111.222.333-44");
        entityManager.persistAndFlush(cliente);

        // When
        Optional<Cliente> clienteEncontrado = clienteRepository.findByCpf("111.222.333-44");

        // Then
        assertThat(clienteEncontrado).isPresent();
        assertThat(clienteEncontrado.get().getNome()).isEqualTo("Pedro Oliveira");
    }

    @Test
    public void naoDeveEncontrarClienteComCpfInexistente() {
        // When
        Optional<Cliente> clienteEncontrado = clienteRepository.findByCpf("000.000.000-00");

        // Then
        assertThat(clienteEncontrado).isNotPresent();
    }

    @Test
    public void deveVerificarSeExisteClienteComCpf() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNome("Ana Costa");
        cliente.setCpf("555.666.777-88");
        entityManager.persistAndFlush(cliente);

        // When & Then
        assertThat(clienteRepository.existsByCpf("555.666.777-88")).isTrue();
        assertThat(clienteRepository.existsByCpf("999.999.999-99")).isFalse();
    }

    @Test
    public void deveAtualizarCliente() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNome("Carlos Silva");
        cliente.setCpf("444.555.666-77");
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente);

        // When
        clienteSalvo.setNome("Carlos Santos");
        clienteSalvo.setEmail("carlos@email.com");
        Cliente clienteAtualizado = clienteRepository.save(clienteSalvo);

        // Then
        assertThat(clienteAtualizado.getNome()).isEqualTo("Carlos Santos");
        assertThat(clienteAtualizado.getEmail()).isEqualTo("carlos@email.com");
    }

    @Test
    public void deveDeletarCliente() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNome("Roberto Lima");
        cliente.setCpf("333.444.555-66");
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente);

        // When
        clienteRepository.deleteById(clienteSalvo.getId());

        // Then
        Optional<Cliente> clienteDeletado = clienteRepository.findById(clienteSalvo.getId());
        assertThat(clienteDeletado).isNotPresent();
    }

    @Test
    public void deveListarTodosOsClientes() {
        // Given
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Cliente Um");
        cliente1.setCpf("111.111.111-11");

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Cliente Dois");
        cliente2.setCpf("222.222.222-22");

        entityManager.persistAndFlush(cliente1);
        entityManager.persistAndFlush(cliente2);

        // When
        Iterable<Cliente> clientes = clienteRepository.findAll();

        // Then
        assertThat(clientes).hasSize(2);
    }

    @Test
    public void deveContarTotalDeClientes() {
        // Given
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Cliente Teste 1");
        cliente1.setCpf("100.100.100-10");

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Cliente Teste 2");
        cliente2.setCpf("200.200.200-20");

        entityManager.persistAndFlush(cliente1);
        entityManager.persistAndFlush(cliente2);

        // When
        long total = clienteRepository.count();

        // Then
        assertThat(total).isEqualTo(2);
    }

    @Test
    public void deveSalvarClienteSemTelefoneEEmail() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Simples");
        cliente.setCpf("777.888.999-00");

        // When
        Cliente clienteSalvo = clienteRepository.save(cliente);

        // Then
        assertThat(clienteSalvo.getId()).isNotNull();
        assertThat(clienteSalvo.getTelefone()).isNull();
        assertThat(clienteSalvo.getEmail()).isNull();
    }
}
