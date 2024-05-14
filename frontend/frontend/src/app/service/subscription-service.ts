import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Subscription } from "../model/subscription";
import { SubscriptionResponse } from "../model/subscription-response";

@Injectable({
    providedIn: 'root'
})
export class SubscriptionService {

    URL: string = 'http://localhost:8090/subscription';

    constructor(
        private http: HttpClient
    ){}

    public createSubscription(): Observable<Subscription> {
        return this.http.post<Subscription>(this.URL + '/save', {});
    }

    public computePrice(id: string): Observable<Number> {
        return this.http.get<Number>(this.URL + '/computeTotalPrice/' + id);
    }

    public getSubscriptionsByUserId(id: string): Observable<SubscriptionResponse[]> {
        return this.http.get<SubscriptionResponse[]>(this.URL + '/getSubscriptions/' + id);
    }
}