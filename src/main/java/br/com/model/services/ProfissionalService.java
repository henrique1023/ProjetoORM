package br.com.model.services;

import java.util.List;

import br.com.model.dao.DaoFactory;
import br.com.model.dao.ProfissionalDao;
import br.com.model.entities.Profissional;

public class ProfissionalService {

	private ProfissionalDao dao = DaoFactory.createProfissionalDao();

	public List<Profissional> findAll() {
		return dao.findAll();
	}

	// esse metodo que salva no banco o objeto passado
	public void saveOrUptade(Profissional obj) {
		if (obj.getIdProfi() == null) {
			dao.insert(obj);
		} else {
			dao.update(obj);
		}
	}

	public void remove(Profissional obj) {
		dao.delete(obj);
	}

	public List<Profissional> findByNome(String nome) {
		return dao.findByNome(nome);
	}
	
	public Profissional findById(Integer id) {
		return dao.findById(id);
	}
}
