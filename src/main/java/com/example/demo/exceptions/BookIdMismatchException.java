package com.example.demo.exceptions;

/**
 * ! Handling a custom exception message-cause class for BooksIdMismatch
 */
public class BookIdMismatchException extends RuntimeException {

    public BookIdMismatchException() {
        super();
    }

    public BookIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);

    }
    
    public BookIdMismatchException(final String message) {  
        super(message);
    }
    public BookIdMismatchException(final Throwable cause) {  
        super(cause);
    }

}
