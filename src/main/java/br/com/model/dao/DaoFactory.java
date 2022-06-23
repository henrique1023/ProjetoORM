package br.com.model.dao;

import br.com.db.DB;
import br.com.model.dao.impl.ConsultaDaoJDBC;
import br.com.model.dao.impl.DiagnosticoDaoJDBC;
import br.com.model.dao.impl.EspecializacaoDaoJDBC;
import br.com.model.dao.impl.PacienteDaoHiber;
import br.com.model.dao.impl.ProfissionalDaoJDBC;

public class DaoFactory {
	
	public static DiagnosticoDao createDiagnosticoDao() {
		return new DiagnosticoDaoJDBC(DB.getConnection());
	}
	
	public static ConsultaDao createConsultaDao() {
		return new ConsultaDaoJDBC(DB.getConnection());
	}
	
	public static PacienteDao createPacienteDao() {
		return new PacienteDaoHiber(DB.getSession());
	}
	
	public static ProfissionalDao createProfissionalDao() {
		return new ProfissionalDaoJDBC(DB.getConnection());
	}
	
	public static EspecializacaoDao createEspecializacaoDao() {
		return new EspecializacaoDaoJDBC(DB.getConnection());
	}
}
