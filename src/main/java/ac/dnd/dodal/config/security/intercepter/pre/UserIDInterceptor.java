package ac.dnd.dodal.config.security.intercepter.pre;

import ac.dnd.dodal.domain.user.constant.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class UserIDInterceptor implements HandlerInterceptor {
    // 컨트롤러에 도달하기 전에 요청을 인터셉팅하여 SecurityContextHolder에 사용자 ID를 저장
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //UserDetails를 구현한 UserPrincipal 객체의 getName 메소드를 통해 사용자 ID를 가져오기 때문에 getName 메소드로
        // 사용자의 ID를 String 타입으로 변환하여 request에 저장
        // 이 ID는 컨트롤러에서 @UserId 어노테이션을 통해 사용할 수 있음
        request.setAttribute(Constants.USER_ID_ATTRIBUTE_NAME, authentication.getName());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
