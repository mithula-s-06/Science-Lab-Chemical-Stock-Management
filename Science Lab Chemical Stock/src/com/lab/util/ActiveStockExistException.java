package com.lab.util;

public class ActiveStockExistException extends Exception {
	public ActiveStockExistException(String message) {
        super(message);
    }
    @Override
    public String toString() {
        return getMessage();
}
}