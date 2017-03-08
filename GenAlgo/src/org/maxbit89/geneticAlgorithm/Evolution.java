package org.maxbit89.geneticAlgorithm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.maxbit89.geneticAlgorithm.sample.typeingMonkey.AsciiMutator;
import org.maxbit89.util.random.RandomGenerator;

/* TODO: make abstract class */
public class Evolution {
	private HashMap<Class<?>, Mutator> mutators = new HashMap<Class<?>, Mutator>();
	
	public Evolution() {
		mutators.put(AsciiMutator.class, new AsciiMutator());
	}
	
	/* generate List/Pool where new generation inherits its genes from: */
	public List<GeneticObject<?>> createGenePool(List<GeneticObject<?>> population) throws GeneticObjectException {
		List<GeneticObject<?>> genePool = new ArrayList<GeneticObject<?>>();
		
		for(GeneticObject<?> obj : population) {
			for(long n=0;n<obj.getFitness();n++) {
				genePool.add(obj);
			}
		}
		
		return genePool;
	}
	
	@SuppressWarnings("unchecked") //suppressed for ctor: ctor is checked by clazz argument.
	public List<GeneticObject<?>> createPopulation(Class<? extends GeneticObject<?>> clazz, long populationSize, List<GeneticObject<?>> genePool, long mutationRatePercent) throws EvolutionException, MutatorException {
		List<GeneticObject<?>> population = new ArrayList<GeneticObject<?>>();
		long mutationRate = mutationRatePercent * populationSize/100;
		
			Constructor<GeneticObject<?>> ctor;
			try {
				ctor = (Constructor<GeneticObject<?>>) clazz.getConstructor();
			} catch (NoSuchMethodException | SecurityException e) {
				throw new EvolutionException("Couldn't find POJO constructor for:"+clazz.getName()+" Make shure Constructor is Public.", e);
			}
			for(long i=0; i<populationSize;i++) {
				GeneticObject<?> genObj = null;
				try {
					genObj = ctor.newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					throw new EvolutionException("Couldn't create instance of:"+clazz.getName()+" maybe class inherits from inaccissable Class", e);
				} catch (IllegalArgumentException e) {
					throw new EvolutionException("Constructor isn't a POJO constructor (POJO constructors have no Arguments): "+clazz.getName(), e);
				} catch (InvocationTargetException e) {
					throw new EvolutionException("Couldn't create instance of:"+clazz.getName()+" class must not be abstract or interface", e);
				}
				List<Field> mutatableFields = Arrays.asList(genObj.getClass().getFields()).stream()
				.filter(f -> f.isAnnotationPresent(Gene.class))
				.collect(Collectors.toList());
				
				for(Field f : mutatableFields) {
					Mutator m = mutators.get(f.getAnnotation(Gene.class).mutator());
					if(genePool != null) {
						GeneticObject<?> parrentA = getParrentFromGenePool(genePool);
						GeneticObject<?> parrentB = getParrentFromGenePool(genePool);
						m.doInheritance(f, genObj, parrentA, parrentB);
					}
					if(i % mutationRate == 0) {
						m.doMutation(f, genObj);
					}
				}
				
				population.add(genObj);
			}
		
		return population;
	}
	
	private GeneticObject<?> getParrentFromGenePool(List<GeneticObject<?>> genePool) {
		int i = RandomGenerator.getRandomIntegerNumberBetween(0, genePool.size()-1);
		return genePool.get(i);
	}
}
