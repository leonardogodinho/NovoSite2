<%@ include file="header.jsp" %>

<% ArrayList listaOportunidade; %>
<% listaOportunidade = (ArrayList) session.getAttribute("listaOportunidade");

   if(listaOportunidade == null)
	   listaOportunidade = new ArrayList();
%>
<div id="content" class="row">
	<ul>
		<% for(Object o:listaOportunidade)
		{
			out.write("<li>");
			Oportunidade op = (Oportunidade)o;
			out.write("<h2 class='align_center'>" + op.getTitulo() + " #" + op.getIdOportunidade() + "</h2>");
			out.write("<section>");
			out.write("<div class='col_5'><h3 class='bold'>Salário: </h3><span>R$" + op.getSalario() + "</span></div>");
			out.write("<div class='col_6'><h3 class='bold'>Área de Atuação: </h3><span>" + op.getAreaAtuacao() + "</span></div>");
			out.write("<div class='col_5'><h3 class='bold'>Carga Horária: </h3><span>" + op.getCargaHoraria() + "hs</span></div>");
			out.write("</section>");
			out.write(op.getDescricao());
			out.write("<ul class='opt-menu'>");
         	out.write("<li class='formee-button'><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaOportunidadeDetalhes&id=" + op.getIdOportunidade() + "'>Detalhes</a></li>");
         	
         	Colaborador c = new Colaborador();
			c.setIdUsuario(u.getIdUsuario());
			DAOColaborador daoCO = new DAOColaborador();
			c = daoCO.consultar(c);
			Candidatura can = new DAOCandidatura().consultarCanUsuario(c,op);
			if(can==null)
				out.write("<li class='formee-button'><a href='http://localhost:8080/SGV/Controle?tela=TelaOportunidadeDetalhes&comando=Inscrever&id=" + op.getIdOportunidade() + "'>Inscrever-se</a></li>");
			else
			{
	         	if(can.getStatus().equals("Aguardando prova")) 
	         	{
	         		if(!(new DAOCandidatura().verificaID(can)))
	         			out.write("<li class='formee-button'><a href='http://localhost:8080/SGV/Controle?tela=TelaOportunidadeDetalhes&comando=Prova&idOportunidade=" + op.getIdOportunidade() + "&idUsuario=" + c.getIdUsuario() + "'>Gerar Prova</a></li>");
	         		else
	         			out.write("<li class='formee-button'><a href='http://localhost:8080/SGV/Controle?tela=TelaOportunidadeDetalhes&comando=Inscrever&id=" + op.getIdOportunidade() + "'>Inscrever-se</a></li>");            	
	            }
         	}
         	out.write("</ul>");
			out.write("</li>");
		}%> 
		
    </ul>
</div>

<%@ include file="footer.jsp" %>