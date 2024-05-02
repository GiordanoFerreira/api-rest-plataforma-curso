package br.com.giordanoferreira.apirestcursos.model.dto.pedido;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
public class PedidoCadastroDTO {

    @NotNull(message = "Usuário não encontrado ou inválido!")
    private Long usuarioId;

    @NotNull(message = "O campo itens é obrigatório!")
    private List<ItemPedidoDTO> itens;
}
