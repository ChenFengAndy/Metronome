package tv.floe.metronome.deeplearning.dbn;

import java.util.ArrayList;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.Vector;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

import tv.floe.metronome.deeplearning.neuralnetwork.core.BaseMultiLayerNeuralNetworkVectorized;
import tv.floe.metronome.deeplearning.neuralnetwork.core.NeuralNetworkVectorized;
import tv.floe.metronome.deeplearning.rbm.RestrictedBoltzmannMachine;
import tv.floe.metronome.math.MatrixUtils;
import tv.floe.metronome.types.Pair;

/**
 * Base draft of a Deep Belief Network based on RBMs
 * (based on concepts by Hinton)
 * 
 * Literature Review and Notes
 * 
 * 1. http://www.iro.umontreal.ca/~lisa/twiki/bin/view.cgi/Public/DBNPseudoCode
 * 
 * 
 *  1. Setup as a normal MLPN 
 *  - but it also has a set of RBM layers that matches the number of hidden layers
 *  
 *  2. as each RBM is trained
 *  - its weights and bias are transferred into the weights/bias of the MLPN
 * 
 * @author josh
 * 
 * TODO
 * 
 * -	decide on a strategy for working with input samples
 * 		-	as we going to look at all the samples in memory at once for the MLPN?
 * 		-	how do we balance out the implementation styles?
 *
 *
 * TODO:
 * - thoughts: are we going to train each layer separately as a yarn job?
 * 		- if so, how do we coordinate that?
 * 		- who tracks layer stuff, and how?
 * 
 * - IR: as long as all the workers advance their layer positing in sync, we should be good
 * 		to make this as one continuous job
 * 		-	need a way to save layers in progress to view in viewer
 *
 * TODO:
 * - need to figure out how the greedy-per-layer SGD pass works in this case
 *
 *
 */
public class DeepBeliefNetwork extends BaseMultiLayerNeuralNetworkVectorized {
	
	private RandomGenerator randomGen = new MersenneTwister(1234);
	
	public DeepBeliefNetwork() {
		
		
	}
	

	
	
	/**
	 * This is where we work through each RBM layer, learning an unsupervised 
	 * representation of the data
	 * 
	 * TODO: make sure layers match and the input layer isnt messing up counts
	 * 
	 * TODO: 
	 * 
	 */
	public void preTrain(Matrix trainingRecords) {
		
		Matrix layerTrainingInput = trainingRecords;
		
		
		
	}
	
	/**
	 * TODO: look at this more closely
	 * 
	 * 
	 * @return
	 */
	public Matrix outputMatrix( Matrix input ) {
//		Matrix mult = input.mmul(W);
//		mult = mult.addRowVector(b);
//		return MatrixUtil.sigmoid(mult);
		return null;
	}
	
	
	/**
	 * This function infers state of visible units given hidden units
	 * 
	 *  
	 * @param input
	 * @param mlpnLayer
	 * @return
	 */
	public Matrix sampleHiddenGivenVisible(Matrix visible) {
		
		
/*		
		Matrix visibleProb = this.generateProbabilitiesForVisibleStatesBasedOnHiddenStates(hidden);

		Matrix visibleBinomialSample = MatrixUtils.genBinomialDistribution(visibleProb, 1, this.randNumGenerator);

		return new Pair<Matrix, Matrix>(visibleProb, visibleBinomialSample);
	*/	
		
		//reset the seed to ensure consistent generation of data
		//Matrix ret = MatrixUtils.binomial(outputMatrix(input), 1, this.randomGen);
		Matrix ret = MatrixUtils.genBinomialDistribution( outputMatrix(visible), 1, this.randomGen);
		//return ret;
		return ret;
	}


	@Override
	public void trainNetwork(Matrix input, Matrix labels, Object[] otherParams) {

/*
		int k = (Integer) otherParams[0];
		double lr = (Double) otherParams[1];
		int epochs = (Integer) otherParams[2];
		pretrain(input,k,lr,epochs);
		if(otherParams.length < 3)
			finetune(labels, lr, epochs);
		else {
			double finetuneLr = otherParams.length > 3 ? (double) otherParams[3] : lr;
			int finetuneEpochs = otherParams.length > 4 ? (int) otherParams[4] : epochs;
			finetune(labels,finetuneLr,finetuneEpochs);
		}
*/		
	}




	@Override
	public NeuralNetworkVectorized createPreTrainingLayer(Matrix input,
			int nVisible, int nHidden, Matrix weights, Matrix hbias,
			Matrix vBias, RandomGenerator rng, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
