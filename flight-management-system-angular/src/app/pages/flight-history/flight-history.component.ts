import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { FlightService } from 'src/app/services/Flight/flight.service';
import { UserService } from 'src/app/services/User/user.service';

@Component({
  selector: 'app-flight-history',
  templateUrl: './flight-history.component.html',
  styleUrls: ['./flight-history.component.css'],
})
export class FlightHistoryComponent implements OnInit {
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
      if(this.user.length === 0 || this.user[0].name === "undefined"){
        this.handleLogout();
      }
    });
    this.displayModal = true;

    this.getBookings();
  }

  getBookings() {
    this.flightService.getFlightBookingHistory(localStorage["userId"]).subscribe(
      (result: any) => {
        console.log(result);
        if (result) {
          console.log('History Fetched Successfully');

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

  cancelBooking(booking: any) {
    this.flightService.cancelBooking(booking.pnr).subscribe(
      (result: any) => {
        console.log(result);
        if (result) {
          console.log('Booking Cancelled Successfully');

          this.toastr.success('Booking Cancelled Successfully');
          this.getBookings();
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

  handleLogout() {
    localStorage.clear();
    this.userService.logoutUser();
    alert("session Expired, Please login again");
    this.router.navigate(['/login-page']);
  }
}
