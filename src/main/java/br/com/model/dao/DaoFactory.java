package br.com.model.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.db.DB;
import br.com.model.dao.impl.ConsultaDaoJDBC;
import br.com.model.dao.impl.DiagnosticoDaoJDBC;
import br.com.model.dao.impl.EspecializacaoDaoHiber;
import br.com.model.dao.impl.PacienteDaoHiber;
import br.com.model.dao.impl.ProfissionalDaoHiber;

public class DaoFactory {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
	
	public static DiagnosticoDao createDiagnosticoDao() {
		return new DiagnosticoDaoJDBC(DB.getConnection());
	}
	
	public static ConsultaDao createConsultaDao() {
		return new ConsultaDaoJDBC(DB.getConnection());
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
