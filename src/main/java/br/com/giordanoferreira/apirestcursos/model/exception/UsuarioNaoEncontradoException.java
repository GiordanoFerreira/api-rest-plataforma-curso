package br.com.giordanoferreira.apirestcursos.model.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException() {
        super("Usuário não encontrado!");
    }
}
