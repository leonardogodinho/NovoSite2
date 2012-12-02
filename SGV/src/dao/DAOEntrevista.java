package dao;

import java.util.ArrayList;

import modelo.*;

import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.IdGenerator;

public class DAOEntrevista {
	private SessionFactory fabrica;

	public DAOEntrevista() {
		fabrica = Conexao.obtemFabrica();
	}

	public void cadastrar(Entrevista e) throws Exception {
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.save(e);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}
	
	public void excluir(Entrevista e) throws Exception {
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.delete(e);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}
	
	public Entrevista consultar(Entrevista e) throws Exception {
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Entrevista.class).add(
				Restrictions.eq("idEntrevista", e.getIdEntrevista()));
		e = (Entrevista) cr.uniqueResult();
		sessao.flush();
		sessao.close();
		return e;
	}
	
	public boolean verificaID(Entrevista e) throws Exception {
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Entrevista.class)
							.add(Restrictions.eq("can", e.getCan()));
		e = (Entrevista) cr.uniqueResult();
		sessao.flush();
		sessao.close();
		if (e == null)
			return true;
		else
			return false;
	}
	
	public Entrevista consultarPorCandidatura(Candidatura c) throws Exception {
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Entrevista.class).add(
							Restrictions.eq("can", c));
		Entrevista e = (Entrevista) cr.uniqueResult();
		sessao.flush();
		sessao.close();
		return e;
	}
	
	public ArrayList consultarEntrevistasAprovar(Usuario u) throws Exception 
	{
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Entrevista.class)
							.add(Restrictions.eq("gerente", u));
		cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList lista = (ArrayList) cr.list();
		sessao.flush();
		sessao.close();
		return lista;
	}
}
