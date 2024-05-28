import { Component, OnInit, Inject } from '@angular/core';
import {  MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FlightService } from 'src/app/services/Flight/flight.service';

import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {
  seats:any = [];
  message: string = "";
  cancelButtonText = "Cancel";
  flightData : any = {};
  paxList : any = [];
  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
    private dialogRef: MatDialogRef<DialogComponent>,private flightService: FlightService,private cd: ChangeDetectorRef) {
    if (data) {
      this.message = data.message || this.message;
      if (data.buttonText) {
        this.cancelButtonText = data.buttonText.cancel || this.cancelButtonText;
      }
      this.flightData = data.flightData;
      this.paxList = data.paxList;
    }
    this.dialogRef.updateSize('400vw','400vw')
  }

  ngOnInit(): void {
    this.flightService.getSeats(this.flightData).subscribe(
      (result: any) => {
        this.seats = result;
        this.paxList.forEach ((pax: { seat: string; }) => {
          if( document.getElementById(pax.seat) && pax.seat !== this.data.currentSeat) {
            const element = document.getElementById(pax.seat);
            element?.setAttribute("disabled","disabled") ;
          }
        });
        result.forEach((seat: string) => {
          if( document.getElementById(seat)) {
            const element = document.getElementById(seat);
            element?.setAttribute("disabled","disabled") ;
            }
        });
        this.cd.detectChanges();
      },
      (error) => {
        console.log('Error Occured: ', error.error.msg);
      }
    );
  }
  onConfirmClick(): void {
    this.dialogRef.close(true);
  }


  fieldsChange(values:any):void {
    const seatInfo = {
      seatId : values.currentTarget.id,
      fare: values.currentTarget.defaultValue
    };
    this.dialogRef.close(seatInfo);
  }

}
