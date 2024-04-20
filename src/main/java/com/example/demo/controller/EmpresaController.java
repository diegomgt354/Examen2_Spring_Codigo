package com.example.demo.controller;

import com.example.demo.constants.Constants;
import com.example.demo.entity.Empresa;
import com.example.demo.request.EmpresaRequest;
import com.example.demo.service.EmpresaService;
import com.example.demo.utilities.Utilitarios;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empresa")
@AllArgsConstructor
@Tag(
        name = "Api de empresa",
        description = "Esta api nos permite obtener, registrar y eliminar una empresa"
)
public class EmpresaController {

    private final EmpresaService service;
    private final Utilitarios utilitarios;

    @GetMapping("/listarEmpresas")
    @Operation(
            summary = "listar las empresas",
            description = "En el EndPoint se buscan todos las Empresas provenientes de la db"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 -> Registro Exitoso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Empresa.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP STATUS 400 -> Solicitud incorrecta",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<List<Empresa>> listaEmpresas(){
        return ResponseEntity.ok(service.getAllEmpresas());
    }


    @GetMapping("/buscarPorId/{id}")
    @Operation(
            summary = "buscar empresa",
            description = "En el EndPoint se busca una Empresa por su id proveniente de la db",
            parameters = {
                    @Parameter(name = "id", description = "El id de la Empresa a buscar")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 -> Registro Exitoso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Empresa.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP STATUS 400 -> Solicitud incorrecta",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id){
        Empresa empresa = service.getByIdEmpresa(id);
        return (empresa != null)
                ?ResponseEntity.ok(empresa)
                :ResponseEntity.badRequest().build();
    }


    @PostMapping("/crearEmpresa")
    @Operation(
            summary = "crear empresa",
            description = "En el EndPoint se crea una Empresa para poder ser guardado en la db",
            parameters = {
                    @Parameter(name = "request", description = "El objeto de tipo EmpresaRequest nos ayuda a solo " +
                            "solicitar al cliente los datos necesarios para poder ser guadado, los datos de auditoria " +
                            "lo maneja el EndPoint")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 -> Registro Exitoso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Empresa.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP STATUS 400 -> Solicitud incorrecta",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<Empresa> crearProducto(@RequestBody EmpresaRequest request){
        Empresa nuevaEmpresa = new ModelMapper().map(request, Empresa.class);
        nuevaEmpresa.setEstado(Constants.STATUS_ACTIVE);
        nuevaEmpresa.setUsuaCrea(Constants.USU_ADMIN);
        nuevaEmpresa.setDateCreate(utilitarios.getTimestamp());
        return ResponseEntity.ok(service.addEmpresa(nuevaEmpresa));
    }

    @PutMapping("/actualizarEmpresa/{id}")
    @Operation(
            summary = "actualizar empresa",
            description = "En el EndPoint se actualiza una Empresa para poder ser actualizada en la db",
            parameters = {
                    @Parameter(name = "request", description = "El objeto de tipo EmpresaRequest nos ayuda a solo " +
                            "solicitar al cliente los datos necesarios para poder ser guadado, los datos de auditoria " +
                            "lo maneja el EndPoint"),
                    @Parameter(name = "id", description = "El id de la Empresa a editar")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 -> Registro Exitoso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Empresa.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP STATUS 400 -> Solicitud incorrecta",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<Empresa> actualizarEmpresa(@RequestBody EmpresaRequest request, @PathVariable Long id){
        Empresa actualizarEmpresa = service.getByIdEmpresa(id);
        if(actualizarEmpresa==null){
            return ResponseEntity.badRequest().build();
        }
        new ModelMapper().map(request,actualizarEmpresa);

        actualizarEmpresa.setUsuaModif(Constants.USU_ADMIN);
        actualizarEmpresa.setDateModif(utilitarios.getTimestamp());

        return ResponseEntity.ok(service.addEmpresa(actualizarEmpresa));
    }


    @DeleteMapping("/eliminarEmpresa/{id}")
    @Operation(
            summary = "eliminar empresa",
            description = "En el EndPoint se elimina de manera logica una Empresa para poder ser actualizado en la db",
            parameters = {
                    @Parameter(name = "id", description = "El id de la Empresa a editar")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 -> Registro Exitoso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Empresa.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP STATUS 400 -> Solicitud incorrecta",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<Empresa> eliminarEmpresa(@PathVariable Long id){
        Empresa eliminarEmpresa = service.getByIdEmpresa(id);
        if(eliminarEmpresa==null){
            return ResponseEntity.badRequest().build();
        }
        eliminarEmpresa.setEstado(Constants.STATUS_INACTIVE);
        eliminarEmpresa.setUsuaDelet(Constants.USU_ADMIN);
        eliminarEmpresa.setDateDelet(utilitarios.getTimestamp());
        return ResponseEntity.ok(service.addEmpresa(eliminarEmpresa));
    }


}
