package pl.ftims.sc.Madeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.ftims.sc.Madeline.Activation.LinearActivationFunction;
import pl.ftims.sc.Madeline.Madeline.MadelineNetwork;
import pl.ftims.sc.Madeline.Neuron.Neuron;
import pl.ftims.sc.Madeline.Pattern.*;
import pl.ftims.sc.Madeline.WeightsGenerator;

import static pl.ftims.sc.Madeline.Utils.Utils.*;

public class App {

	public static int PATTERN_LENGTH;

	public List<String> trainingPatterns = new ArrayList<>();
	public List<String> testingPatterns = new ArrayList<>();

	public static void main(String[] args) {
		new App().init();
	}

	public void init() {

		trainingPatterns = parseFile(TRAINING_FILE, 0);
		testingPatterns = parseFile(TESTING_FILE, 1);
		System.out.println("Training file parsed without errors. Number of training patterns: "
				+ NUMBER_OF_TRAINING_PATTERNS + " Pattern length: " + PATTERN_LENGTH);
		System.out.println("Testing file parsed without errors. Number of testing patterns: "
				+ NUMBER_OF_TESTING_PATTERNS + " Pattern length: " + PATTERN_LENGTH);

		for (String testingPattern : testingPatterns) {
			System.out.println("Proceeding with pattern \'" + testingPattern + "\'");
			double maxI = 0;
			String maxS = "";
			for (String trainingPattern : trainingPatterns) {
				Neuron trainedNeuron = train(trainingPattern);
				Map<Double, String> outputSum = new HashMap<>();

				double x = test(trainedNeuron, testingPattern);
				outputSum.put(x, testingPattern);
				if(x > maxI){
					maxI = x;
					maxS = trainingPattern;
				}
				if(x > 0.5){
				System.out.println(
						"neuron \t" + trainedNeuron.getId() + " \t wartość \t" + x);
				}
			}
			System.out.println("Sieć rozpoznała wzorzec \'" + maxS + "\' w stopniu " + maxI);

		}

	}

	public Neuron initializeOutputLayer(List<Neuron> inputLayer) {
		Neuron output = null;
		List<Double> inputs = new ArrayList<>();
		List<Double> weights = new ArrayList<>();
		for (Neuron input : inputLayer) {
			inputs.addAll(input.getInput());
			weights.addAll(input.getWeights());

		}
		output = new Neuron(new LinearActivationFunction(), weights, inputs);
		return output;
	}

	public List<String> parseFile(String filename, int patternNumbers) {
		List<String> pattern = new ArrayList<>();
		String line = "";
		try {
			File inputContainer = new File(filename);
			if (!inputContainer.exists()) {
				throw new RuntimeException("ERROR: missing input file");
			}
			FileReader fr = new FileReader(inputContainer);
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				String[] inputs = line.split(";");
				setNumber(patternNumbers, Integer.parseInt(inputs[0]));
				PATTERN_LENGTH = Integer.parseInt(inputs[1]) * Integer.parseInt(inputs[2]);
				for (int i = 0; i < getNumber(patternNumbers); i++) {
					pattern.add(inputs[3 + i]);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pattern;
	}

	public Neuron train(String patternLetter) {
		// training
		try {

			Pattern patterns = patternLoader(TRAINING_FOLDER, patternLetter);

			List<Neuron> inputLayer = initializeInputLayer(patterns, WeightsGenerator.getRandomList(1));
			MadelineNetwork network = new MadelineNetwork(inputLayer);
			inputLayer = network.normalizeInputLayer(patterns);
			Neuron outputLayer = initializeOutputLayer(inputLayer);
			outputLayer.setId(patternLetter);
			return outputLayer;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public double test(Neuron neuron, String patternLetter) {
		try {
			Pattern patterns = patternLoader(PATTERN_FOLDER, patternLetter);

			List<Neuron> inputLayer = initializeInputLayer(patterns, neuron);
			MadelineNetwork network = new MadelineNetwork(inputLayer);
			inputLayer = network.calculateResult(patterns);

			Neuron outputLayer = initializeOutputLayer(inputLayer);
			double sum = outputLayer.calculateOutput();
			return sum;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return 0;
	}

}
