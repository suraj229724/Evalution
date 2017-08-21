package cc.accenture.performanceevaluation.configuration;

import cc.accenture.performanceevaluation.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"cc.accenture.performanceevaluation.security"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customUserDetailsService")
    private CustomUserDetailsService customUserDetailsService;

    @Bean(name = "bCryptPasswordEncoder")
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/js/**").access("permitAll")
                .antMatchers("/images/**").access("permitAll")
                .antMatchers("/img/**").access("permitAll")
                .antMatchers("/css/**").access("permitAll")
                .antMatchers("/favicon.ico*").access("permitAll")
                .antMatchers("/home/login.htm*").access("permitAll")
                .antMatchers("/ajax/**").access("hasRole('ROLE_BF_SHOW_HOME')")
                .antMatchers("/errors/accessDenied.htm*").access("isFullyAuthenticated()")
                .antMatchers("/admin/updateExpiredPassword.htm*").access("hasRole('ROLE_BF_PASSWORD_EXPIRED')")
                .antMatchers("/home/home.htm*").access("hasRole('ROLE_BF_SHOW_HOME')")
                .antMatchers("/home/changePassword.htm*").access("hasRole('ROLE_BF_SHOW_HOME')")
                .antMatchers("/admin/reloadApplicationLayer.htm*").access("hasRole('ROLE_BF_RELOAD_APP_LAYER')")
                .antMatchers("/admin/addUser.htm*").access("hasRole('ROLE_BF_CREATE_USER')")
                .antMatchers("/admin/userList.htm*").access("hasRole('ROLE_BF_LIST_USER')")
                .antMatchers("/admin/editUser.htm*").access("hasRole('ROLE_BF_EDIT_USER')")
                .antMatchers("/admin/userFailedAttemptsReset.htm*").access("hasRole('ROLE_BF_EDIT_USER')")
                .antMatchers("/admin/showEditUser.htm*").access("hasRole('ROLE_BF_EDIT_USER')")
                .antMatchers("/report/reportAccessLog.htm*").access("hasRole('ROLE_BF_REPORT_ACCESS_LOG')")
                .and().formLogin().loginProcessingUrl("/perform-login").loginPage("/home/login.htm").
                defaultSuccessUrl("/home/home.htm").failureUrl("/home/login.htm?login_error=true")
                .usernameParameter("j_username").passwordParameter("j_password")
                .and().logout().invalidateHttpSession(true).logoutUrl("/perform-logout")
                .and().csrf()
                .and().exceptionHandling().accessDeniedPage("/errors/accessDenied.htm")
                .and().sessionManagement().maximumSessions(1);

    }
}
