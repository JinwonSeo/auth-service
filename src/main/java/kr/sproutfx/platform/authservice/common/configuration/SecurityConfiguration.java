package kr.sproutfx.platform.authservice.common.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import kr.sproutfx.platform.authservice.common.security.jwt.JwtAuthenticationFilter;
import kr.sproutfx.platform.authservice.common.security.jwt.JwtProvider;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private JwtProvider jwtProvider;

    public SecurityConfiguration(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .cors().and()
            .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/api/auth/generate-token").permitAll()
                .antMatchers("/api/auth/refresh-token").permitAll()
                .antMatchers("/api/auth/common/security/user/**").permitAll()
                
                .anyRequest().authenticated();

        httpSecurity
            .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity
            .ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
            .antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/actuator/**"
                );
    }
}