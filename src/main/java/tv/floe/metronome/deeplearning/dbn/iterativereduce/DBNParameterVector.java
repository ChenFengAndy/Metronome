package tv.floe.metronome.deeplearning.dbn.iterativereduce;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import tv.floe.metronome.classification.neuralnetworks.core.NeuralNetwork;

public class DBNParameterVector {

	
	  public byte[] Serialize() throws IOException {
		    
		    // DataOutput d
		    
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    DataOutput d = new DataOutputStream(out);
/*		    
		    // d.writeUTF(src_host);
		    //d.writeInt(this.SrcWorkerPassCount);
		    d.writeInt(this.GlobalPassCount);
		    
		    d.writeInt(this.IterationComplete);
		    d.writeInt(this.CurrentIteration);
		    
		    d.writeInt(this.TrainedRecords);
		    //d.writeFloat(this.AvgLogLikelihood);
		    d.writeFloat(this.PercentCorrect);
		    d.writeDouble(this.RMSE);
		    
		    //d.write
		    
		    // buf.write
		    // MatrixWritable.writeMatrix(d, this.worker_gradient.getMatrix());
		    //MatrixWritable.writeMatrix(d, this.parameter_vector);
		    // MatrixWritable.
		    ObjectOutputStream oos = new ObjectOutputStream(out);
		    
		    //System.out.println("Worker:Serialize() > " + this.network.getClass());
		    
		    oos.writeObject( this.network );
		    
		    oos.flush();
		    oos.close();
*/
		    
		    return out.toByteArray();
		  }
		  
		  public void Deserialize(byte[] bytes) throws IOException {
		    // DataInput in) throws IOException {
		    
		    ByteArrayInputStream b = new ByteArrayInputStream(bytes);
		    DataInput in = new DataInputStream(b);
/*		    // this.src_host = in.readUTF();
		    //this.SrcWorkerPassCount = in.readInt();
		    this.GlobalPassCount = in.readInt();
		    
		    this.IterationComplete = in.readInt();
		    this.CurrentIteration = in.readInt();
		    
		    this.TrainedRecords = in.readInt(); // d.writeInt(this.TrainedRecords);
		    //this.AvgLogLikelihood = in.readFloat(); // d.writeFloat(this.AvgLogLikelihood);
		    this.PercentCorrect = in.readFloat(); // d.writeFloat(this.PercentCorrect);
		    this.RMSE = in.readDouble();

		     ObjectInputStream oistream = null;

		      try {

		          oistream = new ObjectInputStream(b);
		          this.network = (NeuralNetwork) oistream.readObject();

		      } catch (IOException ioe) {
		          ioe.printStackTrace();
		      } catch (ClassNotFoundException cnfe) {
		          cnfe.printStackTrace();
		      } finally {
		          if (oistream != null) {
		              try {
		                  oistream.close();
		              } catch (IOException ioe) {
		              }
		          }
		      }		    
		    */
		    
		  }	  	
	
}
