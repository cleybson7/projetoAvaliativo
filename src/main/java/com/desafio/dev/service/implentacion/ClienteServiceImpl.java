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

/**
 * Implementação dos serviços relacionados a clientes.
 * 
 * Esta classe implementa a interface ClienteService e fornece a lógica de negócio
 * para todas as operações relacionadas a clientes, incluindo criação, consulta,
 * atualização e exclusão de registros.
 */
@Service
public class ClienteServiceImpl implements ClienteService{

    /**
     * Repositório para acesso aos dados de clientes no banco de dados.
     */
    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Cria um novo cliente no sistema.
     * 
     * @param clienteRequestDTO DTO contendo os dados do cliente a ser criado
     * @return DTO com os dados do cliente criado
     * @throws ClienteAlreadyExistsException se já existir um cliente com o CPF informado
     */
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

    /**
     * Retorna a lista de todos os clientes cadastrados.
     * 
     * @return Lista de DTOs com os dados dos clientes
     * @throws ClienteEmptyException se não houver clientes cadastrados
     */
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

    /**
     * Atualiza os dados de um cliente existente.
     * 
     * @param id ID do cliente a ser atualizado
     * @param clienteRequestDTO DTO contendo os novos dados do cliente
     * @return DTO com os dados atualizados do cliente
     * @throws ClienteNotFoundException se o cliente não for encontrado
     */
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

    /**
     * Remove um cliente do sistema.
     * 
     * @param id ID do cliente a ser removido
     */
    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }
}
