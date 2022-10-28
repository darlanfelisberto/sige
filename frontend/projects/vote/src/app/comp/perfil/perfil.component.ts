import {Component, OnInit} from '@angular/core';
import {Logger} from "../../../../../template-lib/src/util/Logger";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "../../../../../template-lib/src/service/auth.service";


@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.scss']
})
export class PerfilComponent implements OnInit {

  constructor(private log: Logger, private http: HttpClient, private authService: AuthService) {

  }

  ngOnInit(): void {
    let httpOptions = {
      headers: new HttpHeaders({
          // "Content-Type": "application/json",
          // 'Access-Control-Allow-Origin': '*',
          'Authorization': 'Bearer ' + this.authService.getAccessToken()+'s'

        }
      )
    };

    console.log(
      this.http.get("http://localhost:8081/auth/realms/iffar/userinfo_endpoint", httpOptions).subscribe(
        data => {
          console.log(data)
        }
      )
    );
  }

}
