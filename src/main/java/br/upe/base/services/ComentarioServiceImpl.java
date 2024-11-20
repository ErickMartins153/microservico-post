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
        return comentarioRepository.findAll();
    }

    @Override
    public Comentario getComentarioById(UUID id) {
        if (comentarioRepository.findById(id).isEmpty()){
            return null;
        }

        return comentarioRepository.findById(id).get();
    }

    @Override
    public Comentario saveComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    @Override
    public Comentario updateComentario(Comentario comentario) {
        return null;
    }

    @Override
    public void deleteComentario(UUID id) {

    }
}
