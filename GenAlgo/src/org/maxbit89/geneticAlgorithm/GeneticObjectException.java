package org.maxbit89.geneticAlgorithm;

public class GeneticObjectException extends RuntimeException {
	private static final long serialVersionUID = 6268523237061451299L;

	public GeneticObjectException() {
		super();
	}
	public GeneticObjectException(String message) {
		super(message);
	}
	public GeneticObjectException(String message, Throwable cause) {
		super(message, cause);
	}
	public GeneticObjectException(String message, Throwable cause, boolean enableSupperession, boolean writeableStackTrace) {
		super(message, cause, enableSupperession, writeableStackTrace);
	}
}
