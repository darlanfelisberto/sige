import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-kanban',
  templateUrl: './kanban.component.html',
  styleUrls: ['./kanban.component.scss']
})
export class KanbanComponent {
  colunas:String[] = ['A','B','C'];
  referencia: any = null;
  constructor() { }

  onDragover(event:DragEvent){
    event.preventDefault();
    console.log('onDragover');
  }

  onDrop(event:DragEvent){
    event.preventDefault();
    console.log('onDrop');
    console.log(event);
    (<HTMLDivElement>event.target).appendChild(this.referencia);
  }

  onDragStart(event:DragEvent){
    // event.preventDefault();
    console.log(event);
    this.referencia = event.target;
    event.dataTransfer?.setData('texto','');
  }
}
