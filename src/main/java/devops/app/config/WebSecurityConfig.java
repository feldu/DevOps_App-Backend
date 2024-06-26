package devops.app.config;

import devops.app.components.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    final private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    public WebSecurityConfig(LoginSuccessHandler loginSuccessHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http)
            throws Exception {
        http.csrf()
                .disable().
                authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/auth/**").not().fullyAuthenticated()
                //Доступ только для пользователей с ролью Модератор
                .antMatchers(HttpMethod.GET, "/moderator/**").hasRole("MODERATOR")
                .antMatchers(HttpMethod.GET, "/review/approved/false").hasRole("MODERATOR")
                .antMatchers(HttpMethod.DELETE, "/review/**").hasRole("MODERATOR")
                .antMatchers(HttpMethod.PATCH, "/review/approval/**").hasRole("MODERATOR")

                //Доступ только для пользователей с ролью Юзер
                .antMatchers(HttpMethod.GET, "/user/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/user/**").hasRole("USER")

                //Доступ и для Модератора и для Юзера
                .antMatchers(HttpMethod.GET, "/review/authorName/**").hasAnyRole("MODERATOR", "USER")
                .antMatchers(HttpMethod.POST, "/review/").hasAnyRole("MODERATOR", "USER")
                //Доступ разрешен всем пользователей
                .antMatchers(HttpMethod.GET, "/index*", "/static/**", "/*.js", "/*.json", "/*.ico").permitAll()
                .antMatchers(HttpMethod.GET, "/review/approved/true").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
//                .anyRequest().permitAll()
                .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/auth/signin")
                .loginProcessingUrl("/auth/signin")
                .successHandler(loginSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/signin")
                .permitAll();
    }
}
