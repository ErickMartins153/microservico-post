package br.upe.base.services;

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
}
