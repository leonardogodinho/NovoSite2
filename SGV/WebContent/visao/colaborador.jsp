<%@ include file="header.jsp" %>

<% Colaborador c; %>
<% c = (Colaborador)request.getAttribute("c");
   if(c==null)
   {
	   c = new Colaborador();
	   c.setIdUsuario(0);
	   c.setCpf("");
	   c.setNome("");
	   c.setDepto("");
	   c.setEmail("");
	   c.setSenha("");
	   c.setTipo("");
	   c.setStatus("");
   }%>
   	<form action="http://localhost:8080/SGV/Controle" method="GET" class="formee" id="cadastroColaborador">
		<fieldset>
			<legend>Dados do Colaborador</legend>
				<div class="grid-1-12">
					<label for="id" class="bold" >ID <em class="formee-req">*</em></label>
					<input type="text" name="id" value="<%=c.getIdUsuario() %>"  placeholder="Nº" maxlength="5"  />
				</div>
				<div class="grid-2-12">
	            	<label   for="cpf" class="bold">CPF <em class="formee-req">*</em></label>
	            	<input type="text" name="cpf" value="<%=c.getCpf() %>"  placeholder="00000000000" pattern="[0-9]{11}" onBlur="ValidarCPF(cadastroColaborador.cpf);" maxlength="11" title="Insira apenas Números"/>
				</div>
				<div class="grid-6-12">
	                <label   for="nome" class="bold">Nome <em class="formee-req">*</em></label>
	                <input type="text" name="nome" value="<%=c.getNome() %>"  placeholder="Nome completo do colaborador" >
				</div>
				<div class="grid-3-12">
	            	<label  class="bold">Departamento <em class="formee-req">*</em></label>			
	                <select name="departamento" class="no-margin">
						<%
							String aux = "", aux1 = "", aux2 = "", aux3 = "", aux4 = "";
							if(c.getDepto().equals("Administrativo"))
								aux = "selected";
							if(c.getDepto().equals("Comercial"))
								aux1 = "selected";
							if(c.getDepto().equals("Finanças"))
								aux2 = "selected";
							if(c.getDepto().equals("TI"))
								aux3 = "selected";
							if(c.getDepto().equals("Vendas"))
								aux4 = "selected";
							out.write("<option value='Administrativo' " + aux + ">Administrativo</option>");
							out.write("<option value='Comercial' " + aux1 + ">Comercial</option>");
							out.write("<option value='Finanças' " + aux2 + ">Finanças</option>");
							out.write("<option value='TI' " + aux3 + ">TI</option>");
							out.write("<option value='Vendas' " + aux4 + ">Vendas</option>");
						%>
				</select>
			</div>
			<div class="grid-4-12">
            	<label   for="email" class="bold">E-mail <em class="formee-req">*</em></label>
            	<input type="email" name="email" value="<%=c.getEmail() %>" placeholder="seu@email.com" >
			</div>
			<div class="grid-4-12">
            				<label  for="senha" class="bold">Senha de acesso <em class="formee-req">*</em></label>
            				<input type="text" name="senha" value="<%=c.getSenha() %>"  placeholder="Senha de Acesso" >
			</div>
			<div class="grid-4-12">
            			<label  class="bold">Tipo de usuário <em class="formee-req">*</em></label>
			<select name="tipoUsuario">
				<option value="Colaborador">Colaborador</option>
			</select>
			</div>
			<div class="grid-12-12">
			<label  class="bold">Status <em class="formee-req">*</em></label>
			<select name="status">
				<option value="Sem Candidatura">Sem Candidatura</option>
			</select>
			</div>
			<div class="grid-12-12 no-margin">
				<div>
					<div class="grid-3-12 no-margin"><input type="submit" name="comando" value="Cadastrar"/></div>
					<div class="grid-3-12 no-margin"><input type="submit" name="comando" value="Alterar"/></div>
					<div class="grid-3-12 no-margin"><input type="submit" name="comando" value="Excluir"/></div>
					<div class="grid-3-12 no-margin"><input type="submit" name="comando" value="Consultar"/></div>
					<input type=hidden name="tela" value="TelaColaborador">				
				</div>
			</div>
		</fieldset>
	</form>
	    <script type="text/javascript">
    	function ValidarCPF(Objcpf){
        var cpf = Objcpf.value;
		if (cpf != "") {
			exp = /\.|\-/g
			cpf = cpf.toString().replace( exp, "" ); 
			var digitoDigitado = eval(cpf.charAt(9)+cpf.charAt(10));
			var soma1=0, soma2=0;
			var vlr =11;
			
			for(i=0;i<9;i++){
					soma1+=eval(cpf.charAt(i)*(vlr-1));
					soma2+=eval(cpf.charAt(i)*vlr);
					vlr--;
			}       
			soma1 = (((soma1*10)%11)==10 ? 0:((soma1*10)%11));
			soma2=(((soma2+(2*soma1))*10)%11);
			
			var digitoGerado=(soma1*10)+soma2;
			if(digitoGerado!=digitoDigitado)        
					alert('O CPF digitado não é válido!');         
		}
}

    </script>
	
<%@ include file="footer.jsp" %>