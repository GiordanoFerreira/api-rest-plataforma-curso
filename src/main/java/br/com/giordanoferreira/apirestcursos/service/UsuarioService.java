package br.com.giordanoferreira.apirestcursos.service;

import br.com.giordanoferreira.apirestcursos.model.dto.curso.ResponseCursoDTO;
import br.com.giordanoferreira.apirestcursos.model.dto.usuario.AtualizacaoUsuarioDTO;
import br.com.giordanoferreira.apirestcursos.model.dto.usuario.CadastroUsuarioDTO;
import br.com.giordanoferreira.apirestcursos.model.dto.usuario.ResponseUsuarioDTO;
import br.com.giordanoferreira.apirestcursos.model.entity.Curso;
import br.com.giordanoferreira.apirestcursos.model.entity.Usuario;
import br.com.giordanoferreira.apirestcursos.model.exception.UsuarioNaoEncontradoException;
import br.com.giordanoferreira.apirestcursos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(CadastroUsuarioDTO dto) {
        Usuario usuarioDTO = new Usuario(dto);
        usuarioDTO.setAtivo(true);
        usuarioDTO.setDataCadastro(LocalDateTime.now());

        return usuarioRepository.save(usuarioDTO);
    }

    public List<ResponseUsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(ResponseUsuarioDTO::new)
                .collect(Collectors.toList());
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(UsuarioNaoEncontradoException::new);
    }

    public Usuario atualizarUsuario(Long id, AtualizacaoUsuarioDTO usuarioDTO) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.atualizaInformacoes(usuarioDTO);
                    usuario.setDataAtualizacao(LocalDateTime.now());
                    usuarioRepository.save(usuario);
                    return usuario;
                }).orElseThrow(UsuarioNaoEncontradoException::new);
    }

    public void excluirUsuario(Long id) {
        usuarioRepository.findById(id)
                .map(usuario -> {
                    usuarioRepository.delete(usuario);
                    return Void.TYPE;
                }).orElseThrow(UsuarioNaoEncontradoException::new);
    }

    public Usuario desativarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setAtivo(false);
                    usuarioRepository.saveAndFlush(usuario);
                    return usuario;
                }).orElseThrow(UsuarioNaoEncontradoException::new);
    }

    public List<ResponseCursoDTO> listarCursosUsuario(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        List<Curso> cursos = usuario.getCursos();

        return cursos.stream()
                .map(ResponseCursoDTO::new)
                .collect(Collectors.toList());
    }
}
