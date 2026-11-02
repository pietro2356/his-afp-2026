package com.sio.backend.resources;

import com.sio.backend.generics.ApiResponse;
import com.sio.backend.resources.pathology.PathologiesDTO;
import com.sio.backend.resources.pathology.PathologiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources")
class ResourcesController {

    @Autowired
    private PathologiesService pathologiesService;

    @GetMapping("/pathologies")
    public ApiResponse<List<PathologiesDTO>> getAllPathologies(){
        return ApiResponse.success(
                pathologiesService.getAllPathologies()
        );
    }

    @GetMapping("/pathologies/{code}")
    public ApiResponse<PathologiesDTO> getPathologiesByCode(@PathVariable String code){
        PathologiesDTO dto = pathologiesService.getPathologyById(code);

        return ApiResponse.success(dto);
    }

    @PostMapping("/pathologies")
    public PathologiesDTO createPathology(@RequestBody PathologiesDTO dto){
        return pathologiesService.createNewPathology(dto);
    }

    @DeleteMapping("/pathologies")
    public void deletePathology(@RequestParam String code){
        pathologiesService.deletePathology(code);
    }

    @PutMapping("/pathologies")
    public void editPathology(@RequestBody PathologiesDTO dto){
        pathologiesService.editPathology(dto);
    }

    @GetMapping("/sim-error")
    public ApiResponse<PathologiesDTO> getSimError() throws Exception {
        throw new Exception("Eccezione dinamica lanciata");
//        return ApiResponse.error(
//                "Messaggio di errore simulato",
//                HttpStatus.INTERNAL_SERVER_ERROR.value()
//                );
    }
}
