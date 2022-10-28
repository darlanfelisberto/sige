import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {AuthService} from "../service/auth.service";
import {CustomRoute} from "../util/CustomRoute";
import {OidcService} from "../service/Oidc.service";
import {Logger} from "../util/Logger";
import {HttpClient, HttpHeaders, HttpRequest} from "@angular/common/http";

@Injectable()
export class AuthGuardService implements CanActivate{
  //oidc:OidcService = new OidcService(new Logger());

  constructor(private  autService:AuthService,private router:Router,private http:HttpClient) {

  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
    : Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

      console.log(route.queryParams);

    if(this.autService.isAuthenticad()){
      return true;
    }else{
      if(this.autService.finalizaLogin(route.queryParams)){
        // this.router.navigate([state.url],{});
      }else{
        this.autService.encaminhaLogin(state.url);
      }
    }

    return this.autService.hasPermissao((route.routeConfig as CustomRoute).perm);
  }
}
