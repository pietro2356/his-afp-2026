import { Component, signal } from '@angular/core';
import { Divider } from '@openng/optimus-ui/divider';
import { ToggleTheme } from '../toggle-theme/toggle-theme';
import { NgOptimizedImage } from '@angular/common';

@Component({
  imports: [Divider, ToggleTheme, NgOptimizedImage],
  selector: 'sio-header',
  styleUrl: './header.css',
  templateUrl: './header.html',
})
export class Header {
  readonly reparto = signal('Pronto Soccorso');
  readonly struttura = signal('Presidio Ospedaliero di Arco');
}
