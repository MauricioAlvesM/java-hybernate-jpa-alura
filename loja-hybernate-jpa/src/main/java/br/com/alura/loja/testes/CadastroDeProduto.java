package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProduto {

	public static void main(String[] args) {
		cadastrarProduto();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);

		Produto p = produtoDao.buscarPorId(1l);
		System.out.println(p.getPreco());

		List<Produto> buscarTodos = produtoDao.buscarTodos();
		buscarTodos.forEach(p2 -> System.out.println("Buscando todos " + p.getNome()));

		List<Produto> buscarTodos2 = produtoDao.buscarPorNome("G PRO X Superlight");
		buscarTodos2.forEach(p2 -> System.out.println("Consulta Buscando pelo nome " + p.getNome()));

		List<Produto> buscarTodos3 = produtoDao.buscarPorNomeDaCategoria("PERIFERICOS");
		buscarTodos3.forEach(p2 -> System.out.println("Consulta Buscando por categoria" + p.getNome()));
		
		BigDecimal precoDoProduto = produtoDao.buscarPrecoDoProdutoComNome("G PRO X Superlight");
		System.out.println("Preço do Produto =  R$:"+precoDoProduto);
	}

	private static void cadastrarProduto() {
		Categoria perifericos = new Categoria("PERIFERICOS");
		Produto mouse = new Produto("G PRO X Superlight", "Mouse Sem Fio, Lightspeed, 5 Botões, 25000 DPI, Branco",
				new BigDecimal("799.99"), perifericos);

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		CategoriaDAO categoriaDao = new CategoriaDAO(em);

		em.getTransaction().begin();

		categoriaDao.cadastrar(perifericos);
		produtoDao.cadastrar(mouse);

		em.getTransaction().commit();
		em.close();
	}

}
