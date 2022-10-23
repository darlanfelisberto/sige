import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {TemplateLibComponent} from "../../../template-lib/src/lib/template-lib.component";
import { PerfilComponent } from './comp/perfil/perfil.component';
import {AuthService} from "../../../template-lib/src/service/auth.service";
import {AuthGuardService} from "../../../template-lib/src/guards/auth-guard.service";

@NgModule({
  declarations: [
    AppComponent,
    TemplateLibComponent,
    PerfilComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
    // ,routing
  ],
  providers: [AuthService,AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
