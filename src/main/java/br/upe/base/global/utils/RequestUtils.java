package br.upe.base.global.utils;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtils {

    /**
     * Retorna a url completa, juntamente com quaisquer parâmetros passados
     * @param request {@link HttpServletRequest} com as informações da requisição
     * @return url e seus parâmetros
     */
    public static String getFullRequestURL(HttpServletRequest request) {
        return request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }

}
