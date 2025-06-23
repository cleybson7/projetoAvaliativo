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

@RestControllerAdvice
public class GlobalExceptionController {

    @Autowired
    private MessageSource messageSource;

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

    @ExceptionHandler(ClienteAlreadyExistsException.class)
    public ResponseEntity<String> handleClienteAlreadyExistsException(ClienteAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<String> handleClienteNotFoundException(ClienteNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ContaNotFoundException.class)
    public ResponseEntity<String> handleContaNotFoundException(ContaNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ClienteEmptyException.class)
    public ResponseEntity<String> handleClienteEmptyException(ClienteEmptyException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ContaEmptyException.class)
    public ResponseEntity<String> handleContaEmptyException(ContaEmptyException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(SituacaoCanceladaNotAvailableException.class)
    public ResponseEntity<String> handleSituacaoCanceladaNotAvailableException(SituacaoCanceladaNotAvailableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(SituacaoNotAvailableException.class)
    public ResponseEntity<String> handleSituacaoNotAvailableException(SituacaoNotAvailableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

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
