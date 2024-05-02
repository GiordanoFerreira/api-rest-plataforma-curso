package br.com.giordanoferreira.apirestcursos.model.dto.pedido;

import lombok.*;

import java.util.List;

@Data
public class AtualizacaoPedidoDTO {

    private List<Long> cursosAdicionados;
    private List<Long> cursosRemovidos;
}
