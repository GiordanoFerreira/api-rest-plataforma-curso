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
import br.com.giordanoferreira.apirestcursos.repository.CursoPedidoRepository;
import br.com.giordanoferreira.apirestcursos.repository.CursoRepository;
import br.com.giordanoferreira.apirestcursos.repository.PedidoRepository;
import br.com.giordanoferreira.apirestcursos.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CursoPedidoRepository cursoPedidoRepository;

    private Usuario usuarioMock;
    private Curso curso1Mock;
    private Curso curso2Mock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setNome("Usuário Mock");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));

        curso1Mock = new Curso();
        curso1Mock.setId(1L);
        curso1Mock.setNome("Curso Mock 1");
        curso1Mock.setPreco(BigDecimal.valueOf(50.00));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso1Mock));

        curso2Mock = new Curso();
        curso2Mock.setId(2L);
        curso2Mock.setNome("Curso Mock 2");
        curso2Mock.setPreco(BigDecimal.valueOf(100.00));
        when(cursoRepository.findById(2L)).thenReturn(Optional.of(curso2Mock));
    }

    @Test
    public void testCriarPedido() {
        PedidoCadastroDTO pedidoDTO = new PedidoCadastroDTO();
        pedidoDTO.setUsuarioId(usuarioMock.getId());
        List<ItemPedidoDTO> itens = new ArrayList<>();
        itens.add(new ItemPedidoDTO(curso1Mock.getId(), BigDecimal.TEN));
        pedidoDTO.setItens(itens);

        Pedido pedido = pedidoService.criarPedido(pedidoDTO);

        assertNotNull(pedido);
        assertNotNull(pedido.getStatus());
        assertEquals(StatusPedido.REALIZADO, pedido.getStatus());

        assertEquals(itens.size(), pedido.getCursos().size());
    }

    @Test
    public void testListarPedidos() {
        Pedido pedido1Mock = criarPedidoTeste(1L);
        Pedido pedido2Mock = criarPedidoTeste(2L);

        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido1Mock, pedido2Mock));

        List<PedidoResponseDTO> pedidos = pedidoService.listarPedidos();

        assertNotNull(pedidos);
        assertEquals(2, pedidos.size());
    }

    @Test
    public void testConcluirPedido() {
        Pedido pedido1Mock = criarPedidoTeste(1L);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido1Mock));

        Pedido pedidoConcluido = pedidoService.concluirPedido(1L);

        assertNotNull(pedidoConcluido);
        assertNotNull(pedidoConcluido.getStatus());
        assertEquals(StatusPedido.CONCLUIDO, pedidoConcluido.getStatus());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void testCancelarPedido() {
        Pedido pedidoMock = criarPedidoTeste(1L);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoMock));

        pedidoService.cancelarPedido(1L);

        verify(pedidoRepository, times(1)).delete(pedidoMock);
    }

    @Test
    public void testAtualizarPedido() {
        Pedido pedidoMock = criarPedidoTeste(1L);

        Curso curso3Mock = new Curso();
        curso3Mock.setId(3L);
        curso3Mock.setNome("Curso Mock 3");
        curso3Mock.setPreco(BigDecimal.valueOf(150.00));

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoMock));

        when(cursoRepository.findById(3L)).thenReturn(Optional.of(curso3Mock));

        AtualizacaoPedidoDTO atualizacaoDTO = new AtualizacaoPedidoDTO();
        atualizacaoDTO.setCursosAdicionados(Collections.singletonList(3L));
        atualizacaoDTO.setCursosRemovidos(Collections.singletonList(2L));

        Pedido pedidoAtualizado = pedidoService.atualizarPedido(1L, atualizacaoDTO);

        assertNotNull(pedidoAtualizado);
        assertEquals(2, pedidoAtualizado.getCursos().size());
        assertEquals(curso3Mock, pedidoAtualizado.getCursos().get(1).getCurso());
    }

    private Pedido criarPedidoTeste(Long id) {
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setDataPedido(LocalDateTime.now());

        Usuario usuario = usuarioMock;

        Curso curso1 = curso1Mock;
        Curso curso2 = curso2Mock;

        CursoPedido cursoPedido1 = new CursoPedido();
        cursoPedido1.setCurso(curso1);
        cursoPedido1.setPedido(pedido);

        CursoPedido cursoPedido2 = new CursoPedido();
        cursoPedido2.setCurso(curso2);
        cursoPedido2.setPedido(pedido);

        List<CursoPedido> cursosPedidos = new ArrayList<>();
        cursosPedidos.add(cursoPedido1);
        cursosPedidos.add(cursoPedido2);

        pedido.setUsuario(usuario);
        pedido.setCursos(cursosPedidos);

        return pedido;
    }
}