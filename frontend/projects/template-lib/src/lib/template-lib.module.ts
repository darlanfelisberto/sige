import { NgModule } from '@angular/core';
import { TemplateLibComponent } from './template-lib.component';
import {OidcService} from "../service/Oidc.service";
import {Logger} from "../util/Logger";
import {AuthGuardService} from "../guards/auth-guard.service";
import {AuthService} from "../service/auth.service";
import {HttpClientModule, HttpContextToken, HttpHeaders} from "@angular/common/http";
import {BrowserModule} from "@angular/platform-browser";

@NgModule({
  declarations: [

  ],
  providers:[
    OidcService,
    AuthGuardService,
    AuthService,
    Logger
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  exports: [

  ]
})
export class TemplateLibModule { }
