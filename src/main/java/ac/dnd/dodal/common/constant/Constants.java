package ac.dnd.dodal.common.constant;

import java.util.List;

public class Constants {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REAUTHORIZATION = "refreshToken";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String USER_ROLE_CLAIM_NAME = "role";
    public static final String USER_ID_CLAIM_NAME = "uid";
    public static final String USER_EMAIL_CLAIM_NAME = "email";

    // 소셜 로그인 관련 상수
    public static final String APPLE_PUBLIC_KEYS_URL = "https://appleid.apple.com/auth/keys";


    public static final List<String> NO_NEED_AUTH_URLS = List.of(

            //로그인 URL
            //Swagger URL
            //로그인 없이 둘러볼 수 있는 URL

    );

}
