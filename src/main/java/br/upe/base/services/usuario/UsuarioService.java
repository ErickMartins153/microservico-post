package br.upe.base.services.usuario;

import br.upe.base.models.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface UsuarioService {
    Usuario salvarUsuario(Usuario usuario);

    Optional<Usuario> buscarPorEmail(String email);

    boolean emailExiste(String email);

    public Optional<Usuario> buscarPorId(UUID id);

    void deletarUsuario(UUID id);

}
