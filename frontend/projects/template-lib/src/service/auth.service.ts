import {Injectable, OnInit} from '@angular/core';
import {OidcService} from "./Oidc.service";
import * as Crypto from "crypto";

@Injectable()
export class AuthService implements OnInit {

  private sessionType:string = "";
  private accessToken:string = "";
  private expireIn:number = 0;


  constructor(private oidc: OidcService) {
  }

  isAuthenticad(): boolean {
    return false;
  }

  ngOnInit(): void {
    this.oidc.loagServerinfo();
  }

  finalizaLogin(param: any):boolean{
    if(param['access_token']){
      this.accessToken = param['access_token'];
      console.log(JSON.parse(atob( this.accessToken.split('.')[1])));
    }
    return true;
  }

  encaminhaLogin(path:string) :void {
    window.location.href = this.oidc.getAuthUrl() +
      '?response_type='+ encodeURI("id_token token") +
       '&client_id=angular' +
       '&redirect_uri=http://localhost:4200' + path +
       '&state=' + this.broofa() +
       '&scope=openid';
  }

  broofa() :string{
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
      var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
  }

  hasPermissao(permissaoes:object) : boolean{

    return true;
  }

}
