package com.salesianostriana.dam;

import com.salesianostriana.dam.model.Cliente;
import com.salesianostriana.dam.model.LineaDeVenta;
import com.salesianostriana.dam.model.Producto;
import com.salesianostriana.dam.model.Venta;
import com.salesianostriana.dam.repos.ProductoRepositorio;
import com.salesianostriana.dam.repos.VentaRepositorio;
import com.salesianostriana.dam.service.VentaServicio;
import org.junit.jupiter.api.BeforeEach;
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
class DemoApplicationTests {

	@Mock
	VentaRepositorio ventaRepositorio;

	@Mock
	ProductoRepositorio productoRepositorio;

	@InjectMocks
	VentaServicio ventaServicio;


	//Caja negra
	@Test
	public void whenNuevaVenta_thenReturnVenta(){
		Cliente c = Cliente.builder()
				.dni("12312312A")
				.email("Ale@mgmail.com")
				.nombre("Alejandro")
				.build();

		Producto p = Producto.builder()
				.id(1L)
				.codigoProducto("1")
				.nombre("Ordenador Portátil")
				.precio(699.0)
				.build();

		Venta v = new Venta();
		v.setCliente(c);
		v.setLineasDeVenta(List.of(new LineaDeVenta(p,2,699.0)));
		Map<Long, Integer> venta = Map.of(1L,2);


		lenient().when(productoRepositorio.findById(1L)).thenReturn(java.util.Optional.ofNullable(p));
		lenient().when(ventaRepositorio.save(v)).thenReturn(v);
		assertEquals(v.hashCode(),ventaServicio.nuevaVenta(venta,c).hashCode());
	}
	//Caja blanca
	@Test
	public void whenAddProductoToVenta_thenReturnVenta(){
		Cliente c = Cliente.builder()
				.dni("12312312A")
				.email("Ale@gmail.com")
				.nombre("Alejandro")
				.build();

		LineaDeVenta lineaDeVenta = new LineaDeVenta(new Producto(1L,"1","Ordenador Portátil",699.0),10,6990.0);
		LineaDeVenta lineaDeVenta2 = new LineaDeVenta(new Producto(2L,"2","Ordenador de sobremesa",499.0),10,4990.0);
		List<LineaDeVenta> lista = new ArrayList<>();
		lista.add(lineaDeVenta);
		Optional<Venta> optionalVenta= Optional.of(new Venta(1L, LocalDate.of(2022, 01, 18), lista, c));
		lista.add(lineaDeVenta2);
		Venta venta =new Venta(1L, LocalDate.of(2022, 01, 18), lista, c);

		lenient().when(productoRepositorio.findById(2L)).thenReturn(Optional.of(new Producto(2L, "2", "Ordenador de sobremesa", 499.0)));
		lenient().when(ventaRepositorio.findById(1L)).thenReturn(optionalVenta);
		lenient().when(ventaRepositorio.save(optionalVenta.get())).thenReturn(venta);


		Venta v2 = ventaServicio.addProductoToVenta(1L,2L,10);
		assertEquals(venta,v2);
	}
	//Caja Blanca
	@Test
	public void whenRemoveLineaVenta_thenReturnVenta(){
		Cliente cliente = new Cliente();
		cliente.setNombre("Alejandro");
		LineaDeVenta lineaDeVenta = new LineaDeVenta(new Producto(1L,"1","Ordenador Portátil",699.0),10,6990.0);
		LineaDeVenta lineaDeVenta2 = new LineaDeVenta(new Producto(2L,"2","Ordenador de sobremesa",499.0),10,4990.0);
		List<LineaDeVenta> lista = new ArrayList<>();
		lista.add(lineaDeVenta);
		lista.add(lineaDeVenta2);
		Optional<Venta> optionalVenta= Optional.of(new Venta(1L, LocalDate.of(2022, 01, 18), lista, cliente));
		lista.remove(lineaDeVenta2);
		Venta venta =new Venta(1L, LocalDate.of(2022, 01, 18), lista, cliente);
		lista.add(lineaDeVenta2);


		lenient().when(ventaRepositorio.findById(1L)).thenReturn(optionalVenta);
		lenient().when(ventaRepositorio.save(optionalVenta.get())).thenReturn(venta);

		Venta v2 = ventaServicio.removeLineaVenta(1L,2L);
		System.out.println(v2);
		assertEquals(venta,v2);
	}


}
