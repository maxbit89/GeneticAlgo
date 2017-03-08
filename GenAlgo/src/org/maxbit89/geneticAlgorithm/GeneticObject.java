package org.maxbit89.geneticAlgorithm;

public interface GeneticObject <T> {
	public long getFitness() throws GeneticObjectException;
	public T getResult();
}
