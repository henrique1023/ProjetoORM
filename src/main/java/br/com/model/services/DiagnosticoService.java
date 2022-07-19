package br.com.model.services;

import java.util.List;

import br.com.model.dao.DaoFactory;
import br.com.model.dao.DiagnosticoDao;
import br.com.model.entities.Diagnostico;

public class DiagnosticoService {

	private DiagnosticoDao dao = DaoFactory.createDiagnosticoDao();
	
	public List<Diagnostico> findAll(){
		return dao.findAll();
	}
	
	//esse metodo que salva no banco o objeto passado
	public void saveOrUptade(Diagnostico obj) {
		if(obj.getIdDiag() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(Diagnostico obj) {
		dao.deleteById(obj);
	}
}
