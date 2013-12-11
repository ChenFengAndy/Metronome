package tv.floe.metronome.classification.neuralnetworks.core;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.junit.Test;

import tv.floe.metronome.classification.neuralnetworks.activation.Tanh;
import tv.floe.metronome.classification.neuralnetworks.conf.Config;
import tv.floe.metronome.classification.neuralnetworks.core.neurons.Neuron;
import tv.floe.metronome.classification.neuralnetworks.input.WeightedSum;
import tv.floe.metronome.classification.neuralnetworks.networks.MultiLayerPerceptronNetwork;

public class TestLayerToVectorSerde {

	
	public NeuralNetwork buildXORMLP() throws Exception {
		

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
		//xor_recs.add(v3);

		
		Config c = new Config();
		//c.parse(null); // default layer: 2-3-2
        c.setConfValue("inputFunction", WeightedSum.class);
		c.setConfValue("transferFunction", Tanh.class);
		c.setConfValue("neuronType", Neuron.class);
		c.setConfValue("networkType", NeuralNetwork.NetworkType.MULTI_LAYER_PERCEPTRON);
		c.setConfValue("layerNeuronCounts", "2,3,1" );
		c.parse(null);
		
		MultiLayerPerceptronNetwork mlp_network = new MultiLayerPerceptronNetwork();
		
		
		
//		int[] neurons = { 2, 3, 1 };
//		c.setLayerNeuronCounts( neurons );
		
		mlp_network.buildFromConf(c);		
		
		return mlp_network;
	}		
	
	
	@Test
	public void testLayerExportConnectionsToVector() throws Exception {

		// 1. create network
		
		NeuralNetwork nn0 = buildXORMLP();

		nn0.randomizeWeights();
		
		// 2. export weights to vector array
		ArrayList<Vector> vecs = nn0.getWeightsAsArrayOfVectors();
		
		assertEquals(2, vecs.size() );
		
		assertEquals(6, vecs.get(0).size() );
		
		assertEquals(3, vecs.get(1).size() );
		
		// 3. import weights into new network
		
		NeuralNetwork nn1 = buildXORMLP();
		nn1.clearNetworkConnectionWeights();
		
		ArrayList<Vector> vecs_1 = nn1.getWeightsAsArrayOfVectors();
		
		for ( int x = 0; x < vecs_1.get(0).size(); x++ ) {
			
			assertEquals(0.0, vecs_1.get(0).get(x), 0.0);
			
		}
		
		nn1.setWeightsFromArrayOfVectors(vecs);
		for (int n = 0; n < nn0.getLayerByIndex(1).getNeurons().size(); n++) {

			for ( int c = 0; c < nn0.getLayerByIndex(1).getNeuronAt(n).inConnections.size(); c++ ) {
				
				assertEquals( nn0.getLayerByIndex(1).getNeuronAt(n).inConnections.get(c).getWeight().value, nn1.getLayerByIndex(1).getNeuronAt(n).inConnections.get(c).getWeight().value, 0.0 );
				System.out.println("conn: " + nn0.getLayerByIndex(1).getNeuronAt(n).inConnections.get(c).getWeight().value);
				
			}
			
		}
						
		
		
		
	}

}
