import { Component, OnInit } from '@angular/core';
import { AddUserRequest } from 'src/app/model/add-user-request';
import { Reservation } from 'src/app/model/reservation-obj';
import { ReservationService } from 'src/app/service/reservation-service';

@Component({
  selector: 'app-takeover-requests',
  templateUrl: './takeover-requests.component.html',
  styleUrls: ['./takeover-requests.component.scss']
})
export class TakeoverRequestsComponent implements OnInit {

  takeoverReservations: Reservation[] = [];
  userSessionString = sessionStorage.getItem('user');
  userId: string = '';
  selectedReservationId: any;

  constructor(private reservationService: ReservationService) { }

  ngOnInit(): void {
    this.getTakeoverReservations();

    if(this.userSessionString && JSON.parse(this.userSessionString as string).roleId == '1'){
      this.userId = JSON.parse(this.userSessionString as string).id;
    }
  }

  getTakeoverReservations(){
    this.reservationService.getReservationsByStatus('0').subscribe((reservationList) => {
      reservationList.forEach((reservation) => {
        console.log(reservation.users);
        reservation.users?.forEach((user) => {
          const userSession = this.userSessionString ? JSON.parse(this.userSessionString) : null;
          if(user.id !== userSession.id){
            console.log(reservation);
            this.takeoverReservations.push(reservation);
          }
        });
    });
    });
  }

  setSelectedReservationId(reservation: Reservation){
    this.selectedReservationId = reservation.id;
    console.log(this.selectedReservationId);
    const request: AddUserRequest = {
      id: this.selectedReservationId,
      user: {
        id: this.userId
      }
    }
    this.reservationService.addUserToReservation(request).subscribe(
      response => console.log(response)
    );

    window.location.reload();
  }

}
