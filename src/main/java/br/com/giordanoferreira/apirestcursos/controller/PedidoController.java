package br.com.giordanoferreira.apirestcursos.controller;

import br.com.giordanoferreira.apirestcursos.model.dto.pedido.AtualizacaoPedidoDTO;
import br.com.giordanoferreira.apirestcursos.model.dto.pedido.PedidoCadastroDTO;
import br.com.giordanoferreira.apirestcursos.model.dto.pedido.PedidoResponseDTO;
import br.com.giordanoferreira.apirestcursos.model.entity.Pedido;
import br.com.giordanoferreira.apirestcursos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Salvar", description = "Metodo que salva um registro de Pedido", tags = "Pedidos")
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody @Valid PedidoCadastroDTO pedidoDTO) {
        Pedido pedido = pedidoService.criarPedido(pedidoDTO);
        PedidoResponseDTO responseDTO = new PedidoResponseDTO(pedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Operation(summary = "Listar", description = "Metodo que lista todos os registros de Pedido", tags = "Pedidos")
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        List<PedidoResponseDTO> pedidos = pedidoService.listarPedidos();

        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Concluir Pedido", description = "Metodo que conclui e atualiza um registro de Pedido", tags = "Pedidos")
    @PatchMapping(path = "/concluir-pedido/{id}")
    public ResponseEntity<PedidoResponseDTO> concluirPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.concluirPedido(id);
        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO(pedido);

        return ResponseEntity.ok(pedidoResponseDTO);
    }

    @Operation(summary = "Atualizar", description = "Metodo que atualiza um registro de Pedido", tags = "Pedidos")
    @PatchMapping(path = "/atualizar-pedido/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizarPedido(@PathVariable Long id, @RequestBody AtualizacaoPedidoDTO pedidoDTO){
        Pedido pedido = pedidoService.atualizarPedido(id, pedidoDTO);
        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO(pedido);

        return ResponseEntity.ok(pedidoResponseDTO);
    }

    @Operation(summary = "Deletar", description = "Metodo que cancela e deleta um registro de Pedido", tags = "Pedidos")
    @DeleteMapping(path = "/cancelar-pedido/{id}")
    public ResponseEntity cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
