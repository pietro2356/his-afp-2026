import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Sidebar } from '../../ui/sidebar/sidebar';
import { Header } from '../../ui/header/header';

@Component({
  imports: [RouterOutlet, Sidebar, Header],
  selector: 'sio-main-layout',
  styleUrl: './main-layout.css',
  templateUrl: './main-layout.html',
})
export class MainLayout {}
