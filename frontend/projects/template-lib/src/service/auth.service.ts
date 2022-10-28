import {Injectable, OnInit} from '@angular/core';
import {OidcService} from "./Oidc.service";
import {Params} from "@angular/router";
import {AccessTokenModel} from "../models/access-token.model";
import {Logger} from "../util/Logger"; AccessTokenModel

@Injectable()
export class AuthService implements OnInit {

  private sessionType:string = "";
  private accessToken:string = "";
  private expireIn:number = 0;
  private accessTokenModel:AccessTokenModel = new AccessTokenModel();


  constructor(private oidc: OidcService,private log:Logger) {
  }

  isAuthenticad(): boolean {
    return this.accessToken != "";
  }

  ngOnInit(): void {
    this.oidc.loagServerinfo();
  }

  finalizaLogin(param:Params):boolean{
    if(param['access_token']){
      this.accessToken = param['access_token'];
      // console.log(JSON.parse(atob( this.accessToken.split('.')[1])));
      this.expireIn = param['expire_in'];
      this.sessionType = param['session_type'];
      Object.assign(this.accessTokenModel,JSON.parse(atob( this.accessToken.split('.')[1])));
      this.log.info("Usuário logado!");
      return true;
    }else {
      return false;
    }
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

  hasPermissao(permissaoes:string[]) : boolean{
    return this.accessTokenModel.possuiPermissoes(permissaoes);
  }

  getAccessToken():string{
    return this.accessToken;
  }

}
