package br.com.model.dao;

import java.util.List;

import br.com.model.entities.Consulta;


public interface ConsultaDao {

	void insert(Consulta obj);

	void update(Consulta obj);
	
	void deleteId(Consulta obj);

	Consulta findById(Consulta obj);

	List<Consulta> findAll();
}
