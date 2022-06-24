package br.com.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_paciente")
public class Paciente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_paciente")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer IdPaciente;
	
	private String nomePaciente;
	private Date dataAniversario;
	private String cpf;
	private String telefone;

	public Paciente() {
	}

	public Paciente(Integer idPaciente, String nomePaciente, Date dataAniversario, String cpf, String telefone) {
		super();
		IdPaciente = idPaciente;
		this.nomePaciente = nomePaciente;
		this.dataAniversario = dataAniversario;
		this.cpf = cpf;
		this.telefone = telefone;
	}

	public Integer getIdPaciente() {
		return IdPaciente;
	}

	public void setIdPaciente(Integer idPaciente) {
		IdPaciente = idPaciente;
	}

	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public Date getDataAniversario() {
		return dataAniversario;
	}

	public void setDataAniversario(Date dataAniversario) {
		this.dataAniversario = dataAniversario;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		return "Paciente [IdPaciente=" + IdPaciente + ", nomePaciente=" + nomePaciente + ", dataAniversario="
				+ dataAniversario + ", cpf=" + cpf + ", Telefone=" + telefone + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(IdPaciente, cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paciente other = (Paciente) obj;
		return Objects.equals(IdPaciente, other.IdPaciente) && Objects.equals(cpf, other.cpf);
	}

	
	

}
