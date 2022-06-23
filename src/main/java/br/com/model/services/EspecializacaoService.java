package br.com.model.services;

import java.util.List;

import br.com.model.dao.DaoFactory;
import br.com.model.dao.EspecializacaoDao;
import br.com.model.entities.Especializacao;

public class EspecializacaoService {

	private EspecializacaoDao dao = DaoFactory.createEspecializacaoDao();
	
	public List<Especializacao> findAll(){
		return dao.findAll();
	}
	
	//esse metodo que salva no banco o objeto passado
	public void saveOrUptade(Especializacao obj) {
		if(obj.getIdEspeci() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(Especializacao obj) {
		dao.deleteById(obj.getIdEspeci());
	}
}
