package br.upe.base.models.DTOs;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;


public record UsuarioCreationDTO(
        @NotNull(message = "o ID do usuário não pode ser nulo")
        UUID id,
        @NotNull(message = "o NOME do usuário não pode ser nulo")
        String nome,
        @NotNull(message = "o EMAIL do usuário não pode ser nulo")
        String email,
        @NotNull(message = "a SENHA do usuário não pode ser nula")
        String senha
){
}
