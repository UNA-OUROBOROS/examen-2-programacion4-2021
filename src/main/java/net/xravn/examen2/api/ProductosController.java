package net.xravn.examen2.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.xravn.examen2.controller.GeneralController;
import net.xravn.examen2.model.Producto;

@RestController
@RequestMapping("api")
public class ProductosController {

    @GetMapping("/productos")
    public List<Producto> getProductos() {
        return GeneralController.getInstance().getProductos();
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable Integer id) {
        Producto producto = GeneralController.getInstance().getProducto(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping("/productos")
    // retorna una tupla con el estado y el mensaje
    public ResponseEntity<Map<String, Object>> postProducto(@RequestBody Producto producto) {
        Map<String, Object> respuesta = new HashMap<>();
        // validamos el producto
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            respuesta.put("exitoso", false);
            respuesta.put("mensaje", "El nombre del producto es obligatorio");
        } else if (producto.getPrecio() == null || producto.getPrecio() < 0) {
            respuesta.put("exitoso", false);
            respuesta.put("mensaje", "El precio del producto no es valido");
        } else if (producto.getStock() == null || producto.getStock() < 0) {
            respuesta.put("exitoso", false);
            respuesta.put("mensaje", "el stock del producto no es valido");
        } else {
            // no se pueden agregar categorias
            producto.getCategorias().clear();
            if (GeneralController.getInstance().agregarProducto(producto)) {
                respuesta.put("exitoso", true);
                respuesta.put("mensaje", "Producto agregado");
            } else {
                respuesta.put("exitoso", false);
                respuesta.put("mensaje", "error desconocido al agregar el producto");
                // retornamos un error 500
                return ResponseEntity.status(500).body(respuesta);
            }
        }

        if (respuesta.get("exitoso").equals(true)) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.unprocessableEntity().body(respuesta);
        }
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Map<String, Object>> actualizarProducto(@PathVariable Integer id,
            @RequestBody Producto producto) {
        Map<String, Object> respuesta = new HashMap<>();
        Producto productoActual = GeneralController.getInstance().getProducto(id);

        if (productoActual == null) {
            respuesta.put("exitoso", false);
            respuesta.put("mensaje", "El producto no existe");
        } else if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            respuesta.put("exitoso", false);
            respuesta.put("mensaje", "El nombre del producto es obligatorio");
        } else if (producto.getPrecio() == null || producto.getPrecio() < 0) {
            respuesta.put("exitoso", false);
            respuesta.put("mensaje", "El precio del producto no es valido");
        } else {
            // ponemos las categorias originales
            producto.getCategorias().addAll(productoActual.getCategorias());
            producto.setId(productoActual.getId());
            producto.setStock(productoActual.getStock());
            if (GeneralController.getInstance().actualizarProducto(producto)) {
                respuesta.put("exitoso", true);
                respuesta.put("mensaje", "Producto actualizado");
            } else {
                respuesta.put("exitoso", false);
                respuesta.put("mensaje", "error desconocido al actualizar el producto");
                // retornamos un error 500
                return ResponseEntity.status(500).body(respuesta);
            }
        }

        if (respuesta.get("exitoso").equals(true)) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.unprocessableEntity().body(respuesta);
        }
    }

    @PatchMapping("/productos/{id}")
    public ResponseEntity<Map<String, Object>> modificarCantidadProducto(@PathVariable Integer id,
            @RequestBody Map<String, Object> datos) {
        Map<String, Object> respuesta = new HashMap<>();
        Producto producto = GeneralController.getInstance().getProducto(id);
        if (producto == null) {
            respuesta.put("exitoso", false);
            respuesta.put("mensaje", "El producto no existe");
            return ResponseEntity.notFound().build();
        } else {
            // recuperamos la operacion
            if (!datos.containsKey("operation")) {
                respuesta.put("exitoso", false);
                respuesta.put("mensaje", "La operacion es obligatoria {operation}");
            } else if (!datos.containsKey("cantidad")) {
                respuesta.put("exitoso", false);
                respuesta.put("mensaje", "La cantidad es obligatoria {cantidad}");
            } else {
                String operation = (String) datos.get("operation");
                Integer cantidad = (Integer) datos.get("cantidad");
                switch (operation) {
                case "agregar":
                    producto.setStock(producto.getStock() + cantidad);
                    break;
                case "modificar":
                    producto.setStock(cantidad);
                    break;
                case "restar":
                    producto.setStock(producto.getStock() - cantidad);
                    break;
                default:
                    respuesta.put("exitoso", false);
                    respuesta.put("mensaje", "La operacion no es valida");
                    return ResponseEntity.unprocessableEntity().body(respuesta);
                }

                if (GeneralController.getInstance().actualizarProducto(producto)) {
                    respuesta.put("exitoso", true);
                    respuesta.put("mensaje", "Producto actualizado");
                } else {
                    respuesta.put("exitoso", false);
                    respuesta.put("mensaje", "error desconocido al actualizar el producto");
                    // retornamos un error 500
                    return ResponseEntity.status(500).body(respuesta);
                }
            }

            if (respuesta.get("exitoso").equals(true)) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.unprocessableEntity().body(respuesta);
            }
        }
    }
}