package br.upe.base.models.DTOs;

import jakarta.validation.constraints.NotNull;

public record UsuarioCreationDTO(
        @NotNull(message = "o NOME do usuário não pode ser nulo")
        String nome,
        @NotNull(message = "o EMAIL do usuário não pode ser nulo")
        String email,
        @NotNull(message = "a SENHA do usuário não pode ser nula")
        String senha
){
}
