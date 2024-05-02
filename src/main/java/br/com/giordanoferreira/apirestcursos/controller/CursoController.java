package br.com.giordanoferreira.apirestcursos.controller;

import br.com.giordanoferreira.apirestcursos.model.dto.curso.CadastroCursoDTO;
import br.com.giordanoferreira.apirestcursos.model.dto.curso.ResponseCursoDTO;
import br.com.giordanoferreira.apirestcursos.model.entity.Curso;
import br.com.giordanoferreira.apirestcursos.model.enums.Categoria;
import br.com.giordanoferreira.apirestcursos.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Operation(summary = "Salvar", description = "Metodo que salva um registro de Curso", tags = "Cursos")
    @PostMapping
    public ResponseEntity<Curso> salvarCurso(@RequestBody @Valid CadastroCursoDTO curso) {
        Curso cursoSalvo = cursoService.salvarCurso(curso);

        return ResponseEntity.status(HttpStatus.CREATED).body(cursoSalvo);
    }

    @Operation(summary = "Listar", description = "Metodo que lista todos os registros de Curso", tags = "Cursos")
    @GetMapping
    public ResponseEntity<List<ResponseCursoDTO>> listarCurso() {
        List<ResponseCursoDTO> cursos = cursoService.listarCursos();

        return ResponseEntity.ok(cursos);
    }

    @Operation(summary = "Buscar por Id", description = "Metodo que retorna um registro de Curso pelo Id", tags = "Cursos")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {
        Curso curso = cursoService.buscarPorId(id);

        return ResponseEntity.ok(curso);
    }

    @Operation(summary = "Atualizar", description = "Metodo que atualiza um registro salvo de Curso", tags = "Cursos")
    @PutMapping(path = "/{id}")
    public ResponseEntity<Curso> atualizarCurso(@PathVariable Long id, @RequestBody Curso cursoDTO) {
        Curso curso = cursoService.atualizarCurso(id, cursoDTO);

        return ResponseEntity.ok(curso);
    }

    @Operation(summary = "Deletar", description = "Metodo que deleta um registro de Curso", tags = "Cursos")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletarCurso(@PathVariable Long id) {
        cursoService.deletarCurso(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Listar por Categoria", description = "Metodo que lista todos os registros de Curso por Categoria",
            tags = "Cursos")
    @GetMapping(path = "/pesquisa-categoria")
    public ResponseEntity<List<ResponseCursoDTO>> listarCursosPorCategoria(@RequestParam("categoria") Categoria categoria) {
        List<ResponseCursoDTO> cursos = cursoService.listarCursosPorCategoria(categoria);

        return ResponseEntity.ok(cursos);
    }

    @Operation(summary = "Pesquisa por Curso", description = "Metodo que faz uma pesquisa de um registro de Curso",
            tags = "Cursos")
    @GetMapping(path = "/pesquisa-curso")
    public ResponseEntity<List<ResponseCursoDTO>> filtroPesquisaCurso(Curso filtro) {
        List<ResponseCursoDTO> cursos = cursoService.filtroPesquisaCurso(filtro);

        return ResponseEntity.ok(cursos);
    }
}
