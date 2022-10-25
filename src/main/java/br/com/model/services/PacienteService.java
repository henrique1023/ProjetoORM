package br.com.model.services;

import java.util.List;

import br.com.model.dao.DaoFactory;
import br.com.model.dao.PacienteDao;
import br.com.model.entities.Paciente;

public class PacienteService {
	
	private PacienteDao dao = DaoFactory.createPacienteDao();
	
	public List<Paciente> findAll(){
		return dao.findAll();
	}
	
	//esse metodo que salva no banco o objeto passado
	public void saveOrUptade(Paciente obj) {
		if(obj.getIdPaciente() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(Paciente obj) {
		dao.delete(obj);
	}
	
	public List<Paciente> findByNome(String nome){
		return dao.findByNome(nome);
	}
	
	public Paciente findById(Integer id) {
		return dao.findById(id);
	}
}
