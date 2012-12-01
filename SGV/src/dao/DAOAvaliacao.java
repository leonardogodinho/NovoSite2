package dao;

import modelo.Avaliacao;

import org.hibernate.*;

public class DAOAvaliacao {
	private SessionFactory fabrica;

	public DAOAvaliacao() {
		fabrica = Conexao.obtemFabrica();
	}

	public void cadastrar(Avaliacao c) throws Exception {
		Session sessao = fabrica.openSession();
		Transaction transacao = sessao.beginTransaction();
		sessao.save(c);
		transacao.commit();
		sessao.flush();
		sessao.close();
	}
}
