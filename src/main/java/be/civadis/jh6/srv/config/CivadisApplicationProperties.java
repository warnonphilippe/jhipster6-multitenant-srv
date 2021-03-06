package be.civadis.jh6.srv.config;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

public class CivadisApplicationProperties {

    private String fileDbUpdate;
    private String issuerBaseUri;
    private Multitenancy multitenancy = new Multitenancy();

    public String getFileDbUpdate() {
        return fileDbUpdate;
    }

    public String getIssuerBaseUri() {
        return issuerBaseUri;
    }

    public void setIssuerBaseUri(String issuerBaseUri) {
        this.issuerBaseUri = issuerBaseUri;
    }

    public void setFileDbUpdate(String fileDbUpdate) {
        this.fileDbUpdate = fileDbUpdate;
    }

    public Multitenancy getMultitenancy() {
        return multitenancy;
    }

    public void setMultitenancy(Multitenancy multitenancy) {
        this.multitenancy = multitenancy;
    }

    public static class Multitenancy {

        private Tenant defaultTenant;

        private List<Tenant> tenants = new ArrayList<Tenant>();

        @PostConstruct
        public void init() {
            List<Tenant> tcs = tenants.stream().filter(tc -> tc.isDefault()).collect(Collectors.toCollection(ArrayList::new));
            if (tcs.size() > 1) {
                throw new IllegalStateException("Only can be configured as default one data source. Review your configuration");
            }
            this.defaultTenant = tcs.get(0);
        }

        public List<Tenant> getTenants() {
            return tenants;
        }

        public void setTenants(List<Tenant> tenants) {
            this.tenants = tenants;
        }

        public Tenant getDefaultTenant() {
            if (defaultTenant == null){
                init();
            }
            return defaultTenant;
        }

        public static class Tenant {

            private String name;

            private boolean isDefault;

            private String driverClassName;

            private String url;

            private String username;

            private String password;

            private Hikari hikari = new Hikari();

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDriverClassName() {
                return driverClassName;
            }

            public void setDriverClassName(String driverClassName) {
                this.driverClassName = driverClassName;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public boolean isDefault() {
                return isDefault;
            }

            public void setDefault(boolean isDefault) {
                this.isDefault = isDefault;
            }

            public Hikari getHikari() {
                return hikari;
            }

            public void setHikari(Hikari hikari) {
                this.hikari = hikari;
            }

            public static class Hikari{

                private boolean isAutoCommit = true;
                private Properties dataSourceProperties = new Properties();

                public boolean isAutoCommit() {
                    return isAutoCommit;
                }

                public void setAutoCommit(boolean autoCommit) {
                    isAutoCommit = autoCommit;
                }

                public Properties getDataSourceProperties() {
                    return dataSourceProperties;
                }

                public void setDataSourceProperties(Properties dataSourceProperties) {
                    this.dataSourceProperties = dataSourceProperties;
                }
            }

        }

    }

}
