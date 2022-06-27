package br.com.model.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.model.dao.EspecializacaoDao;
import br.com.model.entities.Especializacao;

public class EspecializacaoDaoHiber implements EspecializacaoDao{

	private EntityManagerFactory emf;

	public EspecializacaoDaoHiber(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public void insert(Especializacao obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		System.out.println(obj.getIdEspeci());
		entityManager.persist(obj);
		transaction.commit();
	}

	@Override
	public void update(Especializacao obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(obj);
		transaction.commit();
	}

	@Override
	public void delete(Especializacao obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(entityManager.find(Especializacao.class, obj.getIdEspeci()));
		transaction.commit();
	}

	@Override
	public Especializacao findById(int i) {
		EntityManager entityManager = emf.createEntityManager();
		Especializacao obj = new Especializacao();
		obj = entityManager.find(Especializacao.class, i);
		return obj;
	}

	@Override
	public List<Especializacao> findAll() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tb_especializacao.* FROM tb_especializacao " + "ORDER BY nome_especializacao");
		EntityManager entityManager = emf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());
		@SuppressWarnings("unchecked")
		List<Object[]> especResultSet = query.getResultList();
		List<Especializacao> list = new ArrayList<>();
		if (!especResultSet.isEmpty()) {
			for (Object[] o : especResultSet) {

				Especializacao espec;
				try {
					espec = instatiateEspecializacao(o);
					list.add(espec);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	private Especializacao instatiateEspecializacao(Object[] o) throws SQLException {
		Especializacao espec = new Especializacao();
		espec.setIdEspeci(Integer.parseInt(o[0].toString()));
		espec.setNomeEspeci(o[1].toString());
		return espec;
	}
}
