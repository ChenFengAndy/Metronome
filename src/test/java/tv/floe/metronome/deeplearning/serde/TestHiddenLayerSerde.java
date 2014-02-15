package tv.floe.metronome.deeplearning.serde;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.mahout.math.DenseMatrix;
import org.apache.mahout.math.Matrix;
import org.junit.Test;

import tv.floe.metronome.deeplearning.neuralnetwork.layer.HiddenLayer;
import tv.floe.metronome.math.MatrixUtils;

public class TestHiddenLayerSerde {

	
	
	@Test
	public void test() throws FileNotFoundException {
		
		String tmpFilename = "/tmp/hiddenLayer.model";
		
		Matrix input = new DenseMatrix(new double[][] 
		{
				{1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0}
				,{1,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0}
				,{1,1,0,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0}
				,{1,1,1,1,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0}
				,{0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0}
				,{0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1}
				,{0,0,0,0,0,0,0,0,0,0,1,0,1,1,1,1,1,1,1,1}
				,{0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1,0,1}
				,{0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,1,1,1,1}
				,{0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0}
        }
		);
		
		MatrixUtils.debug_print_matrix_stats( input, "input" );
		
		
		
		
		
		RandomGenerator r = new MersenneTwister( 1234 );
		
		HiddenLayer layer = new HiddenLayer( 20, 2, r );
		layer.setInput( input );
		
		// save / write the model
		
		FileOutputStream oFileOutStream = new FileOutputStream( tmpFilename, false);
		layer.write( oFileOutStream );
		
		
		
		
		// read / load the model
		FileInputStream oFileInputStream = new FileInputStream( tmpFilename );
		
		HiddenLayer layer_deser = new HiddenLayer( 1, 1, null );
		layer_deser.load(oFileInputStream);
		
		assertEquals( 20, layer_deser.neuronCountPreviousLayer );
		assertEquals( 2, layer_deser.neuronCount );
		
	}

}
