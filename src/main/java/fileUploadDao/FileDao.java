package fileUploadDao;

import java.util.List;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.myproject_app.DbConnection;

public class FileDao {

	public boolean saveFile(String username, String filePath, String originalfileName, String storedFileName) {

		Connection connection = null;

		try {

			connection = DbConnection.getConnection();
			connection.setAutoCommit(false);

			String query = "INSERT INTO user_files(username, filepath ,original_filename , stored_filename ) VALUES(?,?,?,?)";

			PreparedStatement insertFileStatement = connection.prepareStatement(query);

			insertFileStatement.setString(1, username);
			insertFileStatement.setString(2, filePath);
			insertFileStatement.setString(3, originalfileName);
			insertFileStatement.setString(4, storedFileName);

			int update = insertFileStatement.executeUpdate();

			if (update > 0) {
				connection.commit();
				System.out.println("file added");
			} else {
				connection.rollback();
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public List<FileData> viewAllFiles(String username) {

		List<FileData> fiilesList = new ArrayList<>();

		Connection connection = null;

		try {

			connection = DbConnection.getConnection();

			String viewFileQuery = "SELECT * FROM user_files WHERE username = ?";
			PreparedStatement addStatement = connection.prepareStatement(viewFileQuery);

			addStatement.setString(1, username);
			ResultSet rs = addStatement.executeQuery();

			while (rs.next()) {
				fiilesList.add(new FileData(rs.getString("original_filename"), rs.getString("stored_filename")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return fiilesList;
	}

	public boolean checkFile(String username, String stored_filename) {

		try (Connection connection = DbConnection.getConnection()) {

			String queryCheckFile = "SELECT * FROM user_files WHERE username = ? AND stored_filename = ? ";

			PreparedStatement preparedStatement = connection.prepareStatement(queryCheckFile);

			preparedStatement.setString(1, username);
			preparedStatement.setString(2, stored_filename);

			ResultSet rs = preparedStatement.executeQuery();

			if (!rs.next()) {

				return false;

			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean deleteFile(String username, String stored_filename , String uploadPath) {
		
		

		try (Connection connection = DbConnection.getConnection()) {

			String queryCheckFile = "DELETE FROM user_files WHERE username = ? AND stored_filename = ? ";

			PreparedStatement preparedStatement = connection.prepareStatement(queryCheckFile);

			preparedStatement.setString(1, username);
			preparedStatement.setString(2, stored_filename);

			int update = preparedStatement.executeUpdate();

			if (update > 0) {
				
				File file = new File(uploadPath + File.separator + stored_filename);
				if (file.exists()) {
				    file.delete();
				}

				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
