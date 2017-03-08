package org.maxbit89.geneticAlgorithm.sample.typeingMonkey;

import org.maxbit89.geneticAlgorithm.Gene;
import org.maxbit89.geneticAlgorithm.GeneticObject;
import org.maxbit89.geneticAlgorithm.GeneticObjectException;
import org.maxbit89.util.random.RandomGenerator;

public class TypeingMonkey implements GeneticObject<String> {
	public static final String POEM = "to be or not to be, that is the question?!";
	
	@Gene(mutator=AsciiMutator.class)
	public String text;
	
	public TypeingMonkey() {
		text = RandomGenerator.getRandomString(POEM.length());
	}

	@Override
	public long getFitness() throws GeneticObjectException {
		char[] should = POEM.toCharArray();
		char[] string = text.toCharArray();
		
		if(should.length < string.length) {
			throw new GeneticObjectException("Error Calculating FitnessValue!");
		}
		
		long fittness = 0;
		
		for(int i=0;i<string.length;i++) {
			if(should[i] == string[i]) {
				fittness++;
			}
		}
		
		fittness = (100*fittness)/should.length;
		
		return fittness;
	}

	@Override
	public String getResult() {
		// TODO Auto-generated method stub
		return text;
	}
}
