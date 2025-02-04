package ac.dnd.dodal.config.security.handler.logout;

import ac.dnd.dodal.common.enums.ResultCode;
import ac.dnd.dodal.common.response.ApiResponse;
import ac.dnd.dodal.config.security.handler.common.AbstractAuthenticationFailureHandler;
import ac.dnd.dodal.domain.user.enums.E_user_code;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomSignOutResultHandler extends AbstractAuthenticationFailureHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication == null) {
            setErrorResponse(response, E_user_code.NOT_FOUND_USER);
            return;
        }

        setSuccessResponse(response, E_user_code.SUCCESS_LOGOUT);
    }

    protected void setSuccessResponse(HttpServletResponse response, E_user_code successCode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<?> apiResponse = ApiResponse.success(successCode);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}

