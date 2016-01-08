package pl.ftims.sc.Madeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import pl.ftims.sc.Madeline.Neuron.Neuron;

public class DeltaRuleTrainer {

	private Double learningRate;
	private Neuron neuron;

	private double epsilon = 0.01;

	private int iteration = 0;

	public DeltaRuleTrainer(Double learningRate, Neuron neuron) {
		this.learningRate = learningRate;
		this.neuron = neuron;
	}

	public void train(List<Double> inputs, Double desiredOutput) {

		while (Math.abs(neuron.calculateOutput(inputs) - desiredOutput) > epsilon) {

			List<Double> newWeights = new ArrayList<Double>();

			for (int i = 0; i < inputs.size(); i++) {
				Double delta = findDelta(inputs, desiredOutput, i);
				Double newWeight = neuron.getWeights().get(i) + delta;
				newWeights.add(newWeight);
			}

			Collections.copy(neuron.getWeights(), newWeights);
			neuron.setWeights(newWeights);
		}

	}

	public void trainForList(List<List<Double>> inputsList,
			List<Double> desiredOutputs) {

		List<Double> outputs = calculateOutputs(inputsList);
		Counter counter = new Counter();
		while (!areValidOutputs(outputs, desiredOutputs)) {

			List<Double> newWeights = new ArrayList<Double>();

			for (int i = 0; i < inputsList.get(0).size(); i++) {
				counter.increment();
				System.out.println("Counter: " + counter.getValue());
				Double delta = findDeltaForList(inputsList, desiredOutputs, i);
				Double newWeight = neuron.getWeights().get(i) + delta;
				newWeights.add(newWeight);
			}

			Collections.copy(neuron.getWeights(), newWeights);
			neuron.setWeights(newWeights);
			outputs = calculateOutputs(inputsList);
		}

	}

	public void trainForListParallel(final List<List<Double>> inputsList,
			final List<Double> desiredOutputs) {
		List<Double> outputs= calculateOutputs(inputsList);
		final Counter counter = new Counter(); 
		while (!areValidOutputs(outputs, desiredOutputs)) {

			List<Double> newWeights = new ArrayList<Double>();

			int threads = Runtime.getRuntime().availableProcessors();
			List<Future<Double>> list = new ArrayList<Future<Double>>();
			ExecutorService executor = Executors.newFixedThreadPool(threads);
			for (int i = 0; i < inputsList.get(0).size(); i++) {
				final int j = i;
				Callable<Double> worker = new Callable<Double>() {
					@Override
					public Double call() throws Exception {
						counter.increment();
						System.out.println("Counter " + counter.getValue() + " on proc " + Thread.currentThread().getId());
						Double delta = findDeltaForList(inputsList,
								desiredOutputs, j);
						Double newWeight = neuron.getWeights().get(j) + delta;
						return newWeight;
					}
				};
				list.add(executor.submit(worker));
			}
			executor.shutdown();
			while (!executor.isTerminated()) {}
			
			for (Future<Double> future : list)
			try {
				newWeights.add(future.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Collections.copy(neuron.getWeights(), newWeights);
			neuron.setWeights(newWeights);
			outputs = calculateOutputs(inputsList);
		}
	}
	public void trainForListOnline(List<List<Double>> inputsList,
			List<Double> desiredOutputs) {

		List<Double> outputs = calculateOutputs(inputsList);

		while (!areValidOutputs(outputs, desiredOutputs)) {
			for (int i = 0; i < inputsList.get(0).size(); i++) {
				updateDeltaForListOnline(inputsList, desiredOutputs, i);
			}
			outputs = calculateOutputs(inputsList);
		}
	}

	private Double findDelta(List<Double> inputs, Double desiredOutput, int i) {
		Double output = neuron.calculateOutput(inputs);

		// System.out.println(iteration + ": " + output);
		setIteration(getIteration() + 1);

		Double netInput = neuron.calculateWeightedSum(inputs);
		Double derivative = neuron.getActivationFunction().calculateDerivative(
				netInput);
		Double delta = learningRate * (desiredOutput - output);
		delta *= derivative;
		delta *= inputs.get(i);

		return delta;
	}

	private void updateDeltaForListOnline(List<List<Double>> inputsList,
			List<Double> desiredOutputs, int i) {

		Double delta = 0.0;
		Double newWeight;

		for (int patternIndex = 0; patternIndex < inputsList.size(); patternIndex++) {
			delta = findDelta(inputsList.get(patternIndex),
					desiredOutputs.get(patternIndex), i);
			newWeight = neuron.getWeights().get(i) + delta;
			neuron.updateWeight(newWeight, i);
		}

	}

	private double findDeltaForList(List<List<Double>> inputsList,
			List<Double> desiredOutputs, int i) {

		List<Double> outputs = calculateOutputs(inputsList);

		Double differenceSum = 0.0;

		for (int patternIndex = 0; patternIndex < inputsList.size(); patternIndex++) {
			differenceSum += ((desiredOutputs.get(patternIndex) - outputs
					.get(patternIndex)) * inputsList.get(patternIndex).get(i)
					);
		}

		Double delta = learningRate * differenceSum;

		return delta;

	}

	private boolean areValidOutputs(List<Double> outputs,
			List<Double> desiredOutputs) {

		boolean areValid = true;

		for (int i = 0; i < outputs.size(); i++) {
			if (Math.abs(outputs.get(i) - desiredOutputs.get(i)) > epsilon) {
				areValid = false;
				break;
			}
		}

		return areValid;

	}

	private List<Double> calculateOutputs(List<List<Double>> inputsList) {

		List<Double> outputs = new ArrayList<Double>();

		for (List<Double> inputs : inputsList) {
			Double output = neuron.calculateOutput(inputs);
			outputs.add(output);
		}

		System.out.print("\n");

		return outputs;

	}
	public int getIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}
	public class Counter {
		  private AtomicInteger value = new AtomicInteger(); 
		  public int getValue(){
		    return value.get();
		  }
		  public int increment(){
		    return value.incrementAndGet();
		  }
		  public int incrementLongVersion(){
			    int oldValue = value.get();
			    while (!value.compareAndSet(oldValue, oldValue+1)){
			       oldValue = value.get();
			    }
			    return oldValue+1;
			  }
	}
}
