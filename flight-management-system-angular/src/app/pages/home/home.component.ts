import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/User/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styles: [
    `
      .All {
        width: 100%;
        display: flex;
        flex-direction: row;
        justify-content: center;
      }

      .top-left {
        position: absolute;
        top: 8px;
        left: 16px;
        margin-top: 60px;
        margin-left: 40px;
        color: white;
        text-shadow: 2px 2px 4px #000000;
        font-size: 60px;
        font-style: italic;
        font-weight: bold;
        font-family: 'Georgia', Times, serif;
      }

      .bottom-right {
        position: absolute;
        bottom: 8px;
        right: 16px;
        margin-bottom: 50px;
        margin-right: 40px;
        color: white;
        text-shadow: 2px 2px 4px #000000;
        font-size: 40px;
        font-style: italic;
        font-weight: bold;
        font-family: 'cursive';
      }

      .container {
        position: relative;
        margin-left: 40px;
        width: 80%;
      }
      .image1 {
        display: block;
        width: 80%;
        /*height: auto;*/
      }
      .overlay {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        background-color: #2874a6;
        overflow: hidden;
        width: 80%;
        height: 0;
        transition: 0.5s ease;
      }
      .container:hover .overlay {
        height: 100%;
      }
      .text {
        white-space: nowrap;
        color: white;
        font-size: 20px;
        font-family: 'Georgia', Times, serif;
        position: absolute;
        overflow: hidden;
        top: 50%;
        left: 54%;
        transform: translate(-50%, -50%);
        -ms-transform: translate(-50%, -50%);
      }
      /*---------------------------------------------*/
      .container2 {
        position: relative;
        width: 80%;
      }

      .image2 {
        display: block;
        width: 80%;
        height: auto;
      }

      .overlay2 {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        background-color: #2874a6;
        overflow: hidden;
        width: 0;
        height: 100%;
        transition: 0.5s ease;
      }

      .container2:hover .overlay2 {
        width: 80%;
      }
      .carousel-item img {
        height: 60vh;
        object-fit: cover;
      }
    `,
  ],
})
export class HomeComponent implements OnInit {
  carouselImages = [
    '../../assets/flightImages/aeroplane_img.jpg',
    'https://images.unsplash.com/photo-1606768666853-403c90a981ad?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8ZmxpZ2h0fGVufDB8fDB8fA%3D%3D&w=1000&q=80',
    'https://images.moneycontrol.com/static-mcnews/2021/09/Air-India.jpg?impolicy=website&width=770&height=431',
  ];
  user: any = [];
  constructor(
    private router: Router,private userService: UserService) { }

  ngOnInit(): void { 
    this.userService.getCurrentUser().subscribe((user) => {
      this.user = user;
      if(this.user.length >0 && this.user[0].name === "undefined"){
        this.handleLogout();
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
