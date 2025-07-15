package com.myproject_app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import otp.OtpDao;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet{

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String otp = req.getParameter("otp");
		
		if (username.isEmpty() || email.isEmpty() || password.isEmpty() || otp.isEmpty() ) {
			req.setAttribute("message", "please fill all information ");
			RequestDispatcher rd = req.getRequestDispatcher("/signup.jsp");
			rd.include(req, resp);
			return;
		}
		
		OtpDao otpDao = new OtpDao();
		
	    boolean vaildOtp = otpDao.verifyOtp(email, otp);
	    
	    if (!vaildOtp) {
			req.setAttribute("message", "Wrong otp !!");
			req.getRequestDispatcher("/signup.jsp").forward(req, resp);
			return;
	    }
		
		Connection conn = null;
		
		
		
		try {
			
			conn = DbConnection.getConnection();
			conn.setAutoCommit(false);
			
			String checkUserIfExits = "SELECT * FROM users WHERE username = ?";
			
			PreparedStatement preparedStatement = conn.prepareStatement(checkUserIfExits);
			
	       preparedStatement.setString(1, username);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				req.setAttribute("message", "User already exists. Please login.");
				RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
				rd.forward(req, resp);
			}
			else {
				String insertquery = "INSERT INTO users(username , email,password) VALUES (?,?,?)";
				PreparedStatement insert = conn.prepareStatement(insertquery);
				insert.setString(1, username);
				insert.setString(2, email);
				insert.setString(3, password);
				
				int update = insert.executeUpdate();
				
				if (update > 0) {
					conn.commit();
					
					req.setAttribute("message", "Signup successfully  please login ....");
					RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
					rd.forward(req, resp);
				}
				else {
					req.setAttribute("message", "Somethng error please try letter....");
					conn.rollback();
				}
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			req.setAttribute("message", "Somethng error please try letter...." + e.getMessage());
			e.printStackTrace();
		}		
		finally {
            try {
                if (conn != null) conn.close(); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		

	}

}
