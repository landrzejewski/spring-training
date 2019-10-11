package pl.training.bank;

import lombok.Setter;
import org.modelmapper.spi.Tokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
public class OAuthConfig {

    @Value("classpath:token-store-schema.sql")
    private Resource schemaScript;

    @Bean
    public DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        return populator;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource, DatabasePopulator databasePopulator) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);
        return initializer;
    }

    @EnableAuthorizationServer
    @Configuration
    public static class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

        @Autowired
        @Setter
        private AuthenticationManager authenticationManagerBean;
        @Autowired
        @Setter
        private PasswordEncoder passwordEncoder;
        @Autowired
        @Setter
        private TokenStore tokenStore;
        @Autowired
        private DataSource dataSource;

        @Bean
        public TokenStore tokenStore(DataSource dataSource) {
            return new JdbcTokenStore(dataSource);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.tokenStore(tokenStore).authenticationManager(authenticationManagerBean);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.allowFormAuthenticationForClients().passwordEncoder(passwordEncoder);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            /*clients.inMemory()
                    .withClient("bank")
                    .authorizedGrantTypes("password")
                    .scopes("public");*/

            JdbcClientDetailsServiceBuilder detailsServiceBuilder = clients.jdbc(dataSource);
            try {
                new JdbcClientDetailsService(dataSource).loadClientByClientId("bank");
            } catch (Exception ignored) {
                detailsServiceBuilder
                        .withClient("bank")
                        .authorizedGrantTypes("password")
                        .scopes("public")
                        .accessTokenValiditySeconds(10 * 60);
            }

        }

    }

    @EnableResourceServer
    @Configuration
    public static class ResourceServer extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                    .antMatchers("/api/v1/**").hasRole("USER");
        }

    }

}
