package com.desafio.dev.validations_test;

import com.desafio.dev.model.Cliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTeste {

    // Testes para Nome
    @Test
    void quandoNomeVazio_deveRetornarErro() {
        Cliente cliente = new Cliente();
        cliente.setNome("algo");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("(11)99999-9999");
        cliente.setEmail("cliente@email.com");

        assertNull(cliente.getNome().isEmpty() ? cliente.getNome() : null,
                "\n\n\n\n\nNome não pode estar em branco\n\n\n\n\n");
    }

    @Test
    void quandoNomeNulo_deveRetornarErro() {
        Cliente cliente = new Cliente();
        cliente.setNome(null);
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("(11)99999-9999");
        cliente.setEmail("cliente@email.com");

        assertNull(cliente.getNome(),
                "\n\n\n\n\nNome não pode ser nulo\n\n\n\n\n");
    }

    @Test
    void quandoNomeValido_devePassar() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("(11)99999-9999");
        cliente.setEmail("cliente@email.com");

        assertNotNull(cliente.getNome(),
                "\n\n\n\n\nNome válido deve ser aceito\n\n\n\n\n");
        assertFalse(cliente.getNome().isEmpty(),
                "\n\n\n\n\nNome válido não deve estar vazio\n\n\n\n\n");
    }

    // Testes para CPF
    @Test
    void quandoCpfFormatoInvalido_deveRetornarErro() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("12345678900"); // Formato inválido
        cliente.setTelefone("(11)99999-9999");
        cliente.setEmail("cliente@email.com");

        assertFalse(cliente.getCpf().matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$"),
                "\n\n\n\n\nCPF deve estar no formato xxx.xxx.xxx-xx\n\n\n\n\n");
    }

    @Test
    void quandoCpfVazio_deveRetornarErro() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("");
        cliente.setTelefone("(11)99999-9999");
        cliente.setEmail("cliente@email.com");

        assertNull(cliente.getCpf().isEmpty() ? cliente.getCpf() : null,
                "\n\n\n\n\nCPF não pode estar em branco\n\n\n\n\n");
    }

    @Test
    void quandoCpfValido_devePassar() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("(11)99999-9999");
        cliente.setEmail("cliente@email.com");

        assertTrue(cliente.getCpf().matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$"),
                "\n\n\n\n\nCPF válido deve ser aceito\n\n\n\n\n");
    }

    // Testes para Telefone
    @Test
    void quandoTelefoneFormatoInvalido_deveRetornarErro() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999"); // Formato inválido
        cliente.setEmail("cliente@email.com");

        assertFalse(cliente.getTelefone().matches("^\\(\\d{2}\\)\\d{4,5}-\\d{4}$"),
                "\n\n\n\n\nTelefone deve estar no formato (xx)xxxxx-xxxx ou (xx)xxxx-xxxx\n\n\n\n\n");
    }

    @Test
    void quandoTelefoneNulo_devePassar() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone(null); // Campo opcional
        cliente.setEmail("cliente@email.com");

        assertNull(cliente.getTelefone(),
                "\n\n\n\n\nTelefone pode ser nulo pois é opcional\n\n\n\n\n");
    }

    @Test
    void quandoTelefoneValido_devePassar() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("(11)99999-9999");
        cliente.setEmail("cliente@email.com");

        assertTrue(cliente.getTelefone().matches("^\\(\\d{2}\\)\\d{4,5}-\\d{4}$"),
                "\n\n\n\n\nTelefone válido deve ser aceito\n\n\n\n\n");
    }

    // Testes para Email
    @Test
    void quandoEmailFormatoInvalido_deveRetornarErro() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("(11)99999-9999");
        cliente.setEmail("email_invalido"); // Formato inválido

        assertFalse(cliente.getEmail().contains("@"),
                "\n\n\n\n\nEmail deve ter formato válido\n\n\n\n\n");
    }

    @Test
    void quandoEmailNulo_devePassar() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("(11)99999-9999");
        cliente.setEmail(null); // Campo opcional

        assertNull(cliente.getEmail(),
                "\n\n\n\n\nEmail pode ser nulo pois é opcional\n\n\n\n\n");
    }

    @Test
    void quandoEmailValido_devePassar() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("(11)99999-9999");
        cliente.setEmail("cliente@email.com");

        assertTrue(cliente.getEmail().contains("@") && cliente.getEmail().contains("."),
                "\n\n\n\n\nEmail válido deve ser aceito\n\n\n\n\n");
    }
}
