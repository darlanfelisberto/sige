import {Injectable} from "@angular/core";
import {Logger} from "./Logger";

@Injectable()
export class Oidc {

  oidcProvider: string = 'http://localhost:8081/auth';
  realm: string = 'sige';
  resource: string = 'client_id';
  secret: string = '3fd70ff4-fe2b-47b6-a8a3-cd1cf281a939';
  oidcConfiguration:any = undefined;

  constructor(private log:Logger) {
  }

  public loagServerinfo() {

    this.log.info('Chamando oidc proviver well-know');
    var oidcProviderConfigUrl;
    if (this.oidcProvider.charAt(this.oidcProvider.length - 1) == '/') {
      oidcProviderConfigUrl = this.oidcProvider + '.well-known/openid-configuration';
    } else {
      oidcProviderConfigUrl = this.oidcProvider + '/.well-known/openid-configuration';
    }
    var req = new XMLHttpRequest();
    req.open('GET', oidcProviderConfigUrl, true);
    req.setRequestHeader('Accept', 'application/json');

    req.onreadystatechange = () => {
      if (req.readyState == 4) {
        if (req.status == 200) {
          this.oidcConfiguration = JSON.parse(req.responseText);
        }
      }
    };

    req.send();
  }
}
