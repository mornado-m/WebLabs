<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mornado
  Date: 01.10.2017
  Time: 2:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Title</title>
    <style>
        <%@include file="/WEB-INF/styles.css"%>
    </style>
    <script>
        <%@include file="method.js"%>
    </script>
</head>
<body>
    <div id="left">
        <h2>List of all tables</h2>
        <c:forEach var="TableName" items="${Tables}">
            <p class="link" onclick="SetTable(id)" id="${TableName}">${TableName}</p>
        </c:forEach>
    </div>
    <div id="center">
        <h1 id="table_name">${Tables.get(ActiveTableIdx)}</h1>
        <div id="table">
            <table class="table">
            <tr>
            <c:forEach var="Column" items="${TableColumns}">
                <th>${Column}</th>
            </c:forEach>
                <th onclick="DeleteAllRows()">Delete_rows</th>
            </tr>
            <c:forEach var="Row" items="${TableRows}">
                <tr>
                <c:forEach var="Cell" items="${Row}">
                    <td>${Cell}</td>
                </c:forEach>
                    <td class="delete" onclick="DeleteRow(${TableRows.indexOf(Row)})">delete</td>
                </tr>
            </c:forEach>
            </table>
        </div>

        <form action="mainServlet" name="hiddenForm" method="get" hidden>
            <input name="action" type="text">
            <input name="LoadName" type="text">
            <input name="DeleteIdx" type="text">
            <input name="DeleteAll" type="number">
        </form>

        <div id="INSERT">
            <p>Fill information to add new row:</p>
            <form action="mainServlet" name="InsertForm" method="get">
                <c:forEach var="Column" items="${TableColumns}">
                    <div>${TableColumns.get(TableColumns.indexOf(Column))}:
                    <input name="${TableColumns.indexOf(Column)}" type="text">
                    </div><br>
                </c:forEach>
                <button type="submit">Add new row</button>
                <input name="LoadName" type="text" value="${Tables.get(ActiveTableIdx)}" hidden>
            </form>
        </div>
    </div>
</body>
</html>
