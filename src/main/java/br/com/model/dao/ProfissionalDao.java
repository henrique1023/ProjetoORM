package br.com.model.dao;

import java.util.List;

import br.com.model.entities.Especializacao;
import br.com.model.entities.Profissional;

public interface ProfissionalDao {
	void insert(Profissional obj);
	void update(Profissional obj);
	void delete(Profissional obj);
	Profissional findById(Profissional obj);
	List<Profissional> findByNome(String nome);
	List<Profissional> findAll();
	List<Profissional> findByEspecializacao(Especializacao especializacao);
}
