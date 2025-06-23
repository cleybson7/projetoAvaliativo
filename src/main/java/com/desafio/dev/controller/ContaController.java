package com.desafio.dev.controller;

import com.desafio.dev.dtos.conta.ContaRequestDTO;
import com.desafio.dev.dtos.conta.ContaResponseDTO;
import com.desafio.dev.service.implentacion.ContaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ContaController {

    @Autowired
    private ContaServiceImpl contaService;

    @PostMapping("/clientes/{idCliente}/contas")
    public ResponseEntity<ContaResponseDTO> save(@PathVariable Long idCliente, @Valid @RequestBody ContaRequestDTO contaRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.save(idCliente, contaRequestDTO));
    }

    @GetMapping("/clientes/{idCliente}/contas")
    public ResponseEntity<List<ContaResponseDTO>> findAllByClienteId(@PathVariable Long idCliente) {
        return ResponseEntity.status(HttpStatus.OK).body(contaService.findAllByClienteId(idCliente));
    }

    @PutMapping("/contas/{id}")
    public ResponseEntity<ContaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ContaRequestDTO contaRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(contaService.update(id, contaRequestDTO));
    }

    @DeleteMapping("/contas/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        contaService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Conta deletada com sucesso!");
    }
}
