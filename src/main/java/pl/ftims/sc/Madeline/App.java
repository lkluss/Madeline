package pl.ftims.sc.Madeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.List;

import pl.ftims.sc.Madeline.Activation.LinearActivationFunction;
import pl.ftims.sc.Madeline.Madeline.MadelineNetwork;
import pl.ftims.sc.Madeline.Neuron.Neuron;
import pl.ftims.sc.Madeline.Pattern.*;
import pl.oi.neuron.main.WeightsGenerator;

/**
 * Hello world!
 *
 */
public class App {

	private static final String INPUT_FILE = "input";
	private static final String LETTERS_FOLDER = "patterns\\";
	public static int NUMBER_OF_PATTERNS;
	public static int PATTERN_LENGTH;

	public List<String> patternList = new ArrayList<>();

	public static void main(String[] args) {
		new App().init();
	}

	public void init() {

		parseInputFile();
		System.out.println("Input file parsed without errors.\nNumber of training patterns: " + NUMBER_OF_PATTERNS
				+ "\nPattern length: " + PATTERN_LENGTH);

		
		
		
		// trining
		for (String patternLetter : patternList) {
			System.out.println("Proceeding with pattern: " + patternLetter);
			
			try {

				FilePatternPreparer filePattern = new FilePatternPreparer(new File(LETTERS_FOLDER + patternLetter));
				Pattern patterns = filePattern.prepare();
				
				List<Neuron> inputLayer = initializeInputLayer(patterns);
				List<Neuron> outputLayer = new ArrayList<>();
				
				MadelineNetwork network = new MadelineNetwork(inputLayer);
				
				inputLayer = network.calculateInputLayers(patterns);
				
//				outputLayer.add(new Neuron())

				System.out.println("Neuron \'" + patternLetter + "' odpowiedzial wartoscia " );

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public List<Neuron> initializeInputLayer(Pattern patterns) {
		List<Neuron> neurons = new ArrayList<>();
		List<Double> input = new ArrayList<>();
		for(int i = 0; i < PATTERN_LENGTH; i++) {
			input.clear();
			input.add(patterns.getInputs().get(i));
			neurons.add(new Neuron(new LinearActivationFunction(), WeightsGenerator.getRandomList(1), input)); 
		}

		return neurons;
	}

	
	public 
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
				NUMBER_OF_PATTERNS = Integer.parseInt(inputs[0]);
				PATTERN_LENGTH = Integer.parseInt(inputs[1]) * Integer.parseInt(inputs[2]);

				for (int i = 0; i < NUMBER_OF_PATTERNS; i++) {
					patternList.add(inputs[3 + i]);
				}
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
