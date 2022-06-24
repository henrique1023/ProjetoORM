package br.com.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_consulta")
public class Consulta implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_consulta")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idConsulta;
	
	@ManyToOne
	@JoinColumn(name = "id_diagnostico")
	private Diagnostico diagnostico;
	
	@ManyToOne
	@JoinColumn(name = "id_paciente")
	private Paciente paciente;
	
	@ManyToOne
	@JoinColumn(name = "id_profissional")
	private Profissional profissional;
	
	@Id
	@Column(name = "data_consulta")
	private Date dataConsul;
	
	public Consulta() {
	}
	
	public Consulta(Integer idConsulta, Diagnostico diagnostico, Paciente paciente, Profissional profissional,
			Date dataConsul) {
		this.idConsulta = idConsulta;
		this.diagnostico = diagnostico;
		this.paciente = paciente;
		this.profissional = profissional;
		this.dataConsul = dataConsul;
	}

	public Integer getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(Integer idConsulta) {
		this.idConsulta = idConsulta;
	}

	public Diagnostico getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(Diagnostico diagnostico) {
		this.diagnostico = diagnostico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public Date getDataConsul() {
		return dataConsul;
	}

	public void setDataConsul(Date dataConsul) {
		this.dataConsul = dataConsul;
	}

}
