package br.upe.base.controllers;

import java.util.List;
import java.util.UUID;

import br.upe.base.models.Credentials;
import br.upe.base.models.DTOs.UsuarioCreationDTO;
import br.upe.base.models.DTOs.UsuarioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.upe.base.models.Usuario;
import br.upe.base.services.usuario.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody UsuarioCreationDTO dto) {
        UsuarioDTO usuario = usuarioService.salvarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PostMapping("/login")
    public UsuarioDTO login(@RequestBody Credentials usuario) {
        return usuarioService.logar(usuario.getEmail(), usuario.getSenha());
    }

    @PostMapping("/follow/{seguidorId}/{seguidoId}")
    public ResponseEntity<?> seguir(@PathVariable UUID seguidorId, @PathVariable UUID seguidoId) {
        try {
            usuarioService.follow(seguidorId, seguidoId);
            UsuarioDTO usuario = usuarioService.buscarPorId(seguidorId);
            for (UsuarioDTO u : usuarioService.listarSeguidores(seguidoId)) {
                System.out.println(u.nome());
            }
            return ResponseEntity.ok().body(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);}
    }


    @PostMapping("/unfollow/{seguidorId}/{seguidoId}")
    public ResponseEntity<?> deixarDeSeguir(@PathVariable UUID seguidorId, @PathVariable UUID seguidoId) {
        try {
            usuarioService.unfollow(seguidorId, seguidoId);
            UsuarioDTO usuario = usuarioService.buscarPorId(seguidorId);
            
            return ResponseEntity.ok().body(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao deixar de seguir usuário: " + e.getMessage());
        }
    }

    @GetMapping("/followers/{usuarioId}")
    public ResponseEntity<List<UsuarioDTO>> followers(@PathVariable UUID usuarioId) {
        List<UsuarioDTO> lista = usuarioService.listarSeguidores(usuarioId);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/following/{usuarioId}")
    public ResponseEntity<List<UsuarioDTO>> following(@PathVariable UUID usuarioId) {
        List<UsuarioDTO> lista = usuarioService.listarSeguidos(usuarioId);
        return ResponseEntity.ok(lista);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        List<UsuarioDTO> lista = usuarioService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable UUID id) {
       UsuarioDTO usuarioDTO = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuarioDTO);
    }


}
