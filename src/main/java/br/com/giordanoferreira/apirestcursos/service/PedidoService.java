package br.com.giordanoferreira.apirestcursos.service;

import br.com.giordanoferreira.apirestcursos.model.dto.pedido.AtualizacaoPedidoDTO;
import br.com.giordanoferreira.apirestcursos.model.dto.pedido.ItemPedidoDTO;
import br.com.giordanoferreira.apirestcursos.model.dto.pedido.PedidoCadastroDTO;
import br.com.giordanoferreira.apirestcursos.model.dto.pedido.PedidoResponseDTO;
import br.com.giordanoferreira.apirestcursos.model.entity.Curso;
import br.com.giordanoferreira.apirestcursos.model.entity.CursoPedido;
import br.com.giordanoferreira.apirestcursos.model.entity.Pedido;
import br.com.giordanoferreira.apirestcursos.model.entity.Usuario;
import br.com.giordanoferreira.apirestcursos.model.enums.StatusPedido;
import br.com.giordanoferreira.apirestcursos.model.exception.CursoNaoEncontradoException;
import br.com.giordanoferreira.apirestcursos.model.exception.PedidoNaoEncontradoException;
import br.com.giordanoferreira.apirestcursos.model.exception.UsuarioNaoEncontradoException;
import br.com.giordanoferreira.apirestcursos.repository.CursoPedidoRepository;
import br.com.giordanoferreira.apirestcursos.repository.CursoRepository;
import br.com.giordanoferreira.apirestcursos.repository.PedidoRepository;
import br.com.giordanoferreira.apirestcursos.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoPedidoRepository cursoPedidoRepository;

    @Transactional
    public Pedido criarPedido(PedidoCadastroDTO pedidoDTO) {
        Long idUsuario = pedidoDTO.getUsuarioId();
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setTotal(calcularTotal(pedidoDTO));

        List<CursoPedido> cursosPedidos = new ArrayList<>();

        for (ItemPedidoDTO item : pedidoDTO.getItens()) {
            Curso curso = cursoRepository.findById(item.getCursoId())
                    .orElseThrow(CursoNaoEncontradoException::new);

            CursoPedido cursoPedido = new CursoPedido();
            cursoPedido.setCurso(curso);
            cursoPedido.setPedido(pedido);

            cursosPedidos.add(cursoPedido);
        }
        pedido.setCursos(cursosPedidos);

        cursoPedidoRepository.saveAll(cursosPedidos);
        pedidoRepository.save(pedido);

        return pedido;
    }

    public List<PedidoResponseDTO> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        return pedidos.stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Pedido concluirPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));

        verificaPedido(pedido);
        pedido.setStatus(StatusPedido.CONCLUIDO);
        pedidoRepository.save(pedido);

        Usuario usuario = pedido.getUsuario();

        List<CursoPedido> cursosPedidos = pedido.getCursos();
        for (CursoPedido cursoPedido : cursosPedidos) {
            usuario.adicionarCurso(cursoPedido.getCurso());
        }

        usuarioRepository.save(usuario);

        return pedido;
    }

    @Transactional
    public void cancelarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));

        if (pedido.getStatus() == StatusPedido.CONCLUIDO) {
            throw new IllegalStateException("O pedido não pode ser deletado por que está no status CONCLUIDO.");
        }

        cursoPedidoRepository.deleteByPedidoId(pedidoId);

        pedidoRepository.delete(pedido);
    }

    @Transactional
    public Pedido atualizarPedido(Long pedidoId, AtualizacaoPedidoDTO pedidoDTO){
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));

        List<CursoPedido> cursoPedidos = pedido.getCursos();

        if (pedidoDTO.getCursosAdicionados() != null) {
            for (Long cursoId : pedidoDTO.getCursosAdicionados()) {
                Curso curso = cursoRepository.findById(cursoId)
                        .orElseThrow(CursoNaoEncontradoException::new);

                CursoPedido cursoPedido = new CursoPedido();
                cursoPedido.setCurso(curso);
                cursoPedido.setPedido(pedido);

                cursoPedidoRepository.save(cursoPedido);

                cursoPedidos.add(cursoPedido);
            }
        }

        if (pedidoDTO.getCursosRemovidos() != null) {
            cursoPedidos.removeIf(cursoPedido -> pedidoDTO.getCursosRemovidos()
                    .contains(cursoPedido.getCurso().getId()));
        }

        BigDecimal totalPedido = atualizarTotalPedido(pedido);
        pedido.setTotal(totalPedido);
        pedidoRepository.save(pedido);

        return pedido;
    }

    public BigDecimal calcularTotal(PedidoCadastroDTO dto) {
        BigDecimal totalPedido = BigDecimal.ZERO;

        for (ItemPedidoDTO item : dto.getItens()) {
            BigDecimal precoItem = item.getPreco();
            totalPedido = totalPedido.add(precoItem);
        }

        return totalPedido;
    }

    public void verificaPedido(Pedido pedido) {
        if (!pedido.getStatus().equals(StatusPedido.REALIZADO)) {
            throw new IllegalStateException("O pedido não pode ser concluído porque não está no status REALIZADO.");
        }
    }

    public BigDecimal atualizarTotalPedido(Pedido pedido) {
        List<CursoPedido> cursosPedidos = pedido.getCursos();
        BigDecimal totalPedido = BigDecimal.ZERO;

        for (CursoPedido cursoPedido : cursosPedidos) {
            BigDecimal precoUnitario = cursoPedido.getCurso().getPreco();
            totalPedido = totalPedido.add(precoUnitario);
        }

        return totalPedido;
    }
}
