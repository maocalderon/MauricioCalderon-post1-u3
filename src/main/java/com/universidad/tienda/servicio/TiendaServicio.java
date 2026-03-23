package com.universidad.tienda.servicio;

import com.universidad.tienda.adapter.PasarelaPago;
import com.universidad.tienda.composite.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TiendaServicio {
    private final PasarelaPago pasarela;

    // Se usa @Qualifier para cumplir con la inyeccion especifica de la rubrica
    public TiendaServicio(@Qualifier("stripe") PasarelaPago pasarela) {
        this.pasarela = pasarela;
    }

    public Categoria construirCatalogo() {
        Categoria raiz = new Categoria("Catalogo General");
        
        // Estructura recursiva: Electronica -> Computadores -> Laptops
        Categoria electronica = new Categoria("Electronica");
        Categoria computadores = new Categoria("Computadores");
        
        computadores.agregar(new Producto("Laptop Dell XPS 15", 4500000));
        computadores.agregar(new Producto("MacBook Air M3", 6200000));
        
        electronica.agregar(computadores);
        electronica.agregar(new Producto("Audifonos Sony WH-1000XM5", 1200000));
        
        Categoria libros = new Categoria("Libros Tecnicos");
        libros.agregar(new Producto("Clean Code - Robert Martin", 95000));
        
        raiz.agregar(electronica);
        raiz.agregar(libros);
        
        return raiz;
    }

    public boolean realizarCompra(double monto, String token) {
        System.out.println("Procesando con: " + pasarela.obtenerNombre());
        return pasarela.procesarPago("COP", monto, token);
    }
}
