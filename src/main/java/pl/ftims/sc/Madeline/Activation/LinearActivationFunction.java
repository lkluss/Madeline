package pl.ftims.sc.Madeline.Activation;

public class LinearActivationFunction implements ActivationFunction {

	@Override
	public double calculateOutput(double netInput) {
		return netInput;
	}

	@Override
	public double calculateDerivative(double netInput) {
		return 1.0;
	}

}
