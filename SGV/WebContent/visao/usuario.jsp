<%@ include file="header.jsp" %>

<% Usuario us; %>
<% us = (Usuario)request.getAttribute("u");
   if(us==null)
   {
	   us = new Usuario();
	   us.setIdUsuario(0);
	   us.setCpf("");
	   us.setNome("");
	   us.setDepto("");
	   us.setEmail("");
	   us.setSenha("");
	   us.setTipo("");
   }%>

<form action="http://localhost:8080/SGV/Controle" method="get" class="formee" name="cadastroUsuario">
		<fieldset>
			<legend>Dados do usuário</legend>

			<div class="grid-1-12">
				<label for="id" class="bold" >ID <em class="formee-req">*</em></label>
                <input type=text name="id" id="id" value="<%=us.getIdUsuario() %>" placeholder="Nº" maxlength="5" >
			</div>
			<div class="grid-2-12">
            	<label   for="cpf" class="bold">CPF <em class="formee-req">*</em></label>
                <input type="text" name="cpf" id="cpf" value="<%=us.getCpf() %>" placeholder="00000000000" pattern="[0-9]{11}" onBlur="ValidarCPF(cadastroUsuario.cpf);" maxlength="11" title="Insira apenas Números">
            </div>
            <div class="grid-6-12">
                <label   for="nome" class="bold">Nome <em class="formee-req">*</em></label>
                <input type="text" id="nome" name="nome" value="<%=us.getNome() %>" placeholder="Nome completo do colaborador">
            </div>
			<div class="grid-3-12">
            	<label  class="bold">Departamento <em class="formee-req">*</em></label>			
                <select name="departamento" class="no-margin">
                    <%
                        String aux = "", aux1 = "", aux2 = "", aux3 = "", aux4 = "";
                        if(us.getDepto().equals("Administrativo"))
                            aux = "selected";
                        if(us.getDepto().equals("Comercial"))
                            aux1 = "selected";
                        if(us.getDepto().equals("Finanças"))
                            aux2 = "selected";
                        if(us.getDepto().equals("TI"))
                            aux3 = "selected";
                        if(us.getDepto().equals("Vendas"))
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
                <input type="email" id="email" name="email" value="<%=us.getEmail() %>" placeholder="seu@email.com">
            </div>
			<div class="grid-4-12">
            				<label  for="senha" class="bold">Senha de acesso <em class="formee-req">*</em></label>
                            <input type="password" id="senha" name="senha" value="<%=us.getSenha() %>" placeholder="Senha de Acesso">
            </div>
			<div class="grid-4-12">
            			<label  class="bold">Tipo de usuário <em class="formee-req">*</em></label>
			<select name="tipoUsuario">
				<%
					String aux5 = "", aux6 = "", aux7 = "";
					if(us.getTipo().equals("RH"))
						aux5 = "selected";
					if(us.getTipo().equals("Supervisor"))
						aux6 = "selected";
					if(us.getTipo().equals("Gerente"))
						aux7 = "selected";
					out.write("<option value='RH' " + aux + ">RH</option>");
					out.write("<option value='Supervisor' " + aux1 + ">Supervisor</option>");
					out.write("<option value='Gerente' " + aux2 + ">Gerente</option>");				
				%>
			</select>
			<br>
		
            </div>
			
		<div class="grid-12-12 no-margin">
		<div>
				<div class="grid-3-12"><input type="submit" name="comando" value="Cadastrar" /></div>
				<div class="grid-3-12"><input type="submit" name="comando" value="Alterar"/></div>
				<div class="grid-3-12"><input type="submit" name="comando" value="Excluir"/></div>
				<div class="grid-3-12"><input type="submit" name="comando" value="Consultar"/></div>
		</div>
		</div>
            <input type=hidden name="tela" value="TelaUsuario">	
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