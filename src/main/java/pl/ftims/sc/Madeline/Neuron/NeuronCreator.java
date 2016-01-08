package pl.ftims.sc.Madeline.Neuron;

import java.util.ArrayList;
import java.util.List;

public class NeuronCreator {

	private String folderName;
	
	public NeuronCreator(String folderName) {
		setFolderName(folderName);
	}
	
	public List<Neuron> createNeurons(){
		List<Neuron> neurons = new ArrayList<Neuron>();
		
		return neurons;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

}
