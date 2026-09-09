<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="uy.edu.fing.tse.entidad.Ciudadano" %>
<%@ page import="java.util.List" %>
<html>
<head><title>Gestor de Ciudadanos</title></head>
<body>
    <h1>Gestor de Ciudadanos</h1>

    <% if (request.getAttribute("mensaje") != null) { %>
        <p><b><%= request.getAttribute("mensaje") %></b></p>
    <% } %>

    <h2>Agregar Ciudadano</h2>
    <form action="ciudadano" method="post">
        Cedula (8 digitos): <input type="text" name="cedula" required /><br/>
        Correo: <input type="email" name="correo" required /><br/>
        Fecha primer login: <input type="date" name="fecha" required /><br/>
        <input type="submit" value="Agregar" />
    </form>

    <h2>Buscar por Cedula</h2>
    <form action="ciudadano" method="get">
        Cedula: <input type="text" name="cedulaBuscar" /><br/>
        <input type="submit" value="Buscar" />
    </form>

    <% Ciudadano resultado = (Ciudadano) request.getAttribute("resultadoBusqueda");
       if (resultado != null) { %>
        <p>Resultado: <%= resultado %></p>
    <% } %>

    <h2>Listado</h2>
    <table border="1">
        <tr><th>Cedula</th><th>Correo</th><th>Fecha primer login</th></tr>
        <%
            List<Ciudadano> lista = (List<Ciudadano>) request.getAttribute("ciudadanos");
            if (lista != null) {
                for (Ciudadano c : lista) {
        %>
        <tr>
            <td><%= c.getCedula() %></td>
            <td><%= c.getCorreoElectronico() %></td>
            <td><%= c.getFechaPrimerLogin() %></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>
