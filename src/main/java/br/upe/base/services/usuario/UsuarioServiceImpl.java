package br.upe.base.services.usuario;

import br.upe.base.exceptions.usuarioExceptions.EmailNaoCadastradoException;
import br.upe.base.exceptions.usuarioExceptions.SenhaIncorretaException;
import br.upe.base.exceptions.usuarioExceptions.UsuarioNaoEncontradoException;
import br.upe.base.models.DTOs.UsuarioCreationDTO;
import br.upe.base.models.DTOs.UsuarioDTO;
import br.upe.base.models.Usuario;
import br.upe.base.repositories.UsuarioRepository;
import br.upe.base.services.usuario.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDTO salvarUsuario(UsuarioCreationDTO dto) {

        Usuario novoUsuario = new Usuario(
                dto.id(),
                dto.nome(),
                dto.email(),
                dto.senha(),
                null,
                null,
                null,
                null
        );

        Usuario savedUsuario = usuarioRepository.save(novoUsuario);
        return UsuarioDTO.to(savedUsuario);
    }

    public Optional<UsuarioDTO> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(UsuarioDTO::to);
    }

    public UsuarioDTO logar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new EmailNaoCadastradoException(email));
        if (!senha.equals(usuario.getSenha())) throw new SenhaIncorretaException();
        return UsuarioDTO.to(usuario);
    }

    public UsuarioDTO buscarPorId(UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) return null;
        return UsuarioDTO.to(usuario.get());
    }

    public void deletarUsuario(UUID id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public void follow(UUID id, UUID idSeguido) {
        Usuario seguidor = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        Usuario seguido = usuarioRepository.findById(idSeguido)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(idSeguido));

        if (!seguidor.getSeguindo().contains(seguido)) {
            seguidor.getSeguindo().add(seguido);
            seguido.getSeguidores().add(seguidor);
            usuarioRepository.save(seguidor);
            usuarioRepository.save(seguido);
        }
    }

    @Override
    public void unfollow(UUID id, UUID idSeguido) {
        Usuario seguidor = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        Usuario seguido = usuarioRepository.findById(idSeguido)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(idSeguido));

        seguidor.getSeguindo().remove(seguido);
        seguido.getSeguidores().remove(seguidor);
        usuarioRepository.save(seguidor);
        usuarioRepository.save(seguido);
    }

    @Override
    public List<UsuarioDTO> listarSeguidores(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        return usuario.getSeguidores().stream()
                .map(UsuarioDTO::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> listarSeguidos(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        return usuario.getSeguindo().stream()
                .map(UsuarioDTO::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioDTO::to)
                .collect(Collectors.toList());
    }
}
