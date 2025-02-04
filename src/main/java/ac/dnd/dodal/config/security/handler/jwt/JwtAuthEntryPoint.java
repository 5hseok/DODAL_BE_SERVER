package ac.dnd.dodal.config.security.handler.jwt;

import ac.dnd.dodal.common.enums.CommonResultCode;
import ac.dnd.dodal.config.security.handler.common.AbstractAuthenticationFailureHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint extends AbstractAuthenticationFailureHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        CommonResultCode errorCode = request.getAttribute("exception") == null ?
                CommonResultCode.NOT_FOUND_END_POINT : (CommonResultCode) request.getAttribute("exception");

        setErrorResponse(response, errorCode);
    }
}