import {Route} from "@angular/router";

export interface CustomRoute extends Route{
  perm:string[];
}
