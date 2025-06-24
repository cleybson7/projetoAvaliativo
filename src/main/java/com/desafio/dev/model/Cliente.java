package com.desafio.dev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entidade que representa um cliente no sistema.
 * 
 * Esta classe mapeia os dados de um cliente para o banco de dados e define
 * as validações necessárias para garantir a integridade dos dados.
 */
@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    /**
     * Identificador único do cliente.
     * Gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Nome completo do cliente.
     * Campo obrigatório que não pode ser vazio ou nulo.
     */
    @NotBlank(message = "O nome não pode ser vazio")
    @Column(nullable = false)
    private String nome;

    /**
     * CPF do cliente.
     * Campo obrigatório e único que deve seguir o formato xxx.xxx.xxx-xx.
     */
    @NotBlank(message = "O CPF não pode ser vazio")
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "O CPF deve estar no formato xxx.xxx.xxx-xx")
    private String cpf;

    /**
     * Número de telefone do cliente.
     * Campo opcional que deve seguir o formato (xx)xxxxx-xxxx ou (xx)xxxx-xxxx.
     */
    @Column(nullable = true)
    @Pattern.List({
        @Pattern(regexp = "^$|^\\(\\d{2}\\)\\d{4,5}-\\d{4}$", message = "O telefone deve estar no formato (xx)xxxxx-xxxx ou (xx)xxxx-xxxx")
    })
    private String telefone;

    /**
     * Endereço de e-mail do cliente.
     * Campo opcional que deve ser um e-mail válido.
     */
    @Email(message = "O email não é valido")
    @Column(nullable = true)
    private String email;

    /**
     * Lista de contas associadas ao cliente.
     * Relacionamento um-para-muitos com a entidade Conta.
     * As operações de persistência são propagadas para as contas (cascade).
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Conta> contas;
}
