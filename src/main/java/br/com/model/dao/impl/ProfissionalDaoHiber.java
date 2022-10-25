package br.com.model.dao.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.model.dao.ProfissionalDao;
import br.com.model.entities.Especializacao;
import br.com.model.entities.Profissional;
import br.com.model.services.EspecializacaoService;

public class ProfissionalDaoHiber implements ProfissionalDao {

	private EntityManagerFactory emf;

	public ProfissionalDaoHiber(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public void insert(Profissional obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(obj);
		transaction.commit();
	}

	@Override
	public void update(Profissional obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(obj);
		transaction.commit();
	}

	@Override
	public void delete(Profissional obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(entityManager.find(Profissional.class, obj.getIdProfi()));
		transaction.commit();
	}

	@Override
	public Profissional findById(Integer id) {
		Profissional obj = new Profissional();
		EntityManager entityManager = emf.createEntityManager();
		obj = entityManager.find(Profissional.class, id);
		return obj;
	}

	@Override
	public List<Profissional> findByNome(String nome) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tb_profissional.*, tb_especializacao.nome_especializacao as EspecNome ");
		sql.append("FROM tb_profissional INNER JOIN tb_especializacao ");
		sql.append("ON tb_profissional.id_especializacao = tb_especializacao.id_especializacao ");
		sql.append("WHERE tb_profissional.nome LIKE '" + nome + "%'");
		EntityManager entityManager = emf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());
		@SuppressWarnings("unchecked")
		List<Object[]> profissionalResultSet = query.getResultList();
		List<Profissional> list = new ArrayList<>();
		Map<Integer, Especializacao> map = new HashMap<>();

		if (!profissionalResultSet.isEmpty()) {
			for (Object[] o : profissionalResultSet) {
				Especializacao espec = map.get(Integer.parseInt(o[5].toString()));

				if (espec == null) {
					espec = instatiateEspecializacao(o);
					map.put(Integer.parseInt(o[5].toString()), espec);
				}

				Profissional prof;
				try {
					prof = instatiateProfissional(o, espec);
					list.add(prof);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<Profissional> findAll() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tb_profissional.*, tb_especializacao.nome_especializacao as EspecNome ");
		sql.append("FROM tb_profissional INNER JOIN tb_especializacao ");
		sql.append("ON tb_profissional.id_especializacao = tb_especializacao.id_especializacao ORDER BY id_profissional;");
		EntityManager entityManager = emf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());
		@SuppressWarnings("unchecked")
		List<Object[]> profissionalResultSet = query.getResultList();
		List<Profissional> list = new ArrayList<>();
		Map<Integer, Especializacao> map = new HashMap<>();

		if (!profissionalResultSet.isEmpty()) {
			for (Object[] o : profissionalResultSet) {
				Especializacao espec = map.get(Integer.parseInt(o[5].toString()));

				if (espec == null) {
					espec = instatiateEspecializacao(o);
					map.put(Integer.parseInt(o[5].toString()), espec);
				}

				Profissional prof;
				try {
					prof = instatiateProfissional(o, espec);
					list.add(prof);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<Profissional> findByEspecializacao(Especializacao especializao) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tb_profissional.*, tb_especializacao.nome_especializacao as EspecNome "
				+ "FROM tb_profissional INNER JOIN tb_especializacao "
				+ "ON tb_profissional.id_especializacao = tb_especializacao.id_especializacao"
				+ "WHERE id_especializacao = ? " + "ORDER BY EspecNome");
		EntityManager entityManager = emf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());
		@SuppressWarnings("unchecked")
		List<Object[]> profissionalResultSet = query.getResultList();
		List<Profissional> list = new ArrayList<>();
		Map<Integer, Especializacao> map = new HashMap<>();

		if (!profissionalResultSet.isEmpty()) {
			for (Object[] o : profissionalResultSet) {
				Especializacao espec = map.get(Integer.parseInt(o[5].toString()));

				if (espec == null) {
					espec = instatiateEspecializacao(o);
					map.put(Integer.parseInt(o[5].toString()), espec);
				}

				Profissional prof;
				try {
					prof = instatiateProfissional(o, espec);
					list.add(prof);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	private Profissional instatiateProfissional(Object[] o, Especializacao espec) throws SQLException {
		Profissional prof = new Profissional();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		prof.setIdProfi(Integer.parseInt(o[0].toString()));
		prof.setNome(o[3].toString());
		prof.setEmail(o[2].toString());
		prof.setSalarioBase(Double.parseDouble(o[4].toString()));
		String data = o[1].toString();
		data = data.substring(0, 19).replace('-', '/');
		try {
			prof.setDataAniver(sdf.parse(data));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prof.setEspecializacao(espec);
		return prof;
	}

	private Especializacao instatiateEspecializacao(Object[] o) {
		EspecializacaoService service = new EspecializacaoService();
		Especializacao espec = service.findById(Integer.parseInt(o[5].toString()));
		return espec;
	}
}
