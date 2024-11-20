package br.upe.base.services;

import br.upe.base.models.Comentario;
import br.upe.base.repositories.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ComentarioServiceImpl implements ComentarioService{

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    public List<Comentario> listAllComentarios() {
        return List.of();
    }

    @Override
    public Comentario getComentarioById(UUID id) {
        return null;
    }

    @Override
    public Comentario saveComentario(Comentario comentario) {
        return null;
    }

    @Override
    public Comentario updateComentario(Comentario comentario) {
        return null;
    }

    @Override
    public void deleteComentario(UUID id) {

    }
}
