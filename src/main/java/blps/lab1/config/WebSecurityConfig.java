package blps.lab1.config;

import blps.lab1.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
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
                .antMatchers("/moderator/**").hasRole("MODERATOR")
                //Доступ только для пользователей с ролью Юзер
                .antMatchers("/user/**").hasRole("USER")
                //Доступ разрешен всем пользователей
                .antMatchers(HttpMethod.GET, "/index*", "/static/**", "/*.js", "/*.json", "/*.ico").permitAll()
                //Все остальные страницы требуют аутентификации
//                .anyRequest().authenticated()
                .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/auth/signin")
                .loginProcessingUrl("/auth/signin")
                .successHandler((request, response, authentication) -> {
                    User user = (User) authentication.getPrincipal();
                    String roleName = "NO ROLES";
                    String redirectURL = request.getContextPath();
                    if (user.hasRole("ROLE_MODERATOR")) {
                        roleName = "MODERATOR";
                        redirectURL = "/moderator";
                    } else if (user.hasRole("ROLE_USER")) {
                        roleName = "USER";
                        redirectURL = "/user";
                    }
                    log.debug("user {} has role {}", user, roleName);
                    response.sendRedirect(redirectURL);
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/signin")
                .permitAll();
    }
}
