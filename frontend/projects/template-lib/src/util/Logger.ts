import {Injectable} from "@angular/core";

@Injectable()
export class Logger{

  private enable:boolean = true;

  info(msg:string):void{
    if(this.enable) {
      console.info('[INFO] ' + msg);
    }
  }

}
