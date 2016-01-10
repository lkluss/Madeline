package pl.ftims.sc.Madeline.Pattern;

import java.util.List;
public class Pattern {
	private List<Double> inputs;
	
	public Pattern(List<Double> inputs){
		setInputs(inputs);
	}

	public Pattern() {
	}

	public List<Double> getInputs() {
		return inputs;
	}

	public void setInputs(List<Double> inputs) {
		this.inputs = inputs;
	}

	public int sumOnes() {
		int sum = 0;
		for (Double double1 : inputs) {
			if(double1==1) sum++;
		}
		return sum;
	}
	
}
