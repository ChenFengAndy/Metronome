package tv.floe.metronome.deeplearning.neuralnetwork.gradient;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;




public class MultiLayerGradient {

	private static final long serialVersionUID = 5262146791172613616L;
	private List<NeuralNetworkGradient> gradients;
	private LogisticRegressionGradient logRegGradient;
	
	
	
	
	public MultiLayerGradient(List<NeuralNetworkGradient> gradients,
			LogisticRegressionGradient logRegGradient) {
		super();
		this.gradients = gradients;
		this.logRegGradient = logRegGradient;
	}
/*
	@Override
	public void write(OutputStream os) {
		SerializationUtils.writeObject(this, os);
	}

	@Override
	public void load(InputStream is) {
		MultiLayerGradient read = SerializationUtils.readObject(is);
		this.gradients = read.gradients;
		this.logRegGradient = read.logRegGradient;
	}
*/
	public void div(int num) {
		
		for (NeuralNetworkGradient g : gradients) {
			g.div(num);
		}
		
			
	}
	
/*
	@Override
	public MultiLayerGradient clone() {
		return org.apache.commons.lang3.SerializationUtils.clone(this);
	}
*/
	
	public void addGradient(MultiLayerGradient other) {
		for(int i = 0;i < gradients.size(); i++) {
			gradients.get(i).add(other.getGradients().get(i));
		}
		
		logRegGradient.add(other.getLogRegGradient());
	}
	
	public  List<NeuralNetworkGradient> getGradients() {
		return gradients;
	}

	public  void setGradients(List<NeuralNetworkGradient> gradients) {
		this.gradients = gradients;
	}

	public  LogisticRegressionGradient getLogRegGradient() {
		return logRegGradient;
	}

	public  void setLogRegGradient(
			LogisticRegressionGradient logRegGradient) {
		this.logRegGradient = logRegGradient;
	}	
	
}
