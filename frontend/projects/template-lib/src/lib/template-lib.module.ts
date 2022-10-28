import { NgModule } from '@angular/core';
import {OidcService} from "../service/Oidc.service";
import {Logger} from "../util/Logger";
import {AuthGuardService} from "../guards/auth-guard.service";
import {AuthService} from "../service/auth.service";
import {HttpClientModule} from "@angular/common/http";
import {BrowserModule} from "@angular/platform-browser";

@NgModule({
  declarations: [

  ],
  providers:[
    Logger,
    OidcService,
    AuthGuardService,
    AuthService
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  exports: [
  ]
})
export class TemplateLibModule { }
