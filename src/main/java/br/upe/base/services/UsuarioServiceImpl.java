package br.upe.base.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.upe.base.models.Usuario;
import br.upe.base.repositories.UsuarioRepository;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarioRepository.findById(id);
    }

    public void deletarUsuario(UUID id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public void follow(UUID id, UUID idSeguido) {
        Usuario seguidor = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Usuario seguido = usuarioRepository.findById(idSeguido)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

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
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Usuario seguido = usuarioRepository.findById(idSeguido)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        seguidor.getSeguindo().remove(seguido);
        seguido.getSeguidores().remove(seguidor);
        usuarioRepository.save(seguidor);
        usuarioRepository.save(seguido);
    }

    @Override
    public List<Usuario> listarSeguidores(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return usuario.getSeguidores();

    }

    @Override
    public List<Usuario> listarSeguidos(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return usuario.getSeguindo();
    }
}
