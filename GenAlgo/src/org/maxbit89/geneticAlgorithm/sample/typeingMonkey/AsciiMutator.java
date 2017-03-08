package org.maxbit89.geneticAlgorithm.sample.typeingMonkey;

import java.lang.reflect.Field;

import org.maxbit89.geneticAlgorithm.GeneticObject;
import org.maxbit89.geneticAlgorithm.Mutator;
import org.maxbit89.geneticAlgorithm.MutatorException;
import org.maxbit89.util.random.RandomGenerator;

public class AsciiMutator implements Mutator {
	
	private static final long MUTATIONRATE_PERCENT = 010;
	
	private static final int POEM_LEN = TypeingMonkey.POEM.length();

	@Override
	public void doMutation(Field gene, Object target) throws MutatorException {
		if(String.class.isAssignableFrom(gene.getType())) {
			try {
				char[] string = ((String)gene.get(target)).toCharArray();
				
				long nbOfMutatoions = string.length * MUTATIONRATE_PERCENT / 100;
				if(nbOfMutatoions <= 0) { //There should be always a Mutation
					nbOfMutatoions = 1;
				}
				
				for(long m = 0; m<nbOfMutatoions;m++) {
					int offset = RandomGenerator.getRandomIntegerNumberBetween(0, string.length-1);
					string[offset] = RandomGenerator.getRandomChar();
				}
				
				gene.set(target, String.valueOf(string));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new MutatorException("Field["+gene.getName()+"] is not Accisable on"+target.getClass().getName(), e);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new MutatorException("AsciiMutator can't handle Field from type:"+gene.getType().getName());
		}
	}

	@Override
	public void doInheritance(Field gene, Object target, GeneticObject<?>... parrents) throws MutatorException {
		char[] string = new char[POEM_LEN];
		try {
			if(parrents.length <= 0) {
				string = RandomGenerator.getRandomCharacterArray(POEM_LEN);
			} else {
				for(int i = 0; i<POEM_LEN;i++) {
					int p = i % parrents.length;
					Field parrentGene;
						parrentGene = parrents[p].getClass().getField(gene.getName());
					if(parrentGene.getType() != gene.getType()) {
						throw new MutatorException("incompatible GeneticObjects:"+target.getClass()+" "+parrents[p].getClass());
					}
					String parrentValue = (String) parrentGene.get(parrents[p]);
					string[i] = parrentValue.toCharArray()[i];
				}
			}
			gene.set(target, String.valueOf(string));
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
