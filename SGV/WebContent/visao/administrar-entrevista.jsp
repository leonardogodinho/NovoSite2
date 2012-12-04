<%@ include file="header.jsp" %>
<%@page import="dao.*" %>

<% ArrayList listaEntrevistas; %>
<% listaEntrevistas = (ArrayList) session.getAttribute("listaEntrevistas");
   if(listaEntrevistas == null)
	   listaEntrevistas = new ArrayList();
%>
<div class="grid-12-12">
    <table class="grid-12-12" id="table-questoes">
        <caption class="grid-12-12"><h2>Entrevistados Pendentes</h2></caption>
        <thead class="grid-12-12">
            <tr class="grid-12-12">
                <th class="grid-2-12">Oportunidade</th>
                <th class="grid-8-12">Colaborador</th>
                <th class="grid-2-12">Ações</th>
            </tr>
        </thead>
        <tbody class="grid-12-12">
        <% for(Object o:listaEntrevistas)
		{
        	Entrevista e = (Entrevista)o;
        	if(e.getCan().getStatus().equals("Entrevista marcada"))
        	{
				out.write("<tr class='grid-12-12'>");		
				out.write("<td class='grid-2-12'><div><p>" + e.getCan().getOp().getTitulo() + "</p></div></td>");
				out.write("<td class='grid-8-12'><div><p>" + e.getCan().getC().getNome() + "</p></div></td>");
				out.write("<td class='grid-8-12'>");
				out.write("<div class='adicionar'><a href='http://localhost:8080/SGV/Controle?tela=TelaAprovarEntrevista&comando=Aprovar&id=" + e.getIdEntrevista() + "'><img src='" + request.getContextPath() + "/visao/images/approve.png' alt='Aprovar Candidatura' ></a></div>");
				out.write("<div class='remover'><a href='http://localhost:8080/SGV/Controle?tela=TelaAprovarEntrevista&comando=Reprovar&id=" + e.getIdEntrevista() + "'><img src='" + request.getContextPath() + "/visao/images/disapprove.png' alt='Reprovar Candidatura' ></a></div>");		
				out.write("</td>");
        	}
		}
	%>
	 </tr>                            
        </tbody>
    </table>
</div>
	
<%@ include file="footer.jsp" %>