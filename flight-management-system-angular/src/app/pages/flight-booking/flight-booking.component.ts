import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FlightService } from 'src/app/services/Flight/flight.service';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/User/user.service';

@Component({
  selector: 'app-flight-booking',
  templateUrl: './flight-booking.component.html',
  styleUrls: ['./flight-booking.component.css'],
})
export class FlightBookingComponent implements OnInit {
  tripType: string;

  origin: string;
  destination: string;

  depDate: string;
  returnDate: string;

  adultCount: number;
  childrenCount: number;

  flightClass: string;

  displayModal = false;
  user: any[];
  minDate : string;

  constructor(
    private router: Router,
    private flightService: FlightService,
    private toastr: ToastrService,
    private userService: UserService, 
  ) {
    this.tripType = '';
    this.origin = '';
    this.destination = '';
    this.depDate = '';
    this.returnDate = '';
    this.adultCount = 0;
    this.childrenCount = 0;
    this.flightClass = '';
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
  }

  handleFormSubmit(event: Event) {
    event.preventDefault();

    if (this.origin === this.destination) {
      this.toastr.error('Error', 'Origin and source cannot be the same');
      return console.log('Origin and source cannot be the same');
    }

    const newFlightBookingObject: any = {
      tripType: this.tripType,
      routeSource: this.origin,
      routeDestination: this.destination,
      departureDate: this.depDate,
      returnDate: this.returnDate,
      adultCount: this.adultCount,
      childrenCount: this.childrenCount,
    };

    const newFlightBookingFilterObject: any = {
      origin: this.origin,
      destination: this.destination,
      depDate: this.depDate,
    };

    

    console.log('Flight Object: ', newFlightBookingObject);
    console.log('Flight Fitler Object: ', newFlightBookingFilterObject);

    this.displayModal = true;

    this.flightService.getFlights(newFlightBookingFilterObject).subscribe(
      (result: any) => {
        console.log('Fetched: ', result);
        this.flightService.flights.splice(0, this.flightService.flights.length);
        this.flightService.nextFlights.splice(
          0,
          this.flightService.nextFlights.length
        );

        this.flightService.flights.push(...result);
        this.flightService.nextFlights.push(
          ...result
        );

        this.displayModal = false;
        this.router.navigate(['/flights']);
      },
      (error) => {
        this.toastr.error('Error');
        console.log('Error Occured: ', error.error.msg);
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
