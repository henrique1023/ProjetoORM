package br.com.model.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.model.dao.ConsultaDao;
import br.com.model.entities.Consulta;
import br.com.model.entities.Paciente;
import br.com.model.entities.Profissional;
import br.com.model.services.PacienteService;
import br.com.model.services.ProfissionalService;

public class ConsultaDaoJDBC implements ConsultaDao {

	private EntityManagerFactory emf;

	public ConsultaDaoJDBC(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public void insert(Consulta obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(obj);
		transaction.commit();
	}

	@Override
	public void update(Consulta obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(obj);
		transaction.commit();
	}
	
	@Override
	public void deleteId(Consulta obj) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(entityManager.find(Profissional.class, obj.getIdConsulta()));
		transaction.commit();
	}

	@Override
	public Consulta findById(Consulta obj) {
		EntityManager entityManager = emf.createEntityManager();
		obj = entityManager.find(Consulta.class, obj.getIdConsulta());
		return obj;
	}

	private Consulta instatiateConsulta(Object[] o, Profissional pro, Paciente pac) {
		Consulta consu = new Consulta();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		consu.setIdConsulta(Integer.parseInt(o[0].toString()));
		String data = o[3].toString();
		data = data.substring(0, 19).replace('-', '/');
		try {
			consu.setDataConsul(sdf.parse(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		consu.setPaciente(pac);
		consu.setProfissional(pro);
		return consu;
	}

	@Override
	public List<Consulta> findAll() {
		StringBuffer sql = new StringBuffer();
		sql.append("Select id_consulta, id_paciente, id_profissional, data_consulta, deletado from tb_consulta ");
		sql.append("where deletado = 'F';");
		
		EntityManager entityManager = emf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());
		
		@SuppressWarnings("unchecked")
		List<Object[]> consultaRS = query.getResultList();
		List<Consulta> list = new ArrayList<>();
		Map<Integer, Profissional> mapPro = new HashMap<>();
		Map<Integer, Paciente> mapPac = new HashMap<>();
		
		if(!consultaRS.isEmpty()) {
			for (Object[] o : consultaRS) {
				Paciente pac = mapPac.get(Integer.parseInt(o[1].toString()));
				Profissional pro = mapPro.get(Integer.parseInt(o[2].toString()));
				if(pro == null) {
					ProfissionalService ser = new ProfissionalService();
					pro = ser.findById(Integer.parseInt(o[2].toString()));
					mapPro.put(Integer.parseInt(o[2].toString()), pro);
				}
				
				if(pac == null) {
					PacienteService ser = new PacienteService();
					pac = ser.findById(Integer.parseInt(o[1].toString()));
				}
				
				Consulta consu = new Consulta();
				
				consu = instatiateConsulta(o, pro, pac);
				list.add(consu);
			}
		}
		return list;
	}

	@Override
	public List<Consulta> findByFilter(String nome, String cpf, Date data) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id_consulta, a.id_paciente, a.id_profissional, a.data_consulta, a.deletado, b.nomePaciente, b.cpf ");
		sql.append("from tb_consulta as a inner join tb_paciente as b on a.id_paciente = b.id_paciente and ");
		sql.append("a.deletado = 'F' ");
		if(nome != null && !nome.trim().equals("")) {
			sql.append("and b.nomePaciente LIKE '" + nome + "%' ");
		}
		if(cpf != null && !cpf.trim().equals("")) {
			sql.append("AND b.cpf = "+ cpf + " ");
		}
		
		EntityManager entityManager = emf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());
		
		@SuppressWarnings("unchecked")
		List<Object[]> consultaRS = query.getResultList();
		List<Consulta> list = new ArrayList<>();
		Map<Integer, Profissional> mapPro = new HashMap<>();
		Map<Integer, Paciente> mapPac = new HashMap<>();
		
		if(!consultaRS.isEmpty()) {
			for (Object[] o : consultaRS) {
				Paciente pac = mapPac.get(Integer.parseInt(o[1].toString()));
				Profissional pro = mapPro.get(Integer.parseInt(o[2].toString()));
				if(pro == null) {
					ProfissionalService ser = new ProfissionalService();
					pro = ser.findById(Integer.parseInt(o[2].toString()));
					mapPro.put(Integer.parseInt(o[2].toString()), pro);
				}
				
				if(pac == null) {
					PacienteService ser = new PacienteService();
					pac = ser.findById(Integer.parseInt(o[1].toString()));
				}
				
				Consulta consu = new Consulta();
				
				consu = instatiateConsulta(o, pro, pac);
				list.add(consu);
			}
		}
		return list;
	}

}
