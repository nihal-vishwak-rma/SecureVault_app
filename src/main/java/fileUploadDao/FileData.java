package fileUploadDao;

public class FileData {

	private String original_name;
	private String stored_name;
	
	
	public FileData(String original_name, String stored_name) {
		
		this.original_name = original_name;
		this.stored_name = stored_name;
	}

	public String getOriginal_name() {
		return original_name;
	}

	public String getStored_name() {
		return stored_name;
	}

	
}
