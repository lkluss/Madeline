package pl.ftims.sc.Madeline.Activation;

public interface ActivationFunction {
	
	public double calculateOutput(double netInput);
	public double calculateDerivative(double netInput);
	
}