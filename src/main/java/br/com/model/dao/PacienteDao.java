package br.com.model.dao;

import java.util.List;

import br.com.model.entities.Paciente;

public interface PacienteDao {
	void insert(Paciente obj);
	void update(Paciente obj);
	void delete(Paciente obj);
	Paciente findById(Integer id);
	List<Paciente> findByNome(String nome);
	List<Paciente> findAll();
}
