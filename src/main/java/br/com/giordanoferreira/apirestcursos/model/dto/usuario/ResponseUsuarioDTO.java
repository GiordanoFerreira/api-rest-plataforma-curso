package br.com.giordanoferreira.apirestcursos.model.dto.usuario;

import br.com.giordanoferreira.apirestcursos.model.entity.Usuario;
import lombok.*;

@Data
public class ResponseUsuarioDTO {

    private String nome;
    private String cpf;
    private String email;

    public ResponseUsuarioDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        this.cpf = usuario.getCpf();
        this.email = usuario.getEmail();
    }
}
