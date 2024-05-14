import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AppService } from '../../app-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent {

  constructor(private builder:FormBuilder, private service:AppService, private router:Router){

  }

  registerform = this.builder.group({
    name:this.builder.control('', Validators.required),
    email:this.builder.control('', Validators.compose([Validators.required, Validators.email])),
    password: this.builder.control('', Validators.compose([Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}')])),
    phoneNumber:this.builder.control('', Validators.compose([Validators.required, Validators.pattern('07[0-9]{8}')])),
    role:this.builder.control('')
  })

  getTransformedObject(): any{
    const roleID = parseInt(this.registerform.value.role ?? '0');
    return {
      ...this.registerform.value,
      role: {id: roleID}
    };
  }

  proceedregistration() {
  if (this.registerform.valid){
    // console.log(this.registerform.value)
    const transformedObject = this.getTransformedObject();
    console.log(transformedObject);
    this.service.register(transformedObject).subscribe(
      (response: any) => {
        console.log(response);
        this.router.navigate(['login'])
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
