package tv.floe.metronome.deeplearning.rbm.visualization;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import tv.floe.metronome.deeplearning.neuralnetwork.core.NeuralNetworkGradient;
import tv.floe.metronome.deeplearning.neuralnetwork.core.NeuralNetworkVectorized;



/**
 * Based on the designs in the paper:
 * 
 * http://yosinski.com/media/papers/Yosinski2012VisuallyDebuggingRestrictedBoltzmannMachine.pdf
 * 
 * 
 * for visualizations and concepts 
 * 
 * @author Adam Gibson
 *
 */
public class NeuralNetworkVisualizer {


	private static 	ClassPathResource r = new ClassPathResource("/scripts/plot.py");
	private static Logger log = LoggerFactory.getLogger(NeuralNetworkVisualizer.class);


	static {
		loadIntoTmp();
	}


	public void renderFilter(DoubleMatrix w,int r,int c,long length) {
		try {
			String filePath = writeMatrix(w);
			Process is = Runtime.getRuntime().exec("python /tmp/plot.py filter " + filePath + " " + r + " " + c + " " + length);
			log.info("Std out " + IOUtils.readLines(is.getInputStream()).toString());
			log.info("Rendering weights " + filePath);
			log.error(IOUtils.readLines(is.getErrorStream()).toString());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}




	public void plotNetworkGradient(NeuralNetworkVectorized network, NeuralNetworkGradient gradient) {
		plotMatrices(
				new String[]{"W","hbias","vbias","w-gradient","hbias-gradient","vbias-gradient"},

				new DoubleMatrix[]{
						network.getW(),
						network.gethBias(),
						network.getvBias(),
						gradient.getwGradient(),
						gradient.gethBiasGradient(),
						gradient.getvBiasGradient()

				});
		plotActivations(network);
		/*DoubleMatrix w = network.getW().transpose();
		boolean isPerfectSquare = Math.sqrt(w.columns) % 1 == 0;
		if(isPerfectSquare)
			renderFilter(w,w.rows,w.columns,network.getInput().rows);
		else
			log.warn("Cant render good representation of filter witout perfect square, please choose different hidden layer size");*/
	}

	public void plotMatrices(String[] titles,DoubleMatrix[] matrices) {
		String[] path = new String[matrices.length * 2];
		try {
			if(titles.length != matrices.length)
				throw new IllegalArgumentException("Titles and matrix lengths must be equal");


			for(int i = 0; i < path.length - 1; i+=2) {
				path[i] = writeMatrix(MatrixUtil.unroll(matrices[i / 2]));
				path[i + 1] = titles[i / 2];
			}
			String paths = StringUtils.join(path,",");

			Process is = Runtime.getRuntime().exec("python /tmp/plot.py multi " + paths);

			log.info("Rendering multiple matrices... ");
			log.info("Std out " + IOUtils.readLines(is.getInputStream()).toString());
			log.error(IOUtils.readLines(is.getErrorStream()).toString());


		}catch(IOException e) {
			throw new RuntimeException(e);
		}

	}

	

	protected String writeMatrix(DoubleMatrix matrix) throws IOException {
		String filePath = System.getProperty("java.io.tmpdir") + File.separator +  UUID.randomUUID().toString();
		File write = new File(filePath);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(write,true));
		write.deleteOnExit();
		for(int i = 0; i < matrix.rows; i++) {
			DoubleMatrix row = matrix.getRow(i);
			StringBuffer sb = new StringBuffer();
			for(int j = 0; j < row.length; j++) {
				sb.append(String.format("%.10f", row.get(j)));
				if(j < row.length - 1)
					sb.append(",");
			}
			sb.append("\n");
			String line = sb.toString();
			bos.write(line.getBytes());
			bos.flush();
		}

		bos.close();
		return filePath;
	}

	public void plotWeights(NeuralNetworkVectorized network) {
		try {

			String filePath = writeMatrix(network.getW()); 
			Process is = Runtime.getRuntime().exec("python /tmp/plot.py weights " + filePath);

			log.info("Rendering weights " + filePath);
			log.error(IOUtils.readLines(is.getErrorStream()).toString());

		}catch(Exception e) {

		}
	}

	public void plotActivations(NeuralNetworkVectorized network) {
		try {
			if(network.getInput() == null)
				throw new IllegalStateException("Unable to plot; missing input");;

				DoubleMatrix hbiasMean = network.getInput().mmul(network.getW()).addRowVector(network.gethBias());


				String filePath = writeMatrix(hbiasMean);

				Process is = Runtime.getRuntime().exec("python /tmp/plot.py hbias " + filePath);

				Thread.sleep(10000);
				is.destroy();


				log.info("Rendering hbias " + filePath);
				log.error(IOUtils.readLines(is.getErrorStream()).toString());

		}catch(Exception e) {
			log.warn("Image closed");

		}
	}


	private static void loadIntoTmp() {

		File script = new File("/tmp/plot.py");


		try {
			List<String> lines = IOUtils.readLines(r.getInputStream());
			FileUtils.writeLines(script, lines);

		} catch (IOException e) {
			throw new IllegalStateException("Unable to load python file");

		}

	}
}
