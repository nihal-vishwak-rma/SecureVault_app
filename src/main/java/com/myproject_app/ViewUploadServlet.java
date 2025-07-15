package com.myproject_app;

import java.io.IOException;
import java.util.List;

import fileUploadDao.FileDao;
import fileUploadDao.FileData;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/viewUploads")

public class ViewUploadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(false);

		if (session == null || session.getAttribute("username") == null) {
		    resp.sendRedirect("login.jsp");
		    return;
		}
		
		 String username = (String) session.getAttribute("username");
		
		FileDao uploadedfiles = new FileDao();
		
		List<FileData> fileList = uploadedfiles.viewAllFiles(username);
		
		
		if (fileList == null ||  fileList.isEmpty()) {
		    req.setAttribute("message", "No files found.");
		}
		else {
			req.setAttribute("files", fileList);
			
		}
		RequestDispatcher rd = req.getRequestDispatcher("/viewUploads.jsp");
		rd.forward(req, resp);

		
		
		

	}

}
