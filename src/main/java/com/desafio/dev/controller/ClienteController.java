package com.desafio.dev.controller;

import com.desafio.dev.dtos.cliente.ClienteRequestDTO;
import com.desafio.dev.dtos.cliente.ClienteResponseDTO;
import com.desafio.dev.service.implentacion.ClienteServiceImpl;

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
 * Controlador responsável por gerenciar as operações relacionadas a clientes.
 * 
 * Esta classe fornece endpoints REST para criar, listar, atualizar e excluir
 * clientes no sistema. Todas as operações são documentadas usando anotações
 * do Swagger para facilitar o uso da API.
 */
@Tag(name = "Clientes", description = "Operações para gestão de clientes")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    /**
     * Serviço que implementa a lógica de negócio relacionada a clientes.
     */
    @Autowired
    private ClienteServiceImpl clienteService;

    /**
     * Cria um novo cliente no sistema.
     * 
     * @param clienteRequestDTO DTO contendo os dados do cliente a ser criado
     * @return ResponseEntity contendo os dados do cliente criado
     */
    @Operation(
        summary = "Criar novo cliente",
        description = "Registra um novo cliente no sistema",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Cliente criado com sucesso",
                content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class)))}
    
    )
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> createCliente(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(clienteRequestDTO));
    }

    /**
     * Retorna a lista de todos os clientes cadastrados no sistema.
     * 
     * @return ResponseEntity contendo a lista de clientes
     */
    @Operation(
        summary = "Listar todos os clientes",
        description = "Recupera todos os clientes cadastrados",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de clientes recuperada com sucesso",
                content = @Content(schema = @Schema(implementation = ClienteResponseDTO[].class)))}
    
    )
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> findAllClientes() {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAll());
    }

    /**
     * Atualiza os dados de um cliente existente.
     * 
     * @param id ID do cliente a ser atualizado
     * @param clienteRequestDTO DTO contendo os novos dados do cliente
     * @return ResponseEntity contendo os dados atualizados do cliente
     */
    @Operation(
        summary = "Atualizar cliente",
        description = "Atualiza os dados de um cliente existente",
        parameters = {
            @Parameter(name = "id", description = "ID do cliente a ser atualizado", example = "123")},
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Cliente atualizado com sucesso",
                content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class)))}
    

    )
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.update(id, clienteRequestDTO));
    }

    /**
     * Remove um cliente do sistema.
     * 
     * @param id ID do cliente a ser excluído
     * @return ResponseEntity com mensagem de confirmação
     */
    @Operation(
        summary = "Excluir cliente",
        description = "Remove um cliente do sistema",
        parameters = {
            @Parameter(name = "id", description = "ID do cliente a ser excluído", example = "123")},
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Cliente excluído com sucesso",
                content = @Content(schema = @Schema(implementation = String.class)))}
    

    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado com sucesso!");
    }
}
