package br.com.model.services;

import java.util.Date;
import java.util.List;

import br.com.model.dao.ConsultaDao;
import br.com.model.dao.DaoFactory;
import br.com.model.entities.Consulta;

public class ConsultaService {

	private ConsultaDao dao = DaoFactory.createConsultaDao();
	
	public List<Consulta> findAll(){
		return dao.findAll();
	}
	
	//esse metodo que salva no banco o objeto passado
	public void saveOrUptade(Consulta obj) {
		if(obj.getIdConsulta() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(Consulta obj) {
		dao.deleteId(obj);
	}
	
	public List<Consulta> findByCampos(String nome, String cpf, Date data) {
		return dao.findByFilter(nome, cpf, data);
	}
}
