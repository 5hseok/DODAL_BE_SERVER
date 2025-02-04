package ac.dnd.dodal.domain.user.constant;

import java.util.List;

public class Constants {
    public static String USER_ID_ATTRIBUTE_NAME = "USER_ID";
    public static String USER_ID_CLAIM_NAME = "uid";
    public static String USER_ROLE_CLAIM_NAME = "rol";
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHORIZATION_HEADER = "Authorization";

    public static List<String> NO_NEED_AUTH_URLS = List.of(
            "/auth/reissue",
            "/oauth/login/apple",
            "/oauth/login/apple/callback",
            "/oauth/social/apple",

            "/api-docs.html",
            "/api-docs/**",

            "/favicon.ico",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",

            "/hello"
    );

    public static List<String> USER_URLS = List.of(
            "/**");

    public static List<String> ADMIN_URLS = List.of(
            "/admin/**");
    //로그인 없이 둘러보기 시 허용할 URL
//    public static List<String> GUEST_URLS = List.of(
//            "/");
}
