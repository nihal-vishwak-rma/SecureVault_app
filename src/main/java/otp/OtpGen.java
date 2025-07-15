package otp;

import java.security.SecureRandom;

public class OtpGen {

	
	public String generate() {
		
		SecureRandom random = new SecureRandom();
        int otp = 10000 + random.nextInt(90000); // 5-digit OTP
        return  String.valueOf(otp);
        
	}
	
}
