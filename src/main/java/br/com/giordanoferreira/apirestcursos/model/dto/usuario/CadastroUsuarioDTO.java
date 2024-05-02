package br.com.giordanoferreira.apirestcursos.model.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class CadastroUsuarioDTO {

    @NotEmpty(message = "O campo nome é obrigatório!")
    private String nome;

    @CPF
    @NotNull(message = "O campo CPF é obrigatório!")
    private String cpf;

    @Email
    @NotEmpty(message = "O campo email é obrigatório!")
    private String email;

    @NotNull(message = "O campo senha é obrigatório!")
    private String senha;
}
