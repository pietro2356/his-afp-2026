package com.sio.backend.resources;

import com.sio.backend.generics.ApiResponse;
import com.sio.backend.resources.pathology.PathologiesDTO;
import com.sio.backend.resources.pathology.PathologiesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources")
@Tag(name="Gestione risorse SIO", description = "Endpoint per la gestione delle risorse SIO")
class ResourcesController {

    @Autowired
    private PathologiesService pathologiesService;


    @Operation(summary = "Recupera tutte le patologie", description = "Recupero di tutte le patologie presenti a DB")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Lista di patologie recuperata con successo"
        )
    })
    @GetMapping("/pathologies")
    public ApiResponse<Page<PathologiesDTO>> getAllPathologies(
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
    public ApiResponse<PathologiesDTO> getPathologiesByCode(
            @Parameter(description = "Codice identificativo della patologia", example = "C02")
            @Valid
            @PathVariable
            String code
    ){
        PathologiesDTO dto = pathologiesService.getPathologyById(code);

        return ApiResponse.success(dto);
    }

    @PostMapping("/pathologies")
    public PathologiesDTO createPathology(@Valid @RequestBody PathologiesDTO dto){
        return pathologiesService.createNewPathology(dto);
    }

    @DeleteMapping("/pathologies")
    public ApiResponse<Void> deletePathology(@RequestParam String code){
        pathologiesService.deletePathology(code);

        return ApiResponse.success((Void) null);
    }

    @PutMapping("/pathologies")
    public ApiResponse<PathologiesDTO> editPathology(@RequestBody PathologiesDTO dto){
        PathologiesDTO newDto = pathologiesService.editPathology(dto);
        return ApiResponse.success(newDto);
    }

    @GetMapping("/sim-error")
    public ApiResponse<PathologiesDTO> getSimError() throws Exception {
        throw new Exception("è scoppiato tutto!");
//        throw new EntityNotFoundException("Patologia non trovata!");
//        throw new IllegalArgumentException("Dati non validi");
//        return ApiResponse.error(
//                "Messaggio di errore simulato",
//                HttpStatus.INTERNAL_SERVER_ERROR.value()
//                );
    }
}
