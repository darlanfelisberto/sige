import {Injectable, OnInit} from "@angular/core";
import {Logger} from "../util/Logger";

@Injectable()
export class OidcService implements OnInit{

  public static OIDC_WELL_KNOWN:string = 'OIDC_WELL_KNOWN';

  oidcProvider: string = 'http://localhost:8081/auth/realms/iffar';
  realm: string = 'sige';
  resource: string = 'client_id';
  secret: string = '3fd70ff4-fe2b-47b6-a8a3-cd1cf281a939';
  oidcConfiguration:any = {};

  constructor(private log:Logger) {
    this.loagServerinfo();
    if(sessionStorage.getItem(OidcService.OIDC_WELL_KNOWN) ){
      this.log.info('Recuperando '+OidcService.OIDC_WELL_KNOWN);
      this.oidcConfiguration = JSON.parse(sessionStorage.getItem(OidcService.OIDC_WELL_KNOWN) as string);
    }
  }

  ngOnInit(): void {
    console.log('teste')
    this.loagServerinfo();
  }

  getAuthUrl():string{
    return this.oidcConfiguration['authorization_endpoint'];
  }

  loagServerinfo() {

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

    req.onerror =  () =>{
      this.log.info('erro');
    }

    req.onreadystatechange = () => {
      this.log.info('respospa');
      if (req.readyState == 4) {
        if (req.status == 200) {
          this.oidcConfiguration = JSON.parse(req.responseText);
          sessionStorage.setItem(OidcService.OIDC_WELL_KNOWN,req.responseText);
        }
      }
    };

    req.send();
  }
}
