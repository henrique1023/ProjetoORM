package br.com.model.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_diagnostico")
public class Diagnostico implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_diagnostico")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idDiag;
	
	@Column(name = "nome_diagnostico")
	private String nomeDiag;
	
	public Diagnostico() {
	}

	public Diagnostico(Integer idDiag, String nomeDiag) {
		super();
		this.idDiag = idDiag;
		this.nomeDiag = nomeDiag;
	}

	public Integer getIdDiag() {
		return idDiag;
	}

	public void setIdDiag(Integer idDiag) {
		this.idDiag = idDiag;
	}

	public String getNomeDiag() {
		return nomeDiag;
	}

	public void setNomeDiag(String nomeDiag) {
		this.nomeDiag = nomeDiag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idDiag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Diagnostico other = (Diagnostico) obj;
		return Objects.equals(idDiag, other.idDiag);
	}
	
	

}
