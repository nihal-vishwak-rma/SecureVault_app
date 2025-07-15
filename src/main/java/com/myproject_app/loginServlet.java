package com.myproject_app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class loginServlet extends HttpServlet{

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html");
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		if (username.isEmpty() || password.isEmpty()) {
			req.setAttribute("message", "please fill all information ");
			RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
			rd.include(req, resp);
			return;
		}
		
		Connection connection= null;
		
		
		try {
			connection = DbConnection.getConnection();
			
			String checkuser = "SELECT * FROM users WHERE username = ? ";
			
			PreparedStatement checkuserStatement = connection.prepareStatement(checkuser);
			
			checkuserStatement.setString(1, username);
			
			ResultSet checkRs = checkuserStatement.executeQuery();
			
			if (!checkRs.next()) {
				req.setAttribute("message", "user does not exits please signup !!");
				RequestDispatcher rd = req.getRequestDispatcher("/signup.jsp");
				rd.forward(req, resp);
				return;
			}
			
			
			String checkquery = "SELECT * FROM users WHERE username = ? AND password = ?";
			
			PreparedStatement checkUserStatement = connection.prepareStatement(checkquery);
			checkUserStatement.setString(1, username);
			checkUserStatement.setString(2, password);
			
			ResultSet rs = checkUserStatement.executeQuery();
			
			if (rs.next()) {
				
				HttpSession session = req.getSession();
				session.setAttribute("username", username);
				
				req.setAttribute("message", "Login successfully ");
				RequestDispatcher rd = req.getRequestDispatcher("/dashboard.jsp");
				rd.forward(req, resp);
				return;

			}
			else {
				req.setAttribute("message", "please fill correct username or password ");
				RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
				rd.forward(req, resp);
				return;
			}
			
			
		} catch (Exception e) {
			req.setAttribute("message", "something error please try letter " + e.getMessage());
			RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
			rd.forward(req, resp);
		}
		
		
		
		
	}

}
