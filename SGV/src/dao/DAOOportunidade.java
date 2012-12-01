package dao;

import java.util.ArrayList;
import java.util.List;

import modelo.ItemRequisito;
import modelo.Itens;
import modelo.Oportunidade;
import modelo.Requisito;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import com.sun.xml.internal.bind.v2.model.core.ID;

public class DAOOportunidade {

	private SessionFactory fabrica;
	
	public DAOOportunidade()
	{
		fabrica = Conexao.obtemFabrica();
	}
	
	public void cadastrar(Oportunidade op) throws Exception
	{
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.save(op);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}
	
	public void cadastrarItemRequisito(ItemRequisito ir) throws Exception
	{
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.save(ir);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}
	
	public Oportunidade consultar(Oportunidade o) throws Exception
	{
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Oportunidade.class)
							.add(Restrictions.eq("idOportunidade",o.getIdOportunidade()));
		o = (Oportunidade) cr.uniqueResult();
		sessao.flush();
		sessao.close();
		return o;
	}
	
	public ArrayList consultarDisponiveis() throws Exception
	{
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Oportunidade.class)
							.add(Restrictions.isNull("dataEncerramento"));
		ArrayList lista = (ArrayList)cr.list();
		sessao.flush();
		sessao.close();
		return lista;
	}
	
	public ArrayList listarRequisitos(int idOportunidade) throws Exception
	{
		String sql = "select r.nm_requisito, ir.nr_quantidade " +
		"from itemrequisito ir, itemop io, requisito r, oportunidade op " +
		"where ir.r_id_requisito = r.id_requisito " +
		"and ir.id_itemrequisito = io.id_itemrequisito " +
		"and io.id_oportunidade = op.id_oportunidade " +
		"and op.id_oportunidade = " + String.valueOf(idOportunidade);
		
		Session sessao = fabrica.openSession();
		SQLQuery query = sessao.createSQLQuery(sql);
		query.addScalar("nm_requisito");
		query.addScalar("nr_quantidade");
		//query.setParameter("id", idOportunidade);
		//query.addEntity(Itens.class);
		query.setResultTransformer(Transformers.aliasToBean(Itens.class));
		List lista = query.list();
		sessao.flush();
		sessao.close();
		return (ArrayList)lista;
	}
}
