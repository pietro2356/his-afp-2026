package com.sio.backend.resources;

import com.sio.backend.generics.ApiResponse;
import com.sio.backend.resources.pathology.PathologiesDTO;
import com.sio.backend.resources.pathology.PathologiesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources")
class ResourcesController {

    private final PathologiesService pathologiesService;

    ResourcesController(PathologiesService pathologiesService) {
        this.pathologiesService = pathologiesService;
    }

    @GetMapping("/pathologies")
    public ApiResponse<List<PathologiesDTO>> getAllPathologies(){
        return ApiResponse.success(
                pathologiesService.getAllPathologies()
        );
    }

    @GetMapping("/pathologies/{code}")
    public ApiResponse<PathologiesDTO> getPathologiesByCode(@Valid @PathVariable String code){
        PathologiesDTO dto = pathologiesService.getPathologyById(code);

        return ApiResponse.success(dto);
    }

    @PostMapping("/pathologies")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PathologiesDTO> createPathology(@Valid @RequestBody PathologiesDTO dto){
        return ApiResponse.success(pathologiesService.createNewPathology(dto));
    }

    @DeleteMapping("/pathologies/{code}")
    public ApiResponse<Void> deletePathology(@PathVariable String code){
        pathologiesService.deletePathology(code);

        return ApiResponse.success((Void) null);
    }

    @PutMapping("/pathologies")
    public ApiResponse<PathologiesDTO> editPathology(@Valid @RequestBody PathologiesDTO dto){
        PathologiesDTO newDto = pathologiesService.editPathology(dto);
        return ApiResponse.success(newDto);
    }
}
