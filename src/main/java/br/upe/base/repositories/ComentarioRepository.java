package br.upe.base.repositories;

import br.upe.base.models.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComentarioRepository extends JpaRepository<Comentario, UUID> {
}
