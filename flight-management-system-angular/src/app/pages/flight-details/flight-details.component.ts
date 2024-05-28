import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FlightService } from 'src/app/services/Flight/flight.service';
import { FormGroup, FormArray, FormBuilder, Validators } from "@angular/forms";
import { DialogComponent } from "../../dialog/dialog.component"
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/User/user.service';

@Component({
  selector: 'app-flight-details',
  templateUrl: './flight-details.component.html',
  styleUrls: ['./flight-details.component.css'],
})
export class FlightDetailsComponent implements OnInit {
  user: any = [];
  paxList: any = [];
  guestObj: any = {
    "firstName": "",
    "lastName": "",
    "age": "",
    "seatNo": "",
    "fare": ""
  }

  selectedFlight: any[];
  totalSeats = 0;
  remainingSeats = 0;
  isBookingDisabled = false;

  constructor(
    private flightService: FlightService,
    private router: Router,
    private datePipe: DatePipe,
    private dialog: MatDialog,
    private toastr: ToastrService,
    private userService: UserService
  ) {
    this.selectedFlight = [];
  }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe((user) => {
      this.user = user;
      if (this.user.length === 0 || this.user[0].name === "undefined") {
        this.handleLogout();
      }
    });
    this.flightService.getSelectedFlight().subscribe((flightData) => {
      this.selectedFlight = flightData;
      const obj = JSON.stringify(this.guestObj);
      const parserobj = JSON.parse(obj);
      this.paxList.unshift(parserobj);
    });


    if (this.selectedFlight.length === 0) {
      this.router.navigate(['/flight-booking']);
      return;
    }

    const currentDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');

    if (currentDate !== null) {
      if (this.selectedFlight[0].departureDate < currentDate) {
        this.isBookingDisabled = true;
      }
    }
  }


  addPax() {
    if (this.paxList.length === this.selectedFlight[0].available) {
      this.toastr.success('Maximum passengers added.Please try another booking');
    } else {
      const obj = JSON.stringify(this.guestObj);
      const parserobj = JSON.parse(obj);
      this.paxList.push(parserobj);
    }

  }

  removeGuest(index: number) {
    if (this.paxList.length == 1) {
      this.toastr.success('Minimum One Passenger is required');
    } else {
      this.paxList.splice(index, 1)
    }
  }

  onSaveBooking(event: Event) {
    event.preventDefault();
    var proceedBooking: any = true;

    var loopBreak: any = false;
    this.paxList.forEach((pax: any, index: any) => {
      if (!loopBreak) {
        if (!pax.firstName) {
          this.toastr.error('Passenger ' + ( index+1 ) + ': First Name Required');
          proceedBooking = false;
          loopBreak = true;
        }
        else if (pax.firstName.trim().length < 3) {
          this.toastr.error('Passenger ' + ( index+1 )+ ': First Name should be atleast 3 characters');
          proceedBooking = false;
          loopBreak = true;
        }
        else if (!pax.lastName) {
          this.toastr.error('Passenger ' + ( index+1 ) + ': Last Name Required');
          proceedBooking = false;
          loopBreak = true;
        }
        else if (pax.lastName.trim().length < 3) {
          this.toastr.error('Passenger ' + ( index+1 ) + ': Last Name should be atleast 3 characters');
          proceedBooking = false;
          loopBreak = true;
        }
        else if (!pax.seat) {
          this.toastr.error('Passenger ' + ( index+1 ) + ': seat selection reqired');
          proceedBooking = false;
          loopBreak = true;
        }
      }
    });

    if (proceedBooking) {
      const data: any = [];
      this.paxList.forEach((pax: any) => {
        var paxInfo: any = {
          flightNo: this.selectedFlight[0].flightNo,
          paxName: pax.firstName + " " + pax.lastName,
          age: pax.age,
          seatNo: pax.seat,
          flightDate: this.selectedFlight[0].depDate,
          userId: localStorage["userId"],
          fare: pax.fare
        };
        data.push(paxInfo);
      });

      console.log('paxInfo ', data);

      this.flightService.bookNewFlight(data).subscribe(
        (result: any) => {
          console.log(result);
          alert("Booking Successfull");
          this.router.navigate(['/']);
        },
        (error: any) => {
          this.toastr.error("Booking Failed ,Please try after sometime");
          console.log('Error Occured: ', error.error.msg);
        }
      );
    }
  }

  selectSeat(index: number) {
    const dialogRef = this.dialog.open(DialogComponent, {
      data: {
        message: 'Are you sure want to delete?',
        buttonText: {
          ok: 'Save',
          cancel: 'No'
        },
        flightData: this.selectedFlight[0],
        currentSeat: this.paxList[index].seat,
        paxList: this.paxList
      }
    });

    dialogRef.afterClosed().subscribe((data: any) => {
      if (data) {
        this.paxList[index].seat = data.seatId;
        this.paxList[index].fare = data.fare;
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