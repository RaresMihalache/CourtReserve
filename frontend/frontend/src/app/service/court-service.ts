import { HttpClient } from '@angular/common/http'
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { Court } from '../model/court';
import { CourtCreateDto } from '../model/court-create-dto';
import { Address } from '../model/address';
import { Tariff } from '../model/tariff';

@Injectable({
    providedIn: 'root'
})
export class CourtService {

    constructor(
        private http: HttpClient
    ){}

    public getAllAddresses(): Observable<Address[]> {
        return this.http.get<Address[]>('http://localhost:8090/addresses');
    }

    public getAll(): Observable<Court[]> {
        return this.http.get<Court[]>('http://localhost:8090/court/findAll');
    }

    public create(court: CourtCreateDto): Observable<Court> {
        return this.http.post<Court>('http://localhost:8090/court/save', court);
    }

    public update(court: CourtCreateDto): Observable<void> {
        return this.http.put<void>('http://localhost:8090/court/update', court);
    }

    public delete(id: string): Observable<void> {
        return this.http.delete<void>('http://localhost:8090/court/delete/' + id);
    }

    public getTariffByCourtId(id: string): Observable<Tariff> {
        return this.http.get<Tariff>('http://localhost:8090/court/getCourtTariff/' + id);
    }
}