package ac.dnd.dodal.domain.user.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum E_user_role {
    USER("USER", "ROLE_USER"),
    ADMIN("ADMIN", "ROLE_ADMIN"),
    GUEST("GUEST", "ROLE_GUEST");

    private final String name;
    private final String securityName;
}