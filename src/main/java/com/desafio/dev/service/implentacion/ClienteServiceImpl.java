package com.desafio.dev.service.implentacion;

import com.desafio.dev.dtos.cliente.ClienteRequestDTO;
import com.desafio.dev.dtos.cliente.ClienteResponseDTO;
import com.desafio.dev.exceptions.ClienteAlreadyExistsException;
import com.desafio.dev.exceptions.ClienteEmptyException;
import com.desafio.dev.exceptions.ClienteNotFoundException;
import com.desafio.dev.model.Cliente;
import com.desafio.dev.repository.ClienteRepository;
import com.desafio.dev.service.interfaces.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public ClienteResponseDTO save(ClienteRequestDTO clienteRequestDTO) {
        if (clienteRepository.findByCpf(clienteRequestDTO.cpf()).isPresent()) {
            throw new ClienteAlreadyExistsException();
        }
        Cliente cliente = new Cliente();
        cliente.setNome(clienteRequestDTO.nome());
        cliente.setCpf(clienteRequestDTO.cpf());
        cliente.setTelefone(clienteRequestDTO.telefone());
        cliente.setEmail(clienteRequestDTO.email());
        clienteRepository.save(cliente);

        return new ClienteResponseDTO(cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail()
        );
    }

    @Override
    public List<ClienteResponseDTO> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        if (clientes.isEmpty()) {
            throw new ClienteEmptyException();
        }
        return clientes.stream()
                .map(cliente -> new ClienteResponseDTO(
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getTelefone(),
                    cliente.getEmail()
                )).toList();
    }

    @Override
    public ClienteResponseDTO update(Long id, ClienteRequestDTO clienteRequestDTO) {
        if (clienteRepository.findByCpf(clienteRequestDTO.cpf()) == null) {
            throw new ClienteNotFoundException();
        }

        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () -> new ClienteNotFoundException()
        );
        cliente.setNome(clienteRequestDTO.nome());
        cliente.setCpf(clienteRequestDTO.cpf());
        cliente.setTelefone(clienteRequestDTO.telefone());
        cliente.setEmail(clienteRequestDTO.email());
        clienteRepository.save(cliente);

        return new ClienteResponseDTO(cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail()
        );
    }

    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }
}
