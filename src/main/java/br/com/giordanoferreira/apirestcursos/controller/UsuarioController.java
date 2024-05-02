package br.com.giordanoferreira.apirestcursos.controller;

import br.com.giordanoferreira.apirestcursos.model.dto.curso.ResponseCursoDTO;
import br.com.giordanoferreira.apirestcursos.model.dto.usuario.AtualizacaoUsuarioDTO;
import br.com.giordanoferreira.apirestcursos.model.dto.usuario.CadastroUsuarioDTO;
import br.com.giordanoferreira.apirestcursos.model.dto.usuario.ResponseUsuarioDTO;
import br.com.giordanoferreira.apirestcursos.model.entity.Usuario;
import br.com.giordanoferreira.apirestcursos.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Salvar", description = "Metodo que salva um registro de Usuario", tags = "Usuarios")
    @PostMapping
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody @Valid CadastroUsuarioDTO usuario) {
        Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @Operation(summary = "Listar", description = "Metodo que lista todos os registros de Usuario", tags = "Usuarios")
    @GetMapping
    public ResponseEntity<List<ResponseUsuarioDTO>> listarUsuarios() {
        List<ResponseUsuarioDTO> usuarios = usuarioService.listarUsuarios();

        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Buscar por Id", description = "Metodo que retorna um registro de Usuario por Id", tags = "Usuarios")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);

        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Atualizar", description = "Metodo que atualiza um registro salvo de Usuario", tags = "Usuarios")
    @PutMapping(path = "/{id}")
    public ResponseEntity<ResponseUsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid AtualizacaoUsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.atualizarUsuario(id, usuarioDTO);
        ResponseUsuarioDTO responseUsuarioDTO = new ResponseUsuarioDTO(usuario);

        return ResponseEntity.ok(responseUsuarioDTO);
    }

    @Operation(summary = "Deletar", description = "Metodo que deleta um registro de Usuario", tags = "Usuarios")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletarUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Desativar", description = "Metodo que desativa um registro de Usuario", tags = "Usuarios")
    @PatchMapping(path = "/desativar-usuario/{id}")
    public ResponseEntity<Usuario> desativarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.desativarUsuario(id);

        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Listar Cursos", description = "Metodo que lista todos os cursos de Usuario", tags = "Usuarios")
    @GetMapping(path = "/cursos/{id}")
    public ResponseEntity<List<ResponseCursoDTO>> listarCursosUsuario(@PathVariable Long id) {
        List<ResponseCursoDTO> cursosUsuario = usuarioService.listarCursosUsuario(id);

        return ResponseEntity.ok(cursosUsuario);
    }
}
