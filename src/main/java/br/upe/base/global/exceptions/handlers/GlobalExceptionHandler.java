package br.upe.base.global.exceptions.handlers;

import br.upe.base.global.exceptions.ExceptionBody;
import br.upe.base.global.utils.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionBody> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                    HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        var response = ExceptionBody.builder()
            .httpStatus(HttpStatus.BAD_REQUEST.value())
            .error("Dados inválidos")
            .message(errors.toString())
            .request(RequestUtils.getFullRequestURL(req))
            .timeStamp(Instant.now())
            .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionBody> handleUnresolvedException(Exception ex, HttpServletRequest req) {
        log.error(ex.getMessage(), ex);

        var response = ExceptionBody.builder()
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Erro Interno do Servidor")
            .message("Ocorreu um erro inesperado no servidor, tente novamente mais tarde")
            .request(RequestUtils.getFullRequestURL(req))
            .timeStamp(Instant.now())
            .build();

        return ResponseEntity.internalServerError().body(response);
    }

}
