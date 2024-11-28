package br.upe.base.services.comentario;

import br.upe.base.models.DTOs.ComentarioCreationDTO;
import br.upe.base.models.DTOs.ComentarioDTO;

import java.util.List;
import java.util.UUID;

public interface ComentarioService {
    ComentarioDTO getComentarioById(UUID idComentario);
    List<ComentarioDTO> listAllComentarios();
    ComentarioDTO saveComentario(ComentarioCreationDTO comentario);
    ComentarioDTO updateComentario(UUID idComentario, ComentarioDTO comentario);
    ComentarioDTO addCurtida(UUID idComentario);
    ComentarioDTO removeCurtida(UUID idComentario);
    void deleteComentario(UUID idComentario);
}
