package controle;

import java.io.IOException;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import dao.*;

import modelo.*;

public class Controle extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	public Controle() {
		super();
	}

	protected void doGet(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException 
	{
				
		String tela = req.getParameter("tela");
		
		if(tela.equals("Login"))
		{
			this.carregaLogin(req, res);
		}
		
		if(tela.equals("principal"))
		{
			this.carregaTelaPrincipal(req, res);
		}
		
		if(tela.equals("TelaOportunidade"))
		{
			this.carregaTelaOportunidade(req, res);
		}
		
		if(tela.equals("TelaRequisito"))
		{
			this.carregaTelaRequisito(req, res);
		}
		
		if(tela.equals("TelaQuestao"))
		{
			this.carregaTelaQuestoes(req, res);
		}
		
		if(tela.equals("TelaUsuario"))
		{
			this.carregaTelaUsuario(req, res);
		}
		
		if(tela.equals("TelaColaborador"))
		{
			this.carregaTelaColaborador(req, res);
		}
		if(tela.equals("TelaOportunidadeDetalhes"))
		{
			this.carregaTelaOportunidadeDetalhes(req, res);
		}
		if(tela.equals("TelaAprovarCandidato"))
		{
			this.carregaTelaAprovarCandidato(req,res);
		}
		if(tela.equals("TelaProva"))
		{
			this.carregaProva(req, res);
		}
	}
	
	private void carregaLogin(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{
		String comando = req.getParameter("comando");
		if(comando.equals("Entrar"))
		{
			HttpSession sessao = (HttpSession)req.getSession();
			try
			{
				String usuario = req.getParameter("usuario");
				String senha = req.getParameter("senha");
				if(usuario.equals("admin") && senha.equals("admin"))
				{
					sessao.setAttribute("userADM", "admin");
					RequestDispatcher rd = req.getRequestDispatcher("/visao/principal.jsp");
					rd.forward(req, res);
				}
				else
				{
					String cpf = usuario;
					Usuario u = new Usuario();
					u.setCpf(cpf);
					u.setSenha(senha);
					DAOLogin daoL = new DAOLogin();
					if(daoL.logar(u))
					{
						DAOUsuario daoU = new DAOUsuario();
						u = daoU.consultarID(u);
						sessao.setAttribute("user", u);
						RequestDispatcher rd = req.getRequestDispatcher("/visao/principal.jsp");
						rd.forward(req, res);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Login inválido!");
						RequestDispatcher rd = req.getRequestDispatcher("/visao/login.jsp");
						rd.forward(req, res);
					}
				}
			}
			catch(NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Login inválido!");
				RequestDispatcher rd = req.getRequestDispatcher("/visao/login.jsp");
				rd.forward(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}			
		}
		if(comando.equals("Sair"))
		{
			HttpSession sessao = (HttpSession)req.getSession();
			sessao.setAttribute("user", null);
			sessao.setAttribute("userADM", null);
			RequestDispatcher rd = req.getRequestDispatcher("/visao/login.jsp");
			rd.forward(req, res);
		}
	}
	
	private void carregaTelaPrincipal(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{

		String comando = req.getParameter("comando");
		
		if(comando.equals("TelaOportunidades"))
		{
			try
			{
				DAORequisito daoR = new DAORequisito();
				ArrayList requisitos = daoR.consultarTodos();
				
				HttpSession sessao = req.getSession();
				sessao.setAttribute("requisitos", requisitos);
				sessao.setAttribute("itens", null);					
				
				RequestDispatcher rd = req.getRequestDispatcher("/visao/oportunidades.jsp");
				rd.forward(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
		}
		if(comando.equals("TelaRequisitos"))
		{
			this.carregaRequisitos(req, res);
			
		}
		if(comando.equals("TelaQuestoes"))
		{
			this.carregaQuestoes(req,res);								
		}
		if(comando.equals("TelaUsuarios"))
		{
			RequestDispatcher rd = req.getRequestDispatcher("/visao/usuario.jsp");
			rd.forward(req, res);				
		}
		if(comando.equals("TelaColaboradores"))
		{
			RequestDispatcher rd = req.getRequestDispatcher("/visao/colaborador.jsp");
			rd.forward(req, res);				
		}
		if(comando.equals("TelaListaOportunidade"))
		{
			try
			{
				DAOOportunidade daoO = new DAOOportunidade();
				ArrayList listaOportunidade = daoO.consultarDisponiveis();
				
				HttpSession sessao = req.getSession();
				sessao.setAttribute("listaOportunidade", listaOportunidade);
				
				RequestDispatcher rd = req.getRequestDispatcher("/visao/listaOportunidade.jsp");
				rd.forward(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}							
		}
		if(comando.equals("TelaOportunidadeDetalhes"))
		{
			try
			{
				String id = req.getParameter("id");
				DAOOportunidade daoO = new DAOOportunidade();
				Oportunidade op = new Oportunidade();
				op.setIdOportunidade(Integer.parseInt(id));
				op = daoO.consultar(op);
				
				req.setAttribute("oportunidade", op);					
				
				RequestDispatcher rd = req.getRequestDispatcher("/visao/oportunidadeDetalhes.jsp");
				rd.forward(req, res);	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		if(comando.equals("TelaAprovarCandidato"))
		{
			this.carregaCandidaturasAprovar(req, res);
		}
			
	}
	
	private void carregaTelaOportunidade(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{

		Oportunidade op = new Oportunidade();
		
		int id = Integer.parseInt(req.getParameter("id"));
		String titulo = req.getParameter("titulo");
		int cargaHoraria = Integer.parseInt(req.getParameter("cargaHoraria"));
		String areaAtuacao = req.getParameter("areaAtuacao");
		double salario = Double.parseDouble(req.getParameter("salario"));
		String descricao = req.getParameter("descricao");
		Date encerramento;
        try
        {
        	encerramento = this.formataData(req.getParameter("dataEncerramento"));
        }
        catch(Exception ex)
        {   encerramento = null;	}
		
        String vt = req.getParameter("vt") != null ? req.getParameter("vt") : "";
		String planoSaude = req.getParameter("planoSaude") != null ? req.getParameter("planoSaude") : "";
		String cestaBasica = req.getParameter("cestaBasica") != null ? req.getParameter("cestaBasica") : "";
		String pl = req.getParameter("pl") != null ? req.getParameter("pl") : "";
		String vr = req.getParameter("vr") != null ? req.getParameter("vr") : "";
		String planoOdonto = req.getParameter("planoOdonto") != null ? req.getParameter("planoOdonto") : "";
		String beneficios = vt + ";"+ planoSaude + ";"+ cestaBasica + ";" + pl + ";"+ vr + ";"+ planoOdonto;
		
		op.setIdOportunidade(id);
		op.setTitulo(titulo);
		op.setCargaHoraria(cargaHoraria);
		op.setAreaAtuacao(areaAtuacao);
		op.setSalario(salario);
		op.setDescricao(descricao);
		op.setDataEncerramento(encerramento);
		op.setBeneficios(beneficios.trim());	
		
		String comando = req.getParameter("comando");
		if(comando.equals("Adicionar"))
		{
			HttpSession sessao = (HttpSession)req.getSession();
			
			Requisito r = new Requisito();
			r.setIdRequisito(Integer.parseInt(req.getParameter("requisito")));
			
			try
			{
				DAORequisito daoR = new DAORequisito();
				Requisito rNome = daoR.consultar(r);
				r.setNome(rNome.getNome());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			ArrayList itens = (ArrayList)sessao.getAttribute("itens");
			
			ItemRequisito i = new ItemRequisito();
			i.setQuantidade(Integer.parseInt(req.getParameter("quantidade")));
			i.setR(r);
			
			if(itens==null)
			{
				itens = new ArrayList();					
			}
			try
			{
				itens.add(i);
				sessao.setAttribute("itens", itens);
				sessao.setAttribute("oportunidade", op);
				RequestDispatcher rd = req.getRequestDispatcher("/visao/oportunidades.jsp");
				rd.forward(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if(comando.equals("Remover"))
		{
			Requisito r = new Requisito();
			r.setNome(req.getParameter("requisito"));
			
			HttpSession sessao = (HttpSession)req.getSession();
			
			ArrayList itens = (ArrayList)sessao.getAttribute("itens");
			
			ItemRequisito i = new ItemRequisito();
			i.setQuantidade(Integer.parseInt(req.getParameter("quantidade")));
			i.setR(r);
			
			if(itens==null)
			{
				itens = new ArrayList();					
			}
			
			itens.remove(i);
			sessao.setAttribute("itens", itens);
			sessao.setAttribute("oportunidade", op);
			RequestDispatcher rd = req.getRequestDispatcher("/visao/oportunidades.jsp");
			rd.forward(req, res);
		}
		
		if(comando.equals("Cadastrar"))
		{
			HttpSession sessao = (HttpSession)req.getSession();				
			op.setItens(new HashSet((ArrayList)sessao.getAttribute("itens")));
			
			try
			{
				DAOOportunidade daoO = new DAOOportunidade();					
				daoO.cadastrar(op);
				JOptionPane.showMessageDialog(null,"Oportunidade cadastrada com sucesso!");
				RequestDispatcher rd = req.getRequestDispatcher("/visao/oportunidades.jsp");
				rd.forward(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private void carregaTelaRequisito(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{

		String id = req.getParameter("id");
		String nome = req.getParameter("nome");
		
		String comando = req.getParameter("comando");
		if(comando.equals("Salvar"))
		{
			Requisito r = new Requisito();
			r.setIdRequisito(Integer.parseInt(id));
			r.setNome(nome);
			try
			{
				DAORequisito daoR = new DAORequisito();
				if(daoR.verificaID(r))
				{
					daoR.cadastrar(r);
					JOptionPane.showMessageDialog(null,"Requisito cadastrado com sucesso!");
				}
				else
				{
					daoR.alterar(r);
					JOptionPane.showMessageDialog(null,"Requisito alterado com sucesso!");
				}
				this.carregaRequisitos(req, res);						
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if(comando.equals("Editar"))
		{
			Requisito r = new Requisito();
			r.setIdRequisito(Integer.parseInt(req.getParameter("id")));
			try
			{
				DAORequisito daoR = new DAORequisito();
				r = daoR.consultar(r);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
			req.setAttribute("r", r);					
			
			RequestDispatcher rd = req.getRequestDispatcher("/visao/requisito.jsp");
			rd.forward(req, res);			
		}		
		
		if(comando.equals("Excluir"))
		{
			try
			{
				Requisito r = new Requisito();
				r.setIdRequisito(Integer.parseInt(id));
				
				DAORequisito daoR = new DAORequisito();
				daoR.excluir(r);	
				JOptionPane.showMessageDialog(null,"Requisito removido com sucesso!");
				this.carregaRequisitos(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private void carregaTelaQuestoes(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{
		
		String comando = req.getParameter("comando");
		if(comando.equals("Salvar"))
		{
			String id = req.getParameter("id");
			String enunciado = req.getParameter("enunciado");
			String respostaA = req.getParameter("respostaA");
			String respostaB = req.getParameter("respostaB");
			String respostaC = req.getParameter("respostaC");
			String respostaD = req.getParameter("respostaD");
			String respCerta = req.getParameter("respCerta");
			String status = req.getParameter("status");
			String tipo = req.getParameter("tipo-questao");
			
			Questao q = new Questao();
			
			q.setIdQuestao(Integer.parseInt(id));
			q.setEnunciado(enunciado);
			q.setRespostaA(respostaA);
			q.setRespostaB(respostaB);
			q.setRespostaC(respostaC);
			q.setRespostaD(respostaD);
			q.setRespCerta(respCerta);
			q.setStatus(status);
			q.setTipo(tipo);
			try
			{
				DAOQuestao daoQ = new DAOQuestao();
				if(daoQ.verificaID(q))
				{
					daoQ.cadastrar(q);
					JOptionPane.showMessageDialog(null,"Questão cadastrada com sucesso!");
				}
				else					
				{
					daoQ.alterar(q);
					JOptionPane.showMessageDialog(null,"Questão alterada com sucesso!");
				}
				this.carregaQuestoes(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if(comando.equals("Editar"))
		{
			Questao q = new Questao();
			q.setIdQuestao(Integer.parseInt(req.getParameter("id")));
			try
			{
				DAOQuestao daoQ = new DAOQuestao();
				q = daoQ.consultar(q);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
			req.setAttribute("questoes", q);					
			
			RequestDispatcher rd = req.getRequestDispatcher("/visao/questao.jsp");
			rd.forward(req, res);			
		}
		if(comando.equals("Excluir"))
		{
			Questao q = new Questao();
			q.setIdQuestao(Integer.parseInt(req.getParameter("id")));
			try
			{
				DAOQuestao daoQ = new DAOQuestao();
				daoQ.excluir(q);
				
				JOptionPane.showMessageDialog(null,"Questão removida com sucesso!");
				this.carregaQuestoes(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private void carregaTelaUsuario(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{

		String id = req.getParameter("id");
		String cpf = req.getParameter("cpf");
		String nome = req.getParameter("nome");
		String depto = req.getParameter("departamento");
		String email = req.getParameter("email");
		String senha = req.getParameter("senha");
		String tipo = req.getParameter("tipoUsuario");
		
		String comando = req.getParameter("comando");
		if(comando.equals("Cadastrar") || comando.equals("Alterar"))
		{
			try
			{
				Usuario u = new Usuario();
				u.setIdUsuario(Integer.parseInt(id));
				u.setCpf(cpf);
				u.setNome(nome);
				u.setDepto(depto);
				u.setEmail(email);
				u.setSenha(senha);
				u.setTipo(tipo);
				
				DAOUsuario daoU = new DAOUsuario();
				if(comando.equals("Cadastrar"))
				{
					if(daoU.verificaID(u))
					{
						daoU.cadastrar(u);
						JOptionPane.showMessageDialog(null,"Usuário cadastrado com sucesso!");
					}
					else
						JOptionPane.showMessageDialog(null,"O ID " + id + " já está cadastrado! Escolha outro ID!");
				}
				else
				{
					daoU.alterar(u);
					JOptionPane.showMessageDialog(null,"Usuário alterado com sucesso!");
				}
				RequestDispatcher rd = req.getRequestDispatcher("/visao/usuario.jsp");
				rd.forward(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if(comando.equals("Excluir"))
		{
			try
			{
				Usuario u = new Usuario();
				u.setIdUsuario(Integer.parseInt(id));
				
				DAOUsuario daoU = new DAOUsuario();
				daoU.excluir(u);	
				JOptionPane.showMessageDialog(null,"Usuário removido com sucesso!");
				RequestDispatcher rd = req.getRequestDispatcher("/visao/usuario.jsp");
				rd.forward(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if(comando.equals("Consultar"))
		{
			try
			{
				Usuario u = new Usuario();
				u.setIdUsuario(Integer.parseInt(id));
				
				DAOUsuario daoU = new DAOUsuario();
				u = daoU.consultar(u);
				req.setAttribute("u", u);
				RequestDispatcher rd = req.getRequestDispatcher("/visao/usuario.jsp");
				rd.forward(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private void carregaTelaColaborador(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{

		String id = req.getParameter("id");
		String cpf = req.getParameter("cpf");
		String nome = req.getParameter("nome");
		String depto = req.getParameter("departamento");
		String email = req.getParameter("email");
		String senha = req.getParameter("senha");
		String tipo = req.getParameter("tipoUsuario");
		String status = req.getParameter("status");
		
		String comando = req.getParameter("comando");
		if(comando.equals("Cadastrar") || comando.equals("Alterar"))
		{
			try
			{
				Colaborador c = new Colaborador();
				c.setIdUsuario(Integer.parseInt(id));
				c.setCpf(cpf);
				c.setNome(nome);
				c.setDepto(depto);
				c.setEmail(email);
				c.setSenha(senha);
				c.setTipo(tipo);
				c.setStatus(status);
				
				DAOColaborador daoC = new DAOColaborador();
				if(comando.equals("Cadastrar"))
				{
					if(daoC.verificaID(c))
					{
						daoC.cadastrar(c);
						JOptionPane.showMessageDialog(null,"Colaborador cadastrado com sucesso!");
					}
					else
						JOptionPane.showMessageDialog(null,"O ID " + id + " já está cadastrado! Escolha outro ID!");
				}
				else
				{
					daoC.alterar(c);
					JOptionPane.showMessageDialog(null,"Colaborador alterado com sucesso!");
				}
				RequestDispatcher rd = req.getRequestDispatcher("/visao/colaborador.jsp");
				rd.forward(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if(comando.equals("Excluir"))
		{
			try
			{
				Colaborador c = new Colaborador();
				c.setIdUsuario(Integer.parseInt(id));
				
				DAOColaborador daoC = new DAOColaborador();
				daoC.excluir(c);	
				JOptionPane.showMessageDialog(null,"Colaborador removido com sucesso!");
				RequestDispatcher rd = req.getRequestDispatcher("/visao/colaborador.jsp");
				rd.forward(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if(comando.equals("Consultar"))
		{
			try
			{
				Colaborador c = new Colaborador();
				c.setIdUsuario(Integer.parseInt(id));
				
				DAOColaborador daoC = new DAOColaborador();
				c = daoC.consultar(c);
				req.setAttribute("c", c);
				RequestDispatcher rd = req.getRequestDispatcher("/visao/colaborador.jsp");
				rd.forward(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private void carregaTelaOportunidadeDetalhes(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{
		String comando = req.getParameter("comando");
		if(comando.equals("Inscrever"))
		{
			try
			{
				HttpSession sessao = req.getSession();
				Usuario u = (Usuario)sessao.getAttribute("user");
				
				Colaborador c = new Colaborador();
				c.setIdUsuario(u.getIdUsuario());
				DAOColaborador daoCO = new DAOColaborador();
				c = daoCO.consultar(c);
				if(c==null)
				{
					JOptionPane.showMessageDialog(null, "Não foi possível se inscrever!\n" +
														"O usuário " + u.getNome() + " não é Colaborador!");
					RequestDispatcher rd = req.getRequestDispatcher("/visao/principal.jsp");
					rd.forward(req, res);
				}
				else
				{
					String id = req.getParameter("id");
					Oportunidade op = new Oportunidade();
					op.setIdOportunidade(Integer.parseInt(id));
					
					Candidatura can = new Candidatura();
					can.setOp(new DAOOportunidade().consultar(op));
					can.setC(c);
					
					DAOCandidatura daoC = new DAOCandidatura();
					if(daoC.verificaID(can))
					{
						daoC.cadastrar(can);				
						c.setStatus("Aguardando supervisor");
						daoCO.alterar(c);
						
						JOptionPane.showMessageDialog(null, "Inscrição realizada com sucesso!");
						RequestDispatcher rd = req.getRequestDispatcher("/visao/principal.jsp");
						rd.forward(req, res);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Você já está inscrito nesta candidatura!");
						RequestDispatcher rd = req.getRequestDispatcher("/visao/principal.jsp");
						rd.forward(req, res);
					}
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if(comando.equals("Prova"))
		{
			this.carregaQuestoesProva(req, res);
		}
	}
	
	private void carregaTelaAprovarCandidato(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{
		String comando = req.getParameter("comando");
		try
		{
			HttpSession sessao = req.getSession();
			String id = req.getParameter("id");
			Usuario u = (Usuario)sessao.getAttribute("user");
			
			Candidatura c = new Candidatura();
			c.setIdCandidatura(Integer.parseInt(id));
			
			DAOCandidatura daoC = new DAOCandidatura();
			c = daoC.consultar(c);
			
			Colaborador co = new Colaborador();
			co.setIdUsuario(c.getC().getIdUsuario());
			co = new DAOColaborador().consultar(co);
			
			if(comando.equals("Aprovar"))
			{
				c.setSupervisorAceite("S");
				co.setStatus("Aguardando prova");
				JOptionPane.showMessageDialog(null, "Candidato aprovado para o processo seletivo!");
			}
			else
			{
				c.setSupervisorAceite("N");
				co.setStatus("Reprovado");
				JOptionPane.showMessageDialog(null, "Candidato reprovado para o processo seletivo!");
			}
			
			daoC.alterar(c);
			
			DAOColaborador daoCO = new DAOColaborador();
			daoCO.alterar(co);
			
			this.carregaCandidaturasAprovar(req, res);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void carregaQuestoes(HttpServletRequest req,
			HttpServletResponse res) {
		try
		{
			DAOQuestao daoQ = new DAOQuestao();
			ArrayList questoes = daoQ.consultarTodas();
			HttpSession sessao = req.getSession();
			sessao.setAttribute("listaQuestoes", questoes);
			
			RequestDispatcher rd = req.getRequestDispatcher("/visao/questao.jsp");
			rd.forward(req, res);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}		
	}

	private void carregaRequisitos(HttpServletRequest req,
			HttpServletResponse res) {
		try
		{
			DAORequisito daoR = new DAORequisito();
			ArrayList requisitos = daoR.consultarTodos();
			HttpSession sessao = req.getSession();
			sessao.setAttribute("requisitos", requisitos);
			
			RequestDispatcher rd = req.getRequestDispatcher("/visao/requisito.jsp");
			rd.forward(req, res);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}		
	}
	
	private void carregaCandidaturasAprovar(HttpServletRequest req,
			HttpServletResponse res) {
		try
		{
			DAOCandidatura daoC = new DAOCandidatura();
			ArrayList listaCandidatura = daoC.consultarCandidaturasAprovar();
			HttpSession sessao = req.getSession();
			sessao.setAttribute("listaCandidatura", listaCandidatura);
			
			RequestDispatcher rd = req.getRequestDispatcher("/visao/aprovar-candidato.jsp");
			rd.forward(req, res);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}		
	}
	
	private void carregaProva(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{
		String comando = req.getParameter("comando");
		if(comando.equals("Enviar Respostas"))
		{
			HttpSession sessao = req.getSession();
			Avaliacao a = (Avaliacao)sessao.getAttribute("avaliacao");
			ArrayList qGerais = (ArrayList)sessao.getAttribute("qGerais"); 
			ArrayList qEspecificas = (ArrayList)sessao.getAttribute("qEspecificas");
			int acertosG = 0;
			int acertosE = 0;
			
			for(int i=0;i<=7;i++)
			{
				String r = req.getParameter("questao" + String.valueOf(i+1));
				Questao q = new Questao();
				if(i<5)
				{
					q = (Questao)qGerais.get(i);
					if(q.getRespCerta().equals(r))
						acertosG++;
				}
				else
				{
					q = (Questao)qEspecificas.get(i-5);
					if(q.getRespCerta().equals(r))
						acertosE++;
				}				
							
			}			
			//JOptionPane.showMessageDialog(null, "Você teve um total de " + acertos + " acertos!");
			
			Colaborador c = a.getC().getC();
			if((acertosG>=3) && (acertosE>=2))
			{
				c.setStatus("Aguardando entrevista");
				JOptionPane.showMessageDialog(null, "Parabéns, você foi aprovado na prova!\n" +
													"Aguarde a entrevista com o Gerente!");			
			}
			else
			{
				c.setStatus("Reprovado");
				JOptionPane.showMessageDialog(null, "Você não acertou o mínimo necessário e está eliminado do processo seletivo!");
			}
			
			try
			{
				DAOColaborador daoCO = new DAOColaborador();
				daoCO.alterar(c);
				
				RequestDispatcher rd = req.getRequestDispatcher("/visao/principal.jsp");
				rd.forward(req, res);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private void carregaQuestoesProva(HttpServletRequest req,
			HttpServletResponse res)
	{
		try
		{
			Oportunidade op = new Oportunidade();
			op.setIdOportunidade(Integer.parseInt(req.getParameter("idOportunidade")));
			Colaborador c = new Colaborador();
			c.setIdUsuario(Integer.parseInt(req.getParameter("idUsuario")));
			Candidatura ca = new Candidatura();
			ca.setC(c);
			ca.setOp(op);
			ca = new DAOCandidatura().consultarCanUsuario(c, op);
			
			Avaliacao a = new Avaliacao();
			a.setC(ca);
			
			DAOQuestao daoQ = new DAOQuestao();
			ArrayList questoesGerais = daoQ.consultarGerais();
			ArrayList questoesEspecificas = daoQ.consultarEspecificas();
			ArrayList questoes = new ArrayList();
			questoes.addAll(questoesGerais);
			questoes.addAll(questoesEspecificas);
			a.setQuestoes(new HashSet(questoes));
			
			HttpSession sessao = req.getSession();
			sessao.setAttribute("qGerais", questoesGerais);
			sessao.setAttribute("qEspecificas", questoesEspecificas);
			sessao.setAttribute("avaliacao", a);
			
			RequestDispatcher rd = req.getRequestDispatcher("/visao/prova.jsp");
			rd.forward(req, res);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}	
	}
	
	public Date formataData(String data) throws Exception {   
        if (data == null || data.equals(""))  
            return null;  
          
        Date date = null;  
        try {  
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
            date = (java.util.Date)formatter.parse(data);  
        } catch (ParseException e) {              
            throw e;  
        }  
        return date;  
    }
	
	protected void doPost(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		
	}
}