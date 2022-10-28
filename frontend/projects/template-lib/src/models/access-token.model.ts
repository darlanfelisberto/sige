export class AccessTokenModel{
  // issuer
    iss:string ="";
  // subject
    sub:string ="";
  // audience
    aud:string ="";
  // expirationTime
    exp:string ="";
  nbf:string ="";
  iat:string ="";
  jti:string ="";
  code:string ="";
  groups:string[] =[];
  nonce:string ="";
  typ:string ="";
  azp:string ="";
  scope:string ="";
  sid:string ="";
  email_verified:string ="";
  name:string ="";
  preferred_username:string ="";
  locale:string ="";
  given_name:string ="";
  family_name:string ="";
  email:string ="";


  possuiPermissoes(permissoes:string[]):boolean{
    for (const permissao of permissoes) {
      if("*"=== permissao && this.groups.length > 0){
        return true;
      }
      for (const grupo of this.groups){
        if(permissao === grupo){
          return true;
        }
      }
    }
    return false;
  }

}
