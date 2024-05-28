import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { API_PATH, TOKEN_PREFIX } from 'src/app/constants/IMPData';

@Injectable({
  providedIn: 'root',
})
export class FlightService {
  flights: any[];
  nextFlights: any[];
  selectedFlight: any[];
  bookedFlight: any[];
  flightHistory: any[];
  selectedPnr: any;
  hideFooter: any ;
  viewBookingFlight:any;
  constructor(private http: HttpClient) {
    this.flights = [];
    this.nextFlights = [];
    this.selectedFlight = [];
    this.bookedFlight = [];
    this.flightHistory = [];
    this.selectedPnr = "";
    this.hideFooter = false;
    this.viewBookingFlight= {};
    }

  getFetchedFlights() {
    return of(this.flights);
  }

  getAfterDepartureDateFlights() {
    return of(this.nextFlights);
  }

  getFlights(filterObject: any) {
    const jwt_token = localStorage.getItem('token');

    return this.http.post(
      `${API_PATH}/getFlights`,
      {
        ...filterObject,
      },
      {
        headers: {
          authorization: `${jwt_token}`,
        },
      }
    );
  }

  getSeats(filterObject: any) {
    const jwt_token = localStorage.getItem('token');

    return this.http.post(
      `${API_PATH}/getSeats`,
      {
        ...filterObject,
      },
      {
        headers: {
          authorization: `${jwt_token}`,
        },
      }
    );
  }

  addNewFligt(flightData: any) {
    const jwt_token = localStorage.getItem('token');

    return this.http.post(
      `${API_PATH}/addFlight`,
      { ...flightData },
      {
        headers: {
          authorization: `${TOKEN_PREFIX} ${jwt_token}`,
        },
      }
    );
  }

  // Selected Flights
  setSelectedFlight(flightData: any) {
    this.selectedFlight.splice(0, 1);
    return this.selectedFlight.push(flightData);
  }

  getSelectedFlight() {
    return of(this.selectedFlight);
  }

  // Book Flight
  bookNewFlight(paxData: any) {
    const jwt_token = localStorage.getItem('token');

    return this.http.post(
      `${API_PATH}/booking`,
      paxData
      ,
      {
        headers: {
          authorization: `${TOKEN_PREFIX} ${jwt_token}`,
        },
      }
    );
  }

  getBookedFlightData() {
    return of(this.bookedFlight);
  }

  // Flight Booking
  getFlightBookingHistory(userId:any) {
    const jwt_token = localStorage.getItem('token');

    return this.http.get(
      `${API_PATH}/getBookings/`+userId,
      {
        headers: {
          authorization: `${TOKEN_PREFIX} ${jwt_token}`,
        },
      }
    );
  }

    cancelBooking(pnr:any) {
      const jwt_token = localStorage.getItem('token');
  
      return this.http.put(
        `${API_PATH}/cancelBooking/pnr/`+pnr,
        {
          headers: {
            authorization: `${TOKEN_PREFIX} ${jwt_token}`,
          },
        }
      );
  }

  getPnrDetails(pnr:string) {
    const jwt_token = localStorage.getItem('token');

    return this.http.get(
      `${API_PATH}/getBookings/pnr/`+pnr,
      {
        headers: {
          authorization: `${TOKEN_PREFIX} ${jwt_token}`,
        },
      }
    );
  }

  cancelFlight(flightData: any) {
    const jwt_token = localStorage.getItem('token');

    return this.http.post(
      `${API_PATH}/cancelFlight`,
      { ...flightData },
      {
        headers: {
          authorization: `${TOKEN_PREFIX} ${jwt_token}`,
        },
      }
    );
  }

  viewBookings(flightData: any) {
    const jwt_token = localStorage.getItem('token');

    return this.http.post(
      `${API_PATH}/viewBookings`,
      { ...flightData },
      {
        headers: {
          authorization: `${TOKEN_PREFIX} ${jwt_token}`,
        },
      }
    );
  }
}
