package com.utn.productos.controller;

import com.utn.productos.dto.ActualizarStockDTO;
import com.utn.productos.dto.ProductoDTO;
import com.utn.productos.dto.ProductoResponseDTO;
import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;
import com.utn.productos.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Productos", description = "Operaciones CRUD para la gestión de productos")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    private ProductoResponseDTO mapToDTO(Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria()
        );
    }

    private Producto mapToEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());
        return producto;
    }

    // ================================================
    // GET /api/productos - Listar todos los productos
    // ================================================
    @Operation(
            summary = "Listar todos los productos",
            description = "Obtiene una lista de todos los productos disponibles en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductoResponseDTO.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos() {
        List<ProductoResponseDTO> productos = productoService.obtenerTodos()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }

    // ======================================================
    // GET /api/productos/{id} - Obtener producto por ID
    // ======================================================
    @Operation(
            summary = "Obtener producto por ID",
            description = "Devuelve un producto según su identificador único.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                            content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(producto -> ResponseEntity.ok(mapToDTO(producto)))
                .orElse(ResponseEntity.notFound().build());
    }

    // =================================================================
    // GET /api/productos/categoria/{categoria} - Filtrar por categoría
    // =================================================================
    @Operation(
            summary = "Filtrar productos por categoría",
            description = "Obtiene todos los productos pertenecientes a una categoría específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado filtrado correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductoResponseDTO.class)))
            }
    )
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerPorCategoria(@PathVariable Categoria categoria) {
        List<ProductoResponseDTO> productos = productoService.obtenerPorCategoria(categoria)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }

    // ===================================================
    // POST /api/productos - Crear nuevo producto
    // ===================================================
    @Operation(
            summary = "Crear un nuevo producto",
            description = "Crea un nuevo producto en la base de datos.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Producto creado correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @RequestBody ProductoDTO dto) {
        Producto nuevo = mapToEntity(dto);
        Producto guardado = productoService.crearProducto(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDTO(guardado));
    }

    // ======================================================
    // PUT /api/productos/{id} - Actualizar producto completo
    // ======================================================
    @Operation(
            summary = "Actualizar un producto existente",
            description = "Modifica todos los campos de un producto existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@PathVariable Long id,
                                                                  @Valid @RequestBody ProductoDTO dto) {
        Producto actualizado = mapToEntity(dto);
        Producto guardado = productoService.actualizarProducto(id, actualizado);
        return ResponseEntity.ok(mapToDTO(guardado));
    }

    // ====================================================
    // PATCH /api/productos/{id}/stock - Actualizar stock
    // ====================================================
    @Operation(
            summary = "Actualizar el stock de un producto",
            description = "Modifica únicamente el stock disponible de un producto.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
            }
    )
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoResponseDTO> actualizarStock(@PathVariable Long id,
                                                               @Valid @RequestBody ActualizarStockDTO dto) {
        Producto actualizado = productoService.actualizarStock(id, dto.getStock());
        return ResponseEntity.ok(mapToDTO(actualizado));
    }

    // =====================================================
    // DELETE /api/productos/{id} - Eliminar un producto
    // =====================================================
    @Operation(
            summary = "Eliminar un producto por ID",
            description = "Elimina un producto existente de la base de datos.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
