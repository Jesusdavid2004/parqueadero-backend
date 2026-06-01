package com.parqueadero.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDashboardResponse {
    private Long clienteId;
    private String nombreCliente;
    private Long totalVehiculos;
    private Long totalTickets;
    private String mensaje;
}