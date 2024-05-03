package br.com.giordanoferreira.apirestcursos.model.entity;

import br.com.giordanoferreira.apirestcursos.model.dto.usuario.AtualizacaoUsuarioDTO;
import br.com.giordanoferreira.apirestcursos.model.dto.usuario.CadastroUsuarioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50)
    private String nome;

    @Column(length = 11, unique = true)
    private String cpf;

    @Column(length = 40, unique = true)
    private String email;

    @Column(length = 50)
    private String senha;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "usuario_curso",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id"))
    private List<Curso> cursos;

    private boolean ativo = false;

    public Usuario(CadastroUsuarioDTO dto) {
        this.nome = dto.getNome();
        this.cpf = dto.getCpf();
        this.email = dto.getEmail();
        this.senha = dto.getSenha();
    }

    public void atualizaInformacoes(AtualizacaoUsuarioDTO dto) {
        if (dto.getNome() != null) {
            this.nome = dto.getNome();
        }
        if (dto.getCpf() != null) {
            this.cpf = dto.getCpf();
        }
        if (dto.getEmail() != null) {
            this.email = dto.getEmail();
        }
    }

    public void adicionarCurso(Curso curso) {
        if (cursos == null) {
            cursos = new ArrayList<>();
        }
        cursos.add(curso);
    }
}
