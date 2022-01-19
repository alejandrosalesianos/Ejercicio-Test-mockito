package com.salesianostriana.dam.model;

import lombok.*;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {


    @Id @GeneratedValue
    private Long id;

    @Builder.Default
    private LocalDate fecha = LocalDate.now();

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<LineaDeVenta> lineasDeVenta = new ArrayList<>();

    @ManyToOne
    private Cliente cliente;

    public void addLineaVenta(LineaDeVenta lineaDeVenta) {
        lineaDeVenta.setVenta(this);
        lineasDeVenta.add(lineaDeVenta);
    }

    public void removeLineaVenta(LineaDeVenta lineaDeVenta) {
        lineaDeVenta.setVenta(null);
        lineasDeVenta.remove(lineaDeVenta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venta)) return false;
        Venta venta = (Venta) o;
        return Objects.equals(id, venta.id) && Objects.equals(fecha, venta.fecha) && Objects.equals(lineasDeVenta, venta.lineasDeVenta) && Objects.equals(cliente, venta.cliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha, lineasDeVenta, cliente);
    }
}
