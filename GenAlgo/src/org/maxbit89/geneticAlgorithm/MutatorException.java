package org.maxbit89.geneticAlgorithm;

public class MutatorException extends Exception {
	private static final long serialVersionUID = 6268523237061451299L;

	public MutatorException() {
		super();
	}
	public MutatorException(String message) {
		super(message);
	}
	public MutatorException(String message, Throwable cause) {
		super(message, cause);
	}
	public MutatorException(String message, Throwable cause, boolean enableSupperession, boolean writeableStackTrace) {
		super(message, cause, enableSupperession, writeableStackTrace);
	}
}
