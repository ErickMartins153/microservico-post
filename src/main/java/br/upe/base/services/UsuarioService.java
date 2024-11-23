package br.upe.base.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.upe.base.models.Usuario;

@Service 
public interface UsuarioService {
    public Usuario salvarUsuario(Usuario usuario);
    public Optional<Usuario> buscarPorEmail(String email);
    public boolean emailExiste(String email);
    public Optional<Usuario> buscarPorId(UUID id);
    public void deletarUsuario(UUID id);
    public void follow(UUID id, UUID idSeguido);
    public void unfollow(UUID id, UUID idSeguido);
    public List<Usuario> listarSeguidores(UUID id);
    public List<Usuario> listarSeguidos(UUID id);

}
