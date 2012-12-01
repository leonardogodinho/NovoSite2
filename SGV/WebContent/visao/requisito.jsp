<%@ include file="header.jsp" %>

<%! Requisito r; %>
<% r = (Requisito)request.getAttribute("r");
	if(r==null)
	{
		r = new Requisito();
		r.setIdRequisito(0);
		r.setNome("");
}%>


<% ArrayList requisitos; %>
<% requisitos = (ArrayList) session.getAttribute("requisitos");

   if(requisitos == null)
	   requisitos = new ArrayList();
%>

<form action="http://localhost:8080/SGV/Controle" class="formee" method="get">
	<fieldset id="dados">
		<legend>Novo</legend>
			<div class="grid-1-12">
				<label for="id" class="bold">ID <em class="formee-req">*</em></label>
				<input type="text" name="id" id="id" value="<%=r.getIdRequisito() %>" class="no-margin">
			</div>
			<div class="grid-6-12">
            	<label for="nome-requisito" class="bold">Nome do Requisito</label>
                <input type="text" name="nome" id="nome-requisito" value="<%=r.getNome() %>" class="no-margin">
            </div>

            <div class="grid-5-12">
            
			<input type=hidden name="tela" value="TelaRequisito"> 
                <input type="submit" name="comando" value="Salvar" class="formee-small align_center last" >
                <!-- 
			 	<input type="submit" name="comando" value="Alterar" class="formee-small align_center last"/>
				<input type="submit" name="comando" value="Excluir" class="formee-small align_center last"/>
				<input type="submit" name="comando" value="Consultar" class="formee-small align_center last"/>
				 -->			
			 </div>
	</fieldset>
</form>
<div class="grid-12-12">
    <table class="grid-12-12" id="table-req">
        <caption class="grid-12-12"><h2>Requisitos Cadastrados</h2></caption>
        <thead class="grid-12-12">
            <tr class="grid-12-12">
                <th class="grid-1-12">ID</th>
                <th class="grid-10-12">Titulo</th>
            </tr>
        </thead>
        <tbody class="grid-12-12">
        <% for(Object o:requisitos)
		{
        	out.write("<tr class='grid-12-12'>");
        	Requisito r = (Requisito)o;
        	out.write("<td class='grid-1-12'><div><p>" + r.getIdRequisito() + "</p></div></td>");
        	out.write("<td class='grid-10-12'><div><p>" + r.getNome() + "</p></div></td>");
        	out.write("<input type='hidden' name='tela' value='TelaRequisito' />");
			out.write("<td><a href='http://localhost:8080/SGV/Controle?tela=TelaRequisito&comando=Editar&id=" + r.getIdRequisito() + "' />Editar</a></td>");
			out.write("<td><a href='http://localhost:8080/SGV/Controle?tela=TelaRequisito&comando=Excluir&id=" + r.getIdRequisito() + "' />Excluir</a></td>");
			out.write("</tr>");
		}
        %>                            

        </tbody>
    </table>
</div>

<%@ include file="footer.jsp" %>