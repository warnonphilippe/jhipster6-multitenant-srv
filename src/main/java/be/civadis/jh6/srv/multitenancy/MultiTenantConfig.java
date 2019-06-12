package be.civadis.jh6.srv.multitenancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.web.context.WebApplicationContext;

import be.civadis.jh6.srv.config.ApplicationProperties;

/**
 * Beans spring nécessaire au multitenant
 */
@Configuration
public class MultiTenantConfig {

    private final Logger log = LoggerFactory.getLogger(MultiTenantConfig.class);

    private ApplicationProperties applicationProperties;

    public MultiTenantConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public JwtDecoder jwtDecoder(TenantUtils tenantUtils){
        log.warn("*************************************************");
        log.warn("*************************************************");
        log.warn("*************************************************");
        log.warn("Creating JwtDecoder for tenant " + tenantUtils.getTenant());
        log.warn("*************************************************");
        log.warn("*************************************************");
        log.warn("*************************************************");
        return JwtDecoders.fromOidcIssuerLocation(applicationProperties.getIssuerBaseUri() + tenantUtils.getTenant());
    }

}
