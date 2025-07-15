<%@page import="java.io.File"%>
<%@page import="fileUploadDao.FileData"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%
    String usernamecheck = (String) session.getAttribute("username");
    if (usernamecheck == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%
    List<FileData> files = (List<FileData>) request.getAttribute("files");
    String message = (String) request.getAttribute("message");
   
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Uploaded Files</title>
    <style>
        body {
            background-color: #111;
            color: #fff;
            font-family: 'Segoe UI', sans-serif;
            padding: 30px;
            text-align: center;
        }

        h2 {
            color: crimson;
            margin-bottom: 20px;
        }

        table {
            margin: auto;
            border-collapse: collapse;
            width: 70%;
            background-color: #222;
            border-radius: 8px;
            overflow: hidden;
        }

        th, td {
            padding: 12px 20px;
            border-bottom: 1px solid #444;
        }

        th {
            background-color: crimson;
            color: white;
        }

        tr:hover {
            background-color: #333;
        }

        .btn {
            padding: 8px 15px;
            margin: 2px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-weight: bold;
        }

        .btn-view {
            background-color: #3498db;
            color: white;
        }

        .btn-download {
            background-color: #2ecc71;
            color: white;
        }

        .btn-delete {
            background-color: #e74c3c;
            color: white;
        }
    </style>
</head>
<body>

    <h2>📂 Uploaded Files</h2>

    <%
    
    String username = (String) session.getAttribute("username");
        if (message != null ) {
    %>
        <p><%= message %></p>
       
    <%
        } else if (files != null && !files.isEmpty()) {
    %>

    <table>
        <tr>
            <th>File Name</th>
            <th>Actions</th>
        </tr>

        <%
            for (FileData fileName : files) {
  
            	String viewPath = "uploadFiles/" + username + "/" + fileName.getStored_name();

        %>
            <tr>
                <td><%= fileName.getOriginal_name() %></td>
                <td>
                    <!-- View Button -->
                    <a class="btn btn-view" href="<%= viewPath %>" target="_blank">👁️ View</a>

                    <!-- Download Button -->
                   
                    
                     <form action="downloadFile" method="get" style="display:inline;">
                        <input type="hidden" name="stored_filename" value="<%= fileName.getStored_name() %>">
                        <button class="btn btn-download" type="submit">⬇️ Download</button>
                    </form>

                    <!-- Delete Button -->
                    <form action="deleteFile" method="post" style="display:inline;">
                        <input type="hidden" name="stored_filename" value="<%= fileName.getStored_name() %>">
                        <button class="btn btn-delete" type="submit">❌ Delete</button>
                    </form>
                </td>
            </tr>
        <%
            }
        %>
    </table>

    <%
        }
    %>

</body>
</html>
