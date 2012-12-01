package dao;

import modelo.Usuario;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

public class DAOUsuario {

	private SessionFactory fabrica;

	public DAOUsuario() {
		fabrica = Conexao.obtemFabrica();
	}

	public void cadastrar(Usuario u) throws Exception {
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.save(u);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}

	public void alterar(Usuario u) throws Exception {
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.update(u);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}
	
	public void excluir(Usuario u) throws Exception {
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.delete(u);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}
	
	public Usuario consultar(Usuario u) throws Exception
	{
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Usuario.class)
						.add(Restrictions.eq("idUsuario",u.getIdUsuario()))
						.add(Restrictions.not(Restrictions.eq("tipo", "Colaborador")));
		u = (Usuario) cr.uniqueResult();
		sessao.flush();
		sessao.close();
		return u;
	}
	
	public Usuario consultarID(Usuario u) throws Exception
	{
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Usuario.class)
						.add(Restrictions.eq("cpf",u.getCpf()))
						.add(Restrictions.eq("senha",u.getSenha()));
		u = (Usuario) cr.uniqueResult();
		sessao.flush();
		sessao.close();
		return u;
	}
	
	public boolean verificaID(Usuario u) throws Exception 
	{
		Session sessao = fabrica.openSession();
		Criteria cr = sessao.createCriteria(Usuario.class)
							.add(Restrictions.eq("idUsuario", u.getIdUsuario()));
		u = (Usuario) cr.uniqueResult();
		if(u==null)
			return true;
		else
			return false;	
	}
	
}
