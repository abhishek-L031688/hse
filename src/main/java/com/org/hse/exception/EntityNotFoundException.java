package com.org.hse.exception;

/**
 * Defines an exception to be thrown when an entity is not found.
 *
 * @author Abhishek
 */
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String msg) {
        super(msg);
    }

}
