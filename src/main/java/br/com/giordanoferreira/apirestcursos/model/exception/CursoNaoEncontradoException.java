package br.com.giordanoferreira.apirestcursos.model.exception;

public class CursoNaoEncontradoException extends RuntimeException {

    public CursoNaoEncontradoException() {
        super("Curso não encontrado!");
    }
}
