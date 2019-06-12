package be.civadis.jh6.srv.multitenancy;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.civadis.jh6.srv.security.oauth2.AuthorizationHeaderUtil;

/**
 * Permet de rechercher le tenant courant
 * Recherche dans la secu spring puis dans le TenantContext
 */
@Component
public class TenantUtils {

    public static final String TENANT_PATH_VAR = "{realm}";
    public static final String TENANT_PATH_PREFIX = "realms";

    //@Autowired
    private AuthorizationHeaderUtil authorizationHeaderUtil;

    public TenantUtils(AuthorizationHeaderUtil authorizationHeaderUtil){
        this.authorizationHeaderUtil = authorizationHeaderUtil;
    }    

    public String getTenant(){

        //recherche dans la secu
        if (this.authorizationHeaderUtil != null
            && authorizationHeaderUtil.getAuthorizationHeader().isPresent()){
            String token = authorizationHeaderUtil.getAuthorizationHeader().get();
            if (token != null){
                //on extrait le tenant du token
                return TokenDecoder.getInstance().getTenant(token);
            }
        }

        //si pas dans le secu, recherche dans le TenantContext
        return Optional.ofNullable(TenantContext.getCurrentTenant()).orElse(null);
    }


}
