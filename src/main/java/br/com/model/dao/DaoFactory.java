package br.com.model.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.model.dao.impl.DiagnosticoDaoHiber;
import br.com.model.dao.impl.EspecializacaoDaoHiber;
import br.com.model.dao.impl.PacienteDaoHiber;
import br.com.model.dao.impl.ProfissionalDaoHiber;

public class DaoFactory {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
	
	public static DiagnosticoDao createDiagnosticoDao() {
		return new DiagnosticoDaoHiber(emf);
	}
	
	public static ConsultaDao createConsultaDao() {
		return null;
	}
	
	public static PacienteDao createPacienteDao() {
		return new PacienteDaoHiber(emf);
	}
	
	public static ProfissionalDao createProfissionalDao() {
		return new ProfissionalDaoHiber(emf);
	}
	
	public static EspecializacaoDao createEspecializacaoDao() {
		return new EspecializacaoDaoHiber(emf);
	}
}
