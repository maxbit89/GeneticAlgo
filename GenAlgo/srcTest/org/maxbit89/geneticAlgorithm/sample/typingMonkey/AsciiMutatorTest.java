package org.maxbit89.geneticAlgorithm.sample.typingMonkey;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;
import org.maxbit89.geneticAlgorithm.GeneticObjectException;
import org.maxbit89.geneticAlgorithm.Mutator;
import org.maxbit89.geneticAlgorithm.MutatorException;
import org.maxbit89.geneticAlgorithm.sample.typeingMonkey.AsciiMutator;
import org.maxbit89.geneticAlgorithm.sample.typeingMonkey.TypeingMonkey;

public class AsciiMutatorTest {	
	@Test
	public void mutation() throws MutatorException {
		TypeingMonkey monkey = new TypeingMonkey();
		
		HashMap<String, Long> map = new HashMap<String, Long>();
		Mutator mutator = new AsciiMutator();
		Field gene = Arrays.asList(monkey.getClass().getFields()).stream()
				.filter(f -> f.getName().equals("text")).findFirst().get();
		
		mutator.doInheritance(gene, monkey);
		for(long i=0;i<20;i++) {
			Long value;
			if((value = map.get(monkey.text)) != null) {
				map.put(monkey.text, value+1);
			} else {
				map.put(monkey.text, (long) 1);
			}
			System.out.println(monkey.text);
			mutator.doMutation(gene, monkey);
		}
		System.out.println(monkey.text);
		
		for(Entry<String, Long> e : map.entrySet()) {
			Assert.assertTrue("randomicer isn't realy random :"+e.getKey()+" = "+e.getValue(), e.getValue() <= 1);
		}
	}
	
	@Test
	public void geneticMutation() throws MutatorException, GeneticObjectException {
		TypeingMonkey monkey = new TypeingMonkey();
		TypeingMonkey parrentA = new TypeingMonkey();
		TypeingMonkey parrentB = new TypeingMonkey();
		parrentA.text = "t_ _e_o_ _o_ _o_b_,_t_a_ _s_t_e_q_e_t_o_?_";
		parrentB.text = "_o_b_ _r_n_t_t_ _e_ _h_t_i_ _h_ _u_s_i_n_!";
		Mutator mutator = new AsciiMutator();
		Field gene = Arrays.asList(monkey.getClass().getFields()).stream()
				.filter(f -> f.getName().equals("text")).findFirst().get();
		
		mutator.doInheritance(gene, monkey, parrentA, parrentB);

		System.out.println(parrentA.text);
		System.out.println(parrentA.getFitness());
		System.out.println(parrentB.text);
		System.out.println(parrentB.getFitness());
		System.out.println(monkey.text);
		System.out.println(monkey.getFitness());
		
		Assert.assertTrue("monkey shuld be 100% fit", monkey.getFitness() == 100);
	}
}
