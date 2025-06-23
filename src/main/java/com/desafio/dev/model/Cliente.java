package com.desafio.dev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "O nome não pode ser vazio")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O CPF não pode ser vazio")
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "O CPF deve estar no formato xxx.xxx.xxx-xx")
    private String cpf;

    @Column(nullable = true)
    @Pattern(regexp = "^\\(\\d{2}\\)\\d{4,5}-\\d{4}$", message = "O telefone deve estar no formato (xx)xxxxx-xxxx ou (xx)xxxx-xxxx")
    private String telefone;

    @Email(message = "O email não é valido")
    @Column(nullable = true)
    private String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Conta> contas;
}
