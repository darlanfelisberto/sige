import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {AuthService} from "../service/auth.service";
import {CustomRoute} from "../util/CustomRoute";
import {HttpClient, HttpHeaders, HttpRequest} from "@angular/common/http";

@Injectable({
  providedIn:'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private autService: AuthService, private router: Router, private http: HttpClient) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
    : Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (this.autService.isAuthenticad()) {
      return true;
    } else {
      if (this.autService.finalizaLogin(route.queryParams)) {
      } else {
        this.autService.encaminhaLogin(state.url);
      }
    }

    return this.autService.hasPermissao((route.routeConfig as CustomRoute).perm);
  }
}
