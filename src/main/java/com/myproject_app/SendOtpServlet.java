package com.myproject_app;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import otp.OtpDao;
import otp.OtpGen;


@WebServlet("/sendOtp")
public class SendOtpServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String useremail = req.getParameter("email");
		
		if (useremail == null || useremail.isEmpty()) {
            resp.getWriter().println("❌ Email is required.");
            return;
        }
		
		OtpGen otpGen  = new OtpGen();
		
		String otp = otpGen.generate();
	    long expiry = System.currentTimeMillis() + (5 * 60 * 1000);
		
		OtpDao otpDao = new OtpDao();
		otpDao.saveOtp(otp, useremail, expiry);
				
		
		String message = "Your otp is " + otp;
		message = URLEncoder.encode(message,"UTF-8");
		
		// main logic
		
		
		Email from = new Email("YOUR_GMAIL@gmail.com", "SecureVault 🔐");

		
		Email to = new Email(useremail);
		Content content = new Content("text/plain", "Your OTP is: " + otp+ "\nThis OTP will expire in 5 minutes.");
	
		Mail mail = new Mail(from, message, to, content);
		
		 mail.personalization.get(0).addHeader("X-Mailer", "SecureVaultApp Java Mailer");
		
		String sendGridApi = "API KEY";
		
		SendGrid sendGrid = new SendGrid(sendGridApi);
		
		Request request = new Request();
		
		try {
			
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
		    request.setBody(mail.build());
		    
		    Response response = sendGrid.api(request);
		    
		   

		    
		    if (response.getStatusCode() == 202) {
                resp.getWriter().println("✅ OTP sent successfully to your email ( SPAM SECTION ).");
            } else {
                resp.getWriter().println("❌ Failed to send OTP. Try again.");
                System.out.println("SendGrid Error: " + response.getBody());
            }
		    
		    
		} catch (Exception e) {
			  e.printStackTrace();
	            resp.getWriter().println("❌ Server error while sending OTP.");
		}
		  
	     
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		 System.setProperty("java.net.preferIPv4Stack", "true");
	}

	
	
}
