import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PerfilComponent } from './comp/perfil/perfil.component';
import {TemplateLibModule} from "../../../template-lib/src/lib/template-lib.module";
import {TemplateLibComponent} from "../../../template-lib/src/lib/template-lib.component";
import { KanbanComponent } from './comp/kanban/kanban.component';

@NgModule({
  declarations: [
    AppComponent,
    PerfilComponent,
    TemplateLibComponent,
    KanbanComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    TemplateLibModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
