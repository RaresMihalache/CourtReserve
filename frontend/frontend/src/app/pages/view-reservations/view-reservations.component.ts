import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ReservationRequest } from '../../model/reservation';
import { ReservationService } from '../../service/reservation-service';
import { Reservation } from '../../model/reservation-obj';

@Component({
  selector: 'app-view-reservations',
  templateUrl: './view-reservations.component.html',
  styleUrls: ['./view-reservations.component.scss']
})
export class ViewReservationsComponent implements OnInit {

  datePipe: DatePipe = new DatePipe('en-US');

  allReservations: ReservationRequest[] = [];
  takeoverReservations: Reservation[] = [];
  partnerReservations: Reservation[] = [];
  completedReservations: Reservation[] = [];
  userSessionString = sessionStorage.getItem('user');
  reservationDetails: Reservation = {};
  secondUserName: string | undefined;
  timeDifferenceNowReservation:any;

  selectedReservationId: any;
  reservationType: string = '';
  displayed:boolean = false;

  now = this.datePipe.transform(new Date(), 'yyyy-MM-dd HH:mm');
  

  constructor(private reservationService: ReservationService) { }

  ngOnInit(): void {
  }

  getReservationsByStatus(){
    this.reservationService.getReservationsByStatus('0').subscribe((reservationList) => {
      reservationList.forEach((reservation) => {
        console.log(reservation.users);
        reservation.users?.forEach((user) => {
          const userSession = this.userSessionString ? JSON.parse(this.userSessionString) : null;
          if(user.id === userSession.id){
            console.log(reservation);
            this.takeoverReservations.push(reservation);
          }
        });
    });
    });

    this.reservationService.getReservationsByStatus('1').subscribe((reservationList) => {
      console.log(reservationList);
      reservationList.forEach((reservation) => {
        console.log(reservation.users);
        reservation.users?.forEach((user) => {
          const userSession = this.userSessionString ? JSON.parse(this.userSessionString) : null;
          if(user.id === userSession.id){
            console.log(reservation);
            this.partnerReservations.push(reservation);
          }
        });
    });
  });

    this.reservationService.getReservationsByStatus('2').subscribe((reservationList) => {
      reservationList.forEach((reservation) => {
        console.log(reservation.users);
        reservation.users?.forEach((user) => {
          const userSession = this.userSessionString ? JSON.parse(this.userSessionString) : null;
          if(user.id === userSession.id){
            console.log(reservation);
            this.completedReservations.push(reservation);
          }
        });
    });

    });

    this.displayed = true;
  }


  setReservationType(type: string){
    this.reservationType = type;
    console.log(this.reservationType);
  }

  setSelectedReservationId(reservation: Reservation){
    this.selectedReservationId = reservation.id;
    this.reservationService.getReservationById(this.selectedReservationId).subscribe((reservationDetails) => {
      this.reservationDetails = reservationDetails;
      this.reservationDetails.startTime = this.reservationDetails.startTime?.replace('T', ' ');
      this.reservationDetails.endTime = this.reservationDetails.endTime?.replace('T', ' ');
      if(this.reservationDetails.users != undefined){
        if(this.reservationDetails.users[1] != undefined)
          this.secondUserName = this.reservationDetails.users[1].name;
        else
          this.secondUserName = '-';        
      }

      const startTime = this.datePipe.transform(this.reservationDetails.startTime, 'yyyy-MM-dd HH:mm');
      const currentTime = this.now;

      if(startTime && currentTime){
        const timeDifferenceInMilliseconds = new Date(startTime).getTime() - new Date(currentTime).getTime();
        const timeDifferenceInHours = timeDifferenceInMilliseconds / (1000 * 60 * 60);
        this.timeDifferenceNowReservation = timeDifferenceInHours;
      }

      console.log(this.reservationDetails);
      console.log(this.secondUserName);
    });
    console.log(this.selectedReservationId);
    console.log(this.now);
  }

  cancelReservation(){
    this.reservationService.deleteReservationById(this.selectedReservationId).subscribe();
    window.location.reload();
  }

  findPlayers(){
    this.reservationService.findPlayers(this.selectedReservationId).subscribe();
    window.location.reload();
  }

}
