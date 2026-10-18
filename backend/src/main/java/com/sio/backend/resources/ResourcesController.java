package com.sio.backend.resources;

import com.sio.backend.core.dtos.APIResponse;
import com.sio.backend.resources.dtos.TriageColorDTO;
import com.sio.backend.resources.services.TriageColorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourcesController {

    public final TriageColorService triageColorService;

    public ResourcesController(TriageColorService triageColorService) {
        this.triageColorService = triageColorService;
    }

    @GetMapping("/triage-colors")
    public APIResponse<List<TriageColorDTO>> getTriageColors() {
        List<TriageColorDTO> triageColorDTOs = triageColorService.getTriageColors();

        return APIResponse.success(triageColorDTOs);
    }
}
