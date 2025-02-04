package ac.dnd.dodal.config.security.config;


import ac.dnd.dodal.config.security.filter.JwtAuthenticationFilter;
import ac.dnd.dodal.config.security.filter.JwtExceptionFilter;
import ac.dnd.dodal.config.security.handler.jwt.JwtAccessDeniedHandler;
import ac.dnd.dodal.config.security.handler.jwt.JwtAuthEntryPoint;
import ac.dnd.dodal.config.security.handler.logout.CustomSignOutProcessHandler;
import ac.dnd.dodal.config.security.handler.logout.CustomSignOutResultHandler;
import ac.dnd.dodal.config.security.usecase.LoadUserPrincipalByIdUseCase;
import ac.dnd.dodal.config.security.util.JwtUtil;
import ac.dnd.dodal.domain.user.constant.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomSignOutProcessHandler customSignOutProcessHandler;
    private final CustomSignOutResultHandler customSignOutResultHandler;

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final LoadUserPrincipalByIdUseCase loadUserPrincipalByIdUseCase;
    private final JwtUtil jwtUtil;
    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(registry ->
                        registry
//                                .anyRequest().permitAll()
                                .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll()
                                .requestMatchers(Constants.ADMIN_URLS.toArray(String[]::new)).hasRole("ADMIN")
                                .requestMatchers(Constants.USER_URLS.toArray(String[]::new)).hasAnyRole("USER", "ADMIN")
                                //로그인 없이 둘러보기 시 사용할 권한
//                                .requestMatchers(Constants.GUEST_URLS.toArray(String[]::new)).hasAnyRole("GUEST")
                                .anyRequest().authenticated()
                )

                .formLogin(AbstractHttpConfigurer::disable)

                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .addLogoutHandler(customSignOutProcessHandler)
                        .logoutSuccessHandler(customSignOutResultHandler)
                )

                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .authenticationEntryPoint(jwtAuthEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(loadUserPrincipalByIdUseCase, jwtUtil),
                        LogoutFilter.class)
                .addFilterBefore(
                        new JwtExceptionFilter(),
                        JwtAuthenticationFilter.class)

                .getOrBuild();
    }
}
