
<%@ page language="java" contentType="text/html;charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<%@page import="modelo.*,java.util.*,dao.*" %>
    
<!DOCTYPE HTML>
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">  
    <title>Login | Sistema de Gerenciamento de Vagas</title>
    
    <script src="<%=request.getContextPath() %>/visao/js/52/modernizr-1.1.min.js"></script><!-- this is the javascript allowing html5 to run in older browsers -->
    
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/visao/css/52/reset.css" media="screen" title="html5doctor.com Reset Stylesheet" />
    
    <!--Formee js call-->
	<script type="text/javascript" src="<%=request.getContextPath() %>/visao/js/formee/jquery-1.6.4.min.js"></script>
    
    <!--Formee Framework-->
	<script type="text/javascript" src="<%=request.getContextPath() %>/visao/js/formee/formee.js"></script>
    
    <!--Formee stylesheet structure-->
	<link rel="stylesheet" href="<%=request.getContextPath() %>/visao/css/formee/formee-structure.css" type="text/css" media="screen" />
	
    <!--Formee style-->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/visao/css/formee/formee-style.css" type="text/css" media="screen" />
    
    <!--Formee basic style page-->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/visao/css/formee/formee/style-page.css" type="text/css" media="screen" />
    
    <!--52Framework basic style page-->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/visao/css/52/style-page.css" type="text/css" media="screen" />
    
    <!--Custom Stylesheet-->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/visao/css/custom.css" type="text/css" media="screen" />

    <!--Custom Font-->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/visao/font/stylesheet.css" media="all" type="text/css">
    
    <!-- in the CSS3 stylesheet you will find examples of some great new features CSS has to offer -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/visao/css/52/css3.css" media="screen" />
    
    <!-- general stylesheet contains some default styles, you do not need this, but it helps you keep a uniform style -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/visao/css/52/general.css" media="screen" />
    
    <!-- grid's will help you keep your website appealing to your users, view 52framework.com website for documentation -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/visao/css/52/grid.css" media="screen" />
    
    <!--[if IE]> <style> .report_bugs {left:0px;} </style>	<![endif]-->
    
    <!-- OF COURSE YOU NEED TO ADAPT NEXT LINE TO YOUR tiny_mce.js PATH -->
	<script type="text/javascript" src="<%=request.getContextPath() %>/visao/tiny_mce/tiny_mce.js"></script>

<script type="text/javascript">
tinyMCE.init({
        theme: "advanced",
		mode : "textareas",
		theme_advanced_buttons1 : "bold, italic, underline, left, right, justify, separator, numlist, bullist, separator, undo, redo"

});
</script>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<script src="<%=request.getContextPath() %>/visao/js/jquery-latest.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />


    </head>

<body>
	<nav>
        <ul>
        <% Usuario u; %>
		<% u = (Usuario)session.getAttribute("user");
		if(u==null)
		{
			
		}
		else
		{
			if(u.getTipo().equals("RH"))
			{ 
				out.write("<li class='has-sub '><a href='#'><span>Cadastrar</span></a>");
				out.write("<ul>");
	            out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaUsuarios'><span>Usuário</span></a></li>");
	            out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaColaboradores'><span>Colaborador</span></a></li>");
				out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaOportunidades'><span>Oportunidade</span></a></li>");
	            out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaQuestoes'><span>Questões</span></a></li>");
	            out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaRequisitos'><span>Requisitos</span></a></li>");
	            out.write("</ul>");
	           	out.write("</li>");
	           	out.write("<li class='has-sub '><a href='#'><span>Entrevista</span></a>");
	           	out.write("<ul>");
	           	out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaAgendarEntrevista'><span>Agendar</span></a></li>");			
	           	out.write("</ul>");
	           	out.write("</li>");
	           	out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaAprovarCandidato'><span>Administrar candidatos</span></a></li>");         
				out.write("<li class='has-sub '><a href='#'><span>Relatórios</span></a>");
				out.write("<ul>");
				out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=TelaRelatorios&nome=Oportunidades'><span>Oportunidades</span></a></li>");
				out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaRelatorio'><span>Histórico de Oportunidades</span></a></li>");
				out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=TelaRelatorios&nome=Colaboradores'><span>Colaboradores em Processo</span></a></li>");
	            out.write("</ul>");
	           	out.write("</li>");
	           	out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaListaOportunidade'><span>Em Aberto</span></a></li>");       
			}			
			if(u.getTipo().equals("Colaborador"))
				out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaListaOportunidade'><span>Em Aberto</span></a></li>");      
			if(u.getTipo().equals("Supervisor"))
			{
				out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaAprovarCandidato'><span>Administrar candidatos</span></a></li>");         
				out.write("<li><a href='#'><span>Relatórios</span></a></li>");           	
			}
			if(u.getTipo().equals("Gerente"))
			{
				out.write("<li class='has-sub '><a href='#'><span>Cadastrar</span></a>");
				out.write("<ul>");
				out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaQuestoes'><span>Questões</span></a></li>");            
				out.write("</ul>");
	           	out.write("</li>");
	           	out.write("<li class='has-sub '><a href='#'><span>Entrevista</span></a>");
	           	out.write("<ul>");
	           	out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaAprovarEntrevista'><span>Administrar</span></a></li>");			
	           	out.write("</ul>");
	           	out.write("</li>");
	           	out.write("<li class='has-sub '><a href='#'><span>Relatórios</span></a>");
				out.write("<ul>");
				out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=TelaRelatorios&nome=Oportunidades'><span>Oportunidades</span></a></li>");
				out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=principal&comando=TelaRelatorio'><span>Histórico de Oportunidades</span></a></li>");
				out.write("<li><a href='http://localhost:8080/SGV/Controle?tela=TelaRelatorios&nome=Colaboradores'><span>Colaboradores em Processo</span></a></li>");
	            out.write("</ul>");
	           	out.write("</li>");
			}
		}
       %>
       </ul>
    </nav>    
<div class="row">

	<header class="no-border col_16">
    
    	<div class="col_10">
	        <div id="user-info">
	        	<%! Usuario u; %>
		    	<%
		    	u = (Usuario)session.getAttribute("user");
		    	if(u==null)
		    		if(session.getAttribute("userADM")!=null)
		    			out.write("<p>Bem vindo, <span class='bold'>Admin</span>!<br>");
		    		else
		    			pageContext.forward("login.jsp"); 
		    	else
		    	{
		    		out.write("<p>Bem vindo, <span class='bold'>" + u.getNome() + "</span>!<br>");
		    		out.write("Seu nível de acesso é <span class='bold'>" + u.getTipo() + "</span>.</p>");
		    	}
				%>				
				<p class="left">
				<a href="http://localhost:8080/SGV/Controle?tela=Login&comando=Sair">Logout</a>
				</p>            
	        </div>
        </div>
    	<div class="logo float_right"><img src="<%=request.getContextPath() %>/visao/images/svg-logo300.png" alt="Sistema de Gerenciamento de Vagas"></div>
        
        <div class="clear"></div><!-- clear -->
    </header>
</div><!-- row -->

