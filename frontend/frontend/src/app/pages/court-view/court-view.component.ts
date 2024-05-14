import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Address } from 'src/app/model/address';
import { Court } from 'src/app/model/court';
import { CourtService } from 'src/app/service/court-service';
import { ReservationService } from 'src/app/service/reservation-service';
import { FormGroup, FormControl } from '@angular/forms';
import { ReservationRequest } from 'src/app/model/reservation';
import { CourtCreateDto } from 'src/app/model/court-create-dto';
import { SubscriptionService } from 'src/app/service/subscription-service';
import { Tariff } from 'src/app/model/tariff';

@Component({
  selector: 'app-court-view',
  templateUrl: './court-view.component.html',
  styleUrls: ['./court-view.component.scss']
})
export class CourtViewComponent implements OnInit {

  datePipe: DatePipe = new DatePipe('en-US');
  id: string = '';
  
  selectedAddress: Address = {};
  selectedAddressBool = false;
  todayDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
  selectedDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
  selectedStart = '';
  selectedEnd = '';

  selectedCourtId: any;
  hoursDiff: number = 0;
  selectedCourt: any;
  reservationPrice: any;
  pricePerHourDay = 50;
  pricePerHourNight: any;
  seasonPercent = 5;

  displayCourts = true;
  isLoggedUser = false;
  
  courts: Court[] = [];
  filteredCourts: Court[] = []
  addresses: Address[] = [];

  description: string = '';

  reservationForm = new FormGroup ({
    id: new FormControl(''),
    startTime: new FormControl(),
    endTime: new FormControl(),
    court: new FormControl(''),
    users: new FormControl(''),
  });

  constructor(
    private courtService: CourtService, 
    private reservationService: ReservationService,
    private subscriptionService: SubscriptionService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    var user = sessionStorage.getItem('user');

    if(user && JSON.parse(user as string).roleId == '1'){
      this.description = 'Select where and when you want to make a reservation.';
      this.isLoggedUser = true;
      this.id = JSON.parse(user as string).id;
    }
    
    this.courtService.getAllAddresses().subscribe(
      (addresses) => this.addresses = addresses
    );

    //this.getAllAvailableCourts();
  }

  getAllCourts() {
    this.courtService.getAll().subscribe(courts => this.courts = courts);
  }

  getAllAvailableCourts() {
    this.reservationService.getAvailableCourts(this.selectedDate, this.selectedStart, this.selectedEnd).subscribe(courts => this.courts = courts);
    this.getHours();
  }

  getAvailableCourtsForSelectedAddress() {
    this.filteredCourts = this.courts.filter((court) => court.address.id == this.selectedAddress.id);
  }

  createReservation() {
    this.subscriptionService.createSubscription().subscribe(
      (subscription) => {
          let reservation: ReservationRequest = {
            subscriptionId: subscription.id,
            reservation: {
              startTime: this.selectedDate + ' ' + this.selectedStart + ':01',
              endTime: this.selectedDate + ' ' + this.selectedEnd + ':01',
              court: {
                id: this.selectedCourt.id,
              },
              users: [
                {id: this.id}
              ]
            },
            sendNotificationTo: {
              id: this.id,
            }
          }
          this.selectedCourtId = reservation?.reservation?.court?.id;
          console.log(this.selectedCourtId);
          
          this.reservationService.createReservation(reservation).subscribe();
        }
    );

    window.location.reload();
  }

  display() {
    console.log(this.selectedDate);
  }

  getHours(){
    console.log(this.selectedStart);
    let startHour = parseInt(this.selectedStart);
    let endHour = parseInt(this.selectedEnd);
    this.hoursDiff = endHour - startHour;
    console.log("aia e" + this.hoursDiff);
  }

  computePrice(){

    let startReservationHour:string = this.selectedStart.substring(0, 2);

    if(parseInt(startReservationHour) > 20){
      this.courtService.getTariffByCourtId(this.selectedCourt.id).subscribe((tariff:Tariff) => {
        this.pricePerHourNight = tariff.nightTariff;
        let month = this.selectedDate?.substring(5, 7);
      if(this.isSummer(month))
        this.seasonPercent = 10;
      else
        this.seasonPercent = 5;
      });
      this.courtService.getTariffByCourtId(this.selectedCourt.id).subscribe((tariff:Tariff) => {
        this.pricePerHourNight = tariff.nightTariff;
        this.reservationPrice = (this.pricePerHourNight * this.hoursDiff) - (this.pricePerHourNight * this.seasonPercent / 100);
        console.log(this.reservationPrice);
      });
    }
    else {
      let month = this.selectedDate?.substring(5, 7);
      if(this.isSummer(month))
        this.seasonPercent = 10;
      else
        this.seasonPercent = 5;

        this.reservationPrice = (this.pricePerHourDay * this.hoursDiff) - (this.pricePerHourDay * this.seasonPercent / 100);
        console.log(this.reservationPrice);
    }
  }

  isSummer(month: string | undefined){
    if(month === "04" || month === "05" || month === "06" || month === "07")
      return true;
    return false;
  }
}
