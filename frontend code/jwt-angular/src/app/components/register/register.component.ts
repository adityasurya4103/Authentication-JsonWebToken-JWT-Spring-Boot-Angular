import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JwtService } from 'src/app/service/jwt.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerForm!: FormGroup;

  constructor(
    private service: JwtService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      confirmPassword: ['', [Validators.required]],
    }, { validator: this.passwordMathValidator })
  }

  passwordMathValidator(formGroup: FormGroup) {
    const password = formGroup.get('password')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;
    if (password != confirmPassword) {
      formGroup.get('confirmPassword')?.setErrors({ passwordMismatch: true });
    } else {
      formGroup.get('confirmPassword')?.setErrors(null);
    }
  }

  submitForm() {
    // Extracting required fields from the registerForm
    const { email, name, password } = this.registerForm.value;
    
    // Creating a new object with only the required fields
    const registrationData = { email, name, password };

    console.log(registrationData);
    console.log(this.registerForm.value);
    
    this.service.register(registrationData).subscribe(
      (response) => {
        console.log(response);
        
        if (response.id != null) {
          alert("Hello " + response.name);
        }
      },(error)=>{console.log(error);
        alert(error.message);
      }
      
    );
}

}