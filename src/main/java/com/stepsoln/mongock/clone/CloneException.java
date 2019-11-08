package com.stepsoln.mongock.clone;

public class CloneException extends RuntimeException
{
	private static final long serialVersionUID = -9206311580171190598L;

	public CloneException(String message)
	{
		super(message);
	}

	public CloneException(String message, Throwable e)
	{
		super(message, e);
	}
}
