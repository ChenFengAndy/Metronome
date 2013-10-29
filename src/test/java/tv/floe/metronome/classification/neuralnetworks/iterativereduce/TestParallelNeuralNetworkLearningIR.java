package tv.floe.metronome.classification.neuralnetworks.iterativereduce;

import static org.junit.Assert.*;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.junit.Test;

import tv.floe.metronome.classification.neuralnetworks.core.NeuralNetwork;
import tv.floe.metronome.irunit.IRUnitDriver;
//import tv.floe.metronome.linearregression.iterativereduce.MasterNode;
import tv.floe.metronome.utils.Utils;

public class TestParallelNeuralNetworkLearningIR {

	public void scoreNeuralNetworkXor(NeuralNetwork mlp_network) throws Exception {
		
		Vector v0 = new DenseVector(2);
		v0.set(0, 0);
		v0.set(1, 0);
		Vector v0_out = new DenseVector(1);
		v0_out.set(0, 0);
		//xor_recs.add(v0);

		Vector v1 = new DenseVector(2);
		v1.set(0, 0);
		v1.set(1, 1);

		Vector v1_out = new DenseVector(1);
		v1_out.set(0, 1);
		//xor_recs.add(v1);

		
		
		Vector v2 = new DenseVector(2);
		v2.set(0, 1);
		v2.set(1, 0);

		Vector v2_out = new DenseVector(1);
		v2_out.set(0, 1);
		//xor_recs.add(v2);

		
		
		Vector v3 = new DenseVector(2);
		v3.set(0, 1);
		v3.set(1, 1);

		Vector v3_out = new DenseVector(1);
		v3_out.set(0, 0);	
		
		
		mlp_network.setInputVector( v0 );
		mlp_network.calculate();
        Vector networkOutput = mlp_network.getOutputVector();

        System.out.println( "> out: 0 =? " + networkOutput.get(0) );
	            
        
        
		mlp_network.setInputVector( v1 );
		mlp_network.calculate();
        Vector networkOutput_1 = mlp_network.getOutputVector();

        System.out.println( "> out: 1 =? " + networkOutput_1.get(0) );
	            		

		mlp_network.setInputVector( v2 );
		mlp_network.calculate();
        Vector networkOutput_2 = mlp_network.getOutputVector();

        System.out.println( "> out: 1 =? " + networkOutput_2.get(0) );


		mlp_network.setInputVector( v3 );
		mlp_network.calculate();
        Vector networkOutput_3 = mlp_network.getOutputVector();

        System.out.println( "> out: 0 =? " + networkOutput_3.get(0) );
        		
		
	}
	
	@Test
	public void testLearnXORFunctionViaIRNN_MLP() throws Exception {
		
		IRUnitDriver polr_ir = new IRUnitDriver("src/test/resources/run_profiles/unit_tests/app.unit_test.nn.xor.properties");
		polr_ir.Setup();

		polr_ir.SimulateRun();

		
		MasterNode master = (MasterNode) polr_ir.getMaster();
		
		System.out.println("\n\nComplete: ");
		//Utils.PrintVector( master.polr.getBeta().viewRow(0) );

		this.scoreNeuralNetworkXor( master.master_nn );
		
	}
	
}
