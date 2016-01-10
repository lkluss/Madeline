package pl.ftims.sc.Madeline.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.ftims.sc.Madeline.Activation.LinearActivationFunction;
import pl.ftims.sc.Madeline.Neuron.Neuron;
import pl.ftims.sc.Madeline.Pattern.FilePatternPreparer;
import pl.ftims.sc.Madeline.Pattern.Pattern;

public final class Utils {
	
	public static final String TRAINING_FOLDER = "train\\";
	public static final String PATTERN_FOLDER = "patterns\\";
	
	public static final String TRAINING_FILE = "training_input";
	public static final String TESTING_FILE = "testing_input";
	
	public static int NUMBER_OF_TRAINING_PATTERNS;
	public static int NUMBER_OF_TESTING_PATTERNS;
	
	public static Pattern patternLoader(String foldername, String filename){
		FilePatternPreparer filePattern = new FilePatternPreparer(new File(foldername + filename));
		return filePattern.prepare();
	}
	
	public static List<Neuron> initializeInputLayer(Pattern patterns, List<Double> weights) {
		List<Neuron> neurons = new ArrayList<>();
		for (int i = 0; i < patterns.getInputs().size(); i++) {
			List<Double> input = new ArrayList<>();
			input.add(patterns.getInputs().get(i));
			neurons.add(new Neuron(new LinearActivationFunction(),weights, input));
		}
		return neurons;
	}
	
	public static List<Neuron> initializeInputLayer(Pattern patterns, Neuron trainedWeights) {
		List<Neuron> neurons = new ArrayList<>();
		for (int i = 0; i < patterns.getInputs().size(); i++) {
			List<Double> input = new ArrayList<>();
			List<Double> weight = new ArrayList<>();
	
			input.add(patterns.getInputs().get(i));
			weight.add(trainedWeights.getWeights().get(i));
			neurons.add(new Neuron(new LinearActivationFunction(),weight, input));
		}
		return neurons;
	}
	
	public static void setNumber(int number, int value) {
		if (number == 1) {
			NUMBER_OF_TESTING_PATTERNS = value;
		} else {
			NUMBER_OF_TRAINING_PATTERNS = value;
		}
	}

	public static int getNumber(int number) {
		if (number == 1) {
			return NUMBER_OF_TESTING_PATTERNS;
		} else {
			return NUMBER_OF_TRAINING_PATTERNS;
		}
	}
}
