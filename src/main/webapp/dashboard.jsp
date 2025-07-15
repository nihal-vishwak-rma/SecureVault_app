<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>

 <style>
    body {
        margin: 0;
        padding: 0;
        background-color: #111;
        color: white;
        font-family: Arial, sans-serif;
        height: 100vh;
        display: flex;
        flex-direction: column;
    }
    

    .top-bar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 10px 20px;
    }
    
    .message-box {
     
     color: green;
}


    

    .logout-btn {
        align-self: flex-start;
    }

    .center-content {
        flex-grow: 1;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        text-align: center;
    }

    h2 {
        color: crimson;
    }

    .btn1 {
        background: #333;
        color: white;
        padding: 10px 20px;
        margin: 10px;
        text-decoration: none;
        border: 1px solid crimson;
        border-radius: 5px;
    }

    .btn1:hover {
        background: crimson;
        color: white;
        cursor: pointer;
    }

    .btn2 {
        background: #333;
        color: white;
        padding: 10px 20px;
        text-decoration: none;
        border: 1px solid crimson;
        border-radius: 20px;
    }

    .btn2:hover {
        background: crimson;
        color: white;
        cursor: pointer;
    }

    form {
        margin: 20px 0;
    }

    input[type="submit"] {
        padding: 8px 16px;
        background-color: crimson;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }
    
    
</style>

</head>
<body>
    <% String msg = (String) request.getAttribute("message"); %>
    <% if (msg != null) { %>
        <p class="message.box" ><%= msg %></p>
    <% } %>

    <% String user = (String) session.getAttribute("username"); 
       if (user == null) {
           response.sendRedirect("login.jsp"); 
           return;
       }
    %>

    <div class="top-bar">
       <form action="<%= request.getContextPath() %>/logout" method="post" style="position: absolute; top: 20px; left: 20px;">
    <button type="submit" class="btn2">🚪 Logout</button>
</form>

    </div>

    <div class="center-content">
        <h1>Welcome <%= session.getAttribute("username") %></h1>

        <form action="<%= application.getContextPath() %>/uploadFile" method="post" enctype="multipart/form-data">
            <input id="fileid" type="file" name="fileUpload" required="required">
            <input type="submit" value="Upload">
        </form>
 
 		<a href="viewUploads" class="btn1">📂 View My Files</a>

		
       
    </div>
</body>

</html>