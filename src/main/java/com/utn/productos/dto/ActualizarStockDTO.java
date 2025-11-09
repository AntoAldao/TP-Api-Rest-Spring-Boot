package com.utn.productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Objeto utilizado para actualizar únicamente el stock de un producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarStockDTO {

    @Schema(
            description = "Nuevo valor de stock para el producto. Debe ser un número entero mayor o igual a 0.",
            example = "25"
    )
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
}
