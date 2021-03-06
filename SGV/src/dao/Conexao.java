package dao;

import modelo.*;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class Conexao {
	
	private static SessionFactory fabrica;
	
	private Conexao()
	{
		if(fabrica == null)
			fabrica = new AnnotationConfiguration()
							.addAnnotatedClass(Requisito.class)
							.addAnnotatedClass(Usuario.class)
							.addAnnotatedClass(Colaborador.class)
							.addAnnotatedClass(ItemRequisito.class)
							.addAnnotatedClass(Oportunidade.class)
							.addAnnotatedClass(Questao.class)
							.addAnnotatedClass(Itens.class)
							.addAnnotatedClass(Candidatura.class)
							.addAnnotatedClass(Avaliacao.class)
							.addAnnotatedClass(Entrevista.class)
							.addAnnotatedClass(RelColaborador.class)
							.buildSessionFactory();
	}
	
	public static SessionFactory obtemFabrica()
	{
		new Conexao();
		return fabrica;
	}

}
