package be.civadis.jh6.srv.multitenancy;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * Filtre permettant d'extraire le tenant courant du token d'authentication
 */
public class TenantFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        //extraire le token
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.toLowerCase().startsWith("bearer ") && authHeader.length() > 7){
            token = authHeader.substring(7);
        }

        //extraire le tenant du token
        String tenant = TokenDecoder.getInstance().getTenant(token);

        // set du tenant dans le context
        try {
            if (tenant != null && !tenant.isEmpty()){
                TenantContext.setCurrentTenant(tenant);
            } else {
                TenantContext.clear();
            }
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }        

    }

}
