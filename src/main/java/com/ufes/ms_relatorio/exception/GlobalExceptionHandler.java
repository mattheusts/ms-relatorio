package com.ufes.ms_relatorio.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDTO> handleValidacao(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ErroDTO.CampoErroDTO> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> new ErroDTO.CampoErroDTO(e.getField(), e.getDefaultMessage()))
                .collect(Collectors.toList());
        ErroDTO dto = ErroDTO.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Requisição inválida")
                .path(request.getRequestURI())
                .message("Erro de validação")
                .erros(erros)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErroDTO> handleParametroObrigatorio(MissingServletRequestParameterException ex,
            HttpServletRequest request) {
        ErroDTO dto = ErroDTO.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Requisição inválida")
                .path(request.getRequestURI())
                .message("Parâmetro obrigatório ausente: " + ex.getParameterName())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroDTO> handleTipoInvalido(MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {
        ErroDTO dto = ErroDTO.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Requisição inválida")
                .path(request.getRequestURI())
                .message("Valor inválido para o parâmetro: " + ex.getName())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErroDTO> handleRecursoNaoEncontrado(NoResourceFoundException ex, HttpServletRequest request) {
        log.warn("Nenhum handler para {}: {}", request.getRequestURI(), ex.getMessage());
        ErroDTO dto = ErroDTO.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Não encontrado")
                .path(request.getRequestURI())
                .message("Endpoint não encontrado: " + request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroDTO> handleArgumentoInvalido(IllegalArgumentException ex, HttpServletRequest request) {
        ErroDTO dto = ErroDTO.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Requisição inválida")
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> handleGenerico(Exception ex, HttpServletRequest request) {
        log.error("Erro ao processar requisição {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        String msg = ex.getMessage() != null ? ex.getMessage() : ex.getClass().getSimpleName();
        if (ex.getCause() != null && ex.getCause().getMessage() != null) {
            msg = msg + "; causa: " + ex.getCause().getMessage();
        }
        final String detail = (msg != null && !msg.isEmpty()) ? msg : "Erro sem mensagem";
        ErroDTO dto = ErroDTO.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Erro interno")
                .path(request.getRequestURI())
                .message("Ocorreu um erro inesperado")
                .detail(detail)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }
}
