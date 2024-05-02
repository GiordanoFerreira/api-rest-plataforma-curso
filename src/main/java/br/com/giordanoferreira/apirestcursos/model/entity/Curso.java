package br.com.giordanoferreira.apirestcursos.model.entity;

import br.com.giordanoferreira.apirestcursos.model.dto.curso.CadastroCursoDTO;
import br.com.giordanoferreira.apirestcursos.model.enums.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50)
    private String nome;

    @Column(length = 200)
    private String descricao;

    @Column(name = "preco_unitario", precision = 20, scale = 2)
    private BigDecimal preco;

    private String instrutor;
    private Integer duracao;

    @Column(name = "pre_requisitos")
    private List<String> preRequisitos;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    public Curso(CadastroCursoDTO dto) {
        this.nome = dto.getNome();
        this.descricao = dto.getDescricao();
        this.preco = dto.getPreco();
        this.instrutor = dto.getInstrutor();
        this.duracao = dto.getDuracao();
        this.preRequisitos = dto.getPreRequisitos();
        this.categoria = dto.getCategoria();
        this.dataCadastro = LocalDateTime.now();
    }
}
