package dao;

import java.util.ArrayList;
import java.util.List;

import modelo.*;

import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;

public class DAOCandidatura {
	private SessionFactory fabrica;

	public DAOCandidatura() {
		fabrica = Conexao.obtemFabrica();
	}

	public void cadastrar(Candidatura c) throws Exception {
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.save(c);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}
	
	public void alterar(Candidatura c) throws Exception {
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.update(c);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}
	
	public Candidatura consultar(Candidatura c) throws Exception {
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Candidatura.class).add(
				Restrictions.eq("idCandidatura", c.getIdCandidatura()));
		c = (Candidatura) cr.uniqueResult();
		sessao.flush();
		sessao.close();
		return c;
	}
	
	public ArrayList consultarCandidaturasAprovar() throws Exception {
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Candidatura.class)
							.add(Restrictions.isNull("supervisorAceite"));
		cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList lista = (ArrayList) cr.list();
		sessao.flush();
		sessao.close();
		return lista;
	}
	
	public boolean verificaID(Candidatura c) throws Exception {
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Candidatura.class)
							.add(Restrictions.eq("c", c.getC()))
							.add(Restrictions.eq("op", c.getOp()));
		Candidatura u = (Candidatura) cr.uniqueResult();
		sessao.flush();
		sessao.close();
		if (u == null)
			return true;
		else
			return false;
	}
	
	public Candidatura consultarCanUsuario(Colaborador c, Oportunidade op) throws Exception {
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Candidatura.class)
							.add(Restrictions.eq("c", c))
							.add(Restrictions.eq("op", op));
		Candidatura can = (Candidatura) cr.uniqueResult();
		sessao.flush();
		sessao.close();
		return can;
	}
	
	public ArrayList consultarCandidaturasParaEntrevista() throws Exception
	{
		/*String sql = "select u.id_usuario as idUsuario, u.nm_usuario as nome, u.nr_cpf as cpf, " +
					 "u.tp_usuario as tipo from usuario u, candidatura c where c.c_id_usuario = " +
					 "u.id_usuario and c.nm_status = 'Aguardando entrevista'";
			
		Session sessao = fabrica.openSession();
		SQLQuery query = sessao.createSQLQuery(sql);
		query.addScalar("idUsuario");
		query.addScalar("nome");
		query.addScalar("cpf");
		query.addScalar("tipo");
		query.setResultTransformer(Transformers.aliasToBean(Usuario.class));
		List lista = query.list();
		sessao.flush();
		sessao.close();
		return (ArrayList)lista;*/
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Candidatura.class)
							.add(Restrictions.eq("status", "Aguardando entrevista"));
		cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList lista = (ArrayList) cr.list();
		sessao.flush();
		sessao.close();
		return lista;
	}
}
