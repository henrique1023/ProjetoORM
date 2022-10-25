package br.com.model.dao.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.model.dao.PacienteDao;
import br.com.model.entities.Paciente;

public class PacienteDaoHiber implements PacienteDao {

	private EntityManagerFactory emf;

	public PacienteDaoHiber(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public void insert(Paciente obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		System.out.println(obj.getIdPaciente());
		entityManager.persist(obj);
		transaction.commit();
	}

	@Override
	public void update(Paciente obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(obj);
		transaction.commit();
	}
	
	//para deletar corretamente seguindo o ID do objeto se usa o find dentro do remove
	//assim ele persiste o item
	@Override
	public void delete(Paciente obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(entityManager.find(Paciente.class, obj.getIdPaciente()));
		transaction.commit();
	}

	@Override
	public Paciente findById(Integer id) {
		Paciente obj = new Paciente();
		EntityManager entityManager = emf.createEntityManager();
		obj = entityManager.find(Paciente.class, id);
		return obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Paciente> findAll() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tb_paciente.* FROM tb_paciente " + "ORDER BY nomePaciente");
		EntityManager entityManager = emf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> PacienteResultSet = query.getResultList();
		List<Paciente> list = new ArrayList<>();
		if (!PacienteResultSet.isEmpty()) {
			for (Object[] o : PacienteResultSet) {

				Paciente paciente;
				try {
					paciente = instatiatePaciente(o);
					list.add(paciente);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	private Paciente instatiatePaciente(Object[] o) throws SQLException {
		Paciente paciente = new Paciente();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		paciente.setIdPaciente(Integer.parseInt(o[0].toString()));
		paciente.setNomePaciente(o[3].toString());
		String data = o[2].toString();
		data = data.substring(0, 19).replace('-', '/');
		try {
			paciente.setDataAniversario(sdf.parse(data));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		paciente.setCpf(o[1].toString());
		if (o[4] != null) {
			paciente.setTelefone(o[4].toString());
		} else {
			paciente.setTelefone(null);
		}
		return paciente;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Paciente> findByNome(String nome) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tb_paciente.* FROM tb_paciente WHERE nomePaciente LIKE '" + nome + "%'" + "ORDER BY nomePaciente");
		EntityManager entityManager = emf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> PacienteResultSet = query.getResultList();
		List<Paciente> list = new ArrayList<>();
		for (Object[] o : PacienteResultSet) {
			Paciente paciente;
			try {
				paciente = instatiatePaciente(o);
				list.add(paciente);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

}
