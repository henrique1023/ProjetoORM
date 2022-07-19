package br.com.model.dao;

import java.util.List;

import br.com.model.entities.Diagnostico;

public interface DiagnosticoDao {
	
	void insert(Diagnostico obj);
	void update(Diagnostico obj);
	void deleteById(Diagnostico obj);
	Diagnostico findById(Integer id);
	List<Diagnostico> findAll();
}
