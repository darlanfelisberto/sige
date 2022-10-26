import {Injectable, OnInit} from '@angular/core';
import {OidcService} from "./Oidc.service";
import * as Crypto from "crypto";

@Injectable()
export class AuthService implements OnInit {

  constructor(private oidc: OidcService) {
  }


  isAuthenticad(): boolean {
    return false;
  }

  ngOnInit(): void {
    this.oidc.loagServerinfo();
  }

  encaminhaLogin() {
    window.location.href = this.oidc.getAuthUrl() +
      '?response_type='+ encodeURI("id_token token") +
       '&client_id=angular' +
       '&redirect_uri=http://localhost:4200' +
       '&state=' + this.broofa() +
       '&scope=openid';
  }

  broofa() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
      var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
  }

  /**
   * http://localhost:8081/auth/realms/sige/auth
   * ?response_type=code
   * &client_id=client_id
   * &redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fcomendo%2F
   * &state=6591f420-17b8-4abf-8f73-6e884164b50b
   * &scope=openid
   */
}
