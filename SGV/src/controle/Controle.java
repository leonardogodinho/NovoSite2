package controle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.*;
import java.util.*;

import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
		if(tela.equals("TelaRelatorios"))
		{
			this.carregaRelatorio(req, res);
		}
		if(tela.equals("TelaAgendarEntrevista"))
		{
			this.carregaTelaAgendarEntrevista(req, res);
		}
		if(tela.equals("TelaAprovarEntrevista"))
		{
			this.carregaTelaAprovarEntrevista(req, res);
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
		
		if(comando.equals("TelaAgendarEntrevista"))
		{
			this.carregaEntrevistasAgendar(req, res);
		}
		if(comando.equals("TelaAprovarEntrevista"))
		{
			this.carregaEntrevistasAprovar(req, res);
		}
		if(comando.equals("TelaRelatorio"))
		{
			this.carregaTelaRelatorio(req, res);
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
        	encerramento = this.formataData2(req.getParameter("dataEncerramento"));
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
			String areaAtuacao = req.getParameter("areaAtuacao");
			
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
			q.setAreaAtuacao(areaAtuacao);
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
				c.setQtdCandidaturas(0);
				
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
					can.setDataInscricao(new Date());
					
					DAOCandidatura daoC = new DAOCandidatura();
					if((daoC.verificaID(can)) && (c.getQtdCandidaturas() <= 3))
					{
						
						c.setQtdCandidaturas(c.getQtdCandidaturas() + 1);
						can.setStatus("Aguardando supervisor");
						daoC.cadastrar(can);							
						daoCO.alterar(c);
						
						JOptionPane.showMessageDialog(null, "Inscrição realizada com sucesso!");
						RequestDispatcher rd = req.getRequestDispatcher("/visao/principal.jsp");
						rd.forward(req, res);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Você já está inscrito nesta oportunidade, " +
															"ou ultrapassou o limite de candidaturas permitidas!");
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
				c.setStatus("Aguardando prova");
				JOptionPane.showMessageDialog(null, "Candidato aprovado para o processo seletivo!");
			}
			else
			{
				c.setSupervisorAceite("N");
				c.setStatus("Reprovado");
				JOptionPane.showMessageDialog(null, "Candidato reprovado para o processo seletivo!");
			}
			
			daoC.alterar(c);
			
			this.carregaCandidaturasAprovar(req, res);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void carregaTelaAgendarEntrevista(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{
		String comando = req.getParameter("comando");
		if(comando.equals("Cadastrar"))
		{
			try
			{
				String idCandidatura = req.getParameter("candidatura");
				String idGerente = req.getParameter("gerente");
				Date data;
		        try
		        {
		        	data = this.formataData2(req.getParameter("dataEntrevista"));
		        }
		        catch(Exception ex)
		        {   ex.printStackTrace(); data = null;	}
				
				Candidatura can = new Candidatura();
				can.setIdCandidatura(Integer.parseInt(idCandidatura));
				DAOCandidatura daoCa = new DAOCandidatura();
				can = daoCa.consultar(can);
				
				Usuario g = new Usuario();
				g.setIdUsuario(Integer.parseInt(idGerente));
				g = new DAOUsuario().consultar(g);
				
				Entrevista e = new Entrevista();
				e.setCan(can);
				e.setGerente(g);
				e.setData(data);
				
				DAOEntrevista daoE = new DAOEntrevista();
				if(daoE.verificaID(e))
				{
					daoE.cadastrar(e);	
					can.setStatus("Entrevista marcada");
					daoCa.alterar(can);
					JOptionPane.showMessageDialog(null, "Entrevista marcada com sucesso!");
					this.carregaEntrevistasAgendar(req, res);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Esta entrevista já está marcada!");
					this.carregaEntrevistasAgendar(req, res);
				}
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
				String idCandidatura = req.getParameter("candidatura");
				String idGerente = req.getParameter("gerente");
				
				Candidatura can = new Candidatura();
				can.setIdCandidatura(Integer.parseInt(idCandidatura));
				DAOEntrevista daoE = new DAOEntrevista();
				Entrevista e = daoE.consultarPorCandidatura(can);
				
				if(e!=null)
				{
					JOptionPane.showMessageDialog(null, "Esta entrevista ainda não está marcada!");
					this.carregaEntrevistasAgendar(req, res);
				}
				else
				{
					daoE.excluir(e);	
					JOptionPane.showMessageDialog(null,"Entrevista removida com sucesso!");
					this.carregaEntrevistasAgendar(req, res);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private void carregaTelaAprovarEntrevista(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{
		String comando = req.getParameter("comando");
		try
		{
			HttpSession sessao = req.getSession();
			String id = req.getParameter("id");
			Usuario u = (Usuario)sessao.getAttribute("user");
			
			Entrevista e = new Entrevista();
			e.setIdEntrevista(Integer.parseInt(id));
			
			DAOEntrevista daoE = new DAOEntrevista();
			e = daoE.consultar(e);
			
			DAOCandidatura daoC = new DAOCandidatura();
			Candidatura c = daoC.consultar(e.getCan());
			
			Oportunidade op = new Oportunidade();
			op = new DAOOportunidade().consultar(e.getCan().getOp());
			
			if(comando.equals("Aprovar"))
			{
				c.setAprovado("S");
				c.setStatus("Aprovado");
				op.setDataEncerramento(new Date());
				DAOOportunidade daoO = new DAOOportunidade();
				daoO.alterar(op);
				JOptionPane.showMessageDialog(null, "Candidato aprovado para a oportunidade!");
			}
			else
			{
				c.setAprovado("N");
				c.setStatus("Reprovado");
				JOptionPane.showMessageDialog(null, "Candidato reprovado para a oportunidade!");
			}
			
			daoC.alterar(c);
			this.carregaEntrevistasAprovar(req, res);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void carregaTelaRelatorio(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{
		RequestDispatcher rd = req.getRequestDispatcher("/visao/relatorio.jsp");
		rd.forward(req, res);
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
			
			Candidatura c = a.getC();
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
				DAOCandidatura daoC = new DAOCandidatura();
				daoC.alterar(c);
				
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
			ArrayList questoesEspecificas = daoQ.consultarEspecificas(ca.getOp().getAreaAtuacao());
			ArrayList questoes = new ArrayList();
			questoes.addAll(questoesGerais);
			questoes.addAll(questoesEspecificas);
			a.setQuestoes(new HashSet(questoes));
			
			DAOAvaliacao daoA = new DAOAvaliacao();
			daoA.cadastrar(a);
			
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
	
	private void carregaEntrevistasAgendar(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{
		try
		{
			DAOCandidatura daoC = new DAOCandidatura();
			DAOUsuario daoU = new DAOUsuario();
			ArrayList listaCandidaturas = daoC.consultarCandidaturasParaEntrevista();
			ArrayList listaGerentes = daoU.consultarGerentes();
			
			HttpSession sessao = req.getSession();
			sessao.setAttribute("listaCandidaturas", listaCandidaturas);
			sessao.setAttribute("listaGerentes", listaGerentes);
			
			RequestDispatcher rd = req.getRequestDispatcher("/visao/agendar-entrevista.jsp");
			rd.forward(req, res);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}	
	}
	
	private void carregaEntrevistasAprovar(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{
		try
		{
			HttpSession sessao = req.getSession();
			Usuario u = (Usuario)sessao.getAttribute("user");			
			
			DAOEntrevista daoE = new DAOEntrevista();
			ArrayList listaEntrevistas = daoE.consultarEntrevistasAprovar(u);
			sessao.setAttribute("listaEntrevistas", listaEntrevistas);
			
			RequestDispatcher rd = req.getRequestDispatcher("/visao/administrar-entrevista.jsp");
			rd.forward(req, res);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void carregaRelatorio(HttpServletRequest req,
			HttpServletResponse res)
	{
		String relatorio = req.getParameter("nome");
		if(relatorio.equals("Colaboradores"))
		{
			try
			{
				DAOColaborador daoCO = new DAOColaborador();
				ArrayList lista = daoCO.consultarColaboradoresProcesso();
				JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(lista);
				
				// preparar a lista para o relatório
				File f = new File(this.getServletConfig()
						.getServletContext()
						.getRealPath("Relatorio_sem_nome_1.jasper"));
		
				byte[] b = JasperRunManager.runReportToPdf(f.getPath(),
						new HashMap(), jr);
				
				//preencher o arquivo jasper com as informações preparadas JR
				//transformar num PDF, depois transformar o pdf num vetor de bytes
				
				ServletOutputStream out = res.getOutputStream();
				res.setContentType("application/pdf");
				res.setContentLength(b.length);
				out.write(b, 0, b.length);
				out.flush();
				out.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if(relatorio.equals("Oportunidades"))
		{
			try
			{
				DAOOportunidade daoO = new DAOOportunidade();
				ArrayList lista = daoO.consultarRelatorioOportunidades();
				JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(lista);
			
				File f = new File(this.getServletConfig()
						.getServletContext()
						.getRealPath("Relatorio_sem_nome_2.jasper"));
				byte[] b = JasperRunManager.runReportToPdf(f.getPath(),
						new HashMap(), jr);
				
				//preencher o arquivo jasper com as informações preparadas JR
				//transformar num PDF, depois transformar o pdf num vetor de bytes
				
				ServletOutputStream out = res.getOutputStream();
				res.setContentType("application/pdf");
				res.setContentLength(b.length);
				out.write(b, 0, b.length);
				out.flush();
				out.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if(relatorio.equals("Gerar Relatorio"))
		{
			String data = req.getParameter("data");
			try
			{
				DAOOportunidade daoO = new DAOOportunidade();
				ArrayList lista = daoO.consultarRelatorioPorData(data);
				JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(lista);
				
				File f = new File(this.getServletConfig()
						.getServletContext()
						.getRealPath("Relatorio_sem_nome_3.jasper"));
				byte[] b = JasperRunManager.runReportToPdf(f.getPath(),
						new HashMap(), jr);
				
				//preencher o arquivo jasper com as informações preparadas JR
				//transformar num PDF, depois transformar o pdf num vetor de bytes
				
				ServletOutputStream out = res.getOutputStream();
				res.setContentType("application/pdf");
				res.setContentLength(b.length);
				out.write(b, 0, b.length);
				out.flush();
				out.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	public Date formataData2(String data) throws Exception {   
                  
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = (Date)formatter.parse(data);

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String mydate = dateFormat.format(date);
		
		return this.formataData(mydate);
    }
	
	public Date formataData(String data) throws Exception {   
        if (data == null || data.equals(""))  
            return null;  
          
        Date date = null;  
        try {  
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
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