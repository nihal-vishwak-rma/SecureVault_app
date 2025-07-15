package otp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.myproject_app.DbConnection;

public class OtpDao {

	public boolean saveOtp(String otp, String email, long expiry) {

		try (Connection connection = DbConnection.getConnection()) {

			connection.setAutoCommit(false);

			String checkMobileQuery = "SELECT * FROM otp_verification WHERE email = ?";

			PreparedStatement phoneExitCheckStatement = connection.prepareStatement(checkMobileQuery);

			phoneExitCheckStatement.setString(1, email);

			ResultSet rs = phoneExitCheckStatement.executeQuery();

			if (!rs.next()) {

				String insertMobileQuery = "INSERT INTO  otp_verification(email , otp_code , expires_at) VALUES (?,?,?)";

				PreparedStatement insertPhoneStatement = connection.prepareStatement(insertMobileQuery);

				insertPhoneStatement.setString(1, email);
				insertPhoneStatement.setString(2, otp);
				insertPhoneStatement.setTimestamp(3, new Timestamp(expiry));

				int update = insertPhoneStatement.executeUpdate();

				if (update > 0) {

					connection.commit();

					return true;
				} else {
					connection.rollback();

				}

			} else {

				String updateOtp = "UPDATE otp_verification SET otp_code = ?, expires_at = ? WHERE email = ?";

				PreparedStatement updateStatement = connection.prepareStatement(updateOtp);

				updateStatement.setString(1, otp);
				updateStatement.setTimestamp(2, new Timestamp(expiry));
				updateStatement.setString(3, email);

				int update = updateStatement.executeUpdate();

				if (update > 0) {

					connection.commit();
					System.out.println("otp  update  " + otp + "email id " + email);
					return true;
				} else {
					connection.rollback();
					
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return true;
	}

	public boolean verifyOtp(String email, String otp) {

		try (Connection connection = DbConnection.getConnection()) {

			String query = "SELECT * FROM otp_verification WHERE email = ? AND otp_code = ? AND expires_at > CURRENT_TIMESTAMP";
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, email.trim());
			ps.setString(2, otp.trim());


			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				String DeleteQuery = "DELETE FROM otp_verification WHERE email = ? AND otp_code = ?";

				PreparedStatement deleteStatement = connection.prepareStatement(DeleteQuery);

				deleteStatement.setString(1, email);
				deleteStatement.setString(2, otp);

				int update = deleteStatement.executeUpdate();

				if (update > 0) {
					System.out.println("🔍 Verifying OTP: " + otp + " for email: " + email);

	                return true;
	            } else {
	                System.out.println("❌ OTP verified but not deleted.");
	                return false;
	            }
			}
			else {
				 System.out.println("❌ Invalid or expired OTP.");
		            return false;
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		
	}
}
