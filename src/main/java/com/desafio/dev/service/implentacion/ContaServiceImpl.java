package com.desafio.dev.service.implentacion;

import com.desafio.dev.ENUMS.Situacao;
import com.desafio.dev.dtos.conta.ContaRequestDTO;
import com.desafio.dev.dtos.conta.ContaResponseDTO;
import com.desafio.dev.exceptions.*;
import com.desafio.dev.model.Cliente;
import com.desafio.dev.model.Conta;
import com.desafio.dev.repository.ClienteRepository;
import com.desafio.dev.repository.ContaRepository;
import com.desafio.dev.service.interfaces.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Implementação dos serviços relacionados a contas bancárias.
 * 
 * Esta classe implementa a interface ContaService e fornece a lógica de negócio
 * para todas as operações relacionadas a contas, incluindo criação, consulta,
 * atualização e exclusão de registros. Também gerencia as regras de negócio
 * relacionadas às situações das contas (PENDENTE, PAGA, CANCELADA).
 */
@Service
public class ContaServiceImpl implements ContaService {

    /**
     * Repositório para acesso aos dados de contas no banco de dados.
     */
    @Autowired
    private ContaRepository contaRepository;

    /**
     * Repositório para acesso aos dados de clientes no banco de dados.
     */
    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Cria uma nova conta associada a um cliente.
     * 
     * @param id ID do cliente ao qual a conta será associada
     * @param contaRequestDTO DTO contendo os dados da conta a ser criada
     * @return DTO com os dados da conta criada
     * @throws ClienteNotFoundException se o cliente não for encontrado
     * @throws SituacaoCanceladaNotAvailableException se tentar criar uma conta com situação CANCELADA
     * @throws SituacaoNotAvailableException se a situação informada não for válida
     */
    @Override
    public ContaResponseDTO save(Long id, ContaRequestDTO contaRequestDTO) {
        // Verificar se o cliente existe e associar a conta
        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () -> new ClienteNotFoundException()
        );

        if (contaRequestDTO.situacao().equals(Situacao.CANCELADA)) {
            throw new SituacaoCanceladaNotAvailableException();
        }

        if (!contaRequestDTO.situacao().equals(Situacao.PAGA) && !contaRequestDTO.situacao().equals(Situacao.PENDENTE)) {
            throw new SituacaoNotAvailableException(contaRequestDTO.situacao().toString());
        }

        Conta conta = new Conta();
        conta.setCliente(cliente);
        conta.setReferencia(contaRequestDTO.referencia());
        conta.setValor(contaRequestDTO.valor());
        conta.setSituacao(contaRequestDTO.situacao());
        contaRepository.save(conta);

        return new ContaResponseDTO(
                conta.getId(),
                conta.getReferencia(),
                conta.getValor(),
                conta.getSituacao()
        );
    }

    /**
     * Retorna a lista de todas as contas não canceladas de um cliente.
     * 
     * @param id ID do cliente cujas contas serão listadas
     * @return Lista de DTOs com os dados das contas
     * @throws ContaEmptyException se o cliente não tiver contas ou se todas estiverem canceladas
     */
    @Override
    public List<ContaResponseDTO> findAllByClienteId(Long id) {
        List<Situacao> situacoesPermitidas = Arrays.asList(Situacao.PENDENTE, Situacao.PAGA);
        List<Conta> contas = contaRepository.findAllByClienteIdAndSituacaoIn(id, situacoesPermitidas);

        if (contas.isEmpty()) {
            throw new ContaEmptyException();
        }
        return contas.stream().map(conta -> new ContaResponseDTO(
                conta.getId(),
                conta.getReferencia(),
                conta.getValor(),
                conta.getSituacao())
        ).toList();
    }

    /**
     * Atualiza os dados de uma conta existente.
     * 
     * @param id ID da conta a ser atualizada
     * @param contaRequestDTO DTO contendo os novos dados da conta
     * @return DTO com os dados atualizados da conta
     * @throws ContaNotFoundException se a conta não for encontrada
     */
    @Override
    public ContaResponseDTO update(Long id, ContaRequestDTO contaRequestDTO) {
        Conta conta = contaRepository.findById(id).orElseThrow(
                () -> new ContaNotFoundException()
        );
        conta.setReferencia(contaRequestDTO.referencia());
        conta.setValor(contaRequestDTO.valor());
        conta.setSituacao(contaRequestDTO.situacao());
        contaRepository.save(conta);
        return new ContaResponseDTO(
                conta.getId(),
                conta.getReferencia(),
                conta.getValor(),
                conta.getSituacao()
        );
    }

    /**
     * Marca uma conta como CANCELADA no sistema.
     * 
     * @param id ID da conta a ser cancelada
     * @throws ContaNotFoundException se a conta não for encontrada
     */
    @Override
    public void delete(Long id) {
        Conta conta = contaRepository.findById(id).orElseThrow(
            () -> new ContaNotFoundException()
        );
        conta.setSituacao(Situacao.CANCELADA);
        contaRepository.save(conta);
    }
}
