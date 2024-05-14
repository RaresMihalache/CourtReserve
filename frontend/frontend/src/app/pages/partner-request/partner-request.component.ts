import { Component, OnInit } from '@angular/core';
import { ReservationService } from 'src/app/service/reservation-service';
import { AddUserRequest } from 'src/app/model/add-user-request';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-partner-request',
  templateUrl: './partner-request.component.html',
  styleUrls: ['./partner-request.component.scss']
})
export class PartnerRequestComponent implements OnInit {

  datePipe: DatePipe = new DatePipe('en-US');

  reservations: any[] = [];
  id: string = '';

  date = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
  today = this.date;

  display = false;

  constructor(
    private reservationService: ReservationService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const user = sessionStorage.getItem('user');

    if(user && JSON.parse(user as string).roleId == '1'){
      this.id = JSON.parse(user as string).id;
    }
    else {
      this.router.navigate(['login'])
    }
  }

  addUserToReservation(reservationId: string) {
    const request: AddUserRequest = {
      id: reservationId,
      user: {
        id: this.id
      }
    }
    this.reservationService.addUserToReservation(request).subscribe(
      response => console.log(response)
    );

    window.location.reload();
  }

  search() {
    this.reservationService.getPartnerRequests().subscribe(
      response => this.reservations = response.filter(reservation => reservation.startTime?.substring(0,10) == this.date)  
    );
    
    this.display = true;
  }

  emptyReservations(){
    return this.reservations.length == 0;
  }
}
