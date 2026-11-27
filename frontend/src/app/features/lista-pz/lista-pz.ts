import { Component, inject } from '@angular/core';
import { Resources } from '../../core/resources/resources';
import { Admissions } from '../../core/admissions/admissions';

@Component({
  imports: [],
  selector: 'sio-lista-pz',
  styleUrl: './lista-pz.css',
  templateUrl: './lista-pz.html',
})
export class ListaPz {
  protected readonly admService = inject(Admissions);
}
