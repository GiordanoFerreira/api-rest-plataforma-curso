package br.com.giordanoferreira.apirestcursos.model.dto.pedido;

import br.com.giordanoferreira.apirestcursos.model.entity.Curso;
import br.com.giordanoferreira.apirestcursos.model.entity.CursoPedido;
import br.com.giordanoferreira.apirestcursos.model.entity.Pedido;
import br.com.giordanoferreira.apirestcursos.model.enums.StatusPedido;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoResponseDTO {

    private Long id;
    private Long usuarioId;
    private List<Long> cursosIds;
    private BigDecimal totalPedido;
    private LocalDateTime dataPedido;
    private StatusPedido status;

    public PedidoResponseDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.usuarioId = pedido.getUsuario().getId();

        List<Long> cursosIds = new ArrayList<>();
        for (CursoPedido cursoPedido : pedido.getCursos()) {
            cursosIds.add(cursoPedido.getCurso().getId());
        }

        this.cursosIds = cursosIds;
        this.totalPedido = pedido.getTotal();
        this.dataPedido = pedido.getDataPedido();
        this.status = pedido.getStatus();
    }
}
