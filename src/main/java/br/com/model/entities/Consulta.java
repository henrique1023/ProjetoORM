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
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_consulta")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idConsulta;
	
	@ManyToOne
	@JoinColumn(name = "id_paciente")
	private Paciente paciente;
	
	@ManyToOne
	@JoinColumn(name = "id_profissional")
	private Profissional profissional;
	
	@Column(name = "data_consulta")
	private Date dataConsul;
	
	@Column(name = "deletado", columnDefinition = "varchar(1) default 'F'" )
	private String deletado = "F";
	
	@Column(name = "horario_consulta")
	private Integer horario;
	
	public Consulta() {
	}

	public Consulta(Integer idConsulta, Paciente paciente, Profissional profissional,
			Date dataConsul, String deletado, Integer horario) {
		super();
		this.idConsulta = idConsulta;
		this.paciente = paciente;
		this.profissional = profissional;
		this.dataConsul = dataConsul;
		this.deletado = deletado;
		this.horario = horario;
	}

	public Integer getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(Integer idConsulta) {
		this.idConsulta = idConsulta;
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

	public String getDeletado() {
		return deletado;
	}

	public void setDeletado(String deletado) {
		this.deletado = deletado;
	}

	public Integer getHorario() {
		return horario;
	}

	public void setHorario(Integer horario) {
		this.horario = horario;
	}

	@Override
	public String toString() {
		return "Consulta [idConsulta=" + idConsulta + ", paciente=" + paciente
				+ ", profissional=" + profissional + ", dataConsul=" + dataConsul + ", deletado=" + deletado + "]";
	}
	

}
