<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Signup Page</title>
    <link rel="stylesheet" type="text/css" href="<%= application.getContextPath() %>/css/signup.css">

    <!-- ✅ JavaScript for Send OTP -->
  <script>
function sendOtpHandler() {
    const email = document.getElementById('email').value;

    if (!email) {
        alert("Please enter your email first.");
        return;
    }

    fetch('<%= application.getContextPath() %>/sendOtp', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'email=' + encodeURIComponent(email)
    })
    .then(response => response.text())
    .then(data => {
        alert(data); // show success/failure from servlet
    })
    .catch(error => {
        console.error('Error:', error);
        alert('❌ Failed to send OTP');
    });
}
</script>

</head>
<body>

<h1 id="sig">Signup</h1>

<%-- ✅ Show message if exists --%>
<%
    String msg = (String) request.getAttribute("message");
    if (msg != null) {
%>
    <p><%= msg %></p>
<%
    }
%>

<!-- ✅ Single Form -->
<form id="signupForm" action="<%= application.getContextPath() %>/signup" method="post">

    <!-- Username -->
    <label class="leb2" for="username"><h5>Enter your username</h5></label>
    <input class="in" type="text" id="username" name="username" placeholder="Enter username" required>

    <!-- Phone -->
    <label class="leb2" for="email"><h5>Enter your Email</h5></label>
    <input class="in" type="email" id="email" name="email" placeholder="Enter email" required >

    <!-- Password -->
    <label class="leb2" for="password"><h5>Enter your password</h5></label>
    <input class="in" type="password" id="password" name="password" placeholder="Enter password" required>

    <!-- OTP -->
    <label class="leb2" for="otp"><h5>Enter your OTP</h5></label>
    <input class="in" type="text" id="otp" name="otp" placeholder="Enter OTP" required>

    <!-- ✅ Send OTP Button -->
    <button type="button"
        onclick="sendOtpHandler()"
        style="margin-top: 10px; background-color: #00ffff; border: none; padding: 10px; border-radius: 6px; cursor: pointer; font-weight: bold;">
        📩 Send OTP
    </button>

    <!-- ✅ Submit Signup -->
    <input id="submit" type="submit" value="Signup">

</form>

</body>
</html>
