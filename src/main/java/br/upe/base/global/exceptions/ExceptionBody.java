package br.upe.base.global.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Builder
@AllArgsConstructor
@Getter @Setter
public class ExceptionBody {

    /**
     * Status HTTP retornado
     */
    private int httpStatus;

    /**
     * Erro que ocorreu
     */
    private String error;

    /**
     * Mensagem que será mostrada para contextualizar o erro
     */
    private String message;

    /**
     * URL completa da requisição que ocasionou a exceção
     */
    private String request;

    /**
     * Instante no qual o erro ocorreu
     */
    private Instant timeStamp;

}
