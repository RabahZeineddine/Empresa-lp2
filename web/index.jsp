<%-- 
    Document   : index
    Created on : 20/09/2016, 11:40:13
    Author     : 41502779
--%>

<%@page import="br.mackenzie.fci.lp2.model.Funcionario"%>
<%@page import="br.mackenzie.fci.lp2.dao.FuncionarioDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
            for(Funcionario funcionario: new FuncionarioDAO().listar()){
                out.println(funcionario.getNome()+"<br/>");
            }
        %>
    </body>
</html>
