import { Routes } from '@angular/router';
import { MainLayout } from './layout/main-layout/main-layout';

export const routes: Routes = [
  {
    path: '',
    component: MainLayout,
    children: [
      {
        path: '',
        redirectTo: 'lista-pz',
        pathMatch: 'full',
      },
      {
        path: 'accettazione',
        loadComponent: () =>
          import('./features/accettazione/accettazione').then((m) => m.Accettazione),
      },
      {
        path: 'lista-pz',
        loadComponent: () =>
          import('./features/lista-pz/lista-pz').then(m => m.ListaPz),
      },
      {
        path: 'stato-servizi',
        loadComponent: () =>
          import('./features/stato-servizi/stato-servizi').then((m) => m.StatoServizi),
      }
    ],
  },
];
