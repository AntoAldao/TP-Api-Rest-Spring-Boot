package com.utn.productos.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private LocalDateTime timestamp;  // Fecha y hora del error
    private int status;                // Código de estado HTTP
    private String error;              // Mensaje de error
    private String path;               // Ruta del endpoint donde ocurrió el error
}
