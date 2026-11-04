package com.sio.backend.resources;

import com.sio.backend.generics.ApiResponse;
import com.sio.backend.resources.pathology.PathologyDTO;
import com.sio.backend.resources.pathology.PathologyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources")
class ResourcesController {

    private final PathologyService pathologyService;

    ResourcesController(PathologyService pathologyService) {
        this.pathologyService = pathologyService;
    }

    @GetMapping("/pathologies")
    public ApiResponse<List<PathologyDTO>> getAllPathologies(){
        return ApiResponse.success(
                pathologyService.getAllPathologies()
        );
    }

    @GetMapping("/pathologies/{code}")
    public ApiResponse<PathologyDTO> getPathologiesByCode(@Valid @PathVariable String code){
        PathologyDTO dto = pathologyService.getPathologyById(code);

        return ApiResponse.success(dto);
    }

    @PostMapping("/pathologies")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PathologyDTO> createPathology(@Valid @RequestBody PathologyDTO dto){
        return ApiResponse.success(pathologyService.createNewPathology(dto));
    }

    @DeleteMapping("/pathologies/{code}")
    public ApiResponse<Void> deletePathology(@PathVariable String code){
        pathologyService.deletePathology(code);

        return ApiResponse.success((Void) null);
    }

    @PutMapping("/pathologies")
    public ApiResponse<PathologyDTO> editPathology(@Valid @RequestBody PathologyDTO dto){
        PathologyDTO newDto = pathologyService.editPathology(dto);
        return ApiResponse.success(newDto);
    }
}
