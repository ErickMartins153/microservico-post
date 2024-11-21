package br.upe.base.controllers;

import br.upe.base.models.DTOs.ComentarioCreationDTO;
import br.upe.base.models.DTOs.ComentarioDTO;
import br.upe.base.services.ComentarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comentarios")
@AllArgsConstructor
public class ComentarioController {

    private ComentarioService comentarioService;

    @GetMapping("/{idComentario}")
    public ResponseEntity<ComentarioDTO> getComentarioById(@RequestParam UUID idComentario) {
        ComentarioDTO comentario = comentarioService.getComentarioById(idComentario);
        return ResponseEntity.ok().body(comentario);
    }

    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> getAllComentarios(){
        List<ComentarioDTO> comentarios = comentarioService.listAllComentarios();
        return ResponseEntity.ok().body(comentarios);
    }

    @PostMapping
    public ResponseEntity<ComentarioDTO> createComentario(@RequestBody ComentarioCreationDTO comentarioDTO){
        ComentarioDTO comentario = comentarioService.saveComentario(comentarioDTO);
        return ResponseEntity.ok().body(comentario);
    }

    @PutMapping("/{idComentario/curtidas}")
    public ResponseEntity<ComentarioDTO> updateComentario(@RequestParam UUID idComentario,
                                                          @RequestBody ComentarioDTO comentarioDTO){
        ComentarioDTO comentario = comentarioService.updateComentario(idComentario, comentarioDTO);
        return ResponseEntity.ok().body(comentario);
    }

    @PutMapping("/{idComentario}/curtidas")
    public ResponseEntity<ComentarioDTO> addCurtida(@RequestParam UUID idComentario){
        ComentarioDTO comentario = comentarioService.addCurtida(idComentario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{idComentario}")
    public ResponseEntity<ComentarioDTO> removeCurtida(@RequestParam UUID idComentario){
        ComentarioDTO comentario = comentarioService.removeCurtida(idComentario);
        return ResponseEntity.ok().body(comentario);
    }

    @DeleteMapping("/{idComentario}")
    public ResponseEntity deleteComentario(@RequestParam UUID idComentario){
        comentarioService.deleteComentario(idComentario);
        return ResponseEntity.noContent().build();
    }
}