package com.parqueadero.dto;

public class FacturaDTO {

    private Long id;
    private String numeroFactura;
    private Double subtotal;
    private Double impuesto;
    private Double total;
    private Long pagoId;

    public FacturaDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Double getImpuesto() {
        return impuesto;
    }

    public Double getTotal() {
        return total;
    }

    public Long getPagoId() {
        return pagoId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setPagoId(Long pagoId) {
        this.pagoId = pagoId;
    }
}