package pl.ftims.sc.Madeline.Madeline;

import java.util.ArrayList;
import java.util.List;

import pl.ftims.sc.Madeline.Neuron.Neuron;
import pl.ftims.sc.Madeline.Pattern.Pattern;

public class MadelineNetwork {

	private List<Neuron> inputNeurons;

	public MadelineNetwork(List<Neuron> neurons) {
		this.setNeurons(neurons);
	}

	public List<Neuron> getNeurons() {
		return inputNeurons;
	}

	public void setNeurons(List<Neuron> neurons) {
		this.inputNeurons = neurons;
	}

	public List<Neuron> calculateResult(Pattern patterns) {
		return computeResult(this.getNeurons(), patterns);
	}

	public List<Neuron> normalizeInputLayer(Pattern patterns) {
		return computeInputLayer(this.getNeurons(), patterns);
	}

	public List<Neuron> computeInputLayer(List<Neuron> neurons, Pattern patterns) {

		for (Neuron neuron : neurons) {
			neuron.setWeights(normalize(neuron.getInput(), patterns.sumOnes()));
		}
		return neurons;
	}

	public List<Neuron> computeResult(List<Neuron> neurons, Pattern patterns) {
		for (Neuron neuron : neurons) {
			neuron.setInput(normalize(neuron.getInput(), patterns.sumOnes()));
		}
		return neurons;
	}

	public List<Double> normalize(List<Double> inputs, int ones) {
		List<Double> output = new ArrayList<>();
		for (int i = 0; i < inputs.size(); i++) {
			output.add(inputs.get(i) / Math.sqrt(ones));
		}
		return output;

	}

}
