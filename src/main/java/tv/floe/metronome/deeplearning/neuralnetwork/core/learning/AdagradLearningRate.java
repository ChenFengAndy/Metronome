package tv.floe.metronome.deeplearning.neuralnetwork.core.learning;

import org.apache.mahout.math.DenseMatrix;
import org.apache.mahout.math.Matrix;
import tv.floe.metronome.math.MatrixUtils;

/**
 * 
 * Vectorized Learning Rate used per Connection Weight
 * 
 * @author josh
 *
 */
public class AdagradLearningRate {

	//	private double gamma = 10; // default for gamma (this is the numerator)
	//private double squaredGradientSum = 0;
	//	public Matrix squaredGradientSums;
	//	public Matrix connectionLearningRates;

	private double masterStepSize = 1e-3; // default for masterStepSize (this is the numerator)
	//private double squaredGradientSum = 0;
	public Matrix historicalGradient;
	public Matrix adjustedGradient;
	public double fudgeFactor = 0.000001;
	public Matrix gradient;
	public int rows;
	public int cols;
	private int numIterations = 0;
	private double lrDecay = 0.95;
	private boolean decayLr;
	private double minLearningRate = 1e-4;

//	public double autoCorrect = 0.95;	

	public AdagradLearningRate( int rows, int cols, double gamma) {

		this.rows = rows;
		this.cols = cols;

		this.adjustedGradient = new DenseMatrix(rows, cols);
		this.adjustedGradient.assign( 0.0 );

		this.historicalGradient = new DenseMatrix(rows, cols);
		this.historicalGradient.assign( 0.0 );

		this.masterStepSize = gamma;
		this.decayLr = false;


	}

	public AdagradLearningRate( int rows, int cols) {

		this( rows, cols, 0.01 );

	}

	/*	public void addLastIterationGradient(Matrix gradient) {

		//this.squaredGradientSum += gradient * gradient;
		Matrix gradientsSquared = MatrixUtils.pow(gradient, 2);

		//this.connectionLearningRates.
		MatrixUtils.addi(this.squaredGradientSums, gradientsSquared);

		MatrixUtils.debug_print(squaredGradientSums);

	}
	 */	
	/*	
	public double getLearningRate(int row, int col) {

		double squaredGradientSum = this.squaredGradientSums.get(row, col);

		if ( squaredGradientSum > 0) {
			return this.gamma / Math.sqrt( squaredGradientSum );
		} else {
			return this.gamma;
		}


	}
	 */
	/*	
	public Matrix getLearningRates() {

		for ( int r = 0; r < this.squaredGradientSums.numRows(); r++ ) {

			for ( int c = 0; c < this.squaredGradientSums.numCols(); c++ ) {

				double squaredGradientSum = this.squaredGradientSums.get(r, c);

				if ( squaredGradientSum > 0) {
					this.connectionLearningRates.set(r, c, this.gamma / Math.sqrt( squaredGradientSum ) );
				} else {
					//this.gamma;
					this.connectionLearningRates.set(r, c, this.gamma);
				}

			}

		}

		return this.connectionLearningRates;


	}
	 */



	public Matrix getLearningRates(Matrix gradient) {

		this.gradient = gradient.clone();
		
		double currentLearningRate = this.masterStepSize;
		if(decayLr && numIterations > 0) {
			this.masterStepSize *= lrDecay;
			if(masterStepSize < minLearningRate)
				masterStepSize = minLearningRate;
		}

		numIterations++;
		
		
		this.adjustedGradient = MatrixUtils.sqrt(MatrixUtils.pow( gradient, 2 )).times( currentLearningRate );
		
		//ensure no zeros
//		this.adjustedGradient.addi(1e-6);
		this.adjustedGradient = this.adjustedGradient.plus( 1e-6 );
		
		
		return adjustedGradient;
	}
	
	public AdagradLearningRate clone() {
		
		AdagradLearningRate ret = new AdagradLearningRate( this.rows, this.cols );
		ret.adjustedGradient.assign( this.adjustedGradient );
		//ret.autoCorrect = this.autoCorrect;
		ret.decayLr = this.decayLr;
		ret.fudgeFactor = this.fudgeFactor;
		if ( null == this.gradient ) {
			ret.gradient = null;
		} else {
			ret.gradient.assign( this.gradient );
		}
		ret.historicalGradient.assign( this.historicalGradient );
		ret.masterStepSize = this.masterStepSize;
		
		return ret;
		
	}
	
	public  double getMasterStepSize() {
		return masterStepSize;
	}

	public  void setMasterStepSize(double masterStepSize) {
		this.masterStepSize = masterStepSize;
	}

	public synchronized boolean isDecayLr() {
		return decayLr;
	}

	public synchronized void setDecayLr(boolean decayLr) {
		this.decayLr = decayLr;
	}
	

}
