import { Component, inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {
  ArrivalModeDTO,
  PathologyDTO,
  ResourcesControllerService,
  TriageColorDTO,
} from './core/api';

@Component({
  imports: [],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  private readonly resourceAPI = inject(ResourcesControllerService);

  listaColori = signal<TriageColorDTO[]>([]);
  listaModArrivo = signal<ArrivalModeDTO[]>([]);
  listaPatologie = signal<PathologyDTO[]>([]);

  fetchTriageColors() {
    this.resourceAPI.getTriageColors()
      .subscribe({
        next: (res) => this.listaColori.set(res.data ?? []),
        error: () => this.listaColori.set([]),
      })
  }

  fetchModArriva(){
    this.resourceAPI.getArrivalModes().subscribe({
      next: (res) => this.listaModArrivo.set(res.data ?? []),
      error: () => this.listaModArrivo.set([]),
    });
  }

  fetchPatho(){
    this.resourceAPI
      .getPathologies()
      .subscribe({
        next: (res) => this.listaPatologie.set(res.data ?? []),
        error: () => this.listaPatologie.set([]),
      });
  }
}
