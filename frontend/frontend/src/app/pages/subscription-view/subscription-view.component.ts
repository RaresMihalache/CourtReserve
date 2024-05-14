import { Component, OnInit } from '@angular/core';
import { CourtService } from 'src/app/service/court-service';
import { Address } from 'src/app/model/address';
import { Court } from 'src/app/model/court';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { ReservationRequest } from 'src/app/model/reservation';
import { ReservationService } from 'src/app/service/reservation-service';
import { UserService } from 'src/app/service/user-service';
import { User } from 'src/app/model/user';
import { SubscriptionService } from 'src/app/service/subscription-service';
import { SubscriptionResponse } from 'src/app/model/subscription-response';

@Component({
  selector: 'app-subscription-view',
  templateUrl: './subscription-view.component.html',
  styleUrls: ['./subscription-view.component.scss']
})
export class SubscriptionViewComponent implements OnInit {

  datePipe: DatePipe = new DatePipe('en-US');

  constructor(
    private courtService: CourtService,
    private reservationService: ReservationService,
    private userService: UserService,
    private subscriptionService: SubscriptionService,
    private router: Router
  ) { }

  user: User = {}
  id: string = '';

  addresses: Address[] = [];
  selectedAddress: Address = {};
  
  courts: Court[] = [];
  selectedCourtId: string = '';

  selectedDay: number = 1;
  startHour = '';
  endHour = '';
  occurence = 2;

  subscriptions: SubscriptionResponse[] = [];

  ngOnInit(): void {
    var user = sessionStorage.getItem('user');

    if(user && JSON.parse(user as string).roleId == '1'){
      this.id = JSON.parse(user as string).id;
      this.userService.getById(this.id).subscribe(
        result => this.user = result
      );
    }
    else{
      this.router.navigate(['login']);
    }
    
    this.getAllAddresses();
    this.subscriptionService.getSubscriptionsByUserId(this.id).subscribe(subscriptions => this.subscriptions = subscriptions);
  }

  getAllAddresses() {
    this.courtService.getAllAddresses().subscribe(
      (addresses) => {
        this.addresses = addresses;
      }
    );
  }

  getAllCourtsBySelectedAddress(){ 
    this.courtService.getAll().subscribe(
      result => {
        this.courts = result.filter(court => court.address.id == this.selectedAddress.id)
      }
    )
  }

  async createSubscription() {
    this.subscriptionService.createSubscription().subscribe(
      (subscription) => {
        var date = new Date();    
    
        for(let i = 0; i < this.occurence; i++){
          if(i == 0){
            date.setDate(date.getDate() + (this.selectedDay + (7 - date.getDay())) % 7);
          }
          else{
            date.setDate(date.getDate() + (this.selectedDay + (7 - date.getDay())) % 7 + 7);
          }

          let dateString = this.datePipe.transform(date, 'yyyy-MM-dd');
          let startHour = this.startHour + ':01';
          let endHour = this.endHour + ':01'

          let startTime = dateString + ' ' + startHour;
          let endTime = dateString + ' ' + endHour;

          let reservation: ReservationRequest = {
            subscriptionId: subscription.id,
            reservation: {
              startTime: startTime,
              endTime: endTime,
              court: {
                id: this.selectedCourtId,
              },
              users: [
                {id: this.id}
              ]
            },
            sendNotificationTo: {
              id: this.id,
              name: this.user.name,
              email: this.user.email
            }
          }
          
          this.reservationService.createReservation(reservation).subscribe();
        }
        this.resetForm();
      }
    );

    window.location.reload();
  }

  resetForm() {
    this.selectedDay = 1;
    this.startHour = '';
    this.endHour = '';
    this.occurence = 2;
  }

  generateInvoice(id: any) {
    this.subscriptionService.computePrice(id).subscribe(
      () => {alert('Invoice sent!');}
      ,
      (error) => {alert(error);}
    );
  }
}
