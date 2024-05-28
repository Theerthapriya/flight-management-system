import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { FlightService } from 'src/app/services/Flight/flight.service';
import { UserService } from 'src/app/services/User/user.service';

@Component({
  selector: 'app-view-bookings',
  templateUrl: './view-bookings.component.html',
  styleUrls: ['./view-bookings.component.css']
})
export class ViewBookingsComponent implements OnInit {
  flightHistory: any[];
  user: any = [];
  displayModal = false;
  constructor(
    private router: Router,
    private flightService: FlightService,
    private toastr: ToastrService,
    private userService: UserService
  ) {
    this.flightHistory = [];
  }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe((user) => {
      this.user = user;
      if (this.user.length === 0 || this.user[0].name === "undefined") {
        this.handleLogout();
      }
    });
    this.displayModal = true;

    this.flightService.viewBookings(this.flightService.viewBookingFlight).subscribe(
      (result: any) => {
        console.log(result);
        if (result) {
          console.log('Bookings Fetched Successfully');

          this.toastr.success('Bookings Fetched Successfully');

          this.flightHistory = result;
          this.flightService.flightHistory = result;
          this.displayModal = false;
        } else {
          console.log('Error', result.err.writeErrors[0].errmsg);
          this.toastr.error('Error', result.err.writeErrors[0].errmsg);
          this.displayModal = false;
        }
      },
      (error) => {
        console.log('Error Occured: ', error.error.msg);
        this.toastr.error('Error', error.error.msg);
        this.displayModal = false;
      }
    );
  }

  viewReceipt(booking: any) {
    this.flightService.selectedPnr = booking.pnr;
    this.router.navigate(['/flight-receipt']);
  }

  handleLogout() {
    localStorage.clear();
    this.userService.logoutUser();
    alert("session Expired, Please login again");
    this.router.navigate(['/login-page']);
  }
}
