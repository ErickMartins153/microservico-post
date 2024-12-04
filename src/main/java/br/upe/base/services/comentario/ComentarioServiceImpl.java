package br.upe.base.services.comentario;

import br.upe.base.models.Comentario;
import br.upe.base.models.DTOs.ComentarioCreationDTO;
import br.upe.base.models.DTOs.ComentarioDTO;
import br.upe.base.models.DTOs.PostDTO;
import br.upe.base.models.DTOs.UsuarioDTO;
import br.upe.base.models.Usuario;
import br.upe.base.repositories.ComentarioRepository;
import br.upe.base.services.post.PostServiceImpl;
import br.upe.base.services.usuario.UsuarioServiceImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    public final ComentarioRepository comentarioRepository;
    public final PostServiceImpl postService;
    public final UsuarioServiceImpl usuarioService;

    @Transactional
    @Override
    public ComentarioDTO getComentarioById(UUID idComentario) {
        return ComentarioDTO.from(findById(idComentario));
    }

    @Override
    public List<ComentarioDTO> listAllComentarios(UUID idPost) {

        return postService
                .getPostById(idPost)
                .comentarios()
                .stream()
                .map(ComentarioDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO saveComentario(ComentarioCreationDTO comentarioCreationDTO) {
        PostDTO postDTO = postService.getPostById(comentarioCreationDTO.idPost());
        UsuarioDTO usuarioDTO = usuarioService.buscarPorId(comentarioCreationDTO.idDono());

        Usuario usuario = UsuarioDTO.from(usuarioDTO);

        Comentario comentario = new Comentario(
                null,
                PostDTO.from(postDTO,usuario),
                usuario,
                comentarioCreationDTO.conteudo(),
                0,
                Instant.now());

        Comentario postSalvo = comentarioRepository.save(comentario);
        return ComentarioDTO.from(postSalvo);
    }

    @Override
    public ComentarioDTO updateComentario(UUID idComentario, ComentarioDTO comentario) {
        Comentario comentarioSalvo = findById(idComentario);
        comentarioSalvo.setConteudo(comentario.conteudo());
        comentarioRepository.save(comentarioSalvo);
        return ComentarioDTO.from(comentarioSalvo);
    }

    @Override
    public ComentarioDTO addCurtida(UUID idComentario) {
        Comentario comentario = findById(idComentario);
        comentario.setCurtidas(comentario.getCurtidas() + 1);
        comentarioRepository.save(comentario);
        return ComentarioDTO.from(comentario);
    }

    @Override
    public ComentarioDTO removeCurtida(UUID idComentario) {
        Comentario comentario = findById(idComentario);

        if (comentario.getCurtidas() > 0) {
            comentario.setCurtidas(comentario.getCurtidas() - 1);
        }

        return ComentarioDTO.from(comentarioRepository.save(comentario));
    }

    @Override
    public void deleteComentario(UUID idComentario) {
        Comentario comentario = findById(idComentario);
        comentarioRepository.delete(comentario);
    }

    private Comentario findById(UUID idComentario){
        return comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
    }
}
