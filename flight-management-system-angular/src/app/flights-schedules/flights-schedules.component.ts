import { Component, OnInit } from '@angular/core';
import { FlightService } from 'src/app/services/Flight/flight.service';
import { Router } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

import { UserService } from 'src/app/services/User/user.service';
@Component({
  selector: 'app-flights-schedules',
  templateUrl: './flights-schedules.component.html',
  styleUrls: ['./flights-schedules.component.css']
})
export class FlightsSchedulesComponent implements OnInit {
  flights: any[];
  nextFlights: any[];
  user: any = [];

  constructor(private flightService: FlightService,
     private router: Router,private cd: ChangeDetectorRef,private toastr: ToastrService,
     private userService: UserService, ) {
    this.nextFlights = [];
    this.flights = [];
  }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe((user) => {
      this.user = user;
      if(this.user.length === 0 || this.user[0].name === "undefined"){
        this.handleLogout();
      }
    });
    this.flightService.getFlights({}).subscribe(
      (result: any) => {
        this.flights = result;

      },
      (error) => {
        console.log('Error Occured: ', error.error.msg);
      }
    );
  }

  cancelFlight(flight: any) {
    console.log(flight);
    this.flightService.cancelFlight(flight).subscribe(
      (result1: any) => {
        this.toastr.success('Flight Cancelled Successfully', '');
        this.flightService.getFlights({}).subscribe(
          (result: any) => {
            this.flights = result;
            this.cd.detectChanges();
          },
          (error) => {
            console.log('Error Occured: ', error.error.msg);
          }
        );

      },
      (error) => {
        console.log('Error Occured: ', error.error.msg);
      }
    );
  }

  handleLogout() {
    localStorage.clear();
    this.userService.logoutUser();
    alert("session Expired, Please login again");
    this.router.navigate(['/login-page']);
  }

  viewBookings(selectedFlight:any) {
      this.flightService.viewBookingFlight = selectedFlight;
      this.router.navigate(['/view-bookings']);
    
  }

}

