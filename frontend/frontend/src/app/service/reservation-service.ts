import { HttpClient } from '@angular/common/http'
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { Court } from '../model/court';
import { PartnerRequestsResponse } from '../model/partner-requests-response';
import { ReservationRequest } from '../model/reservation';
import { AddUserRequest } from '../model/add-user-request';
import { Reservation } from '../model/reservation-obj';
import { CancelReservation } from '../model/cancel-reservation';

@Injectable({
    providedIn: 'root'
})
export class ReservationService {

    URL: string = 'http://localhost:8090/reservation';

    constructor(
        private http: HttpClient
    ){}

    get(id: string): Observable<ReservationRequest> {
        return this.http.get<ReservationRequest>(this.URL + '/get/' + id);
    }

    getAvailableCourts(date: any, start: any, end: any): Observable<Court[]> {
        return this.http.get<Court[]>(this.URL+ '/findAllAvailableCourts/' + date + '/' + start + ":01" + '/' + end + ':01');
    }

    getAllReservations(): Observable<ReservationRequest[]>{
        return this.http.get<ReservationRequest[]>(this.URL + '/findAll');
    }

    createReservation(reservation: ReservationRequest): Observable<ReservationRequest> {
        console.log(reservation);
        return this.http.post<ReservationRequest>(this.URL + '/save/', reservation);
    }

    getPartnerRequests(): Observable<PartnerRequestsResponse[]> {
        return this.http.get<PartnerRequestsResponse[]>(this.URL + '/getReservationsByStatus/1');
    }

    addUserToReservation(request: AddUserRequest): Observable<ReservationRequest> {
        return this.http.put<ReservationRequest>(this.URL + '/updateUserList', request);
    }

    getReservationsByStatus(status:string): Observable<Reservation[]> {
        return this.http.get<Reservation[]>(this.URL + '/getReservationsByStatus/' + status);
    }

    getReservationById(id:string): Observable<Reservation>{
        return this.http.get<Reservation>(this.URL + '/get/' + id);
    }

    deleteReservationById(id: string): Observable<CancelReservation> {
        return this.http.delete<CancelReservation>(this.URL + '/cancel/' + id);
    }

    findPlayers(id: string): Observable<Reservation> {
        return this.http.put<Reservation>(this.URL + '/findPlayers/' + id, null);
    }
}