import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from './service/user-service';
import { User } from './model/user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'court-reserve-ui';
  userRole: string = '';
  isLoggedIn = false;
  userId: string = '';
  userName: any;

  constructor(private userService: UserService, private router: Router){}

  ngOnInit() {
    this.setUserRoleAndId();
  }

  private setUserRoleAndId() {
    const userSessionString = sessionStorage.getItem('user');
    const userSession = userSessionString ? JSON.parse(userSessionString) : null;
    
    if (userSession && userSession.roleId === 1) {
      this.userRole = 'Client';
      this.userId = userSession["id"];
      console.log(this.userId);
      this.isLoggedIn = true;
    } else if (userSession && userSession.roleId === 2) {
      this.userRole = 'Admin';
      this.userId = userSession["id"];
      this.isLoggedIn = true;
    } else {
      this.userRole = 'Unauthenticated';
      this.isLoggedIn = false;
    }

    this.getUserName(this.userId);

  }

  getUserName(id: string){
    if(this.userId){
      console.log(true);
      this.userService.getById(id).subscribe((user:User) => {
        console.log(user);
        this.userName = user.name;
      });
    }
    else
      console.log(false);
    return 'wtf'
  }

  logout() {
    // Clear the session storage
    sessionStorage.removeItem('user');
    
    // Redirect to the desired page after logout
    this.router.navigate(['/']); // Replace 'logout' with the desired route
  }
}
