package com.salesianos.dam;

import com.salesianostriana.dam.ejerciciotesting.model.Cliente;
import com.salesianostriana.dam.ejerciciotesting.model.LineaDeVenta;
import com.salesianostriana.dam.ejerciciotesting.model.Producto;
import com.salesianostriana.dam.ejerciciotesting.model.Venta;
import com.salesianostriana.dam.ejerciciotesting.repos.ProductoRepositorio;
import com.salesianostriana.dam.ejerciciotesting.repos.VentaRepositorio;
import com.salesianostriana.dam.ejerciciotesting.services.VentaServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
public class TestMockito {
    
    @Mock
    VentaRepositorio ventaRepositorio;

    @Mock
    ProductoRepositorio productoRepositorio;

    @InjectMocks
    VentaServicio ventaServicio;

// Caja negra
    @Test
    public void whenNuevaVenta_thenReturnVenta(){
        Cliente c = Cliente.builder()
                .dni("12312312A")
                .email("Ale@mgmail.com")
                .nombre("Alejandro")
                .build();

        Producto p = Producto.builder()
                .codigoProducto("1")
                .nombre("Producto")
                .precio(12.36)
                .build();

        lenient().when(productoRepositorio.findOne("1")).thenReturn(p);
        Map<String,Integer> carrito = Map.of("1",2);
        Venta v = new Venta();
        v.setId(3l);
        v.setCliente(c);
        v.setLineasDeVenta(List.of(new LineaDeVenta(p,2,12.36)));
        lenient().when(ventaRepositorio.save(v)).thenReturn(v);
        System.out.println(v.equals(ventaServicio.nuevaVenta(carrito,c)));
        assertEquals(v,ventaServicio.nuevaVenta(carrito,c));
    }
    //Caja blanca
    @Test
    public void whenAddProductoToVenta_thenReturnVenta(){
        Cliente cliente = new Cliente();
        cliente.setNombre("Alejandro");
        LineaDeVenta lineaDeVenta = new LineaDeVenta(new Producto("1","Ordenador Portátil",699.0),10,6990.0);
        LineaDeVenta lineaDeVenta2 = new LineaDeVenta(new Producto("2","Ordenador de sobremesa",499.0),10,4990.0);
        List<LineaDeVenta> lista = new ArrayList<>();
        lista.add(lineaDeVenta);
        Optional<Venta> optionalVenta= Optional.of(new Venta(1L, LocalDate.of(2022, 01, 18), lista, cliente));
        lista.add(lineaDeVenta2);
        Venta venta =new Venta(1L, LocalDate.of(2022, 01, 18), lista, cliente);

        lenient().when(productoRepositorio.findOne("2")).thenReturn(new Producto("2", "Ordenador portátil", 699.0));
        lenient().when(ventaRepositorio.findOneOptional(1L)).thenReturn(optionalVenta);
        lenient().when(ventaRepositorio.edit(optionalVenta.get())).thenReturn(venta);

        Venta v2 = ventaServicio.addProductoToVenta(1L,"2",10);
        assertEquals(venta,v2);
    }
    //Caja blanca
    @Test
    public void whenRemoveLineaVenta_thenReturnVenta(){
        Cliente cliente = new Cliente();
        cliente.setNombre("Alejandro");
        LineaDeVenta lineaDeVenta = new LineaDeVenta(new Producto("1","Ordenador Portátil",699.0),10,6990.0);
        LineaDeVenta lineaDeVenta2 = new LineaDeVenta(new Producto("2","Ordenador de sobremesa",499.0),10,4990.0);
        List<LineaDeVenta> lista = new ArrayList<>();
        lista.add(lineaDeVenta);
        lista.add(lineaDeVenta2);
        Optional<Venta> optionalVenta= Optional.of(new Venta(1L, LocalDate.of(2022, 01, 18), lista, cliente));
        lista.remove(lineaDeVenta2);
        Venta venta =new Venta(1L, LocalDate.of(2022, 01, 18), lista, cliente);
        lista.add(lineaDeVenta2);


        lenient().when(ventaRepositorio.findOneOptional(1L)).thenReturn(optionalVenta);
        lenient().when(ventaRepositorio.edit(optionalVenta.get())).thenReturn(venta);

        Venta v2 = ventaServicio.removeLineaVenta(1L,"2");
        assertEquals(venta,v2);
    }

}
