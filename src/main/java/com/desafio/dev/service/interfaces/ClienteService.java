package com.desafio.dev.service.interfaces;

import com.desafio.dev.dtos.cliente.ClienteRequestDTO;
import com.desafio.dev.dtos.cliente.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {
    ClienteResponseDTO save(ClienteRequestDTO clienteRequestDTO);
    List<ClienteResponseDTO> findAll();
    ClienteResponseDTO update(Long id, ClienteRequestDTO clienteRequestDTO);
    void delete(Long id);
}
