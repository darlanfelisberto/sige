import {PerfilComponent} from "./comp/perfil/perfil.component";
import {AuthGuardService} from "../../../template-lib/src/guards/auth-guard.service";
import {CustomRoute} from "../../../template-lib/src/util/CustomRoute";
import {KanbanComponent} from "./comp/kanban/kanban.component";

export const APP_ROUTES: CustomRoute[] = [
  {path: 'perfil', component: PerfilComponent, canActivate: [AuthGuardService], perm: ['teste', 'teste']},
  {path : 'kanban', component:KanbanComponent, canActivate: [AuthGuardService], perm: ['teste', 'teste']}
]
