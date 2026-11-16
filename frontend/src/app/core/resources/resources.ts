import { inject, Service, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IPathology } from './resources.model';
import { ApiResponse } from '../generics/ApiResponse.model';

@Service()
export class Resources {
  readonly #http = inject(HttpClient);
  readonly #pathologies = signal<IPathology[]>([]);

  pathologies = this.#pathologies.asReadonly();

  public fetch() {
    this.fetchPathology();
  }

  private fetchPathology() {
    this.#http
      .get<ApiResponse<IPathology[]>>(`http://localhost:8080/resources/pathologies`)
      .subscribe({
        next: (res) => {
          this.#pathologies.set(res.data);
        },
        error: (err) => {
          console.error('Errore durante il fetch delle patologie:', err);
        },
      });
  }
}
