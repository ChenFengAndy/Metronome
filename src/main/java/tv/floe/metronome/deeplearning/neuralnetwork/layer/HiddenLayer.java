package tv.floe.metronome.deeplearning.neuralnetwork.layer;

import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.mahout.math.DenseMatrix;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.Vector;

import tv.floe.metronome.math.MatrixUtils;

public class HiddenLayer {

	
	private int neuronCountPreviousLayer = 0;
	private int neuronCount = 0;
	
	public Matrix connectionWeights;
	public Matrix biasTerms;
	public RandomGenerator rndNumGenerator;
	public Matrix input;
	//private ActivationFunction sigmoidFunction = new Sigmoid();
	
	/**
	 * LayerVectorized Ctor
	 * 
	 * we use the count of neurons of the prev layer and this layer's neuron count to deduce the weight matrix
	 * 
	 * @param neuronCountPrevLayer
	 * @param neuronCount
	 * @param rndGen
	 */
	public HiddenLayer(int neuronCountPrevLayer, int neuronCount, RandomGenerator rndGen) {
		
		this.neuronCount = neuronCount;
		this.neuronCountPreviousLayer = neuronCountPrevLayer;
		this.rndNumGenerator = rndGen;
		
		double a = 1.0 / (double) neuronCountPrevLayer;

		UniformRealDistribution realDistributionGenerator = new UniformRealDistribution( this.rndNumGenerator, -a, a );

		
		// init the connection weights
		this.connectionWeights = new DenseMatrix( this.neuronCountPreviousLayer, this.neuronCount );
		this.connectionWeights.assign(0.0);

		for (int r = 0; r < this.connectionWeights.numRows(); r++) {
			
			for(int c = 0; c < this.connectionWeights.numCols(); c++) { 
			
				this.connectionWeights.setQuick( r, c, realDistributionGenerator.sample() );
			
			}

		}	
		
		// init the bias terms (column vector)
		// what operations use this Matrix / column vector?
		this.biasTerms = new DenseMatrix( 1, this.neuronCount );
		this.biasTerms.assign(0.0);
		
		
	}
	
	/**
	 * In the vectorized implementation we want to hook the previous layer's
	 * work in progress into this layer
	 * 
	 * In the case of the first layer, its just the matrix of input samples
	 * 
	 * @param layerInput
	 */
	public void setInput(Matrix layerInput) {
		this.input = layerInput;
	}
	
	public void setWeights(Matrix weights) {
		
		this.connectionWeights = weights;
		
	}
	
	
	public Matrix getVectorizedOutputMatrix() {
		
		return null;
	}
	
	public Vector getConnectionsForNeuron( int neuronIndex ) {
		
		Vector connections = new DenseVector();
		
		return null;
		
	}
	
	/**
	 * Trigger an activation with the last specified input
	 * @return the activation of the last specified input
	 */
	public Matrix computeActivationOutput() {
		
		//Matrix mult = this.input.mmul(W); // matrix-matrix multiplication
		Matrix mult = this.input.times(connectionWeights);
		//mult = mult.addRowVector(b);
		
		//MatrixUtils.debug_print_matrix_stats(mult, "mult");
		//MatrixUtils.debug_print_matrix_stats(this.biasTerms, "bias");
		
		
		Matrix multPlusBias = MatrixUtils.addRowVector(mult, this.biasTerms.viewRow(0));
		//return activationFunction.apply(mult);
		
		
		return MatrixUtils.sigmoid(multPlusBias);
	}
	
}
