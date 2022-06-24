package br.com.model.dao;

import java.util.List;

import br.com.model.entities.Especializacao;

public interface EspecializacaoDao {
	void insert(Especializacao obj);
	void update(Especializacao obj);
	void delete(Especializacao obj);
	Especializacao findById(Especializacao obj);
	List<Especializacao> findAll();
}
