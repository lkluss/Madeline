package pl.ftims.sc.Madeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {

	private static final String INPUT_FILE = "input";
	
	public FileLoader(){
	}
	
	public void parseInputFile() {
		String line = "";
		try {
			File inputContainer = new File(INPUT_FILE);
			if (!inputContainer.exists()) {
				throw new RuntimeException("ERROR: missing input file");
			}
			FileReader fr = new FileReader(inputContainer);
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				String[] inputs = line.split(";");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
