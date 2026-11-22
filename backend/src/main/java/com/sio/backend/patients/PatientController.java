package com.sio.backend.patients;

import com.sio.backend.generics.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ApiResponse<List<PatientDTO>> getAllPatients() {
        return ApiResponse.success(patientService.getAllPatient());
    }

    @GetMapping("/{id}")
    public ApiResponse<PatientDTO> getPatientById(@PathVariable Integer id) {
        return ApiResponse.success(patientService.getPatientById(id));
    }

    @GetMapping("/search")
    public ApiResponse<PatientDTO> getPatientByFiscalCode(@RequestParam String cf) {
        return ApiResponse.success(patientService.getPatientByCF(cf));
    }
}
