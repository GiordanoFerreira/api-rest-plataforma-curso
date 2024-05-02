package br.com.giordanoferreira.apirestcursos.model.exception;

public class PedidoNaoEncontradoException extends RuntimeException {

    public PedidoNaoEncontradoException(Long pedidoId) {
        super("Pedido não foi encontrado com o id: " + pedidoId);
    }
}
