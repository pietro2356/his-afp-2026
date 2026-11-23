package com.sio.backend.admissions;

import com.sio.backend.generics.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admissions")
public class AdmissionController {

    private final AdmissionService admissionService;

    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @GetMapping
    public ApiResponse<List<AdmissionResponseDTO>> getActiveAdmissions() {
        return ApiResponse.success(admissionService.getActiveAdmissions());
    }

    @GetMapping("/{id}")
    public ApiResponse<AdmissionResponseDTO> getAdmissionById(@PathVariable Integer id) {
        return ApiResponse.success(admissionService.getAdmissionById(id));
    }

    @GetMapping("/braccialetto/{code}")
    public ApiResponse<AdmissionResponseDTO> getAdmissionByBraccialetto(@PathVariable String code) {
        return ApiResponse.success(admissionService.getAdmissionByBraccialetto(code));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AdmissionResponseDTO> accettaPaziente(@Valid @RequestBody AdmissionRequestDTO request) {
        return ApiResponse.success(admissionService.accettaPaziente(request));
    }

    @PatchMapping("/{id}/stato")
    public ApiResponse<AdmissionResponseDTO> updateStato(
            @PathVariable Integer id,
            @RequestParam AdmissionStatus stato
    ) {
        return ApiResponse.success(admissionService.updateStato(id, stato));
    }
}