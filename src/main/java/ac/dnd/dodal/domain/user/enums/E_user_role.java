package ac.dnd.dodal.domain.user.enums;

import ac.dnd.dodal.domain.user.exception.UserException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum E_user_role {
    USER("USER", "ROLE_USER"),
    ADMIN("ADMIN", "ROLE_ADMIN"),
    GUEST("GUEST", "ROLE_GUEST");

    private final String name;
    private final String securityName;

    public static E_user_role fromName(String name) {
        return Arrays.stream(E_user_role.values()).
                filter(role -> role.getName().equals(name)).
                findFirst().
                orElseThrow(() -> new UserException(E_user_code.NO_SUCH_ROLE, "No such role: " + name));
    }
}