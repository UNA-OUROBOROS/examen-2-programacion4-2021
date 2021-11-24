package net.xravn.examen2.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.xravn.examen2.controller.GeneralController;
import net.xravn.examen2.model.CategoriaProducto;

@RestController
@RequestMapping("api/categorias")
public class CategoriasProductosController {

    @GetMapping()
    public List<CategoriaProducto> getCategorias() {
        return GeneralController.getInstance().getCategorias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProducto> getCategoria(@PathVariable Integer id) {
        CategoriaProducto categoria = GeneralController.getInstance().getCategoria(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoria);
    }

}
