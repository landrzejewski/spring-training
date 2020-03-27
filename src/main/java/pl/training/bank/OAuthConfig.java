package pl.training.bank;

import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import pl.training.bank.user.UserService;

@Configuration
public class OAuthConfig {

    @EnableAuthorizationServer
    @Configuration
    public static class AuthorizationServer implements AuthorizationServerConfigurer {

        @Autowired
        private AuthenticationManager authenticationManagerBean;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private JwtAccessTokenConverter jwtAccessTokenConverter;
        @Autowired
        private TokenStore tokenStore;
        @Autowired
        private UserService userService;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer) throws Exception {
            authorizationServerEndpointsConfigurer
                    .accessTokenConverter(jwtAccessTokenConverter)
                    .authenticationManager(authenticationManagerBean)
                    .tokenStore(tokenStore)
                    .userDetailsService(userService);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer) throws Exception {
            authorizationServerSecurityConfigurer.allowFormAuthenticationForClients().passwordEncoder(passwordEncoder);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer) throws Exception {
            clientDetailsServiceConfigurer.inMemory()
                    .withClient("bank")
                    .authorizedGrantTypes("password")
                    .scopes("public");
        }

    }

    @EnableResourceServer
    @Configuration
    public static class ResourceServer extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                    .antMatchers("/api/v1/**").hasRole("USER");
        }

    }

}
