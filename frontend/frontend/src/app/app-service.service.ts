import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private http: HttpClient) { }

  rootURL = 'http://localhost:8090';

  register(user:any){
    return this.http.post(this.rootURL + '/register', user)
  }
  
  login(user:any){
    return this.http.post(this.rootURL + '/login', user)
  }

  resetPassword(user:any){
    return this.http.put(this.rootURL + '/login/resetPassword', user)
  }

}
