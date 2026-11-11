import { Component, signal } from '@angular/core';
import { MainLayout } from './layout/main-layout/main-layout';

@Component({
  imports: [MainLayout],
  selector: 'sio-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('frontend');
}
