package be.civadis.jh6.srv.multitenancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Beans spring nécessaire au multitenant
 */
@Configuration
public class MultiTenantConfig {

    private final Logger log = LoggerFactory.getLogger(MultiTenantConfig.class);

    public MultiTenantConfig() {
    }

}

