package br.com.alura.loja.testes;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class PerformanceConsultas {

	public static void main(String[] args) {
		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		PedidoDAO pedidoDao = new PedidoDAO(em);
		Pedido pedido = pedidoDao.buscarPedidoComCliente(1l);

		em.close();
		System.out.println(pedido.getCliente().getNome());

	}

	private static void popularBancoDeDados() {

		/* CRIANDO CATEGORIAS */
		Categoria mouses = new Categoria("MOUSE");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria headsets = new Categoria("HEADSETS");

		/* CRIANDO PRODUTOS */
		Produto mouse = new Produto("G PRO X Superlight", "Mouse Sem Fio, Lightspeed, 5 Botões, 25000 DPI, Branco",
				new BigDecimal("799.99"), mouses);
		Produto videogame = new Produto("Playstation 5", "PlayStation®5 Edição Digital", new BigDecimal("3.998"),
				videogames);
		Produto headset = new Produto("Logitech G", "Logitech G Headset Gamer G335", new BigDecimal("324.99"),
				headsets);

		/* CRIANDO CLIENTE */
		Cliente cliente = new Cliente("Mauricio", "123.123.123-90");

		Pedido pedido = new Pedido(cliente);

		/* ADICIONANDO PEDIDOS */
		pedido.adicionarItem(new ItemPedido(150, pedido, mouse));
		pedido.adicionarItem(new ItemPedido(40, pedido, videogame));

		Pedido pedido2 = new Pedido(cliente);
		pedido2.adicionarItem(new ItemPedido(378, pedido, headset));

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		CategoriaDAO categoriaDao = new CategoriaDAO(em);
		ClienteDAO clienteDao = new ClienteDAO(em);
		PedidoDAO pedidoDao = new PedidoDAO(em);

		em.getTransaction().begin();

		/* SALVANDO CATEGORIA NO BANCO DE DADOS */
		categoriaDao.cadastrar(mouses);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(headsets);

		/* SALVANDO PRODUTO NO BANCO DE DADOS */
		produtoDao.cadastrar(mouse);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(headset);

		/* SALVANDO CLIENTE NO BANCO DE DADOS */
		clienteDao.cadastrar(cliente);

		/* SALVANDO PEDIDOS NO BANCO DE DADOS */
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);

		em.getTransaction().commit();
		em.close();
	}

}
