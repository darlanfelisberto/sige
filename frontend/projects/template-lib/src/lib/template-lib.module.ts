import { NgModule } from '@angular/core';
import { TemplateLibComponent } from './template-lib.component';
import {Oidc} from "../util/Oidc";
import {Logger} from "../util/Logger";



@NgModule({
  declarations: [

  ],
  providers:[
    Oidc,
    Logger
  ],
  imports: [
  ],
  exports: [

  ]
})
export class TemplateLibModule { }
