import { Component } from '@angular/core';
import { OffreListComponent } from './components/offre-list/offre-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    OffreListComponent     // déclare notre composant
  ],
  templateUrl: './app.component.html'
})
export class AppComponent {
  title = 'JobHunter';
}