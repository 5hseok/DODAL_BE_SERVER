package ac.dnd.dodal.config.security.intercepter.pre;

import ac.dnd.dodal.annotation.UserId;
import ac.dnd.dodal.common.enums.CommonResultCode;
import ac.dnd.dodal.common.exception.DodalException;
import ac.dnd.dodal.domain.user.constant.Constants;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserIDArgumentResolver implements HandlerMethodArgumentResolver {

    // 컨트롤러의 매개변수에 @UserId 어노테이션이 붙어있고, Long 타입인 경우 true 반환
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Long.class)
                && parameter.hasParameterAnnotation(UserId.class);
    }

    // supportsParameter가 true를 반환할 경우 실행되어 인터셉터에서 설정한 USER_ID_ATTRIBUTE_NAME을 통해
    // 사용자의 userId를 컨트롤러에 전달 (Long 타입으로 변환하여)
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        final Object userIdObj = webRequest.getAttribute(Constants.USER_ID_ATTRIBUTE_NAME, WebRequest.SCOPE_REQUEST);

        if (userIdObj == null) {
            throw new DodalException(CommonResultCode.INVALID_HEADER_ERROR);
        }

        return Long.valueOf((String) userIdObj);
    }
}
