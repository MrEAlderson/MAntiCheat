package de.marcely.manticheat.exception;

public class UnexpectedException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnexpectedException(){
		this("Something unexpected happend.");
	}
	
	public UnexpectedException(String error){
		super(error);
	}
}
