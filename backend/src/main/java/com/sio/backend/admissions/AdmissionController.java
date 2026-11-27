package com.sio.backend.admissions;

import com.sio.backend.generics.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admissions")
class AdmissionController {
    private final AdmissionService service;

    public AdmissionController(AdmissionService service){
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<AdmissionResponseDTO>> getActiveAdmissions(){
        return ApiResponse.success(service.getActiveAdmissions());
    }

    @GetMapping("/{id}")
    public ApiResponse<AdmissionResponseDTO> getAdmissionById(@PathVariable Integer id){
        return ApiResponse.success(service.getAdmissionById(id));
    }

    @GetMapping("/braccialetto/{code}")
    public ApiResponse<AdmissionResponseDTO> getAdmissionByBraccialetto(@PathVariable String code){
        return ApiResponse.success(service.getAdmissionByBraccialetto(code));
    }

    @PostMapping
    public ApiResponse<AdmissionResponseDTO> accettaPaziente(
            @Valid @RequestBody AdmissionRequestDTO req
    ){
        return ApiResponse.success(service.accettazionePaziente(req));
    }

    // admissions/<ID>/stato
    @PutMapping("/{id}/stato")
    public ApiResponse<AdmissionResponseDTO> updateStato(
            @PathVariable Integer id,
            @RequestParam AdmissionStatus stato
    ){
        return ApiResponse.success(service.updateStato(id, stato));
    }
}
