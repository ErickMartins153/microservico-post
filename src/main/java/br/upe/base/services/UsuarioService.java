package br.upe.base.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.upe.base.models.DTOs.UsuarioDTO;
import org.springframework.stereotype.Service;

import br.upe.base.models.Usuario;

@Service
public interface UsuarioService {

    UsuarioDTO salvarUsuario(Usuario usuario);

    Optional<UsuarioDTO> buscarPorEmail(String email);

    UsuarioDTO logar(String email, String senha);

    UsuarioDTO buscarPorId(UUID id);

    void deletarUsuario(UUID id);

    void follow(UUID id, UUID idSeguido);

    void unfollow(UUID id, UUID idSeguido);

    List<UsuarioDTO> listarSeguidores(UUID id);

    List<UsuarioDTO> listarSeguidos(UUID id);

    List<UsuarioDTO> listarTodos();

}
