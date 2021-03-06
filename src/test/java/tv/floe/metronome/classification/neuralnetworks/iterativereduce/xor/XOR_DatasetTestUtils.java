package tv.floe.metronome.classification.neuralnetworks.iterativereduce.xor;

import static org.junit.Assert.assertEquals;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

import tv.floe.metronome.classification.neuralnetworks.core.NeuralNetwork;

public class XOR_DatasetTestUtils {

	public static void PrintNeuralNetwork(NeuralNetwork nn) {
		
		
		for ( int x = 1; x < nn.getLayersCount(); x++ ) {
			
			System.out.println("Layer " + x );
			
			for ( int n = 0; n < nn.getLayerByIndex(x).getNeuronsCount(); n++ ) {
				
				System.out.println("\tNeuron " + n);
				
				for ( int c = 0; c < nn.getLayerByIndex(x).getNeuronAt(n).inConnections.size(); c++) {
					
					System.out.println("\t\tConnection " + c + ", weight: " + nn.getLayerByIndex(x).getNeuronAt(n).inConnections.get(c).getWeight().value );
					
				} // for each incoming connection
				
			} // for each neuron in layer
			
			
		} // for each layer
		
		
		
	}
	
	public static void scoreNeuralNetworkXor(NeuralNetwork mlp_network) throws Exception {
		
		System.out.println("Scoring NN for XOR Function ---------- ");
		
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
        
        
        assertEquals(0.05, networkOutput.get(0), 0.05);
        assertEquals(0.95, networkOutput_1.get(0), 0.05);
        assertEquals(0.95, networkOutput_2.get(0), 0.05);
        assertEquals(0.05, networkOutput_3.get(0), 0.05);
        
		
	}
	
}
