package com.salesianostriana.dam.model;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LineaDeVenta {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Producto producto;

    private int cantidad;
    private double pvp; // precio unitario

    @ManyToOne
    private Venta venta;

    public LineaDeVenta(Producto producto, int cantidad, double pvp) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.pvp = pvp;
    }

    @Override
    public String toString() {
        return "LineaDeVenta{" +
                "id=" + id +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", pvp=" + pvp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineaDeVenta)) return false;
        LineaDeVenta that = (LineaDeVenta) o;
        return cantidad == that.cantidad && Double.compare(that.pvp, pvp) == 0 && Objects.equals(id, that.id) && Objects.equals(producto, that.producto) && Objects.equals(venta, that.venta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, producto, cantidad, pvp);
    }
}
