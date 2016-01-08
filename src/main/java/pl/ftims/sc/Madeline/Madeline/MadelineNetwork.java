package pl.ftims.sc.Madeline.Madeline;

import java.util.ArrayList;
import java.util.List;

import pl.ftims.sc.Madeline.Neuron.Neuron;
import pl.ftims.sc.Madeline.Pattern.Pattern;

public class MadelineNetwork {
	private	List<Neuron> neurons;
	public MadelineNetwork(List<Neuron> neurons) {
		this.setNeurons(neurons);
	}
	
	public List<Double> calculateResult(Pattern patterns){
		return computeResult(this.getNeurons(), patterns);
	}

	public List<Double> calculateInputLayers(Pattern patterns){
		return computeResult(this.getNeurons(), patterns);
	}
	public List<Neuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}
	
	public List<Neuron> computeInputLayer(List<Neuron> neurons, Pattern patterns) {
		for(Neuron neuron : neurons){
			neuron.normalize(patterns.sumOnes());
		}
		return neurons;

		
	}
	public List<Double> computeResult(List<Neuron> neurons, Pattern patterns) {
		List<Double> result = new ArrayList<>();
		
		for(Neuron neuron : neurons){
			double value = neuron.calculateOutput();
			result.add(value);
			
		}
		
		return result;
		
	}
	
	private double normalize(double value, int ones){
		return value / Math.sqrt(ones);
	}
	
	
}
