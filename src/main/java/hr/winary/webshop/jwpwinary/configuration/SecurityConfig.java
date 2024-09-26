package hr.winary.webshop.jwpwinary.configuration;

import com.nimbusds.oauth2.sdk.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/login").anonymous()
                        .requestMatchers("/shop", "/shop/category/{id}", "/shop/viewwine/{id}").hasAnyRole("USER", "ADMIN", "READ_ONLY")
                        .requestMatchers("/addToCart/{id}", "/cart", "/cart/removeItem/{index}", "/cart/increase/{index}", "/cart/decrease/{index}").hasAnyRole("USER", "READ_ONLY")
                        .requestMatchers("/checkout", "/order/complete", "/order/paybycash", "/payment/create", "/payment/success", "/payment/cancel", "/payment/error").hasAnyRole("USER")
                        .requestMatchers("/admin", "/admin/categories", "/admin/categories/add", "/admin/categories/add", "/admin/categories/delete/{id}",
                                "/admin/categories/update/{id}", "/admin/wines", "/admin/wines/add", "/admin/wines/delete/{id}", "/admin/wines/update/{id}", "/admin/userlogins", "/h2-console").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.successHandler(successHandler))
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(accessDeniedHandler()));
        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
        accessDeniedHandler.setErrorPage("/403");
        return accessDeniedHandler;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
