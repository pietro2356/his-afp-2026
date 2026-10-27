package com.sio.backend.resources;

import com.sio.backend.core.dtos.APIResponse;
import com.sio.backend.resources.ArrivalMode.ArrivalModeDTO;
import com.sio.backend.resources.pathology.PathologyDTO;
import com.sio.backend.resources.triage_colors.TriageColorDTO;
import com.sio.backend.resources.ArrivalMode.ArrivalModeService;
import com.sio.backend.resources.pathology.PathologyService;
import com.sio.backend.resources.triage_colors.TriageColorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/resources", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResourcesController {

    private final TriageColorService triageColorService;
    private final ArrivalModeService arrivalModeService;
    private final PathologyService pathologyService;

    public ResourcesController(TriageColorService triageColorService, ArrivalModeService arrivalModeService, PathologyService pathologyService) {
        this.triageColorService = triageColorService;
        this.arrivalModeService = arrivalModeService;
        this.pathologyService = pathologyService;
    }


    @GetMapping("/triage-colors")
    public APIResponse<List<TriageColorDTO>> getTriageColors() {
        List<TriageColorDTO> triageColorDTOs = triageColorService.getTriageColors();

        return APIResponse.success(triageColorDTOs);
    }

    @GetMapping("/arrival-modes")
    public APIResponse<List<ArrivalModeDTO>> getArrivalModes() {
        List<ArrivalModeDTO> arrivalModeDTOs = arrivalModeService.getArrivalModes();

        return APIResponse.success(arrivalModeDTOs);
    }

    @GetMapping("/pathologies")
    public APIResponse<List<PathologyDTO>> getPathologies() {
        List<PathologyDTO> pathologyDTOs = pathologyService.getPathologies();

        return APIResponse.success(pathologyDTOs);
    }
}
