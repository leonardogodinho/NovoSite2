<%@ include file="header.jsp" %>

<% ArrayList listaCandidaturas,listaGerentes; %>
<% listaCandidaturas = (ArrayList) session.getAttribute("listaCandidaturas");
   listaGerentes = (ArrayList) session.getAttribute("listaGerentes");
   if(listaCandidaturas == null)
	   listaCandidaturas = new ArrayList();
   if(listaGerentes == null)
	   listaGerentes = new ArrayList();
%>
<form action="http://localhost:8080/SGV/Controle" method="GET" class="formee" id="cadastroUsuario">
		<fieldset>
			<legend>Agendamento</legend>
			<div class="grid-2-12">
            	<label   for="data" class="bold">Data <em class="formee-req">*</em></label>
            	<input type="date" name="dataEntrevista" placeholder="dd/mm/yyyy" maxlength="10" title="Informe a data Corretamente">
			</div>
			<div class="grid-5-12">
            	<label  for="nome" class="bold">Colaborador <em class="formee-req">*</em></label>
            	<select name="candidatura" class="no-margin">
            		<% for(Object obj:listaCandidaturas)
				   	{
						Candidatura c = (Candidatura)obj;
						out.write("<option value=" + c.getIdCandidatura() + ">" + 
								c.getC().getNome() + " - " + c.getOp().getTitulo() + "</option>");						
				   	}	
					%>
				</select>
			</div>
			<div class="grid-5-12">
            	<label  class="bold">Gerente <em class="formee-req">*</em></label>
				<select name="gerente" class="no-margin">
					<% for(Object obj:listaGerentes)
				   	{
						Usuario us = (Usuario)obj;
						out.write("<option value=" + us.getIdUsuario() + ">" + 
								us.getNome() + "</option>");						
				   	}	
					%>
				</select>
			</div>
			<div class="grid-12-12 no-margin">
				<div>
					<div class="grid-3-12 no-margin"><input type="submit" name="comando" value="Cadastrar"/></div>
					<div class="grid-3-12 no-margin"><input type="submit" name="comando" value="Excluir"/></div>
					<input type=hidden name="tela" value="TelaAgendarEntrevista">				
				</div>
			</div>
		</fieldset>		
</form>
<%@ include file="footer.jsp" %>