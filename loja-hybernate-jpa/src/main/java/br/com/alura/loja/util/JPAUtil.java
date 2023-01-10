package br.com.alura.loja.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

// Objetivo é isolar a criação do entity manager
public class JPAUtil {
	private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("loja");

	// Insert no banco de dados utilizando o EntityManager

	public static EntityManager getEntityManager() {
		return FACTORY.createEntityManager();
	}
}
