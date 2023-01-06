package com.infocz.infoCruise.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.infocz.infoCruise.api.security.JwtAccessDeniedHandler;
import com.infocz.infoCruise.api.security.JwtAuthenticationEntryPoint;
import com.infocz.infoCruise.api.security.TokenProvider;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAtuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Autowired
    private CorsConfig corsConfig;
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers("/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http	.addFilter(corsConfig.corsFilter())
                    .csrf().disable()

                    /**세션 사용하지 않음*/
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

					/**401, 403 Exception 핸들링 */
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(jwtAtuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)

					/** HttpServletRequest를 사용하는 요청들에 대한 접근 제한 설정*/
                    .and()
                    .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/com/**"
                            , "/api/cypher/**"
                            , "/api/rdb2/**"
                            , "/api/getDbMonitor").permitAll()
                        // .requestMatchers("/api/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                    )
                    /**JwtSecurityConfig 적용 */
                    .apply(new JwtSecurityConfig(tokenProvider))

                    .and().build();
    }
}