package br.upe.base.models.DTOs;

import java.util.UUID;

import br.upe.base.models.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    private UUID usuarioId;
    private String nome;
    private String email;

    
    public UsuarioDTO(UUID usuarioId, String nome, String email) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.email = email;
    }


    public Usuario toUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(usuarioId);
        usuario.setNome(nome);
        usuario.setEmail(email);
        return usuario;
    }
}
