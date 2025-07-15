package com.myproject_app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
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

@WebServlet("/downloadFile")
public class DownloadServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String filename = req.getParameter("stored_filename");
		
		HttpSession session = req.getSession(false);
		
		if ( session == null || session.getAttribute("username") == null ) {
			
			req.setAttribute("message", "please login first ");
			resp.sendRedirect(req.getContextPath()+ "/login.jsp");
			return;
		}
		
		String username = (String) session.getAttribute("username");
		
		try (Connection connection = DbConnection.getConnection()){
			
			
			String queryCheckFile = "SELECT * FROM user_files WHERE username = ? AND stored_filename = ? ";
			
			PreparedStatement preparedStatement = connection.prepareStatement(queryCheckFile);
			
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, filename);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if (!rs.next()) {
				 resp.setContentType("text/html");
	                resp.getWriter().println("<p style='color:red;'>No such file found for this user.</p>");
	                return;
				
			}
			
			String uploadPath =  getServletContext().getRealPath("/") + "uploadFiles/" + username;
			
			File file = new File(uploadPath , filename); 
			
			if (!file.exists()) {
				 resp.setContentType("text/html");
			        resp.getWriter().println("<p style='color:red;'>File not found on server.</p>");
			        return;
			}
			
			String originalName = rs.getString("original_filename");
			
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=\"" + originalName + "\"");
			resp.setContentLengthLong( file.length());
			
			try(FileInputStream fileInputStream = new FileInputStream(file)) {
				
				OutputStream out = resp.getOutputStream();
				
				byte[] bytes = new byte[1024 * 64];
				
				int i;
				
				while ((i = fileInputStream.read(bytes)) != -1) {
					
					out.write(bytes, 0, i);
				}
				out.flush();
				
			} 
			
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.setContentType("text/html");
            resp.getWriter().println("<p style='color:red;'>Server error occurred.</p>");
		}
		
		
		
	}

}
