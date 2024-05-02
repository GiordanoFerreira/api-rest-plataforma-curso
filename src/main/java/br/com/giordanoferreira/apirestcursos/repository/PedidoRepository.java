package br.com.giordanoferreira.apirestcursos.repository;

import br.com.giordanoferreira.apirestcursos.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
