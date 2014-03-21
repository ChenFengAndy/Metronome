package tv.floe.metronome.deeplearning.dbn.iterativereduce;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.apache.hadoop.conf.Configuration;

import tv.floe.metronome.classification.neuralnetworks.iterativereduce.NetworkAccumulator;
import tv.floe.metronome.classification.neuralnetworks.iterativereduce.NetworkWeightsUpdateable;
import tv.floe.metronome.classification.neuralnetworks.iterativereduce.NeuralNetworkWeightsDelta;
import tv.floe.metronome.classification.neuralnetworks.learning.BackPropogationLearningAlgorithm;
import tv.floe.metronome.classification.neuralnetworks.networks.MultiLayerPerceptronNetwork;
import tv.floe.metronome.deeplearning.dbn.DeepBeliefNetwork;

import com.cloudera.iterativereduce.ComputableMaster;


public class MasterNode implements ComputableMaster<DBNParameterVectorUpdateable> {

	DeepBeliefNetwork dbn_averaged_master = null;
	
	@Override
	public void complete(DataOutputStream arg0) throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println( "IR DBN Master Node: Complete!" );
		
	}

	/**
	 * Master::Compute
	 * 
	 * This is where the worker parameter averaged updates come in and are processed
	 * 
	 */
	@Override
	public DBNParameterVectorUpdateable compute(
			Collection<DBNParameterVectorUpdateable> workerUpdates,
			Collection<DBNParameterVectorUpdateable> masterUpdates) {

		DBNParameterVectorUpdateable masterReturnMsg = new DBNParameterVectorUpdateable();
		
		DBNParameterVectorUpdateable firstWorkerMsg = workerUpdates.iterator().next();

		if (null == firstWorkerMsg) {
			
			System.out.println("Can't seem to get the first network weights updateable");
			
		} else {
			
			if (null == this.dbn_averaged_master) {
				
				System.out.println("Building base master MLP network");
				//this.master_nn = new MultiLayerPerceptronNetwork();
				
				// TODO: init dbn_averaged_master
				
		        try {
					//this.master_nn.buildFromConf(first.networkUpdate.network.getConfig());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
			}
			
		}	
		
		
		
		try {
			//accumNet = NetworkAccumulator.buildAveragingNetworkFromConf(first.networkUpdate.network.getConfig());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*		
		if (null == accumNet) {
			System.out.println("Master: Network Accumulator is null! [Error]");
			return null;
		}
*/
		
	    for (DBNParameterVectorUpdateable nn_worker : workerUpdates) {

//	    	accumNet.AccumulateWorkerNetwork(nn_worker.networkUpdate.network);
//	    	avg_rmse += nn_worker.networkUpdate.RMSE;
	    	
	    }
	    
//	    avg_rmse = avg_rmse / workerUpdates.size();
//	    BackPropogationLearningAlgorithm bp = ((BackPropogationLearningAlgorithm)this.master_nn.getLearningRule());
//	    bp.getMetrics().setLastRMSE(avg_rmse);
	 
	    // TODO: examine termination conditions
	    
/*	    
	    if (avg_rmse <= this.trainingErrorThreshold && !hasHitThreshold && first.networkUpdate.CurrentIteration > 10) {
	    	
	    	System.out.println("\nMaster hit avg rmse threshold at epoch: " + first.networkUpdate.CurrentIteration + "\n");
	    	
		    for (NetworkWeightsUpdateable nn_worker : workerUpdates) {

		    	System.out.println("worker.rmse: " + nn_worker.networkUpdate.RMSE );
		    }
	    	
	    	
	    	this.hasHitThreshold = true;
	    } else {
	    	
	    	//System.out.println("rmse debug > " + avg_rmse + " <= " + this.trainingErrorThreshold + ", iterations: " + first.networkUpdate.CurrentIteration);
	    	
	    }
*/	    	
	    
	    // TODO: compute average of weights
/*	    try {
			accumNet.AverageNetworkWeights();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/	    
	    
//	    this.master_nn.copyWeightsAndConf(accumNet);
		
//	    NeuralNetworkWeightsDelta nnwd = new NeuralNetworkWeightsDelta();
//	    nnwd.network = this.master_nn;
	    
//	    return_msg.set(nnwd);
	
		DBNParameterVector dbn_update = new DBNParameterVector();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		this.dbn_averaged_master.write( out );
		dbn_update.dbn_payload = out.toByteArray();
		
		//DBNParameterVectorUpdateable updateable = new DBNParameterVectorUpdateable();
		masterReturnMsg.param_msg = dbn_update;
	    
	    
		
	    // THIS NEEDS TO BE DONE, probably automated!
	    workerUpdates.clear();
	    masterUpdates.clear();		
		
		
		
		return masterReturnMsg;
	}

	@Override
	public DBNParameterVectorUpdateable getResults() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setup(Configuration arg0) {
		// TODO Auto-generated method stub
		
	}

}
