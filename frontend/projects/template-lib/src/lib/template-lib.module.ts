import { NgModule } from '@angular/core';
import { TemplateLibComponent } from './template-lib.component';
import {OidcService} from "../service/Oidc.service";
import {Logger} from "../util/Logger";
import {AuthGuardService} from "../guards/auth-guard.service";
import {AuthService} from "../service/auth.service";



@NgModule({
  declarations: [

  ],
  providers:[
    OidcService,
    Logger,
    AuthGuardService,
    AuthService,
    Logger
  ],
  imports: [
  ],
  exports: [

  ]
})
export class TemplateLibModule { }
