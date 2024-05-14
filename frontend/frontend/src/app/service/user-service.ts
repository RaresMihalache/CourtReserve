import { HttpClient } from '@angular/common/http'
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(
        private http: HttpClient
    ){}

    public getById(id: string): Observable<User> {
        return this.http.get<User>("http://localhost:8090/user/" + id);
    }
}