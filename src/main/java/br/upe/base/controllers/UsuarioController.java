package br.upe.base.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.upe.base.models.Usuario;
import br.upe.base.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario) {
        return usuarioService.salvarUsuario(usuario);
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario usuario) {
        return usuarioService.buscarPorEmail(usuario.getEmail())
            .filter(u -> u.getSenha().equals(usuario.getSenha()))
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @PostMapping("/follow/{seguidorId}/{seguidoId}")
    public ResponseEntity<String> seguir(@PathVariable UUID seguidorId, @PathVariable UUID seguidoId) {
        try {
            usuarioService.follow(seguidorId, seguidoId);
            return ResponseEntity.ok("Usuário seguido com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao seguir usuário: " + e.getMessage());
        }
    }


    @PostMapping("/unfollow/{seguidorId}/{seguidoId}")
    public ResponseEntity<String> deixarDeSeguir(@PathVariable UUID seguidorId, @PathVariable UUID seguidoId) {
        try {
            usuarioService.unfollow(seguidorId, seguidoId);
            return ResponseEntity.ok("Deixou de seguir o usuário");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao deixar de seguir usuário: " + e.getMessage());
        }
    }

    @GetMapping("/followers/{usuarioId}")
    public ResponseEntity<List<Usuario>> followers(@PathVariable UUID usuarioId) {
        List<Usuario> lista = usuarioService.listarSeguidores(usuarioId);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/following/{usuarioId}")
    public ResponseEntity<List<Usuario>> following(@PathVariable UUID usuarioId) {
        List<Usuario> lista = usuarioService.listarSeguidos(usuarioId);
        return ResponseEntity.ok(lista);
    }

    
}
