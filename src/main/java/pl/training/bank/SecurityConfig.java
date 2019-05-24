package pl.training.bank;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.util.List;

@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true,prePostEnabled = true)
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @NonNull
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //@Autowired
    //private AccessDecisionManager accessDecisionManager;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth/*.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN")*/
            .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select login,password,enabled from client where login = ?")
                .authoritiesByUsernameQuery("select login,role from client where login = ?");
           /* .ldapAuthentication()
                .userSearchBase("ou=people")
                .userSearchFilter("(uid={0})")
                .groupSearchBase("ou=roles")
                .groupSearchFilter("(member={0})")
                .contextSource()
                .root("dc=springframework,dc=org")
                .ldif("users.ldif");*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/resources/**").permitAll()
                    .antMatchers("/**").hasRole("ADMIN")//.accessDecisionManager(accessDecisionManager)
                .and()
                    .formLogin()
                        .loginPage("/login.html")
                        .permitAll()
                .and()
                    .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout.html"))
                        .logoutSuccessUrl("/login.html");
    }

   /* @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> voters =
                List.of(new RandomVoter()*//*, new WebExpressionVoter(), new RoleVoter(), new AuthenticatedVoter()*//*);
        return new UnanimousBased(voters);// new ConsensusBased(voters) // new AffirmativeBased(voters);
    }*/

}
