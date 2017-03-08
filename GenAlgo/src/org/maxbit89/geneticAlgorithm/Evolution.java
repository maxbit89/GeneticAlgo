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

public class Evolution {
	private HashMap<Class<?>, Mutator> mutators = new HashMap<Class<?>, Mutator>();
	
	
	public Evolution() {
		mutators.put(AsciiMutator.class, new AsciiMutator());
	}
	
	public List<GeneticObject<?>> createGenePool(List<GeneticObject<?>> population) throws GeneticObjectException {
		List<GeneticObject<?>> genePool = new ArrayList<GeneticObject<?>>();
		
		for(GeneticObject<?> obj : population) {
			for(long n=0;n<obj.getFitness();n++) {
				genePool.add(obj);
			}
		}
		
		return genePool;
	}
	
	public List<GeneticObject<?>> createPopulation(Class<? extends GeneticObject<?>> clazz, long populationSize, List<GeneticObject<?>> genePool, long mutationRatePercent) {
		List<GeneticObject<?>> population = new ArrayList<GeneticObject<?>>();
		long mutationRate = mutationRatePercent * populationSize/100;
		try {
			@SuppressWarnings("unchecked")
			Constructor<GeneticObject<?>> ctor = (Constructor<GeneticObject<?>>) clazz.getConstructor();
			for(long i=0; i<populationSize;i++) {
				GeneticObject<?> genObj = ctor.newInstance();
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
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MutatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return population;
	}
	
	private GeneticObject<?> getParrentFromGenePool(List<GeneticObject<?>> genePool) {
		int i = RandomGenerator.getRandomIntegerNumberBetween(0, genePool.size()-1);
		return genePool.get(i);
	}
}
