package ac.dnd.dodal.config.security.util;

import ac.dnd.dodal.common.enums.CommonResultCode;
import ac.dnd.dodal.common.exception.DodalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Objects;

@Component
public class OAuth2Util {
    @Value("${spring.security.oauth2.client.provider.apple.authorization-uri}")
    private String APPLE_AUTHORIZATION_URL;

    @Value("${spring.security.oauth2.client.provider.apple.token-uri}")
    private String APPLE_TOKEN_URL;

    @Value("${spring.security.oauth2.client.provider.apple.user-info-uri}")
    private String APPLE_USERINFO_URL;

    @Value("${spring.security.oauth2.client.registration.apple.redirect-uri}")
    private String APPLE_REDIRECT_URL;

    @Value("${spring.security.oauth2.client.registration.apple.client-id}")
    private String APPLE_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.apple.client-secret}")
    private String APPLE_CLIENT_SECRET;

    private final RestClient restClient = RestClient.create();

    public String getAppleRedirectUrl() {
        return APPLE_AUTHORIZATION_URL
                + "?client_id=" + APPLE_CLIENT_ID +
                "&redirect_uri=" + APPLE_REDIRECT_URL +
                "&response_type=code";
    }

    public String getKakaoAccessToken(String authorizationCode) {
        Map<String, Object> response;

        try {
            response = Objects.requireNonNull(restClient.post()
                    .uri(APPLE_TOKEN_URL)
                    .headers(httpHeaders -> {
                        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                    })
                    .body("grant_type=authorization_code&client_id=" + APPLE_CLIENT_ID + "&client_secret=" + APPLE_CLIENT_SECRET + "&code=" + authorizationCode)
                    .retrieve()
                    .toEntity(Map.class).getBody());
        } catch (Exception e) {
            throw new DodalException(CommonResultCode.EXTERNAL_SERVER_ERROR);
        }

        return response.get("access_token").toString();
    }

    public Map<String, String> getKakaoUserInformation(String accessToken) {
        Map<String, Object> response;

        try {
            response = Objects.requireNonNull(restClient.post()
                    .uri(APPLE_USERINFO_URL)
                    .headers(httpHeaders -> {
                        httpHeaders.setBearerAuth(accessToken);
                        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                    })
                    .retrieve()
                    .toEntity(Map.class).getBody());
        } catch (Exception e) {
            throw new DodalException(CommonResultCode.EXTERNAL_SERVER_ERROR);
        }

        Map<String, Object> appleAccount = (Map<String, Object>) response.get("apple_account");
        Map<String, String> profile = (Map<String, String>) appleAccount.get("profile");

        String id = response.get("id").toString();
        String nickname = profile.get("nickname");
        String email = appleAccount.get("email").toString();

        if (response.get("id") == null || response.get("properties") == null)
            throw new DodalException(CommonResultCode.EXTERNAL_SERVER_ERROR);

        return Map.of(
                "id", id,
                "nickname", nickname,
                "email", email
        );
    }
}
