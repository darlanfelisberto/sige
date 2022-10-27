import {Route, RouterModule, Routes} from "@angular/router";
import {PerfilComponent} from "./comp/perfil/perfil.component";
import {ModuleWithProviders} from "@angular/core";
import {AuthGuardService} from "../../../template-lib/src/guards/auth-guard.service";
import {CustomRoute} from "../../../template-lib/src/util/CustomRoute";

export const APP_ROUTES: CustomRoute[] = [
  {path:'perfil',component:PerfilComponent,canActivate:[AuthGuardService],perm:['teste','teste']}
]
