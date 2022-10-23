import { Component, OnInit } from '@angular/core';
import {Logger} from "../../../../../template-lib/src/util/Logger";


@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.scss']
})
export class PerfilComponent implements OnInit {

  constructor(private log:Logger) {
    this.log.info("rese");
  }

  ngOnInit(): void {
  }

}
