package tv.floe.metronome.deeplearning.datasets.iterator.impl;

import java.io.IOException;

import tv.floe.metronome.deeplearning.datasets.iterator.BaseDatasetIterator;


public class MnistDataSetIterator extends BaseDatasetIterator {

	public MnistDataSetIterator(int batch,int numExamples) throws IOException {
		super(batch, numExamples,new MnistDataFetcher());
	}


}
