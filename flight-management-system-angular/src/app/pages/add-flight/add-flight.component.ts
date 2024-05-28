import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ADMIN_ROLE } from 'src/app/constants/IMPData';
import { FlightService } from 'src/app/services/Flight/flight.service';
import { UserService } from 'src/app/services/User/user.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-add-flight',
  templateUrl: './add-flight.component.html',
  styleUrls: ['./add-flight.component.css'],
})
export class AddFlightComponent implements OnInit {
  user: any[];

  flightNo = '';
  origin = '';
  destination = '';
  duration = '';

  depDate = '';
  depTime = '';
  arrDate = '';
  arrTime = '';
  minDate : string;

  displayModal = false;

  constructor(
    private userService: UserService,
    private flightService: FlightService,
    private toastr: ToastrService,
    private router: Router,
    public datepipe: DatePipe
  ) {
    this.user = [];
    this.minDate = new Date(Date.now() + (3600*1000*24)).toISOString().slice(0, 10);
  }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe((user) => {
      this.user = user;
      if(this.user.length === 0 || this.user[0].name === "undefined"){
        this.handleLogout();
      }
    });
    this.userService
      .getCurrentUser()
      .subscribe((userData) => (this.user = userData));

    if (this.user.length > 0) {
      if (this.user[0].role !== "admin") {
        this.router.navigate(['/']);
        return;
      }
    }
  }

  handleFormSubmit(event: Event) {
    event.preventDefault();

    if (this.origin === this.destination) {
      this.toastr.error('Error', 'Origin and destination cannot be same');
      return console.log('Origin and destination cannot be same');
    } 
    else if (this.depDate > this.arrDate) {
      this.toastr.error('Error', 'Arrival Date should be greated than Departure Date');
      return console.log('Arrival Date should be greated than Departure Date');
    }
    else if (this.depDate === this.arrDate && this.arrTime < this.depTime) {
      this.toastr.error('Error', 'Arrival Time should be greated than Departure Time');
      return console.log('Arrival Time should be greated than Departure Time');
    }

    var newFlightObject: any = {
      flightId:7,
      flightNo: this.flightNo,
      origin: this.origin,
      destination: this.destination,
      duration: this.duration,
      depDate: this.depDate,
      depTime: this.depTime,
      arrDate: this.arrDate,
      arrTime: this.arrTime,
    };

  

    console.log('New Flight Data: ', newFlightObject);

    this.displayModal = true;

    this.flightService.addNewFligt(newFlightObject).subscribe(
      (result: any) => {
        console.log(result);
        if (result) {
          console.log('Flight Added Successfully');

          this.flightNo = '';
          this.origin = '';
          this.destination = '';
          this.duration = '';

          this.depDate = '';
          this.depTime = '';
          this.arrDate = '';
          this.arrTime = '';

          this.toastr.success('Flight Added Successfully');
        } else {
          console.log('Error', result.err);
          this.toastr.error('Error', result.err);
        }

        this.displayModal = false;
      },
      (error) => {
        console.log('Error Occured: ', error.error.msg);
        this.toastr.error("FlightNumber Already exists for the Departure Date,Please give new Flight Number");
        this.displayModal = false;
      }
    );
  }

  handleLogout() {
    localStorage.clear();
    this.userService.logoutUser();
    alert("session Expired, Please login again");
    this.router.navigate(['/login-page']);
  }
}
