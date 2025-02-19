package com.daesoo.dmotools.common.exception;

public class UnauthorizedException extends RuntimeException {
    /**
     * Constructs an {@code IllegalArgumentException} with no
     * detail message.
     */
    public UnauthorizedException() {
        super();
    }

    /**
     * Constructs an {@code IllegalArgumentException} with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public UnauthorizedException(String s) {
        super(s);
    }
}