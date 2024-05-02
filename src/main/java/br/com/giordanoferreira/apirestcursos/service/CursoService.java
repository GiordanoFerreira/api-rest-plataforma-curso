package br.com.giordanoferreira.apirestcursos.service;

import br.com.giordanoferreira.apirestcursos.model.dto.curso.CadastroCursoDTO;
import br.com.giordanoferreira.apirestcursos.model.dto.curso.ResponseCursoDTO;
import br.com.giordanoferreira.apirestcursos.model.entity.Curso;
import br.com.giordanoferreira.apirestcursos.model.enums.Categoria;
import br.com.giordanoferreira.apirestcursos.model.exception.CursoNaoEncontradoException;
import br.com.giordanoferreira.apirestcursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public Curso salvarCurso(CadastroCursoDTO cursoDTO) {
        Curso cursoSalvo = new Curso(cursoDTO);
        cursoSalvo.setDataCadastro(LocalDateTime.now());

        return cursoRepository.save(cursoSalvo);
    }

    public List<ResponseCursoDTO> listarCursos() {
        List<Curso> cursos = cursoRepository.findAll();

        return cursos.stream()
                .map(ResponseCursoDTO::new)
                .collect(Collectors.toList());
    }

    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(CursoNaoEncontradoException::new);
    }

    public Curso atualizarCurso(Long id, Curso curso) {
        return cursoRepository.findById(id)
                .map(cursoExistente -> {
                    curso.setId(cursoExistente.getId());
                    curso.setDataCadastro(cursoExistente.getDataCadastro());
                    curso.setDataAtualizacao(LocalDateTime.now());
                    cursoRepository.save(curso);
                    return cursoExistente;
                }).orElseThrow(CursoNaoEncontradoException::new);
    }

    public void deletarCurso(Long id) {
        cursoRepository.findById(id)
                .map(curso -> {
                    cursoRepository.delete(curso);
                    return Void.TYPE;
                }).orElseThrow(CursoNaoEncontradoException::new);
    }

    public List<ResponseCursoDTO> listarCursosPorCategoria(Categoria categoria) {
        List<Curso> cursos = cursoRepository.findByCategoria(categoria);

        return cursos.stream()
                .map(ResponseCursoDTO::new)
                .collect(Collectors.toList());
    }

    public List<ResponseCursoDTO> filtroPesquisaCurso(Curso filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Curso> example = Example.of(filtro, matcher);

        List<Curso> cursos = cursoRepository.findAll(example);

        return cursos.stream()
                .map(ResponseCursoDTO::new)
                .collect(Collectors.toList());
    }
}
