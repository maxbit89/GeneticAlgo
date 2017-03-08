package org.maxbit89.util.random;

import java.util.Random;

public class RandomGenerator {
	private static Random random = new Random();
	
	public static void setSeed(long seed) {
		random.setSeed(seed);
	}
	
	public static int getRandomIntegerNumberBetween(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
	}
	
	public static char getRandomChar() {
		final int min = 32;
		final int max = 126;
        return(char) getRandomIntegerNumberBetween(min, max);
    }
	
	public static char[] getRandomCharacterArray(int len) {
		char[] buffer = new char[len];
		for(int i=0;i<len;i++) {
			buffer[i] = getRandomChar();
		}
		return buffer;
	}
	
	public static String getRandomString(int len) {
		return String.valueOf(getRandomCharacterArray(len));
	}
}
