package com.myproject_app;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class logout extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(false);
		
		if (session != null) {
			session.invalidate();
			req.setAttribute( "message", "You have been logged out.");
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.jsp");
			requestDispatcher.forward(req, resp);
		}
		else {
		    resp.sendRedirect("login.jsp");
		}

	
	}

	
}
