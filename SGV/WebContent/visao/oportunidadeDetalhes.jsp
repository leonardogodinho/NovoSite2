<%@ include file="header.jsp" %>
<%@page import="dao.DAOOportunidade" %>

<%! Oportunidade o; %>
<% o = (Oportunidade)request.getAttribute("oportunidade");
   if(o==null)
   {
	   o = new Oportunidade();
	   o.setIdOportunidade(0);
	   o.setAreaAtuacao("");
	   o.setBeneficios("");
	   o.setCargaHoraria(0);
	   o.setDataEncerramento(null);
	   o.setDescricao("");
	   o.setSalario(0);
	   o.setTitulo("");
   }
%>
<div id="content" class="row">
	<li>
        	<h2 class="align_center"><a href="#"><%out.write(o.getTitulo()); %></a></h2>
            <section>
            	<div class="col_5"><h3 class="bold">Salário: </h3><span>R$ <%out.write(String.valueOf(o.getSalario())); %></span></div>
            	<div class="col_6"><h3 class="bold">Área de Atuação: </h3><span><%out.write(o.getAreaAtuacao()); %></span></div>
            	<div class="col_5"><h3 class="bold">Carga Horária: </h3><span><%out.write(String.valueOf(o.getCargaHoraria())); %>hs</span></div>
            </section>
            <%out.write(o.getDescricao()); %>
            <section>
            	<div class="col_5"><h3 class="bold">Benefícios: </h3><span>
            	</span></div>
            </section>
            	<%int i = 0;
            	if(o.getBeneficios().contains("vt"))
            	{	
            		out.write("VT");
            		i++;
            	}
            	if(o.getBeneficios().contains("pl"))
            	{	
            		if(i==0)
            			out.write("PL");
            		else
            			out.write(", PL");
            		i++;
            	}
            	if(o.getBeneficios().contains("planoSaude"))
            	{	
            		if(i==0)
            			out.write("Plano de Saúde");
            		else
            			out.write(", Plano de Saúde");
            		i++;
            	} 
            	if(o.getBeneficios().contains("vr"))
            	{	
            		if(i==0)
            			out.write("VR");
            		else
            			out.write(", VR");
            		i++;
            	}
            	if(o.getBeneficios().contains("cesta"))
            	{	
            		if(i==0)
            			out.write("Cesta básica");
            		else
            			out.write(", Cesta básica");
            		i++;
            	}
            	if(o.getBeneficios().contains("planoOdonto"))
            	{	
            		if(i==0)
            			out.write("Plano Odontológico");
            		else
            			out.write(", Plano Odontológico");
            		i++;
            	}    
            	
            	%>
            	
            <section>
            	<div class="col_5"><h3 class="bold">Requisitos: </h3><span>
            	</span></div>
            </section>
            	<%ArrayList lista;
            		DAOOportunidade dao = new DAOOportunidade();
            		lista = dao.listarRequisitos(o.getIdOportunidade());
            		for(Object obj:lista)
 				   	{
 						Itens item = (Itens)obj;
 						out.write(item.getNm_requisito() + ": " + 
 								item.getNr_quantidade());
 						out.write("<br>");
 						
 				   	}
            	%>
            	
            <ul class="opt-menu">
            	<li class="formee-button">
            	<% 
            	out.write("<a href='http://localhost:8080/SGV/Controle?tela=TelaOportunidadeDetalhes&comando=Inscrever&id=" + o.getIdOportunidade() + "'>Inscrever-se</a>");
            	%>
				</li>
            </ul>
	</li>
</div>
<%@include file="footer.jsp" %>