package org.maxbit89.geneticAlgorithm;

import java.lang.reflect.Field;

public interface Mutator {
	public void doMutation(Field gene, Object target) throws MutatorException;
	public void doInheritance(Field gene, Object target, GeneticObject<?> ... parrents) throws MutatorException;
}
