package com.myproject_app;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import fileUploadDao.FileDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/deleteFile")
public class DeleteServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String filename = req.getParameter("stored_filename");

		HttpSession session = req.getSession(false);

		if (session == null || session.getAttribute("username") == null) {

			req.setAttribute("message", "please login first ");
			resp.sendRedirect(req.getContextPath() + "/login.jsp");
			return;
		}

		String username = (String) session.getAttribute("username");

		FileDao fileDao = new FileDao();

		String uploadPath = getServletContext().getRealPath("/") + "uploadFiles" + File.separator + username;

		if (fileDao.checkFile(username, filename)) {

			if (fileDao.deleteFile(username, filename, uploadPath)) {
				req.setAttribute("message", "File deleted successfully");
				req.setAttribute("files", fileDao.viewAllFiles(username));

			} else {
				req.setAttribute("message", "❌ File not deleted due to server error.");
			}

			RequestDispatcher rd = req.getRequestDispatcher("/viewUploads.jsp");
			rd.forward(req, resp);

		} else {
			req.setAttribute("message", "⚠️ File not found in your account.");
			RequestDispatcher rd = req.getRequestDispatcher("/viewUploads.jsp");
			rd.forward(req, resp);
		}

	}

}
