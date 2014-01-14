package tv.floe.metronome.deeplearning.neuralnetwork.optimize;

import cc.mallet.optimize.InvalidOptimizableException;
import cc.mallet.optimize.Optimizable;
import cc.mallet.optimize.OptimizationException;
import cc.mallet.optimize.Optimizer;

import java.io.Serializable;

import org.apache.mahout.math.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import tv.floe.metronome.deeplearning.neuralnetwork.core.BaseMultiLayerNeuralNetworkVectorized;
import tv.floe.metronome.math.MatrixUtils;


public class MultiLayerNetworkOptimizer implements Optimizable.ByGradientValue,Serializable {

	protected BaseMultiLayerNeuralNetworkVectorized network;

	private static Logger log = LoggerFactory.getLogger(MultiLayerNetworkOptimizer.class);
	private double learningRate;
	private transient Optimizer optimizer;
	
	public MultiLayerNetworkOptimizer(BaseMultiLayerNeuralNetworkVectorized net, double lr) {
		
		this.network = net;
		this.learningRate = lr;
		
	}
	
	public void optimize(Matrix labels, double learningRate, int epochs) {
		
		MatrixUtils.ensureValidOutcomeMatrix(labels);
		//sample from the final layer in the network and train on the result
		Matrix layerInput = network.hiddenLayers[network.hiddenLayers.length - 1].sampleHiddenGivenLastVisible();
		
		network.outputLayer.inputTrainingData = layerInput;
		network.outputLayer.outputTrainingLabels = labels;

		
		Matrix w = network.outputLayer.connectionWeights.clone();
		Matrix b = network.outputLayer.biasTerms.clone();
		
		Double currLoss = null;
		Integer numTimesOver = null;

		for(int i = 0; i < epochs; i++) {

			network.outputLayer.train( layerInput, labels, learningRate );
			learningRate *= network.learningRateUpdate;
			
			if (currLoss == null) {
				
				currLoss = network.negativeLogLikelihood();
				
			} else {
				
				Double loss = network.negativeLogLikelihood();
				
				if (loss > currLoss) {
					
					if (numTimesOver == null) {
						
						numTimesOver = 1;
						
					} else {
						
						numTimesOver++;
						
					}
					
					if (numTimesOver >= 5) {
						
						log.info("Reverting weights and exiting...");
						network.outputLayer.connectionWeights = w.clone();
						network.outputLayer.biasTerms = b.clone();
						break;
						
					}
					
				} else if (loss < currLoss) {
					
					w = network.outputLayer.connectionWeights.clone();
					b = network.outputLayer.biasTerms.clone();
					currLoss = loss;
					
				}



			}

			log.info("Negative log likelihood on epoch " + i + " " + network.negativeLogLikelihood());
		}
		
		double curr = network.negativeLogLikelihood();
		
		if (curr > currLoss) {
			
			network.outputLayer.connectionWeights = w.clone();
			network.outputLayer.biasTerms = b.clone();
			log.info("Reverting to last known good state; converged after global minimum");
			
		}
		
		

		
	}	
	
	
	@Override
	public int getNumParameters() {
		// 		return network.logLayer.W.length + network.logLayer.b.length;

		int connectionSize = network.outputLayer.connectionWeights.numRows() * network.outputLayer.connectionWeights.numCols(); 
		int biasSize = network.outputLayer.biasTerms.numRows() * network.outputLayer.biasTerms.numCols();
		return connectionSize + biasSize;
	}

	@Override
	public double getParameter(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void getParameters(double[] arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParameter(int arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParameters(double[] arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void getValueGradient(double[] arg0) {
		// TODO Auto-generated method stub
		
	}


}
