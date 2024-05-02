package br.com.giordanoferreira.apirestcursos.model.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class AtualizacaoUsuarioDTO {

    @Size(max = 50)
    private String nome;

    @CPF
    private String cpf;

    @Email
    private String email;
}
