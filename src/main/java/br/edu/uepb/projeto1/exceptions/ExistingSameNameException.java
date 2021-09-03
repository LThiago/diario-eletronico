package br.edu.uepb.projeto1.exceptions;

public class ExistingSameNameException extends Exception {
    public ExistingSameNameException(String message) {
        super(message);
    }
}