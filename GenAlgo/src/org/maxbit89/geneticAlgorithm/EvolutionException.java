package org.maxbit89.geneticAlgorithm;

public class EvolutionException extends Exception {
	private static final long serialVersionUID = 6268523237061451299L;

	public EvolutionException() {
		super();
	}
	public EvolutionException(String message) {
		super(message);
	}
	public EvolutionException(String message, Throwable cause) {
		super(message, cause);
	}
	public EvolutionException(String message, Throwable cause, boolean enableSupperession, boolean writeableStackTrace) {
		super(message, cause, enableSupperession, writeableStackTrace);
	}
}
