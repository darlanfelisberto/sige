import {Injectable, OnInit} from '@angular/core';
import {OidcService} from "./Oidc.service";

@Injectable()
export class AuthService implements OnInit{

  constructor(private oidc:OidcService) {
  }


  isAuthenticad():boolean{
    return false;
  }

  ngOnInit(): void {
    this.oidc.loagServerinfo();
  }
}
