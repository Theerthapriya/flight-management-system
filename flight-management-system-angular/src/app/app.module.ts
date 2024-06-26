import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';

// Components
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { ReportComponent } from './pages/report/report.component';
import { SignupComponent } from './pages/signup/signup.component';
import { FlightHistoryComponent } from './pages/flight-history/flight-history.component';
import { AccountSettingsComponent } from './pages/account-settings/account-settings.component';
import { HeaderComponent } from './Components/header/header.component';
import { FooterComponent } from './Components/footer/footer.component';
import { FlightBookingComponent } from './pages/flight-booking/flight-booking.component';
import { InvoiceComponent } from './pages/invoice/invoice.component';
import { FlightsComponent } from './pages/flights/flights.component';
import { FlightDetailsComponent } from './pages/flight-details/flight-details.component';
import { AddFlightComponent } from './pages/add-flight/add-flight.component';
import { FlightTicketsComponent } from './pages/flight-tickets/flight-tickets.component';
import { AboutUsComponent } from './pages/about-us/about-us.component';
import { ContactUsComponent } from './pages/contact-us/contact-us.component';
import { DisplayIssuesComponent } from './pages/display-issues/display-issues.component';
import { DatePipe } from '@angular/common';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DialogComponent } from './dialog/dialog.component';
import {MatDialogModule } from '@angular/material/dialog';
import { FlightCancelComponent } from './flight-cancel/flight-cancel.component';
import { FlightsSchedulesComponent } from './flights-schedules/flights-schedules.component';
import { ViewBookingsComponent } from './view-bookings/view-bookings.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SignupComponent,
    LoginPageComponent,
    ReportComponent,
    FlightHistoryComponent,
    AccountSettingsComponent,
    HeaderComponent,
    FooterComponent,
    FlightBookingComponent,
    InvoiceComponent,
    FlightsComponent,
    FlightDetailsComponent,
    AddFlightComponent,
    FlightTicketsComponent,
    AboutUsComponent,
    ContactUsComponent,
    DisplayIssuesComponent,
    DashboardComponent,
    DialogComponent,
    FlightCancelComponent,
    FlightsSchedulesComponent,
    ViewBookingsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgbModule,
    MatDialogModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent],
})
export class AppModule {}
