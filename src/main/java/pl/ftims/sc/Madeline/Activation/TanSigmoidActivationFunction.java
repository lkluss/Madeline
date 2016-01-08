package pl.ftims.sc.Madeline.Activation;

public class TanSigmoidActivationFunction implements ActivationFunction {

	@Override
	public double calculateOutput(double netInput) {
		return Math.tanh(netInput);
	}
	
	@Override
    public double calculateDerivative(double netInput){
    	return (1 - Math.pow(Math.tanh(netInput), 2));
    }

}
