import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { FlightService } from 'src/app/services/Flight/flight.service';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css'],
})
export class InvoiceComponent implements OnInit {
  bookingData: any[];
  totalCost:  any = 0;

  constructor(
    private toastr: ToastrService,
    private flightService: FlightService,
    private router: Router
  ) {
    this.bookingData = [];
  }

  ngOnInit(): void {
    this.flightService.getPnrDetails(this.flightService.selectedPnr).subscribe((result:any) => {
      if(result) {
        this.bookingData = result;
        this.bookingData.forEach(obj => {
          this.totalCost = this.totalCost + obj.fare;
        });
      }
    });
    if (this.bookingData === undefined) {
      this.router.navigate(['/flight-booking']);
    }
  }

  print() {
    window.print();
  }
}
