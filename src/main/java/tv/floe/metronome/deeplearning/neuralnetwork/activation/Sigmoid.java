package tv.floe.metronome.deeplearning.neuralnetwork.activation;

import java.io.Serializable;

import org.apache.mahout.math.Matrix;

import tv.floe.metronome.math.MatrixUtils;


public class Sigmoid implements ActivationFunction,Serializable {

	private static final long serialVersionUID = -6280602270833101092L;

	public Matrix apply(Matrix arg0) {
		return MatrixUtils.sigmoid(arg0);
	}

	@Override
	public Matrix applyDerivative(Matrix input) {
		//return MatrixUtils.sigmoid( input ).times( MatrixUtils.oneMinus( MatrixUtils.sigmoid( input ) ) );
		return MatrixUtils.elementWiseMultiplication( MatrixUtils.sigmoid( input ), MatrixUtils.oneMinus( MatrixUtils.sigmoid( input ) ) );
	}

}
