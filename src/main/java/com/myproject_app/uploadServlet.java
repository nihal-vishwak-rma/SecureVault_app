package com.myproject_app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.security.MessageDigest;

import fileUploadDao.FileDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig
@WebServlet("/uploadFile")
public class uploadServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Part filePart = req.getPart("fileUpload");

		String uploadPath = getServletContext().getRealPath("/") + "uploadFiles";

		File uploadFolder = new File(uploadPath);

		if (!uploadFolder.exists()) {
			uploadFolder.mkdir();
		}

		HttpSession session = req.getSession(false);

		if (session == null || session.getAttribute("username") == null) {

			resp.sendRedirect(req.getContextPath() + "/login.jsp");
			return;

		}

		String username = (String) session.getAttribute("username");

		String userFolderPath = uploadPath + File.separator + username;

		File userFolder = new File(userFolderPath);

		if (!userFolder.exists()) {
			userFolder.mkdir();
		}

		String originalFileName = filePart.getSubmittedFileName();
		String uniqueName = System.currentTimeMillis() + originalFileName;

		// first way to write file using part interface
		// filePart.write(uploadPath + File.separator +
		// filePart.getSubmittedFileName());

		// second way to write file using inputStream

		File file = new File(userFolderPath, uniqueName);
		try (InputStream input = filePart.getInputStream()) {
			Files.copy(input, file.toPath());
		}

		FileDao filedata = new FileDao();

		PrintWriter out = resp.getWriter();

		String relativePath = "uploadFiles" + File.separator + username + File.separator + uniqueName;

		if (filedata.saveFile(username, relativePath, originalFileName, uniqueName)) {

			req.setAttribute("message", "File uploaded successfully: " + filePart.getSubmittedFileName());
			RequestDispatcher rd = req.getRequestDispatcher("/dashboard.jsp");
			rd.include(req, resp);
		} else {
			out.println("File is not  uploaded something error : " + filePart.getSubmittedFileName());

		}

	}

}
