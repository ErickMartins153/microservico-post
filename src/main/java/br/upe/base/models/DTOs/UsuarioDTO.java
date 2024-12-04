package br.upe.base.models.DTOs;

import br.upe.base.models.Usuario;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record UsuarioDTO(
        UUID id,
        String nome,
        String email,
        List<UUID> seguidores,
        List<UUID> seguindo
) {
    public static UsuarioDTO to(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSeguidores().stream().map(u -> u.getId()).collect(Collectors.toList()),
                usuario.getSeguidores().stream().map(u -> u.getId()).collect(Collectors.toList())
        );
    }

    public static Usuario from(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.id);
        usuario.setNome(usuarioDTO.nome);
        usuario.setEmail(usuarioDTO.email);
        usuario.setSeguidores(new ArrayList<>());
        usuario.setSeguindo(new ArrayList<>());
        return usuario;
    }
}
