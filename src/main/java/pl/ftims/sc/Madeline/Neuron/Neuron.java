package pl.ftims.sc.Madeline.Neuron;

import java.util.List;

import pl.ftims.sc.Madeline.Activation.ActivationFunction;

public class Neuron {

	private String id;

	private List<Double> weights;
	private List<Double> input;

	private ActivationFunction activationFunction;

	public Neuron(ActivationFunction activationFunction, List<Double> weights, List<Double> input) {
		this.weights = weights;
		this.activationFunction = activationFunction;
		this.input = input;
	}

	public List<Double> getWeights() {
		return weights;
	}

	public void setWeights(List<Double> weights) {
		this.weights = weights;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public void setActivationFunction(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
	}

	public double calculateOutput() {		
		
		if (input.size() != weights.size()) {
			throw new RuntimeException("Error: wrong input list provided.\n" + "Neuron expects " + weights.size()
					+ " inputs.\n" + input.size() + " given.");
		}
		
		double netInput = calculateWeightedSum(input);
		return activationFunction.calculateOutput(netInput);

	}

	public double calculateWeightedSum(List<Double> inputs) {
		double netInput = 0.0;
		for (int i = 0; i < weights.size(); i++) {
			netInput += weights.get(i) * inputs.get(i);
		}
		return netInput;
	}

	public void presentWeights() {

		String printable = "WEIGHTS: ";

		for (Double weight : weights) {
			printable += weight + ", ";
		}

		String substr = printable.substring(0, printable.length() - 1);

		System.out.println(substr);

	}

	public void presentInputs() {

		String printable = "INPUTS: ";

		for (Double input : input) {
			printable += input + ", ";
		}

		String substr = printable.substring(0, printable.length() - 1);

		System.out.println(substr);

	}

	
	public List<Double> getInput() {
		return input;
	}

	public void setInput(List<Double> input) {
		this.input = input;
	}

	public void updateWeight(double value, int i) {

		this.getWeights().set(i, value);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
