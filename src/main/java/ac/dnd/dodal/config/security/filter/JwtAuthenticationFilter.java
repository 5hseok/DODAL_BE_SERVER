package ac.dnd.dodal.config.security.filter;

import ac.dnd.dodal.common.enums.CommonResultCode;
import ac.dnd.dodal.common.exception.DodalException;
import ac.dnd.dodal.config.security.info.UserPrincipal;
import ac.dnd.dodal.config.security.usecase.LoadUserPrincipalByIdUseCase;
import ac.dnd.dodal.config.security.util.HeaderUtil;
import ac.dnd.dodal.config.security.util.JwtUtil;
import ac.dnd.dodal.domain.user.constant.Constants;
import ac.dnd.dodal.domain.user.enums.E_user_code;
import ac.dnd.dodal.domain.user.enums.E_user_role;
import ac.dnd.dodal.domain.user.exception.UserException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final LoadUserPrincipalByIdUseCase loadUserPrincipalByIdUseCase;

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new DodalException(CommonResultCode.INVALID_HEADER_ERROR));
        Claims claims = jwtUtil.validateToken(token);

        Long userId = Long.valueOf(claims.get(Constants.USER_ID_CLAIM_NAME, String.class));
        E_user_role role = E_user_role.fromName(claims.get(Constants.USER_ROLE_CLAIM_NAME, String.class));
        String requestURI = request.getRequestURI();
        UserPrincipal userPrincipal = (UserPrincipal) loadUserPrincipalByIdUseCase.execute(userId, requestURI);

        if (!userPrincipal.getRole().equals(role)) {
            throw new UserException(E_user_code.ACCESS_DENIED);
        }

        // 인증된 사용자 정보를 SecurityContext에 저장하여 인가 과정에서 사용할 수 있도록 함.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        return Constants.NO_NEED_AUTH_URLS.stream()
                .anyMatch(pattern -> antPathMatcher.match(pattern, requestURI));
    }
}

