package br.com.giordanoferreira.apirestcursos.model.dto.curso;

import br.com.giordanoferreira.apirestcursos.model.entity.Curso;
import br.com.giordanoferreira.apirestcursos.model.enums.Categoria;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ResponseCursoDTO {

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String instrutor;
    private Integer duracao;
    private List<String> preRequisitos;
    private Categoria categoria;

    public ResponseCursoDTO(Curso curso) {
        this.nome = curso.getNome();
        this.descricao = curso.getDescricao();
        this.preco = curso.getPreco();
        this.instrutor = curso.getInstrutor();
        this.duracao = curso.getDuracao();
        this.preRequisitos = curso.getPreRequisitos();
        this.categoria = curso.getCategoria();
    }
}
