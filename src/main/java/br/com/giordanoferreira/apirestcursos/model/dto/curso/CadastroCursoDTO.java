package br.com.giordanoferreira.apirestcursos.model.dto.curso;

import br.com.giordanoferreira.apirestcursos.model.enums.Categoria;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CadastroCursoDTO {

    @NotEmpty(message = "Campo nome é obrigatório!")
    private String nome;

    @NotEmpty(message = "Campo descrição é obrigatório!")
    private String descricao;

    @NotNull(message = "Campo preço é obrigatório!")
    @Positive
    private BigDecimal preco;

    @NotEmpty(message = "Campo instrutor é obrigatório!")
    private String instrutor;

    @NotNull(message = "Campo duração é obrigatório!")
    private Integer duracao;
    private List<String> preRequisitos;

    @NotNull(message = "Campo categoria é obrigatório!")
    private Categoria categoria;
}
