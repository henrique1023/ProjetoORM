package br.com.db;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import br.com.model.entities.Consulta;
import br.com.model.entities.Diagnostico;
import br.com.model.entities.Especializacao;
import br.com.model.entities.Paciente;
import br.com.model.entities.Profissional;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();

				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, "jdbc:mysql://localhost:3306/projetoengenharia?createDatabaseIfNotExist=true");
				settings.put(Environment.USER, "developer");
				settings.put(Environment.PASS, "1234567");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
				settings.put(Environment.SHOW_SQL, "false");
				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				settings.put(Environment.HBM2DDL_AUTO, "update");

				configuration.setProperties(settings);
				configuration.addAnnotatedClass(Consulta.class);
				configuration.addAnnotatedClass(Diagnostico.class);
				configuration.addAnnotatedClass(Especializacao.class);
				configuration.addAnnotatedClass(Paciente.class);
				configuration.addAnnotatedClass(Profissional.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}
