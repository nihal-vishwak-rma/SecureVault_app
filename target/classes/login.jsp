<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>

 <link rel="stylesheet" type="text/css" href="<%= application.getContextPath() %>/css/login.css">


</head>
<body>

<h1 id="log"> Login </h1>

<% String msg = (String) request.getAttribute("message"); %>
<% if (msg != null) { %>
    <p style="color:red;"><%= msg %></p>
<% } %>


<form action="<%= application.getContextPath() %>/login" method="post"> 

 <label class="leb"  for="username"><h5>Enter your username</h5></label>
  <input class="in" type="text" id="username" name="username" placeholder ="Enter username " required> 
  
  <label class="leb" for="password"><h5>Enter your password</h5></label>
  <input class="in"  type="password" id="password" name="password" placeholder ="Enter  password " required> <br>
  
  <input id="click" type="submit" value="Login" >

</form>

</body>
</html>