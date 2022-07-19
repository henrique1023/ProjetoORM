package br.com.model.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.model.dao.DiagnosticoDao;
import br.com.model.entities.Diagnostico;

public class DiagnosticoDaoHiber implements DiagnosticoDao{

	private EntityManagerFactory emf;

	public DiagnosticoDaoHiber(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	@Override
	public void insert(Diagnostico obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		System.out.println(obj.getIdDiag());
		entityManager.persist(obj);
		transaction.commit();
	}

	@Override
	public void update(Diagnostico obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(obj);
		transaction.commit();
	}

	@Override
	public void deleteById(Diagnostico obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(entityManager.find(Diagnostico.class, obj.getIdDiag()));
		transaction.commit();
	}

	@Override
	public Diagnostico findById(Integer id) {
		EntityManager entityManager = emf.createEntityManager();
		Diagnostico obj = new Diagnostico();
		obj = entityManager.find(Diagnostico.class, id);
		return obj;
	}

	@Override
	public List<Diagnostico> findAll() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tb_diagnostico.* FROM tb_diagnostico " + "ORDER BY nome_diagnostico");
		EntityManager entityManager = emf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());
		@SuppressWarnings("unchecked")
		List<Object[]> diagResultSet = query.getResultList();
		List<Diagnostico> list = new ArrayList<>();
		if (!diagResultSet.isEmpty()) {
			for (Object[] o : diagResultSet) {

				Diagnostico diag;
				try {
					diag = instatiateDiagnostico(o);
					list.add(diag);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	private Diagnostico instatiateDiagnostico(Object[] o) throws SQLException {
		Diagnostico diag = new Diagnostico();
		diag.setIdDiag(Integer.parseInt(o[0].toString()));
		diag.setNomeDiag(o[1].toString());
		return diag;
	}
}
