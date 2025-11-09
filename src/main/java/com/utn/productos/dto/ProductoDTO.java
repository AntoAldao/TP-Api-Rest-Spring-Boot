package com.utn.productos.dto;

import com.utn.productos.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Objeto utilizado para crear o actualizar un producto")
@Getter
@Setter
public class ProductoDTO {

    @Schema(
            description = "Nombre del producto",
            example = "Smartphone Samsung Galaxy A15"
    )
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Schema(
            description = "Descripción del producto (opcional)",
            example = "Teléfono inteligente con pantalla AMOLED de 6.5 pulgadas y cámara de 48MP"
    )
    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    private String descripcion;

    @Schema(
            description = "Precio del producto en dólares (USD)",
            example = "299.99"
    )
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor o igual a 0.01")
    private Double precio;

    @Schema(
            description = "Cantidad disponible en stock",
            example = "50"
    )
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Schema(
            description = "Categoría del producto",
            example = "ELECTRONICA"
    )
    @NotNull(message = "La categoría es obligatoria")
    private Categoria categoria;
}
