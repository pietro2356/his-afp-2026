export * from './admissionController.service';
import { AdmissionControllerService } from './admissionController.service';
export * from './gestioneRisorseSIO.service';
import { GestioneRisorseSIOService } from './gestioneRisorseSIO.service';
export * from './patientController.service';
import { PatientControllerService } from './patientController.service';
export const APIS = [AdmissionControllerService, GestioneRisorseSIOService, PatientControllerService];
