package ac.dnd.dodal.config.security.handler.common;

import ac.dnd.dodal.common.enums.ResultCode;
import ac.dnd.dodal.common.response.ApiResponse;
import ac.dnd.dodal.domain.user.enums.E_user_code;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONValue;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AbstractAuthenticationFailureHandler {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected void setErrorResponse(
            HttpServletResponse response,
            ResultCode errorCode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<?> apiResponse = ApiResponse.failure(errorCode);

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
