import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/User/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-flight-cancel',
  templateUrl: './flight-cancel.component.html',
  styleUrls: ['./flight-cancel.component.css']
})
export class FlightCancelComponent implements OnInit {

  user: any = [];
  constructor(
    private router: Router,private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe((user) => {
      this.user = user;
      if(this.user.length === 0 || this.user[0].name === "undefined"){
        this.handleLogout();
      }
    });
  }

  handleLogout() {
    localStorage.clear();
    this.userService.logoutUser();
    alert("session Expired, Please login again");
    this.router.navigate(['/login-page']);
  }
}
