package dao;

import java.util.ArrayList;

import modelo.Questao;
import modelo.Requisito;
import modelo.Usuario;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

public class DAOQuestao {
	private SessionFactory fabrica;

	public DAOQuestao() {
		fabrica = Conexao.obtemFabrica();
	}

	public void cadastrar(Questao q) throws Exception {
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.save(q);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}
	
	public void alterar(Questao q) throws Exception {
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.update(q);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}

	public void excluir(Questao q) throws Exception {
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.delete(q);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}
	
	public Questao consultar(Questao q) throws Exception
	{
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Questao.class)
							.add(Restrictions.eq("idQuestao",q.getIdQuestao()));
		q = (Questao) cr.uniqueResult();
		sessao.flush();
		sessao.close();
		return q;
	}
	
	public ArrayList consultarTodas() throws Exception
	{
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Questao.class);
		ArrayList lista = (ArrayList) cr.list();
		sessao.flush();
		sessao.close();
		return lista;
	}
	
	public boolean verificaID(Questao q) throws Exception 
	{
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Questao.class)
							.add(Restrictions.eq("idQuestao", q.getIdQuestao()));
		q = (Questao) cr.uniqueResult();
		sessao.flush();
		sessao.close();
		if(q==null)
			return true;
		else
			return false;	
	}
	
	public ArrayList consultarGerais() throws Exception
	{
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Questao.class)
							.add(Restrictions.eq("status", "A"))
							.add(Restrictions.eq("tipo", "G"))
							.add(Restrictions.sqlRestriction("1=1 order by random()"));
		cr.setMaxResults(5);
		ArrayList lista = (ArrayList) cr.list();
		sessao.flush();
		sessao.close();
		return lista;
	}
	
	public ArrayList consultarEspecificas(String areaAtuacao) throws Exception
	{
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Questao.class)
							.add(Restrictions.eq("status", "A"))
							.add(Restrictions.eq("tipo", "E"))
							.add(Restrictions.eq("areaAtuacao", areaAtuacao))
							.add(Restrictions.sqlRestriction("1=1 order by random()"));
		cr.setMaxResults(3);
		ArrayList lista = (ArrayList) cr.list();
		sessao.flush();
		sessao.close();
		return lista;
	}
}
