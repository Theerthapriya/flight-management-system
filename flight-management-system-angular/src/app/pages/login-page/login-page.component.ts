import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/User/user.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
})
export class LoginPageComponent implements OnInit {
  user: any[];
  email: string;
  password: string;
  rememberMe: boolean;
  showPassword: boolean = false;

  displayModal = false;

  constructor(
    private userService: UserService,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.user = [];
    this.email = '';
    this.password = '';
    this.rememberMe = false;
  }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe((userData) => {
      this.user = userData;
    });

    if (this.user.length > 0) {
      this.router.navigate(['/']);
    }
  }

  handleFormSubmit(event: Event) {
    event.preventDefault();

    const newData = {
      email: this.email,
      password: this.password,
      rememberMe: this.rememberMe,
    };

    this.displayModal = true;
    this.userService.loginUser(newData).subscribe(
      (result: any) => {
        console.log(result);

        if (result.isError) {
          this.toastr.error('Error', result.msg);
          this.displayModal = false;
          return console.log(result.msg);
        }

        if (result.hasOwnProperty('isAuthorized')) {
          if (!result.isAuthorized) {
            this.toastr.error('Error', result.msg);
            this.displayModal = false;
            return console.log(result.msg);
          }
        }

        const newUser: any = {
          email: result.data.email,
          userId: result.data.userId,
          role: result.data.role,
          username: result.data.username,
          name: result.data.username,
          rememberMe: this.rememberMe,
        };

        this.toastr.success('Authorized User', 'Account LoggedIn Successfully');

        // Saving data to localstorage
        localStorage.setItem('token', result.data.usernametoken);
        localStorage.setItem('username', result.data.username);
        localStorage.setItem('userId', result.data.userId);
        localStorage.setItem('role', result.data.role);
        localStorage.setItem('name', result.data.name);
        localStorage.setItem('email', result.data.email);

        this.userService.user.push(newUser);

        console.log('Login Data: ', newData);

        this.email = '';
        this.password = '';
        this.rememberMe = false;

        this.displayModal = false;

        this.router.navigate(['/']);
      },
      (err) => {
        console.log(err);
        this.toastr.error('Invalid Email/Password. Please try again');
        this.displayModal = false;
      }
    );
  }
  show:any=false;
  onEyeClick() {
      this.show = !this.show;
  }
}
