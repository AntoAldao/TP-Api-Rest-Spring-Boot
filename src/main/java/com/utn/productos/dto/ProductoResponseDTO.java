package com.utn.productos.dto;

import com.utn.productos.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Objeto de respuesta que representa un producto completo con su ID y detalles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {

    @Schema(
            description = "Identificador único del producto",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nombre del producto",
            example = "Smartphone Samsung Galaxy A15"
    )
    private String nombre;

    @Schema(
            description = "Descripción del producto",
            example = "Teléfono inteligente con pantalla AMOLED de 6.5 pulgadas y cámara de 48MP"
    )
    private String descripcion;

    @Schema(
            description = "Precio del producto en dólares (USD)",
            example = "299.99"
    )
    private Double precio;

    @Schema(
            description = "Cantidad disponible en stock",
            example = "50"
    )
    private Integer stock;

    @Schema(
            description = "Categoría del producto",
            example = "ELECTRONICA"
    )
    private Categoria categoria;
}
