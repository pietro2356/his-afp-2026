import { inject, Service, signal } from '@angular/core';
import { AdmissionControllerService, AdmissionResponseDTO } from '../api';
import { finalize } from 'rxjs';

@Service()
export class Admissions {
  readonly #admController = inject(AdmissionControllerService);

  readonly #listaPz = signal<AdmissionResponseDTO[]>([]);
  readonly listaPz = this.#listaPz.asReadonly();

  readonly #loading = signal<boolean>(false);
  readonly loading = this.#loading.asReadonly();

  constructor() {
    this.fetchAdmissions();
  }

  public fetchAdmissions(): void{
    this.#loading.set(true);
    this.#admController.getActiveAdmissions()
      .pipe(finalize(() => this.#loading.set(false)))
      .subscribe({
        next: (res) => {
          console.log(res);
          this.#listaPz.set(res.data ?? []);
        },
        error: (err) => {
          this.#listaPz.set([]);
        }
      })
  }

  public changeStato(): void{}
}
