package com.utn.productos.exception;

public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }

    public StockInsuficienteException() {
        super("El stock no puede ser negativo o insuficiente.");
    }
}
