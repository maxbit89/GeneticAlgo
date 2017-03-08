package org.maxbit89.geneticAlgorithm.sample.typingMonkey;

import org.junit.Assert;
import org.junit.Test;
import org.maxbit89.geneticAlgorithm.GeneticObjectException;
import org.maxbit89.geneticAlgorithm.sample.typeingMonkey.TypeingMonkey;

public class TypeingMonkeyTest {
	@Test
	public void tst() throws GeneticObjectException {
		TypeingMonkey monkey = new TypeingMonkey();
		monkey.text = "t2 be_or not to be, tha";
		Assert.assertTrue("calculate fittness failed!", monkey.getFitness() == 50);
	}
}
