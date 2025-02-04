package ac.dnd.dodal.config.security.handler.jwt;

import ac.dnd.dodal.config.security.handler.common.AbstractAuthenticationFailureHandler;
import ac.dnd.dodal.domain.user.enums.E_user_code;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler extends AbstractAuthenticationFailureHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        setErrorResponse(response, E_user_code.ACCESS_DENIED);
    }
}

