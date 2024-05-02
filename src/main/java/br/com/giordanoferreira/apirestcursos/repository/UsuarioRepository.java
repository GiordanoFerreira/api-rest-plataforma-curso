package br.com.giordanoferreira.apirestcursos.repository;

import br.com.giordanoferreira.apirestcursos.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
