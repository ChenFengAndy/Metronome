package tv.floe.metronome.deeplearning.rbm;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.mahout.math.DenseMatrix;
import org.apache.mahout.math.Matrix;
import org.junit.Test;

import tv.floe.metronome.deeplearning.datasets.DataSet;
import tv.floe.metronome.deeplearning.datasets.iterator.impl.MnistDataSetIterator;
import tv.floe.metronome.deeplearning.rbm.visualization.RBMRenderer;
import tv.floe.metronome.math.MatrixUtils;

public class TestRBMRenderer {

	
	private static void renderActivationsToDisk( RestrictedBoltzmannMachine rbm, String CE, int scale ) throws InterruptedException {
		
		String strCE = String.valueOf(CE).substring(0, 5);

		// Matrix hbiasMean = network.getInput().mmul(network.getW()).addRowVector(network.gethBias());
		
		//Matrix hbiasMean = MatrixUtils.addRowVector( rbm.getInput().times( rbm.connectionWeights ), rbm.getHiddenBias().viewRow(0) );
		Matrix hbiasMean = MatrixUtils.sigmoid( MatrixUtils.addRowVector( rbm.getInput().times( rbm.connectionWeights ), rbm.getHiddenBias().viewRow(0) ) );


		RBMRenderer renderer = new RBMRenderer();
		//rbm_hbias_test.renderHiddenBiases(100, 100, hbiasMean, "/tmp/Metronome/RBM/" + UUIDForRun + "/activations_" + strCE + "_ce.png");
		
		renderer.renderActivations(100, 100, hbiasMean, "/tmp/Metronome/unit_test/RBMRenderer/activations_" + strCE + "_ce.png", scale );
		
	}
	
	
	@Test
	public void testRBMRenders() throws InterruptedException {

		double ce = 0;
		double learningRate = 0.01;


		double[][] data_simple = new double[][]
				{
					{1,1,1,0,0,0},
					{0,0,0,1,1,1},
					{1,1,1,0,0,0},
					{0,0,1,1,1,0},
					{0,0,1,1,0,0},
					{0,0,1,1,1,0},
					{0,0,1,1,1,0}
					
				};
		
		Matrix input = new DenseMatrix(data_simple);		
		
		int weightScale = 1000;
		
		RestrictedBoltzmannMachine rbm = new RestrictedBoltzmannMachine(6, 4, null);
		rbm.useRegularization = false;
		rbm.connectionWeights = rbm.connectionWeights.times( weightScale );
		
		rbm.setInput(input);
		
		System.out.println( "Initial Weights: " );
		
		MatrixUtils.debug_print( rbm.connectionWeights );
		
		//ce = rbm.getReConstructionCrossEntropy();
		renderActivationsToDisk(rbm, "_init", weightScale );
		
			
		//rbm.trainTillConvergence(0.01, 1, input);
		rbm.trainTillConvergence( input, learningRate, new Object[]{ 1, learningRate, 10 } );

		System.out.println( "Trained Weights: " );
		
		MatrixUtils.debug_print( rbm.connectionWeights );

		ce = rbm.getReConstructionCrossEntropy();
		renderActivationsToDisk(rbm, "" + ce, weightScale );
		
		
		
		
	
	
	}
	
	@Test
	public void testMNISTRenderPath() throws InterruptedException, IOException {
		
		MnistDataSetIterator fetcher = new MnistDataSetIterator(100,200);
		MersenneTwister rand = new MersenneTwister(123);

		double learningRate = 0.001;
		
		int[] batchSteps = { 250, 200, 150, 100, 50, 25, 5 };
		
		DataSet first = fetcher.next();
/*
		RestrictedBoltzmannMachine da = new RBM.Builder().numberOfVisible(784).numHidden(400).withRandom(rand).renderWeights(1000)
				.useRegularization(false)
				.withMomentum(0).build();
*/
		RestrictedBoltzmannMachine rbm = new RestrictedBoltzmannMachine( 784, 400, null );
		rbm.useRegularization = false;
		//rbm.scaleWeights( 1000 );
		rbm.momentum = 0 ;
		rbm.sparsity = 0.01;
		// TODO: investigate "render weights"



		rbm.trainingDataset = first.getFirst();

		//MatrixUtils.debug_print_row( rbm.trainingDataset, 1 );

		// render base activations pre train
		
		renderActivationsToDisk(rbm, "_init", 1);		
		
	}

}
