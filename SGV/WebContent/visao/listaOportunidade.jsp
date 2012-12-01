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
		
        <li>
        	<h2 class="align_center"><a href="#">Titulo da Vaga Cadastrada #02</a></h2>
            <section>
            	<div class="col_5"><h3 class="bold">Salário: </h3><span>R$ 10,00</span></div>
            	<div class="col_6"><h3 class="bold">Área de Atuação: </h3><span>Fazer Café</span></div>
            	<div  class="col_5"><h3 class="bold">Carga Horária: </h3><span>4hs</span></div>
            </section>
            <p>
            Mussum ipsum cacilds, vidis litro abertis. Consetis adipiscings elitis. Pra lá , depois divoltis porris, paradis. Paisis, filhis, espiritis santis. Mé faiz elementum girarzis, nisi eros vermeio, in elementis mé pra quem é amistosis quis leo. Manduma pindureta quium dia nois paga. Sapien in monti palavris qui num significa nadis i pareci latim. Interessantiss quisso pudia ce receita de bolis, mais bolis eu num gostis.<br><br>

Suco de cevadiss, é um leite divinis, qui tem lupuliz, matis, aguis e fermentis. Interagi no mé, cursus quis, vehicula ac nisi. Aenean vel dui dui. Nullam leo erat, aliquet quis tempus a, posuere ut mi. Ut scelerisque neque et turpis posuere pulvinar pellentesque nibh ullamcorper. Pharetra in mattis molestie, volutpat elementum justo. Aenean ut ante turpis. Pellentesque laoreet mé vel lectus scelerisque interdum cursus velit auctor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam ac mauris lectus, non scelerisque augue. Aenean justo massa.
            </p>
            <ul class="opt-menu">
            	<li class="formee-button"><a href="">Detalhes</a></li>
            	<li class="formee-button"><a href="">Inscreva-se</a></li>
            </ul>
		</li>
    </ul>
</div>

<%@ include file="footer.jsp" %>