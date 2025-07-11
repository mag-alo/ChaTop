import { Component } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { RentalsService } from '../../services/rentals.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {

  public baseUrl = environment.baseUrl;
  public rentals$ = this.rentalsService.all();

  constructor(
    private sessionService: SessionService,
    private rentalsService: RentalsService
  ) { }

  get user(): User | undefined {
    return this.sessionService.user;
  }
}
