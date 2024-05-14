import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms'
import { Router } from '@angular/router';
import { AppService } from '../../app-service.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent{

  constructor(private builder:FormBuilder, private service:AppService, private router:Router) { }

  resetpassform = this.builder.group({
    email: this.builder.control('', Validators.required),
    password: this.builder.control('', Validators.required),
  });

  proceedResetPassword(){
    if(this.resetpassform.valid){
      this.service.resetPassword(this.resetpassform.value).subscribe(
        (response:any) => {
          console.log(response)
        },
        (error) => {
          console.log(error);
        }
      );
    }
    else{
      console.log('data is not valid');
    }
  }

}
