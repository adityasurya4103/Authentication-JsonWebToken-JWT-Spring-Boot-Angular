import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtService } from 'src/app/service/jwt.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

  export class LoginComponent implements OnInit {

    loginForm!: FormGroup;
  
    constructor(
      private service: JwtService,
      private fb: FormBuilder,
      private router: Router
    ) { }
  
    ngOnInit(): void {
      this.loginForm = this.fb.group({
        email: ['', Validators.required, Validators.email],
        password: ['', Validators.required],
      })
    }
  
    submitForm() {
      this.service.login(this.loginForm.value).subscribe(
        (response) => {
          console.log(response);
          if (response.token != null) {
            alert("Hello, Your token is " + response.token);
            const jwtToken = response.token;
            localStorage.setItem('jwt', jwtToken);
            this.router.navigateByUrl("/home");
          }
        }, (error)=>{
          alert("Error!!!! invalid Credentials ");
        }
      )
    }
  
  }