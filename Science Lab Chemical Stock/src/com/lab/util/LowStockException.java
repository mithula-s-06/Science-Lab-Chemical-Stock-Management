package com.lab.util;

public class LowStockException extends Exception{
	public LowStockException(String message) {
        super(message);
    }
    @Override
    public String toString() {
        return getMessage();
    }
}
