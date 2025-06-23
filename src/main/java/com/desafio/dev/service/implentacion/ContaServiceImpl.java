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

@Service
public class ContaServiceImpl implements ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

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

    @Override
    public void delete(Long id) {
        Conta conta = contaRepository.findById(id).orElseThrow(
            () -> new ContaNotFoundException()
        );
        conta.setSituacao(Situacao.CANCELADA);
        contaRepository.save(conta);
    }
}
