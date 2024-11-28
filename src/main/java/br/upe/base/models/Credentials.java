package br.upe.base.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class Credentials {
    private String email;
    private String senha;
}
