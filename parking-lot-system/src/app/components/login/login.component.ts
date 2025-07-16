import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { ManagerService } from '../../services/manager-service';
import { LoginRequest } from '../../models/login.request';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginRequest: LoginRequest = {
    username: '',
    password: ''
  }

  constructor(private managerService: ManagerService, private router: Router) { }

  login() {
    console.log(this.loginRequest);
    this.managerService.login(this.loginRequest).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: () => alert('Invalid login')
    });
  }
}
