import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms'
import { Router } from '@angular/router';
import { AppService } from '../../app-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  badLogin = false;

  constructor(private builder:FormBuilder, private service:AppService, private router:Router){}

  loginform = this.builder.group({
    email: this.builder.control('', Validators.required),
    password: this.builder.control('', Validators.required),
  });

  proceedlogin(){
    if(this.loginform.valid){
      this.service.login(this.loginform.value).subscribe(
        (response: any) => {
          console.log(response)
          if(response.id != null){
            sessionStorage.setItem('user', JSON.stringify(response))
            console.log('redirect')        
            if(response.roleId == '2')
              this.router.navigate(['court-management'])
            else
              this.router.navigate([''])
          }
          else {
            this.badLogin = true;
          }
        }
      );
    }
    else {
      console.log('data is not valid');
    }
  }

  forgotPass(){
    this.router.navigate(['resetPassword'])
  }
}
