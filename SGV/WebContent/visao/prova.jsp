<%@ include file="header.jsp" %>
<% ArrayList questoesGerais,questoesEspecificas; %>
<% questoesGerais = (ArrayList) session.getAttribute("qGerais");
   questoesEspecificas = (ArrayList) session.getAttribute("qEspecificas");

   if(questoesGerais == null)
	   questoesGerais = new ArrayList();
   if(questoesEspecificas == null)
	   questoesEspecificas = new ArrayList();
%>
<form action="http://localhost:8080/SGV/Controle" class="formee" method="get">
	<fieldset>
		<legend>Avaliação</legend>
		
			<div class="grid-12-12">
			<h2 class='align_left'>Questões Gerais</h2>
            <% int i = 1;
            for(Object o:questoesGerais)
			{
            	Questao q = (Questao)o;
            	out.write("<label class='bold'>" + i + ") " + q.getEnunciado() + "</label>");
            	out.write("<ul class='formee-list'>");
                out.write("<li><input type='radio' value='A' id='alt" + i + "A' name='questao" + i + "'><label for='alt" + i + "A'>" + q.getRespostaA() + "</label></li>");
                out.write("<li><input type='radio' value='B' id='alt" + i + "B' name='questao" + i + "'><label for='alt" + i + "B'>" + q.getRespostaB() + "</label></li>");
                out.write("<li><input type='radio' value='C' id='alt" + i + "C' name='questao" + i + "'><label for='alt" + i + "C'>" + q.getRespostaC() + "</label></li>");
                out.write("<li><input type='radio' value='D' id='alt" + i + "D' name='questao" + i + "'><label for='alt" + i + "D'>" + q.getRespostaD() + "</label></li>");
                out.write("</ul>");
                i++;
			}
            %>
            </div>
            <div class="grid-12-12">
            <h2 class='align_left'>Questões Específicas</h2>
            <% i = 6;
            for(Object o:questoesEspecificas)
			{
            	Questao q = (Questao)o;
            	out.write("<label class='bold'>" + i + ") " + q.getEnunciado() + "</label>");
            	out.write("<ul class='formee-list'>");
                out.write("<li><input type='radio' value='A' id='alt" + i + "A' name='questao" + i + "'><label for='alt" + i + "A'>" + q.getRespostaA() + "</label></li>");
                out.write("<li><input type='radio' value='B' id='alt" + i + "B' name='questao" + i + "'><label for='alt" + i + "B'>" + q.getRespostaB() + "</label></li>");
                out.write("<li><input type='radio' value='C' id='alt" + i + "C' name='questao" + i + "'><label for='alt" + i + "C'>" + q.getRespostaC() + "</label></li>");
                out.write("<li><input type='radio' value='D' id='alt" + i + "D' name='questao" + i + "'><label for='alt" + i + "D'>" + q.getRespostaD() + "</label></li>");
                out.write("</ul>");
                i++;
			}
            %>
            </div>
            <!--
            <div class="grid-12-12">
            	<label class="bold">2) Qual é a segunda questão cadastrada no Sistema de Gerenciamento de Vagas?</label>
                <ul class="formee-list">
                    <li><input type="radio" value="A2" id="alt2A" name="resposta-questao2"><label for="alt2A">A</label></li>
                    <li><input type="radio" value="B2" id="alt2B" name="resposta-questao2"><label for="alt2B">B</label></li>
					<li><input type="radio" value="C2" id="alt2C" name="resposta-questao2"><label for="alt2C">C</label></li>
                    <li><input type="radio" value="D2" id="alt2D" name="resposta-questao2"><label for="alt2D">D</label></li>
                </ul>
            </div>
            <div class="grid-12-12">
            	<label class="bold">3) Qual é a terceira questão cadastrada no Sistema de Gerenciamento de Vagas?</label>
                <ul class="formee-list">
                    <li><input type="radio" value="A3" id="alt3A" name="resposta-questao3"><label for="alt3A">A</label></li>
                    <li><input type="radio" value="B3" id="alt3B" name="resposta-questao3"><label for="alt3B">B</label></li>
					<li><input type="radio" value="C3" id="alt3C" name="resposta-questao3"><label for="alt3C">C</label></li>
                    <li><input type="radio" value="D3" id="alt3D" name="resposta-questao3"><label for="alt3D">D</label></li>
                </ul>
            </div>
            <div class="grid-12-12">
            	<label class="bold">4) Qual é a quarta questão cadastrada no Sistema de Gerenciamento de Vagas?</label>
                <ul class="formee-list">
                    <li><input type="radio" value="A4" id="alt4A" name="resposta-questao4"><label for="alt4A">A</label></li>
                    <li><input type="radio" value="B4" id="alt4B" name="resposta-questao4"><label for="alt4B">B</label></li>
					<li><input type="radio" value="C4" id="alt4C" name="resposta-questao4"><label for="alt4C">C</label></li>
                    <li><input type="radio" value="D4" id="alt4D" name="resposta-questao4"><label for="alt4D">D</label></li>
                </ul>
            </div>
			 -->			 
            <div class="grid-6-12"></div>
            <div class="grid-4-12">
            	<input type=hidden name="tela" value="TelaProva">             
                <input type="submit" value="Enviar Respostas" class="formee-medium align_center last" name="comando" >
			</div>
	</fieldset>
</form>
<%@ include file="footer.jsp" %>