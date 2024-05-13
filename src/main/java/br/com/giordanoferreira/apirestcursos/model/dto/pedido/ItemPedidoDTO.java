package br.com.giordanoferreira.apirestcursos.model.dto.pedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ItemPedidoDTO {

    private Long cursoId;

    @Positive
    private BigDecimal preco;
}
