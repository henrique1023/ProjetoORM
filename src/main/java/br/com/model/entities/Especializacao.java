package br.com.model.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_especializacao")
public class Especializacao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_especializacao")
	private Integer idEspeci;
	
	@Column(name = "nome_especializacao")
	private String nomeEspeci;
	
	public Especializacao() {
	}

	public Especializacao(Integer idEspeci, String nomeEspeci) {
		super();
		this.idEspeci = idEspeci;
		this.nomeEspeci = nomeEspeci;
	}

	public Integer getIdEspeci() {
		return idEspeci;
	}

	public void setIdEspeci(Integer idEspeci) {
		this.idEspeci = idEspeci;
	}

	public String getNomeEspeci() {
		return nomeEspeci;
	}

	public void setNomeEspeci(String nomeEspeci) {
		this.nomeEspeci = nomeEspeci;
	}

	@Override
	public String toString() {
		return  nomeEspeci ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idEspeci);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Especializacao other = (Especializacao) obj;
		return Objects.equals(idEspeci, other.idEspeci);
	}
	

}
