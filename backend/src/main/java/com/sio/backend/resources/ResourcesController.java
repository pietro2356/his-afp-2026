package com.sio.backend.resources;

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
    public List<PathologiesDTO> getPathologies(){
        return pathologiesService.getAllPathologies();
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
}
