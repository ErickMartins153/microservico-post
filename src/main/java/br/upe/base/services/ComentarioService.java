package br.upe.base.services;

import br.upe.base.models.Comentario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface ComentarioService {
    List<Comentario> listAllComentarios();
    Comentario getComentarioById(UUID id);
    Comentario saveComentario(Comentario comentario);
    Comentario updateComentario(Comentario comentario);
    void deleteComentario(UUID id);
}
