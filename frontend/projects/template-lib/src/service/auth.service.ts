import {Injectable, OnInit} from '@angular/core';
import {Oidc} from "../util/Oidc";

@Injectable()
export class AuthService implements OnInit{

  constructor(private oidc:Oidc) { }


  isAuthenticad():boolean{
    return false;
  }

  ngOnInit(): void {
    this.oidc.loagServerinfo();
  }
}
