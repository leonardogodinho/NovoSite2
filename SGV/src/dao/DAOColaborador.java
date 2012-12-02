package dao;

import java.util.ArrayList;
import java.util.List;

import modelo.*;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

public class DAOColaborador {

	private SessionFactory fabrica;

	public DAOColaborador() {
		fabrica = Conexao.obtemFabrica();
	}

	public void cadastrar(Colaborador c) throws Exception {
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.save(c);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}

	public void alterar(Colaborador c) throws Exception {
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.update(c);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}

	public void excluir(Colaborador c) throws Exception {
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.delete(c);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}

	public Colaborador consultar(Colaborador c) throws Exception {
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Colaborador.class).add(
				Restrictions.eq("idUsuario", c.getIdUsuario()));
		c = (Colaborador) cr.uniqueResult();
		sessao.flush();
		sessao.close();
		return c;
	}

	public boolean verificaID(Colaborador c) throws Exception {
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Usuario.class).add(
				Restrictions.eq("idUsuario", c.getIdUsuario()));
		Usuario u = (Usuario) cr.uniqueResult();
		sessao.flush();
		sessao.close();
		if (u == null)
			return true;
		else
			return false;
	}
	
	public ArrayList consultarColaboradoresProcesso() throws Exception
	{
		String sql = "select u.nr_cpf, u.nm_usuario, u.nm_departamento, o.nm_oportunidade, " +
					 "c.nm_status from usuario u, oportunidade o, candidatura c " +
					 "where c.op_id_oportunidade = o.id_oportunidade and c.c_id_usuario = " +
					 "u.id_usuario and c.in_aprovado is null";
		
		Session sessao = fabrica.openSession();
		SQLQuery query = sessao.createSQLQuery(sql);
		query.addScalar("nr_cpf");
		query.addScalar("nm_usuario");
		query.addScalar("nm_departamento");
		query.addScalar("nm_oportunidade");
		query.addScalar("nm_status");
		query.setResultTransformer(Transformers.aliasToBean(RelColaborador.class));
		List lista = query.list();
		sessao.flush();
		sessao.close();
		return (ArrayList)lista;
	}

}
