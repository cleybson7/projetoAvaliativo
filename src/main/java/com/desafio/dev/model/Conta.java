package com.desafio.dev.model;

import com.desafio.dev.ENUMS.Situacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "conta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "A situação nao pode ser nula")
    @Pattern(regexp = "^(0[1-9]|1[0-2])-\\d{4}$", message = "A referência deve estar no formato MM-AAAA válido")
    private String referencia;

    @NotNull(message = "A situação nao pode ser nula")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    private BigDecimal valor;

    @NotNull(message = "A situação nao pode ser nula")
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
