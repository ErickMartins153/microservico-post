package br.upe.base.services;

import br.upe.base.models.Usuario;
import br.upe.base.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface UsuarioService {

    Usuario salvarUsuario(Usuario usuario);
    Optional<Usuario> buscarPorEmail(String email);
    boolean emailExiste(String email);
    Optional<Usuario> buscarPorId(UUID id);
    void deletarUsuario(UUID id);
}
