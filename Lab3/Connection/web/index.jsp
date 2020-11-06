<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.sql.*"%>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<%
    try{
        Class.forName("com.mysql.jdbc.Driver");
    }catch (ClassNotFoundException cne){
        cne.printStackTrace();
    }
    String dburl = "jdbc:mysql://127.0.0.1:3306/12306?useSSL=false&serverTimezone=UTC";
    String sql = "SELECT * FROM Train_Info";
    Connection conn = DriverManager.getConnection(dburl,"root","wangzilong2020");
    Statement stmt = conn.createStatement();
    ResultSet rst = stmt.executeQuery(sql);
%>
<br>
<TABLE border=1 bordercolor="# FF0000"bgcolor=# EFEFEF WIDTH=400>
    <TR bgcolor=FFFFFF ALIGN=CENTER>
        <TD><B>ID</B></TD>
        <TD><B>用户名</B></TD>
        <TD><B>密码</B></TD>
    </TR>
<%
    while(rst.next())
    {
%>
    <TR bgcolor=FFFFFF ALIGN=CENTER>
        <TD><B><%=rst.getString(1)%></B></TD>
        <TD><B><%=rst.getString(2)%></B></TD>
        <TD><B><%=rst.getString(3)%></B></TD>
    </TR>
<%
    }
%>
</body>
</html>