package com.desafio.dev.controller;

import com.desafio.dev.dtos.conta.ContaRequestDTO;
import com.desafio.dev.dtos.conta.ContaResponseDTO;
import com.desafio.dev.service.implentacion.ContaServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas a contas bancárias.
 * 
 * Esta classe fornece endpoints REST para criar, listar, atualizar e excluir
 * contas no sistema, sempre associadas a um cliente específico. Todas as operações
 * são documentadas usando anotações do Swagger para facilitar o uso da API.
 */
@Tag(name = "Contas", description = "Operações para gestão de contas bancárias")
@RestController
@RequestMapping
public class ContaController {

    /**
     * Serviço que implementa a lógica de negócio relacionada a contas.
     */
    @Autowired
    private ContaServiceImpl contaService;

    /**
     * Cria uma nova conta associada a um cliente específico.
     * 
     * @param idCliente ID do cliente ao qual a conta será associada
     * @param contaRequestDTO DTO contendo os dados da conta a ser criada
     * @return ResponseEntity contendo os dados da conta criada
     */
    @Operation(
        summary = "Criar nova conta",
        description = "Associa uma nova conta a um cliente existente",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Conta criada com sucesso",
                content = @Content(schema = @Schema(implementation = ContaResponseDTO.class)))}
    )
    @PostMapping("/clientes/{idCliente}/contas")
    public ResponseEntity<ContaResponseDTO> save(@PathVariable Long idCliente, @Valid @RequestBody ContaRequestDTO contaRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.save(idCliente, contaRequestDTO));
    }

    /**
     * Retorna a lista de todas as contas associadas a um cliente específico.
     * 
     * @param idCliente ID do cliente cujas contas serão listadas
     * @return ResponseEntity contendo a lista de contas do cliente
     */
    @Operation(
        summary = "Listar contas por cliente",
        description = "Recupera todas as contas associadas a um cliente",
        parameters = {
            @Parameter(name = "idCliente", description = "ID do cliente para consulta", example = "456")},
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Contas recuperadas com sucesso",
                content = @Content(schema = @Schema(implementation = ContaResponseDTO[].class)))}
    )
    @GetMapping("/clientes/{idCliente}/contas")
    public ResponseEntity<List<ContaResponseDTO>> findAllByClienteId(@PathVariable Long idCliente) {
        return ResponseEntity.status(HttpStatus.OK).body(contaService.findAllByClienteId(idCliente));
    }

    /**
     * Atualiza os dados de uma conta existente.
     * 
     * @param id ID da conta a ser atualizada
     * @param contaRequestDTO DTO contendo os novos dados da conta
     * @return ResponseEntity contendo os dados atualizados da conta
     */
    @Operation(
        summary = "Atualizar conta",
        description = "Atualiza os dados de uma conta existente",
        parameters = {
            @Parameter(name = "id", description = "ID da conta a ser atualizada", example = "789")},
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Conta atualizada com sucesso",
                content = @Content(schema = @Schema(implementation = ContaResponseDTO.class)))}
    )
    @PutMapping("/contas/{id}")
    public ResponseEntity<ContaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ContaRequestDTO contaRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(contaService.update(id, contaRequestDTO));
    }

    /**
     * Remove uma conta do sistema (marca como CANCELADA).
     * 
     * @param id ID da conta a ser excluída
     * @return ResponseEntity com mensagem de confirmação
     */
    @Operation(
        summary = "Excluir conta",
        description = "Remove uma conta do sistema",
        parameters = {
            @Parameter(name = "id", description = "ID da conta a ser excluída", example = "789")},
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Conta excluída com sucesso",
                content = @Content(schema = @Schema(implementation = String.class)))}
    )
    @DeleteMapping("/contas/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        contaService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Conta deletada com sucesso!");
    }
}
