import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FlightService } from 'src/app/services/Flight/flight.service';
import { UserService } from 'src/app/services/User/user.service';

@Component({
  selector: 'app-flights',
  templateUrl: './flights.component.html',
  styleUrls: ['./flights.component.css'],
})
export class FlightsComponent implements OnInit {
  flights: any[];
  nextFlights: any[];
  user: any = [];
  constructor(private flightService: FlightService, private router: Router,
    private userService: UserService) {
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
    this.flightService
      .getFetchedFlights()
      .subscribe((flightsData) => (this.flights = flightsData));

    this.flightService
      .getAfterDepartureDateFlights()
      .subscribe((nextFlightData) => (this.nextFlights = nextFlightData));

    if (this.flights.length > 0) {
      this.nextFlights = this.nextFlights.filter(
        (item) => item.departureDate !== this.flights[0].departureDate
      );
    }
  }

  displayFlight(flight: any) {
    console.log(flight);
  }

  showDetails(flight: any) {
    console.log('Show Details: ', flight);
    this.flightService.setSelectedFlight(flight);
    this.router.navigate(['/flight-details']);
  }

  handleLogout() {
    localStorage.clear();
    this.userService.logoutUser();
    alert("session Expired, Please login again");
    this.router.navigate(['/login-page']);
  }
}
