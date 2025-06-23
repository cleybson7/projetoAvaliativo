package com.desafio.dev.service.interfaces;

import com.desafio.dev.dtos.conta.ContaRequestDTO;
import com.desafio.dev.dtos.conta.ContaResponseDTO;

import java.util.List;

public interface ContaService {
    ContaResponseDTO save(Long id, ContaRequestDTO contaRequestDTO);
    List<ContaResponseDTO> findAllByClienteId(Long id);
    ContaResponseDTO update(Long id, ContaRequestDTO contaRequestDTO);
    void delete(Long id);
}
