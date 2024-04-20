package com.example.demo.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaRequest {

    private String razonSocial;
    private String tipoDocumento;
    private String numeroDocumento;
    private String condicion;
    private String direccion;
    private String distrito;
    private String provincia;
    private String departamento;
    private Boolean esAgenteRetencion;


}
