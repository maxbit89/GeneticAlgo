package org.maxbit89.geneticAlgorithm.sample.typingMonkey;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.maxbit89.geneticAlgorithm.Evolution;
import org.maxbit89.geneticAlgorithm.EvolutionException;
import org.maxbit89.geneticAlgorithm.GeneticObject;
import org.maxbit89.geneticAlgorithm.GeneticObjectException;
import org.maxbit89.geneticAlgorithm.MutatorException;
import org.maxbit89.geneticAlgorithm.sample.typeingMonkey.TypeingMonkey;
import org.maxbit89.util.random.RandomGenerator;

public class EvolutionTest {
	@Test
	public void tst() throws NoSuchMethodException, SecurityException, GeneticObjectException, IOException, EvolutionException, MutatorException {
		RandomGenerator.setSeed(10);
		Evolution evo = new Evolution();
		List<GeneticObject<?>> genePool = null;
		List<GeneticObject<?>> monkeys = null;
		
		Long fitest = (long) 0;
		long generation  = 0;
		while(fitest < 100) {
			if(monkeys != null) {
				genePool = evo.createGenePool(monkeys);
			}
			monkeys = evo.createPopulation(TypeingMonkey.class, 2000, genePool, 10);
			
			TypeingMonkey best = getBestMonkey(monkeys);
			System.out.println("Generation:"+generation);
			System.out.println(best.getFitness());
			System.out.println(best.getResult());
			fitest = best.getFitness();
//			dumpGenertion(monkeys, generation);
			generation++;
		}
	}
	
	//Used for debug only
	@SuppressWarnings("unused")
	private void dumpGenertion(List<GeneticObject<?>> population, long generation) throws IOException {
		File dump = new File(String.format("./Generation%06d.txt", generation));
		
		FileWriter writer = new FileWriter(dump);
		for(GeneticObject<?> obj : population) {
			writer.write(String.format("%3d %-20s\n", obj.getFitness() , obj.getResult()));
		}
		writer.close();
	}
	
	private TypeingMonkey getBestMonkey(List<GeneticObject<?>> monkeys) {
		List<GeneticObject<?>> rankedMonkeys = new ArrayList<GeneticObject<?>>();
		rankedMonkeys.addAll(monkeys);
		Collections.sort(rankedMonkeys, new Comparator<GeneticObject<?>>() {
			@Override
			public int compare(GeneticObject<?> m2, GeneticObject<?> m1) {
				return Long.compare(m1.getFitness(), m2.getFitness());
			}
		});
		
		GeneticObject<?> best = rankedMonkeys.get(0);
		if(best instanceof TypeingMonkey) {
			return (TypeingMonkey) best;
		} else {
			return null;
		}
	}
	
	@Test
	public void fdjlksa() {
		String target = "1234567890";
		String[] f = {
				"1__4__7__0",
				"_2__5__8__",
				"__3__6__9_"
		};
		
		char[] t = target.toCharArray();
		for(int i=0;i<t.length;i++) {
			int x = i % f.length;
			char[] n = f[x].toCharArray();
			t[i] = n[i];
		}
		
		System.out.println(String.valueOf(t));
		
	}
}
