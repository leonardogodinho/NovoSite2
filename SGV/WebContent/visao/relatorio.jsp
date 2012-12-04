<%@ include file="header.jsp" %>

<form action="http://localhost:8080/SGV/Controle" class="formee" method="get">
	<fieldset id="dados">
		<legend>Relatório</legend>
			
			<div class="grid-6-12">
            	<label for="nome-requisito" class="bold">Data de encerramento </label>
                <input type="date" name="data" placeholder="dd/mm/yyyy" maxlength="10" title="Informe a data Corretamente">
        	</div>

            <div class="grid-6-12">
            
			<input type=hidden name="tela" value="TelaRelatorios"> 
                <input type="submit" name="nome" value="Gerar Relatorio" class="formee-small align_center last" >               			
			 </div>
	</fieldset>
</form>

<%@ include file="footer.jsp" %>