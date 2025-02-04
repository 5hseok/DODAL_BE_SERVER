package ac.dnd.dodal.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonResultCode implements ResultCode {

    // success
    SUCCESS("COM000", "Success"),

    // failure
    // common
    BAD_REQUEST("COM001", "Bad Request"),
    NOT_FOUND("COM002", "Not Found"),
    UNAUTHORIZED("COM003", "Unauthorized"),
    FORBIDDEN("COM004", "Forbidden"),
    INTERNAL_SERVER_ERROR("COM005", "Internal Server Error"),

    // domain
    AUTH_EXCEPTION("AUT000", "Auth Exception"),
    USER_EXCEPTION("USR000", "User Exception"),
    GOAL_EXCEPTION("GOA000", "Goal Exception"),
    PLAN_EXCEPTION("PLN000", "Plan Exception"),
    INDICATOR_EXCEPTION("IND000", "Indicator Exception"),
    NOTIFICATION_EXCEPTION("NOT000", "Notification Exception"),

    // security
    TOKEN_MALFORMED_ERROR("SEC001", "Token Malformed Error"),
    TOKEN_TYPE_ERROR("SEC002", "Token Type Error"),
    EXPIRED_TOKEN_ERROR("SEC003", "Expired Token Error"),
    TOKEN_UNSUPPORTED_ERROR("SEC004", "Token Unsupported Error"),
    TOKEN_UNKNOWN_ERROR("SEC005", "Token Unknown Error"),
    INVALID_HEADER_ERROR("SEC006", "Invalid Header Error"),
    NOT_FOUND_END_POINT("SEC007", "Not Found End Point");

    private final String code;
    private final String message;
}
