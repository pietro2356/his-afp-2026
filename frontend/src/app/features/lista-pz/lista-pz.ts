import { Component, inject } from '@angular/core';
import { Resources } from '../../core/resources/resources';
import { Admissions } from '../../core/admissions/admissions';
import { TableModule } from '@openng/optimus-ui/table';
import { Row } from '../../core/directives/row';
import { DatePipe } from '@angular/common';
import { Tooltip } from '@openng/optimus-ui/tooltip';
import { Tag } from '@openng/optimus-ui/tag';

@Component({
  imports: [TableModule, Row, DatePipe, Tooltip, Tag],
  selector: 'sio-lista-pz',
  styleUrl: './lista-pz.css',
  templateUrl: './lista-pz.html',
})
export class ListaPz {
  protected readonly admService = inject(Admissions);
}
