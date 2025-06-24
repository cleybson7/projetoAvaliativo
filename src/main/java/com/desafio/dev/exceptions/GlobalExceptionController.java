package com.desafio.dev.exceptions;

import com.desafio.dev.dtos.error.ErrorMessageDTO;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controlador global de exceções da aplicação.
 * 
 * Esta classe é responsável por interceptar e tratar todas as exceções lançadas pela aplicação,
 * fornecendo respostas padronizadas e adequadas para cada tipo de erro.
 */
@RestControllerAdvice
public class GlobalExceptionController {

    /**
     * Fonte de mensagens para internacionalização.
     * Utilizado para obter mensagens de erro localizadas.
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Trata exceções de validação de argumentos de métodos.
     * Este método é chamado quando ocorrem erros de validação em parâmetros de requisições.
     *
     * @param e A exceção de validação capturada
     * @return ResponseEntity contendo uma lista de mensagens de erro
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorMessageDTO> errors = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            ErrorMessageDTO dto = new ErrorMessageDTO(message, error.getField());
            errors.add(dto);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Trata exceções de violação de restrições.
     * Este método é chamado quando ocorrem violações de validações definidas por anotações.
     *
     * @param e A exceção de violação de restrição capturada
     * @return ResponseEntity contendo uma lista de mensagens de erro
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleViolationException(ConstraintViolationException e) {
        List<ErrorMessageDTO> errors = new ArrayList<>();

        e.getConstraintViolations().forEach(error -> {
            String campo = error.getPropertyPath().toString();
            String message = error.getMessage();
            ErrorMessageDTO dto = new ErrorMessageDTO(message, campo);
            errors.add(dto);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Trata exceções de cliente já existente.
     * Este método é chamado quando se tenta cadastrar um cliente com CPF já registrado.
     *
     * @param e A exceção de cliente já existente
     * @return ResponseEntity com a mensagem de erro
     */
    @ExceptionHandler(ClienteAlreadyExistsException.class)
    public ResponseEntity<String> handleClienteAlreadyExistsException(ClienteAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    /**
     * Trata exceções de cliente não encontrado.
     * Este método é chamado quando se tenta acessar um cliente que não existe no sistema.
     *
     * @param e A exceção de cliente não encontrado
     * @return ResponseEntity com a mensagem de erro
     */
    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<String> handleClienteNotFoundException(ClienteNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Trata exceções de conta não encontrada.
     * Este método é chamado quando se tenta acessar uma conta que não existe no sistema.
     *
     * @param e A exceção de conta não encontrada
     * @return ResponseEntity com a mensagem de erro
     */
    @ExceptionHandler(ContaNotFoundException.class)
    public ResponseEntity<String> handleContaNotFoundException(ContaNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Trata exceções de lista de clientes vazia.
     * Este método é chamado quando se tenta acessar a lista de clientes e não há registros.
     *
     * @param e A exceção de lista de clientes vazia
     * @return ResponseEntity com a mensagem de erro
     */
    @ExceptionHandler(ClienteEmptyException.class)
    public ResponseEntity<String> handleClienteEmptyException(ClienteEmptyException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Trata exceções de lista de contas vazia.
     * Este método é chamado quando se tenta acessar a lista de contas e não há registros.
     *
     * @param e A exceção de lista de contas vazia
     * @return ResponseEntity com a mensagem de erro
     */
    @ExceptionHandler(ContaEmptyException.class)
    public ResponseEntity<String> handleContaEmptyException(ContaEmptyException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Trata exceções de tentativa de criar conta com situação cancelada.
     * Este método é chamado quando se tenta criar uma nova conta com status CANCELADA.
     *
     * @param e A exceção de situação cancelada não permitida
     * @return ResponseEntity com a mensagem de erro
     */
    @ExceptionHandler(SituacaoCanceladaNotAvailableException.class)
    public ResponseEntity<String> handleSituacaoCanceladaNotAvailableException(SituacaoCanceladaNotAvailableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * Trata exceções de situação não permitida.
     * Este método é chamado quando se tenta usar uma situação inválida para uma conta.
     *
     * @param e A exceção de situação não permitida
     * @return ResponseEntity com a mensagem de erro
     */
    @ExceptionHandler(SituacaoNotAvailableException.class)
    public ResponseEntity<String> handleSituacaoNotAvailableException(SituacaoNotAvailableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * Trata erros de parsing de JSON e valores de enum inválidos.
     * Este método é chamado quando ocorrem erros na conversão do corpo da requisição,
     * especialmente para valores de enum inválidos.
     *
     * @param ex A exceção de mensagem HTTP não legível
     * @return ResponseEntity com a mensagem de erro formatada
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonParseErrors(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof InvalidFormatException formatError) {
            // Trata especificamente erros de enum
            if (formatError.getTargetType().isEnum()) {
                String campo = formatError.getPath().get(0).getFieldName();
                String valorErrado = formatError.getValue().toString();
                String opcoes = Arrays.toString(formatError.getTargetType().getEnumConstants());

                String mensagem = "A situação '" + valorErrado + "' não é aceito para " + campo + ". " +
                        "Os valores permitidos são: " + opcoes;

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagem);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
