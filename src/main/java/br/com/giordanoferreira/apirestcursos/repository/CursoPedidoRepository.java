package br.com.giordanoferreira.apirestcursos.repository;

import br.com.giordanoferreira.apirestcursos.model.entity.CursoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoPedidoRepository extends JpaRepository<CursoPedido, Long> {
    void deleteByPedidoId(Long pedidoId);
}
