import { Component, OnInit } from '@angular/core';
import { Address } from 'src/app/model/address';
import { CourtService } from 'src/app/service/court-service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Court } from 'src/app/model/court';
import { CourtCreateDto } from 'src/app/model/court-create-dto';
import { Router } from '@angular/router';
import { Tariff } from 'src/app/model/tariff';
import { TariffService } from 'src/app/service/tariff-service';


@Component({
  selector: 'app-court-management',
  templateUrl: './court-management.component.html',
  styleUrls: ['./court-management.component.scss']
})
export class CourtManagementComponent implements OnInit {
  
  selectedAddress: any;
  selectedCourtId: any;

  addresses: Address[] = [];
  courts: Court[] = [];
  filteredCourts: Court[] = [];

  courtForm = new FormGroup ({
    id: new FormControl(''),
    number: new FormControl(''),
    surface: new FormControl(''),
  });

  tariffForm = new FormGroup({
    id: new FormControl(''),
    nightTariff: new FormControl('')
  });

  updateCourtTariff: CourtCreateDto = {}

  updateMode = false;

  constructor(private courtService: CourtService, private tariffService: TariffService, private router: Router) { }

  ngOnInit(): void {  
    var user = sessionStorage.getItem('user');

    if(!user || JSON.parse(user as string).roleId != '2'){
      this.router.navigate(['login'])
    }
    
    this.courtService.getAllAddresses().subscribe(addresses => this.addresses = addresses);
    this.getAllCourts();
  }

  getAllCourts() {
    this.courtService.getAll().subscribe(courts => this.courts = courts);
  }

  getCourtsForSelectedAddress() {
    this.filteredCourts = this.courts.filter((court) => court.address.id == this.selectedAddress.id);
  }

  createCourt() {
    let court: CourtCreateDto = {
      number: this.courtForm.value.number as string,
      surface: this.courtForm.value.surface as string,
      address: this.selectedAddress,
      status: "available"
    }
    
    this.courtService.create(court).subscribe();
    this.courtForm.reset();
  }

  updateCourt() {
    let court: CourtCreateDto = {
      id: this.courtForm.value.id as string,
      number: this.courtForm.value.number as string,
      surface: this.courtForm.value.surface as string,
    }
    
    this.courtService.update(court).subscribe();
    this.courtForm.reset();
  }

  deleteCourt(id: any) {
    this.courtService.delete(id).subscribe();
    window.location.reload();
  }

  async submitForm() {
    if(this.updateMode) {
      this.updateCourt();
    }
    else {
      this.createCourt();
    }

    this.getAllCourts();
    window.location.reload();
  }

  changeToUpdateMode(court: Court) {
    this.updateMode = true;
    
    this.courtForm.get('id')?.setValue(court.id);
    this.courtForm.get('number')?.setValue(court.number);
    this.courtForm.get('surface')?.setValue(court.surface);
  }

  setSelectedCourtId(court: Court){
    this.selectedCourtId = court.id;
    console.log(this.selectedCourtId);
  }

  display() {
    console.log(this.selectedAddress);
  }

  changeTariff(){
    let newFormTariff: Tariff = {
      id: this.tariffForm.value.id as string,
      nightTariff: this.tariffForm.value.nightTariff as string

    }
    console.log(newFormTariff);
    this.courtService.getTariffByCourtId(this.selectedCourtId).subscribe((tariff: Tariff) => {
      // console.log(tariff);
      if(tariff === null){
        console.log(false);
        this.tariffService.createTariff(newFormTariff).subscribe((createdTariff: Tariff) => {
          this.updateCourtTariff.id = this.selectedCourtId;
          this.updateCourtTariff.tariff = createdTariff;
          this.courtService.update(this.updateCourtTariff).subscribe();
        });
      }
      else{
        console.log(newFormTariff.nightTariff);
        newFormTariff.id = tariff.id
        console.log(newFormTariff);
        this.tariffService.updateTariff(newFormTariff).subscribe();
      }
    });

    window.location.reload();
  }
}
