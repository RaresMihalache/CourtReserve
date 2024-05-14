import { HttpClient } from '@angular/common/http'
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { Tariff } from '../model/tariff';

@Injectable({
    providedIn: 'root'
})
export class TariffService {

    URL: string = 'http://localhost:8090/tariff';

    constructor (private http: HttpClient){}

    public createTariff(tariff: Tariff): Observable<Tariff> {
        return this.http.post<Tariff>('http://localhost:8090/tariff/save',  tariff);
    }

    public updateTariff(tariff: Tariff): Observable<void>{
        return this.http.put<void>('http://localhost:8090/tariff/update',  tariff);
    }
}
