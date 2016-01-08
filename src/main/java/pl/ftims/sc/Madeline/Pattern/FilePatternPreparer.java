package pl.ftims.sc.Madeline.Pattern;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilePatternPreparer {

	private static final char ONE = '#';
	private static final char ZERO = '-';
	
	
	private File file;
	private char one;
	private char zero;
	
	public FilePatternPreparer(File file){
		setFile(file);
		this.setOne(ONE);
		this.setZero(ZERO);
	}
	
	public FilePatternPreparer(File file, char one, char zero){
		setFile(file);
		this.setOne(one);
		this.setZero(zero);
	}
	
	public Pattern prepare(){

		List<Double> inputs = new ArrayList<Double>();
		Pattern pattern = new Pattern();
		try {
			inputs = this.parseFile();
			pattern.setInputs(inputs);
			
		} catch(IOException e){
			System.out.println("ERROR");
		}
		return pattern;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	
	public char getOne() {
		return one;
	}

	public void setOne(char one) {
		this.one = one;
	}

	public char getZero() {
		return zero;
	}

	public void setZero(char zero) {
		this.zero = zero;
	}

	private List<Double> parseFile() throws IOException{
		
		List<Double> inputs = new ArrayList<Double>();
		
		FileReader fr = new FileReader(this.getFile());
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		
		while ((line = br.readLine()) != null) {
			List<Double> inputsFromLine = this.getInputsFromLine(line);
			inputs.addAll(inputsFromLine);
		}
		
		br.close();
		return inputs;
		
	}

	private List<Double> getInputsFromLine(String line) {
		
		List<Double> inputs = new ArrayList<Double>();		
		for(char c : line.toCharArray()){
			double value = parseCharToInputValue(c);
			
			inputs.add(value);
		}
		return inputs;
	}

	private double parseCharToInputValue(char c) {
		if(c == this.getOne()){
			return 1;
		} else {
			return 0;
		}
		
	}
	
}
