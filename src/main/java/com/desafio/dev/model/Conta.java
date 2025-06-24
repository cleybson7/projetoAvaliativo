package com.desafio.dev.model;

import com.desafio.dev.ENUMS.Situacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidade que representa uma conta no sistema.
 * 
 * Esta classe mapeia os dados de uma conta para o banco de dados e define
 * as validações necessárias para garantir a integridade dos dados. Uma conta
 * está sempre associada a um cliente e possui uma situação específica
 * (PENDENTE, PAGA ou CANCELADA).
 */
@Entity
@Table(name = "conta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conta {

    /**
     * Identificador único da conta.
     * Gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Referência temporal da conta no formato MM-AAAA.
     * Campo obrigatório que deve seguir o padrão mês-ano (ex: 01-2024).
     */
    @NotNull(message = "A situação nao pode ser nula")
    @Pattern(regexp = "^(0[1-9]|1[0-2])-\\d{4}$", message = "A referência deve estar no formato MM-AAAA válido")
    private String referencia;

    /**
     * Valor monetário da conta.
     * Campo obrigatório que deve ser maior que zero.
     */
    @NotNull(message = "A situação nao pode ser nula")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    private BigDecimal valor;

    /**
     * Situação atual da conta (PENDENTE, PAGA ou CANCELADA).
     * Campo obrigatório que utiliza um enum para garantir valores válidos.
     */
    @NotNull(message = "A situação nao pode ser nula")
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    /**
     * Cliente ao qual esta conta está associada.
     * Relacionamento muitos-para-um com a entidade Cliente.
     */
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
