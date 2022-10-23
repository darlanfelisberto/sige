import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {AuthService} from "../service/auth.service";
import {CustomRoute} from "../util/CustomRoute";

@Injectable()
export class AuthGuardService implements CanActivate{

  constructor(private  autService:AuthService,private router:Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
    : Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if(this.autService.isAuthenticad()){
      return true;
    }else{
       console.log((route.routeConfig as CustomRoute).perm);
       
    }

    return this.autService.isAuthenticad();
  }
}
