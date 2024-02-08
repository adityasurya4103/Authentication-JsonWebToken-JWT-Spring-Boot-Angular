import { Component } from '@angular/core';
import { JwtService } from 'src/app/service/jwt.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  message!: string;

  constructor(
    private service: JwtService
  ) { }

  ngOnInit() {
  
  }

  hello() {
    this.service.hello().subscribe(
      (response) => {
        console.log(response);
        this.message = response.message;
      },(error)=>{
        console.log(error);
        alert("hello madhe error aahe");
        
      }
    )
  }

}
