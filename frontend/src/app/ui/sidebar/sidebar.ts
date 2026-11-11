import { Component, signal } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { Tooltip } from '@openng/optimus-ui/tooltip';
import { Tag } from '@openng/optimus-ui/tag';

interface NavItem {
  label: string;
  icon: string;
  route: string;
}
type TAmbiente = 'S' | 'T' | 'P';

@Component({
  imports: [RouterLink, RouterLinkActive, Tooltip, Tag],
  selector: 'sio-sidebar',
  styleUrl: './sidebar.css',
  templateUrl: './sidebar.html',
})
export class Sidebar {
  readonly navItems = signal<NavItem[]>([
    {
      label: 'Dashboard',
      icon: 'pi pi-user',
      route: '/dashboard',
    },
    {
      label: 'Accettazione Paziente',
      icon: 'pi pi-user-plus',
      route: '/accettazione',
    },
    {
      label: 'Stato Servizi',
      icon: 'pi pi-server',
      route: '/stato',
    },
  ]);
  readonly versione = signal('v0.0.1');
  readonly ambiente = signal<TAmbiente>('T');
}
