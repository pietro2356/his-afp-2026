package com.sio.backend.resources;

import com.sio.backend.generics.ApiResponse;
import com.sio.backend.resources.pathology.PathologyDTO;
import com.sio.backend.resources.pathology.PathologyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/resources")
@Tag(name="Gestione risorse SIO", description = "Endpoint per la gestione delle risorse SIO")
class ResourcesController {

    @Autowired
    private PathologyService pathologiesService;


    @Operation(summary = "Recupera tutte le patologie", description = "Recupero di tutte le patologie presenti a DB")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Lista di patologie recuperata con successo"
        )
    })
    @GetMapping("/pathologies")
    public ApiResponse<Page<PathologyDTO>> getAllPathologies(
//            @PageableDefault(size = 10, sort = "code", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ApiResponse.success(
                pathologiesService.getAllPathologiesPaginated(pageable)
        );
    }

    @Operation(
            summary = "Recupera una patologia dato il suo codice",
            description = "Recupero di una patologia dato il suo codice"
    )
    @GetMapping("/pathologies/{code}")
    public ApiResponse<PathologyDTO> getPathologiesByCode(
            @Parameter(description = "Codice identificativo della patologia", example = "C02")
            @Valid
            @PathVariable
            String code
    ){
        PathologyDTO dto = pathologiesService.getPathologyById(code);

        return ApiResponse.success(dto);
    }

    @PostMapping("/pathologies")
    public PathologyDTO createPathology(@Valid @RequestBody PathologyDTO dto){
        return pathologiesService.createNewPathology(dto);
    }

    @DeleteMapping("/pathologies")
    public ApiResponse<Void> deletePathology(@RequestParam String code){
        pathologiesService.deletePathology(code);

        return ApiResponse.success(null);
    }

    @PutMapping("/pathologies")
    public ApiResponse<PathologyDTO> editPathology(@RequestBody PathologyDTO dto){
        PathologyDTO newDto = pathologiesService.editPathology(dto);
        return ApiResponse.success(newDto);
    }

    @GetMapping("/sim-error")
    public ApiResponse<PathologyDTO> getSimError() throws Exception {
        throw new Exception("è scoppiato tutto!");
//        throw new EntityNotFoundException("Patologia non trovata!");
//        throw new IllegalArgumentException("Dati non validi");
//        return ApiResponse.error(
//                "Messaggio di errore simulato",
//                HttpStatus.INTERNAL_SERVER_ERROR.value()
//                );
    }
}
