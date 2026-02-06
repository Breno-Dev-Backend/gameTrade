package com.nexus.game_trade.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(
            String errorCode,
            String message,
            String userMessage,
            LocalDateTime timestamp,
            String path
    ) {}

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        log.warn("Not Found Exception: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                "Desculpe, o recurso solicitado não foi encontrado. Por favor, verifique os dados e tente novamente.",
                LocalDateTime.now(),
                getPath(request)
        );

        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex, WebRequest request) {
        log.warn("Conflict Exception: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                "Operação não pode ser realizada. Verifique se o recurso já existe ou se há conflitos com dados existentes.",
                LocalDateTime.now(),
                getPath(request)
        );

        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex, WebRequest request) {
        log.warn("Validation Exception: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                "Dados inválidos fornecidos. Por favor, verifique os campos obrigatórios e tente novamente.",
                LocalDateTime.now(),
                getPath(request)
        );

        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex, WebRequest request) {
        log.warn("Business Exception: {} - Status: {}", ex.getMessage(), ex.getStatus());

        ErrorResponse error = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                "Ocorreu um erro ao processar sua solicitação. Por favor, tente novamente.",
                LocalDateTime.now(),
                getPath(request)
        );

        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        log.error("Unhandled Exception capturada: ", ex);

        ErrorResponse error = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "Erro interno do servidor",
                "Desculpe, ocorreu um erro inesperado no sistema. Por favor, tente novamente mais tarde ou entre em contato com o suporte.",
                LocalDateTime.now(),
                getPath(request)
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}