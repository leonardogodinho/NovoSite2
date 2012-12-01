
<%@include file="header.jsp" %>

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
<%! ArrayList requisitos,itens; %>
<% requisitos = (ArrayList) session.getAttribute("requisitos");
   itens = (ArrayList) session.getAttribute("itens");

   if(requisitos == null)
	   requisitos = new ArrayList();
   if(itens == null)
	   itens = new ArrayList();
%>
<form action="http://localhost:8080/SGV/Controle" class="formee" method="get">
	<fieldset id="dadosVaga">
		<legend>Dados da Vaga</legend>
		<div class="grid-1-12">
            <label for="id" class="bold">ID <em class="formee-req">*</em></label>
            <input type="text" name="id" value="<%=o.getIdOportunidade() %>" class="no-margin">
		</div>
        <div class="grid-6-12">
        	<label for="titulo" class="bold">Título <em class="formee-req">*</em></label>
            <input type="text" name="titulo" value="<%=o.getTitulo() %>" class="no-margin" placeholder="Título da Vaga" >
		</div>
		<div class="grid-5-12">
			<label class="bold">Área de Atuação</label>
            <select name="areaAtuacao">
                <%
						String aux = "", aux1 = "", aux2 = "", aux3 = "", aux4 = "";
						if(o.getAreaAtuacao().equals("Administrativo"))
							aux = "selected";
						if(o.getAreaAtuacao().equals("Comercial"))
							aux1 = "selected";
						if(o.getAreaAtuacao().equals("Finanças"))
							aux2 = "selected";
						if(o.getAreaAtuacao().equals("TI"))
							aux3 = "selected";
						if(o.getAreaAtuacao().equals("Vendas"))
							aux4 = "selected";
						out.write("<option value='Administrativo' id='area-opt1' " + aux + ">Administrativo</option>");
						out.write("<option value='Comercial' id='area-opt2' " + aux1 + ">Comercial</option>");
						out.write("<option value='Finanças' id='area-opt3' " + aux2 + ">Finanças</option>");
						out.write("<option value='TI' id='area-opt4' " + aux3 + ">TI</option>");
						out.write("<option value='Vendas' id='area-opt5' " + aux4 + ">Vendas</option>");
                %>
                </select>	 
        </div>
        <div class="grid-3-12">
            <label for="jornada" class="bold">Carga Horária <em class="formee-req">*</em></label> 
            <select name="cargaHoraria">
                <option value="20">20</option>
                <option value="40">40</option>
            </select>
        </div>
                	
        <div class="grid-3-12">
	        <label for="salario" class="bold">Salário (R$)</label>
            <input type="text" name="salario" value="<%=o.getSalario() %>"  />
        </div>
        
		<div class="grid-12-12">
            <label for="desc" class="bold">Descrição</label>
            <textarea rows="10" cols="60" name="descricao" value="<%=o.getDescricao() %>" ></textarea>
        </div>	
		
		<div class="grid-6-12">
        	<label class="bold">Benefí­cios</label>
            <input type="checkbox" name="vt" value="vt">VT
            <input type="checkbox" name="planoSaude" value="planoSaude">Plano de Saúde
            <input type="checkbox" name="vr" value="vr">VR<br>
            <input type="checkbox" name="cesta" value="cesta">Cesta Básica
            <input type="checkbox" name="pl" value="pl">PL
            <input type="checkbox" name="planoOdonto" value="planoOdonto">Plano Odontológico		
        </div>
		
        <div class="grid-3-12">
        	<label for="dataEncerramento" class="bold">Data de Encerramento</label>
            <input type="text" id="datepicker" name="dataEncerramento" value="<%=o.getDataEncerramento() %>"  />
        </div>
		
	</fieldset>
	<fieldset id="requisitos">
		<legend>Requisitos do candidato</legend>
		<br>		
			Requisito&nbsp;&nbsp;&nbsp;
			<select name="requisito">
				<option value="">__________________</option>
				<% for(Object obj:requisitos)
				   {
						Requisito r = (Requisito)obj;
						out.write("<option value=" + r.getIdRequisito() + ">" + 
								r.getNome() + "</option>");
						
				   }	
					%>
			</select>&nbsp;&nbsp;&nbsp;
			Quantidade
			<input type="text" name="quantidade" size=2 value=0 />&nbsp;&nbsp;&nbsp;
			<input type="submit" name="comando" value="Adicionar" />
			<input type="submit" name="comando" value="Remover" />
			<br><br>
			<table style="border: 1px solid;">
				<tr>
					<td></td>
				</tr>
				<tr>					
					<% for(Object o:itens)
				   {
						ItemRequisito i = (ItemRequisito)o;
						out.write("<tr>");
						out.write("<td>" + i.getR().getNome() + "    " + i.getQuantidade() + "</td>");
						out.write("</tr>");
				   }	
					%>
				</tr>
			</table>
	</fieldset>
	<div id="botoes">
			<input type="submit" name="comando" value="Cadastrar"/>
	<!-- 	<input type="submit" name="comando" value="Alterar"/>
			<input type="submit" name="comando" value="Excluir"/>
			<input type="submit" name="comando" value="Consultar"/>			
		</div>
	 -->
	<input type="hidden" name="tela" value="TelaOportunidade" />
	</form>

</body>
</html>
<%@ include file="footer.jsp"  %>