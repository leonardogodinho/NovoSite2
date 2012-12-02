package dao;

import java.util.ArrayList;
import java.util.List;

import modelo.ItemRequisito;
import modelo.Itens;
import modelo.Oportunidade;
import modelo.RelColaborador;
import modelo.Requisito;

import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
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
	
	public void alterar(Oportunidade op) throws Exception
	{
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.update(op);
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
	
		cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
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
	
	public ArrayList consultarRelatorioOportunidades() throws Exception
	{
		String sql = "select nm_oportunidade as titulo, vl_carga_horaria as cargaHoraria, " +
					 "nm_area_atuacao as areaAtuacao, vl_salario as salario, " +
					 "replace(replace(ds_oportunidade,'<p>',''),'</p>','') " +
					 "as descricao from oportunidade where dt_encerramento is null or dt_encerramento > now()";
		Session sessao = fabrica.openSession();
		SQLQuery query = sessao.createSQLQuery(sql);
		query.addScalar("titulo");
		query.addScalar("cargaHoraria");
		query.addScalar("areaAtuacao");
		query.addScalar("salario");
		query.addScalar("descricao");
		query.setResultTransformer(Transformers.aliasToBean(Oportunidade.class));
		List lista = query.list();
		sessao.flush();
		sessao.close();
		return (ArrayList)lista;		
	}
	
	public ArrayList consultarRelatorioPorData(String data) throws Exception
	{
		String sql = "select o.nm_oportunidade, u.nm_usuario from oportunidade o, " +
					 "usuario u, candidatura c where c.op_id_oportunidade = o.id_oportunidade " +
					 "and c.c_id_usuario = u.id_usuario and to_char(dt_encerramento,'yyyy-MM-dd') "+
					 "< '" + data + "'";
		Session sessao = fabrica.openSession();
		SQLQuery query = sessao.createSQLQuery(sql);
		query.addScalar("nm_oportunidade");
		query.addScalar("nm_usuario");
		query.setResultTransformer(Transformers.aliasToBean(RelColaborador.class));
		List lista = query.list();
		sessao.flush();
		sessao.close();
		return (ArrayList)lista;		
	}
}
