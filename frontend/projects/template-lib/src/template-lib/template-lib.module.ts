import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Oidc} from "../util/Oidc";
import {Logger} from "../util/Logger";



@NgModule({
  declarations:[],
  providers: [
    Oidc,
    Logger
  ],
  imports: [
    CommonModule
  ]
})
export class TemplateLibModule { }
