package net.xravn.examen2.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import net.xravn.examen2.controller.GeneralController;
import net.xravn.examen2.model.Producto;

@Controller
public class ProductosController {
    
    @GetMapping("api/productos")
    @ResponseBody
    public List<Producto> getProductos() {
        return GeneralController.getInstance().getProductos();
    }


    @GetMapping("api/productos/{id}")
    @ResponseBody
    public ResponseEntity<Producto> getProducto(@PathVariable Integer id) {
        Producto producto = GeneralController.getInstance().getProducto(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping("api/productos")
    @ResponseBody
    // retorna una tupla con el estado y el mensaje
    public ResponseEntity<Map<String, Object>> postProducto(@RequestBody Producto producto) {
        Map<String, Object> respuesta = new HashMap<>();
        // validamos el producto
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            respuesta.put("exitoso", false);
            respuesta.put("mensaje", "El nombre del producto es obligatorio");
        }
        else if (producto.getPrecio() == null || producto.getPrecio() < 0) {
            respuesta.put("exitoso", false);
            respuesta.put("mensaje", "El precio del producto no es valido");
        }
        else if (producto.getStock() == null|| producto.getStock() < 0) {
            respuesta.put("exitoso", false);
            respuesta.put("mensaje", "el stock del producto no es valido");
        }
        else if(GeneralController.getInstance().agregarProducto(producto)) {
            respuesta.put("exitoso", true);
            respuesta.put("mensaje", "Producto agregado");
        }
        else {
            respuesta.put("exitoso", false);
            respuesta.put("mensaje", "error desconocido al agregar el producto");
        }
        if (respuesta.get("exitoso").equals(true)) {
            return ResponseEntity.ok(respuesta);
        }
        else {
            return ResponseEntity.unprocessableEntity().body(respuesta);
        }
    }
}