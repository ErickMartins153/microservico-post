package br.upe.base.controllers.handlers;

import br.upe.base.exceptions.postExceptions.DuplicatePostException;
import br.upe.base.exceptions.postExceptions.PostNotFoundException;
import br.upe.base.global.exceptions.ExceptionBody;
import br.upe.base.global.utils.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@Log4j2
@ControllerAdvice
public class PostExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ExceptionBody> handlePostNotFound(PostNotFoundException ex, HttpServletRequest req) {
        var response = ExceptionBody.builder()
            .httpStatus(HttpStatus.NOT_FOUND.value())
            .error("Post não encontrado")
            .message(ex.getMessage())
            .request(RequestUtils.getFullRequestURL(req))
            .timeStamp(Instant.now())
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicatePostException.class)
    public ResponseEntity<ExceptionBody> handleDuplicatePost(DuplicatePostException ex, HttpServletRequest req) {
        var response = ExceptionBody.builder()
            .httpStatus(HttpStatus.CONFLICT.value())
            .error("Conflito de dados")
            .message(ex.getMessage())
            .request(RequestUtils.getFullRequestURL(req))
            .timeStamp(Instant.now())
            .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
